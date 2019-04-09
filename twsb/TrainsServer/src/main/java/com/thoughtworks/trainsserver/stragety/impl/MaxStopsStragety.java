package com.thoughtworks.trainsserver.stragety.impl;

import java.util.Stack;

import com.thoughtworks.trainsserver.model.Edge;
import com.thoughtworks.trainsserver.stragety.Result;

public class MaxStopsStragety extends BaseStragety {

	private Integer maxStops;

	@SuppressWarnings("unchecked")
	@Override
	public Boolean terminateAt(Edge edge) {
		if (edge == null || (currentEdges.size()) > maxStops) {
			return true;
		}
		if (nodeTo.equals(edge.getNodeTo())) {
			Result result = new Result();
			result.setEdges((Stack<Edge>) currentEdges.clone());
			results.add(result);
		}
		return false;
	}
	
	public void setMaxStops(Integer maxStops) {
		this.maxStops = maxStops;
	}

}
