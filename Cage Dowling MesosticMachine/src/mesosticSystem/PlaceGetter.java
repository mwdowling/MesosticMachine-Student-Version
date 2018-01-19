package mesosticSystem;

/**
 * @author Martin Dowling
 * 
 * This object extracts words from the target chapter that:
 * 
 * (a) begins with an upper class letter
 * (b) is not a word following a word ending with a full stop
 * (c) does not match a word in a "not place" repository
 * 
 * and appends the word with its array index value to
 * the "place" word repository 
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class PlaceGetter implements Igetter {

	// input variables for the chapter
	private String chapterFileAddress;
	private String chapterFile;
	private String[] chapterArray;

	// variables for the repository of words that ARE NOT places
	private String notPlaceFileAddress;
	private String notPlaceFile;
	private ArrayList<String> notPlacesList;

	// output variables
	private String chapterPlacesFileAddress;
	private String chapterPlacesSentencesFileAddress;

	public PlaceGetter(String chapterFileAddress, String notPlaceFileAddress, String chapterPlacesFileAddress,
			String chapterPlacesSentencesFileAddress) throws IOException {

		this.chapterFileAddress = chapterFileAddress;
		this.chapterFile = new String(Files.readAllBytes(Paths.get(chapterFileAddress)));
		this.chapterArray = chapterFile.split("\\s+");

		this.notPlaceFileAddress = notPlaceFileAddress;
		this.notPlaceFile = new String(Files.readAllBytes(Paths.get(notPlaceFileAddress)));
		this.notPlacesList = new ArrayList<>(Arrays.asList(notPlaceFile.split("\\r\n")));

		this.chapterPlacesFileAddress = chapterPlacesFileAddress;
		this.chapterPlacesSentencesFileAddress = chapterPlacesSentencesFileAddress;
	}

	// overridden method to find words beginning with an uppercase letter
	@Override
	public void wordGetter() throws IOException {

		// Place the next word in the chapter in a treemap if first leter is
		// uppercase
		Map<Integer, String> placeWordMap = new TreeMap<>();
		for (int i = 1; i < chapterArray.length; i++) {

			if (Character.isUpperCase(chapterArray[i].charAt(0)) && !chapterArray[i - 1].contains(".")) {
				// remove punctuation an put into the TreeMap
				placeWordMap.put(i, chapterArray[i].replaceAll("\\W", "").trim());

			} // end of if statement
		} // end of for loop

		// Remove word from treemap if it matches a word in the notPlacesList
		Map<Integer, String> placeWordMapCopy = new TreeMap<>();
		placeWordMapCopy.putAll(placeWordMap);

		for (String value : placeWordMapCopy.values()) {

			if (notPlacesList.contains(value.toLowerCase().trim())) {

				for (Entry<Integer, String> entry : placeWordMapCopy.entrySet()) {

					if (entry.getValue().equals(value)) {
						int key = entry.getKey();
						placeWordMap.remove(key, value);
					} // end of inner if statement
				} // end of inner for loop
			} // end of outer if statement
		} // end of outer for loop

		// Write the Treemap to the output file
		for (Entry<Integer, String> entry : placeWordMap.entrySet()) {

			int key = entry.getKey();
			String value = entry.getValue();
			System.out.printf("%s : %s\n", key, value);
			String line = key + "\t" + value;
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(chapterPlacesFileAddress), true));

			bw.write(line);
			bw.newLine();
			bw.close();
		} // end of enhanced for loop through TreeMap

	}

	// overridden method to find the entire sentence containing words beginning with an uppercase letter
	@Override
	public void sentenceGetter() throws IOException {

		// read an reformat the first line of the places file created by
		// wordGetter
		BufferedReader br = new BufferedReader(new FileReader(new File(chapterPlacesFileAddress)));
		String line = br.readLine();
		String[] lineArray = line.split("\t");
		Integer lineArrayInt = new Integer(lineArray[0]);

		// variables to identify the boundaries of a sentence
		int sentenceStart = 0;
		int sentenceEnd = 0;
		String fullStop = ".";

		// read each line of the file created by wordGetter
		for (line = br.readLine(); line != null; line = br.readLine()) {
			String sentencetoFind = "";
			// loop to find and assign the last word of the sentence
			for (int i1 = lineArrayInt; i1 < chapterArray.length; i1++) {

				if (chapterArray[i1].contains(fullStop)) {
					sentenceEnd = i1;
					break;
				}
			}

			// loop to find and assign the first word of the sentence
			for (int i1 = lineArrayInt; i1 >= 0; i1--) {

				if (chapterArray[i1].contains(fullStop)) {
					sentenceStart = i1 + 1;
					break;
				}
			}

			// loop through the words in the sentence and combine in a String
			for (int i1 = sentenceStart; i1 <= sentenceEnd; i1++) {

				sentencetoFind = sentencetoFind + " " + chapterArray[i1].trim();
			}

			// write sentence to output file
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(chapterPlacesSentencesFileAddress), true));
			if (sentencetoFind != "") {
				bw.newLine();
				bw.write(lineArray[0]);
				bw.newLine();
				bw.write(sentencetoFind.trim());
				bw.newLine();
			}
			bw.close();

			// advance and reformat the line
			line = br.readLine();
			lineArray = line.split("\t");
			lineArrayInt = new Integer(lineArray[0]);
			if (lineArrayInt < sentenceEnd) {
				lineArrayInt = sentenceEnd;
			}
		}
		br.close();
	}
}
