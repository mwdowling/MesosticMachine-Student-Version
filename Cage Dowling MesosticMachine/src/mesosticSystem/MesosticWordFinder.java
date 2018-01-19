package mesosticSystem;

/**
 * @author Martin Dowling 

 * 
 * This is a class for the creation of an array-traversing and word-finding object, 
 * which returns a two-element array containing the found word with its index value formatted as a string. 
 * 
 * It has variables for the target files for the chapter and mesosticRow, 
 * and for string and array versions of those files.
 * 
 * It has a variable startIndex used to reset the element of an array
 * where the word-finding function begins. 
 * This variable will in normal usage be initialised at "0".  
 * 
 * 
 * The constructor does the following:
 * 
 * (1) takes parameters for mesosticRow, chapterFile, and variable for the starting element in the chapterArray
 * (2) instantiates variables for the target chapter and mesosticRow
 * 
 * There is one method, ElementFinder(), which does the following:
 * 
 * (1) formats word variables and initialises letter variables for the search 
 * (2) finds the next word from the startIndex that has the first letter
 * (3) splits the word at that letter into two Stings
 * (4) tests whether the second letter is in the second String
 * (5) places (2) to (4) in a for loop that 
 *     (a) moves to the next word if (4) is true
 *     (b) formats and saves the word if (4) is false, then moves to the next word  
 * (6) returns a String[] with two elements: the saved and formatted word and its index in chapterArray
 * 
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MesosticWordFinder {

	// variables for the mesostic row targets
	private String thisMesosticLetter;
	private String nextMesosticLetter;

	// variables for the words in the chapter formatted as an array of strings
	private String[] chapterArray;
	private String startIndex;

	//constructor
	public MesosticWordFinder(String mesosticRowFileAddress,

			int mesosticRowArrayIndex, String[] chapterArray, String startIndex) throws IOException {

		// instantiate the mesostic row as an array
		String mesosticRow = new String(Files.readAllBytes(Paths.get(mesosticRowFileAddress)));
		String[] mesosticRowArray = mesosticRow.split("");

		/*
		 * Instantiate letters in the mesostic row as successive pairs
		 * by taking the constructor's letter as "this" letter
		 * and adding to the index to get "next" letter
		 * 
		 * Following the requirements, if "this" letter is the last in the row 
		 * it is paired with the first letter of the mesostic
		 * 
		 */
		this.thisMesosticLetter = mesosticRowArray[mesosticRowArrayIndex];
		if (mesosticRowArrayIndex + 1 < mesosticRow.length()) {
			this.nextMesosticLetter = mesosticRowArray[mesosticRowArrayIndex + 1];
		} else
			this.nextMesosticLetter = mesosticRowArray[0];

		//instantiate the chapter array and start index
		this.chapterArray = chapterArray;
		this.startIndex = startIndex;

	}

	public String[] ElementFinder() throws IOException {

		// instantiate target word using reformatted starting index
		Integer startIndexAsInteger = Integer.parseInt(startIndex);
		int wordIndex = startIndexAsInteger.intValue();
		String word = chapterArray[wordIndex];

		// initialize index format for output
		String wordIndexAsString = String.valueOf(wordIndex);

		// initialise variables for letter search and word reformatting
		String letter = "";
		int letterIndex = 0;
		String wordSegment = word.substring(letterIndex);

		/* 
		 * Find, reformat, split, and recombine  
		 * the next word after the starting index word that contains thisMesosticLetter
		 */
		for (int i = 0 + wordIndex; i < chapterArray.length; i++) {

			if (chapterArray[i].toLowerCase().contains(thisMesosticLetter)) {
				
				// assign and reformat wordIndex
				wordIndex = i;
				wordIndexAsString = String.valueOf(i);
				
				// assign word, change to lower case, and remove all non-words from string
				word = chapterArray[i].toLowerCase().replaceAll("\\W", "").trim();

				// split word at thisMesosticLetter
				for (int j = 0; j < word.length(); j++) {
					letter = String.valueOf(word.charAt(j));

					if (letter.equals(thisMesosticLetter)) {
						letterIndex = word.indexOf(letter);
						wordSegment = word.substring(letterIndex);
						break;
					}
				}

				/*
				 * Following the requirements, 
				 * format the word variable for output with only thisMesosticLetter capitalised.
				 * 
				 * Do so only if the substring following the target letter 
				 * does not contain the next mesostic letter,
				 * 
				 * else advance the wordIndex and word to next element in the array
				 * 
				 */
				if (!wordSegment.contains(nextMesosticLetter)) {
					letter = letter.toUpperCase();
					word = word.substring(0, letterIndex) + letter + word.substring(letterIndex + 1);
					break;
				} else
					wordIndex = wordIndex + 1;
				
				word = chapterArray[wordIndex];

			} else //reset the array to the current index to move forward in the next loop
				word = chapterArray[i];
		}
		
		//return a two-element string array of the found word and its index
		String[] output = { wordIndexAsString, word };
		return output;
		
	}
}
