package preface.parser.element.coreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import preface.parser.element.NEType;

/**
 * Represents an entity (i.e. a list of coreferent mentions)
 * @author David
 *
 */
public class Entity implements Iterable<Mention> {
	/**
	 * Representative mention
	 */
	private String representativeMention;
	/**
	 * Mentions
	 */
	private List<Mention> mentions;
	private NEType type;
	
	public Entity () {
		mentions = new ArrayList<Mention>();
	}
	
	public Entity (String representative, NEType type) {
		this();
		this.representativeMention = representative;
		this.type = type;
	}
	
	@Override
	public Iterator<Mention> iterator() {
		return mentions.iterator();
	}
	
	public String getRepresentativeMention () {
		return representativeMention;
	}
	
	public NEType getType () {
		return type;
	}
}
