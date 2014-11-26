package parser.element.coreference;

import parser.element.NEType;
/**
 * Represents a mention (i.e. a String standing for the mention in a text of an entity)
 * @author David
 *
 */
public class Mention {
	/**
	 * Textual representation
	 */
	private String textMention;

	/**
	 * Sentence number this mention occurs in
	 */
	private int occursInSentenceNum;
	
	public String getTextMention () {
		return textMention;
	}
	
	public int getOccursInSentenceNum () {
		return occursInSentenceNum;
	}
}
