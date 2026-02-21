package net.akicord.network;

import net.akicord.utils.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NetworkServer {
    private final int port;
    private final String host;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;
    private boolean running = false;
    
    public NetworkServer(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public void start() {
        boolean useEpoll = Epoll.isAvailable();
        
        if (useEpoll) {
            bossGroup = new EpollEventLoopGroup();
            workerGroup = new EpollEventLoopGroup();
            Logger.info("Usando Epoll (melhor performance)");
        } else {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            Logger.info("Usando NIO");
        }
        
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                .channel(useEpoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .childHandler(new PipelineFactory())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true);
            
            channelFuture = bootstrap.bind(host, port).sync();
            running = true;
            
            Logger.success("Servidor iniciado em " + host + ":" + port);
            
        } catch (Exception e) {
            Logger.error("Erro ao iniciar servidor: " + e.getMessage());
        }
    }
    
    public void stop() {
        if (channelFuture != null) {
            channelFuture.channel().close();
        }
        
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        
        running = false;
        Logger.info("Servidor parado");
    }
    
    public boolean isRunning() {
        return running;
    }
}
