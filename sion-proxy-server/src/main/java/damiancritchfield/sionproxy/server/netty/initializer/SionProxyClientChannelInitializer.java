package damiancritchfield.sionproxy.server.netty.initializer;

import damiancritchfield.sionproxy.server.netty.handler.SionProxyClientHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.springframework.stereotype.Component;

@Component
public class SionProxyClientChannelInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline channelPipeline = channel.pipeline();
        channelPipeline.addLast(new ByteArrayDecoder(), new ByteArrayEncoder());
        channel.pipeline().addLast(new SionProxyClientHandler());
    }
}
