package com.kacper.zielinski.aisd.lista3;

public class Select
{
	private int selectedValue;
	private Integer[] array;
	int comparison;
	int moves;

	public Select(Integer[] array)
	{
		this.array = array;
		selectedValue = -1;

		printArrayInfo("Input array: ", array);
	}

	public void printArrayInfo(String header, Integer[] array)
	{
		System.err.print(header);

		for(int i=0; i<array.length; i++)
			System.err.print(array[i] + " ");

		System.err.println();
	}

	public Integer select(Integer[] arr, int p, int r, int i)
	{
		System.err.printf("Starting select(%d, %d, %d)\n", p, r, i);

		if(p == r)
		{
			System.err.printf("return arr[p] = (%d)\n", arr[p]);
			selectedValue = arr[p];
			return arr[p];
		}

		System.err.printf("splitArray() of size %d\n", arr.length);
		Integer[] mediansArray = splitArray(arr);

		// median of medians
		int element = select(mediansArray, 0, mediansArray.length - 1, (int)Math.floor(((arr.length/5.0) + 1) / 2.0));

		System.err.println("Chosen median of medians (element): " + element);

		int q = partition(arr, p, r, element);      // pivot index
		System.err.printf("[pivot] = %d on index %d\n", arr[q], q);
		int k = q - p + 1;                          // pivot jest k-tym najmniejszym elementem w arr[]
		System.err.printf("[i ? k] = %d ? %d\n", i, k);

		if(i == k)
		{
			System.err.printf("[i == k] Return median of medians: %d\n", element);
			selectedValue = element;
			return element;
		}
		else if(i < k)
			return select(arr, p, q - 1, i);

		else
			return select(arr, q + 1, r, i - k);
	}

	private Integer[] splitArray(Integer[] arr)
	{
		int lastArrayStartPos = 0;
		int splitArraysNumber = Math.floorDiv(arr.length, 5);       // +1 lastArray
		Integer[] medians;

		if(arr.length % 5 == 0)
			medians = new Integer[splitArraysNumber];
		else
			medians = new Integer[splitArraysNumber+1];

		if(arr.length >=5)
		{
			for(int j=0; j<splitArraysNumber; j++)
			{
				int pos = j*5;
				int posEnd = pos + 5;
				Integer[] splitArray = new Integer[5];

				for(int k=pos; k<posEnd; k++)
				{
					moves++;
					splitArray[posEnd-k-1] = arr[k];
				}

				insertionSort(splitArray);

				System.err.printf("[splitArray %d] = " , j);

				for (int e : splitArray) {
					System.err.printf(e + " ");
				}

				medians[j] = getMedian(splitArray);
				moves++;
				System.err.printf("- Median {%d} = %d\n" , j, medians[j]);

				lastArrayStartPos = posEnd;
			}
		}

		if(arr.length - lastArrayStartPos != 0)
		{
			// Last splitted array
			Integer[] lastSplitArray = new Integer[arr.length - lastArrayStartPos];

			for(int k=lastArrayStartPos; k<arr.length; k++)
			{
				moves++;
				lastSplitArray[arr.length-k-1] = arr[k];
			}

			insertionSort(lastSplitArray);

			System.err.printf("[lastSplitArray %d] = " , splitArraysNumber);

			for (int e : lastSplitArray) {
				System.err.printf(e + " ");
			}

			medians[splitArraysNumber] = getMedian(lastSplitArray);
			moves++;

			System.err.printf("- Median {%d} = %d\n" , splitArraysNumber, medians[splitArraysNumber]);

		}

		return medians;
	}

	/**
	 * Ascending insertion sort algorithm
	 */
	private void insertionSort(Integer[] tab)
	{
		for(int j=1; j < tab.length; j++)
		{
			int key = tab[j];
			int i = j-1;

			comparison++;
			while(i>=0 && tab[i] > key)
			{
				moves++;
				tab[i+1] = tab[i];
				i--;
			}
			moves++;
			tab[i+1] = key;
		}
	}

	private int getMedian(Integer[] arr)
	{
		int middle = arr.length/2;

		if (arr.length % 2 == 1)
			return arr[middle];
		else
			return arr[middle-1];
	}

	private Integer partition(Integer[] arr, int p, int r, int element)
	{
		printArrayInfo("Array before partition: ", arr);
		System.err.println("Partition...");

		for(int j = p ; j <= r; j++)
		{
			comparison++;
			if(arr[j] == element)
			{
				swapInArray(arr, j, r);
				System.err.println("Swapping selected pivot: " + element + " from index " + j + " to index " + r);
				printArrayInfo("Array after partition swap: ", arr);
				break;
			}
		}

		int i = p-1;

		for(int j = p; j < r; j++)
		{
			comparison++;
			if(arr[j] <= element)
			{
				i++;
				swapInArray(arr, i, j);
			}
		}
		swapInArray(arr,i+1, r);

		System.err.print("Pivot array: ");

		for(int j=0; j<arr.length; j++)
		{
			if(arr[j] == element)
			{
				System.err.print("[" + arr[j] + "] ");
				element = -1;
			}

			else
				System.err.print(arr[j] + " ");
		}
		System.err.println();

		return i+1;
	}

//	private Integer partition(Integer[] arr, int p, int r, int element)
//	{
//		printArrayInfo("Array before partition: ", arr);
//		System.err.println("Partition...");
//		int i = p;
//
//		// swap pivot with first element to avoid collision
//		for(int j = p ; j <= r; j++)
//		{
//			comparison++;
//			if(arr[j] == element)
//			{
//				swapInArray(arr, j, p);
//				System.err.println("Selected pivot: " + element + " on index " + j);
//				break;
//			}
//		}
//
//		for(int j = i+1; j <= r; j++)
//		{
//			comparison++;
//			if(arr[j] <= element)
//			{
//				i++;
//				swapInArray(arr, i, j);
//			}
//		}
//		swapInArray(arr, i, p);
//
//		System.err.print("Pivot array: ");
//
//		for(int j=0; j<arr.length; j++)
//		{
//			if(arr[j] == element)
//			{
//				System.err.print("[" + arr[j] + "] ");
//				element = -1;
//			}
//
//			else
//				System.err.print(arr[j] + " ");
//		}
//		System.err.println();
//
//		return i;
//	}

	private void swapInArray(Integer[] arr, int a, int b)
	{
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
		moves+=2;
	}

	public void printArrayWithSelectedNumber()
	{
		System.out.print("Array: ");
		for(int i=0; i<array.length; i++)
		{
			if(array[i] == selectedValue)
			{
				System.out.print("[" + array[i] + "] ");
				selectedValue = -1;
			}
			else
				System.out.print(array[i] + " ");
		}
		System.out.println();
	}
}
