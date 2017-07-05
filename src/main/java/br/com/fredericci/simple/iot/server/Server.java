package br.com.fredericci.simple.iot.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.springframework.stereotype.Component;

@Component
public class Server {

	private static final int PORT = 1024;

	public void start() throws Exception {

		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			
			ServerBootstrap bootstrap = new ServerBootstrap(); // (2)
			bootstrap.group(bossGroup, workerGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(new Channel()); // (3)
			bootstrap.option(ChannelOption.SO_BACKLOG, 128); // (5)
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// Bind and start to accept incoming connections.
			ChannelFuture f = bootstrap.bind(PORT).sync(); // (7)

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to
			// gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
			
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}
	
	
	class Channel extends ChannelInitializer<SocketChannel> { // (4)
		
		@Override
		public void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new InboundServerHandler());
		}
		
	}

}
