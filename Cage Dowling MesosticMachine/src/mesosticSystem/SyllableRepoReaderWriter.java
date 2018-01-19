package mesosticSystem;

/**
 * @author Martin Dowling


 * 
 * This class allows for the creation of an object that does the following:
 * 
 * (1) has instance variables for the mesosticRow and the target letter in the row
 * (2) instantiates these along with a saved syllable in its constructor
 * (3) has a 2 methods, the first of which:
 *     (a) creates a HashMap of syllable repository file addresses
 *     (b) instantiates and returns the appropriate address for the target letter
 * (4) the second method
 *     (a) instantiates a boolean: saved syllable is in the target repository
 *     (b) if (4)(b) is true, returns true
 *     (c) if (4)(b) is false, writes the syllable to the last line of the repository and returns false 
 *     
 * This class requires the user to have previously established the syllable repositories
 * These are text files in a target folder
 * There is one file for each letter of the mesostic row, named "MesosticLetterX.txt"
 * Where X = the index number of the letter in the mesostic row.
 * The first line of this text has the characters XY
 * where X = the index number of the letter in the mesostic row
 * and Y = the letter so indexed.
 * 
 * In a future version of the software this target folder will be populated
 * in the Setup routine of the GUI
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class SyllableRepoReaderWriter {

	// initialize strings for file paths of mesostic row
	private String directory;
	//private String mesosticRowFileAddress;
	private String[] mesosticRowArray;

	// instance variables for the mesostic row targets
	private int mesosticRowArrayIndex;
	private String thisMesosticLetter;

	// instance variable for target syllable
	private String savedSyllable;

	// constructor
	public SyllableRepoReaderWriter(String directory, String savedSyllable, String mesosticRowFileAddress, int mesosticRowArrayIndex)
			throws IOException {
		this.directory = directory; 
		String mesosticRow = new String(Files.readAllBytes(Paths.get(mesosticRowFileAddress)));
		this.mesosticRowArray = mesosticRow.split("");
		this.mesosticRowArrayIndex = mesosticRowArrayIndex;
		this.thisMesosticLetter = mesosticRowArray[mesosticRowArrayIndex];
		this.savedSyllable = savedSyllable;
	}

	// a method to read the syllable repository
	public String RepoReader() throws IOException {

		String mesosticLetterRepoFileAddress = null;
		/*
		 * create a HashMap repository for each existing syllable repository
		 * (NOTE: these files must have already been created with these exact
		 * names via the UI)
		 */
		Map<Integer, String> syllableRepositoryDir = new HashMap<>();
		for (int i = 0; i < mesosticRowArray.length; i++) {
			String mesosticLetterFileAddress = directory + "\\Syllable Repositories\\MesoticLetter"
					+ (i + 1) + ".txt";
			syllableRepositoryDir.put(i, mesosticLetterFileAddress);
		}

		// get appropriate syllable repository file
		for (Integer key : syllableRepositoryDir.keySet()) {

			mesosticLetterRepoFileAddress = syllableRepositoryDir.get(key);
			BufferedReader br = new BufferedReader(new FileReader(new File(mesosticLetterRepoFileAddress)));
			String line = br.readLine();

			int n = mesosticRowArrayIndex + 1;
			String m = n + thisMesosticLetter;
			// System.out.println(m);
			if (line.startsWith(m)) {
				break;
			}
			br.close();

		}

		return mesosticLetterRepoFileAddress;

	}

	// a method to write to the repository
	public boolean RepoWriter(String fileAddress) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(new File(fileAddress)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileAddress), true));
		boolean RepositoryHas = true;

		for (String line = br.readLine(); line != null; line = br.readLine()) {
			if (line.equals(savedSyllable)) {
				//System.out.println("Syllable is in Repository. Start again with next word.");
				RepositoryHas = true;
				break;
			} else
				RepositoryHas = false;
		}
		br.close();

		//System.out.println(RepositoryHas);
		if (!RepositoryHas) {
			System.out.println("Syllable not Repository.");
			bw.newLine();
			bw.write(savedSyllable);
			System.out.println("Syllable written to Repository.");
		}
		bw.close();
		return RepositoryHas;
	}
}
