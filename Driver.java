/**
 * @author Michelle Nguyen
 * @version 1.0
 * @version 2023-01-24
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {
	
	
	/**
	 * Prints empty output line,
	 * to keep text driver organized and easy to read
	 */
	public static void space() {
		System.out.println("");
	}
	
	
	/**
	 * Puts a pause in the text driver,
	 * to avoid immediately looping back to driver
	 * @param in
	 */
	public static void pause(Scanner in) {
		String choice = "";
		System.out.println("Are you done?");
		System.out.print("Type 'y' if you are: ");
		// Repeats until the user enters "y"
		while (!(choice.equals("y"))) {
			choice = in.nextLine();
		}
	}
	
	
	// MAIN DRIVER
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		String choice = "";	// The user's input
		// Loops until the user enters "4" which ends the program
		while (!(choice.equals("4"))) {
			System.out.println("WORD SEARCH GENERATOR");
			System.out.println("---------------------");
			System.out.println("Enter 1 to view the wordlists.");
			System.out.println("Enter 2 to create a new wordlist.");
			System.out.println("Enter 3 to generate a word search.");
			System.out.println("Enter 4 to quit.");
			System.out.print("CHOICE: ");
			choice = in.nextLine();
			if (choice.equals("1")) {
				viewWordlists(in);
				pause(in);
			}
			else if (choice.equals("2")) {
				newWordlist(in);	
			}
			else if (choice.equals("3")) {
				generate(in);
				pause(in);
			}
			else if (choice.equals("4")) {
				in.close();
			}
			else {
				System.out.println("That was not an option. Choose again.");
			}
			space();
			space();
			space();
		}	
	}
	
	
	// *** START OF METHODS *** \\
	
	
	// MENU OPTION - 1 - VIEW WORDLISTS
	/**
	 * Views all of the stored word lists in file.txt
	 * @param in is the Scanner object
	 * @throws FileNotFoundException 
	 */
	public static void viewWordlists(Scanner in) throws FileNotFoundException {
		// Calls method that will read file.txt
		ArrayList<Wordlist> lists = getLists();	// ArrayList containing all of the Wordlists available
		space();
		// Outputs all of the Wordlists
		for (int x = 0; x < lists.size(); x++) {
			System.out.println((x + 1) + ": " + lists.get(x));
		}
		space();
	}
	
	
	/**
	 * Reads all the word lists in file.txt and stores it as a Wordlist object in an ArrayList<Wordlist>
	 * @return lists is the list containing all of the Wordlists
	 * @throws FileNotFoundException
	 */
	public static ArrayList<Wordlist> getLists() throws FileNotFoundException {
		ArrayList<Wordlist> lists = new ArrayList<>();	// ArrayList containing all of the Wordlists available
		ArrayList<String> temp = new ArrayList<>();		// Temporary ArrayList used to grab input from file.txt
		
		File file = new File("file.txt");
		Scanner inFile = new Scanner(file);		// Scanner object that reads file.txt
		/*
		 * The file is organized in sections, a space signifying the end of a section
		 * The first word in every section is the title of the word list
		 * At the end of the file is "---", without this, the program will not read the last section (last word list)
		 */
		
		// Keeps going until there are no more lines left to read
		while (inFile.hasNext()) {
			String line = inFile.nextLine();	// The line
			// If the section has ended
			if (line.equals("")) {
				Wordlist wordlist = new Wordlist(temp.get(0));	// Grabs the name of the word list for the Wordlist object
				temp.remove(0);	// Removes the name from the section so that it's only the words of the word list left
				// Adds the word list words into the Wordlist object ArrayList
				for (String w: temp) {
					Word word = new Word(w);
					wordlist.setWord(word);
				}
				// Clears the temporary ArrayList for the next section
				temp.clear();
				// Adds the new Wordlist object to the ArrayList of Wordlists
				lists.add(wordlist);
			}
			else {
				// If the section has not ended
				temp.add(line);
			}
		}
		inFile.close();
		return lists;
	}
	
	
	// MENU OPTION - 2
	/**
	 * Grabs all the old Wordlists and the new one to format, calls method to write in file.txt
	 * @param in is the Scanner object
	 * @throws IOException 
	 */
	public static void newWordlist(Scanner in) throws IOException {
		space();
		ArrayList<Wordlist> lists = getLists();	// ArrayList of Wordlists
		Wordlist newSet = createWordlist(in);	// Creates a new Wordlist object
		String input = "";	// The updated text (word lists) for file.txt
		// Adds the old word lists to input
		for (Wordlist w: lists) {
			input += w.getName() + "\n";
			for (int x = 0; x < w.getLength(); x++) {
				input += w.getWord(x) + "\n";
			}
			input += "\n";
		}
		// Adds the new word list to input
		input += newSet.getName() + ":\n";
		for (int x = 0; x < newSet.getLength(); x++) {
			input += newSet.getWord(x) + "\n";
		}
		
		input += "\n---";
		overwrite(input);
	}
	
	
	/**
	 * Creates a new Wordlist using user input
	 * @param in is the Scanner object
	 * @return tempWordlist is the new Wordlist that was created but won't be saved in file.txt
	 */
	public static Wordlist createWordlist(Scanner in) {
		Wordlist tempWordlist = new Wordlist();	// Creates a new Wordlist object
		System.out.println("Let's create a new wordlist.");
		System.out.print("What is the name of this list? : ");	// Prompts user for name of Wordlist
		String name = in.nextLine();
		tempWordlist.setName(name);
		space();
		System.out.print("How many words do you want? Enter an integer: ");
		int n = in.nextInt(); 

		// TODO: could try making integer input more robust
		in.nextLine();
		// Grabs words of Wordlist from user
		for (int x = 0; x < n; x++) {
			System.out.print("WORD #" + (x + 1) + ": ");
			String str = in.nextLine();
			while (str.length() < 2) {
				System.out.println("Please enter words of minimum 2 letters.");
				str = in.nextLine();
			}
				Word word = new Word(str.toUpperCase());
				tempWordlist.setWord(word);
		}
		return tempWordlist;
	}
	
	
	// Write in File
	/**
	 * Writes all the old Wordlists and the new one in file.txt
	 * @param str is the formatted String of the old and new Wordlsit
	 * @throws IOException
	 */
	public static void overwrite(String str) throws IOException {
		File result = new File("file.txt");
		FileWriter fw = new FileWriter(result);
			fw.write(str);	// Overwrites file.txt
		fw.flush();
		fw.close();
	}
	
	
	// MENU OPTION - 3
	/**
	 * Menu for generating a word search
	 * @param in is the Scanner object
	 * @throws FileNotFoundException 
	 */
	public static void generate(Scanner in) throws FileNotFoundException {
		String wordlistChoice = "";	// User's input if they want to create a temporary word list or use an old one
		space();
		System.out.println("Would you like to use a created wordlist or "
				+ "enter new words right now?");
		System.out.println("Enter 1 for a created wordlist.");
		System.out.println("Enter 2 for new words right now.");
		System.out.print("CHOICE: ");
		
		while (!(wordlistChoice.equals("1")) && !(wordlistChoice.equals("2"))) {
			wordlistChoice = in.nextLine();
			// If user wants to use an old Wordlist
			if (wordlistChoice.equals("1")) {
				viewWordlists(in);
				ArrayList<Wordlist> lists = getLists();
				System.out.println("Enter the number of the wordlist you would like: ");
				// TODO: make integer input, n, robust
				// -1 to accommodate for ArrayList indexes, since they start at 0
				int n = in.nextInt() - 1; // The index of the Wordlist the user wants in the ArrayList
				in.nextLine();
				while (n > lists.size() - 1|| n < 0) {
					System.out.println("This is not a valid number. Enter again.");
					n = in.nextInt() - 1;
					in.nextLine();
				}
				// Calls method that will make the word search
				makeGrid(lists.get(n));
			}
			// If user wants to create a temporary Wordlist
			else if (wordlistChoice.equals("2")) {
				// Calls method that will make the word search
				makeGrid(createWordlist(in));
			}
			// If user did not enter "1" or "2"
			else {
				System.out.println("That was not an option. Choose again.");
			}
		}
	}
	
	
	/**
	 * Creates a word search grid using a Wordlist object
	 * @param w is the Wordlist the user wishes to use for the word search
	 */
	public static void makeGrid(Wordlist w){
		space();
		
		// sort wordlist to find longest word and set the grid size to the length of the longest word plus 2
		w.sort();
		
		Grid grid = new Grid(w.getWord(0).getLength()+2);
		grid.placeWords(w);
		grid.fill();
		System.out.println(grid);
		
		System.out.println(w); // for testing
		if (!(grid.getDidNotFit().equals(""))) {
			System.out.println("Some words were not printed:");
			System.out.print(grid.getDidNotFit());
		}
		else {
			System.out.println("All the words were printed.");
		}
		space();
	}
	
	
	// *** END OF METHODS *** \\
}