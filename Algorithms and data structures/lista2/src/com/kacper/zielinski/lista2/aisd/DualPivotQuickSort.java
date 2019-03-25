package com.kacper.zielinski.lista2.aisd;

/**
 * DualPivot QuickSort algorithm
 * p and q are pivots
 */
public class DualPivotQuickSort extends SortingAlgorithm<Integer>
{
	public DualPivotQuickSort() throws NullPointerException
	{
		this(null, SortingOrder.DESC);
	}

	public DualPivotQuickSort(SortingOrder sortingOrder) throws NullPointerException
	{
		this(null, sortingOrder);
	}

	public DualPivotQuickSort(Integer[] tab, SortingOrder sortingOrder)
	{
		this.tab = tab;
		this.sortingOrder = sortingOrder;
	}

	@Override
	void sortAsc() {
		sortAsc(0, tab.length-1);
	}

	public void sortAsc(int left, int right)
	{
		int p, q, i, k, j, d;

		comparisons++;
		if(left >= right)
			return;

		comparisons++;
		if(tab[left] > tab[right])
		{
			p = tab[right];
			q = tab[left];
		}
		else
		{
			p = tab[left];
			q = tab[right];
		}

		i = left + 1;
		k = right - 1;
		j = i;

		// d holds the difference of the number of small and large elements
		d = 0;

		while(j <= k)
		{
			comparisons++;
			if(d >= 0)
			{
				comparisons++;
				if(tab[j] < p)
				{
					swapInTab(i, j);
					i++;
					j++;
					d++;
				}
				else
				{
					comparisons++;
					if(tab[j] < q)
						j++;
					else
					{
						swapInTab(j, k);
						k--;
						d--;
					}
				}
			}
			else
			{
				comparisons++;
				if(tab[k] > q)
				{
					k--;
					d--;
				}
				else
				{
					comparisons++;
					if(tab[k] < p)
					{
						moves+=3;
						int tmp = tab[k];
						tab[k] = tab[j];
						tab[j] = tab[i];
						tab[i] = tmp;
						i++;
						d++;
					}
					else
						swapInTab(j, k);
					j++;
				}
			}
		}
		comparisons++;
		moves+=4;
		tab[left] = tab[i - 1];
		tab[i - 1] = p;
		tab[right] = tab[k + 1];
		tab[k + 1] = q;
		sortAsc(left, i - 2);
		sortAsc(i, k);
		sortAsc(k + 2, right);
	}

	@Override
	void sortDesc() {
		sortDesc(0, tab.length-1);
	}

	public void sortDesc(int left, int right)
	{
		int p, q, i, k, j, d;

		comparisons++;
		if(left >= right)
			return;

		comparisons++;
		if(tab[left] < tab[right])
		{
			p = tab[right];
			q = tab[left];
		}
		else
		{
			p = tab[left];
			q = tab[right];
		}

		i = left + 1;
		k = right - 1;
		j = i;

		// d holds the difference of the number of small and large elements
		d = 0;

		while(j <= k)
		{
			comparisons++;
			if(d >= 0)
			{
				comparisons++;
				if(tab[j] > p)
				{
					swapInTab(i, j);
					i++;
					j++;
					d++;
				}
				else
				{
					comparisons++;
					if(tab[j] > q)
						j++;
					else
					{
						swapInTab(j, k);
						k--;
						d--;
					}
				}
			}
			else
			{
				comparisons++;
				if(tab[k] < q)
				{
					k--;
					d--;
				}
				else
				{
					comparisons++;
					if(tab[k] > p)
					{
						moves+=3;
						int tmp = tab[k];
						tab[k] = tab[j];
						tab[j] = tab[i];
						tab[i] = tmp;
						i++;
						d++;
					}
					else
						swapInTab(j, k);
					j++;
				}
			}
		}
		comparisons++;
		moves+=4;
		tab[left] = tab[i - 1];
		tab[i - 1] = p;
		tab[right] = tab[k + 1];
		tab[k + 1] = q;
		sortDesc(left, i - 2);
		sortDesc(i, k);
		sortDesc(k + 2, right);
	}

	@Override
	void sortAscInfo() {
		sortAscInfo(0, tab.length-1);
	}

	public void sortAscInfo(int left, int right)
	{
		int p, q, i, k, j, d;

		System.err.printf("[DualQS] Compared (first index) p = %d < r = %d (last index)\n", left, right);
		comparisons++;
		if(left >= right)
			return;

		System.err.printf("[DualQS] Compared tab[%d] = %d > tab[%d] = %d (last index)\n", left, tab[left], right, tab[right]);
		comparisons++;

		if(tab[left] > tab[right])
		{
			p = tab[right];
			q = tab[left];
		}
		else
		{
			p = tab[left];
			q = tab[right];
		}

		i = left + 1;
		k = right - 1;
		j = i;

		// d holds the difference of the number of small and large elements
		d = 0;

		while(j <= k)
		{
			System.err.printf("[DualQS] Compared %d = d >= 0\n", d);
			comparisons++;

			if(d >= 0)
			{
				System.err.printf("[DualQS] Compared tab[%d] = %d < %d (p)\n", j, tab[j], p);
				comparisons++;

				if(tab[j] < p)
				{
					swapInTabInfo(i, j);
					i++;
					j++;
					d++;
				}
				else
				{
					System.err.printf("[DualQS] Compared tab[%d] = %d < %d (q)\n", j, tab[j], q);
					comparisons++;

					if(tab[j] < q)
						j++;
					else
					{
						swapInTabInfo(j, k);
						k--;
						d--;
					}
				}
			}
			else
			{
				System.err.printf("[DualQS] Compared tab[%d] = %d > %d (q)\n", k, tab[k], q);
				comparisons++;

				if(tab[k] > q)
				{
					k--;
					d--;
				}
				else
				{
					System.err.printf("[DualQS] Compared tab[%d] = %d < %d (p)\n", k, tab[k], p);
					comparisons++;

					if(tab[k] < p)
					{
						int tmp = tab[k];
						tab[k] = tab[j];
						tab[j] = tab[i];
						tab[i] = tmp;

						moves+=3;
						System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", j, k, tab[j], k);
						System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", i, j, tab[i], j);
						System.err.printf("[DualQS] Moved tmp --> tab[%d] = %d (tab[%d])\n", right, tmp, right);

						i++;
						d++;
					}
					else
						swapInTabInfo(j, k);
					j++;
				}
			}
		}
		System.err.printf("[DualQS] Compared j = %d <= k = %d \n", j, k);
		comparisons++;

		moves+=4;
		System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", i-1, left, tab[i-1], left);
		System.err.printf("[DualQS] Moved p --> tab[%d] = %d (tab[%d])\n", i-1, p, i-1);
		System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", k+1, right, tab[k+1], right);
		System.err.printf("[DualQS] Moved q --> tab[%d] = %d (tab[%d])\n", k+1, q, k+1);

		tab[left] = tab[i - 1];
		tab[i - 1] = p;
		tab[right] = tab[k + 1];
		tab[k + 1] = q;
		sortAscInfo(left, i - 2);
		sortAscInfo(i, k);
		sortAscInfo(k + 2, right);
	}

	@Override
	void sortDescInfo() {
		sortDescInfo(0, tab.length-1);
	}

	public void sortDescInfo(int left, int right)
	{
		int p, q, i, k, j, d;

		System.err.printf("[DualQS] Compared (first index) p = %d < r = %d (last index)\n", left, right);
		comparisons++;
		if(left >= right)
			return;

		System.err.printf("[DualQS] Compared tab[%d] = %d > tab[%d] = %d (last index)\n", left, tab[left], right, tab[right]);
		comparisons++;
		if(tab[left] < tab[right])
		{
			p = tab[right];
			q = tab[left];
		}
		else
		{
			p = tab[left];
			q = tab[right];
		}

		i = left + 1;
		k = right - 1;
		j = i;

		// d holds the difference of the number of small and large elements
		d = 0;

		while(j <= k)
		{
			System.err.printf("[DualQS] Compared %d = d >= 0\n", d);
			comparisons++;

			if(d >= 0)
			{
				System.err.printf("[DualQS] Compared tab[%d] = %d > %d (p)\n", j, tab[j], p);
				comparisons++;

				if(tab[j] > p)
				{
					swapInTabInfo(i, j);
					i++;
					j++;
					d++;
				}
				else
				{
					System.err.printf("[DualQS] Compared tab[%d] = %d > %d (q)\n", j, tab[j], q);
					comparisons++;

					if(tab[j] > q)
						j++;

					else
					{
						swapInTabInfo(j, k);
						k--;
						d--;
					}
				}
			}
			else
			{
				System.err.printf("[DualQS] Compared tab[%d] = %d < %d (q)\n", k, tab[k], q);
				comparisons++;

				if(tab[k] < q)
				{
					k--;
					d--;
				}
				else
				{
					System.err.printf("[DualQS] Compared tab[%d] = %d > %d (p)\n", k, tab[k], p);
					comparisons++;

					if(tab[k] > p)
					{
						int tmp = tab[k];
						tab[k] = tab[j];
						tab[j] = tab[i];
						tab[i] = tmp;

						moves+=3;
						System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", j, k, tab[j], k);
						System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", i, j, tab[i], j);
						System.err.printf("[DualQS] Moved tmp --> tab[%d] = %d (tab[%d])\n", right, tmp, right);

						i++;
						d++;
					}
					else
						swapInTabInfo(j, k);
					j++;
				}
			}
		}
		System.err.printf("[DualQS] Compared j = %d <= k = %d \n", j, k);
		comparisons++;

		moves+=4;
		System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", i-1, left, tab[i-1], left);
		System.err.printf("[DualQS] Moved p --> tab[%d] = %d (tab[%d])\n", i-1, p, i-1);
		System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", k+1, right, tab[k+1], right);
		System.err.printf("[DualQS] Moved q --> tab[%d] = %d (tab[%d])\n", k+1, q, k+1);

		tab[left] = tab[i - 1];
		tab[i - 1] = p;
		tab[right] = tab[k + 1];
		tab[k + 1] = q;
		sortDescInfo(left, i - 2);
		sortDescInfo(i, k);
		sortDescInfo(k + 2, right);
	}

	private void swapInTabInfo(int i, int j)
	{
		moves+=2;
		System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", j, i, tab[j], i);
		System.err.printf("[DualQS] Moved tab[%d] --> tab[%d] = %d (tab[%d])\n", i, j, tab[i], j);
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
		return "DualPivotQuickSort";
	}
}
