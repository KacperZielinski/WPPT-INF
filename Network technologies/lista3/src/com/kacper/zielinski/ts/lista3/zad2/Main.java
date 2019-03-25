package com.kacper.zielinski.ts.lista3.zad2;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		List<Device> devicesList = new ArrayList<>();
		List<Integer> devicesIndexList = new ArrayList<>();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Podaj długość tablicy: ");
		int tabSize = scanner.nextInt();
		int[] tab = new int[tabSize];

		System.out.println("Podaj liczbę urządzeń: ");
		int devices = scanner.nextInt();

		SecureRandom random;
		List<JamMessage> jamMessageList = new ArrayList<>();

		for(int i=0; i<devices; i++)
		{
			try
			{
				random = SecureRandom.getInstanceStrong();
				int pos = Math.floorMod(random.nextInt(), tabSize);

				while(devicesIndexList.contains(pos))
				{
					random = SecureRandom.getInstanceStrong();
					pos = Math.floorMod(random.nextInt(), tabSize);
				}

				JamMessage jamMessage = new JamMessage();
				jamMessageList.add(jamMessage);
				Device device = new Device(tab, pos, "dev" + pos, jamMessageList);

				device.setMaxSleepTime(1000);     // in ms
				device.setNumberOfPackets(2);
				device.setPacketLength(3);
				device.setSignalSendProbability(0.1); // number from 0 to 1
				device.setSleepTimeAfterCollision(3000);
				device.setSendAttempts(10);

				devicesIndexList.add(pos);
				devicesList.add(device);
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
		}

		for (Device device : devicesList)
		{
			device.setDevicesIndexList(devicesIndexList);
			device.start();
		}



//		Device device1 = new Device(0);
//		Device device2 = new Device(tabSize/2);
//		Device device3 = new Device(tabSize-1);

//		device1.start();
//		device2.start();
//		device3.start();
	}

}
