package paragraphe.polarity;

import preface.parser.element.text.interfaces.Polariseable;

import java.util.*;
import java.io.*;

public class Polaris {

	private byte debug = 0;
	private HashMap<String, Double> map;
	
	public Polaris () {
		map = new HashMap<>();
		try {
			initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize () throws IOException {
		if (debug > 5)
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
		if (debug > 5)
			System.err.println("Finished loading Polaris.");
	}
	
	public <T extends Polariseable> T annotate (T word) {
		if (debug > 7)
			System.err.println("Starting annotation of " + word.getString());
		String w = word.getString();
		double previousPolarity = 0;
		if (word.isPolarized()) 
			previousPolarity = word.getPolarity();
		double currentPolarity = 0;
		if (map.get(w)!=null) {
			currentPolarity = map.get(w);
			System.out.println("polarity " + w + " " + currentPolarity);
		} else { 
			if (debug > 9)
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
