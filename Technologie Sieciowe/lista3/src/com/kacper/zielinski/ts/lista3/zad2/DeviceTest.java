package com.kacper.zielinski.ts.lista3.zad2;

class DeviceTest {

	public static void main(String[] args) {
		DeviceTest dt = new DeviceTest();
		dt.checkIndexMovement();
	}

	int[] tab = new int[100];
	int[] logicTab = new int[100];

	public void checkIndexMovement()
	{
		int deviceIndex = 10;
		int indexOfCurrentDeviceInTab = 40;
		int packetLength = 100;

		for(int i=0; i<tab.length; i++)
		{
			tab[i] = 0;
			logicTab[i] = 0;
		}

		if(deviceIndex > indexOfCurrentDeviceInTab)
		{
			System.out.println("Wchodzimy w pierwsza petle");

			int border = indexOfCurrentDeviceInTab;

			for(int i = indexOfCurrentDeviceInTab; i < indexOfCurrentDeviceInTab + packetLength; i++)
			{
				if(deviceIndex >= i)
				{
					tab[i] = 1;
					logicTab[i] = 1;
				}
				printTab();
			}

			System.out.println("Wchodzimy w druga petle");

			if(packetLength < deviceIndex - indexOfCurrentDeviceInTab)
			{
				for(int i = indexOfCurrentDeviceInTab + packetLength; i < deviceIndex; i++)
				{
					tab[i - packetLength] = 0;
					tab[i] = 1;
					logicTab[i - packetLength] = 0;
					logicTab[i] = 1;
					border = i - packetLength + 1;

					printTab();
				}
			}

			System.out.println("Wchodzimy w trzecia petle");

			for(int i = border; i < deviceIndex; i++)
			{
				tab[i] = 0;
				logicTab[i] = 0;

				printTab();
			}
		}
		else
		{
			int border = indexOfCurrentDeviceInTab;

			System.out.println("Wchodzimy w pierwsza petle");
			for(int i = indexOfCurrentDeviceInTab; i > indexOfCurrentDeviceInTab - packetLength; i--)
			{
				if(deviceIndex <= i)
				{
					tab[i] = 1;
					logicTab[i] = 1;
				}
				// w sumie tu moglby byc break;
				printTab();
			}

			System.out.println("Wchodzimy w druga petle");
			if(packetLength < indexOfCurrentDeviceInTab - deviceIndex)
			{
				for(int i = indexOfCurrentDeviceInTab - packetLength; i > deviceIndex; i--)
				{
					tab[i + packetLength] = 0;
					tab[i] = 1;
					logicTab[i + packetLength] = 0;
					logicTab[i] = 1;
					border = i + packetLength - 1;

					printTab();
				}
			}
			System.out.println("Wchodzimy w trzecia petle");
			for(int i = border; i > deviceIndex; i--)
			{
				tab[i] = 0;
				logicTab[i] = 0;

				printTab();
			}
		}
	}

	public void printTab()
	{
		for(int i=0; i<tab.length; i++)
		{
			System.out.print(tab[i] + "");
		}
		System.out.println();
	}
}