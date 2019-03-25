package com.kacper.zielinski.lista2.aisd;

public class InsertionSort extends SortingAlgorithm<Integer>
{
	public InsertionSort() throws NullPointerException
	{
		this(null, SortingOrder.DESC);
	}

	public InsertionSort(SortingOrder sortingOrder) throws NullPointerException
	{
		this(null, sortingOrder);
	}

	public InsertionSort(Integer[] tab, SortingOrder sortingOrder)
	{
		this.tab = tab;
		this.sortingOrder = sortingOrder;
	}

	@Override
	public void sortAsc()
	{
		for(int j=1; j < tab.length; j++)
		{
			int key = tab[j];
			int i = j-1;

			while(i>=0 && tab[i] > key)
			{
				comparisons+=2;
				moves++;
				tab[i+1] = tab[i];
				i--;
			}
			comparisons++;
			moves++;
			tab[i+1] = key;
		}
		comparisons++;
	}

	@Override
	public void sortDesc()
	{

		for(int j=1; j < tab.length; j++)
		{
			int key = tab[j];
			int i = j-1;

			comparisons++;
			while(i>=0 && tab[i] < key)
			{
				comparisons+=2;
				moves++;
				tab[i+1] = tab[i];
				i--;
			}
			comparisons++;
			moves++;
			tab[i+1] = key;
		}
		comparisons++;
	}

	@Override
	public void sortAscInfo()
	{
		for(int j=1; j < tab.length; j++)
		{
			int key = tab[j];
			int i = j-1;

			while(i>=0)
			{
				System.err.printf("[IS] Compared tab[%d] = %d > %d = key\n", i, tab[i], key);
				comparisons+=2;

				if(tab[i] > key)
				{
					tab[i+1] = tab[i];
					System.err.printf("[IS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", i, i+1, tab[i], i);
					moves++;
				}

				else
					break;

				i--;
			}
			System.err.printf("[IS] Compared i (%d) >= 0\n", i);
			comparisons++;

			moves++;
			System.err.printf("[IS] Moved key --> tab[%d] = %d \n", i+1, key);
			tab[i+1] = key;
		}
		System.err.printf("[IS] Compared (%d) j < tab.length (%d)\n", tab.length, tab.length);
		comparisons++;
	}

	@Override
	public void sortDescInfo()
	{
		for(int j=1; j < tab.length; j++)
		{
			int key = tab[j];
			int i = j-1;


			while(i>=0)
			{
				System.err.printf("[IS] Compared tab[%d] = %d < %d = key\n", i, tab[i], key);
				comparisons+=2;

				if(tab[i] < key)
				{
					tab[i+1] = tab[i];
					System.err.printf("[IS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", i, i+1, tab[i], i);
					moves++;
				}

				else {
					break;
				}

				i--;
			}
			System.err.printf("[IS] Compared i (%d) >= 0\n", i);
			comparisons++;

			moves++;
			System.err.printf("[IS] Moved key --> tab[%d] = %d \n", i+1, key);
			tab[i+1] = key;
		}
		System.err.printf("[IS] Compared (%d) j < tab.length (%d)\n", tab.length, tab.length);
		comparisons++;
	}

	@Override
	public String getAlgorithmName() {
		return "InsertionSort";
	}
}
