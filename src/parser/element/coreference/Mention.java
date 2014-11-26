package parser.element.coreference;
/**
 * Represents a mention (i.e. a String standing for the mention in a text of an entity)
 * @author David
 *
 */
public class Mention implements Cloneable {
	/**
	 * Textual representation
	 */
	private String textMention;
	/**
	 * Is this Mention representative for the entity?
	 */
	private boolean isRepresentative;
	/**
	 * Numbers
	 */
	private int occursInSentenceNum, 
	wordNumberStart, wordNumberEnd, wordNumberHead;
	
	
	public Mention() {
	
	}
	
	public Mention (String textMention, int occursIn) {
		this.textMention = textMention;
		this.occursInSentenceNum = occursIn;
	}
	
	public boolean isRepresentative() {
		return isRepresentative;
	}

	public void setRepresentative(boolean isRepresentative) {
		this.isRepresentative = isRepresentative;
	}

	public int getWordNumberStart() {
		return wordNumberStart;
	}

	public void setWordNumberStart(int wordStart) {
		this.wordNumberStart = wordStart;
	}

	public int getWordNumberEnd() {
		return wordNumberEnd;
	}

	public void setWordNumberEnd(int wordEnd) {
		this.wordNumberEnd = wordEnd;
	}

	public int getWordNumberHead() {
		return wordNumberHead;
	}

	public void setWordNumberHead(int wordHead) {
		this.wordNumberHead = wordHead;
	}

	public void setTextMention(String textMention) {
		this.textMention = textMention;
	}

	public void setOccursInSentenceNum(int occursInSentenceNum) {
		this.occursInSentenceNum = occursInSentenceNum;
	}

	public String getTextMention () {
		return textMention;
	}
	
	public int getOccursInSentenceNum () {
		return occursInSentenceNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + occursInSentenceNum;
		result = prime * result
				+ ((textMention == null) ? 0 : textMention.hashCode());
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
		Mention other = (Mention) obj;
		if (occursInSentenceNum != other.occursInSentenceNum)
			return false;
		if (textMention == null) {
			if (other.textMention != null)
				return false;
		} else if (!textMention.equals(other.textMention))
			return false;
		return true;
	}
	
	@Override
	public Mention clone () {
		return new Mention(this.textMention, this.occursInSentenceNum);
	}
}
