package com.kacper.zielinski.lista2.aisd;

public class MergeSort extends SortingAlgorithm<Integer>
{
//	private Integer middle;

	public MergeSort() throws NullPointerException
	{
		this(null, SortingOrder.DESC);
	}

	public MergeSort(SortingOrder sortingOrder) throws NullPointerException
	{
		this(null, sortingOrder);
	}

	public MergeSort(Integer[] tab, SortingOrder sortingOrder)
	{
		this.tab = tab;
		this.sortingOrder = sortingOrder;
	}

	/**
	 * MergeSort recursive function with ascending merge version
	 * @param p first table index
	 * @param r last table index
	 */
	public void sortAsc(int p, int r)
	{
		comparisons++;
		if(p < r)
		{
			int middle = ((Double) (Math.floor((p+r)/2))).intValue();
			sortAsc(p, middle);
			sortAsc(middle+1, r);
			mergeAsc(p, middle ,r);
		}
	}

	/**
	 * MergeSort recursive function with descending merge version
	 * @param p first table index
	 * @param r last table index
	 */
	public void sortDesc(int p, int r)
	{
		comparisons++;
		if(p < r)
		{
			int middle = ((Double) (Math.floor((p+r)/2))).intValue();
			sortDesc(p, middle);
			sortDesc(middle+1, r);
			mergeDesc(p, middle ,r);
		}
	}

	private void mergeAsc(int p, int q, int r)
	{
		int n1 = q - p + 1;
		int n2 = r - q ;
		int i, j;
		Integer[] leftTab = new Integer[n1 + 1];
		Integer[] rightTab = new Integer[n2 + 1];

		for(i = 0; i < n1; i++)
		{
			comparisons++;
			moves++;
			leftTab[i] = tab[p + i];
		}
		comparisons++;

		for(j = 0; j < n2; j++)
		{
			comparisons++;
			moves++;
			rightTab[j] = tab[q + j + 1];
		}
		comparisons++;

		moves+=2;
		leftTab[n1] = Integer.MAX_VALUE;
		rightTab[n2] = Integer.MAX_VALUE;
		i = 0;
		j = 0;

		for(int k = p; k <= r; k++)
		{
			comparisons++;
			moves++;
			if(leftTab[i] <= rightTab[j])
			{
				tab[k] = leftTab[i];
				i++;
			}
			else
			{
				tab[k] = rightTab[j];
				j++;
			}
		}
		comparisons++;
	}

	private void mergeDesc(int p, int q, int r)
	{
		int n1 = q - p + 1;
		int n2 = r - q;
		int i, j;
		Integer[] leftTab = new Integer[n1 + 1];
		Integer[] rightTab = new Integer[n2 + 1];

		for(i = 0; i < n1; i++)
		{
			comparisons++;
			moves++;
			leftTab[i] = tab[p + i];
		}
		comparisons++;

		for(j = 0; j < n2; j++)
		{
			comparisons++;
			moves++;
			rightTab[j] = tab[q + j + 1];
		}
		comparisons++;

		moves+=2;
		leftTab[n1] = Integer.MIN_VALUE;
		rightTab[n2] = Integer.MIN_VALUE;
		i = 0;
		j = 0;

		for(int k = p; k <= r; k++)
		{
			comparisons++;
			moves++;
			if(leftTab[i] >= rightTab[j])
			{
				tab[k] = leftTab[i];
				i++;
			}
			else
			{
				tab[k] = rightTab[j];
				j++;
			}
		}
		comparisons++;
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
	 * MergeSort recursive function with ascending merge and info version
	 * @param p first table index
	 * @param r last table index
	 */
	public void sortAscInfo(int p, int r)
	{
		System.err.printf("[MS] Compared (first index) p = %d < r = %d (last index)\n", p, r);
		comparisons++;

		if(p < r)
		{
			int middle = ((Double) (Math.floor((p+r)/2))).intValue();
			sortAscInfo(p, middle);
			sortAscInfo(middle+1, r);
			mergeAscInfo(p, middle ,r);
		}
	}

	/**
	 * MergeSort recursive function with descending merge and info version
	 * @param p first table index
	 * @param r last table index
	 */
	public void sortDescInfo(int p, int r)
	{
		System.err.printf("[MS] Compared (first index) p = %d < r = %d (last index)\n", p, r);
		comparisons++;

		if(p < r)
		{
			int middle = ((Double) (Math.floor((p+r)/2))).intValue();
			sortDescInfo(p, middle);
			sortDescInfo(middle+1, r);
			mergeDescInfo(p, middle ,r);
		}
	}

	private void mergeAscInfo(int p, int q, int r)
	{
		int n1 = q - p + 1;
		int n2 = r - q ;
		int i, j;
		Integer[] leftTab = new Integer[n1 + 1];
		Integer[] rightTab = new Integer[n2 + 1];

		for(i = 0; i < n1; i++)
		{
			comparisons++;
			System.err.printf("[MS] Compared %d = i < n1 = %d\n", i, n1);
			int val = p + i;
			leftTab[i] = tab[val];
			System.err.printf("[MS] Moved tab[%d] --> leftTab[%d] = %d (tab[%d])\n", val, i, leftTab[i], val);
			moves++;
		}
		System.err.printf("[MS] Compared %d = i < n1 = %d\n", n1, n1);
		comparisons++;

		for(j = 0; j < n2; j++)
		{
			comparisons++;
			System.err.printf("[MS] Compared %d = i < n1 = %d\n", i, n2);
			int val = q + j + 1;
			rightTab[j] = tab[val];
			System.err.printf("[MS] Moved tab[%d] --> rightTab[%d] = %d (tab[%d])\n", val, j, rightTab[j], val);
			moves++;
		}
		System.err.printf("[MS] Compared %d = i < n1 = %d\n", n2, n2);
		comparisons++;

		// guards
		leftTab[n1] = Integer.MAX_VALUE;
		rightTab[n2] = Integer.MAX_VALUE;
		moves+=2;
		System.err.printf("[MS] Moved %d --> leftTab[%d]\n", leftTab[n1], n1);
		System.err.printf("[MS] Moved %d --> rightTab[%d]\n", rightTab[n2], n2);
		i = 0;
		j = 0;

		for(int k = p; k <= r; k++)
		{
			System.err.printf("[MS] Compared leftTab[%d] = %d > rightTab[%d] = %d\n", i, leftTab[i], j, rightTab[j]);
			comparisons++;

			if(leftTab[i] <= rightTab[j])
			{
				tab[k] = leftTab[i];
				i++;
				System.err.printf("[MS] Moved leftTab[%d] --> tab[%d] = %d\n", i, k, tab[k]);
				moves++;
			}
			else
			{
				tab[k] = rightTab[j];
				j++;
				System.err.printf("[MS] Moved rightTab[%d] --> tab[%d] = %d\n", j, k, tab[k]);
				moves++;
			}
		}
		System.err.printf("[MS] Compared %d = k <= r = %d\n", r+1, r);
		comparisons++;
	}

	private void mergeDescInfo(int p, int q, int r)
	{
		int n1 = q - p + 1;
		int n2 = r - q ;
		int i, j;
		Integer[] leftTab = new Integer[n1 + 1];
		Integer[] rightTab = new Integer[n2 + 1];

		for(i = 0; i < n1; i++)
		{
			comparisons++;
			System.err.printf("[MS] Compared %d = i < n1 = %d\n", i, n1);
			int val = p + i;
			leftTab[i] = tab[val];
			System.err.printf("[MS] Moved tab[%d] --> leftTab[%d] = %d (tab[%d])\n", val, i, leftTab[i], val);
			moves++;
		}
		System.err.printf("[MS] Compared %d = i < n1 = %d\n", n1, n1);
		comparisons++;

		for(j = 0; j < n2; j++)
		{
			comparisons++;
			System.err.printf("[MS] Compared %d = i < n1 = %d\n", i, n2);
			int val = q + j + 1;
			rightTab[j] = tab[val];
			System.err.printf("[MS] Moved tab[%d] --> rightTab[%d] = %d (tab[%d])\n", val, j, rightTab[j], val);
			moves++;
		}
		System.err.printf("[MS] Compared %d = i < n1 = %d\n", n2, n2);
		comparisons++;

		// guards
		leftTab[n1] = Integer.MIN_VALUE;
		rightTab[n2] = Integer.MIN_VALUE;
		moves+=2;
		System.err.printf("[MS] Moved %d --> leftTab[%d]\n", leftTab[n1], n1);
		System.err.printf("[MS] Moved %d --> rightTab[%d]\n", rightTab[n2], n2);
		i = 0;
		j = 0;

		for(int k = p; k <= r; k++)
		{
			System.err.printf("[MS] Compared leftTab[%d] = %d > rightTab[%d] = %d\n", i, leftTab[i], j, rightTab[j]);
			comparisons++;

			if(leftTab[i] >= rightTab[j])
			{
				tab[k] = leftTab[i];
				i++;
				System.err.printf("[MS] Moved leftTab[%d] --> tab[%d] = %d\n", i, k, tab[k]);
				moves++;
			}
			else
			{
				tab[k] = rightTab[j];
				j++;
				System.err.printf("[MS] Moved rightTab[%d] --> tab[%d] = %d\n", j, k, tab[k]);
				moves++;
			}
		}
		System.err.printf("[MS] Compared %d = k <= r = %d\n", r+1, r);
		comparisons++;
	}

	@Override
	public String getAlgorithmName() {
		return "MergeSort";
	}
}

