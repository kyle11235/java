package com.thoughtworks.trainsserver.stragety;

import java.util.List;

import com.thoughtworks.trainsserver.model.Edge;

public interface Stragety {

	public Boolean terminateAt(Edge edge);
	
	public void push(Edge edge);

	public Edge pop();
	
	public List<Result> getResults();
	
	public Boolean isSuccessful();
	

}
