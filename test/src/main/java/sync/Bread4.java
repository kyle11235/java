package sync;

public class Bread4 implements Bread {

	private int count;
	private boolean readable = false;

	// read 1 by 1, high cpu
	public synchronized void produce() {
		if (!readable) {
			System.out.println("produce, count=" + ++count);
			readable = !readable;
		}
	}

	public synchronized void consume() {
		if (readable) {
			System.out.println("read, count=" + count);
			readable = !readable;
		}
	}

}
