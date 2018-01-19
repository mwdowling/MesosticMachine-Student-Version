package mesosticSystem;


/**
 * @author Martin Dowling 
 * 
 * Information for writing this class taken from: http://toolsqa.com/selenium-webdriver/webelement-commands/
 * 
 * This class permits the creation of an object that 
 * (1) takes a word from target text as parameter
 * (2) conducts a website search to find the syllabic division of the word
 * (3) identifies and returns the appropriate syllable
 * (4) if the target website does not provide a syllable division, 
 * 	   saves the whole word and prints a message
 */

import java.io.IOException;



import java.nio.file.Files;
import java.nio.file.Paths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SyllableWebSearcher {

	// input variable for file path of mesostic row
	String mesosticRowFileAddress;

	// input variable for the mesostic row target letter
	private String thisMesosticLetter;

	// instance variable for target word 
	private String word;

	// instance variable for web driver
	WebDriver driver;

	//constructor
	public SyllableWebSearcher(String word, String mesosticRowFileAddress, int mesosticRowArrayIndex)
			throws IOException {

		this.mesosticRowFileAddress = mesosticRowFileAddress;
		String mesosticRow = new String(Files.readAllBytes(Paths.get(mesosticRowFileAddress)));
		String[] mesosticRowArray = mesosticRow.split("");
		this.thisMesosticLetter = mesosticRowArray[mesosticRowArrayIndex];
		
		this.word = word;

		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\Martin\\Documents\\Java Libraries\\geckodriver-v0.17.0-win64\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		
	}
	
	//a method to return a syllable saved from www.howmanysyllables.com
	public String SyllableSaver() throws InterruptedException {

		/* 
		 * Set the "geckodriver.exe", the engine linking Selenium and Firefox,
		 * create a new instance of the Firefox driver,
		 * and get the syllable website
		 */
		driver.get("http://www.howmanysyllables.com/");

		// Get word divided into syllables from website
		WebElement input = driver.findElement(By.name("SearchQuery_FromUser"));
		input.sendKeys(word);
		WebElement submit = driver.findElement(By.id("SearchDictionary_Button"));
		submit.click();

		/*
		 * On some pages of the website there is no syllable division given 
		 * and the following line throws: 
		 * 
		 * "Exception in thread main org.openqa.selenium.NoSuchElementException: 
		 * Unable to locate element: .Answer_Red"
		 * 
		 * If this exception is thrown,
		 * the word will be saved to repository AS IS
		 * and the repository must be edited manually by the user 
		 * to record the correct syllable used. 
		 */
		WebElement result;
		String[] wordAsSyllables = new String[1];
		try {
			result = driver.findElement(By.id("SyllableContentContainer")).findElement(By.className("Answer_Red"));

			// divide the output String into an array of syllables
			wordAsSyllables = result.getText().split("-");
		} catch (Exception ex) {
			System.out.println("Syllable division not found. Saving whole word as one syllable");
			wordAsSyllables[0] = word;
		}

		// save syllable that contains the target letter
		String savedSyllable = wordAsSyllables[0];
		for (int i = 0; i < wordAsSyllables.length; i++) {

			if (wordAsSyllables[i].contains(thisMesosticLetter)) {
				savedSyllable = wordAsSyllables[i].trim();
			}
			
		}
		
		driver.quit();
		return savedSyllable;
		
	}
}
