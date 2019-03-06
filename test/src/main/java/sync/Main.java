package sync;

public class Main {


	public static void main(String[] args) {

		System.out.println("------ start ------");

		Integer number = 1;
		int producerCount = 4;
		int consumerCount = 4;


		if (args.length > 0) {
			number = Integer.parseInt(args[0]);
		}
		if (args.length > 1) {
			producerCount = Integer.parseInt(args[1]);
		}
		if (args.length > 2) {
			consumerCount = Integer.parseInt(args[2]);
		}
		
		Bread bread = null;
		if(number == 1) {
			bread = new Bread1();
		}
		if(number == 2) {
			bread = new Bread2();
		}
		if(number == 3) {
			bread = new Bread3();
		}
		if(number == 4) {
			bread = new Bread4();
		}
		if(number == 5) {
			bread = new Bread5();
		}
		
		while (producerCount-- > 0) {
			new Producer(bread).start();
		}

		while (consumerCount-- > 0) {
			new Consumer(bread).start();
		}
	}
}
