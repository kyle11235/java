package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {

	private Selector selector = null;
	private final ReentrantLock selectorLock = new ReentrantLock();

	public Worker() {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void register(SocketChannel channel) throws IOException {
		channel.configureBlocking(false);
		int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT;

		// prevent selector from a wake up and immediate another select again
		selectorLock.lock();
		try {
			selector.wakeup();
			channel.register(selector, interestSet);
			System.out.println("channel is registered");
		} finally {
			selectorLock.unlock();
		}
	}

	@Override
	public void run() {
		while (true) {
			SelectionKey key = null;
			try {

				selectorLock.lock();
				selectorLock.unlock();
				// blocking select
				int readyChannels = selector.select();

				if (readyChannels == 0) {
					continue;
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
