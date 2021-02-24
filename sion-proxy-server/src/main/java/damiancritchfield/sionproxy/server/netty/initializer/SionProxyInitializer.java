package damiancritchfield.sionproxy.server.netty.initializer;

import damiancritchfield.sionproxy.server.netty.handler.SionProxyFrontendHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.springframework.stereotype.Component;

@Component
public class SionProxyInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new SionProxyFrontendHandler());
    }
}
