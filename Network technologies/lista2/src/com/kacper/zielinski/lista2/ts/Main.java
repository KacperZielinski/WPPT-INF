package com.kacper.zielinski.lista2.ts;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Main
{
	private static int ATTEMPTS = 1000;
	private static double CAPACITY = 30000.0;
	private static double REAL_FLOW = 10.0;

    public static void main(String[] args)
    {
	    Graph graph = new Graph();
	    double suma = 0.0;

	    Vertex firstVertex = new Vertex("v(1)");
	    graph.addVertex(firstVertex);
	    firstVertex.visited();

	    for(int i=2; i<=20; i++)
	    {
		    Vertex vertex = new Vertex("v(" + i + ")");

		    graph.addVertex(vertex);

		    Edge edge = new Edge(graph.getVertex(i-2), graph.getVertex(i-1), 0.95);
		    graph.addEdge(edge);
	    }
//
//	    // 1b
//	    graph.addEdge(new Edge(graph.getVertex(0), graph.getVertex(19), 0.95));
//
//	    // 1c
//	    graph.addEdge(new Edge(graph.getVertex(0), graph.getVertex(9), 0.8));
//	    graph.addEdge(new Edge(graph.getVertex(4), graph.getVertex(14), 0.7));
//
//	    // 1d
//	    Random secureRandom = null;
//	    try
//	    {
//		    secureRandom = SecureRandom.getInstanceStrong();
//	    }
//	    catch (NoSuchAlgorithmException e)
//	    {
//		    e.printStackTrace();
//	    }
//	    graph.addEdge(new Edge(graph.getVertex(Math.floorMod(secureRandom.nextInt(), 19)), graph.getVertex(Math.floorMod(secureRandom.nextInt(), 19)), 0.4));
//	    graph.addEdge(new Edge(graph.getVertex(Math.floorMod(secureRandom.nextInt(), 19)), graph.getVertex(Math.floorMod(secureRandom.nextInt(), 19)), 0.4));
//	    graph.addEdge(new Edge(graph.getVertex(Math.floorMod(secureRandom.nextInt(), 19)), graph.getVertex(Math.floorMod(secureRandom.nextInt(), 19)), 0.4));
//	    graph.addEdge(new Edge(graph.getVertex(Math.floorMod(secureRandom.nextInt(), 19)), graph.getVertex(Math.floorMod(secureRandom.nextInt(), 19)), 0.4));

	    int success = 0;

	    for(int tests = 0; tests < ATTEMPTS; tests++)
	    {
		    double probability;
		    SecureRandom random = null;
		    List<Edge> tmpList = graph.getEdgeList();
		    List<Edge> valuesToRemove = new ArrayList<>();

		    for(Edge tmp: graph.getEdgeList())
		    {
			    try
			    {
				    random = SecureRandom.getInstanceStrong();
			    }
			    catch (NoSuchAlgorithmException e)
			    {
				    e.printStackTrace();
			    }

			    probability = random.nextDouble();

//			    System.out.print(probability + ";");

			    // branch is not available, so remove it
			    if(tmp.getReliabilityFunction() < probability)
			    {
				    valuesToRemove.add(tmp);
			    }

			    probability = Math.round(probability*100.0)/100.0;
		    }

		    graph.getEdgeList().removeAll(valuesToRemove);



		    if(graph.isConsistent())
		    {
//			    success++;  // odhacz do zadania nr 1

//////////////////////////////////////////////////////////////////// 2 zadanie
			    double p;
			    double t_max;
			    double t = 0.0;

			    if(args.length > 1)
			    {
				    try
				    {
					    p = Double.parseDouble(args[0]);
					    t_max = Double.parseDouble(args[1]);
				    }
				    catch (NumberFormatException e)
				    {
					    p = 0.95;
					    t_max = 0.3;
				    }
			    }
			    else
			    {
				    p = 0.95;
				    t_max = 0.3;
			    }

			    Graph secondGraph = new Graph();
			    Network network = new Network(secondGraph);

			    for(int i=1; i<=10; i++)
			    {
				    Vertex vertex = new Vertex("v(" + i + ")");
				    secondGraph.addVertex(vertex);
			    }
			    // my graph
			    List<Edge> tmpEdgeList = new ArrayList<>();
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(0), secondGraph.getVertex(1), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(0), secondGraph.getVertex(2), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(0), secondGraph.getVertex(9), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(1), secondGraph.getVertex(8), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(1), secondGraph.getVertex(2), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(1), secondGraph.getVertex(3), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(2), secondGraph.getVertex(7), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(2), secondGraph.getVertex(3), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(3), secondGraph.getVertex(6), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(3), secondGraph.getVertex(4), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(5), secondGraph.getVertex(3), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(6), secondGraph.getVertex(4), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(5), secondGraph.getVertex(4), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(6), secondGraph.getVertex(5), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(7), secondGraph.getVertex(6), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(8), secondGraph.getVertex(7), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(8), secondGraph.getVertex(6), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(9), secondGraph.getVertex(7), p));
			    tmpEdgeList.add(new Edge(secondGraph.getVertex(9), secondGraph.getVertex(8), p));

			    for (Edge e: tmpEdgeList) {
			    	e.setCapacity(CAPACITY);
			    	e.setRealPackageFlow(REAL_FLOW);
				    secondGraph.addEdge(e);
			    }
			    network.createMatrix();
			    t = network.getAveragePacketDelay();
			    suma += t;

			    if(t < t_max && t > 0.0)
				    success++;
////////////////////////////////////////////////////////////////////

		    }
		    tmpList.addAll(valuesToRemove);
	    }

	    System.out.println("Sukcesów: " + success);
	    System.out.println("Porażek: " + (ATTEMPTS-success));
        System.out.println("Średnie opóźnienie: " + (Math.round((suma/ATTEMPTS)*1000.0)/1000.0) + " ms");
        System.out.println("Przepustowość każdej krawędzi: " + CAPACITY + " b/s");
        System.out.println("Rzeczywisty przepływ pakietu: " + REAL_FLOW + " pakietów / s");
	    System.out.println("Niezawodność sieci: " + success * 100.0/ATTEMPTS + "%");
    }
}
