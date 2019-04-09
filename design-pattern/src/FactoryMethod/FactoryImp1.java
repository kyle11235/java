package FactoryMethod;

public class FactoryImp1 implements AbstractFactory{

	@Override
	public AbstractProduct getProduct() {
		// TODO Auto-generated method stub
		return (AbstractProduct)new ProductImp1();
	}

}
