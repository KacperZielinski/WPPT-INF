package com.kacper.zielinski.aisd.lista5;

import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Podaj liczbę operacji: ");
		int numberOfOperations = scanner.nextInt();

		String line = "";

		if(scanner.hasNextLine())
			line = scanner.nextLine();

		long startTime = System.nanoTime();

		PriorityQueue<Integer, Integer> priorityQueue = new PriorityQueue<>();

		for(int i=0 ; i<numberOfOperations; i++)
		{
			if(scanner.hasNextLine())
			{
				line = scanner.nextLine();
			}

			String[] trimTab = line.split("\\s+");

			PriorityQueueFlowSimpleFactory priorityQueueFlowSimpleFactory = new PriorityQueueFlowSimpleFactory(priorityQueue);
			priorityQueueFlowSimpleFactory.execute(trimTab);
		}

		long estimatedTime = System.nanoTime() - startTime;
		double timeInMiliSeconds = ((double) estimatedTime) / 1000000.0;

		System.err.println("Total time: " + timeInMiliSeconds + " miliseconds");
	}
}
