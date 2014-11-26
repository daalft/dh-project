package parser.element.coreference;

import java.util.ArrayList;
import java.util.Collection;
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
	
	/**
	 * Merges this Entity with another given Entity
	 * <br/><br/>
	 * If the representative mentions differ, <code>this</code> 
	 * representative mention is retained.<br/><br/>
	 * If the NE types differ, <code>this</code> 
	 * NE type is retained.
	 * @param other other Entity to merge with
	 * @return merged Entity
	 */
	public Entity merge (Entity other) {
		Entity copy = new Entity(this.representativeMention, this.type);
		for (Mention m : this) {
			copy.add(m);
		}
		for (Mention m : other) {
			if (!copy.contains(m))
				copy.add(m);
		}
		return copy;
	}

	/**
	 * Merges all the given entities with this entity
	 * <br/><br/>
	 * If the representative mentions differ, the representative
	 * mention is recalculated on the basis of the majority vote
	 * (i.e. the representative mention occurring most often is 
	 * chosen as the new representative mention)
	 * <br/><br/>
	 * If the NE types differ, the NE type is recalculated on the
	 * basis of the majority vote (i.e. the NE type occurring most
	 * often is chosen as the new NE type)
	 * @param others
	 * @return
	 */
	public Entity mergeAll (Collection<Entity> others) {
		Entity copy = new Entity();
		// TODO 
		return copy;
	}
	
	private void add(Mention m) {
		this.mentions.add(m.clone());
	}

	private boolean contains(Mention m) {
		return this.mentions.contains(m);
	}
	
	
}
