package mesosticSystem;

/**
 * @author Martin Dowling



 * 
 * This class allows the creation of an object that does the following:
 * 
 * (1) Declares an integer assigned to a value 
 * 	to place a letter in the middle of the page of a notepad file
 * (2) Reads and reformats a target mesostics file line by line
 * (3) Calculates the position of the mesostic row letter in the line
 * (4) Calculates and adds the appropriate number of spaces to the beginning
 * 	of the line to centre that mesotic letter on the page
 * (5) writes the line to a new output file
 * 
 * This object will work on a mesostics file with and without adjeacent words added.
 * 
 * If the latter, the user should ideally have reduced the adjacent words "to taste"
 * and saved the file before calling this object.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MesosticFinisherCentered implements ImesosticMaker {

	// variables for input and output files 
	private String allMesosticsWithAdjacentFileAddress;
	private String allMesosticsFinalCentredFileAddress;
	
	//variables for setting the centred text
	private final int spacesToAddMax = 35;
	private int spaceAdjuster = 0;
	
	//constructor
	public MesosticFinisherCentered(String allMesosticsWithAdjacentFileAddress, String allMesosticsFinalCentredFileAddress) throws IOException {
		
		this.allMesosticsWithAdjacentFileAddress = allMesosticsWithAdjacentFileAddress;
		this.allMesosticsFinalCentredFileAddress = allMesosticsFinalCentredFileAddress;
	}

	@Override
	public void runMaker() throws IOException, InterruptedException {
		
		//read and reformat the first line of the input file
		BufferedReader br = new BufferedReader(new FileReader(new File(allMesosticsWithAdjacentFileAddress)));
		String line = br.readLine();// initially the first line
		String[] lineContent = line.split("\\t");

		while (line != null) {
			/*
			 * split the line into a two-element array:
			 * element [0] is the index value
			 * element [1] is the sentence
			 */
			lineContent = line.split("\\t");
			
			/*
			 * loop through the sentence 
			 * and assign the spaceAdjuster variable 
			 * to the value of the position of the mesostic letter
			 * 
			 */
			for (int i = 0; i < lineContent[1].length(); i++) {

				if (Character.isUpperCase(lineContent[1].charAt(i))) {
					
					spaceAdjuster = i;
				}
			}
			
			/*
			 * center the text 
			 * by adding to the line 
			 * the appropriate net number of spaces =
			 * (spacesToAddMax - spaceAdjuster)
			 * 
			 */
			for (int i = 0; i < spacesToAddMax - spaceAdjuster; i++) {

				lineContent[1] = " " + lineContent[1];
			}
			
			// write the newly spaced line to the output file 
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(allMesosticsFinalCentredFileAddress), true));
			bw.newLine();
			bw.write(lineContent[0] + "\t" + lineContent[1]);
			bw.close();
			
			//move to next line
			line = br.readLine();
			
		}//end of while loop
		
		br.close();//close reader

	}

}
