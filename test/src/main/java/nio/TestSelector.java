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

				if (key.isAcceptable()) {
					// a connection was accepted by a ServerSocketChannel.
				} else if (key.isConnectable()) {
					// a connection was established with a remote server.
				} else if (key.isReadable()) {
					// a channel is ready for reading
				} else if (key.isWritable()) {
					// a channel is ready for writing
					write((SocketChannel) key.channel(), "hello " + System.currentTimeMillis());
				}

				keyIterator.remove();
			}
		} catch (IOException e) {
			e.printStackTrace();
			key.cancel();
		}

	}

	public static void write(SocketChannel channel, String data) throws IOException {

		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(data.getBytes());

		buf.flip();

		while (buf.hasRemaining()) {
			channel.write(buf);
		}
	}

}
