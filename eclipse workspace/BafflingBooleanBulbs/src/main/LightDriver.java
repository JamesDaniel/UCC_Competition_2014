package main;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class LightDriver
{
	public List<SwitchOrGate> switchesAndGates = new ArrayList<>();
	public List<Bulb> bulbs = new ArrayList<>();
	public int numOfGates;
	//public List
	
	public LightDriver(){

		/*
		Scanner in = null;
		try{
		in = new Scanner(new File("file/ya.txt"));
		}
		catch (Exception ex)
		{
			
		}*/
		System.out.println("Please enter input: ");
		Scanner in = new Scanner(System.in);
		int numOfLightSwitches = in.nextInt();
		int numOfBulbs = in.nextInt();
		numOfGates = in.nextInt();
		
		//   
		
		for (int i=0; i<numOfLightSwitches; i++)  // populate the switches
		{
			int identifier = in.nextInt();
			int state = in.nextInt();
			
			switchesAndGates.add(new SwitchOrGate(identifier,
					                                  state,
					                                  -1,-1,-1));
		}
		for (int i=0; i<numOfBulbs; i++)    // populate the bulbs
		{
			int identifier = in.nextInt();
			int sourceOne = in.nextInt();
			
			bulbs.add(new Bulb(identifier,
					           sourceOne));
		}
		for (int i=0;i<numOfGates;i++)      // populate the gates
		{
			int identifier = in.nextInt();
			int sourceOne = in.nextInt();
			int sourceTwo = in.nextInt();
			int gateType = in.nextInt();
			
			switchesAndGates.add(new SwitchOrGate(identifier,
					                                  -1,          // initial state
					                                  sourceOne,
					                                  sourceTwo,
					                                  gateType));
		}
		for (SwitchOrGate sog:switchesAndGates)
		{
			if (sog.state != -1)
				System.out.println("\n\nSwitch identifier: " + sog.identifier +
					               "\nSwitch state: " + sog.state);
			else
				System.out.println("\nGate identifier: " + sog.identifier +
					               "\nGate initial state: " + sog.state +
					               "\nGate source1: " + sog.sourceOne +
					               "\nGate source2: " + sog.sourceTwo +
					               "\nGate type: " + sog.gateType);
		}
		System.out.println("\n\nProcessing Information: ");
		setGateStates();
	}
	
	
	public static void main(String args[])
	{
		LightDriver app = new LightDriver();
	}
	private class Bulb
	{
		public int identifier;
		public int source;
		public int state;
		
		public Bulb(int identifier, int source)
		{
			this.identifier = identifier;
			this.source = source;
			this.state = -1;
		}
	}
	private class SwitchOrGate{
		public int identifier;
		public int state;
		public int sourceOne;
		public int sourceTwo;
		public int gateType;
		public SwitchOrGate(){
			this.identifier = -1;
			this.state = -1;
			this.sourceOne = -1;
			this.sourceTwo = -1;
			this.gateType = -1;
		}
		public SwitchOrGate(int a,int b,int c,int d,int e){
			this.identifier = a;
			this.state = b;
			this.sourceOne = c;
			this.sourceTwo = d;
			this.gateType = e;
		}
	}
	
	public void setGateStates(){
		System.out.println("Setting Gate States: ");
		int changedStateCount=0;
		while (changedStateCount != numOfGates)    // the state of all gates need to be set
			                                       // this'll loop more times if one gate needs another gate that wasn't set yet.
		{
			System.out.println("Entering while loop: ");
			int sourceToUse1 = -1;  // this is just housekeeping to reset all variables   
			int sourceToUse2 = -1;
			boolean source1isValid = false;
			boolean source2isValid = false;
			
			for (SwitchOrGate sog : switchesAndGates)
			{
				System.out.println("Entering for each loop: ");
				sourceToUse1 = -1;
				sourceToUse2 = -1;
				source1isValid = false;
				source2isValid = false;
				if (sog.state == -1)
				{
					System.out.println("sog state == -1");
					for (SwitchOrGate sog2: switchesAndGates)  // this whole loop will go on even after we got what we wanted. doesn't matter.
					{
						if (sog.sourceOne == sog2.identifier &&
							sog2.state != -1)
						{
							sourceToUse1 = sog2.state;
							source1isValid = true;
						}
						if (sog.sourceTwo == sog2.identifier &&
							sog2.state != -1)
						{
							sourceToUse2 = sog2.state;
							source2isValid = true;
						}
					}
					

					if (source1isValid && source2isValid)   // if we have 2 inputs we should set the output state
					{
						System.out.println("two valid sources found.");
						processGate(sourceToUse1,sourceToUse2,sog);
						changedStateCount++;
					}
				}
			}
		}
		
		// all gates should have an output value now
		for (Bulb b: bulbs)   // run through the bulbs and change their state
		{
			for (SwitchOrGate g: switchesAndGates)
			{
				if (b.source == g.identifier)
				{
					b.state = g.state;
				}
			}
		}

		System.out.println("\nThe state of the bulb or bulbs is: ");
		for (Bulb b:bulbs) // print out the state of the bulbs or bulb
		{
			System.out.print(b.state + " ");
		}
		
	}
	public void processGate(int sourceToUse1, int sourceToUse2, SwitchOrGate sog) // if we have both sources then change the state according to them.
	{
		System.out.println("Beginning to process gate output: ");
		if (sog.gateType == 1)
		{
			System.out.println("Entering AND gate");
			if (sourceToUse1 == 1 && sourceToUse2 == 1)  // AND gate
				sog.state = 1;
			else
				sog.state = 0;
		}
		else if (sog.gateType == 2)
		{
			if (sourceToUse1 == 0 && sourceToUse2 == 0)  // OR gate
				sog.state = 0;
			else
				sog.state = 1;
		}
		else if (sog.gateType == 3)
		{
			if ((sourceToUse1 == 1 && sourceToUse2 == 1) ||
				(sourceToUse1 == 0 && sourceToUse2 == 0))      // XOR gate
				sog.state = 0;
			else
				sog.state = 1;
		}
		else if (sog.gateType == 4) 
		{
			if (sourceToUse1 == 0 && sourceToUse2 == 0)  // NOR gate
				sog.state = 1;
			else
				sog.state = 0;
		}
		else if (sog.gateType == 5)
		{
			if (sourceToUse1 == 1 && sourceToUse2 == 1)  // NAND gate
				sog.state = 0;
			else
				sog.state = 1;
		}
	}

}