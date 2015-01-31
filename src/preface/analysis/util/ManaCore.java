package preface.analysis.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import preface.parser.element.coreference.Chain;
import preface.parser.element.coreference.Entity;
import preface.parser.element.coreference.EntityReference;
import preface.parser.element.coreference.Mention;

/**
 * MANACORE - MANAger and CORrelator for Entities
 * <p>
 * Holds information about entities and entity references
 * and allows look up of combined information
 * @author David
 *
 */
public class ManaCore {

	private List<Chain> chains;
	private List<Entity> entities;
	private List<BookEntity> su;
	private Map<Entity, List<Integer>> entityChapterMap;
	
	public ManaCore() {
		chains = new ArrayList<>();
		entities = new ArrayList<>();
		su = new ArrayList<>();
		entityChapterMap = new HashMap<Entity, List<Integer>>();
	}
	
	public ManaCore(List<Chain> chains, List<Entity> entities) {
		this();
		this.chains = chains;
		this.entities = entities;
	}

	/**
	 * Correlates the given information about
	 * entities and entity references. Does nothing
	 * if no entities or references have been provided.
	 * @return true on success
	 */
	public boolean correlate () {
		if (chains == null || entities == null)
			return false;
		buildBookEntities();
		buildOccursInIndex();
		return true;
	}
	
	// TODO rework 
	private boolean buildBookEntities () {
		for (Chain chain : chains) {
			BookEntity se = new BookEntity();
			se.setRepresentativeMention(chain.getText());
			for (EntityReference ref : chain) {
				for (Entity e : entities) {
					if ((ref.getChapterNumber() == e.getChapterNumber()) && (ref.getId() == e.getId())) {
						for (Mention m : e) {
							se.map(ref.getChapterNumber(), m.clone());
						}
					}
				}
			}
			if (!su.contains(se))
				su.add(se);
		}
		return true;
	}
	
	private boolean buildOccursInIndex() {
		for (Entity e : entities) {
			int chapNum = e.getChapterNumber();
			if (entityChapterMap.containsKey(e)) {
				entityChapterMap.get(e).add(chapNum);
			} else {
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(chapNum);
				entityChapterMap.put(e, list);
			}
		}
		return true;
	}
	
	public boolean occursInChapter (Entity e, int chapterNumber) {
		if (!entityChapterMap.containsKey(e))
			return false;
		return entityChapterMap.get(e).contains(chapterNumber);
	}

}

/**
 * Represents an entity in a book, as opposed to
 * an entity inside a chapter
 * 
 * @author David
 *
 */
class BookEntity {
	
	private static int counter;
	private int uniqueID;
	private String representativeMention;
	private Map<Integer, List<Mention>> map;
	
	public BookEntity() {
		uniqueID = counter++;
	}
	
	void setRepresentativeMention (String mention) {
		representativeMention = mention;
	}
		
	void map (int chap, Mention m) {
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
}
