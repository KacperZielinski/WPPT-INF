package com.kacper.zielinski.ts.lista3.zad1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MyFileWriter
{
	private String filename = "W";
	private List<Frame> framesList;

	public MyFileWriter()
	{
		framesList = new ArrayList<>();
	}

	public MyFileWriter(Frame frame)
	{
		add(frame);
	}

	public MyFileWriter(String filename)
	{
		this();
		this.filename = filename;
	}

	public void write()
	{
		try
		{
			FileWriter fileWriter = new FileWriter(new File(filename), false);

			for(Frame frame : framesList)
			{
				fileWriter.write(frame.getFrame());
			}

			System.out.println("\nWrite to file successful.");

			fileWriter.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void add(Frame t)
	{
		framesList.add(t);
	}

	public void add(String t)
	{
		framesList.add(new Frame(t));
	}

	public List<Frame> getFramesList() {
		return framesList;
	}
}
