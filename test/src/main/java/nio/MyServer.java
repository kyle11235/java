package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class MyServer {

	public void listen(int port) throws IOException {

		System.out.println("listening at " + port);

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		// block it
		// serverSocketChannel.configureBlocking(false);

		Worker worker = new Worker();
		worker.start();

		while (true) {
			// block it for low cpu
			SocketChannel channel = serverSocketChannel.accept();
			if (channel != null) {
				worker.register(channel);
			}
		}

	}

}
