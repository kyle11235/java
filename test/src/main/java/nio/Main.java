package nio;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		System.out.println("------ start ------");

		TestServerSocketChannel.listen(9999);

		System.out.println("------ end ------");

	}

}
