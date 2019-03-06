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
		// block it
		// serverSocketChannel.configureBlocking(false);

		TestSelector testSelector = new TestSelector();
		testSelector.start();

		while (true) {
			// blocking accept for low cpu
			SocketChannel channel = serverSocketChannel.accept();
			if (channel != null) {
				testSelector.register(channel);
			}
		}

	}

}
