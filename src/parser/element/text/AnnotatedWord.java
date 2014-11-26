package parser.element.text;

import parser.element.NEType;
/**
 * Represents an annotated word
 * @author David
 *
 */
public class AnnotatedWord {

	private String 	word,
					lemma,
					pos;
	private NEType type;
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
}
