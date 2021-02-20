package damiancritchfield.sionproxy.server.netty.handler;

import damiancritchfield.sionproxy.server.common.SpringApplicationContext;
import damiancritchfield.sionproxy.server.netty.service.ChannelService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLHandshakeException;
import java.nio.charset.StandardCharsets;

public class SionProxyClientHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(SionProxyClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel serverChannel = this.getChannelService().findServerChannel(ctx.channel());
        if(serverChannel == null){
            logger.error("no serverChannel");
            return;
        }

        byte[] bytes = (byte[])msg;

        String text = new String(bytes, StandardCharsets.UTF_8);

        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        serverChannel.writeAndFlush(byteBuf);
    }

    private ChannelService getChannelService(){
        return SpringApplicationContext.getBean(ChannelService.class);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
