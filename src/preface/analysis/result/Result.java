package preface.analysis.result;

import java.util.HashMap;
import preface.parser.element.text.AnnotatedWord;

public class Result {

	/**
	 * Links between named entites
	 */
	private HashMap<AnnotatedWord, Integer> links;
	
	/**
	 * Chapter -> (word->frequency)
	 */
	private HashMap<Integer, FrequencyTable> words;
	
	public Result() {
		links = new HashMap<>();
		words = new HashMap<>();
	}
	
	public void link(AnnotatedWord w) {
		if (links.containsKey(w)) {
			links.put(w, links.get(w)+1);
		} else {
			links.put(w, 1);
		}
	}

	public void add(AnnotatedWord w, int chapter) {
		if (words.containsKey(chapter)) {
			words.get(chapter).add(w);
		} else {
			FrequencyTable ft = new FrequencyTable();
			ft.add(w);
			words.put(chapter, ft);
		}
	}

	public boolean isEmpty() {
		return (links.isEmpty() && words.isEmpty());
	}
}
