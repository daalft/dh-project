package parser.element.coreference;

import java.util.Iterator;
import java.util.List;

public class Entity implements Iterable<Mention> {

	private String representativeMention;
	private List<Mention> mentions;
	
	@Override
	public Iterator<Mention> iterator() {
		return mentions.iterator();
	}
	
	public String getRepresentativeMention () {
		return representativeMention;
	}
}
