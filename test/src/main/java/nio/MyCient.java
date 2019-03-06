package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MyCient {

	public static void connect() throws IOException {

		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);

		// non-blocking
		channel.connect(new InetSocketAddress("localhost", 9999));

		while (!channel.finishConnect()) {
			// wait, or do something else...
		}

		read(channel);
	}

	public static void read(SocketChannel channel) throws IOException {

		// create buffer with capacity of 48 bytes
		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = channel.read(buf); // read into buffer.
		while (bytesRead != -1) {

			buf.flip(); // make buffer ready for read

			while (buf.hasRemaining()) {
				System.out.print((char) buf.get()); // read 1 byte at a time
			}
			System.out.println();

			buf.clear(); // make buffer ready for writing
			bytesRead = channel.read(buf);
		}

	}

	public static void main(String[] args) throws IOException {

		connect();

	}

}
