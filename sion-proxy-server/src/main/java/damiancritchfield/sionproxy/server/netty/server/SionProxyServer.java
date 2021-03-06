package damiancritchfield.sionproxy.server.netty.server;

import damiancritchfield.sionproxy.server.netty.initializer.SionProxyInitializer;
import damiancritchfield.sionproxy.server.netty.initializer.SionProxyServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class SionProxyServer {

    @Autowired
    private EventLoopGroup eventLoopGroup;

    private Channel channel;

    @Autowired
    private SionProxyServerChannelInitializer sionProxyServerChannelInitializer;

    @Autowired
    private SionProxyInitializer sionProxyInitializer;

    public void start(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(sionProxyInitializer)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.AUTO_READ, false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);
        ChannelFuture channelFuture = serverBootstrap.bind(inetSocketAddress);

        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();

        Runtime.getRuntime().addShutdownHook(new Thread(this::destroy));
    }

    public void destroy(){
        if(channel != null){
            channel.close();
        }
//        channelGroup.close();
        eventLoopGroup.shutdownGracefully();
    }

    @Bean
    public EventLoopGroup eventLoopGroup(){
        return new NioEventLoopGroup();
    }

}
