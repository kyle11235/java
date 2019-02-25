package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TestServerSocketChannel {

	public static void listen(int port) throws IOException {

		System.out.println("listening at " + port);

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);

		while (true) {
			// non-blocking
			SocketChannel channel = serverSocketChannel.accept();

			if (channel != null) {
				TestSelector.register(channel);
			}
			// best to have other thread to select
			TestSelector.select();
		}

	}

	public static void main(String[] args) throws IOException {

		listen(9999);

	}

}
