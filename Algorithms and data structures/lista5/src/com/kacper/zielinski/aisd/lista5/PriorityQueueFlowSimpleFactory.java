package com.kacper.zielinski.aisd.lista5;

public class PriorityQueueFlowSimpleFactory
{
	private PriorityQueue<Integer, Integer> priorityQueue;

	public PriorityQueueFlowSimpleFactory(PriorityQueue priorityQueue)
	{
		this.priorityQueue = priorityQueue;
	}

	public void execute(String[] trimTab)
	{
		if(trimTab.length != 0)
		{
			switch (trimTab[0])
			{
				case "insert":
				{
					try
					{
						if(trimTab.length == 3)
						{
							int priority = Integer.parseInt(trimTab[2]);

							if(priority < 0)
								throw new NumberFormatException();
							else
								priorityQueue.insert(Integer.parseInt(trimTab[1]), priority);
						}
					}
					catch(NumberFormatException e)
					{
						System.err.println("Error while parsing String to Integer in" + this.getClass().getSimpleName());
					}
					break;
				}
				case "empty":
				{
					priorityQueue.emptyWithInfo();
					break;
				}
				case "top":
				{
					priorityQueue.top();
					break;
				}
				case "pop":
				{
					priorityQueue.pop();
					break;
				}
				case "priority":
				{
					try
					{
						if(trimTab.length == 3)
						{
							int priority = Integer.parseInt(trimTab[2]);

							if (priority < 0)
								throw new NumberFormatException();
							else
								priorityQueue.priority(Integer.parseInt(trimTab[1]), priority);
						}
					}
					catch(NumberFormatException e)
					{
						System.err.println("Error while parsing String to Integer in" + this.getClass().getSimpleName());
					}
					break;
				}
				case "print":
				{
					priorityQueue.print();
					break;
				}
			}
		}
	}
}
