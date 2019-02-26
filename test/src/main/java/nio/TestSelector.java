package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TestSelector {

	private static Selector selector = null;

	static {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void register(SocketChannel channel) throws IOException {
		System.out.println("register new channel");
		channel.configureBlocking(false);
		int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT;
		channel.register(selector, interestSet);
	}

	public static void select() {

		SelectionKey key = null;
		try {
			int readyChannels = selector.selectNow();

			if (readyChannels == 0) {
				return;
			}

			Set<SelectionKey> selectedKeys = selector.selectedKeys();

			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

			while (keyIterator.hasNext()) {

				key = keyIterator.next();
				SocketChannel channel = (SocketChannel) key.channel();
				String response = "";

				// the if else in the tutorial sucks
				// channel for http://localhost:9999 can be read/written at the same time

				if (key.isAcceptable()) {
				}
				if (key.isConnectable()) {
				}
				if (key.isReadable()) {
					System.out.println("reading...");
					
					response = response + "HTTP/1.1 200 OK\r\n";
					response = response + "Content-Length: 38\r\n";
					response = response + "Content-Type: text/html\r\n";
					response = response + "\r\n";
					response = response + "<html><body>Hello World!</body></html>";
				}
				if (key.isWritable()) {
					System.out.println("writing...");
					
					write(channel, response);

					// for http
					channel.close();
					key.cancel();
				}

				keyIterator.remove();
			}
		} catch (IOException e) {
			e.printStackTrace();
			key.cancel();
		}

	}

	public static void write(SocketChannel channel, String data) throws IOException {

		// fixed size may throw BufferOverflowException for long data
		ByteBuffer buf = ByteBuffer.allocate(1024);
		buf.clear();
		buf.put(data.getBytes("UTF-8"));

		buf.flip();

		while (buf.hasRemaining()) {
			channel.write(buf);
		}

	}

}
