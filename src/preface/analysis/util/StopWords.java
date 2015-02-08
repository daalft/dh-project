package preface.analysis.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Stop word filtering utility class
 * @author David
 *
 */
public class StopWords {

	/**
	 * Stop word list
	 */
	List<String> words;
	
	/**
	 * Constructor
	 */
	public StopWords() {
		words = new ArrayList<>();
		try {
			initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializer
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		File defaultFile = new File("./data/stopwords.in");
		if (defaultFile.canRead()) {
			BufferedReader br = new BufferedReader(new FileReader(defaultFile));
			String l = "";
			while((l=br.readLine())!=null) {
				words.add(l.trim());
			}
			br.close();
		} else {
			// file does not exist or is not readable
			System.err.println("Default stopword file could not be opened!");
		}
	}
	
	/**
	 * Checks whether the given word is a stop word
	 * @param word word to check
	 * @return true if word is stop word
	 */
	public boolean isStopword (String word) {
		return words.contains(word);
	}
}
