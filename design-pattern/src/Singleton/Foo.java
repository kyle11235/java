package Singleton;

import util.P;

public class Foo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		P.p(Singleton.getInstance().getId());
		P.p(Singleton.getInstance().getId());
		P.p(Singleton.getInstance().getId());
	}

}
