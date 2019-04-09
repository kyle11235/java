
package Strategy;


public class Upcase implements Processor {

	@Override
	public Object process(Object input) {
		// TODO Auto-generated method stub

		return ((String)input).toUpperCase();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName();
	}

}
