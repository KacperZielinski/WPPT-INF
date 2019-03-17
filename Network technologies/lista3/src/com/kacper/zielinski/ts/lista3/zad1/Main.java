package com.kacper.zielinski.ts.lista3.zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static final int frameSize = 32;

    public static void main(String[] args)
    {
    	Scanner scanner = new Scanner(System.in);

	    System.out.println("[1] Ramkowanie ");
	    System.out.println("[2] Odwrotne ramkowanie ");

	    switch (scanner.nextInt())
	    {
		    case 1:
		    {
		    	frame();
			    break;
		    }
		    case 2:
		    {
		    	unframe();
		    	break;
		    }
		    default:
		    {
			    System.out.println("\nRamkowanie ");
			    frame();
		    }
	    }

    }

    public static void frame()
    {
	    MyFileReader reader = new MyFileReader();
	    MyFileWriter writer = new MyFileWriter();

	    reader.read();

	    List<Frame> framesList = new ArrayList<>();
	    String buf = reader.getFramesString();

	    int i;

	    for(i=0; i<(buf.length() / frameSize); i++)
		    framesList.add(new Frame(buf.substring(frameSize*i, frameSize*(i+1))));

	    if(Math.floorMod(buf.length(),frameSize) != 0)
			framesList.add(new Frame(buf.substring(frameSize * i)));

	    for (Frame frame : framesList)
	    {
		    System.out.println("Frame: " + frame.getFrame());
		    frame.addCRC32CheckSum();
		    System.out.println("Frame with checksum: " + frame.getFrame());
		    frame.setExpanded();
		    System.out.println("Frame expanded: " + frame.getFrame());
		    frame.setFlagged();
		    System.out.println("Frame flagged: " + frame.getFrame());
		    writer.add(frame);
	    }

	    writer.write();
    }

    public static void unframe()
    {
	    MyFileReader reader = new MyFileReader("W");
	    MyFileWriter writer = new MyFileWriter("copyZ.txt");

	    reader.read();

	    String buf = reader.getFramesString();

	    buf = buf.replace("0111111001111110", " ");
	    buf = buf.replace("01111110", "");

	    List<Frame> framesList = new ArrayList<>();
	    String[] framesArray = buf.split(" ");

	    for (String frameString: framesArray)
		    framesList.add(new Frame(frameString));

	    for (Frame frame : framesList)
	    {
	    	if(frame.getFrame().length() < 32)
		    {
			    System.out.println("[ERROR] Ramka niepoprawna!");
			    continue;
		    }

		    System.out.println("Unflagged frame: " + frame.getFrame() + " size: " + frame.getFrame().length());

		    Frame anotherFrame = new Frame(frame.getFrame());
		    anotherFrame.unsetExpanded();

		    String anotherFrameString = anotherFrame.getFrame();
		    int anotherFrameSize = anotherFrameString.length();

		    System.out.println("Unflagged unexpanded frame: " + anotherFrameString + " size: " + anotherFrameSize);

		    String attachedCRC = anotherFrameString.substring(anotherFrameSize-32);
		    System.out.println("AttachedCRC: " + attachedCRC);

		    String rawFrameString = anotherFrameString.substring(0, anotherFrameSize-32);
		    System.out.println("rawFrameString: " + rawFrameString);

		    Frame rawFrame = new Frame(rawFrameString);
		    String rawFrameCRCString = rawFrame.addCRC32CheckSum();

		    System.out.println("rawFrameCRC: " + rawFrameCRCString);
		    Frame rawFrameCRC = new Frame(rawFrameCRCString);

		    if(rawFrameCRC.getFrame().equals(attachedCRC))
		    {
			    frame = new Frame(rawFrameString);
			    writer.add(frame);
		    }
		    else
		    {
			    System.out.println("[ERROR] Ramka niepoprawna!");
		    }

	    }

	    if(writer.getFramesList() != null || !writer.getFramesList().isEmpty())
	        writer.write();
    }
}
