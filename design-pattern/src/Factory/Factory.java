package Factory;

public class Factory {

	public AbstractProduct getProduct(String name) {

		if (name.equals("ProductImp1")) {
			return (AbstractProduct) new ProductImp1();
		}
		if (name.equals("ProductImp2")) {
			return (AbstractProduct) new ProductImp2();

		} else {
			return null;
		}
	}
}
