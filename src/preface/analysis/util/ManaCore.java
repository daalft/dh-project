package preface.analysis.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import preface.parser.element.NEType;
import preface.parser.element.coreference.BookEntity;
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
	private List<BookEntity> bookEntities;
	private Map<BookEntity, List<Integer>> entityChapterMap;
	private Map<Doublet<Integer, Integer>, Integer> entityNetwork;

	public ManaCore() {
		chains = new ArrayList<>();
		entities = new ArrayList<>();
		bookEntities = new ArrayList<>();
		entityChapterMap = new HashMap<BookEntity, List<Integer>>();
		entityNetwork = new HashMap<Doublet<Integer, Integer>, Integer>();
	}

	public ManaCore(List<Entity> entities, List<Chain> chains) {
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
		System.err.println("Build book entities...");
		buildBookEntities();
		System.err.println("Build Occurs in Index");
		buildOccursInIndex();
		System.err.println("Build entity Network");
		buildEntityNetwork();
		System.err.println("Done.");
		return true;
	}

	private boolean buildBookEntities () {
		for (Chain chain : chains) {
			String mention = extractName(chain.getText());
			if (mention.isEmpty())
				continue;
			StopWords sw = new StopWords();
			if (sw.isStopword(mention.toLowerCase()))
				continue;
			for (EntityReference ref : chain.getEntityReferences()) {
				Entity e = lookup(ref);
				if (e.getType().equals(NEType.PERSON)) {
					BookEntity be = null;
					boolean exists = exists(mention);
					if (exists) {
						//System.err.println(mention);
						//System.err.println(bookEntities);
						be = getEntityByMention(mention);
					} else {
						be = new BookEntity();
						be.setRepresentativeMention(mention);
					}
					for (Mention m : e) {
						int chapNum = ref.getChapterNumber();
						be.map(chapNum, m);
						be.setType(e.getType());
					}
					if (!exists) {
						bookEntities.add(be);
					}
				}
			}
		}
		return true;
	}

	private BookEntity getEntityByMention(String mention) {
		for (BookEntity be : bookEntities) {
			if (be.getRepresentativeMention().equals(mention))
				return be;
		}
		return null;
	}

	private boolean exists(String mention) {
		for (BookEntity be : bookEntities) {
			if (be.getRepresentativeMention().equals(mention))
				return true;
		}
		return false;
	}

	private String extractName(String text) {
		// there are a lot of recurrent names with slight variation
		// => too many nodes in the graph!
		Pattern name = Pattern.compile("([\\s\\w.]+)");
		Matcher m = name.matcher(text);
		if (m.find()) {
			return m.group().trim();
		}
		//System.err.println("No name found in String " + text);
		return "";
	}

	private Entity lookup(EntityReference ref) {
		for (Entity e : entities) {
			if ((ref.getChapterNumber() == e.getChapterNumber()) && (ref.getId() == e.getId())) {
				return e;
			}
		}
		return null;
	}

	private boolean buildOccursInIndex() {
		for (BookEntity e : bookEntities) {
			Set<Integer> chapterNums = e.getMap().keySet();
			for (Integer i : chapterNums) {
				int chapNum = i.intValue();
				if (entityChapterMap.containsKey(e)) {
					entityChapterMap.get(e).add(chapNum);
				} else {
					ArrayList<Integer> list = new ArrayList<Integer>();
					list.add(chapNum);
					entityChapterMap.put(e, list);
				}
			}
		}
		return true;
	}

	private boolean buildEntityNetwork () {
		// require book entities
		if (bookEntities.isEmpty())
			return false;

		for (int i = 0; i < bookEntities.size(); i++) {
			BookEntity be = bookEntities.get(i);
			if (!be.getType().equals(NEType.PERSON))
				continue;
			for (Entry<Integer, List<Mention>> entry : be.getMap().entrySet()) {
				int chapter = entry.getKey().intValue();
				List<Mention> mentions = entry.getValue();
				for (Mention m : mentions) {
					for (int j = i+1; j < bookEntities.size(); j++) {
						BookEntity bf = bookEntities.get(j);
						if (!bf.getType().equals(NEType.PERSON))
							continue;
						List<Mention> list = bf.getMentionsByChapter(chapter);
						if (list == null) {
							continue;
						} 
						for (Mention m2 : list) {
							if (m.getOccursInSentenceNum() == m2.getOccursInSentenceNum()) {
								int beuid = be.getUniqueID();
								int bfuid = bf.getUniqueID();
								if (beuid == bfuid) {
									// something went wrong
									// we shouldn't be linking entity with itself
									System.err.println("Error! Trying to link entity with itself!");
									continue;
								}
								link(beuid, bfuid);
							}
						}
					}
				}
			}
		}
		return true;
	}

	public boolean occursInChapter (BookEntity e, int chapterNumber) {
		if (!entityChapterMap.containsKey(e))
			return false;
		return entityChapterMap.get(e).contains(chapterNumber);
	}

	private void link(int i, int j) {
		Doublet<Integer, Integer> d = new Doublet<>(i,j);
		if (entityNetwork.containsKey(d)) {
			entityNetwork.put(d, entityNetwork.get(d)+1);
		} else {
			entityNetwork.put(d, 1);
		}
	}

	public String networkLinksToJSONString () throws IOException {
		//		StringBuilder sb = new StringBuilder("{\"nodes\":[");

		//		HashSet<Doublet<String, Integer>> unique = new HashSet<>();
		//		
		//		for (Entry<Doublet<Integer, Integer>, Integer> e : entityNetwork.entrySet()) {
		//			Doublet<Integer, Integer> d = e.getKey();
		//			BookEntity be1 = bookEntities.get(d.getValue1());
		//			BookEntity be2 = bookEntities.get(d.getValue2());
		//			unique.add(new Doublet<String, Integer>(be1.getRepresentativeMention(), be1.getUniqueID()));
		//			unique.add(new Doublet<String, Integer>(be2.getRepresentativeMention(), be2.getUniqueID()));
		//		}
		//		
		//		for (Doublet<String, Integer> e : unique) {
		//			sb.append("{");
		//			sb.append("\"name\":\"").append(e.value1).append("\"");
		//			sb.append(",\"id\":").append(e.value2);
		//			sb.append("},");
		//		}

		//		for (BookEntity be : bookEntities) {
		//			sb.append("{\"name\":\"").append(be.getRepresentativeMention()).append("\",\"id\":");
		//			sb.append(be.getUniqueID()).append("},");
		//		}

		//		sb.deleteCharAt(sb.length()-1);
		//		sb.append("],\"links\":[");

		StringBuilder sb = new StringBuilder("\"links\":[");
		for (Entry<Doublet<Integer, Integer>, Integer> e : entityNetwork.entrySet()) {
			Doublet<Integer, Integer> d = e.getKey();
			int frequency = e.getValue().intValue();
			sb.append("{\"source\":" + d.getValue1()).append(",\"target\":").append(d.getValue2()).append(",\"value\":"+frequency+"},");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}

	public String oldNetworkJSON () throws IOException {
				StringBuilder sb = new StringBuilder("{\"nodes\":[");

//				HashSet<Doublet<String, Integer>> unique = new HashSet<>();
//				
//				for (Entry<Doublet<Integer, Integer>, Integer> e : entityNetwork.entrySet()) {
//					Doublet<Integer, Integer> d = e.getKey();
//					BookEntity be1 = bookEntities.get(d.getValue1());
//					BookEntity be2 = bookEntities.get(d.getValue2());
//					unique.add(new Doublet<String, Integer>(be1.getRepresentativeMention(), be1.getUniqueID()));
//					unique.add(new Doublet<String, Integer>(be2.getRepresentativeMention(), be2.getUniqueID()));
//				}
//				
//				for (Doublet<String, Integer> e : unique) {
//					sb.append("{");
//					sb.append("\"name\":\"").append(e.value1).append("\"");
//					sb.append(",\"id\":").append(e.value2);
//					sb.append("},");
//				}

				for (BookEntity be : bookEntities) {
					sb.append("{\"name\":\"").append(be.getRepresentativeMention()).append("\",\"id\":");
					sb.append(be.getUniqueID()).append("},");
				}

				sb.deleteCharAt(sb.length()-1);
				sb.append("],\"links\":[");

//		StringBuilder sb = new StringBuilder("\"links\":[");
		for (Entry<Doublet<Integer, Integer>, Integer> e : entityNetwork.entrySet()) {
			Doublet<Integer, Integer> d = e.getKey();
			int frequency = e.getValue().intValue();
			sb.append("{\"source\":" + d.getValue1()).append(",\"target\":").append(d.getValue2()).append(",\"value\":"+frequency+"},");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]}");
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("network_v5test.json")));
		bw.write(sb.toString());
		bw.close();
		return sb.toString();
	}
	
	public void allPersons () throws IOException {
		StringBuilder sb = new StringBuilder();
		for (BookEntity be : bookEntities) {
			sb.append("{\"name\":\"").append(be.getRepresentativeMention()).append("\",\"id\":");
			sb.append(be.getUniqueID()).append("},");
		}
		sb.deleteCharAt(sb.length()-1);
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("allchar.json")));
		bw.write(sb.toString());
		bw.close();
	}
	
	class Doublet<T1, T2> {

		private T1 value1;
		private T2 value2;

		public Doublet() {

		}

		public Doublet(T1 val1, T2 val2) {
			value1 = val1;
			value2 = val2;
		}

		public T1 getValue1() {
			return value1;
		}

		public T2 getValue2() {
			return value2;
		}

		private ManaCore getOuterType() {
			return ManaCore.this;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((value1 == null) ? 0 : value1.hashCode());
			result = prime * result
					+ ((value2 == null) ? 0 : value2.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Doublet<?, ?> other = (Doublet<?, ?>) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (value1 == null) {
				if (other.value1 != null)
					return false;
			} else if (!value1.equals(other.value1))
				return false;
			if (value2 == null) {
				if (other.value2 != null)
					return false;
			} else if (!value2.equals(other.value2))
				return false;
			return true;
		}
	}

	public List<BookEntity> getBookEntities() {
		return bookEntities;
	}
}