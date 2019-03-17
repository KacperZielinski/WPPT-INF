package com.kacper.zielinski.ts.lista3.zad1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyFileReader
{
	private String filename = "Z";
	private String framesString = "";
//	private List<Frame> framesList;

	public MyFileReader()
	{
//		framesList = new ArrayList<>();
	}

	public MyFileReader(String filename)
	{
		this.filename = filename;
	}

	public void read()
	{
		try
		{
			StringBuilder framesStringBuilder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
			String line = bufferedReader.readLine();

			while (line != null)
			{
//				framesList.add(new Frame(line));
				framesStringBuilder.append(line);
				line = bufferedReader.readLine();
			}

			framesString = framesStringBuilder.toString();
			bufferedReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

//	public List<Frame> getFramesList() {
//		return framesList;
//	}

	public String getFramesString()
	{
		/* return framesList
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining("")); */
		return framesString;
	}
}
