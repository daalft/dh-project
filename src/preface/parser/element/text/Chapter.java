package preface.parser.element.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Represents a chapter from a book consisting of paragraphs
 * @author s2daalft
 *
 */
public class Chapter implements Iterable<Paragraph> {

	private List<Paragraph> paragraphs;
	private int chapterNumber;

	public Chapter() {
		paragraphs = new ArrayList<>();
	}
	
	@Override
	public Iterator<Paragraph> iterator() {
		return paragraphs.iterator();
	}
	
	public int getChapterNumber () {
		return chapterNumber;
	}

	public List<Paragraph> getParagraphs () {
		return paragraphs;
	}
}
