package com.kacper.zielinski.aisd.lista3;

import java.security.SecureRandom;
import java.util.Scanner;

public class BinarySearch
{
	private Integer[] array;
	int comparison;

	BinarySearch(Integer[] array)
	{
		this.array = array;
	}

	/**
	 * @return 1 if value has been found, 0 otherwise
	 */
	public int search(int left, int right, int searchedValue)
	{
		if (right >= left)
		{
			int mid = left + (right - left)/2;

			comparison++;
			if (array[mid] == searchedValue)
			{
				System.err.println("Exists '" + searchedValue + "' at index " + mid);
				return 1;
			}

			comparison++;
			if (array[mid] > searchedValue)
				return search(left, mid-1, searchedValue);

			return search(mid+1, right, searchedValue);
		}
		System.err.println("Value not exists ");
		return 0;
	}

	public static void main(String[] args)
	{
		boolean test = true;
		double timeInSeconds;

		if(!test)
		{
			Scanner scan = new Scanner(System.in);
			System.out.println("Podaj rozmiar tablicy: ");
			int n = scan.nextInt();
			Integer[] array = new Integer[n];

			for(int i=0; i<n; i++)
				array[i] = scan.nextInt();

			System.out.println("Podaj szukaną wartość: ");
			int v = scan.nextInt();

			BinarySearch binarySearch = new BinarySearch(array);

			long startTime = System.nanoTime();

			binarySearch.search(0, array.length - 1, v);

			long estimatedTime = System.nanoTime() - startTime;
			timeInSeconds = ((double) estimatedTime) / 1000000000.0;

			System.err.printf("Total comparisons: %d\n", binarySearch.comparison);
			System.err.println("Total time: " + estimatedTime + " ns" + " | " + timeInSeconds + " seconds");
		}


		if(test)
		{
			SecureRandom randomizer = new SecureRandom();

			for(int i=1000; i<=100000; i+=1000)
			{
				Integer[] array = new Integer[i];

				for(int h = 0; h < i; h++)
					array[h] = randomizer.nextInt(1000000);

				int v = randomizer.nextInt(1000000);

				QuickSort qs = new QuickSort(array);
				qs.sort(0, array.length - 1);

				BinarySearch binarySearch = new BinarySearch(array);
				long startTime = System.nanoTime();

				binarySearch.search(0, array.length - 1, v);

				long estimatedTime = System.nanoTime() - startTime;
				timeInSeconds = ((double) estimatedTime) / 1000000000.0;

				System.err.printf("Total comparisons: %d\n", binarySearch.comparison);
				System.err.println("Total time: " + estimatedTime + " ns" + " | " + timeInSeconds + " seconds");
			}
		}
	}
}
