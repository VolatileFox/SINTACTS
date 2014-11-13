/*=====================================================================*\
| Author: Andrew Hammil                        		  	                |
| Group: VolatileFox                                                    |
| Site: VolatileFox.com            		                                |
|                                                                       |
| Name: Thread	                                                        |
| Date: 10/14/2014                                                      |
| Description: This class represents a thread of texts. On creation it	|
| it reads the given CSV file and stores all texts in LinkedLists.		|
| There is a LinkedList created for each unique phone number. All texts |
| sent from that phone number are stored in the corresponding List.		|
| The LinkedLists are kept in a HashMap using the unique phone number	|
| as a key.																|
\*=====================================================================*/
package frequency;

import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class Thread
{
	// Collections of texts with phone numbers as keys.
	private HashMap<String, LinkedList<Text>> textsHash;
	
	// Collection of texts in order of sending.
	private LinkedList<Text> allTexts;
	
	// Phone numbers
	public static final String[][] FRIEND_NUMBERS = {{"Jessie", "+12103557887"},
													{"Andrew", "+17069923021"},
													{"Kris", "+18304802115"}};
	
	// Thread constructor.
	public Thread (String source) throws IOException, FileNotFoundException
	{
		// Notify user of loading.
		System.out.print(source + " is being loading... ");
		
		// Create buffer for reading in CSV.
		BufferedReader reader = new BufferedReader(new FileReader(source));
		
		// Initialize textsHash.
		textsHash = new HashMap<String, LinkedList<Text>>();
		
		// Initialize allTexts.
		allTexts = new LinkedList<Text>();
		
		// StringBuffer for removing substrings.
		StringBuffer lineBuffer;
		
		// Temporary variables for storing current text.
		Text currentText;
		String phoneNumber = null;
		String name = null;
		String date = null;
		String content = null;
		
		// Reads in CSV a line at a time.
		String line = null;
		while ((line = reader.readLine()) != null)
		{
			// Create new StringBuffer using newly read line.
			lineBuffer = new StringBuffer(line);
			
			// Reads phone number and deletes from lineBuffer.
			int currentIndex = lineBuffer.indexOf(",");
			phoneNumber = lineBuffer.substring(0, currentIndex);
			lineBuffer.delete(0, currentIndex + 1);
			
			// Reads date and deletes from lineBuffer
			currentIndex = lineBuffer.indexOf(",");
			currentIndex = lineBuffer.indexOf(",", currentIndex + 1);
			date = lineBuffer.substring(1, currentIndex - 1);
			lineBuffer.delete(0, currentIndex + 1);
			
			// Deletes send status and beginning quote from lineBuffer
			currentIndex = lineBuffer.indexOf(",");
			lineBuffer.delete(0, currentIndex + 1);
			currentIndex = lineBuffer.indexOf("\"");
			lineBuffer.delete(0, currentIndex + 1);
			
			// Reads content and deletes from lineBuffer
			currentIndex = lineBuffer.length();
			content = lineBuffer.substring(0, currentIndex - 1);
			
			// Apply name if applicable.
			for (int i = 0; i < FRIEND_NUMBERS.length; i++)
			{
				if (phoneNumber.equals(FRIEND_NUMBERS[i][1]))
				{
					name = FRIEND_NUMBERS[i][0];
				}
			}
			
			// If empty phone number, replace with "Number Missing".
			if (phoneNumber.isEmpty())
			{
				// Tosses missing numbers.
				continue;
				// Includes missing numbers.
				// phoneNumber = "Number Missing";
			}
			
			// Creates Text object and adds to textsHash.
			currentText = new Text(phoneNumber, name, date, content);
			
			// Adds Text to collection of recieved texts.
			allTexts.add(currentText);
			
			// If phone number already exists as key in textsHash,
			if (textsHash.containsKey(phoneNumber))
			{
				// Add currentText to corresponding LinkedList.
				textsHash.get(phoneNumber).add(currentText);
			}
			// Otherwise,
			else
			{
				// Create new LinkedList.
				LinkedList<Text> newList = new LinkedList<Text>();
				// Add currentText as initial value.
				newList.add(currentText);
				// Add newList to textsHash with phoneNumber as key.
				textsHash.put(phoneNumber, newList);
			}
		}
		
		// Close BufferReader.
		reader.close();
		
		// Notify finished.
		System.out.println("done.");
	}
	
	// Returns array containing phone numbers.
	protected String[] getPhoneNumbers ()
	{
		return textsHash.keySet().toArray(new String[0]);
	}
	
	// Returns the number of times a certain word has been used by a phoneNumber.
	protected int getCountForWordByPhoneNumber (String word, String phoneNumber)
	{
		// Iterate till word found.
		for (Word currentWord : getWordCountsByPhoneNumber(phoneNumber))
		{
			// If word found, return count.
			if (word.equals(currentWord.getWord()))
					return currentWord.getCount();
		}
		
		// Word not found.
		return 0;
	}
	
	// Returns total count of all words by a phoneNumber.
	protected int getTotalWordCountByPhoneNumber (String phoneNumber)
	{
		// Total count.
		int count = 0;
		
		// Iterate through all texts sent by phoneNumber.
		for (Text currentText : getTextsByPhoneNumber(phoneNumber))
		{
			count += currentText.getWordCount();
		}
		
		// Return count.
		return count;
	}
	
	// Returns a LinkedList of Word objects for a phone number sorted by occurrence.
	private LinkedList<Word> getWordCountsByPhoneNumber (String phoneNumber)
	{
		// Check if textsHash contains phoneNumber.
		if (!textsHash.containsKey(phoneNumber))
			return null;
		
		// Create HashMap for easy word counting.
		HashMap<String, Word> wordCounts = new HashMap<String, Word>();
		
		// Create new LinkedList to accumulate words.
		LinkedList<Word> words = new LinkedList<Word>();
		
		// Iterate through all texts sent by phoneNumber.
		for (Text currentText : getTextsByPhoneNumber(phoneNumber))
		{
			// Iterate through all words in each text.
			for (String currentWord : currentText.getWords())
			{
				// If word exists,
				if (wordCounts.get(currentWord) != null)
				{
					// increment.
					wordCounts.get(currentWord).increment();
				}
				// Otherwise,
				else
				{
					// Add first instance.
					wordCounts.put(currentWord, new Word(currentWord, 1));
				}
			}
		}
		
		// Loop over the Words in wordCounts.
		Iterator<Word> wordCountsIterator = wordCounts.values().iterator();
		while (wordCountsIterator.hasNext())
		{
			// Get next word in wordCounts.
			Word word = wordCountsIterator.next();
			
			// Add it into the list
			words.add(word);
		}
		
		// Sort words from most to least used.
		Collections.sort(words);
		
		// Return sorted list.
		return words;
	}
	
	// Returns array of Texts using a phone number.
	protected Text[] getTextsByPhoneNumber (String phoneNumber)
	{
		// If all numbers wanted.
		if (phoneNumber.equals("All numbers"))
			return allTexts.toArray(new Text[0]);
		
		// Check if textsHash contains phoneNumber as a key.
		if (textsHash.containsKey(phoneNumber))
			return textsHash.get(phoneNumber).toArray(new Text[0]);
		
		// Otherwise return null.
		return null;
	}
	
	// Search for texts containing a specific word.
	protected Text[] getTextsByWord (String word)
	{
		// TODO
		return null;
	}
	
	// Returns true if Thread contains a number.
	private boolean containsNumber (String phoneNumber)
	{
		return textsHash.containsKey(phoneNumber);
	}
	
	// Returns true if Thread contains a number.
	protected boolean containsNameOrNumber (String nameOrNumber)
	{
		// Loop through known numbers.
		for (String[] number : FRIEND_NUMBERS)
		{
			// If known.
			if (nameOrNumber.equals(number[0]))
			{
				return containsNumber(number[1]);
			}
		}
		return containsNumber(nameOrNumber);
	}
	
	// Get longest get from pboneNumber.
	protected Text getLongestTextByPhoneNumber (String phoneNumber)
	{
		// Max words seen;
		int max = 0;
		// Longest text.
		Text longestText = null;
		
		// Search through all texts from phoneNumber.
		for (Text text : getTextsByPhoneNumber(phoneNumber))
		{
			// If word count higher than max.
			if (text.getWordCount() > max)
			{
				// Update max.
				max = text.getWordCount();
				// Update longestText.
				longestText = text;
			}
		}
		
		return longestText;
	}
	
	// Returns name is name associated with number, otherwise returns number.
	protected String getName (String phoneNumber)
	{
		// Loop through known numbers.
		for (String[] number : FRIEND_NUMBERS)
		{
			// If known.
			if (phoneNumber.equals(number[1]))
				return number[1];
		}
		return phoneNumber;
	}
	
	// Returns number of unique words from phone number.
	protected int getUniqueWordCountByPhoneNumber (String phoneNumber)
	{	
		return getWordCountsByPhoneNumber(phoneNumber).size();
	}
	
	// Returns text counts for each phone number.
	public String toString ()
	{
		// Initialize StringBuffer to append to.
		StringBuffer buffer = new StringBuffer("Numbers\t\tText Count\n");
		
		// Iterate through textsHash grabbing keys and counts.
		for (Map.Entry<String, LinkedList<Text>> thread : textsHash.entrySet())
		{
			// Add phoneNumber.
			buffer.append(thread.getKey());
			// Add tab.
			buffer.append("\t");
			// Add count.
			buffer.append(thread.getValue().size());
			// Add newline.
			buffer.append("\n");
		}
		
		// Return finalized buffer.
		return buffer.toString();
	}
}