package preface.tree;

/**
 * Represents a special mapping node
 * that maps a word to its frequency
 * <br/><br/>
 * This node is a separate class instead
 * of a parameterized Node in order to
 * overwrite the <code>equals</code> method
 * (which should only check for equality
 * based on the String and <b>not</b> based
 * on the Integer frequency
 * @author David
 *
 */
public class FrequencyNode extends Node<String> {

	private String word;
	private int totalFrequency;
	
	public FrequencyNode(String lemma) {
		word = lemma;
		totalFrequency = 1;
	}

	public boolean equals (FrequencyNode other) {
		return this.word.equals(other.word);
	}

	public void increaseFrequency () {
		totalFrequency++;
	}

	public void increaseFrequency (int by) {
		totalFrequency += by;
	}
	
	public double getRelativeFrequency (int total) {
		return ((double)totalFrequency)/total;
	}
}
