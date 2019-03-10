package sync;

// wait() 

// you always need condition check before wait so synchronized is always required
// the thread releases ownership of the object's monitor and waits
// threads have no/don't need knowledge of other threads, they communicate by wait/notify of the shared object 

// notifyAll()
// The awakened threads will not be able to proceed 
// until the current thread relinquishes the lock on this object. 
// The awakened threads will compete in the usual manner 
// with any other threads that might be actively competing to synchronize on this object; 
// for example, the awakened threads enjoy no reliable privilege or disadvantage in being the next thread to lock this object.
public class Bread3 implements Bread {

	private int count;

	// cooperation, wait set of monitor, wait/notify in synchronized to release lock
	public synchronized void produce() {
		if (count == 0) {
			System.out.println("produce, count=" + ++count);
			this.notifyAll();
		}
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void consume() {
		if (count != 0) {
			System.out.println("consume, count=" + --count);
			this.notifyAll();
		}
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
