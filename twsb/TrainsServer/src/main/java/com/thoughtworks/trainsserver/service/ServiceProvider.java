package com.thoughtworks.trainsserver.service;

public interface ServiceProvider {

	public void initGraph(String file);
	
	public String getDistance(String input);

	public String getNumberWithinMaxStops(String nodeFromId, String nodeToId, Integer maxStops);

	public String getNumberOfExactStops(String nodeFromId, String nodeToId, Integer exactStops);

	public String getShortestLength(String nodeFromId, String nodeToId);
	
	public String getNumberWithinMaxDistance(String nodeFromId, String nodeToId, Integer maxDistance);
}
