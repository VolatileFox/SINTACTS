package frequency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Tester
{
	// Test Numbers
	public final static String JessieNumber = "+12103557887";
	public final static String AndrewNumber = "+17069923021";
	public final static String KrisNumber = "+18304802115";
	
	public static void main (String[] args)
	{
		// Create new thread for testing.
		Thread testThread1 = null;
		try
		{
			testThread1 = new Thread("Jessie.csv");
		}
		catch (FileNotFoundException e)
		{
			// Print stack trace.
			e.printStackTrace();
			// Print error.
			System.out.println("Error: Make sure file exists and is in proper location.");
		}
		catch (IOException e)
		{
			// Print stack trace.
			e.printStackTrace();
			// Print error.
			System.out.println("Error: Failed to read file.");
		}
		
		// Print phone numbers in textThread1.
		System.out.println("Numbers found in file.");
		for (String s : testThread1.getPhoneNumbers())
		{
			System.out.println(s);
		}
		
		// Print toString for testThread1.
		System.out.println();
		System.out.println(testThread1);
		
		// Print first 3 texts for testThread1 from JessieNumber.
		System.out.println("First 3 texts and words for testThread1 from Jessie");
		for (int i = 0; i < 3; i++)
		{
			// Create text.
			Text testText = testThread1.getTextsByPhoneNumber(JessieNumber)[i];
			// Print out text.
			System.out.println(testText);
			// Print words in text.
			System.out.println(Arrays.toString(testText.getWords()));
		}
		// Print first 3 texts for testThread1 from +17069923021.
		System.out.println("\nFirst 3 texts and words for testThread1 from Andrew");
		for (int i = 0; i < 3; i++)
		{
			// Create text.
			Text testText = testThread1.getTextsByPhoneNumber(AndrewNumber)[i];
			// Print out text.
			System.out.println(testText);
			// Print words in text.
			System.out.println(Arrays.toString(testText.getWords()));
		}
		
		// Print out word frequencies.
		//System.out.println("Frequencies:");
		//System.out.println("Jessie's Frequencies:" + testThread1.getWordCountsByPhoneNumber(JessieNumber));
		//System.out.println("Andrews's Frequencies:" + testThread1.getWordCountsByPhoneNumber(AndrewNumber));
		
		// Print out word frequencies.
		System.out.println("\nUnique word counts.");
		System.out.println("Jessie's unique words:" + testThread1.getUniqueWordCountByPhoneNumber(JessieNumber));
		System.out.println("Andrews's unique words:" + testThread1.getUniqueWordCountByPhoneNumber(AndrewNumber));
		
		// Print out longest texts.
		System.out.println("\nLongest texts.");
		System.out.println(testThread1.getLongestTextByPhoneNumber(JessieNumber) + "\nWord count: "
				+ testThread1.getLongestTextByPhoneNumber(JessieNumber).getWordCount());
		System.out.println(testThread1.getLongestTextByPhoneNumber(AndrewNumber) + "\nWord count: "
				+ testThread1.getLongestTextByPhoneNumber(AndrewNumber).getWordCount());
		
		// Print out total word count accross all texts.
		System.out.println("\nTotal word count.");
		System.out.println("Jessie: " + testThread1.getTotalWordCountByPhoneNumber(JessieNumber));
		System.out.println("Andrew: " + testThread1.getTotalWordCountByPhoneNumber(AndrewNumber));
	}
}
