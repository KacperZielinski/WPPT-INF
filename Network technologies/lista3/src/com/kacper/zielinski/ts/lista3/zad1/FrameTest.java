package com.kacper.zielinski.ts.lista3.zad1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FrameTest
{
	@Test
	void addCRC32CheckSum()
	{
		Frame testFrame = new Frame("010101011111");
		assertEquals("111110111111100100111011110101", testFrame.addCRC32CheckSum());

	}

	@Test
	void unsetFlagged()
	{
		Frame testFrame = new Frame("011111100101010111110111110111110110010011101111010101111110");
		testFrame.unsetFlagged();

		assertEquals("01010101111101111101111101100100111011110101", testFrame.getFrame());
	}

	@Test
	void unsetExpanded()
	{
		Frame testFrame = new Frame("1111101111101100100111011110101");
		testFrame.unsetExpanded();

		assertEquals("11111111111100100111011110101", testFrame.getFrame());
	}

	@Test
	void setExpanded2()
	{
		Frame testFrame = new Frame("1111101111101100100111011110101");
		testFrame.setExpanded();

		assertEquals("111110011111001100100111011110101", testFrame.getFrame());
	}

	@Test
	void setExpanded3()
	{
		Frame testFrame = new Frame("1111101111101100100111011110101");
		testFrame.setExpanded();

		assertEquals("111110011111001100100111011110101", testFrame.getFrame());
	}



	@Test
	void setExpanded()
	{
		Frame testFrame = new Frame("000111111");
		testFrame.setExpanded();

		assertEquals("0001111101", testFrame.getFrame());
	}

	@Test
	void setFlagged()
	{
		// private final static String FLAG = "01111110" from Frame.java;

		Frame testFrame = new Frame("00011111");
		testFrame.setFlagged();

		assertEquals("011111100001111101111110", testFrame.getFrame());
	}

}