package com.kacper.zielinski.aisd.lista5;

import java.util.ArrayList;
import java.util.List;

public class Vertex
{
	private int name;
	private int dist;
	private boolean isVisited;
	private Vertex prev;
	private List<Edge> neighborsList;

	public Vertex(int name)
	{
		this.name = name;
		neighborsList = new ArrayList<>();
	}

	public void addNeighbor(Vertex vertex, Integer weight)
	{
		neighborsList.add(new Edge(this, vertex, weight));
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public List<Edge> getNeighborsList() {
		return neighborsList;
	}

	public void setNeighborsList(List<Edge> neighborsList) {
		this.neighborsList = neighborsList;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean visited) {
		isVisited = visited;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public Vertex getPrev() {
		return prev;
	}

	public void setPrev(Vertex prev) {
		this.prev = prev;
	}
}



//public class Vertex
//{
//	private int name;
//	private boolean isVisited;
//	private Map<Vertex, Integer> neighborsHashMap;
//
//	public Vertex(int name)
//	{
//		this.name = name;
//		neighborsHashMap = new HashMap<>();
//	}
//
//	public void addNeighbor(Vertex vertex, Integer weight)
//	{
//		neighborsHashMap.put(vertex, weight);
//	}
//
//	public int getName() {
//		return name;
//	}
//
//	public void setName(int name) {
//		this.name = name;
//	}
//
//	public Map<Vertex, Integer> getNeighborsHashMap() {
//		return neighborsHashMap;
//	}
//
//	public void setNeighborsHashMap(Map<Vertex, Integer> neighborsHashMap) {
//		this.neighborsHashMap = neighborsHashMap;
//	}
//
//	public boolean isVisited() {
//		return isVisited;
//	}
//
//	public void setVisited(boolean visited) {
//		isVisited = visited;
//	}
//}