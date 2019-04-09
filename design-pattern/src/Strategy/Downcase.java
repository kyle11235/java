package Strategy;

public class Downcase implements Processor {

	@Override
	public Object process(Object input) {
		// TODO Auto-generated method stub
		return ((String)input).toLowerCase();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName();
	}

}
