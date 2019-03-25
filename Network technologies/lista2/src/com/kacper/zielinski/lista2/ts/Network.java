package com.kacper.zielinski.lista2.ts;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Network
{
	private static double averagePacketInBitsSize = 64;    // bits

	private Graph graph;
	private int[][] matrix;


	public Network(Graph graph) {
		this.graph = graph;

	}

	public void createMatrix()
	{
		int[][] tab = new int[10][10];

		SecureRandom random = null;

		for(int i=0; i< tab.length; i++)
			for(int j=0; j< tab[i].length; j++)
			{
				try
				{
					random = SecureRandom.getInstanceStrong();
					tab[i][j] = Math.floorMod(random.nextInt(), 10);
				}
				catch (NoSuchAlgorithmException e)
				{
					e.printStackTrace();
				}
			}

		matrix = tab;
	}

	public double getAveragePacketDelay()
	{
		double t, g=0, ae, ce, sum=0;

		for(int i=0; i< matrix.length; i++)
			for(int j=0; j< matrix[i].length; j++)
				g +=  matrix[i][j];

		for(Edge e : graph.getEdgeList())
		{
			ae = e.getRealPackageFlow();
			ce = e.getCapacity();

			sum += ae/((ce/averagePacketInBitsSize)-ae);
		}

		g = 1/g;

		t = g * sum;

		return t;
	}

}
