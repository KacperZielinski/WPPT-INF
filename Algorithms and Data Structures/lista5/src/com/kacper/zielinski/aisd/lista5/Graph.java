package com.kacper.zielinski.aisd.lista5;

import java.util.ArrayList;
import java.util.List;

public class Graph
{
	private List<Edge> edgeList = new ArrayList<>();
	private List<Vertex> vertexList = new ArrayList<>();

	public void addVertex(Vertex vertex)
	{
		vertexList.add(vertex);
	}

	public Vertex getVertexByIndex(int index)
	{
		return vertexList.get(index);
	}

	public Vertex getVertexByName(int name)
	{
		for (Vertex vertex : vertexList)
		{
			if (vertex.getName() == name)
				return vertex;
		}
		System.err.println("Cannot find vertex! " + this.getClass().getSimpleName());
		return null;
	}

	public List<Vertex> getVertexList() {
		return vertexList;
	}

	public void addEdge(Edge edge)
	{
		edgeList.add(edge);
	}

	public Edge getEdge(int index)
	{
		return edgeList.get(index);
	}

	public List<Edge> getEdgeList() {
		return edgeList;
	}
}
