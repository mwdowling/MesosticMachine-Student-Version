package mesosticSystem;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Igetter {

	public void wordGetter() throws IOException;
	
	public void sentenceGetter() throws FileNotFoundException, IOException; 
	
}
