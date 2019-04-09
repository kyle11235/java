package Adapter;

import Strategy.Processor;

public class FilterAdapter implements Processor {

	private Filter filter;
	public FilterAdapter(Filter filter){
		this.filter = filter;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "filter adapter";
	}

	@Override
	public Object process(Object input) {
		// TODO Auto-generated method stub
		return filter.process((Integer)input);
	}

}
