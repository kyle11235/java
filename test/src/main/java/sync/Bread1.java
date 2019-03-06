package sync;

public class Bread1 implements Bread {

	private int count;

	// entry set of monitor
	public synchronized void produce() {
		if (count < 1) {
			System.out.println("produce, count=" + ++count);
		}
	}

	public synchronized void consume() {
		if (count > 0) {
			System.out.println("consume, count=" + --count);
		}
	}

}
