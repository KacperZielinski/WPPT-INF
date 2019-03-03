package com.kacper.zielinski.aisd.lista5;

public class Edge
{
	private Vertex first;
	private Vertex second;
	private int weight;

	public Edge(Vertex first, Vertex second, int weight)
	{
		this.first = first;
		this.second = second;
		this.weight = weight;
	}

	public Vertex getFirst() {
		return first;
	}

	public void setFirst(Vertex first) {
		this.first = first;
	}

	public Vertex getSecond() {
		return second;
	}

	public void setSecond(Vertex second) {
		this.second = second;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Edge getEdge()
	{
		return this;
	}
}
