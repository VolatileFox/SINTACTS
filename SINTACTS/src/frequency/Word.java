/*=====================================================================*\
| Author: Andrew Hammil                        		  	                |
| Group: VolatileFox                                                    |
| Site: VolatileFox.com            		                                |
|                                                                       |
| Name: Word	                                                        |
| Date: 10/14/2014                                                      |
| Description: This class represents an occurrence of a word found in a |
| string of text. It has values for the word itself, as well as a count |
| to keep track of the number of occurrences. Word can be operated on	|
| to increment or decrement its count. It can also implements			|
| the comparable interface so that it can be easily sorted by frequency	|
| from highest to lowest.												|
\*=====================================================================*/
package frequency;

public class Word implements Comparable<Word>
{
	// Word being counted.
	private String word;
	// Count for word.
	private int count;

	// Word constructor.
	public Word (String w, int c)
	{
		// Initialize.
		word = w;
		count = c;
	}
	
	// CompareTo function for words.
	public int compareTo (Word other)
	{
		// If other has higher count.
		if (other.count > count)
		{
			return 1;
		}
		// If other has equal count.
		else if (count == other.count)
		{
			return word.compareTo(other.word);
		}
		// Other has lower count.
		else
		{
			return -1;
		}
	}
	
	// Returns word.
	protected String getWord ()
	{
		return word;
	}
	
	// Get value for count.
	protected int getCount ()
	{
		return count;
	}
	
	// Set value for count.
	protected void setCount (int c)
	{
		count = c;
	}
	
	// Increases the count of Word.
	protected void increment ()
	{
		count++;
	}
	
	// Decrement the count of Word.
	protected void decrement ()
	{
		count--;
	}
	
	// Returns String representation of Word.
	public String toString ()
	{
		return word + ": " + count;
	}
}