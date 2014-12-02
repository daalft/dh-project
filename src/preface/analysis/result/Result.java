package preface.analysis.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import preface.parser.element.text.AnnotatedWord;

public class Result {

	/**
	 * Links between named entites
	 */
	private List<AnnotatedWord> links;
	/**
	 * Chapter -> (word->frequency)
	 */
	private HashMap<Integer, FrequencyTable> words;
	
	public Result() {
		links = new ArrayList<>();
		words = new HashMap<>();
	}
	
	public void link(AnnotatedWord w) {
		links.add(w);
	}

	public void add(AnnotatedWord w, int chapter) {
		if (words.containsKey(chapter)) {
			words.get(chapter).add(w);
		}
	}

	public boolean isEmpty() {
		return (links.isEmpty() && words.isEmpty());
	}
}
