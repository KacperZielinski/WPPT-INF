package com.kacper.zielinski.aisd.lista5;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Simple binary Heap of type min
 * A[parent[i]] <= A[i]
 */
public class Heap<T>
{
	private ArrayList<HeapElement<T, Integer>> heap;
	public int heapSize = 0;

	public Heap()
	{
		heap = new ArrayList<>();
	}

	public int parent(int i) {

		if (i % 2 == 1) {
			return i / 2;
		}

		return (i - 1) / 2;
	}

	public int left(int i)
	{
		return (i << 1) + 1;
	}

	public int right(int i)
	{
		return (i << 1) + 2;
	}

	public void minHeapify(int i)
	{
		int l = left(i);
		int r = right(i);
		int smallestIndex;

		if(l < heapSize && heap.get(l).getPriority() < heap.get(r).getPriority())
			smallestIndex = l;
		else
			smallestIndex = i;

		if(r < heapSize && heap.get(r).getPriority() < heap.get(i).getPriority())
			smallestIndex = r;

		if(smallestIndex != i)
		{
			Collections.swap(heap, i, smallestIndex);
			minHeapify(smallestIndex);
		}
	}

	public void buildMinHeap()
	{
		int heapSize = this.heapSize >> 1;

		for(int i = heapSize; i>0; i--)
		{
			minHeapify(i);
		}
	}

	public ArrayList<HeapElement<T, Integer>> getHeap() {
		return heap;
	}

	public void setHeap(ArrayList<HeapElement<T, Integer>> heap) {
		this.heap = heap;
	}
}
