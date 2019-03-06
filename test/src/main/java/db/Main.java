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
		
		Worker worker = null;
		if ("mysql".equals(ds)) {
			worker = new Worker(new MysqlDS(), count);
		}
		if ("druid".equals(ds)) {
			worker = new Worker(new Druid(), count);
		}
		if ("hikari".equals(ds)) {
			worker = new Worker(new Hikari(), count);
		}
		if ("ucp".equals(ds)) {
			worker = new Worker(new UCP(), count);
		}
		if ("oracle".equals(ds)) {
			worker = new Worker(new OracleDS(), count);
		}
		if ("c3p0".equals(ds)) {
			worker = new Worker(new C3P0(), count);
		}
		if ("dbcp".equals(ds)) {
			worker = new Worker(new DBCP(), count);
		}
		
		for (int i = 0; i < threads; i++) {
			new Thread(worker).start();
		}

		System.out.println("------ end ------");

	}

}
