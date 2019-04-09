package Factory;

public class ProductImp2 extends AbstractProduct {

	public ProductImp2(){
		super.name = "ProductImp2";
	}
	@Override
	public String getName(){
		return super.name;
	}
}
