package sync;

public class Bread6 implements Bread {

	private Object a = new Object();
	private Object b = new Object();

	// deadlock
	public void produce() {
		synchronized (a) {
			// locked a
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// wait to lock b
			synchronized (b) {
				System.out.println(b);
			}
		}
	}

	public void consume() {
		// locked b
		synchronized (b) {
			// wait to lock a, deadlock
			synchronized (a) {
				System.out.println(a);
			}
		}
	}
}

//Java stack information for the threads listed above:
//===================================================
//"Thread-9":
//	at sync.Bread6.consume(Bread6.java:25)
//	- waiting to lock <0x00000007ae052078> (a java.lang.Object)
//	at sync.Consumer.run(Consumer.java:16)
//"Thread-6":
//	at sync.Bread6.consume(Bread6.java:26)
//	- waiting to lock <0x00000007ae052068> (a java.lang.Object)
//	- locked <0x00000007ae052078> (a java.lang.Object)
//	at sync.Consumer.run(Consumer.java:16)
//"Thread-4":
//	at sync.Bread6.produce(Bread6.java:18)
//	- waiting to lock <0x00000007ae052078> (a java.lang.Object)
//	- locked <0x00000007ae052068> (a java.lang.Object)
//	at sync.Producer.run(Producer.java:16)
//
//Found 1 deadlock.


