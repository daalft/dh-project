package preface.parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import preface.parser.element.NEType;
import preface.parser.element.text.AnnotatedWord;
import preface.parser.element.text.Chapter;
import preface.parser.element.text.Sentence;
import preface.parser.element.text.Text;
import preface.parser.element.coreference.*;
/**
 * Basic STaX parser for wrapping XML into java-classes
 * @author Julian
 *
 */

public class Parser {
	/** 
	* contains a list of entities which contains a list of corresponding mentions
	*/	
	private List<Entity> entities; 
	/**
	 * contains the corefrence chains from the index file
	 */
	private List<Chain> chains;
	/**
	 * contains all the textual data
	 */
	private Text text;
	
	/**
	 * Constructor
	 */
	public Parser () {
		entities = new ArrayList<Entity>();
		chains = new ArrayList<Chain>();
		text = new Text();
	}
	/**
	 * Returns the parsed entities
	 * @return list of entities
	 */
	public List<Entity> getEntities () {
		return entities;
	}
	/**
	 * Returns the parsed coreference chains
	 * @return list of chains
	 */
	public List<Chain> getChains (){
		return chains;
	}
	/**
	 * Returns the parsed text according to the specified structure in preface.parser.element.text
	 * @return text structure 
	 */
	public Text getText () {
		return text;
	}
	/**
	 * Resets all variables used for parsing. Usually executed at the end of a book.
	 */
	public void dispose () {
		entities = null;
		text = null;
		chains = null;  
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	/**
	 * Main function for parsing text and chapter files
	 * @param dir directory containing preprocessed chapterfiles
	 */
	public void parse(File dir) {
		File[] fileList = dir.listFiles();
		int chptNr = 0;
		Chapter chapter = null;
		Sentence sentence = null;
		AnnotatedWord annotatedWord = null;
//		ensures that every file in the directory is processed
		for (File f : fileList){
			if (f.isDirectory()) { continue; }
			try {
				Entity currEnt = null;		
				Mention currMen = null;
//				initializing the StAX parser
				InputStream	in = new FileInputStream(f);
				XMLInputFactory factory = XMLInputFactory.newInstance();
				XMLStreamReader parser = factory.createXMLStreamReader(in);

//				continiues as long as the parser finds another tag
				while (parser.hasNext())
				{
					switch (parser.getEventType() )
					{
//					at the end of file the parser is closed
					case XMLStreamConstants.END_DOCUMENT:
						parser.close();
						break;

//					this part specifies what the parser should do when encountering a specific tag	
					case XMLStreamConstants.START_ELEMENT:
						switch(parser.getLocalName())
						{
//						COREFERENCE PART of Parser
//						action to perform on an opening <chapter id=""> tag
						case "chapter":
//							parse chapter id
							chptNr= Integer.parseInt(parser.getAttributeValue(0)); 
//							create new chapter instance
							chapter = new Chapter();
//							set chapter id on the new instance
							chapter.setChapterNumber(chptNr);
							break;
//						action to perform on an opening <corefrence id= ""> tag
						case "coreference":
//							create new coreference (entity) instance
							currEnt = new Entity();
//							parse coreference id
							currEnt.setId(Integer.parseInt(parser.getAttributeValue(0))); 
//							set coreference id on the new instance
							currEnt.setChapterNumber(chptNr);
							break;
//						action to perform on an opening <mention> tag
						case "mention":
//							create new mention instance
							currMen = new Mention();
//							check if mention is representative and set it on the new instance
							if(parser.getAttributeValue(0) != null){currMen.setRepresentative(true);} 
							break; 
//						action to perform on an opening <start> subtag from <mention>
						case "start":
							currMen.setWordNumberStart(Integer.parseInt(parser.getElementText()));
							break;
//						action to perform on an opening <end> subtag from <mention>
						case "end":
							currMen.setWordNumberEnd(Integer.parseInt(parser.getElementText()));
							break;
//						action to perform on an opening <head> subtag from <mention>
						case "head":
							currMen.setWordNumberHead(Integer.parseInt(parser.getElementText()));
							break;
//						action to perform on an opening <text> subtag form <mention>
						case "text":
							currMen.setTextMention(parser.getElementText());
							break;
						
//						TEXT PART of Parser	
//						action to perform on an opening <sentence> tag 	
						case "sentence":
							String att = parser.getAttributeValue(0);
//							because there are sentence tags without attributes, we check if there are any
							if (att == null) {
								currMen.setOccursInSentenceNum(Integer.parseInt(parser.getElementText()));
							} else {
								sentence = new Sentence();
								sentence.setSentenceNumber(Integer.parseInt(parser.getAttributeValue(0)));
							}
							break;
//						action to perform on an opening <sentence> tag 	
						case "token":
							annotatedWord = new AnnotatedWord();
							annotatedWord.setId(Integer.parseInt(parser.getAttributeValue(0)));
							break;
//						action to perform on an opening <word> tag 	
						case "word":
							annotatedWord.setWord(parser.getElementText());
							break;
//						action to perform on an opening <lemma> tag 	
						case "lemma":
							annotatedWord.setLemma(parser.getElementText());
							break;
//						action to perform on an opening <POS> tag 	
						case "POS":
							annotatedWord.setPOS(parser.getElementText());
							break;
//						action to perform on an opening <NER> tag 	
//						set corresponding NER type
						case "NER":
							switch(parser.getElementText()) {
							case "O":
								annotatedWord.setType(NEType.OTHER);
								break;
							case "PERSON":
								annotatedWord.setType(NEType.PERSON);
								break;
							case "LOCATION":
								annotatedWord.setType(NEType.LOCATION);
								break;
							case "NUMBER":
								annotatedWord.setType(NEType.NUMBER);
								break;
								// if we encounter some other NE Type not defined
								// set NEType to OTHER
								default:
									annotatedWord.setType(NEType.OTHER);
									break;
							} 
						
						default:
							break;
						}
						break;
//					action on the closing tag
					case XMLStreamConstants.END_ELEMENT:
						switch (parser.getLocalName())
						{
//						action to perform on closing </coreference> tag
						case "coreference":
//							add the current entity to the list of entities
							entities.add(currEnt); 
							break;
//						action to perform on closing </mention> tag
						case "mention":
//							add the finished mention to the current entity
							currEnt.add(currMen);
							break;
//						action to perform on closing </chapter> tag	
						case "chapter":
							text.add(chapter);
							break;
//						action to perform on closing </sentence> tag
						case "sentence":
							chapter.add(sentence);
							break;
//						action to perform on closing </sentence> tag
						case "token":
							sentence.add(annotatedWord);
							break;
						default:
							break;
						}
						break;
					}
//					call next ELEMENT
					parser.next();
				}
//			necessary error handling
			} catch (FileNotFoundException e){
				e.printStackTrace();
			} catch (XMLStreamException e){
				e.printStackTrace();
			}

		}
	}
	/**
	 * main function for parsing the coreference index file
	 * @param indexFile path to the index file
	 */
	public void parseIndex(File indexFile){
		try{
			Chain currChn = null;
			EntityReference currRef = null;
//			initializing the StAX parser
			InputStream	in = new FileInputStream(indexFile);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(in);
//			continiues as long as the parser finds another tag
			while(parser.hasNext()){
				switch(parser.getEventType())
				{
//				action to perform on the end of file
				case XMLStreamConstants.END_DOCUMENT:
					parser.close();
					break;
//				this part specifies what the parser should do when encountering a specific tag	
				case XMLStreamConstants.START_ELEMENT:
					switch(parser.getLocalName()){
//					action to perform on opening <chain> tag
					case "chain":
						currChn = new Chain();
						currChn.setText(parser.getAttributeValue(0));
						break;
//					action to perform on opening <coreference> tag
					case "coreference":
						currRef = new EntityReference();					
						break;
//					action to perform on opening <id> tag
					case "id":
						currRef.setId(Integer.parseInt(parser.getElementText()));
						break;
//					action to perform on opening <chapter> tag
					case "chapter":
						currRef.setChapterNumber(Integer.parseInt(parser.getElementText()));
						break;
					default:
						break;
					}
				case XMLStreamConstants.END_ELEMENT:
					switch(parser.getLocalName()){
//					action to perform on closing </chain> tag
					case "chain":
//						add current chain to the list of chains
						chains.add(currChn);
						break;
//					action to perform on closing </coreference> tag
					case "coreference":
//						add the current entity reference to the current chain
						currChn.add(currRef);
						break;
					default:
						break;
					}
					break;	
				}
				parser.next();
			}





		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(XMLStreamException e){
			e.printStackTrace();
		}
	}

//	public static void main(String args[]) {
//		/**
//		 * Development only. Not used in the later process.
//		 *
//		File dir = new File("data\\UncleTomsCabin\\chapters\\extracted");
//		Parser p = new Parser();
//		p.parse(dir);
//		List<Entity> entities = p.getEntities();
//		System.out.println(entities);
//		 **/
//
//		Parser p = new Parser();
//		p.parseIndex(new File("data\\coref_index.xml"));
//		List<Chain> chains = p.getChains();
//		System.out.println(chains);
//
//	}
}
