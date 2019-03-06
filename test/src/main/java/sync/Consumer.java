package sync;

public class Consumer extends Thread {

	private Bread bread;

	public Consumer(Bread bread) {
		this.bread = bread;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				bread.consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
