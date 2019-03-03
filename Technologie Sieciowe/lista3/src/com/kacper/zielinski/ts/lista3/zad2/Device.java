package com.kacper.zielinski.ts.lista3.zad2;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Device extends Thread
{
	private static int collisions = 0;

	private List<JamMessage> jamMessageList;
	private List<Integer> devicesIndexList = new ArrayList<>();
	private int[] tab;
	private int[] logicTab;
	private int indexOfCurrentDeviceInTab;
	private int maxSleepTime = 20000;
	private int numberOfPackets = 5;
	private int packetLength = 5;
	private int sendAttempts = 0;
	private int sleepTimeAfterCollision = 10000;
	private double signalSendProbability = 0.1;
	private String name = "";

	public Device(int[] tab, int index, List<JamMessage> jamMessageList)
	{
		this.jamMessageList = jamMessageList;
		this.tab = tab;
		this.indexOfCurrentDeviceInTab = index;

		logicTab = new int[tab.length];
	}

	public Device(int[] tab, int index, String name, List<JamMessage> jamMessageList)
	{
		this(tab, index, jamMessageList);
		this.name = name;
	}

	public Device(int[] tab, int index, int maxSleepTime, String name, List<JamMessage> jamMessageList)
	{
		this(tab, index, name, jamMessageList);
		this.maxSleepTime = maxSleepTime;
	}

	public void run()
	{
		sendSynchronizedOutput(this.toString() + "Running...");
		SecureRandom random;

		while(numberOfPackets >= 0)
		{
			try
			{
				random = SecureRandom.getInstanceStrong();

//				int randomPos = Math.floorMod(random.nextInt(), tab.length);
//				tab[randomPos] = 1;
//				System.out.println("[ Thread " + name + " ]  Working " + randomPos);
//				for (int numb: tab) System.out.print(numb); System.out.println("\n");

				double probability = random.nextDouble();

				if(probability <= signalSendProbability)
				{
					if(isMediumFree())
					{
						sendSynchronizedOutput(this.toString() + "Claims that medium is free");

						int deviceIndex = devicesIndexList.get(Math.floorMod(random.nextInt(), devicesIndexList.size()));

						while(deviceIndex == indexOfCurrentDeviceInTab && devicesIndexList.size() > 1)
						{
							deviceIndex = Math.floorMod(random.nextInt(), devicesIndexList.size());
							deviceIndex = devicesIndexList.get(deviceIndex);
						}

						sendSynchronizedOutput(this.toString() +
								"Sending from index " + indexOfCurrentDeviceInTab +
								" to " + deviceIndex);

						// to w jakiejs petli dopoki nie jest pusta tablica, czyli sygnal nie doszedl
						if(deviceIndex > indexOfCurrentDeviceInTab)
						{
							int border = indexOfCurrentDeviceInTab;

							for(int i = indexOfCurrentDeviceInTab; i < indexOfCurrentDeviceInTab + packetLength; i++)
							{
								if(deviceIndex >= i)
								{
									if(isTabLogicBroken())
									{
										notifyAllWithJamMessage();
										clearTableAndJamMessages();
										goToSleep(sleepTimeAfterCollision);
										break;
									}
									tab[i] = 1;
									logicTab[i] = 1;

									printTab();
								}
							}

							// ten if chyba niepotrzebny bo wynika z fora
							if(packetLength < deviceIndex - indexOfCurrentDeviceInTab)
							{
								for(int i = indexOfCurrentDeviceInTab + packetLength; i < deviceIndex; i++)
								{
									if(isTabLogicBroken())
									{
										notifyAllWithJamMessage();
										clearTableAndJamMessages();
										goToSleep(sleepTimeAfterCollision);
										break;
									}
									tab[i - packetLength] = 0;
									tab[i] = 1;
									logicTab[i - packetLength] = 0;
									logicTab[i] = 1;
									border = i - packetLength + 1;

									printTab();
								}
							}


							for(int i = border; i < deviceIndex; i++)
							{
								if(isTabLogicBroken())
								{
									notifyAllWithJamMessage();
									clearTableAndJamMessages();
									goToSleep(sleepTimeAfterCollision);
									break;
								}
								tab[i] = 0;
								logicTab[i] = 0;
								printTab();
							}

						}
						else if(deviceIndex < indexOfCurrentDeviceInTab)
						{
							int border = indexOfCurrentDeviceInTab;

							for(int i = indexOfCurrentDeviceInTab; i > indexOfCurrentDeviceInTab - packetLength; i--)
							{
								if(deviceIndex <= i)
								{
									if(isTabLogicBroken())
									{
										notifyAllWithJamMessage();
										clearTableAndJamMessages();
										goToSleep(sleepTimeAfterCollision);
										break;
									}
									tab[i] = 1;
									logicTab[i] = 1;
									printTab();
								}
							}


							if(packetLength < indexOfCurrentDeviceInTab - deviceIndex)
							{
								for(int i = indexOfCurrentDeviceInTab - packetLength; i > deviceIndex; i--)
								{
									if(isTabLogicBroken())
									{
										notifyAllWithJamMessage();
										clearTableAndJamMessages();
										goToSleep(sleepTimeAfterCollision);
										break;
									}
									tab[i + packetLength] = 0;
									tab[i] = 1;
									logicTab[i + packetLength] = 0;
									logicTab[i] = 1;
									border = i + packetLength - 1;
									printTab();
								}
							}


							for(int i = border; i > deviceIndex; i--)
							{
								if(isTabLogicBroken())
								{
									notifyAllWithJamMessage();
									clearTableAndJamMessages();
									goToSleep(sleepTimeAfterCollision);
									break;
								}
								tab[i] = 0;
								logicTab[i] = 0;
								printTab();
							}


						}
						else
						{
							sendSynchronizedOutput(this.toString() + "Last device, shutdown.");
							numberOfPackets = 0;
						}
						sendSynchronizedOutput(this.toString() + "liczba pozostalych pakietow: " + numberOfPackets);
						numberOfPackets--;
					}
					else
						goToSleep(maxSleepTime);

				}
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
				break;
			}
		}
		devicesIndexList.remove(devicesIndexList.indexOf(indexOfCurrentDeviceInTab));
		sendSynchronizedOutput(this.toString() + "Nadałem wszystkie pakiety, odłączam się od sieci! :) ");
		sendSynchronizedOutput(this.toString() + "Ilosc kolizji: " + collisions);

	}
	private void notifyAllWithJamMessage()
	{
		sendSynchronizedOutput(this.toString() + "COLLISION!");

		for (JamMessage jamMessage: jamMessageList)
			jamMessage.setCorrutped(true);
	}

	private synchronized void clearAllJamMessages()
	{
		for (JamMessage jamMessage: jamMessageList)
			jamMessage.setCorrutped(false);

		collisions++;

		sendSynchronizedOutput(this.toString() + "Kanał znów jest wolny!");
	}

	private synchronized void printTab()
	{
		System.out.print(this.toString() + " ");
		for (int numb: tab) System.out.print(numb); System.out.println();
	}

	private synchronized void sendSynchronizedOutput(String output)
	{
		System.out.println(output);
	}

	private boolean checkIfIsJamMessage()
	{
		for (JamMessage jamMessage: jamMessageList)
			if(jamMessage.isCorrutped())
				return true;

		return false;
	}

	private boolean isMediumFree()
	{
		if(checkIfIsJamMessage())
			return false;

		for(int i=0; i<tab.length; i++)
		{
			if(tab[i] != 0)
				return false;
		}
		return true;
	}

	private boolean isTabLogicBroken()
	{
		if(checkIfIsJamMessage())
			return true;

		for(int i=0; i<tab.length; i++)
		{
			if(tab[i] != logicTab[i])
				return true;
		}
		return false;
	}

	private void clearTableAndJamMessages()
	{
		for(int i=0; i<tab.length; i++)
		{
			tab[i] = 0;
			logicTab[i] = 0;
		}
		clearAllJamMessages();
	}

	private void goToSleep(int maxRange)
	{
		if(sendAttempts >= 10)
			maxRange += maxSleepTime;

		else if(sendAttempts >=15)
//			Thread.interrupted();
			numberOfPackets--;

		try
		{
			SecureRandom random = SecureRandom.getInstanceStrong();
			int time = Math.floorMod(random.nextInt(), maxRange);
			Thread.sleep(time);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}

	public int getIndexOfCurrentDeviceInTab() {
		return indexOfCurrentDeviceInTab;
	}

	public void setSleepTimeAfterCollision(int sleepTimeAfterCollision) {
		this.sleepTimeAfterCollision = sleepTimeAfterCollision;
	}

	public void setMaxSleepTime(int maxSleepTime) {
		this.maxSleepTime = maxSleepTime;
	}

	public void setPacketLength(int packetLength) {
		this.packetLength = packetLength;
	}

	public void setNumberOfPackets(int numberOfPackets) {
		this.numberOfPackets = numberOfPackets;
	}

	public void setSignalSendProbability(double signalSendProbability) {
		this.signalSendProbability = signalSendProbability;
	}

	public void setSendAttempts(int sendAttempts) {
		this.sendAttempts = sendAttempts;
	}

	public void setDevicesIndexList(List<Integer> devicesIndexList) {
		this.devicesIndexList = devicesIndexList;
	}

	@Override
	public String toString() {
		return "[ Thread " + name + " ] ";
	}
}
