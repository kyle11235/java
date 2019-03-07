package sync;

import java.util.concurrent.locks.ReentrantLock;

public class Bread2 implements Bread {

	private int count;
	private ReentrantLock lock = new ReentrantLock();

	// competition, entry set of monitor
	public void produce() {
		lock.lock();
		try {
			if (count == 0) {
				System.out.println("produce, count=" + ++count);
			}
		} finally {
			lock.unlock();
		}

	}

	public void consume() {
		lock.lock();
		try {
			if (count != 0) {
				System.out.println("consume, count=" + --count);
			}
		} finally {
			lock.unlock();
		}
	}

}
