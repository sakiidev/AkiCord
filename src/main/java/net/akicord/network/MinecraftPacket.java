package net.akicord.network;

import io.netty.buffer.ByteBuf;

public class MinecraftPacket {
    private final int packetId;
    private final ByteBuf data;
    
    public MinecraftPacket(int packetId, ByteBuf data) {
        this.packetId = packetId;
        this.data = data;
    }
    
    public int getPacketId() {
        return packetId;
    }
    
    public ByteBuf getData() {
        return data;
    }
    
    public void release() {
        if (data != null) {
            data.release();
        }
    }
}
