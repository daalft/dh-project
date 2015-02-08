package preface.analysis.result;

import java.util.HashMap;
import java.util.Map.Entry;

import preface.parser.element.text.AnnotatedWord;
/**
 * Represents a frequency table that maps a word to its frequency
 * @author David
 *
 */
public class FrequencyTable {

	/**
	 * Frequency map
	 */
	private HashMap<AnnotatedWord, Integer> map;

	/**
	 * Constructor
	 */
	public FrequencyTable() {
		map = new HashMap<AnnotatedWord, Integer>();
	}
	
	/**
	 * Adds a word to this frequency table.
	 * <p>
	 * If specified word exists, its frequency is raised by 1.
	 * Otherwise the word is added.
	 * @param w word to add
	 */
	public void add(AnnotatedWord w) {
		if (map.containsKey(w)) {
			map.put(w, map.get(w)+1);
		} else {
			map.put(w, 1);
		}
	}
	
	/**
	 * toString
	 */
	public String toString () {
		StringBuilder sb = new StringBuilder("[Frequency table]:\n");
		for (Entry<AnnotatedWord, Integer> e : map.entrySet()) {
			sb.append(e.getKey().toString()).append("\t").append(e.getValue().intValue()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Converts this frequency table to partial JSON String
	 * @return partial JSON String
	 */
	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		for (Entry<AnnotatedWord, Integer> e : map.entrySet()) {
			sb.append("{").append(e.getKey().toJSON()).append(",\n\"frequency\":").append(e.getValue().intValue()).append("},\n");
		}
		sb.deleteCharAt(sb.length()-2);
		return sb.toString();
	}
}
