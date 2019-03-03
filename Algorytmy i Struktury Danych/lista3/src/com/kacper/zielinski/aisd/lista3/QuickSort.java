package com.kacper.zielinski.aisd.lista3;


public class QuickSort
{
	Integer[] tab;

	public QuickSort(Integer[] tab)
	{
		this.tab = tab;
	}

	/**
	 * QuickSort recursive function with ascending partition version
	 * @param p first table index
	 * @param r last table index
	 */
	public void sort(int p, int r)
	{
		if(p < r)
		{
			int pivot = partition(p, r);
			sort(p, pivot-1);
			sort(pivot+1, r);
		}
	}

	private Integer partition(int p, int r)
	{
		int x = tab[r];
		int i = p-1;

		for(int j = p; j < r; j++)
		{
			if(tab[j] <= x)
			{
				i++;
				swapInTab(i, j);
			}
		}
		swapInTab(i+1, r);
		return i+1;
	}

	private void swapInTab(int i, int j)
	{
		Integer temp = tab[i];
		tab[i] = tab[j];
		tab[j] = temp;
	}
}
