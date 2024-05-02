/**
 * @author Michelle Nguyen
 * @author Zalea Ohren
 * @version 1.0
 * @version 2023-01-24
 */

import java.util.Random;

public class Letter {
	private char character;
	
	
	/**
	 * Creates Letter
	 * @param c is the char type of the letter
	 */
	public Letter(char c) {
		character = c;
	}
	
	
	/**
	 * Constructor for creating a Letter object of a random letter 
	 */
	public Letter() {
		// ASCII upper case letters are 65 to 90
		// Get a random number within that range and type cast into char
		Random rand = new Random();
		char c = (char) rand.nextInt(65, 91);
		character = c;
	}
	
	
	// *** START OF METHODS *** \\
	
	
	/**
	 * Returns the char type of the letter
	 * @return
	 */
	public char getChar() {
		return character;
	}
	
	
	/**
	 * Returns the string of the letter
	 */
	@Override
	public String toString() {
		return Character.toString(character);
	}
	
	
	// *** END OF METHODS *** \\
}