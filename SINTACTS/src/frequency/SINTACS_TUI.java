/*=====================================================================*\
| Author: Andrew Hammil                        		  	                |
| Group: VolatileFox                                                    |
| Site: VolatileFox.com            		                                |
|                                                                       |
| Name: SINTACTS_TUI                                                    |
| Date: 10/14/2014                                                      |
| Description: This class represents a sent or received text.			|
\*=====================================================================*/
package frequency;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class SINTACS_TUI
{
	// Thread used for textual analysis.
	private Thread thread;
	
	// Options for operations to perform.
	private static final String [] OPERATIONS = {"Get phone numbers", // Index 0
												"Show count for a word used by phone number", // Index 1
												"Show total word count for phone number", // Index 2
												"Show longest text from phone number", // Index 3
												"Show unique word count for phone number", // Index 4
												"List all texts from phone number", // Index 5
												"Analyze another CSV file", // Index 6
												"Exit program"}; // Index 7
	
	public static void main (String[] args)
	{
		// Create SINTACTS_TUI and run.
		SINTACS_TUI sintacs = new SINTACS_TUI();
		sintacs.run();
	}
	
	// SINTACS_TUI constructor.
	public SINTACS_TUI ()
	{
		// Nullify thread.
		thread = null;
	}
	
	// Starts the analysis.
	protected void run ()
	{
		// Create Thread based on user input.
		try { thread = new Thread(chooseThread()); }
		catch (FileNotFoundException e) { System.out.println("Error: File not found."); System.exit(-1); }
		catch (IOException e) { System.out.println("Error: Problem reading file."); System.exit(-1); }
		
		// Run main operation loop.
		mainLoop();
	}
	
	// Main operation loop.
	protected void mainLoop ()
	{
		// String for input.
		String input = null;
		
		// Loop until exit.
		while (true)
		{
			// Prompt user
			System.out.println("Please type a number corresponding to\nthe operation you would like to perform.");
			// Get operation from user.
			input = getValidInputFromChoices(new LinkedList<String>(Arrays.asList(OPERATIONS)), false);
			
			// Several if statements to select operations.
			if (input.equals(OPERATIONS[0])) { showPhoneNumbers(); }
			if (input.equals(OPERATIONS[1])) { showCountForWordByPhoneNumber(); }
			if (input.equals(OPERATIONS[2])) { showTotalWordCountByPhoneNumber(); }
			if (input.equals(OPERATIONS[3])) { showLongestTextByPhoneNumber(); }
			if (input.equals(OPERATIONS[4])) { showUniqueWordCountByNumber(); }
			if (input.equals(OPERATIONS[5])) { showAllTextsByPhoneNumber(); }
			if (input.equals(OPERATIONS[6])) { run(); }
			if (input.equals(OPERATIONS[7])) { System.exit(0); }
		}
	}
	
	// Get phone numbers.
	private void showPhoneNumbers () // Operation 1
	{
		// Print out phone numbers used in thread.
		System.out.println(Arrays.toString(thread.getPhoneNumbers()));
		pressEnter();
	}
	
	// Show count for a word used by phone number.
	private void showCountForWordByPhoneNumber () // Operation 2
	{
		// Prompt for phone number.
		System.out.println("Please enter the number corresponding to the phone number to be searched.");
		
		// Get phone number from user.
		String phoneNumber = getValidInputFromChoices(thread.getPhoneNumbers(), true);
		
		// Prompt for word.
		System.out.println("Please enter the word to find a count of.");
		
		// Get phone number from user.
		String word = getValidStringInput();
		
		// Print out results.
		System.out.println(thread.getCountForWordByPhoneNumber(word, phoneNumber));
		
		// Prompt for enter.
		pressEnter();
	}
	
	// Show count for a word used by phone number.
	private void showTotalWordCountByPhoneNumber () // Operation 3
	{
		// Prompt for phone number.
		System.out.println("Please enter the number corresponding to the phone number to be searched.");
		
		// Get phone number from user.
		String phoneNumber = getValidInputFromChoices(thread.getPhoneNumbers(), true);
		
		// Print out results.
		System.out.println(thread.getTotalWordCountByPhoneNumber(phoneNumber));
		
		// Prompt for enter.
		pressEnter();
	}
	
	// Show count for a word used by phone number.
	private void showLongestTextByPhoneNumber () // Operation 4
	{
		// Prompt for phone number.
		System.out.println("Please enter the number corresponding to the phone number to be searched.");
		
		// Get phone number from user.
		String phoneNumber = getValidInputFromChoices(thread.getPhoneNumbers(), true);
		
		// Print out results.
		System.out.println(thread.getLongestTextByPhoneNumber(phoneNumber));
		
		// Prompt for enter.
		pressEnter();
	}
	
	// Show count for a word used by phone number.
	private void showUniqueWordCountByNumber () // Operation 5
	{
		// Prompt for phone number.
		System.out.println("Please enter the number corresponding to the phone number to be searched.");
		
		// Get phone number from user.
		String phoneNumber = getValidInputFromChoices(thread.getPhoneNumbers(), true);
		
		// Print out results.
		System.out.println(thread.getUniqueWordCountByPhoneNumber(phoneNumber));
		
		// Prompt for enter.
		pressEnter();
	}
	
	// Show all texts from phone number.
	private void showAllTextsByPhoneNumber () // Operation 6
	{
		// Prompt for phone number.
		System.out.println("Please enter the number corresponding to the phone number to be searched.");
		
		// Get phone number from user.
		String phoneNumber = getValidInputFromChoices(thread.getPhoneNumbers(), true);
		
		// Print out results.
		Text[] texts = thread.getTextsByPhoneNumber(phoneNumber);
		for (Text text : texts)
			System.out.println(text);
		
		// Prompt for enter.
		pressEnter();
	}
	
	// Checks directory for files and prompts for choice.
	protected String chooseThread ()
	{
		// Initial prompt on startup.
		System.out.println("SINTACS - Systematically Informing the Norm Through Analyzing Common Speech");
		System.out.println("Please type a number corresponding to\nthe file you would like to analyze.");

		// Find files in directory.
		File[] files = new File(".").listFiles();
		LinkedList<String> fileNames = new LinkedList<String>();
		for (File file : files)
		{
			if (file.isFile() && file.getName().substring(file.getName().lastIndexOf('.')).equals(".csv"))
				fileNames.add(file.getName());
		}
		
		// Prompt for number choice.
		return getValidInputFromChoices(fileNames, false);
	}
	
	// Returns input choices from array.
	protected String getValidInputFromChoices (String[] choices, boolean all)
	{
		return getValidInputFromChoices(new LinkedList<String>(Arrays.asList(choices)), all);
	}
	
	// Returns input choices from LinkedList.
	protected String getValidInputFromChoices (LinkedList<String> choices, boolean all)
	{
		// Scanner for reading input.
		Scanner scan = new Scanner(System.in);
		
		// Add all as an option if flagged.
		if (all)
			choices.add("All numbers");
		
		// Loop for valid input.
		int input = 0;
		while (true)
		{
			// List available choices and prompt for selection.
			for (int i = 1; i <= choices.size(); i++)
			{
				System.out.println("" + i + ". " + choices.get(i - 1));
			}
			
			// Initial prompt.
			System.out.print("Choice: ");
			
			// Resets Scanner to it doesn't skip.
			scan = new Scanner(System.in);
			
			// Attempt to parse integer from input.
			try
			{
				// Get input from user.
				input = scan.nextInt();
				
				// If number invalid.
				if (input < 1 || input > choices.size())
					throw new InputMismatchException();
				
				// If reached success and break.
				break;
			}
			catch (InputMismatchException e)
			{
				// Notify user of error.
				System.out.println("You have entered an invalid choice.");
				System.out.println("Please enter a number between 1 and " + choices.size() + " inclusive.");
			}
		}
		
		// Return choice corresponding to input.
		return choices.get(input - 1);
	}
	
	// Press enter to continue.
	private void pressEnter ()
	{
		// Prompt user to press enter.
		System.out.println("Press Enter to continue.");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	
	// Returns input.
	protected String getValidStringInput ()
	{
		// Create scanner for receiving input.
		Scanner scan = new Scanner(System.in);
		
		// Get input from user.
		String input = scan.nextLine();
		
		return input;
	}	
}