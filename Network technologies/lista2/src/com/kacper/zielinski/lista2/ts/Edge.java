package com.kacper.zielinski.lista2.ts;

public class Edge
{


	public double getReliabilityFunction()
	{
		return reliabilityFunction;
	}

	public double getCapacity() {
		return capacity;
	}

	public double getRealPackageFlow() {
		return realPackageFlow;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public void setRealPackageFlow(double realPackageFlow) {
		this.realPackageFlow = realPackageFlow;
	}
}
