package com.kacper.zielinski.aisd.lista5;

public class HeapElement<T, Integer>
{
	private int priority;
	private T value;

	public HeapElement(T value, int priority)
	{
		this.priority = priority;
		this.value = value;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
