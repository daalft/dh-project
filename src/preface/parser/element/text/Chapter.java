package preface.parser.element.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Represents a chapter from a book consisting of sentences
 * @author s2daalft
 *
 */
public class Chapter implements Iterable<Sentence>, Comparable<Chapter> {

	private List<Sentence> sentences;
	private int chapterNumber;

	public Chapter() {
		sentences = new ArrayList<>();
	}
	
	@Override
	public Iterator<Sentence> iterator() {
		return sentences.iterator();
	}
	
	public int getChapterNumber () {
		return chapterNumber;
	}

	public void setChapterNumber(int i) {
		chapterNumber = i;
	}

	public Sentence getSentence (int position) {
		return sentences.get(position-1);
	}
	
	public List<Sentence> getSentences () {
		return sentences;
	}
	
	public void add(Sentence s) {
		sentences.add(s);
	}

	@Override
	public int compareTo(Chapter o) {
		if (this.chapterNumber > o.chapterNumber)
			return 1;
		if (this.chapterNumber < o.chapterNumber)
			return -1;
		return 0;
	}
}
