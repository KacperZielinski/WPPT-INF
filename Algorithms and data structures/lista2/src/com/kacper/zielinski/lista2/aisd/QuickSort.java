package com.kacper.zielinski.lista2.aisd;


public class QuickSort extends SortingAlgorithm<Integer>
{
	public QuickSort() throws NullPointerException
	{
		this(null, SortingOrder.DESC);
	}

	public QuickSort(SortingOrder sortingOrder) throws NullPointerException
	{
		this(null, sortingOrder);
	}

	public QuickSort(Integer[] tab, SortingOrder sortingOrder)
	{
		this.tab = tab;
		this.sortingOrder = sortingOrder;
	}

	/**
	 * QuickSort recursive function with ascending partition version
	 * @param p first table index
	 * @param r last table index
	 */
	public void sortAsc(int p, int r)
	{
		comparisons++;
		if(p < r)
		{
			int pivot = partitionAsc(p, r);
			sortAsc(p, pivot-1);
			sortAsc(pivot+1, r);
		}
	}

	/**
	 * QuickSort recursive function with descending partition version
	 * @param p first table index
	 * @param r last table index
	 */
	public void sortDesc(int p, int r)
	{
		comparisons++;
		if(p < r)
		{
			int pivot = partitionDesc(p, r);
			sortDesc(p, pivot-1);
			sortDesc(pivot+1, r);
		}
	}

	private Integer partitionAsc(int p, int r)
	{
		int x = tab[r];
		int i = p-1;

		for(int j = p; j < r; j++)
		{
			comparisons+=2;
			if(tab[j] <= x)
			{
				i++;
				swapInTab(i, j);
			}
		}
		comparisons++;
		swapInTab(i+1, r);
		return i+1;
	}

	private Integer partitionDesc(int p, int r)
	{
		int x = tab[r];
		int i = p-1;

		for(int j = p; j < r; j++)
		{
			comparisons+=2;
			if(tab[j] >= x)
			{
				i++;
				swapInTab(i, j);
			}
		}
		comparisons++;
		swapInTab(i+1, r);
		return i+1;
	}

	@Override
	void sortAsc() {
		sortAsc(0, tab.length-1);
	}

	@Override
	void sortDesc() {
		sortDesc(0, tab.length-1);
	}

	@Override
	public void sortAscInfo() {
		sortAscInfo(0, tab.length-1);
	}

	@Override
	public void sortDescInfo() {
		sortDescInfo(0, tab.length-1);
	}

	/**
	 * QuickSort recursive function with ascending partition and info version
	 * @param p first table index
	 * @param r last table index
	 */
	public void sortAscInfo(int p, int r)
	{
		System.err.printf("[QS] Compared (first index) p = %d < r = %d (last index)\n", p, r);
		comparisons++;
		if(p < r)
		{
			int pivot = partitionAscInfo(p, r);
			sortAscInfo(p, pivot-1);
			sortAscInfo(pivot+1, r);
		}
	}

	/**
	 * QuickSort recursive function with descending partition and info version
	 * @param p first table index
	 * @param r last table index
	 */
	public void sortDescInfo(int p, int r)
	{
		System.err.printf("[QS] Compared (first index) p = %d < r = %d (last index)\n", p, r);
		comparisons++;
		if(p < r)
		{
			int pivot = partitionDescInfo(p, r);
			sortDescInfo(p, pivot-1);
			sortDescInfo(pivot+1, r);
		}
	}

	private Integer partitionAscInfo(int p, int r)
	{
		int x = tab[r];
		int i = p-1;

		for(int j = p; j < r; j++)
		{
			System.err.printf("[QS] Compared (%d) j < r (%d)\n", j, r);
			System.err.printf("[QS] Compared tab[%d] = %d > x = %d (last index)\n", p, tab[p], x);
			comparisons+=2;

			if(tab[j] <= x)
			{
				i++;
				swapInTabInfo(i, j);
			}
		}
		System.err.printf("[QS] Compared (%d) j < r (%d)\n", r, r);
		comparisons++;
		swapInTabInfo(i+1, r);
		return i+1;
	}

	private Integer partitionDescInfo(int p, int r)
	{
		int x = tab[r];
		int i = p-1;

		for(int j = p; j < r; j++)
		{
			System.err.printf("[QS] Compared (%d) j < r (%d)\n", j, r);
			System.err.printf("[QS] Compared tab[%d] = %d > x = %d (last index)\n", p, tab[p], x);
			comparisons+=2;

			if(tab[j] >= x)
			{
				i++;
				swapInTabInfo(i, j);
			}
		}
		System.err.printf("[QS] Compared (%d) j < r (%d)\n", r, r);
		comparisons++;
		swapInTabInfo(i+1, r);
		return i+1;
	}

	private void swapInTabInfo(int i, int j)
	{
		moves+=2;
		System.err.printf("[QS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", j, i, tab[j], i);
		System.err.printf("[QS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", i, j, tab[i], j);
		Integer temp = tab[i];
		tab[i] = tab[j];
		tab[j] = temp;
	}

	private void swapInTab(int i, int j)
	{
		moves+=2;
		Integer temp = tab[i];
		tab[i] = tab[j];
		tab[j] = temp;
	}

	@Override
	public String getAlgorithmName() {
		return "QuickSort";
	}
}
