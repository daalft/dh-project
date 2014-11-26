package parser.element.text;

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
	
}
