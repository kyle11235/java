package sync;

// java.lang.Thread.State

/**
 * Thread state for a thread which has not yet started.
 */
//NEW,

/**
 * Thread state for a runnable thread.  A thread in the runnable
 * state is executing in the Java virtual machine but it may
 * be waiting for other resources from the operating system
 * such as processor.
 */
//RUNNABLE,

/**
 * Thread state for a thread blocked waiting for a monitor lock.
 * A thread in the blocked state is waiting for a monitor lock
 * to enter a synchronized block/method or
 * reenter a synchronized block/method after calling
 * {@link Object#wait() Object.wait}.
 */
//BLOCKED,

/**
 * Thread state for a waiting thread.
 * A thread is in the waiting state due to calling one of the
 * following methods:
 * <ul>
 *   <li>{@link Object#wait() Object.wait} with no timeout</li>
 *   <li>{@link #join() Thread.join} with no timeout</li>
 *   <li>{@link LockSupport#park() LockSupport.park}</li>
 * </ul>
 *
 * <p>A thread in the waiting state is waiting for another thread to
 * perform a particular action.
 *
 * For example, a thread that has called <tt>Object.wait()</tt>
 * on an object is waiting for another thread to call
 * <tt>Object.notify()</tt> or <tt>Object.notifyAll()</tt> on
 * that object. A thread that has called <tt>Thread.join()</tt>
 * is waiting for a specified thread to terminate.
 */
//WAITING,

/**
 * Thread state for a waiting thread with a specified waiting time.
 * A thread is in the timed waiting state due to calling one of
 * the following methods with a specified positive waiting time:
 * <ul>
 *   <li>{@link #sleep Thread.sleep}</li>
 *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
 *   <li>{@link #join(long) Thread.join} with timeout</li>
 *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
 *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
 * </ul>
 */
//TIMED_WAITING,

/**
 * Thread state for a terminated thread. The thread has completed execution.
 */
//TERMINATED;

public class Main {

	public static void main(String[] args) {

		System.out.println("------ start ------");

		Integer number = 1;
		int producerCount = 4;
		int consumerCount = 4;

		if (args.length > 0) {
			number = Integer.parseInt(args[0]);
		}
		if (args.length > 1) {
			producerCount = Integer.parseInt(args[1]);
		}
		if (args.length > 2) {
			consumerCount = Integer.parseInt(args[2]);
		}

		Bread bread = null;
		if (number == 1) {
			bread = new Bread1();
		}
		if (number == 2) {
			bread = new Bread2();
		}
		if (number == 3) {
			bread = new Bread3();
		}
		if (number == 4) {
			bread = new Bread4();
		}
		if (number == 5) {
			bread = new Bread5();
		}
		if (number == 6) {
			bread = new Bread6();
		}

		while (producerCount-- > 0) {
			new Producer(bread).start();
		}

		while (consumerCount-- > 0) {
			new Consumer(bread).start();
		}
	}
}
