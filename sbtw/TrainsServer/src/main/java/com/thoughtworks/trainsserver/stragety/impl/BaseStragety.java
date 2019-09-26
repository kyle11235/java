package com.thoughtworks.trainsserver.stragety.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.thoughtworks.trainsserver.model.Edge;
import com.thoughtworks.trainsserver.model.Node;
import com.thoughtworks.trainsserver.stragety.Result;
import com.thoughtworks.trainsserver.stragety.Stragety;

public abstract class BaseStragety implements Stragety {

	protected Node nodeTo;
	protected Stack<Edge> currentEdges = new Stack<Edge>();
	protected List<Result> results = new LinkedList<Result>();

	@Override
	public void push(Edge edge) {
		currentEdges.push(edge);
	}

	@Override
	public Edge pop() {
		return currentEdges.pop();
	}

	@Override
	public List<Result> getResults() {
		return results;
	}

	@Override
	public Boolean isSuccessful() {
		return !results.isEmpty();
	}

	public void setNodeTo(Node nodeTo) {
		this.nodeTo = nodeTo;
	}

}
