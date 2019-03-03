package com.kacper.zielinski.aisd.lista5;

import java.util.ArrayList;
import java.util.Collections;

public class PriorityQueue<T, Integer>
{
	private Heap heap;
	private ArrayList<HeapElement<T, Integer>> heapList;

	public PriorityQueue()
	{
		this.heap = new Heap<T>();
		this.heapList = heap.getHeap();
	}

	public void insert(T value, int priority)
	{
		HeapElement<T, Integer> hp = new HeapElement<>(value, priority);
		heapList.add(heap.heapSize, hp);
		heap.heapSize++;
		decreaseKey(heap.heapSize-1, heapList.get(heap.heapSize-1));
	}

	public boolean empty()
	{
		if(heap.heapSize == 0)
			return true;
		else
			return false;

	}

	public boolean emptyWithInfo()
	{
		if(heap.heapSize == 0) {
			System.out.println("1");
			return true;
		}
		else {
			System.out.println("0");
			return false;
		}
	}

	// Minimum
	public HeapElement<T, Integer> top()
	{
		if(empty())
		{
			System.out.println();
			return null;
		}
		else
			System.out.println(heapList.get(0).getValue());

		return heapList.get(0);
	}

	// Extract-Min
	public HeapElement<T, Integer> pop()
	{
		// TODO exception
		if(empty())
			return null;

		HeapElement<T, Integer> min = heapList.get(0);
		int indexOfLastElement = heap.heapSize - 1;
		Collections.swap(heapList, 0, indexOfLastElement);

		heap.heapSize--;
		heap.minHeapify(0);

		return min;
	}

	public void priority(T value, int priority)
	{
		for (HeapElement<T, Integer> hp: heapList)
		{
			if(hp.getValue().equals(value))
			{
				if(priority < hp.getPriority())
					hp.setPriority(priority);
			}
		}

		heap.minHeapify(0);
	}

	// zmniejsz priorytet, bo klucz = priorytet
	public void decreaseKey(int index, HeapElement<T, Integer> element)
	{
		int key = element.getPriority();

		if(key > heapList.get(index).getPriority())
			return;

		heapList.get(index).setPriority(key);

		while(index > 0 && heapList.get(index).getPriority() < heapList.get(heap.parent(index)).getPriority())
		{
			Collections.swap(heapList, index, heap.parent(index));
			index = heap.parent(index);
		}
	}

	public void print()
	{
		for (int i = 0; i < heap.heapSize; i++) {
			HeapElement<T, Integer> heapElement = heapList.get(i);
			System.out.print("(" + heapElement.getValue() + ", " + heapElement.getPriority() + "), ");
		}
		System.out.println();
	}

}
