package net.akicord.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class PipelineFactory extends ChannelInitializer<Channel> {
    
    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        
        // Timeout de leitura (30 segundos)
        pipeline.addLast("timeout", new ReadTimeoutHandler(30));
        
        // Decodificador de pacotes Minecraft
        pipeline.addLast("decoder", new MinecraftDecoder());
        
        // Codificador de pacotes Minecraft
        pipeline.addLast("encoder", new MinecraftEncoder());
        
        // Handler principal
        pipeline.addLast("handler", new MinecraftHandler());
    }
}
