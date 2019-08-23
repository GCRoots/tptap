package tapDame.soft_control.softServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author shipengfei
 * @data 19-8-11
 */
public class SoftServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpResponseEncoder());//server端发送的是httpResponse,要进行编码

        pipeline.addLast(new HttpRequestDecoder());//server端接收的是httpRequest,要进行解码

        pipeline.addLast(new HttpObjectAggregator(65535));
        //等待解码后的报文头和报文体一起扔给下一层
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new SoftServerHandler());//自定义handler
    }
}
