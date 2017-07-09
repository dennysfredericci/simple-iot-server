package br.com.fredericci.iot.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;


public class Server {

	private int port;

	private EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	public Server(int port) {
		this.port = port;
	}

	public void startAndWait() throws Exception {

		try {

			ServerBootstrap bootstrap = new ServerBootstrap(); // (2)
			bootstrap.group(bossGroup, workerGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(new Channel()); // (3)
			bootstrap.option(ChannelOption.SO_BACKLOG, 128); // (5)
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// Bind and start to accept incoming connections.
			ChannelFuture channel = bootstrap.bind(port).sync(); // (7)

			// Wait until the server socket is closed.
			channel.channel().closeFuture().sync();
			
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}
	

	class Channel extends ChannelInitializer<SocketChannel> { // (4)
		
		@Override
		public void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast("frameDecoder" , new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
			ch.pipeline().addLast("objectHandler", new ObjectHandler(bossGroup, workerGroup));
		}
		
	}

}

