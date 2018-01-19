package mesosticSystem;

/**
 * @author Martin Dowling
 * 
 * This object extracts words from the target chapter 
 * match words is a sound repository
 * and appends those words with its array index value to
 * the "sound" word repository 
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

public class SoundGetter implements Igetter {

	//input variables for target chapter
	private String chapterFileAddress;
	private String chapterFile;
	private String[] chapterArray;

	// variables for sounds repository
	private String soundFileAddress;
	private String soundFile;
	private String[] soundArray;

	// variables for output files
	private String chapterSoundsFileAddress;
	private String chapterSoundsSentencesFileAddress;

	// variable for finding sentences
	private int keepIndex = 0;

	// constructor
	public SoundGetter(String chapterFileAddress, String soundFileAddress, String chapterSoundsFileAddress,
			String chapterSoundsSentencesFileAddress) throws IOException {

		this.chapterFileAddress = chapterFileAddress;
		this.chapterFile = new String(Files.readAllBytes(Paths.get(chapterFileAddress)));
		this.chapterArray = chapterFile.split("\\s+");

		this.soundFileAddress = soundFileAddress;
		this.soundFile = new String(Files.readAllBytes(Paths.get(soundFileAddress)));
		this.soundArray = soundFile.split("\t");

		this.chapterSoundsFileAddress = chapterSoundsFileAddress;
		this.chapterSoundsSentencesFileAddress = chapterSoundsSentencesFileAddress;

	}
	// overridden method to find words matching a word in the sound repository
	@Override
	public void wordGetter() throws IOException {
	
		// for loop to get words that match words in sounds file
		for (int i = 0; i < chapterArray.length; i++) {

			String wordChapter = chapterArray[i].toLowerCase().replaceAll("\\W", "").trim();
			String wordChapterKeep = "";

			for (int j = 0; j < soundArray.length; j++) {

				String wordSound = soundArray[j];

				if (wordChapter.startsWith(wordSound)) {

					wordChapterKeep = wordChapter;
					keepIndex = i;
					// check validity of wordSound
					System.out.println(i + "\t" + wordChapter + "\t" + wordSound);
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(chapterSoundsFileAddress), true));
					bw.write(i + "\t" + wordChapter + "\t" + wordSound);
					// adding "\r" to this string gets rid of the exception but puts in miles of whitespace
					// need to write a file with just the found word and its index also 
					bw.newLine();
					bw.close();

				}
			} // end of soundArray loop
		} // end of chapterArray loop
	}
	
	// overridden method to find the entire sentence containing words that match the sound repository
	@Override
	public void sentenceGetter() throws FileNotFoundException, IOException {

		BufferedReader br = new BufferedReader(new FileReader(new File(chapterSoundsFileAddress)));
		String line = br.readLine();
		String[] lineArray = line.split("\t");
		Integer lineArrayInt;
		lineArrayInt = new Integer(lineArray[0]);

		// for loop to get sentences
		for (int i = 0; i < chapterArray.length; i++) {

			String fullStop = ".";
			int sentenceStart = 0;
			int sentenceEnd = 0;
		
			System.out.println(lineArrayInt);
			for (line = br.readLine(); line != null; line = br.readLine()) {

				// loop to find the end of the sentence
				for (int i1 = lineArrayInt; i1 < chapterArray.length; i1++) {

					if (chapterArray[i1].contains(fullStop)) {
						sentenceEnd = i1;
						break;
					}
				}

				// loop to find the beginning of the sentence
				for (int i1 = lineArrayInt; i1 >= 0; i1--) {

					if (chapterArray[i1].contains(fullStop)) {
						sentenceStart = i1 + 1;
						break;
					}
				}

				// loop through the words in the sentence and combine in a
				// String
				String sentencetoFind = "";
				for (int i1 = sentenceStart; i1 <= sentenceEnd; i1++) {

					sentencetoFind = sentencetoFind + " " + chapterArray[i1].trim();
				}

				if (sentencetoFind != "") {
					System.out.println(sentencetoFind.trim());
				
					BufferedWriter bw = new BufferedWriter(
						new FileWriter(new File(chapterSoundsSentencesFileAddress), true));

					bw.newLine();
					bw.write(lineArray[0]);
					bw.newLine();
					bw.write(sentencetoFind.trim());
					bw.newLine();
					bw.close();
				}
				

				line = br.readLine();			
				lineArray = line.split("\t");
				lineArrayInt = new Integer(lineArray[0]);
				
				if (lineArrayInt < sentenceEnd) {
					lineArrayInt = sentenceEnd;

				}
			} 
		}br.close();
	}
}
