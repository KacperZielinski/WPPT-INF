package com.kacper.zielinski.aisd.lista4.structures;

import com.kacper.zielinski.aisd.lista4.DataStructure;
import com.kacper.zielinski.aisd.lista4.StructureException;

public class RBT extends DataStructure
{
	public void leftRotate(Node<String> x)
	{
		Node<String> y = x.right;

		moves++;
		x.right = y.left;

		comparisons++;
		if(y.left != null)
		{
			moves++;
			y.left.parent = x;
		}

		moves++;
		y.parent = x.parent;

		comparisons++;
		moves++;
		if(x.parent == null)
			root = y;
		else if(x == x.parent.left)
			x.parent.left = y;
		else
			x.parent.right = y;

		moves+=2;
		y.left = x;
		x.parent = y;

	}

	public void rightRotate(Node<String> y)
	{
		Node<String> x = y.left;

		moves++;
		y.left = x.right;

		comparisons++;
		if(x.right != null)
		{
			moves++;
			x.right.parent = y;
		}

		moves++;
		x.parent = y.parent;

		comparisons++;
		moves++;

		if(y.parent == null)
			root = x;
		else if(y == y.parent.left)
			y.parent.left = x;
		else
			y.parent.right = x;

		moves+=2;
		x.right = y;
		y.parent = x;
	}

	@Override
	public void insert(String key)
	{
		currentSize++;

		if(currentSize > maxElements)
			maxElements = currentSize;

		// bst-insert
		Node<String> x;
		Node<String> y;

		comparisons++;
		if(root == null)
		{
			root = new Node<String>(key);

			moves++;
			x = root;
		}

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

				moves++;
				x = parent.left;
			}
			else
			{
				parent.right = new Node<String>(key);
				parent.right.parent = parent;

				moves++;
				x = parent.right;
			}
		}

		// insert-fixup
		x.color = NodeColors.RED;

		while (x != root && x.parent.color == NodeColors.RED)
		{
			comparisons+=3;
			if(x.parent == x.parent.parent.left)
			{
				moves++;
				y = x.parent.parent.right;

				comparisons++;
				if (y.color == NodeColors.RED)
				{
					x.parent.color = NodeColors.BLACK;
					y.color = NodeColors.BLACK;
					x.parent.parent.color = NodeColors.RED;

					moves++;
					x = x.parent.parent;
				}
				else
				{
					comparisons++;
					if(x == x.parent.right)
					{
						moves++;
						x = x.parent;
						leftRotate(x);
					}

					x.parent.color = NodeColors.BLACK;
					x.parent.parent.color = NodeColors.RED;
					rightRotate(x.parent.parent);
				}
			}
			else
			{
				moves++;
				y = x.parent.parent.left;

				comparisons++;
				if (y.color == NodeColors.RED)
				{
					x.parent.color = NodeColors.BLACK;
					y.color = NodeColors.BLACK;
					x.parent.parent.color = NodeColors.RED;

					moves++;
					x = x.parent.parent;
				}
				else
				{
					comparisons++;
					if(x == x.parent.left)
					{
						moves++;
						x = x.parent;
						rightRotate(x);
					}

					x.parent.color = NodeColors.BLACK;
					x.parent.parent.color = NodeColors.RED;
					leftRotate(x.parent.parent);
				}
			}
		}
		root.color = NodeColors.BLACK;
	}

	@Override
	public void delete(String key) throws StructureException
	{
		currentSize--;

		Node<String> removedNode = justFind(key);
		Node<String> x, y;

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

		if(y.color == NodeColors.BLACK)
			deleteFixUp(x);
	}

	public void deleteFixUp(Node<String> x)
	{
		Node<String> w;

		while(x != root && x.color == NodeColors.BLACK)
		{
			comparisons+=3;
			if(x == x.parent.left)
			{
				moves++;
				w = x.parent.right;

				comparisons++;
				if(w.color == NodeColors.RED)
				{
					w.color = NodeColors.BLACK;
					x.parent.color = NodeColors.RED;
					leftRotate(x.parent);

					moves++;
					w = x.parent.right;
				}

				comparisons+=2;
				if(w.left.color == NodeColors.BLACK && w.right.color == NodeColors.BLACK)
				{
					w.color = NodeColors.RED;

					moves++;
					x = x.parent;
				}
				else
				{
					comparisons++;
					if(w.right.color == NodeColors.BLACK)
					{
						w.color = NodeColors.RED;
						rightRotate(w);

						moves++;
						w = x.parent.right;
					}

					w.color = x.parent.color;
					x.parent.color = NodeColors.BLACK;

					comparisons++;
					if(w.right != null)
						w.right.color = NodeColors.BLACK;

					leftRotate(x.parent);

					moves++;
					x = root;
				}
			}
			else
			{
				moves++;
				w = x.parent.left;

				comparisons++;
				if(w.color == NodeColors.RED)
				{
					w.color = NodeColors.BLACK;
					x.parent.color = NodeColors.RED;
					rightRotate(x.parent);

					moves++;
					w = x.parent.left;
				}

				comparisons+=2;
				if(w.left.color == NodeColors.BLACK && w.right.color == NodeColors.BLACK)
				{
					w.color = NodeColors.RED;

					moves++;
					x = x.parent;
				}
				else
				{
					comparisons++;
					if(w.left.color == NodeColors.BLACK)
					{
						w.color = NodeColors.RED;
						leftRotate(w);

						moves++;
						w = x.parent.left;
					}

					w.color = x.parent.color;
					x.parent.color = NodeColors.BLACK;

					comparisons++;
					if(w.left != null)
						w.left.color = NodeColors.BLACK;

					rightRotate(x.parent);

					moves++;
					x = root;
				}
			}
		}
		x.color = NodeColors.BLACK;
	}

	@Override
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
		Node<String> node = root;

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
		Node<String> node = root;

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

	@Override
	public void inOrder()
	{
		inOrder(root);
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
