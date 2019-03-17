package com.kacper.zielinski.ts.lista3.zad1;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Frame
{
	private final static String FLAG = "01111110";
	private String frame;
	private String frameCopy;

	Frame(String frame)
	{
		this.frame = frame;
		this.frameCopy = frame;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public void setExpanded()
	{
		frame = frame.replace("11111", "111110");
	}

	public String unsetExpanded()
	{
		frame = frame.replace("111110", "11111");
		return frame;
	}

	public void setFlagged()
	{
		StringBuilder frameBuilder = new StringBuilder();
		frameBuilder.append(FLAG);
		frameBuilder.append(frame);
		frameBuilder.append(FLAG);

		this.frame = frameBuilder.toString();
	}

	public void unsetFlagged()
	{
		frame = frame.substring(8, frame.length() - 8);
	}

	public void setFlagged(String flag)
	{
		StringBuilder frameBuilder = new StringBuilder();
		frameBuilder.append(flag);
		frameBuilder.append(frame);
		frameBuilder.append(flag);

		this.frame = frameBuilder.toString();
	}

	public String addCRC32CheckSum()
	{
		byte bytes[] = frame.getBytes();

		Checksum checksum = new CRC32();

		// update the current checksum with the specified array of bytes
		checksum.update(bytes, 0, bytes.length);

		// get the current checksum value
		long checksumValue = checksum.getValue();

		StringBuilder frameBuilder = new StringBuilder();
		String checkSum = Long.toBinaryString(checksumValue);

		StringBuilder sb = new StringBuilder();

		while(checkSum.length() + sb.length() != 32)
			sb.append('0');

		sb.append(checkSum);

		checkSum = sb.toString();

		frameBuilder.append(frame);
		frameBuilder.append(checkSum);

		this.frame = frameBuilder.toString();

		return checkSum;
	}

	@Override
	public String toString() {
		return frame;
	}
}
