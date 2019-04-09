package com.thoughtworks.trainsserver.model;

public class Edge {

	private Node nodeFrom;
	private Node nodeTo;
	private Integer weight;

	public Edge(Node nodeFrom, Node nodeTo, Integer weight) {
		this.nodeFrom = nodeFrom;
		this.nodeTo = nodeTo;
		this.weight = weight;

	}

	public Node getNodeFrom() {
		return nodeFrom;
	}

	public void setNodeFrom(Node nodeFrom) {
		this.nodeFrom = nodeFrom;
	}

	public Node getNodeTo() {
		return nodeTo;
	}

	public void setNodeTo(Node nodeTo) {
		this.nodeTo = nodeTo;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}
