package br.com.fredericci.iot.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import br.com.fredericci.iot.common.Message;

public class ObjectHandler extends ChannelInboundHandlerAdapter {

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	public ObjectHandler(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
		this.bossGroup = bossGroup;
		this.workerGroup = workerGroup;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object object) throws Exception {

		Message message = (Message) object;
		
		String value = message.getValue();

		System.out.println(value);
		
		if( value.equalsIgnoreCase("bye")) {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}

	}

}
