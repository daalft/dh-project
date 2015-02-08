package preface.analysis;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import preface.analysis.result.Result;
import preface.analysis.util.Polaris;
import preface.analysis.util.StopWords;
import preface.parser.Parser;
import preface.parser.element.coreference.BookEntity;
import preface.parser.element.coreference.Chain;
import preface.parser.element.coreference.Entity;
import preface.parser.element.coreference.Mention;
import preface.parser.element.text.AnnotatedWord;
import preface.parser.element.text.Chapter;
import preface.parser.element.text.Sentence;
import preface.parser.element.text.Text;

/**
 * PreVisualizer Frequency Analyzer and Co-occurrence Extractor
 * <p>
 * Main class for extraction of information
 * @author David
 *
 */
public class Preface {

	/**
	 * Linguistic model to use
	 */
	//private boolean bagOfWords;
	/**
	 * Search window width or -1 for unrestricted
	 */
	private int searchWindow = -1;
	/**
	 * Result map
	 */
	private HashMap<BookEntity, Result> map;
	/**
	 * Flag indicating the use of stop word filtering
	 */
	private boolean stopWords;
	/**
	 * Number of words for word cloud
	 */
	private int cloudWordNumber = 100;
	/**
	 * Output dir
	 */
	private String outputdir = "./visual/";

	/**
	 * Constructor
	 */
	public Preface () {
		map = new HashMap<>();
	}

	/**
	 * Sets the flag to use stop words
	 * @param use flag
	 */
	public void setUseStopwords (boolean use) {
		stopWords = use;
	}

	/**
	 * Sets the search window
	 * <p>
	 * A value of -1 corresponds to unrestricted search.
	 * @param i search window width
	 */
	public void setSearchWindow (int i) {
		searchWindow = i;
	}
	
	/**
	 * Sorts a map by value
	 * @param map map to sort
	 * @return sorted map
	 */
	private static <K, V extends Comparable<? super V>> Map<K, V> 
	sortByValue( Map<K, V> map )
	{
		List<Map.Entry<K, V>> list =
				new LinkedList<>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
				{
			@Override
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
			{
				return (o2.getValue()).compareTo( o1.getValue() );
			}
				} );

		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}
	
	/**
	 * Runs Preface
	 * @param dir directory containing chapter files
	 * @param index index file
	 */
	public void run (File dir, File index) {
		// PARSER BLOCK
		System.out.println("Parsing");
		Parser p = new Parser();
		p.parse(dir);
		p.parseIndex(index);
		List<Entity> chapterEntities = p.getEntities();
		List<Chain> chains = p.getChains();
		Text text = p.getText();
		// numerical sort => rewrite lexicographic order
		Collections.sort(text.getChapters());
		p.dispose();
		// END PARSER BLOCK

		// max number of chapters in book
		int maxChapters = text.getChapters().size();

		// stop words filter
		StopWords stop = new StopWords();

		// cloud calculation
		System.err.println("Cloud computation");
		HashMap<String, Integer> cloud = new HashMap<String, Integer>();
		for (Chapter c : text) {
			for (Sentence s : c) {
				for (AnnotatedWord w : s) {
					if (stop.isStopword(w.getLemma()))
						continue;
					if (w.getLemma().matches("[\\W\\d]+"))
						continue;
					if (cloud.containsKey(w.getLemma())) {
						cloud.put(w.getLemma(), cloud.get(w.getLemma())+1);
					} else {
						cloud.put(w.getLemma(), 1);
					}
				}
			}
		}
		Map<String, Integer> sorted = sortByValue(cloud);
		
		// more.json
		StringBuilder sb = new StringBuilder("{\"maxChapterNumber\":").append(maxChapters);
		sb.append(",\n\"words\":[");
		int count = 0;
		for (Entry<String, Integer> e : sorted.entrySet()) {
			if (count >= cloudWordNumber )
				break;
			sb.append("{\"word\":\"").append(e.getKey()).append("\",\"weight\":").append(e.getValue().intValue()).append("},\n");
			count++;
		}
		sb.deleteCharAt(sb.length()-2);
		sb.append("]}");
		try {
			BufferedWriter more = new BufferedWriter(new FileWriter(new File(outputdir+"more.json")));
			more.write(sb.toString());
			more.close();
		} catch (Exception e) {

		}

		// NE Type resolution
		System.out.println("NETYPE resolution");
		for (Entity e : chapterEntities) {
			int chapter = e.getChapterNumber();
			for (Mention m : e) {
				int sentence = m.getOccursInSentenceNum();
				int head = m.getWordNumberHead();
				Chapter c = text.getChapter(chapter);
				e.setType(c.getSentence(sentence).getWord(head).getType());
			}
		}

		// MANACORE
		System.out.println("MANACORE start");
		ManaCore mc = new ManaCore(chapterEntities, chains);
		mc.correlate();
		String links = "";
		try {
			links = mc.networkLinksToJSONString();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		List<BookEntity> entities = mc.getBookEntities();

		// POLARIS
		Polaris polaris = new Polaris();
		
		// Preface proper
		System.out.println("Preface go");
		for (int i = 0; i < entities.size(); i++) {
			// fetch entities
			BookEntity e = entities.get(i);
			// init result
			Result r = new Result();
			// iterate over entity map -> chapter - mentions
			for (Entry<Integer, List<Mention>> entry : e.getMap().entrySet()) {
				// get chapter number
				int chapterNumber = entry.getKey().intValue();
				// get mentions
				List<Mention> list = entry.getValue();
				// for each mention
				for (Mention m : list) {
					// set left and right limit for search window
					int leftLimit = m.getWordNumberStart();
					int rightLimit = m.getWordNumberEnd();
					// fetch correct chapter from text
					Chapter chapter = text.getChapter(chapterNumber);
					// double check: entity occurs in chapter?
					if (mc.occursInChapter(e, chapterNumber)) {
						// get sentence with mention in it
						Sentence s = chapter.getSentence(m.getOccursInSentenceNum());
						// for each word in sentence
						for (AnnotatedWord w : s) {
							// stop word filtering
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
							// if we have a noun, verb or adjective
							if (w.getPOS().matches("NN[^P]?") || 
									w.getPOS().matches("VB\\w?") ||
									w.getPOS().equals("JJ\\w?")) { 
								// annotate with POLARIS and add to result
								r.add(polaris.annotate(w), chapter.getChapterNumber());
							}
						}
					}
				}
			}
			if (r.isEmpty()) {
				// add required entities even if result is empty
				if (mc.requiredEntities().contains(e.getUniqueID())) {
					map.put(e,r);
				}
			} else 
				map.put(e, r);
		}
		System.out.println("Done");
		try {
			write(map, links);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Writes the calculated data to file
	 * @param map2 data
	 * @param links network links
	 * @throws IOException
	 */
	private void write(HashMap<BookEntity, Result> map2, String links) throws IOException {
		StringBuilder sb = new StringBuilder("{\"nodes\":[");
		for (Entry<BookEntity, Result> e : map2.entrySet()) {
			BookEntity currEnt = e.getKey();
			Result currRes = e.getValue();
			sb.append("{").append(currEnt.toJSON()).append(",\n\"children\":[");
			sb.append(currRes.toJSON());
			sb.append("]},");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("],").append(links).append("}");
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputdir+"data.json")));
		bw.write(sb.toString());
		bw.close();
	}

	/**
	 * Changes the output dir
	 * @param s output dir
	 */
	public void setOutputDir (String s) {
		if (!s.endsWith("/"))
			s += "/";
		outputdir = s;
	}
	
	/**
	 * Main method for command line invocation
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Wrong number of arguments! Expected 2, got " + args.length);
			System.err.println("Please indicate directory containing text files and path to index file.");
			System.exit(0);
		}
		Preface preface = new Preface();
		// TODO
		// use CLI parser to read/set the following attributes
		preface.setUseStopwords(true);
		preface.setSearchWindow(3);
		if (args.length > 2) {
			preface.setOutputDir(args[2]);
		}
		File dir = new File(args[0]);
		File index = new File(args[1]);
		preface.run(dir, index);
	}
}
