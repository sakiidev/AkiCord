package net.akicord.player;

import net.akicord.server.ServerInfo;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.UUID;

public class AkiPlayer {
    private final UUID uniqueId;
    private final String name;
    private final Channel channel;
    private final InetSocketAddress address;
    private ServerInfo server;
    private String locale = "en_US";
    private int ping = 0;
    private boolean online = true;
    private long lastActivity = System.currentTimeMillis();
    
    public AkiPlayer(UUID uniqueId, String name, Channel channel, InetSocketAddress address) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.channel = channel;
        this.address = address;
    }
    
    public UUID getUniqueId() { return uniqueId; }
    public String getName() { return name; }
    public Channel getChannel() { return channel; }
    public InetSocketAddress getAddress() { return address; }
    public ServerInfo getServer() { return server; }
    public void setServer(ServerInfo server) { this.server = server; }
    public String getLocale() { return locale; }
    public void setLocale(String locale) { this.locale = locale; }
    public int getPing() { return ping; }
    public void setPing(int ping) { this.ping = ping; }
    public boolean isOnline() { return online; }
    public void setOnline(boolean online) { this.online = online; }
    public long getLastActivity() { return lastActivity; }
    public void updateActivity() { this.lastActivity = System.currentTimeMillis(); }
    
    public void disconnect(String reason) {
        if (channel != null && channel.isActive()) {
            channel.close();
        }
        this.online = false;
    }
    
    public void sendMessage(String message) {
        // Enviar mensagem para o jogador
        // Implementar sistema de mensagens
    }
    
    public void connect(ServerInfo target) {
        this.server = target;
        // Implementar conexão com servidor
    }
}
