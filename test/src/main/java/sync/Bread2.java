package sync;

import java.util.concurrent.locks.ReentrantLock;

public class Bread2 implements Bread {

	private int count;
	private ReentrantLock lock = new ReentrantLock();

	// entry set of monitor
	public void produce() {
		lock.lock();
		if (count < 1) {
			System.out.println("produce, count=" + ++count);
		}
		lock.unlock();
	}

	public void consume() {
		lock.lock();
		if (count > 0) {
			System.out.println("consume, count=" + --count);
		}
		lock.unlock();
	}

}
