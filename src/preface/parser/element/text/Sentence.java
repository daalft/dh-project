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
	
	public Sentence(int i) {
		this();
		this.sentenceNumber = i;
	}
	
	@Override
	public Iterator<AnnotatedWord> iterator() {
		return words.iterator();
	}
	
	public int getSentenceNumber () {
		return sentenceNumber;
	}
	
	public AnnotatedWord getWord (int position) {
		// Position is 1-indexed, word list is zero-indexed
		return words.get(position-1);
	}
	
	public List<AnnotatedWord> getWords () {
		return words;
	}
	
	public void add(AnnotatedWord w) {
		words.add(w);
	}
	
	public void setSentenceNumber (int n) {
		sentenceNumber = n;
	}
	
	@Override
	public String toString() {
		return "[sentence num:" + sentenceNumber + "] " + words.toString();
	}
}
