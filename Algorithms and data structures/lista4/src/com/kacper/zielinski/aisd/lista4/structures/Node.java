package com.kacper.zielinski.aisd.lista4.structures;

public class Node<T>
{
	public T key;
	public NodeColors color;
	public Node<T> left = null;
	public Node<T> right = null;
	public Node<T> parent = null;

	public Node(T key)
	{
		this.key = key;
	}

	public Node(T key, NodeColors nodeColor)
	{
		this.key = key;
		this.color = nodeColor;
	}
}