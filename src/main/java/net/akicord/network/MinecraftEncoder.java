package net.akicord.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MinecraftEncoder extends MessageToByteEncoder<MinecraftPacket> {
    
    @Override
    protected void encode(ChannelHandlerContext ctx, MinecraftPacket packet, ByteBuf out) {
        // Calcular tamanho total
        int packetId = packet.getPacketId();
        int packetIdSize = getVarIntSize(packetId);
        int dataSize = packet.getData() != null ? packet.getData().readableBytes() : 0;
        int totalSize = packetIdSize + dataSize;
        
        // Escrever comprimento do pacote (VarInt)
        writeVarInt(out, totalSize);
        
        // Escrever ID do pacote (VarInt)
        writeVarInt(out, packetId);
        
        // Escrever dados do pacote
        if (packet.getData() != null) {
            out.writeBytes(packet.getData());
        }
    }
    
    private void writeVarInt(ByteBuf buf, int value) {
        do {
            byte temp = (byte) (value & 0x7F);
            value >>>= 7;
            if (value != 0) {
                temp |= 0x80;
            }
            buf.writeByte(temp);
        } while (value != 0);
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
