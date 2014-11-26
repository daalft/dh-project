package parser.element.coreference;

import java.util.Iterator;
import java.util.List;

import parser.element.NEType;

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
	
	/**
	 * Merges this Entity with another given Entity
	 * <br/>
	 * May change the representative mention
	 * @param other other Entity to merge with
	 * @return merged Entity
	 */
	public Entity merge (Entity other) {
		return other;
		
	}
}
