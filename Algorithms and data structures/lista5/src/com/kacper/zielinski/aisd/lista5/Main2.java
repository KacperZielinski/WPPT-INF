package com.kacper.zielinski.aisd.lista5;

import java.util.Scanner;

public class Main2 {
	public static void main(String[] args)
	{
		Graph graph = new Graph();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Podaj liczbę wierzcholkow: ");
		int vertexNumber = scanner.nextInt();
		for(int i=1; i<=vertexNumber; i++)
			graph.addVertex(new Vertex(i));

		System.out.println("Podaj liczbę krawedzi: ");
		int edgeNumber = scanner.nextInt();

		System.out.println("Podaj definicję krawędzi, kolejno u v w");
		System.out.println("u - wierzcholek startowy, v - wierzcholek koncowy, w - waga krawedzi");
		for(int i=0; i<edgeNumber; i++)
		{
			int u = scanner.nextInt();
			Vertex uVertex = graph.getVertexByName(u);

			int v = scanner.nextInt();
			Vertex vVertex = graph.getVertexByName(v);

			int w = scanner.nextInt();
			uVertex.addNeighbor(vVertex, w);
			Edge newEdge = new Edge(uVertex, vVertex, w);
			graph.addEdge(newEdge);
		}

		int startSource = scanner.nextInt();
		Vertex startVertex = graph.getVertexByName(startSource);

		long startTime = System.nanoTime();

		Dijkstra dijkstra = new Dijkstra(graph, startVertex);
		dijkstra.findShortestPath();

		long estimatedTime = System.nanoTime() - startTime;
		double timeInMiliSeconds = ((double) estimatedTime) / 1000000.0;

		System.err.println("Total time: " + timeInMiliSeconds + " miliseconds");
	}
}
