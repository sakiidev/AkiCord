package net.akicord.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.akicord.utils.Logger;

import java.util.List;

public class MinecraftDecoder extends ByteToMessageDecoder {
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        in.markReaderIndex();
        
        if (!in.isReadable()) {
            return;
        }
        
        try {
            // Ler comprimento do pacote (VarInt)
            int packetLength = readVarInt(in);
            
            if (packetLength <= 0) {
                in.resetReaderIndex();
                return;
            }
            
            if (in.readableBytes() < packetLength) {
                in.resetReaderIndex();
                return;
            }
            
            // Ler ID do pacote (VarInt)
            int packetId = readVarInt(in);
            
            // Ler dados do pacote
            ByteBuf data = in.readBytes(packetLength - getVarIntSize(packetId));
            
            // Criar pacote
            MinecraftPacket packet = new MinecraftPacket(packetId, data);
            out.add(packet);
            
        } catch (Exception e) {
            in.resetReaderIndex();
            Logger.debug("Erro ao decodificar pacote: " + e.getMessage());
        }
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
    
    private int getVarIntSize(int value) {
        int size = 0;
        do {
            value >>>= 7;
            size++;
        } while (value != 0);
        return size;
    }
}
