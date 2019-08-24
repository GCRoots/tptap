package tapDame.soft_control.softServer;

/**
 * @author shipengfei
 * @data 19-8-11
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import tapDame.hard_control.home.server.HomeServer;

public class SoftServer {
    public void start(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new SoftServerInitializer())
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture cf = b.bind(port).sync();
            System.out.println();
            cf.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        SoftServer httpServer = new SoftServer();
        //绑定端口，端口随意。。。但也别太随意。。。。
        httpServer.start(12345);
    }


}
