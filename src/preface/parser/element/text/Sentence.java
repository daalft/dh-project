package preface.parser.element.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Represents a sentence consisting of annotated words.
 * @author David
 *
 */
public class Sentence implements Iterable<AnnotatedWord> {

	private List<AnnotatedWord> words;
	private int sentenceNumber;

	public Sentence() {
		words = new ArrayList<>();
	}
	
	@Override
	public Iterator<AnnotatedWord> iterator() {
		return words.iterator();
	}
	
	public int getSentenceNumber () {
		return sentenceNumber;
	}
	
	public AnnotatedWord getWord (int position) {
		return words.get(position);
	}
	
	public List<AnnotatedWord> getWords () {
		return words;
	}
}
