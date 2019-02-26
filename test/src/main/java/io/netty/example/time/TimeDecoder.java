package io.netty.example.time;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TimeDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        
    	if (in.readableBytes() < 4) {
            return; // (3)
        }
    	
    	// split stream into messages
        out.add(in.readBytes(4)); // (4)
    }
}