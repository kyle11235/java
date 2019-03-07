package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestBuffer {

	public void write(FileChannel channel, String data) throws IOException {

		// write to end of file
		channel.position(channel.size());

		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(data.getBytes());

		buf.flip();

		while (buf.hasRemaining()) {
			channel.write(buf);
		}

	}

	public void read(FileChannel channel) throws IOException {

		// read from beginning of file
		channel.position(0);

		// create buffer with capacity of 48 bytes
		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = channel.read(buf); // read into buffer.
		while (bytesRead != -1) {

			buf.flip(); // make buffer ready for read

			while (buf.hasRemaining()) {
				System.out.print((char) buf.get()); // read 1 byte at a time
			}

			buf.clear(); // make buffer ready for writing
			bytesRead = channel.read(buf);
		}

	}

	public static void main(String[] args) throws IOException {

		RandomAccessFile file = new RandomAccessFile("nio/nio-data.txt", "rw");
		FileChannel channel = file.getChannel();

		TestBuffer testBuffer = new TestBuffer();
		testBuffer.write(channel, "hello 中国 " + System.currentTimeMillis());
		testBuffer.read(channel);

		channel.close();
		file.close();

	}

}
