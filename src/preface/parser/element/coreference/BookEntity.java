package preface.parser.element.coreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import preface.parser.element.NEType;

/**
 * Represents an entity in a book, as opposed to
 * an entity inside a chapter
 * 
 * @author David
 *
 */
public class BookEntity {

	/**
	 * ID counter
	 */
	private static int counter;
	/**
	 * Unique ID
	 */
	private int uniqueID;
	/**
	 * Representative mention
	 */
	private String representativeMention;
	/**
	 * Map of chapter-mentions
	 */
	private Map<Integer, List<Mention>> map;
	/**
	 * NE type
	 */
	private NEType type;

	/**
	 * Constructor
	 */
	public BookEntity() {
		// auto id
		uniqueID = counter++;
		map = new HashMap<Integer, List<Mention>>();
	}

	/**
	 * Sets the representative mention
	 * @param mention mention
	 */
	public void setRepresentativeMention (String mention) {
		representativeMention = mention;
	}

	/**
	 * Adds a chapter-mention to this entity
	 * @param chap chapter
	 * @param m mention
	 */
	public void map (int chap, Mention m) {
		if (map.containsKey(chap)) {
			List<Mention> list = map.get(chap);
			if (!list.contains(m))
				list.add(m.clone());
		} else {
			List<Mention> list = new ArrayList<Mention>();
			list.add(m.clone());
			map.put(chap, list);
		}
	}

	/**
	 * Returns uniqueID
	 * @return unique ID
	 */
	public int getUniqueID () {
		return uniqueID;
	}

	/**
	 * Sets this NE type
	 * @param type NE type
	 */
	public void setType (NEType type) {
		this.type = type;
	}

	/**
	 * Returns the representative mention
	 * @return representative mention
	 */
	public String getRepresentativeMention () {
		return representativeMention;
	}

	/**
	 * Returns all the mentions in a given chapter
	 * @param chapter chapter
	 * @return mentions in chapter
	 */
	public List<Mention> getMentionsByChapter (int chapter) {
		if (!map.containsKey(chapter)) {
			return null;
		}
		return map.get(chapter);
	}

	/**
	 * Returns this map
	 * @return map
	 */
	public Map<Integer, List<Mention>> getMap () {
		return map;
	}

	/**
	 * Returns this NE type
	 * @return NE type
	 */
	public NEType getType () {
		return type;
	}
	
	@Override
	public boolean equals (Object other) {
		BookEntity be = (BookEntity) other;
		return this.representativeMention.equals(be.representativeMention);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(representativeMention).append("]\n");
		for (Entry<Integer, List<Mention>> e : map.entrySet()) {
			sb.append("\tChapter " + e.getKey() + "\n");
			for (Mention m : e.getValue())
				sb.append("\t\t").append(m).append("\n");
			
		}
		sb.append("\n");
		return sb.toString();
	}
	
	/**
	 * Returns a partial JSON String representation
	 * @return partial JSON String
	 */
	public String toJSON () {
		return "\"name\":\""+representativeMention+"\",\n\"id\":"+uniqueID;
	}
}
