package com.kacper.zielinski.lista2.aisd;

public class RadixSort extends SortingAlgorithm<Integer>
{
	private int d;
	private int maximum;

	public RadixSort() throws NullPointerException
	{
		this(null, SortingOrder.DESC);
	}

	public RadixSort(SortingOrder sortingOrder) throws NullPointerException
	{
		this(null, sortingOrder);
	}

	public RadixSort(Integer[] tab, SortingOrder sortingOrder)
	{
		this.tab = tab;
		this.sortingOrder = sortingOrder;
		this.d = 0;
	}

	@Override
	public String getAlgorithmName() {
		return "RadixSort";
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public void computerParamD()
	{
		d = 0;

		int tempNumber = tab[0];

		while(tempNumber !=0)
		{
			tempNumber /= 10;
			this.d++;
		}
	}

	@Override
	void sortAsc() {
		for(int i=0; i<d; i++)
			countingSortAsc(i);
	}

	@Override
	void sortAscInfo() {
		for(int i=0; i<d; i++)
			countingSortAscInfo(i);
	}

	@Override
	void sortDesc() {
		for(int i=0; i<d; i++)
			countingSortDesc(i);
	}

	@Override
	void sortDescInfo() {
		for(int i=0; i<d; i++)
			countingSortDescInfo(i);
	}

	void countingSortAsc(int pos)
{
	Integer[] sortedTab = new Integer[tab.length];
	Integer[] countingTab = new Integer[maximum+1];
	Integer[] unitsTab = new Integer[tab.length];

	for(int i=0; i<tab.length; i++)
	{
		unitsTab[i] = Math.floorMod((tab[i] / (Math.round( (float) Math.pow(10, pos)))), 10);
	}

	for (int i=0; i<= maximum; i++)
		countingTab[i] = 0;

	for (int j=0; j<tab.length; j++)
		++countingTab[unitsTab[j]];

	for (int i=1; i<=maximum; i++)
		countingTab[i] += countingTab[i-1];

	for (int j=tab.length-1; j>=0; j--)
	{
		sortedTab[countingTab[unitsTab[j]]-1] = tab[j];
		--countingTab[unitsTab[j]];
	}
	tab = sortedTab;
}

	void countingSortAscInfo(int pos)
	{
		Integer[] sortedTab = new Integer[tab.length];
		Integer[] countingTab = new Integer[maximum+1];
		Integer[] unitsTab = new Integer[tab.length];

		for(int i=0; i<tab.length; i++)
		{
			unitsTab[i] = Math.floorMod((tab[i] / (Math.round( (float) Math.pow(10, pos)))), 10);
			moves++;
		}

		for (int i=0; i<= maximum; i++)
		{
			countingTab[i] = 0;
			moves++;
		}

		for (int j=0; j<tab.length; j++)
		{
			++countingTab[unitsTab[j]];
			moves++;
		}

		for (int i=1; i<=maximum; i++)
		{
			countingTab[i] += countingTab[i-1];
			moves++;
		}

		for (int j=tab.length-1; j>=0; j--)
		{
			sortedTab[countingTab[unitsTab[j]]-1] = tab[j];
			--countingTab[unitsTab[j]];
			moves+=2;
		}
		tab = sortedTab;
	}

	void countingSortDesc(int pos)
	{
		Integer[] sortedTab = new Integer[tab.length];
		Integer[] countingTab = new Integer[maximum+1];
		Integer[] unitsTab = new Integer[tab.length];

		for(int i=0; i<tab.length; i++)
		{
			unitsTab[i] = Math.floorMod((tab[i] / (Math.round( (float) Math.pow(10, pos)))), 10);
		}

		for (int i=0; i<= maximum; i++)
			countingTab[i] = 0;

		for (int j=0; j<tab.length; j++)
			++countingTab[unitsTab[j]];

		for (int i=1; i<=maximum; i++)
			countingTab[i] += countingTab[i-1];

		for (int j=0; j<=tab.length-1; j++)
		{
			sortedTab[tab.length - countingTab[unitsTab[j]]] = tab[j];
			--countingTab[unitsTab[j]];
		}
		tab = sortedTab;
	}

	void countingSortDescInfo(int pos)
	{
		Integer[] sortedTab = new Integer[tab.length];
		Integer[] countingTab = new Integer[maximum+1];
		Integer[] unitsTab = new Integer[tab.length];

		for(int i=0; i<tab.length; i++)
		{
			unitsTab[i] = Math.floorMod((tab[i] / (Math.round( (float) Math.pow(10, pos)))), 10);
			moves++;
		}

		for (int i=0; i<= maximum; i++)
		{
			countingTab[i] = 0;
			moves++;
		}

		for (int j=0; j<tab.length; j++)
		{
			++countingTab[unitsTab[j]];
			moves++;
		}

		for (int i=1; i<=maximum; i++)
		{
			countingTab[i] += countingTab[i-1];
			moves++;
		}

		for (int j=0; j<=tab.length-1; j++)
		{
			sortedTab[tab.length - countingTab[unitsTab[j]]] = tab[j];
			--countingTab[unitsTab[j]];
			moves+=2;
		}
		tab = sortedTab;
	}
}
