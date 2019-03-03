package com.kacper.zielinski.aisd.lista5;

public class Prim implements SpanningTree
{
	private Graph graph;
	private PriorityQueue<Vertex, Integer> priorityQueue;

	public Prim(Graph graph)
	{
		this.graph = graph;
		priorityQueue = new PriorityQueue<>();
	}

	@Override
	public void generateTree()
	{
		Vertex r = graph.getVertexList().get(0);

		for (Vertex vertex: graph.getVertexList())
		{
			vertex.setDist(Integer.MAX_VALUE);
			vertex.setPrev(null);
		}

		r.setDist(0);

		for (Vertex v: graph.getVertexList()) {
			priorityQueue.insert(v, v.getDist());
		}

		while(!priorityQueue.empty())
		{
			HeapElement<Vertex, Integer> hp = priorityQueue.pop();
			Vertex u =  hp.getValue();

			for (Edge edge : u.getNeighborsList())
			{
				Vertex neighbor = edge.getSecond();
				if(!neighbor.isVisited() && (edge.getWeight() < neighbor.getDist()))
				{
					neighbor.setPrev(u);
					neighbor.setDist(neighbor.getDist());
				}
			}

			u.setVisited(true);
		}
	}
}
