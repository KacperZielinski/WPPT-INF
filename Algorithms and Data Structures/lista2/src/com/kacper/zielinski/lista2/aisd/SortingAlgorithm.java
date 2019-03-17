package com.kacper.zielinski.lista2.aisd;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class SortingAlgorithm <T extends Comparable<T>>
{
	protected int comparisons;
	protected int moves;
	protected boolean isInfo;
	protected T[] tab;
	protected SortingOrder sortingOrder;
	private double timeInSeconds;

	public void sort()
	{
		timeInSeconds = 0.0;
		comparisons = 0;
		moves = 0;

		long startTime = System.nanoTime();

		if(isInfo)
		{

			if(sortingOrder.equals(SortingOrder.ASC))
				sortAscInfo();
			else
				sortDescInfo();

			stopAndShowTime(startTime);

			if(isSortCorrect())
				printFinalTableResult();
			else
				System.out.println("\nSomething goes wrong...");

			System.err.printf("Total comparisons: %d\n", comparisons);
			System.err.printf("Total moves: %d\n", moves);
		}
		else
		{
			if(sortingOrder.equals(SortingOrder.ASC))
				sortAsc();
			else
				sortDesc();

			stopAndShowTime(startTime);
		}
	}

	private void stopAndShowTime(long startTime)
	{
		long estimatedTime = System.nanoTime() - startTime;
		timeInSeconds = ((double) estimatedTime) / 1000000000.0;

		if(isInfo)
			System.err.println("Total time: " + estimatedTime + " ns" + " | " + timeInSeconds + " seconds");
	}

	private boolean isSortCorrect()
	{
		if(sortingOrder.equals(SortingOrder.ASC))
		{
			for(int i=0; i<tab.length-1; i++)
			{
				if(tab[i].compareTo(tab[i+1]) > 0)
					return false;
			}
			return true;
		}

		else
		{
			for(int i=0; i<tab.length-1; i++)
			{
				if(tab[i].compareTo(tab[i+1]) < 0)
					return false;
			}
			return true;
		}
	}

	private void printFinalTableResult()
	{
		int i = tab.length;
		System.out.println("Table size: (" + i + ")");
		System.out.print("Table: [ ");

		for(int j=0; j<i; j++)
			System.out.print(tab[j] + " ");

		System.out.println("]");
	}

	public void setSortingOrder(SortingOrder sortingOrder) {
		this.sortingOrder = sortingOrder;
	}

	public void setTab(T[] tab) {
		this.tab = tab;
	}

	public void setInfo(boolean info) {
		isInfo = info;
	}

	public int getComparisons() {
		return comparisons;
	}

	public int getMoves() {
		return moves;
	}

	public double getTimeInSeconds() {
		return timeInSeconds;
	}

	public String getTimeInSecondsString()
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(6);
		return nf.format(timeInSeconds);
	}

	public abstract String getAlgorithmName();
	abstract void sortAsc();
	abstract void sortAscInfo();
	abstract void sortDesc();
	abstract void sortDescInfo();
}
