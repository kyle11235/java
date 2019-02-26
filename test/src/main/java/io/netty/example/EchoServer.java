package io.netty.example;

import io.netty.example.echo.EchoServerHandler;

public class EchoServer {

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}

		new BaseServer(port).run(new EchoServerHandler());
	}
}