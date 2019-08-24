package tapDame.soft_control.softServer;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import sun.misc.Request;
import tapDame.hard_control.home.server.HomeServerMethods;
import tapDame.pojo.Data;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS;
import static io.netty.handler.codec.http.HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author shipengfei
 * @data 19-8-11
 */
public class SoftServerHandler extends ChannelInboundHandlerAdapter {

    private WebSocketServerHandshaker handshaker;
    private FullHttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("======= start ========");

        String json = "";
        if (msg instanceof FullHttpRequest) {

            json = handleHttpRequest(ctx, (FullHttpRequest) msg);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(json.getBytes("UTF-8")));

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
//            if (HttpUtil.isKeepAlive(request)) {
//                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//            }

//            System.out.println();
//            System.out.println(response);

            ctx.write(response);
            ctx.flush();
        } else if (msg instanceof WebSocketFrame){
            handlerWebSocketFrame(ctx, (TextWebSocketFrame) msg);

        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private String handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest fuHr) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String uri = fuHr.uri();

        System.out.println(fuHr.toString());
        System.out.println("method:" + fuHr.method());
        String json = "";
        /**
         * 唯一的一次http请求，用于创建websocket
         * */
        //要求Upgrade为websocket，过滤掉get/Post
        if (!fuHr.decoderResult().isSuccess()
                || (!"websocket".equals(fuHr.headers().get("Upgrade")))) {
            if (fuHr.method().toString().equals("GET")) {
                if (uri.contains("?")) {
                    uri = uri.split("\\?")[0];
                }
            }
            ByteBuf buf = fuHr.content();
            String inputMessage = buf.toString(CharsetUtil.UTF_8);

//            System.out.println("imputMessage:"+inputMessage);

            String className = "tapDame.soft_control.softContraller";

            String[] strings = uri.split("/");
            for (String s : strings) {
                System.out.println(s);
            }
//            System.out.println(strings.length);
//            System.out.println(strings.toString());
            className = className + "." + strings[2];
//            System.out.println(className);

            Data data = JSON.parseObject(inputMessage, Data.class);

//            System.out.println("data:"+data.toString());
            Class proxy = Class.forName(className);
//            System.out.println(proxy.toString());

//            if (strings[2].equals("Home")){
//            }else if (strings[2].equals("Home")){
//            }else if (strings[2].equals("Home")){
//            }

            Object handler =proxy.getConstructor().newInstance();
            Method method = proxy.getDeclaredMethod(strings[strings.length - 1], Data.class);
            json = (String) method.invoke(handler, data);
            System.out.println();

//            System.out.println(data.toString());

//            System.out.println(buf.toString(CharsetUtil.UTF_8));

            return json;

        }

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:6789/websocket", null, false);
        handshaker = wsFactory.newHandshaker(fuHr);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory
                    .sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), fuHr);
        }
        return "";
    }


    private void handlerWebSocketFrame(final ChannelHandlerContext ctx, WebSocketFrame frame) throws IOException {
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
//            logger.debug("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(String.format(
                    "%s frame types not supported", frame.getClass().getName()));
        }

        String text=((TextWebSocketFrame)frame).text();

//        System.out.println(text);

        ctx.channel().writeAndFlush(new TextWebSocketFrame(text));

    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,
                                         DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        // 如果是非Keep-Alive，关闭连接
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
