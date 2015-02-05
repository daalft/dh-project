package preface.parser.element.coreference;

import java.util.ArrayList;
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
	private int id; //ID from coreference. IS NOT UNIQUE! Only valid in combination with chapterNumber!
	private int chapterNumber;//ChapterNumber from xml file

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

	public void setChapterNumber(int ChptNr){
		this.chapterNumber= ChptNr;
	}

	public int getChapterNumber(){
		return chapterNumber;
	}

	public void setId (int id) {
		this.id = id;
	}

	public int getId () {
		return id;
	}

	public void add (Mention m) {
		mentions.add(m);
		if (m.isRepresentative())
			representativeMention = m.getTextMention();
	}

	public void setType(NEType type) {
		this.type = type;
	}

	public String toString () {
		StringBuilder sb = new StringBuilder();
		sb.append("\n Chapter ID: "+ chapterNumber);
		sb.append("\nCoreference ID: " + id);
		sb.append("\n\n");
		sb.append("\n[Entity]\ntype:" + type);
		for (Mention m : this) {
			sb.append("\n"+m.toString());
		}
		return sb.toString();
	}
}
