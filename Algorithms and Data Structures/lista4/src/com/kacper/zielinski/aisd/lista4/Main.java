package com.kacper.zielinski.aisd.lista4;

import com.kacper.zielinski.aisd.lista4.structures.BST;
import com.kacper.zielinski.aisd.lista4.structures.RBT;

import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
    {
	    DataStructure dataStructure = null;

	    for(int i=0; i<args.length; i++)
	    {
		    if(args[i].length() == 6 && (args[i].substring(0, 6)).equals("--type"))
		    {
			    if(i+1 <= args.length)
			    {
			    	if(args[i+1].substring(0, 3).equals("bst"))
				    {
					    dataStructure = new BST();
				    }
					else if(args[i+1].substring(0, 3).equals("rbt"))
				    {
					    dataStructure = new RBT();
				    }
				    else
				    {
					    dataStructure = new BST();
				    }
				    break;
			    }
			    else
			    {
				    System.out.println("Bad parameters.. Sorry..");
				    System.exit(-1);
			    }
		    }
		    else
		    {
			    System.out.println("Bad parameters.. Sorry..");
			    System.exit(-1);
		    }
	    }

	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Podaj liczbę operacji: ");
	    int numberOfOperations = scanner.nextInt();

	    System.out.println("Jaka klasa: " + dataStructure.getClass().getSimpleName());

	    String line = "";

	    if(scanner.hasNextLine())
	        line = scanner.nextLine();

	    System.out.println("Ta dziwna linia: " + line);

	    long startTime = System.nanoTime();

	    for(int i=0 ; i<numberOfOperations; i++)
	    {
		    if(scanner.hasNextLine())
		    {
			    line = scanner.nextLine();
			    System.out.println("Line: " + line);
		    }

		    String[] trimTab = line.split("\\s+");

		    DataStructureFactory dataStructureFactory = new DataStructureFactory(dataStructure);
		    dataStructureFactory.execute(trimTab);

	    }

	    long estimatedTime = System.nanoTime() - startTime;
	    double timeInMiliSeconds = ((double) estimatedTime) / 1000000.0;

	    System.err.println("Mode: " + dataStructure.getClass().getSimpleName());
	    System.err.println("Maximum elements number: " + dataStructure.maxElements);
	    System.err.println("Current elements number: " + dataStructure.currentSize);
	    System.err.printf("Total comparisons: %d\n", dataStructure.getComparisons());
	    System.err.printf("Total moves: %d\n", dataStructure.getMoves());
	    System.err.println("Total time: " + estimatedTime + " ns" + " | " + timeInMiliSeconds + " miliseconds");

	    dataStructure.draw();

	    scanner.close();
    }


}
