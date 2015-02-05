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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lemma == null) ? 0 : lemma.hashCode());
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		AnnotatedWord other = (AnnotatedWord) obj;
		if (lemma == null) {
			if (other.lemma != null)
				return false;
		} else if (!lemma.equals(other.lemma))
			if (!lemma.toLowerCase().equals(other.lemma.toLowerCase()))
				return false;
			else
				return true;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public String toJSON() {
		return "\"lemma\":\""+lemma+"\",\n\"pos\":\""+pos+"\",\n\"type\":\""+type.toString()+(isPolarized()?"\",\n\"polarity\":"+polarity:"\"");
	}
}
