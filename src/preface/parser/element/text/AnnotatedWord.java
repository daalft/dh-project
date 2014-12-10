package preface.parser.element.text;

import preface.parser.element.NEType;
import preface.parser.element.text.interfaces.Polariseable;
/**
 * Represents an annotated word
 * @author David
 *
 */
public class AnnotatedWord implements Polariseable {

	private String 	word,
					lemma,
					pos;
	private NEType type = NEType.OTHER;
	private int id;
	private double polarity;
	
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

	@Override
	public String getString() {
		return lemma;
	}

	@Override
	public void setPolarity(double d) {
		this.polarity = d;
	}

	@Override
	public double getPolarity() {
		return polarity;
	}

	@Override
	public boolean isPolarized() {
		return polarity > 0;
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder("[AnnotatedWord]\n");
		sb.append("Word: ").append(word).append("\n");
		sb.append("Lemma: ").append(lemma).append("\n");
		sb.append("POS: ").append(pos).append("\n");
		sb.append("NE Type: ").append(type).append("\n");
		sb.append("ID: ").append(id).append("\n");
		if (isPolarized())
			sb.append("Polarity: ").append(polarity).append("\n");
		return sb.toString();
	}
}
