package parser.element.coreference;

import parser.element.NEType;

public class Mention {

	private String textMention;
	private NEType type;
	private int occursInSentenceNum;
	
	public String getTextMention () {
		return textMention;
	}
	
	public NEType getType () {
		return type;
	}
	
	public int getOccursInSentenceNum () {
		return occursInSentenceNum;
	}
}
