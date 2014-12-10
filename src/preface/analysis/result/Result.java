package preface.analysis.result;

import java.util.HashMap;
import java.util.Map.Entry;

import preface.parser.element.coreference.Entity;
import preface.parser.element.text.AnnotatedWord;

public class Result {

	/**
	 * Links between named entites
	 */
	private HashMap<Entity, Integer> links;
	
	/**
	 * Chapter -> (word->frequency)
	 */
	private HashMap<Integer, FrequencyTable> words;
	
	public Result() {
		links = new HashMap<>();
		words = new HashMap<>();
	}
	
	public void link(Entity e) {
		if (links.containsKey(e)) {
			links.put(e, links.get(e)+1);
		} else {
			links.put(e, 1);
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
	
	public String toString () {
		StringBuilder sb = new StringBuilder("[Result]:\n");
		sb.append("Links:\n");
		for (Entry<Entity, Integer> link : links.entrySet()) {
			sb.append(link.getKey().toString()).append("\t").append(link.getValue().intValue()).append("\n");
		}
		sb.append("\n\nChapters:\n");
		for (Entry<Integer, FrequencyTable> e : words.entrySet()) {
			sb.append("Chapter ").append(e.getKey().intValue()).append("\n");
			sb.append(e.getValue().toString()).append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
}
