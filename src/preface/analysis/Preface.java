package preface.analysis;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import preface.analysis.result.Result;
import preface.analysis.util.StopWords;
import preface.parser.Parser;
import preface.parser.element.coreference.Entity;
import preface.parser.element.coreference.Mention;
import preface.parser.element.text.AnnotatedWord;
import preface.parser.element.text.Chapter;
import preface.parser.element.text.Paragraph;
import preface.parser.element.text.Sentence;
import preface.parser.element.text.Text;

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

		Parser p = new Parser();
		p.parse();
		List<Entity> entities = p.getEntities();
		Text text = p.getText();
		p.dispose();

		StopWords stop = new StopWords();

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			Result r = new Result();
			for (Mention m : e) {
				// find content in context based on parameters
				int leftLimit = m.getWordNumberStart();
				int rightLimit = m.getWordNumberEnd();
				for (Chapter chapter : text) {
					for (Paragraph para : chapter) {
						Sentence s = para.getSentence(m.getOccursInSentenceNum()-1);
						for (AnnotatedWord w : s) {
							if (stopWords) {
								if (stop.isStopword(w.getLemma()))
									continue;
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
			}
			// network linking
			for (Mention m1 : e) {
				for (int j = i+1; j < entities.size(); j++) {
					Entity e2 = entities.get(j);
					for (Mention m2 : e2) {
						if (m1.getOccursInSentenceNum() == m2.getOccursInSentenceNum()) {
							r.link(e2.getId());
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
