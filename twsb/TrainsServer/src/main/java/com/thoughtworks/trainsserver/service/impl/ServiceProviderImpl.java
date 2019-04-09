package com.thoughtworks.trainsserver.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.thoughtworks.trainsserver.model.Edge;
import com.thoughtworks.trainsserver.model.Graph;
import com.thoughtworks.trainsserver.model.Node;
import com.thoughtworks.trainsserver.service.ServiceProvider;
import com.thoughtworks.trainsserver.stragety.Result;
import com.thoughtworks.trainsserver.stragety.Stragety;
import com.thoughtworks.trainsserver.stragety.impl.ExactStopsStragety;
import com.thoughtworks.trainsserver.stragety.impl.MaxDistanceStragety;
import com.thoughtworks.trainsserver.stragety.impl.MaxStopsStragety;

public class ServiceProviderImpl implements ServiceProvider {

	private static final String NO_SUCH_ROUTE = "NO SUCH ROUTE";
	private Graph graph = new Graph();
	
	private void execute(Node node, Stragety stragety) {
		// current node
		if (node == null) {
			return;
		}
		Set<Edge> edgesOut = node.getEdgesOut();
		for (Edge edge : edgesOut) {
			// on the way to next node
			stragety.push(edge);
			// key point
			if (!stragety.terminateAt(edge)) {
				// arrived at the next node
				this.execute(edge.getNodeTo(), stragety);
			}
			stragety.pop();
		}
	}
	
	@Override
	public void initGraph(String file) {
		String data = this.readTxtFile(file);
		String[] edges = data.split(",");
		for (String edgeStr : edges) {
			this.validate(edgeStr);

			String nodeFromId = edgeStr.substring(0, 1);
			Node nodeFrom = graph.getNodesMap().get(nodeFromId);
			if (nodeFrom == null) {
				nodeFrom = new Node(nodeFromId);
				graph.getNodesMap().put(nodeFromId, nodeFrom);
			}

			String nodeToId = edgeStr.substring(1, 2);
			Node nodeTo = graph.getNodesMap().get(nodeToId);
			if (nodeTo == null) {
				nodeTo = new Node(nodeToId);
				graph.getNodesMap().put(nodeToId, nodeTo);
			}

			String weight = edgeStr.substring(2, 3);
			Edge edge = new Edge(nodeFrom, nodeTo, Integer.parseInt(weight));
			nodeFrom.getEdgesOut().add(edge);
			nodeTo.getEdgesIn().add(edge);
		}
	}

	@Override
	public String getDistance(String input) {
		List<Node> nodeList = this.convertInput(input);
		Integer distance = 0;
		for (int i = 0; i < (nodeList.size() - 1); i++) {
			List<Result> results = this.getOneStopDistance(nodeList.get(i), nodeList.get(i + 1));
			if (results.isEmpty()) {
				return NO_SUCH_ROUTE;
			} else {
				for (Result result : results) {
					distance += result.getDistance();
				}
			}
		}
		return distance.toString();
	}

	@Override
	public String getNumberWithinMaxStops(String nodeFromId, String nodeToId, Integer maxStops) {
		List<Result> results = this.getDistance(graph.getNodesMap().get(nodeFromId), graph.getNodesMap().get(nodeToId), maxStops);
		return String.valueOf(results.size());
	}

	@Override
	public String getNumberOfExactStops(String nodeFromId, String nodeToId, Integer exactStops) {
		ExactStopsStragety stragety = new ExactStopsStragety();
		stragety.setExactStops(exactStops);
		stragety.setNodeTo(graph.getNodesMap().get(nodeToId));
		this.execute(graph.getNodesMap().get(nodeFromId), stragety);
		return String.valueOf(stragety.getResults().size());
	}

	@Override
	public String getShortestLength(String nodeFromId, String nodeToId) {
		List<Result> results = this.getDistance(graph.getNodesMap().get(nodeFromId), graph.getNodesMap().get(nodeToId), graph.getNodesMap().size());
		if (results.isEmpty()) {
			return NO_SUCH_ROUTE;
		}
		Integer shortest = results.get(0).getDistance();
		for (Result result : results) {
			if (result.getDistance() < shortest) {
				shortest = result.getDistance();
			}
		}
		return shortest.toString();
	}

	@Override
	public String getNumberWithinMaxDistance(String nodeFromId, String nodeToId, Integer maxDistance) {
		MaxDistanceStragety stragety = new MaxDistanceStragety();
		stragety.setMaxDistance(maxDistance);
		stragety.setNodeTo(graph.getNodesMap().get(nodeToId));
		this.execute(graph.getNodesMap().get(nodeFromId), stragety);
		return String.valueOf(stragety.getResults().size());
	}

	private List<Result> getOneStopDistance(Node nodeFrom, Node nodeTo) {
		return this.getDistance(nodeFrom, nodeTo, 1);
	}

	private List<Result> getDistance(Node nodeFrom, Node nodeTo, Integer maxStops) {
		MaxStopsStragety stragety = new MaxStopsStragety();
		stragety.setMaxStops(maxStops);
		stragety.setNodeTo(nodeTo);
		this.execute(nodeFrom, stragety);
		return stragety.getResults();
	}

	private List<Node> convertInput(String input) {
		if (input == null) {
			throw new RuntimeException();
		}
		List<Node> out = new LinkedList<Node>();
		String[] split = input.split("-");
		for (String nodeId : split) {
			out.add(graph.getNodesMap().get(nodeId));
		}
		return out;
	}

	private String readTxtFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				return line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void validate(String edgeStr) {
		if (edgeStr == null || edgeStr.length() != 3) {
			throw new RuntimeException("exception is found when validating " + edgeStr);
		}
	}

}
