package com.kacper.zielinski.aisd.lista4;

public class DataStructureFactory
{
	private DataStructure dataStructure;

	public DataStructureFactory(DataStructure dataStructure)
	{
		this.dataStructure = dataStructure;
	}

	public void execute(String[] trimTab)
	{
		try
		{
			switch (trimTab[0])
			{
				case "insert":
				{
					String newKey = trimTab[1].replaceAll("[^A-Za-z]", "");
					dataStructure.insert(newKey);
					break;
				}
				case "load":
				{
					dataStructure.load(trimTab[1]);
					break;
				}
				case "delete":
				{
					dataStructure.delete(trimTab[1]);
					break;
				}
				case "find":
				{
					dataStructure.find(trimTab[1]);
					break;
				}
				case "min":
				{
					dataStructure.min();
					break;
				}
				case "max":
				{
					dataStructure.max();
					break;
				}
				case "successor":
				{
					dataStructure.successor(trimTab[1]);
					break;
				}
				case "inorder":
				{
					dataStructure.inOrder();
					break;
				}
			}
		}
		catch (StructureException e)
		{

		}
	}
}
