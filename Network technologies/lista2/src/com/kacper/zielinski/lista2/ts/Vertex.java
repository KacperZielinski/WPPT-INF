package com.kacper.zielinski.lista2.ts;

public class Vertex
{
	private String name;
	private boolean isVisited;

	public Vertex(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void visited() {
		isVisited = true;
	}

	public void notVisited() {
		isVisited = false;
	}

	public void setVisited(boolean visited) {
		isVisited = visited;
	}
}
