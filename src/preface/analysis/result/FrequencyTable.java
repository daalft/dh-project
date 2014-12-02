package preface.analysis.result;

import java.util.HashMap;

import preface.parser.element.text.AnnotatedWord;

public class FrequencyTable {

	private HashMap<AnnotatedWord, Integer> map;

	public void add(AnnotatedWord w) {
		if (map.containsKey(w)) {
			map.put(w, map.get(w)+1);
		} else {
			map.put(w, 1);
		}
	}
	
}
