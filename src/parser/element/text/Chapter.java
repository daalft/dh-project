package parser.element.text;

import java.util.Iterator;
import java.util.List;

public class Chapter implements Iterable<Paragraph> {

	private List<Paragraph> paragraphs;
	private int chapterNumber;
	
	@Override
	public Iterator<Paragraph> iterator() {
		return paragraphs.iterator();
	}
	
	public int getChapterNumber () {
		return chapterNumber;
	}

}
