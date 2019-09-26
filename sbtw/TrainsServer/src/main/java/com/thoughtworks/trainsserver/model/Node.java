package com.thoughtworks.trainsserver.model;

import java.util.HashSet;
import java.util.Set;

public class Node {

	private String id;
	private Set<Edge> edgesIn = new HashSet<Edge>();
	private Set<Edge> edgesOut = new HashSet<Edge>();

	public Node(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Edge> getEdgesIn() {
		return edgesIn;
	}

	public void setEdgesIn(Set<Edge> edgesIn) {
		this.edgesIn = edgesIn;
	}

	public Set<Edge> getEdgesOut() {
		return edgesOut;
	}

	public void setEdgesOut(Set<Edge> edgesOut) {
		this.edgesOut = edgesOut;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (!(other instanceof Node)) {
			return false;
		}
		final Node node = (Node) other;
		return this.getId().equals(node.getId());
	}

}
