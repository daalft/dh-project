package preface;

import java.util.List;

import parser.Parser;
import parser.element.NEType;
import parser.element.coreference.Entity;
import parser.element.coreference.Mention;
import parser.element.text.AnnotatedWord;
import parser.element.text.Chapter;
import parser.element.text.Paragraph;
import parser.element.text.Sentence;
import preface.tree.FrequencyNode;
import preface.tree.Node;
import preface.tree.Tree;

/**
 * PreVisualizer Frequency Analyzer and Co-occurrence Extractor
 * @author David
 *
 */
public class Preface {

	//private boolean bagOfWords;
	private int searchWindow = -1;

	public void setSearchWindow (int i) {
		if (i < 1) {
			System.err.println("Search window size must be at least 1"
					+ "\nor -1 for unrestricted window size");
			return;
		}
		searchWindow = i;
	}

	public void run () {
		
		Tree t = new Tree();
		
		// TODO create textNode?
		
		// PARSING BLOCK
		
		Parser p = new Parser();
		p.parse();
		List<Entity> entities = p.getEntities();
		Chapter chapter = p.getChapter();
		p.dispose();
		
		// END PARSING BLOCK
		
		Node<Integer> chapterNode = new Node<>(chapter.getChapterNumber());
		
		// TODO change to textNode?
		t.setRoot(chapterNode);
		
		for (Entity e : entities) {
			Node<Entity> entityNode = new Node<>(e);
			chapterNode.addChild(entityNode);
			// build new nodes
			// based on POS
			Node<String> nounNode = new Node<>("NOUN");
			Node<String> verbNode = new Node<>("VERB");
			Node<String> adjNode = new Node<>("ADJECTIVE");
			entityNode.addChild(adjNode);
			entityNode.addChild(verbNode);
			entityNode.addChild(nounNode);
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
							// TODO link this entity to other entity?
						}
						// if we have a search window limit
						if (searchWindow > 0) {
							// continue until we are inside the window 
							if (w.getId() < (leftLimit - searchWindow) 
									|| w.getId() > (rightLimit + searchWindow))
								continue;
						}
						if (w.getPOS().matches("NN\\w?")) { // nouns
							// build new node with word -> frequency
							// or check for existing node in entityNode and increase freq
							FrequencyNode fn = new FrequencyNode(w.getLemma());
							if (nounNode.contains(fn)) {
								((FrequencyNode) nounNode
										.get(nounNode.indexOf(fn)))
										.increaseFrequency();
							} else {
								nounNode.addChild(fn);
							}
						}
						if (w.getPOS().matches("VB\\w?")) { // verbs
							FrequencyNode fn = new FrequencyNode(w.getLemma());
							if (verbNode.contains(fn)) {
								((FrequencyNode) verbNode
										.get(verbNode.indexOf(fn)))
										.increaseFrequency();
							} else {
								verbNode.addChild(fn);
							}
						}
						if (w.getPOS().equals("JJ\\w?")) { // adjectives
							FrequencyNode fn = new FrequencyNode(w.getLemma());
							if (adjNode.contains(fn)) {
								((FrequencyNode) adjNode
										.get(adjNode.indexOf(fn)))
										.increaseFrequency();
							} else {
								adjNode.addChild(fn);
							}
						}
					}
				}				
			}
		}
	}

	public static void main(String[] args) {
		new Preface().run();
	}
}
