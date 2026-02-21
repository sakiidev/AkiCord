package net.akicord.network;

import net.akicord.proxy.AkiCord;
import net.akicord.utils.Logger;
import net.akicord.player.AkiPlayer;
import net.akicord.player.PlayerManager;
import net.akicord.server.ServerInfo;
import net.akicord.server.ServerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MinecraftHandler extends SimpleChannelInboundHandler<MinecraftPacket> {
    
    private AkiPlayer player;
    private int protocolVersion;
    private String hostname;
    private int port;
    private int state = 0; // 0: handshake, 1: status, 2: login, 3: play
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        Logger.info("Conexão de " + address.getAddress().getHostAddress());
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MinecraftPacket packet) {
        try {
            switch (state) {
                case 0:
                    handleHandshake(ctx, packet);
                    break;
                case 1:
                    handleStatus(ctx, packet);
                    break;
                case 2:
                    handleLogin(ctx, packet);
                    break;
                case 3:
                    handlePlay(ctx, packet);
                    break;
            }
        } finally {
            packet.release();
        }
    }
    
    private void handleHandshake(ChannelHandlerContext ctx, MinecraftPacket packet) {
        ByteBuf buf = packet.getData();
        try {
            this.protocolVersion = readVarInt(buf);
            this.hostname = readString(buf);
            this.port = buf.readUnsignedShort();
            int nextState = readVarInt(buf);
            this.state = nextState;
            
            Logger.debug("Handshake: protocol=" + protocolVersion + ", host=" + hostname + 
                        ", port=" + port + ", nextState=" + nextState);
        } catch (Exception e) {
            Logger.error("Erro no handshake: " + e.getMessage());
        }
    }
    
    private void handleStatus(ChannelHandlerContext ctx, MinecraftPacket packet) {
        if (packet.getPacketId() == 0x00) { // Status request
            sendStatusResponse(ctx);
        } else if (packet.getPacketId() == 0x01) { // Ping
            sendPong(ctx, packet);
        }
    }
    
    private void sendStatusResponse(ChannelHandlerContext ctx) {
        String motd = AkiCord.getInstance().getConfig().getMotd();
        int online = PlayerManager.getInstance().getPlayerCount();
        int max = AkiCord.getInstance().getConfig().getMaxPlayers();
        
        // Construir resposta JSON
        String response = "{\"version\":{\"name\":\"AkiCord 1.8-1.20\",\"protocol\":47},\"players\":{\"max\":" + max + ",\"online\":" + online + "},\"description\":\"" + motd + "\"}";
        
        Logger.debug("Status response: " + response);
    }
    
    private void sendPong(ChannelHandlerContext ctx, MinecraftPacket packet) {
        // Enviar pong de volta
        ctx.writeAndFlush(packet);
    }
    
    private void handleLogin(ChannelHandlerContext ctx, MinecraftPacket packet) {
        if (packet.getPacketId() == 0x00) { // Login start
            ByteBuf buf = packet.getData();
            try {
                // Ler nome do jogador
                String username = readString(buf);
                
                // Criar jogador
                UUID uuid = UUID.randomUUID(); // Na vida real, usar UUID do Mojang
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                
                this.player = new AkiPlayer(uuid, username, ctx.channel(), address);
                PlayerManager.getInstance().addPlayer(this.player);
                
                // Conectar ao servidor fallback
                ServerInfo fallback = ServerManager.getInstance().getFallbackServer();
                if (fallback != null) {
                    this.player.connect(fallback);
                }
                
                this.state = 3; // Play state
                Logger.success("Jogador logado: " + username);
                
                // Enviar sucesso do login
                sendLoginSuccess(ctx, uuid, username);
                
            } catch (Exception e) {
                Logger.error("Erro no login: " + e.getMessage());
            }
        }
    }
    
    private void sendLoginSuccess(ChannelHandlerContext ctx, UUID uuid, String username) {
        // Enviar pacote de login success
        Logger.debug("Login success sent for " + username);
    }
    
    private void handlePlay(ChannelHandlerContext ctx, MinecraftPacket packet) {
        // Processar pacotes durante o jogo
        // Encaminhar para o servidor correspondente
    }
    
    private String readString(ByteBuf buf) {
        int length = readVarInt(buf);
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    private int readVarInt(ByteBuf buf) {
        int result = 0;
        int shift = 0;
        byte b;
        
        do {
            b = buf.readByte();
            result |= (b & 0x7F) << shift;
            shift += 7;
            if (shift > 35) {
                throw new RuntimeException("VarInt muito grande");
            }
        } while ((b & 0x80) != 0);
        
        return result;
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (player != null) {
            PlayerManager.getInstance().removePlayer(player.getUniqueId());
        }
        Logger.info("Conexão fechada");
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Logger.error("Erro na conexão: " + cause.getMessage());
        ctx.close();
    }
}
