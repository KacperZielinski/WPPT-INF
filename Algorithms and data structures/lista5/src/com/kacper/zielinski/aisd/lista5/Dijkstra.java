package com.kacper.zielinski.aisd.lista5;

import java.util.HashSet;
import java.util.Set;

public class Dijkstra
{
	private Graph graph;
	private Vertex source;
	private PriorityQueue<Vertex, Integer> priorityQueue;

	public Dijkstra(Graph graph, Vertex source)
	{
		this.graph = graph;
		this.source = source;
		priorityQueue = new PriorityQueue<>();
	}

	public void findShortestPath()
	{
		findShortestPath(source);
	}

	public void findShortestPath(Vertex source)
	{
		for (Vertex v: graph.getVertexList())
		{
			v.setDist(Integer.MAX_VALUE);
			v.setPrev(null);
		}

		source.setDist(0);

		// trzyma wierzcholki u t.ze dystans od s do u (waga sciezki) jest najmniejszy (najkrotsza)
		Set<Vertex> vertexSet = new HashSet<>();

		for (Vertex v: graph.getVertexList()) {
			priorityQueue.insert(v, v.getDist());
		}

		while(!priorityQueue.empty())
		{
			HeapElement<Vertex, Integer> hp = priorityQueue.pop();
			Vertex u = hp.getValue();
			vertexSet.add(u);

			for (Edge edge : u.getNeighborsList())
			{
				Vertex v = edge.getSecond();
				int checkedDistance = u.getDist() + edge.getWeight();

				if(v.getDist() > checkedDistance)
				{
					v.setDist(checkedDistance);
					v.setPrev(u);
				}
			}
		}

		print(vertexSet);
	}

	public void print(Set<Vertex> vertexSet)
	{
		for (Vertex vertex: vertexSet)
		{
			System.out.println("Id celu: vertex" + vertex.getName() + " waga: " + vertex.getDist());
		}
	}
}
