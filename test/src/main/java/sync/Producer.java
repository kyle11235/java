package sync;

public class Producer extends Thread {

	private Bread bread;

	public Producer(Bread bread) {
		this.bread = bread;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				bread.produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
