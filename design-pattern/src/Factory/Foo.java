package Factory;

import util.P;

public class Foo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			P.p(new Factory().getProduct("ProductImp1").getName());
			P.p(new Factory().getProduct("ProductImp2").getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
