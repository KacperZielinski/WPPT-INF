package com.kacper.zielinski.lista2.aisd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
	    SortingAlgorithm<Integer> algorithm = new InsertionSort();
	    SortingOrder sortingOrder = SortingOrder.DESC;
	    String filename = "stats.txt";
	    int repetition = 0;

	    for(int i=0; i<args.length; i++)
		{
			if(args[i].length() >=2 && args[i].substring(0, 2).equals("--"))
			{
				if(args[i].substring(2, 6).equals("type"))
				{
					if(i+1 < args.length)
					{
						if(args[i+1].equals("insert"))
							algorithm = new InsertionSort();
						else if(args[i+1].equals("merge"))
							algorithm = new MergeSort();
						else if(args[i+1].equals("dual"))
							algorithm = new DualPivotQuickSort();
						else if(args[i+1].equals("radix"))
							algorithm = new RadixSort();
						else
							algorithm = new QuickSort();
					}
				}
				else if(args[i].substring(2, 6).equals("comp"))
				{
					if(i+1 < args.length)
					{
						if(args[i+1].equals(">="))
							sortingOrder = SortingOrder.DESC;
						else
							sortingOrder = SortingOrder.ASC;
					}
				}
				else if(args[i].substring(2, 6).equals("stat"))
				{
					if(i+1 < args.length)
					{
						filename = args[i+1];

						if(i+2 < args.length)
						{
							try
							{
								repetition = Integer.parseInt(args[i+2]);
							}
							catch (NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						else
							repetition = 1;
					}
				}
				else
				{
					System.out.println("Bad parameters.. Sorry..");
					System.exit(-1);
				}
			}
		}
		algorithm.setSortingOrder(sortingOrder);

	    if(repetition == 0)
	    {
		    Scanner scan = new Scanner(System.in);
		    int n = scan.nextInt();
		    Integer[] tab = new Integer[n];

		    for(int i=0; i<n; i++)
			    tab[i] = scan.nextInt();

		    algorithm.setTab(tab);
		    algorithm.setInfo(true);
		    algorithm.sort();
	    }
	    else
	    {
		    SecureRandom randomizer = new SecureRandom();
		    byte[] randomBytes = new byte[128];
		    int j;

		    randomizer.nextBytes(randomBytes);

		    try
		    {
				FileWriter fileWriter = new FileWriter(new File(filename), true);

		        for(j=10; j<=100000;)
			    {
				    Integer[] tab = new Integer[j];
				    Integer[] tempTab = new Integer[j];

				    //TODO dodaj zakres do RadixSorta, zabawa

				    if(algorithm instanceof RadixSort)
				    	((RadixSort) algorithm).setMaximum(10);          // tutaj tez podmienic

				    for(int h = 0; h<j; h++)
					    tab[h] = randomizer.nextInt((9-1) + 1) + 1;     // number from 10000..99999

				    System.arraycopy(tab, 0, tempTab, 0, j);

				    for(int k = 0; k < repetition; k++)
				    {
					    algorithm.setTab(tab);
					    algorithm.setInfo(true);

					    if(algorithm instanceof RadixSort)
						    ((RadixSort) algorithm).computerParamD();

					    algorithm.sort();

					    fileWriter.write(
					            algorithm.getAlgorithmName() + ";" +
							    j + ";" +
							    algorithm.getComparisons() + ";" +
							    algorithm.getMoves() + ";" +
							    algorithm.getTimeInSecondsString() + "\n"
					    );
					    System.arraycopy(tempTab, 0, tab, 0, j);
					}
				    j = formatJ(j);
				}

			    fileWriter.close();
	        }
		    catch (IOException e)
		    {
			    e.printStackTrace();
		    }
	    }
    }

    private static int formatJ(int j)
    {
	    StringBuilder jStr = new StringBuilder(Integer.toString(j));

	    if (jStr.charAt(0) == '1')
		    jStr.replace(0,1, "5");

	    else
	    {
		    jStr.replace(0,1, "1");
		    jStr.append(0);
	    }

	    return Integer.parseInt(jStr.toString());
    }
}

