package damiancritchfield.sionproxy.server.netty.handler;

import damiancritchfield.sionproxy.server.common.SpringApplicationContext;
import damiancritchfield.sionproxy.server.netty.client.NettyClient;
import damiancritchfield.sionproxy.server.netty.service.ChannelService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class SionProxyServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(SionProxyServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("read");

        Channel clientChannel = this.getChannelService().findClientChannel(channelHandlerContext.channel());
        if(clientChannel == null){
            ChannelFuture channelFuture = channelHandlerContext.channel().
                    writeAndFlush(Unpooled.copiedBuffer("no client", StandardCharsets.UTF_8));
            channelFuture.syncUninterruptibly();
            channelHandlerContext.close();
            return;
        }

        byte[] bytes = (byte[])o;
        String text = new String(bytes, StandardCharsets.UTF_8);

        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        clientChannel.writeAndFlush(byteBuf);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.fireChannelActive();
//        ctx.writeAndFlush(Unpooled.copiedBuffer("connect success", CharsetUtil.UTF_8));

        //创建链接
        NettyClient nettyClient = SpringApplicationContext.getBean(NettyClient.class);
        Channel channel = nettyClient.connect();
        this.getChannelService().bindChannel(ctx.channel(), channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        ctx.fireChannelInactive();
//        this.getChannelService().unbindByServerChannel(ctx.channel());
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
