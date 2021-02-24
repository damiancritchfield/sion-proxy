package damiancritchfield.sionproxy.server.netty.client;

import damiancritchfield.sionproxy.server.netty.handler.SionProxyBackendHandler;
import damiancritchfield.sionproxy.server.netty.initializer.SionProxyClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NettyClient {

    @Autowired
    private SionProxyClientChannelInitializer sionProxyClientChannelInitializer;

    public Channel connect() throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.AUTO_READ, false);
        bootstrap.handler(sionProxyClientChannelInitializer);

        Channel channel = bootstrap.connect("www.liaolangsheng.com", 80).sync().channel();
        channel.closeFuture().addListener((ChannelFutureListener) future -> workerGroup.shutdownGracefully());

        return channel;
    }

    public ChannelFuture onlyConnect(Channel channel){
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.AUTO_READ, false);
        bootstrap.handler(new SionProxyBackendHandler(channel));

        return bootstrap.connect("www.cnblogs.com", 443);
    }

}
