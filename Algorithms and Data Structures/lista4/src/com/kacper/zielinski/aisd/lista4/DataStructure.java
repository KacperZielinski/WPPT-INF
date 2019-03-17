package com.kacper.zielinski.aisd.lista4;

import com.kacper.zielinski.aisd.lista4.structures.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class DataStructure
{
	protected int comparisons = 0;
	protected int moves = 0;
	protected int maxElements = 0;
	protected int currentSize = 0;
	protected Node<String> root = null;

	public abstract void insert(String key);
	public abstract void delete(String key) throws StructureException;
	public abstract Node<String> find(String key) throws StructureException;
	public abstract void min();
	public abstract void max();
	public abstract void successor(String key) throws StructureException;
	public abstract void inOrder();

	public void load(String filename)
	{
		String content = "";

		try
		{
			content = new String(Files.readAllBytes(Paths.get(filename)));
		}
		catch (IOException e)
		{
			System.out.println("File not exists");
		}

		String[] wordTab = content.split("\\s+");

		for (String word: wordTab)
		{
			String newKey = word.replaceAll("[^A-Za-z]", "");
			insert(newKey);
		}

	}

	public void draw(Node<String> node)
	{
		if(node != null)
		{
			draw(node.right);

			for(int i=0; i<=getLevel(node); i++)
				System.out.print("|       ");

			System.out.println(node.key);

			draw(node.left);
		}
	}

	public void draw()
	{
		Node<String> node = root;
		draw(node);
	}

	private int getLevel(Node<String> node)
	{
		if (node == root || node == null)
			return 0;
		else
			return 1 + getLevel(node.parent);
	}

	public int getComparisons() {
		return comparisons;
	}

	public int getMoves() {
		return moves;
	}
}
