package main;

import java.util.Scanner;

public class BinaryAddition {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int num = Integer.parseInt(in.next(),2);
		int sum = 0;
		
		for (int i=0; i<num; i++)
		{
			sum += Integer.parseInt(in.next(),2);
		}
		String sumAsString = Integer.toBinaryString(sum) + "";
		while (sumAsString.length() < 32)
			sumAsString = "0" + sumAsString;
			
		System.out.println(sumAsString);
		
	}

}
