package damiancritchfield.sionproxy.server.netty.initializer;

import damiancritchfield.sionproxy.server.netty.handler.SionProxyServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.springframework.stereotype.Component;

@Component
public class SionProxyServerChannelInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline channelPipeline = channel.pipeline();

//        channel.pipeline().addLast(new FixedLengthFrameDecoder(8));

        channelPipeline.addLast(new ByteArrayDecoder(), new ByteArrayEncoder());
        channel.pipeline().addLast(new SionProxyServerHandler());

//        channelPipeline.addLast(new HttpServerCodec());
//        channelPipeline.addLast(new ChunkedWriteHandler());
//        channelPipeline.addLast(new HttpObjectAggregator(64 * 1024));


    }
}
