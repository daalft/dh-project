package preface.analysis;

import java.util.HashMap;
import java.util.List;

import preface.analysis.result.Result;
import preface.parser.Parser;
import preface.parser.element.NEType;
import preface.parser.element.coreference.Entity;
import preface.parser.element.coreference.Mention;
import preface.parser.element.text.AnnotatedWord;
import preface.parser.element.text.Chapter;
import preface.parser.element.text.Paragraph;
import preface.parser.element.text.Sentence;

/**
 * PreVisualizer Frequency Analyzer and Co-occurrence Extractor
 * @author David
 *
 */
public class Preface {

	//private boolean bagOfWords;
	private int searchWindow = -1;
	private HashMap<Entity, Result> map;
	
	public Preface () {
		map = new HashMap<>();
	}
	
	public void setSearchWindow (int i) {
		if (i < 1) {
			System.err.println("Search window size must be at least 1"
					+ "\nor -1 for unrestricted window size");
			return;
		}
		searchWindow = i;
	}

	public void run () {

		Parser p = new Parser();
		p.parse();
		List<Entity> entities = p.getEntities();
		Chapter chapter = p.getChapter();
		p.dispose();
		
		for (Entity e : entities) {
			Result r = new Result();
			for (Mention m : e) {
				// find content in context based on parameters
				int leftLimit = m.getWordNumberStart();
				int rightLimit = m.getWordNumberEnd();
				for (Paragraph para : chapter) {
					Sentence s = para.getSentence(m.getOccursInSentenceNum());
					NEType mentionType = s.getWord(m.getWordNumberHead()).getType();
					for (AnnotatedWord w : s) {
						// for network between people
						if (w.getType().equals(NEType.PERSON) && mentionType.equals(NEType.PERSON)) {
							r.link(w);
						}
						// if we have a search window limit
						if (searchWindow > 0) {
							// continue until we are inside the window 
							if (w.getId() < (leftLimit - searchWindow))
								continue;
							// break if we are outside the window
							if (w.getId() > (rightLimit + searchWindow))
								break;
						}
						if (w.getPOS().matches("NN\\w?") || 
								w.getPOS().matches("VB\\w?") ||
								w.getPOS().equals("JJ\\w?")) { 
							r.add(w, chapter.getChapterNumber());
						}
					}
				}				
			}
			if (!r.isEmpty())
				map.put(e, r);
		}
	}

	public static void main(String[] args) {
		new Preface().run();
	}
}
