package parser.element.text;

import parser.element.NEType;

public class AnnotatedWord {

	private String 	word,
					lemma,
					pos;
	private NEType type;
	
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
}
