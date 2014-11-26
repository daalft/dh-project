package parser.element.text;

import java.util.List;

public class Paragraph {

	private List<Sentence> sentences;
	
	public List<Sentence> getSentences () {
		return sentences;
	}
	
	public Sentence getSentence (int position) {
		return sentences.get(position);
	}
}
