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

	private static int counter;
	private int uniqueID;
	private String representativeMention;
	private Map<Integer, List<Mention>> map;
	private NEType type;

	public BookEntity() {
		uniqueID = counter++;
		map = new HashMap<Integer, List<Mention>>();
	}

	public void setRepresentativeMention (String mention) {
		representativeMention = mention;
	}

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

	public int getUniqueID () {
		return uniqueID;
	}

	public void setType (NEType type) {
		this.type = type;
	}

	public String getRepresentativeMention () {
		return representativeMention;
	}

	public List<Mention> getMentionsByChapter (int chapter) {
		if (!map.containsKey(chapter)) {
			return null;
		}
		return map.get(chapter);
	}

	public Map<Integer, List<Mention>> getMap () {
		return map;
	}

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
	
	public String toJSON () {
		return "\"name\":\""+representativeMention+"\",\n\"id\":"+uniqueID;
	}
}
