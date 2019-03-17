package com.kacper.zielinski.aisd.lista4.structures;

import com.kacper.zielinski.aisd.lista4.DataStructure;
import com.kacper.zielinski.aisd.lista4.StructureException;

public class BST extends DataStructure
{
	public Node<String> getRoot()
	{
		return root;
	}

	public Node<String> find(String key) throws StructureException
	{
		Node<String> actual = root;

		while(actual != null && !actual.key.equals(key))
		{
			actual = (actual.key.compareTo(key) > 0) ? actual.left : actual.right;
			moves++;
			comparisons+=3;
		}

		comparisons++;
		if(actual == null)
			throw new StructureException("0");
//			throw new StructureException("Key's not been found..");

		System.out.println("1");

		return actual;
	}

	public Node<String> justFind(String key) throws StructureException
	{
		Node<String> actual = root;

		while(actual != null && !actual.key.equals(key))
		{
			actual = (actual.key.compareTo(key) > 0) ? actual.left : actual.right;
			moves++;
			comparisons+=3;
		}

		comparisons++;
		if(actual == null)
			return null;

		return actual;
	}

	@Override
	public void delete(String key) throws StructureException
	{
		currentSize--;

		Node<String> removedNode = justFind(key);
		Node<String> x;
		Node<String> y;

		comparisons++;
		if(removedNode == null)
			return;

		comparisons+=2;
		if(removedNode.left == null || removedNode.right == null)
		{
			moves++;
			y = removedNode;
		}
		else
		{
			moves++;
			y = successor(removedNode);
		}

		comparisons++;
		moves++;
		if(y.left != null)
			x = y.left;
		else
			x = y.right;

		comparisons++;
		if(x != null)
		{
			moves++;
			x.parent = y.parent;
		}

		comparisons++;
		moves++;
		if(y.parent == null)
			root = x;
		else if(y == y.parent.left)
			y.parent.left = x;
		else
			y.parent.right = x;

		comparisons++;
		if(y != removedNode)
		{
			moves++;
			removedNode.key = y.key;
		}
	}

	public void successor(String key) throws StructureException
	{
		Node<String> node = find(key);

		comparisons++;
		if(node.right != null)
			min(node.right);

		else if(node != root && node != max(root))
		{
			comparisons+=2;
			Node<String> parent = node.parent;

			while(parent != root && parent.key.compareTo(node.key) < 0)
			{
				comparisons+=2;
				moves++;
				parent = parent.parent;
			}

			System.out.println(parent.key);
		}
		else
		{
			comparisons+=2;
			throw new StructureException("");
		}
	}

	private Node<String> successor(Node<String> node) throws StructureException
	{
		comparisons++;
		if(node.right != null)
			return min(node.right);

		else if(node != root && node != max(root))
		{
			comparisons+=2;
			Node<String> parent = node.parent;

			while(parent != root && parent.key.compareTo(node.key) < 0)
			{
				comparisons+=2;
				moves++;
				parent = parent.parent;
			}

			return parent;
		}
		else
		{
//			throw new StructureException("Successor's not been found..");
			comparisons+=2;
			throw new StructureException("");
		}
	}

	public void min()
	{
		Node<String> node = getRoot();

		comparisons++;
		if(node == null)
		{
			System.out.println();
			return;
		}

		while(node.left != null)
		{
			comparisons++;
			moves++;
			node = node.left;
		}

		System.out.println(node.key);
	}

	private Node<String> min(Node<String> node)
	{
		comparisons++;
		if(node == null)
		{
			System.out.println();
			return null;
		}

		while(node.left != null)
		{
			comparisons++;
			moves++;
			node = node.left;
		}

		System.out.println(node.key);

		return node;
	}

	public void max()
	{
		Node<String> node = getRoot();

		comparisons++;
		if(node == null)
		{
			System.out.println();
			return;
		}

		while(node.right != null)
		{
			moves++;
			comparisons++;
			node = node.right;
		}

		System.out.println(node.key);
	}

	private Node<String> max(Node<String> node)
	{
		comparisons++;
		if(node == null)
		{
			System.out.println();
			return null;
		}

		while(node.right != null)
		{
			moves++;
			comparisons++;
			node = node.right;
		}

		System.out.println(node.key);

		return node;
	}

	public void insert(String key)
	{
		currentSize++;

		if(currentSize > maxElements)
			maxElements = currentSize;

		comparisons++;
		if(root == null)
			root = new Node<String>(key);

		else
		{
			Node<String> actual = root;
			Node<String> parent = null;

			while(actual != null)
			{
				comparisons++;
				moves+=2;
				parent = actual;
				actual = (actual.key.compareTo(key) > 0) ? actual.left : actual.right;
			}

			comparisons++;
			moves++;
			if(parent.key.compareTo(key) > 0)
			{
				parent.left = new Node<String>(key);
				parent.left.parent = parent;
			}
			else
			{
				parent.right = new Node<String>(key);
				parent.right.parent = parent;
			}
		}
	}

	public void inOrder()
	{
		inOrder(getRoot());
	}

	public void inOrder(Node<String> node)
	{
		comparisons++;
		if(node != null)
		{
			inOrder(node.left);
			System.out.print(node.key + ", ");
			inOrder(node.right);
		}
		System.out.println();
	}
}
