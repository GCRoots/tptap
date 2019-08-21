package tapDame.hard_control.home.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import tapDame.pojo.Data;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class HomeServerHandler extends ChannelInboundHandlerAdapter {

    private FullHttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        long start = System.nanoTime();

        String res="";

        String uri="";

        if (msg instanceof HttpRequest) {
            //这里可以去取header之类的东西
            request = (FullHttpRequest) msg;
            uri=request.uri();
            System.out.println(uri);
        }

        if (msg instanceof HttpContent) {
            //这里来做content的相关处理吧
            try {

                HttpContent content = (HttpContent) msg;
                ByteBuf buf = content.content();
                String inputMessage = buf.toString(CharsetUtil.UTF_8);

                String className = "tapDame.hard_control.home.server.HomeServerMethods";

                String[] strings=uri.split("/");
                for (String s:strings){
                    System.out.println(s);
                }

                Data data = JSON.parseObject(inputMessage, Data.class);
                Class proxy = Class.forName(className);
                HomeServerMethods handler=(HomeServerMethods)proxy.getConstructor().newInstance();
                Method method=proxy.getDeclaredMethod(strings[strings.length-1],Data.class);
                res= (String) method.invoke(handler,data);
//                System.out.println();


//                System.out.println(data.toString());

                String humidity=data.getHumidity();

//                System.out.println(humidity);
//                System.out.println(buf.toString(CharsetUtil.UTF_8));

            } catch (Exception e) {
                System.out.println("HttpContent bad");
            }

            /*
             *这中间可以写service,dao,等等的东西。。。感兴趣的话，大家fork一下代码，然后自由发挥就行。
             * */


            //response相关。。。


            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(res.getBytes("UTF-8")));

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            if (HttpUtil.isKeepAlive(request)) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

            }

            ctx.write(response);
            ctx.flush();
        }


        long end = System.nanoTime();

        long resultTime = end - start;
        //计算每次请求到返回的用时
        System.out.println(TimeUnit.NANOSECONDS.toMillis(resultTime));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
