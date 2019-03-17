package com.kacper.zielinski.aisd.lista3;

import java.security.SecureRandom;
import java.util.Scanner;

public class Main {

	private static boolean isP;
	private static boolean isR;

    public static void main(String[] args)
    {
        for(int i=0; i<args.length; i++)
        {
            if(args[i].length() >=2 && args[i].substring(0, 1).equals("-"))
            {
                if(args[i].substring(1, 2).equals("p"))
					isP = true;
                else if(args[i].substring(1, 2).equals("r"))
	                isR = true;
                else
                {
                    System.out.println("Bad parameters.. Sorry..");
                    System.exit(-1);
                }
            }
        }

	    Scanner scan = new Scanner(System.in);
	    System.out.print("Podaj długość danych (n): ");
	    int n = scan.nextInt();
	    System.out.print("Podaj numer szukanej statysyki pozycyjnej (k): ");
	    int k = scan.nextInt();
	    scan.close();

	    if(k < 0 || k > n)
	    {
		    System.out.println("Zla statystyka pozycyjna. Wybacz..");
		    System.exit(-1);
	    }

	    Integer[] arr = generateArray(n, k);

	    System.err.println("RANDOMIZED SELECT START");
	    long startTime = System.nanoTime();

	    RandomizedSelect randomizedSelect = new RandomizedSelect(arr);
	    randomizedSelect.select(arr, 0, arr.length-1, k);
	    randomizedSelect.printArrayWithSelectedNumber();

	    System.err.println("RANDOMIZED SELECT STOP");
	    long estimatedTime = System.nanoTime() - startTime;

	    double timeInSeconds = ((double) estimatedTime) / 1000000000.0;

	    System.err.printf("Total comparisons: %d\n", randomizedSelect.comparison);
	    System.err.printf("Total moves: %d\n", randomizedSelect.moves);
	    System.err.println("Total time: " + estimatedTime + " ns" + " | " + timeInSeconds + " seconds");

	    System.err.println("RANDOMIZED SELECT STOP");
	    System.err.println("");
	    System.err.println("");
	    System.err.println("SELECT START");

	    Select select = new Select(arr);
	    select.select(arr, 0, arr.length-1, k);
	    select.printArrayWithSelectedNumber();

	    estimatedTime = System.nanoTime() - startTime;

	    timeInSeconds = ((double) estimatedTime) / 1000000000.0;

	    System.err.printf("Total comparisons: %d\n", select.comparison);
	    System.err.printf("Total moves: %d\n", select.moves);
	    System.err.println("Total time: " + estimatedTime + " ns" + " | " + timeInSeconds + " seconds");

	    System.err.println("SELECT STOP");
    }

    static Integer[] generateArray(int n, int k)
    {
	    SecureRandom randomizer = new SecureRandom();
	    Integer[] array = new Integer[n];

    	if(isP)
	    {
		    for(int h = 0; h < n; h++)
	            array[h] = Math.floorMod(randomizer.nextInt(), n) + 1;                // from 1 to n
	    }
	    else if(isR)
	    {
		    for(int h = 0; h < n; h++)
			    array[h] = randomizer.nextInt((99999-10000) + 1) + 10000;     // number from 10000..99999
	    }
	    else
	    {
		    System.out.println("Bad parameters.. Sorry..");
		    System.exit(-1);
	    }
		return array;
    }
}