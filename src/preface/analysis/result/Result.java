package preface.analysis.result;

import java.util.HashMap;
import java.util.Map.Entry;

import preface.parser.element.text.AnnotatedWord;

public class Result {

	/**
	 * Chapter -> (word->frequency)
	 */
	private HashMap<Integer, FrequencyTable> words;
	
	public Result() {
		words = new HashMap<>();
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
		return words.isEmpty();
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder("[Result]:\n");
		sb.append("\n\nChapters:\n");
		for (Entry<Integer, FrequencyTable> e : words.entrySet()) {
			sb.append("Chapter ").append(e.getKey().intValue()).append("\n");
			sb.append(e.getValue().toString()).append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Integer, FrequencyTable> e : words.entrySet()) {
			sb.append("{\"chapter\":").append(e.getKey().intValue()).append(",\"children\":[");
			sb.append(e.getValue().toJSON()).append("]},");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
