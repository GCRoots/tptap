package netty.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import pojo.Data;

public class HttpHandler extends SimpleChannelInboundHandler {
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
//
//    }

    private WebSocketServerHandshaker handshaker;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        if (msg instanceof FullHttpRequest) {
            // websocket连接请求
            handleHttpRequest(ctx, (FullHttpRequest)msg);
        } else if (msg instanceof WebSocketFrame) {
            // websocket业务处理
            handleWebSocketRequest(ctx, (WebSocketFrame)msg);
        }
    }


    private void handleWebSocketRequest(ChannelHandlerContext ctx, WebSocketFrame req) throws Exception {
        if (req instanceof CloseWebSocketFrame) {
            // 关闭websocket连接
            handshaker.close(ctx.channel(), (CloseWebSocketFrame)req.retain());
            return;
        }
        if (req instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(req.content().retain()));
            return;
        }
        if (!(req instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException("当前只支持文本消息，不支持二进制消息");
        }
        if (ctx == null || this.handshaker == null || ctx.isRemoved()) {
            throw new Exception("尚未握手成功，无法向客户端发送WebSocket消息");
        }


        TextWebSocketFrame text=(TextWebSocketFrame)req;
        JSONObject jsonObject= JSON.parseObject(text.text());

        Data data=JSON.parseObject(jsonObject.toString(),Data.class);

        System.out.println(data.toString());

        Data ret=new Data();
        ret.setFault("F");
        ret.setHostName("abc");

        String waf=JSON.toJSONString(ret);

        ctx.channel().writeAndFlush(waf);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // Http解码失败，向服务器指定传输的协议为Upgrade：websocket
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        // 握手相应处理,创建websocket握手的工厂类，
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8081/ws", null, false);
        // 根据工厂类和HTTP请求创建握手类
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            // 不支持websocket
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            // 通过它构造握手响应消息返回给客户端
            handshaker.handshake(ctx.channel(), req);
        }
    }



    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // BAD_REQUEST(400) 客户端请求错误返回的应答消息
        if (res.status().code() != 200) {
            // 将返回的状态码放入缓存中，Unpooled没有使用缓存池
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }
        // 发送应答消息
        ChannelFuture cf = ctx.channel().writeAndFlush(res);
        // 非法连接直接关闭连接
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            cf.addListener(ChannelFutureListener.CLOSE);
        }
    }

}
