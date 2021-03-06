package sync;

public class Bread5 implements Bread {

	private int count;
	private boolean readable;

	// cooperation, read 1 by 1
	public synchronized void produce() {
		if (!readable) {
			System.out.println("produce, count=" + ++count);
			readable = !readable;
			this.notifyAll();
		}
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void consume() {
		if (readable) {
			System.out.println("consume, count=" + count);
			readable = !readable;
			this.notifyAll();
		}
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
