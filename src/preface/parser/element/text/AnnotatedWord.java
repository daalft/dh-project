package preface.parser.element.text;

import preface.parser.element.NEType;
/**
 * Represents an annotated word
 * @author David
 *
 */
public class AnnotatedWord {

	private String 	word,
					lemma,
					pos;
	private NEType type = NEType.OTHER;
	private int id;
	
	public String getWord () {
		return word;
	}
	
	public String getLemma () {
		return lemma;
	}
	
	public String getPOS () {
		return pos;
	}
	
	public NEType getType () {
		return type;
	}
	
	public int getId () {
		return id;
	}

	public void setWord(String word) {
		this.word = word;
		if (lemma == null) {
			lemma = word;
		}
		if (pos == null) {
			pos = word;
		}
	}

	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	public void setPOS(String pos) {
		this.pos = pos;
	}

	public void setType(NEType type) {
		this.type = type;
	}

	public void setId(int id) {
		this.id = id;
	}
}
