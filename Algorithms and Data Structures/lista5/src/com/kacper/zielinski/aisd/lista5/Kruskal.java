package com.kacper.zielinski.aisd.lista5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Kruskal implements SpanningTree
{
	private Graph graph;
	private List<Edge> edgeList;

	public Kruskal(Graph graph) {
		this.graph = graph;
	}

	@Override
	public void generateTree()
	{
		for (Vertex vertex: graph.getVertexList())
			makeSet(vertex);

		edgeList = new ArrayList<>();
		Collections.sort(graph.getEdgeList(), Comparator.comparing(Edge::getWeight));

		for (Edge edge: graph.getEdgeList())
		{
			Vertex u = edge.getFirst();
			Vertex v = edge.getSecond();

			if(find(u) != find(v))
			{
				edgeList.add(edge);
				union(u, v);
			}
		}
	}

	public void print()
	{
		int sum = 0;

		for (Edge edge: edgeList)
		{
			System.out.println("u: " + edge.getFirst().getName() +
					" v: " + edge.getSecond().getName() + " w: " +
					edge.getWeight()
			);
			sum += edge.getWeight();
		}

		System.out.println("Laczna waga drzewa rozpinajacego: " + sum);
	}

	private void makeSet(Vertex vertex)
	{
		vertex.setPrev(vertex);
		vertex.setDist(0);
	}

	private Vertex find(Vertex vertex)
	{

		while(vertex != vertex.getPrev())
			vertex.setPrev(vertex.getPrev());

		return vertex;
	}

	private void union(Vertex x, Vertex y)
	{
		Vertex rootX = find(x);
		Vertex rootY = find(y);

		if (rootX == rootY)
			return;

		if (rootX.getDist() > rootY.getDist())
			rootY.setPrev(rootX);

		else
		{
			rootX.setPrev(rootY);

			if (rootX.getDist() == rootY.getDist())
				rootY.setDist(rootY.getDist() + 1);
		}

	}
}
