package preface.analysis;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import preface.analysis.result.Result;
import preface.analysis.util.StopWords;
import preface.parser.Parser;
import preface.parser.element.NEType;
import preface.parser.element.coreference.Entity;
import preface.parser.element.coreference.Mention;
import preface.parser.element.text.AnnotatedWord;
import preface.parser.element.text.Chapter;
import preface.parser.element.text.Paragraph;
import preface.parser.element.text.Sentence;
import test.DummyParser;

/**
 * PreVisualizer Frequency Analyzer and Co-occurrence Extractor
 * @author David
 *
 */
public class Preface {

	//private boolean bagOfWords;
	private int searchWindow = -1;
	private HashMap<Entity, Result> map;
	private boolean stopWords;
	
	public Preface () {
		map = new HashMap<>();
	}
	
	public void setUseStopwords (boolean use) {
		stopWords = use;
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

		DummyParser p = new DummyParser();
		p.parse();
		List<Entity> entities = p.getEntities();
		Chapter chapter = p.getChapter();
		p.dispose();
		
		StopWords stop = new StopWords();
		
		for (Entity e : entities) {
			Result r = new Result();
			for (Mention m : e) {
				// find content in context based on parameters
				int leftLimit = m.getWordNumberStart();
				int rightLimit = m.getWordNumberEnd();
				for (Paragraph para : chapter) {
					Sentence s = para.getSentence(m.getOccursInSentenceNum()-1);
					NEType mentionType = s.getWord(m.getWordNumberHead()).getType();
					for (AnnotatedWord w : s) {
						if (stopWords) {
							if (stop.isStopword(w.getLemma()))
								continue;
						}
						// for network between people
						// TODO I think this links the word to itself
						// should link entity mention type (m or e) to w
						if (w.getType().equals(NEType.PERSON) && mentionType.equals(NEType.PERSON)) {
							r.link(e);
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
		try {
			write(map);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void write(HashMap<Entity, Result> map) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("testoutputstops2.txt")));
		for (Entry<Entity, Result> e : map.entrySet()) {
			Entity currEnt = e.getKey();
			Result currRes = e.getValue();
			bw.write(currEnt.toString());
			bw.write(currRes.toString());
			bw.newLine();
			bw.newLine();
		}
		bw.close();
	}

	public static void main(String[] args) {
		Preface preface = new Preface();
		preface.setUseStopwords(true);
		preface.setSearchWindow(3);
		preface.run();
	}
}
