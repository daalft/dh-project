package preface.parser.element.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Represents a paragraph in a text consisting of sentences.
 * @author David
 *
 */
public class Paragraph implements Iterable<Sentence> {

	private List<Sentence> sentences;
	
	public Paragraph() {
		sentences = new ArrayList<>();
	}
	
	public Sentence getSentence (int position) {
		return sentences.get(position);
	}
	
	public List<Sentence> getSentences () {
		return sentences;
	}

	@Override
	public Iterator<Sentence> iterator() {
		return sentences.iterator();
	}
	
	public void add(Sentence s) {
		sentences.add(s);
	}
}
