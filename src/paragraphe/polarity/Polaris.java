package paragraphe.polarity;

import preface.parser.element.text.interfaces.Polariseable;

import java.util.*;
import java.io.*;
/**
 * Provides polarity information and polarity annotation.
 * @author David
 *
 */
public class Polaris {


	/**
	 * Debug level
	 */
	private byte debugLevel = 0;
	/**
	 * Polarity map
	 */
	private HashMap<String, Double> map;
	
	/**
	 * Constructor
	 */
	public Polaris () {
		map = new HashMap<>();
		try {
			initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializer
	 * <p>
	 * Loads the default polarity file (./data/polarity.pi)
	 * @throws IOException
	 */
	private void initialize () throws IOException {
		if (debugLevel > 5)
			System.err.println("Loading Polaris...");
		File defaultFile = new File("./data/polarity.pi");
		if (defaultFile.canRead()) {
			BufferedReader br = new BufferedReader(new FileReader(defaultFile));
			String l = "";
			while ((l=br.readLine())!=null) {
				String[] sp = l.split("\t");
				double d = 0;
				try {
					d = Double.parseDouble(sp[1]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				map.put(sp[0], d);
			}
			br.close();
			
		} else {
			// either file does not exist or is not readable
			System.err.println("Default polarity file could not be opened!");
		}
		if (debugLevel > 5)
			System.err.println("Finished loading Polaris.");
	}
	
	/**
	 * Annotates a word with polarity if information is present.
	 * <p>
	 * If no information about given word is present, word is 
	 * returned without annotation.
	 * @param word word to annotate
	 * @return word with annotation
	 */
	public <T extends Polariseable> T annotate (T word) {
		if (debugLevel > 7)
			System.err.println("Starting annotation of " + word.getString());
		String w = word.getString();
		double previousPolarity = 0;
		if (word.isPolarized()) 
			previousPolarity = word.getPolarity();
		double currentPolarity = 0;
		if (map.get(w)!=null) {
			currentPolarity = map.get(w);
		} else { 
			if (debugLevel > 9)
				System.err.println("Premature return. No annotation of " + w);
			// premature return:
			// we do not have the word in the word list
			// therefore we cannot annotate the word with 
			// a polarity, which corresponds to 
			// no annotation at all
			return word;
		}		
		word.setPolarity((previousPolarity+currentPolarity)/2);
		return word;
	}
}
