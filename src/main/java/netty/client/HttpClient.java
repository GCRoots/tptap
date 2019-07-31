package netty.client;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class HttpClient {

    private final String host;
    private final int port;

    public static void main(String[] args) throws Exception {
        new HttpClient("localhost", 8089).run();

    }

    public HttpClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpClientInitializer());
//            Channel channel = bootstrap.connect(host, port).sync().channel();

            ChannelFuture f = bootstrap.connect(host, port).sync();

            URI uri = new URI("http://127.0.0.1:8089");
            String msg = "{\"HostName\":\"cc\"}";
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

            // 构建http请求
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());

            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();


//            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//            String s="{\"HostName\":\"cc\"}";
//
//            while (true) {
//                String s1=in.readLine();
//                if (s1=="") {
//                    channel.writeAndFlush(s);
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }



}
