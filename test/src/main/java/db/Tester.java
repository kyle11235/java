package db;

import java.sql.Connection;

public class Tester implements Runnable {

	private DS ds;
	private Integer count;

	public Tester(DS ds, Integer count) {
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
