package com.kacper.zielinski.aisd.lista3;

import java.security.SecureRandom;
import java.util.Random;

public class RandomizedSelect
{
	private Integer[] arr;
	private int selectedValue;
	int comparison;
	int moves;

	public RandomizedSelect(Integer[] arr)
	{
		this.arr = arr;
		selectedValue = -1;
	}

	public void printArrayWithSelectedNumber()
	{
		System.out.print("Array: ");
		for(int i=0; i<arr.length; i++)
		{
			if(arr[i] == selectedValue)
			{
				System.out.print("[" + arr[i] + "] ");
				selectedValue = -1;
			}
			else
				System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public void printArrayInfo(String header, Integer[] array)
	{
		System.err.print(header);

		for(int i=0; i<array.length; i++)
			System.err.print(array[i] + " ");

		System.err.println();
	}

	public Integer select(Integer[] array, int p, int r, int i)
	{
		System.err.printf("Starting randomizedSelect(%d, %d, %d)\n", p, r, i);

		if(p == r)
		{
			System.err.printf("return array[p] = (%d)\n", array[p]);
			selectedValue = array[p];
			return array[p];
		}

		printArrayInfo("Array before partition: ", array);
		Integer q = randomizedPartition(array, p, r);
		Integer k = q - p + 1;      // pivot + all number less than pivot
		System.err.println("k is: " + k);

		if(i == k)
		{
			System.err.printf("[i == k], return array[q] = (%d)\n", array[q]);
			selectedValue = array[q];
			return array[q];
		}
		else if(i < k)
			return select(array, p, q - 1, i);
		else
			return select(array, q + 1, r, i - k);

	}

	private Integer randomizedPartition(Integer[] array, int p, int r)
	{
		Random random = new SecureRandom();
		int i = random.nextInt((r - p) + 1) + p;
		System.err.printf("Swapping last element %d with random pivot %d on index %d\n", array[r], array[i], i);
		System.err.printf("swapInArray(array, %d, %d)\n", r, i);
		swapInArray(array, r, i);

		return partition(array, p, r);
	}

	private void swapInArray(Integer[] array, int a, int b)
	{
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
		moves+=2;
	}

	private Integer partition(Integer[] array, int p, int r)
	{
		printArrayInfo("Array after random pivot swap: ", array);
		System.err.println("Partition...");
		int x = array[r];
		int i = p-1;

		for(int j = p; j < r; j++)
		{
			comparison++;
			if(array[j] <= x)
			{
				i++;
				swapInArray(array, i, j);
			}
		}
		System.err.println("Selected pivot: " + x + " on index " + r);
		swapInArray(array,i+1, r);
		printArrayInfo("Array after partition: ", array);
		return i+1;
	}
}
