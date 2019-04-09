package com.thoughtworks.trainsserver.stragety.impl;

import java.util.Stack;

import com.thoughtworks.trainsserver.model.Edge;
import com.thoughtworks.trainsserver.stragety.Result;

public class ExactStopsStragety extends BaseStragety {

	private Integer exactStops;

	@SuppressWarnings("unchecked")
	@Override
	public Boolean terminateAt(Edge edge) {
		if (edge == null || (currentEdges.size()) > exactStops) {
			return true;
		}
		if (nodeTo.equals(edge.getNodeTo()) && currentEdges.size() == exactStops) {
			Result result = new Result();
			result.setEdges((Stack<Edge>) currentEdges.clone());
			results.add(result);
		}
		return false;
	}

	public void setExactStops(Integer exactStops) {
		this.exactStops = exactStops;
	}

}
