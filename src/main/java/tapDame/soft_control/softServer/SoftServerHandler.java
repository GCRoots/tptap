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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("======= start ========");

        String json = "";
        if (msg instanceof FullHttpRequest) {

            json = handleHttpRequest(ctx, (FullHttpRequest) msg);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(json.getBytes(StandardCharsets.UTF_8)));
            response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.headers().set(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
            ctx.writeAndFlush(response);
        } else if (msg instanceof WebSocketFrame){
            handlerWebSocketFrame(ctx, (TextWebSocketFrame) msg);

        }

//
//        Data reData=new Data();
//
//        String uri="";
//
//        if (msg instanceof HttpRequest) {
//            //这里可以去取header之类的东西
//            request = (FullHttpRequest) msg;
//            uri=request.uri();
//            System.out.println(uri);
//        }
//
//        if (msg instanceof WebSocketFrame) {
//            //这里来做content的相关处理吧
//            try {
//
//                HttpContent content = (HttpContent) msg;
//                ByteBuf buf = content.content();
//                String inputMessage = buf.toString(CharsetUtil.UTF_8);
//
//                String className = "tapDame.soft_control.softContraller";
//
//                String[] strings=uri.split("/");
//                for (String s:strings){
//                    System.out.println(s);
//                }
//
//                className=className+"."+strings[0];
//
//                Data data = JSON.parseObject(inputMessage, Data.class);
//                Class proxy = Class.forName(className);
//                HomeServerMethods handler=(HomeServerMethods)proxy.getConstructor().newInstance();
//                Method method=proxy.getDeclaredMethod(strings[strings.length-1],Data.class);
//                reData= (Data) method.invoke(handler,data);
//                System.out.println();
//
//
//                System.out.println(data.toString());
//
//                String humidity=data.getHumidity();
//
//                System.out.println(humidity);
//                System.out.println(buf.toString(CharsetUtil.UTF_8));
//
//            } catch (Exception e) {
//                System.out.println("HttpContent bad");
//            }
//
//            /*
//             *这中间可以写service,dao,等等的东西。。。感兴趣的话，大家fork一下代码，然后自由发挥就行。
//             * */
//
//
//            //response相关。。。
//
//
//
//            String res = JSON.toJSONString(reData);
//
//            FullHttpResponse response = new DefaultFullHttpResponse(
//                    HttpVersion.HTTP_1_1,
//                    HttpResponseStatus.OK,
//                    Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
//
//            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
//            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
//            if (HttpUtil.isKeepAlive(request)) {
//                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//
//            }
//
//            ctx.write(response);
//            ctx.flush();
//        }

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
        String url = fuHr.uri();
        System.out.println("method:" + fuHr.method());
        String json = "666666";
        /**
         * 唯一的一次http请求，用于创建websocket
         * */
        //要求Upgrade为websocket，过滤掉get/Post
        if (!fuHr.decoderResult().isSuccess()
                || (!"websocket".equals(fuHr.headers().get("Upgrade")))) {
            if (fuHr.method().toString().equals("GET")) {
                if (url.contains("?")) {
                    url = url.split("\\?")[0];
                }
            }
//            logger.info("URL: " + url);
            ByteBuf byteBuf = fuHr.content();
            String data = byteBuf.toString(Charset.forName("utf-8"));
//            logger.info("data " + data);
            String className = "Controller." + url.split("/")[1];
            String methodName = "Return";
            Class clz = Class.forName(className);
            //Constructor constructor = clz.getConstructor(String.class);
            // Object object = constructor.newInstance(data);
            Object object = clz.newInstance();
            //System.out.println(data);
            Method m = object.getClass().getDeclaredMethod(methodName, String.class);
            json = (String) m.invoke(object, data);
            System.out.println(json);
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

        System.out.println(text);


//        ctx.channel().writeAndFlush(new TextWebSocketFrame(((TextWebSocketFrame)frame).text()));

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
