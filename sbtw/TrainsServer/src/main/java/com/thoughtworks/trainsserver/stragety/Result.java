package com.thoughtworks.trainsserver.stragety;

import java.util.Iterator;
import java.util.Stack;

import com.thoughtworks.trainsserver.model.Edge;

public class Result {

	private Stack<Edge> edges = new Stack<Edge>();

	public Integer getDistance() {
		Integer distance = 0;
		Iterator<Edge> iterator = edges.iterator();
		while (iterator.hasNext()) {
			Edge edge = iterator.next();
			distance += edge.getWeight();
		}
		return distance;
	}

	public Stack<Edge> getEdges() {
		return edges;
	}

	public void setEdges(Stack<Edge> edges) {
		this.edges = edges;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Iterator<Edge> iterator = edges.iterator();
		while (iterator.hasNext()) {
			Edge edge = iterator.next();
			sb.append(edge.getNodeFrom().getId());
			sb.append(edge.getNodeTo().getId());
			sb.append(edge.getWeight());
			if (iterator.hasNext()) {
				sb.append(",");
			}
		}
		return sb.toString(); 
	}
}
