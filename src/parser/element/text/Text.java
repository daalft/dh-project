package parser.element.text;

import java.util.Iterator;
import java.util.List;

public class Text implements Iterable<Chapter> {

	private List<Chapter> chapters;

	@Override
	public Iterator<Chapter> iterator() {
		return chapters.iterator();
	}
	
	
}
