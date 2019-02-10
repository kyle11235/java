package jvm;

import java.util.Timer;

public class Main {

	public static void main(String[] args) {

		System.out.println("------ start ------");

		String type = "static";
		Integer count = 1;

		if (args.length > 0) {
			type = args[0];
		}

		if (args.length > 1) {
			count = Integer.parseInt(args[1]);
		}

		System.out.println("type=" + type);
		System.out.println("count for each second=" + count);

		Timer timer = new Timer();
		Tester tester = null;
		if("static".equals(type)) {
			tester = new Tester(new Static(), count);
		}
		if("statement".equals(type)) {
			tester = new Tester(new Statement(), count);
		}
		timer.scheduleAtFixedRate(tester, 0, 1000);

		System.out.println("------ end ------");

	}

}
