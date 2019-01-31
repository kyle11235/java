package test;

import jdbc.Druid;
import jdbc.Hikari;
import jdbc.MysqlDS;
import jdbc.OracleDS;
import jdbc.UCP;

public class App {

	public static void main(String[] args) {

		System.out.println("------ start ------");

		Integer threads = 1;
		Integer count = 1;
		String ds = "mysql";

		if (args.length > 0) {
			threads = Integer.parseInt(args[0]);
		}

		if (args.length > 1) {
			count = Integer.parseInt(args[1]);
		}

		if (args.length > 2) {
			ds = args[2];
		}

		System.out.println("threads=" + threads);
		System.out.println("count=" + count);
		System.out.println("datasource=" + ds);

		for (int i = 0; i < threads; i++) {
			if ("mysql".equals(ds)) {
				new Thread(new Worker(new MysqlDS(), count)).start();
			}
			if ("druid".equals(ds)) {
				new Thread(new Worker(new Druid(), count)).start();
			}
			if ("hikari".equals(ds)) {
				new Thread(new Worker(new Hikari(), count)).start();
			}
			if ("ucp".equals(ds)) {
				new Thread(new Worker(new UCP(), count)).start();
			}
			if ("oracle".equals(ds)) {
				new Thread(new Worker(new OracleDS(), count)).start();
			}
		}

		System.out.println("------ end ------");

	}

}
