package db;

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
		
		Tester tester = null;
		if ("mysql".equals(ds)) {
			tester = new Tester(new MysqlDS(), count);
		}
		if ("druid".equals(ds)) {
			tester = new Tester(new Druid(), count);
		}
		if ("hikari".equals(ds)) {
			tester = new Tester(new Hikari(), count);
		}
		if ("ucp".equals(ds)) {
			tester = new Tester(new UCP(), count);
		}
		if ("oracle".equals(ds)) {
			tester = new Tester(new OracleDS(), count);
		}
		if ("c3p0".equals(ds)) {
			tester = new Tester(new C3P0(), count);
		}
		if ("dbcp".equals(ds)) {
			tester = new Tester(new DBCP(), count);
		}
		
		for (int i = 0; i < threads; i++) {
			new Thread(tester).start();
		}

		System.out.println("------ end ------");

	}

}
