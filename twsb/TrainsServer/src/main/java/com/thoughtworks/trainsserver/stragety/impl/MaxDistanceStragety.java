package com.thoughtworks.trainsserver.stragety.impl;

import java.util.Iterator;
import java.util.Stack;

import com.thoughtworks.trainsserver.model.Edge;
import com.thoughtworks.trainsserver.stragety.Result;

public class MaxDistanceStragety extends BaseStragety {

	private Integer maxDistance;

	@SuppressWarnings("unchecked")
	@Override
	public Boolean terminateAt(Edge edge) {
		if (edge == null) {
			return true;
		}
		Integer currentDistance = 0;
		Iterator<Edge> iterator = currentEdges.iterator();
		while (iterator.hasNext()) {
			Edge nextEdge = iterator.next();
			currentDistance += nextEdge.getWeight();
		}
		if (currentDistance >= maxDistance) {
			return true;
		}
		if (nodeTo.equals(edge.getNodeTo())) {
			Result result = new Result();
			result.setEdges((Stack<Edge>) currentEdges.clone());
			results.add(result);
		}
		return false;
	}

	public void setMaxDistance(Integer maxDistance) {
		this.maxDistance = maxDistance;
	}

}
