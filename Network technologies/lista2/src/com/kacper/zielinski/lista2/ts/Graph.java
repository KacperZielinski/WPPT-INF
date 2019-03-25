package com.kacper.zielinski.lista2.ts;

import java.util.ArrayList;
import java.util.List;

public class Graph
{
	private List<Edge> edgeList = new ArrayList<>();
	private List<Vertex> vertexList = new ArrayList<>();

	public boolean isConsistent()
	{
		List<Vertex> checked = new ArrayList<>();
		boolean consistency = true;
		Vertex temp;
		Vertex first;
		Vertex second;

		if(vertexList.size() <= 1)
			return true;

		else
		{
			temp = vertexList.get(0);
			temp.visited();
			checked.add(temp);
		}

		while(checked.size() != 0)
		{
			temp = checked.get(0);

			for(Edge e : edgeList)
			{
				first = e.getFirstVertex();
				second = e.getSecondVertex();

				if(temp == first)
				{
					if(!second.isVisited())
					{
						second.visited();
						checked.add(second);
					}
				}
				else if(temp == second)
				{
					if(!first.isVisited())
					{
						first.visited();
						checked.add(first);
					}
				}
			}
			checked.remove(temp);
		}

		for(Vertex v : vertexList)
		{
			if(!v.isVisited())
				consistency = false;

			v.notVisited();
		}

		return consistency;
	}

	public void addVertex(Vertex vertex)
	{
		vertexList.add(vertex);
	}

	public Vertex getVertex(int index)
	{
		return vertexList.get(index);
	}

	public Vertex getVertex(String name)
	{
		for (Vertex tmp : vertexList)
		{
			if (tmp.getName().equals(name))
				return tmp;
		}
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
