package com.kacper.zielinski.aisd.lista5;

import java.util.Scanner;

public class Main3
{
	public static void main(String[] args)
	{
		Graph graph = new Graph();
		SpanningTree spanningTree = null;

		if(args.length == 0)
			spanningTree = new Kruskal(graph);

		else
		{
			if ((args[0].substring(0, 2)).equals("-p"))
				spanningTree = new Prim(graph);

			else if ((args[0].substring(0, 2)).equals("-k"))
				spanningTree = new Kruskal(graph);

			else
			{
				System.out.println("Bad parameters.. Sorry..");
				System.exit(-1);
			}
		}


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

		long startTime = System.nanoTime();

		spanningTree.generateTree();

		long estimatedTime = System.nanoTime() - startTime;
		double timeInMiliSeconds = ((double) estimatedTime) / 1000000.0;

		System.err.println("Total time: " + timeInMiliSeconds + " miliseconds");
	}
}
