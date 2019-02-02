package test;

import jdbc.C3P0;
import jdbc.DBCP;
import jdbc.Druid;
import jdbc.Hikari;
import jdbc.MysqlDS;
import jdbc.OracleDS;
import jdbc.UCP;

public class Main {

	public static void main(String[] args) {

		System.out.println("------ start ------");

		Integer threads = 1;
		Integer count = 1;
		String ds = "hikari";

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
		System.out.println("count for each thread=" + count);
		System.out.println("datasource=" + ds);

		for (int i = 0; i < threads; i++) {
			if ("mysql".equals(ds)) {
				new Thread(new Tester(new MysqlDS(), count)).start();
			}
			if ("druid".equals(ds)) {
				new Thread(new Tester(new Druid(), count)).start();
			}
			if ("hikari".equals(ds)) {
				new Thread(new Tester(new Hikari(), count)).start();
			}
			if ("ucp".equals(ds)) {
				new Thread(new Tester(new UCP(), count)).start();
			}
			if ("oracle".equals(ds)) {
				new Thread(new Tester(new OracleDS(), count)).start();
			}
			if ("c3p0".equals(ds)) {
				new Thread(new Tester(new C3P0(), count)).start();
			}
			if ("dbcp".equals(ds)) {
				new Thread(new Tester(new DBCP(), count)).start();
			}
		}

		System.out.println("------ end ------");

	}

}
