package preface.parser.element.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Represents a book made up of chapters.
 * @author David
 *
 */
public class Text implements Iterable<Chapter> {
	
	private List<Chapter> chapters;

	public Text() {
		chapters = new ArrayList<>();
	}
	
	@Override
	public Iterator<Chapter> iterator() {
		return chapters.iterator();
	}
	
	public List<Chapter> getChapters () {
		return chapters;
	}
	
	public Chapter getChapter (int chapter) {
		// Chapter is 1-indexed, chapter list is zero-indexed
		return chapters.get(chapter-1);
	}

	public void add(Chapter chapter) {
		chapters.add(chapter);
	}
}
