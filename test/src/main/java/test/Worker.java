package test;

import java.sql.Connection;

import jdbc.MyDS;

public class Worker implements Runnable {

	private MyDS ds;
	private Integer count;

	public Worker(MyDS ds, Integer count) {
		this.ds = ds;
		this.count = count;
	}

	public void run() {
		long start = 0;
		long end = 0;
		long total = 0;
		Connection connection = null;
		for (int i = 0; i < count; i++) {
			try {
				start = System.currentTimeMillis();
				connection = ds.getConnection();
				end = System.currentTimeMillis();
				total += (end - start);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		System.out.println(Thread.currentThread().getName() + ", avg=" + (total / count));
	}
}
