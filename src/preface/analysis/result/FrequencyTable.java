package preface.analysis.result;

import java.util.HashMap;
import java.util.Map.Entry;

import preface.parser.element.text.AnnotatedWord;

public class FrequencyTable {

	private HashMap<AnnotatedWord, Integer> map;

	public FrequencyTable() {
		map = new HashMap<AnnotatedWord, Integer>();
	}
	
	public void add(AnnotatedWord w) {
		if (map.containsKey(w)) {
			map.put(w, map.get(w)+1);
		} else {
			map.put(w, 1);
		}
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder("[Frequency table]:\n");
		for (Entry<AnnotatedWord, Integer> e : map.entrySet()) {
			sb.append(e.getKey().toString()).append("\t").append(e.getValue().intValue()).append("\n");
		}
		return sb.toString();
	}
}
