package jvm;

import java.util.TimerTask;

public class Tester extends TimerTask {
	
	private Leaker leaker;
	private Integer count;


	public Tester(Leaker leaker, Integer count) {
		this.leaker = leaker;
		this.count = count;
	}

	public void run() {
		System.out.println("leaking count=" + count);

		for (int i = 0; i < count; i++) {
			leaker.leak();
		}
	}
}
