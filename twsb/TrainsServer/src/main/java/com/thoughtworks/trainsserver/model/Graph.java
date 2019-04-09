package com.thoughtworks.trainsserver.model;

import java.util.HashMap;
import java.util.Map;

public class Graph {

	private Map<String, Node> nodesMap = new HashMap<String, Node>();

	public Graph() {
	}

	public Map<String, Node> getNodesMap() {
		return nodesMap;
	}

	public void setNodesMap(Map<String, Node> nodesMap) {
		this.nodesMap = nodesMap;
	}

}
