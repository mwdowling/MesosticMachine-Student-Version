package mesosticSystem;

/**
 * @author Martin Dowling
 * 
 * This class allows the creation of an object that does the following:
 * 
 * takes a target chapter, 
 * reformats it as an array of words,
 * instantiates an output file,
 * writes a generic first line to that file,
 * creates an instance of the word-finding object,
 * finds words adhering to the requirements for each successive letter in the mesostic row
 * advances within the array while the array has a next element
 * writes a generic last line to that file 
 * 
 * According with the requirements, 
 * this object creates a "shorter" series of mesostics from the target chapter 
 * by maintaining a repository of used syllables and
 * using a syllable only once in a found word for each letter in the mesostic row
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MesosticMakerShorter implements ImesosticMaker {

	//input variables for mesostic row
	private String mesosticRowFileAddress;
	private String mesosticRow;
	private String directory;
	//input variables for target chapter 
	
	private String chapterFile;
	private String[] chapterArray;

	//output variable.  CHANGE THIS TO "SHORTER"
	private String allMesosticsFileAddress;

	//constructor
	public MesosticMakerShorter(String directory, String mesosticRowFileAddress, String chapterFileAddress,
			String allMesosticsFileAddress) throws IOException {
		this.directory = directory;
		this.mesosticRowFileAddress = mesosticRowFileAddress;
		this.mesosticRow = new String(Files.readAllBytes(Paths.get(mesosticRowFileAddress))).trim();
		this.chapterFile = new String(Files.readAllBytes(Paths.get(chapterFileAddress)));
		this.chapterArray = chapterFile.split("\\s+");
		this.allMesosticsFileAddress = allMesosticsFileAddress;

	}

	//overridden method to make shorter series of mesostics with syllable filtering
	@Override
	public void runMaker() throws IOException, InterruptedException {

		//input and output variables
		String startIndex = "0";
		Integer integer = new Integer(0);
		String[] result;

		//write a generic buffering opening line to the AllMesostics file
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(new File(allMesosticsFileAddress), true));
		bw1.write("0" + "\t" + "BEGIN");
		bw1.close();
		
		/*
		 * A while loop to traverse the target chapter.
		 * Eventually set to < chapterArray.length. Note that it goes beyond
		 * the set length to complete the for loop first so will have to use
		 * a try/catch for EOF reached in the middle of a mesostic
		 * 
		 * Stopping 100 words short of the end of the chapter 
		 * fulfils the requirements
		 */

		while (integer < chapterArray.length - 100) {
			
			//A for loop to traverse the mesostic row
			for (int i = 0; i < mesosticRow.length(); i++) {

				/*
				 * For each mesostic letter, use MesosticWordFinder
				 * to find and output a target word with its index in chapterFile
				 */
				MesosticWordFinder mwf = new MesosticWordFinder(mesosticRowFileAddress, i, chapterArray, startIndex);
				result = mwf.ElementFinder();
				String mesosticLine = result[0] + "\t" + result[1];
				System.out.println(mesosticLine);

				/* 
				 * Pass the output of the MesosticWordFinder
				 * to the a SyllableWebSearcher 				 * 
				 */
				SyllableWebSearcher sws = new SyllableWebSearcher(result[1], mesosticRowFileAddress, i);
				String savedSyllable = sws.SyllableSaver();
				System.out.println(savedSyllable);

				/*
				 * Pass the output of the SyllableWebSearcher 
				 * to the SyllableRepoReaderWriter
				 */
				SyllableRepoReaderWriter srw = new SyllableRepoReaderWriter(directory, savedSyllable, mesosticRowFileAddress, i);
				String targetRepository = srw.RepoReader();
				System.out.println(targetRepository);

				/* 
				 * If the savedSyllable is in the repository, 
				 * start again with the same mesostic letter...
				 */
				if (srw.RepoWriter(targetRepository)) {
					i--;
					// ...else write the mesosticLine to a new line of MesosticString file
				} else {

					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(allMesosticsFileAddress), true));
					bw.newLine();
					bw.write(mesosticLine);
					bw.close();
				}
				
				// advance and reformat the startIndex to test the next word
				integer = new Integer(result[0]) + 1;
				startIndex = integer.toString();
				
			}// end of for loop
		}// end of while loop
		
		//write a generic buffering last line to the file 
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File(allMesosticsFileAddress), true));
		bw2.newLine();
		bw2.write("10000" + "\t" + "END");
		bw2.close();
		
	}// end of runMaker()
}
