/**
 * @author Michelle Nguyen
 * @version 1.0
 * @version 2023-01-24
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 * Creates a Word List object using input from user or file.txt
 *
 */
public class Wordlist {
	private String name;			// the name of the word list
	private ArrayList<Word> list;	// the word list
	
	
	/**
	 * Constructor for Wordlist
	 * @param s is the name of the word list
	 */
	Wordlist(String s) {
		name = s;
		list = new ArrayList<>();
	}
	
	
	/**
	 * Constructor for Wordlist
	 */
	Wordlist() {
		list = new ArrayList<>();
	}
	
	
	// *** START OF METHODS *** \\
	
	
	/**
	 * Gets name of the word list
	 * @return the name of the word list
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Returns length of the word list
	 * @return the number of words in the word list
	 */
	public int getLength() {
		return list.size();
	}
	
	
	/**
	 * Gets a word
	 * @param n is the index
	 * @return the word at index n
	 */
	public Word getWord(int n) {
		return list.get(n);
	}
	
	
	/**
	 * Sets the name of the word list
	 * @param n is the name String
	 */
	public void setName(String n) {
		name = n;
	}
	
	
	/**
	 * Adds a Word to the word list
	 * @param w is the Word object
	 */
	public void setWord(Word w) {
		list.add(w);
	}
	
	
	
	/**
	 * Returns the Wordlist as a String output
	 */
	@Override
	public String toString() {
		return name + " " + list;
	}
	
	
	/**
	 * Sorts the word list by length
	 */
	public void sort() {
		Collections.sort(list);
	}
	
	
	// *** END OF METHODS *** \\
}
