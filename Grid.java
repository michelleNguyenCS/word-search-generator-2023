/**
 * @author Zalea Ohren
 * @version 1.0
 * @version 2023-01-24
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Models a word search grid
 *
 */
public class Grid {
	Letter[][] letters;
	ArrayList<Word> didNotFit;

	
	/**
	 * Constructor for the Grid
	 * 
	 * @param d the side of the square Grid
	 */
	public Grid(int d) {
		letters = new Letter[d][d]; // TODO change to be full of spaces instead of null
		didNotFit = new ArrayList<>();
	}

	
	/**
	 * Makes a string of words that did not fit in the grid
	 * 
	 * @return all of the words which were not placed
	 */
	public String getDidNotFit() {
		String didNotFitStr = "";
		for (Word word : didNotFit) {
			didNotFitStr += word.toString() + "\n";
		}

		return didNotFitStr;
	}

	
	/**
	 * @return the grid as a String
	 */
	@Override
	public String toString() {
		String s = "";

		for (int y = 0; y < letters[0].length; y++) {

			for (int x = 0; x < letters.length; x++) {

// check if the spot is empty (contains null) before attempting
				if (letters[x][y] == null) {
					return "The grid has not been fully generated";
				}

				s += letters[x][y] + " ";
			}
			s += "\n";
		}

		return s; // the grid
	}

	
	/**
	 * fills in all of the empty spots in letters
	 */
	public void fill() {
		for (int y = 0; y < letters[0].length; y++) {
			for (int x = 0; x < letters.length; x++) {

// if the spot is empty, place a randomly generated letter
				if (letters[x][y] == null) {
					letters[x][y] = new Letter();
				}
			}
		}
	}

	
	/**
	 * places all of the words from the Wordlist into a grid
	 * 
	 * @param words the Wordlist of words to be placed
	 */
	public void placeWords(Wordlist words) {
		Random rand = new Random();
		boolean worked; // if the word was successfully placed
		boolean isVertical = rand.nextBoolean(); // false is horizontal, true is vertical

		for (int i = 0; i < words.getLength(); i++) {
			Word word = words.getWord(i);
			isVertical = !isVertical; // attempt to place the next word in the opposite orientation

			worked = place(word, isVertical, false); // place returns if it was successful as a boolean

			if (!worked) {
				didNotFit.add(word); // add to ArrayList of words that were not successfully placed
			}
		}
	}

	
	/**
	 * places a Word in the Grid
	 * 
	 * @param word          is the Word to be placed
	 * @param isVertical    whether the word should be placed horizontally or
	 *                      vertically
	 * @param repeatAttempt if it is the first or second time the word has been
	 *                      attempted to place
	 * @return
	 */
	private boolean place(Word word, boolean isVertical, boolean repeatAttempt) {
		Random rand = new Random();

// get the possible indices for placing the word and make the keys of the HashMap into an array
		HashMap<Integer, Integer> options = findEmpty(word, isVertical);
		ArrayList<Integer> keysAsArray = new ArrayList<Integer>(options.keySet());

// if no rows/columns were found with enough space to fit the word
		if (keysAsArray.size() == 0) {
// if only one orientation has been tried, try the other one
			if (!repeatAttempt) {
				isVertical = !isVertical;
				return place(word, isVertical, true);
			}

			return false; // was not successfully placed
		}

// randomly chooses a position from the array of keys
		int choice = keysAsArray.get(rand.nextInt(keysAsArray.size()));

// place each letter in the word
		int gridIndex = options.get(choice);
		for (int i = 0; i < word.getLength(); i++) {

			if (isVertical) {
				letters[choice][gridIndex] = word.getLetter(i);
			}

			else {
				letters[gridIndex][choice] = word.getLetter(i);
			}

			gridIndex++;
		}

		return true; // word was successfully placed
	}

	
	/**
	 * finds the row or columns that have enough empty spaces to place the words
	 * 
	 * @param word       the Word to be placed
	 * @param isVertical if the word should be placed horizontally or vertically
	 * @return
	 * @return a HashMap of possible indices
	 */
	private HashMap<Integer, Integer> findEmpty(Word word, boolean isVertical) {
		HashMap<Integer, Integer> options = new HashMap<>();
		int startingIndex = 0;
		int tempStartingIndex = 0;
		int count = 0; // the current number of empty spaces in a row
		int maxSpace = 0;

// if the word is to be placed vertically find the columns with enough space for the word
		if (isVertical) {
			for (int x = 0; x < letters.length; x++) {
				count = 0;
				maxSpace = 0;
				startingIndex = 0;
				tempStartingIndex = 0;
				for (int y = 0; y < letters[0].length; y++) {
					if (letters[x][y] == null) {
						count++; // if there is not a letter there yet, increase the count
					}

// if there is a letter then make sure tempMax is the longest so far, and count is empty
					else {
						if (count >= maxSpace) {
							maxSpace = count;
							count = 0;
							startingIndex = tempStartingIndex;
							tempStartingIndex = y + 1;
						}

						else {
							count = 0;
							tempStartingIndex = y + 1;
						}
					}
				}

				if (count >= maxSpace) {
					maxSpace = count;
					startingIndex = tempStartingIndex;
				}

				if (maxSpace >= word.getLength()) {
					options.put(x, startingIndex); // add to the possible indices
				}
			}
		}

// everything above, but for horizontal words
		else {
			for (int y = 0; y < letters[0].length; y++) {
				count = 0;
				maxSpace = 0;
				startingIndex = 0;
				tempStartingIndex = 0;
				for (int x = 0; x < letters.length; x++) {
					if (letters[x][y] == null) {
						count++;
					}

					else {
						if (count >= maxSpace) {
							maxSpace = count;
							count = 0;
							startingIndex = tempStartingIndex;
							tempStartingIndex = x + 1;
						}

						else {
							count = 0;
							tempStartingIndex = x + 1;
						}
					}
				}
				if (count >= maxSpace) {
					maxSpace = count;
					startingIndex = tempStartingIndex;
				}

				if (maxSpace >= word.getLength()) {
					options.put(y, startingIndex);
				}
			}
		}

		return options;

	}
}
