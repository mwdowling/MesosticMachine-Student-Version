package mesosticSystem;

/**
 * @author Martin Dowling
 * 
 * This class allows the creation of an object that does the following:
 * (1) Locates the words in a mesostic file in the target chapter from whence they came
 * (2) Adds words BEFORE the mesostic word, ensuring that the process stops when
 * 		(a) 43 characters have been added
 *		(b) the mesostic word from the previous line has been reached
 *		(c) the last word in the previous extended line has been reached 
 * (3) Adds words after the mesostic word, ensuring that the process stops when
 * 		(a) 43 characters have been added
 * 		(b) the mesostic word from the next line has been reached
 * (4) Combines (3) and (4) to create a full line of adjacent words 
 * 	and writes this to a new file
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MesosticFinisherAdjacentWordAdder implements ImesosticMaker{
	
	//input variables for the target chapter
	private String chapterFile;
	private String[] chapterArray;
	
	//variables for the input (without adjacent words) and output (with adjacent words) allMesostics file
	private String allMesosticsFileAddress;
	private String allMesosticsWithAdjacentFileAddress;
	private String mesosticLine;
	
	//constructor
	public MesosticFinisherAdjacentWordAdder(String chapterFileAddress, String allMesosticsFileAddress,
			String allMesosticsWithAdjacentFileAddress) throws IOException {
	
		this.chapterFile = new String(Files.readAllBytes(Paths.get(chapterFileAddress)));
		this.chapterArray = chapterFile.split("\\s+");		
		this.allMesosticsFileAddress = allMesosticsFileAddress;
		this.allMesosticsWithAdjacentFileAddress = allMesosticsWithAdjacentFileAddress;
		
	}
	
	/* 
	 * overridden method which reads mesostic words, 
	 * finds them in the target chapter,
	 * and adds adjacent words before and after
	 * 
	 * (non-Javadoc)
	 * @see mesosticSystem.ImesosticMaker#runMaker()
	 */
	@Override
	public void runMaker() throws IOException, InterruptedException {
		
		BufferedReader br = new BufferedReader(new FileReader(new File(allMesosticsFileAddress)));
		String previousLine = br.readLine();//initially the first line
		String line = br.readLine();// initially first line of first mesostic
		br.mark(1000);
		String nextLine = br.readLine();// initially second line of first mesostic
		String lastLine = br.readLine();// a buffer line for the while loop
		br.reset();
		
		/* a variable to find the index of the last word used in a line 
		 * so as not to repeat words in the next line 
		 * (scope is crucial here: this must stay outside the while loop)
		 */
		Integer lastWord = new Integer(-1);

		//while the buffer line has not been reached
		while (nextLine != null) {

			//create two-element arrays for the three mesostic words and their indexes
			String[] previousLineContent = previousLine.split("\\t");
			String[] lineContent = line.split("\\t");
			String[] nextLineContent = nextLine.split("\\t");
			
			//initialize the indexes as Integers
			Integer indexPrevious = new Integer(previousLineContent[0]);
			Integer index = new Integer(lineContent[0]);
			Integer indexNext = new Integer(nextLineContent[0]);
			
			//variables for adding adjacent words before the mesostic word
			String wordsToAddBefore = "";
			int length = 0;
			
			/* 
			 * NOTE:  
			 * This fails to give words before "Street" (i.e. the first extracted word)
			 * DO-WHILE SOLVES THIS BUT CREATES NEW INDEX PROBLEMS 
			 */
			while ((length <= 43 && (index - indexPrevious) > 1) && index > lastWord+1) {
				
				wordsToAddBefore = chapterArray[index - 1].toLowerCase().replaceAll("\\W", "").trim() + " " + wordsToAddBefore;
				index--;
				length = wordsToAddBefore.length();

			}

			/* 
			 * Add words after the mesostic word, ensuring that the process stops when
			 * (1) 43 characters have been added
			 * (2) the mesostic word from the next line has been reached
			 */
			String wordsToAddAfter = "";
			length = 0;
			index = new Integer(lineContent[0]);
			while (length <= 43 && (indexNext - index) > 1) {

				wordsToAddAfter = wordsToAddAfter + " "
						+ chapterArray[index + 1].toLowerCase().replaceAll("\\W", "").trim();
				index++;
				length = wordsToAddAfter.length();

			}
			
			//save the index of the last word in this line
			lastWord = index;
			
			// combine BEFORE and AFTER to create full mesostic line with original index
			mesosticLine = lineContent[0] + "\t" + wordsToAddBefore + lineContent[1] + wordsToAddAfter;

			// write line to new line in file
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(allMesosticsWithAdjacentFileAddress), true));
			bw.write(mesosticLine);
			bw.newLine();
			bw.close();

			// set up while loop for next mesostic line
			previousLine = line;
			line = br.readLine();
			br.mark(1000);
			nextLine = br.readLine();
			lastLine = br.readLine();
			br.reset();
		}
			br.close();//close reader
	}
}
