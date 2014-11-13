/*=====================================================================*\
| Author: Andrew Hammil                        		  	                |
| Group: VolatileFox                                                    |
| Site: VolatileFox.com            		                                |
|                                                                       |
| Name: Text	                                                        |
| Date: 10/14/2014                                                      |
| Description: This class represents a sent or received text.			|
\*=====================================================================*/
package frequency;

public class Text
{
	// Text members.
	private String phoneNumber;
	private String name;
	private String date;
	private String content;
	
	// Text constructor.
	protected Text (String pn, String n, String d, String c)
	{
		phoneNumber = pn;
		name = n;
		date = d;
		content = c;
	}
	
	// Returns String containing phone number.
	protected String getPhoneNumber ()
	{
		return phoneNumber;
	}
	// Returns String containing associated name.
	protected String getName ()
	{
		return (name != null) ? name : phoneNumber;
	}
	// Returns String containing send date.
	protected String getDate ()
	{
		return date;
	}
	// Returns textual contents of text.
	protected String getText ()
	{
		return content;
	}
	// Returns an array of the words found in the text.
	protected String[] getWords ()
	{
		// Make copy of content.
		String words = content;
		// Make lower case.
		words = words.toLowerCase();
		// Remove characters.
		words = words.replaceAll("[(){}\\|=+-^,.;!?<>%]", " ");
		words = words.replaceAll("\"", " ");
		words = words.replaceAll("\\*", " ");
		words = words.replaceAll("\\&", " ");
		// Removing "\LF" from string is difficult.
		
		// Return array of words split by whitespace.
		return words.split("\\s+");
	}
	// Returns number of words in text.
	protected int getWordCount ()
	{
		return this.getWords().length;
	}
	
	// Returns String of text containing primary attributes.
	public String toString ()
	{
		return "" + ((name != null) ? name : phoneNumber) + " - " + date + "\n" + content;
	}
}
