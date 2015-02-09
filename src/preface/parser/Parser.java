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

	private List<Entity> entities; //enth�lt eine Liste von entities (entity = liste von zusammengeh�rigen Mentions. Realisiert ist diese liste in entity.java
	private List<Chain> chains;
	private Text text;
	//private File dir = new File("data\\UncleTomsCabin\\chapters\\extracted"); 	//TODO Path should not be hardwired.
	//private File indexFile = new File("data\\coref_index.xml");

	public Parser () {
		entities = new ArrayList<Entity>();
		chains = new ArrayList<Chain>();
		text = new Text();
	}

	public List<Entity> getEntities () {
		return entities;
	}

	public List<Chain> getChains (){
		return chains;
	}

	public Text getText () {
		return text;
	}

	public void dispose () {
		entities = null;
		text = null;
		chains = null;  //not sure if dispose should reset chains.
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void parse(File dir) {
		File[] fileList = dir.listFiles();
		int chptNr = 0;
		Chapter chapter = null;
		Sentence sentence = null;
		AnnotatedWord annotatedWord = null;

		for (File f : fileList){
			if (f.isDirectory()) { continue; }
			try {
				Entity currEnt = null;		//enth�lt die entity (coreference) mentions werden per .add(mention) zu der entity hinzugef�gt.
				Mention currMen = null;
				InputStream	in = new FileInputStream(f);
				XMLInputFactory factory = XMLInputFactory.newInstance();
				XMLStreamReader parser = factory.createXMLStreamReader(in);

				while (parser.hasNext())
				{
					switch (parser.getEventType() )
					{
					case XMLStreamConstants.END_DOCUMENT:
						parser.close();
						break;

					case XMLStreamConstants.START_ELEMENT:
						switch(parser.getLocalName())
						{
						case "chapter":
							chptNr= Integer.parseInt(parser.getAttributeValue(0)); // Takes the chapter ID from the XML file
							chapter = new Chapter();
							chapter.setChapterNumber(chptNr);
							break;
						case "coreference":
							currEnt = new Entity();
							currEnt.setId(Integer.parseInt(parser.getAttributeValue(0))); // �bernimmt die ID's aus <coreference id="xx">
							currEnt.setChapterNumber(chptNr);
							break;

						case "mention":
							currMen = new Mention();
							if(parser.getAttributeValue(0) != null){currMen.setRepresentative(true);} //set if <mention representative = "true">
							break; 

						case "start":
							currMen.setWordNumberStart(Integer.parseInt(parser.getElementText()));
							break;

						case "end":
							currMen.setWordNumberEnd(Integer.parseInt(parser.getElementText()));
							break;

						case "head":
							currMen.setWordNumberHead(Integer.parseInt(parser.getElementText()));
							break;

						case "text":
							currMen.setTextMention(parser.getElementText());
							break;
						case "sentence":
							String att = parser.getAttributeValue(0);
							if (att == null) {
								currMen.setOccursInSentenceNum(Integer.parseInt(parser.getElementText()));
							} else {
								sentence = new Sentence();
								sentence.setSentenceNumber(Integer.parseInt(parser.getAttributeValue(0)));
							}
							break;
						case "token":
							annotatedWord = new AnnotatedWord();
							annotatedWord.setId(Integer.parseInt(parser.getAttributeValue(0)));
							break;
						case "word":
							annotatedWord.setWord(parser.getElementText());
							break;
						case "lemma":
							annotatedWord.setLemma(parser.getElementText());
							break;
						case "POS":
							annotatedWord.setPOS(parser.getElementText());
							break;
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

					case XMLStreamConstants.END_ELEMENT:
						switch (parser.getLocalName())
						{
						case "coreference":
							entities.add(currEnt); //f�gt currEnt der Liste der entities hinzu
							break;

						case "mention":
							currEnt.add(currMen);
							break;	
						case "chapter":
							text.add(chapter);
							break;
						case "sentence":
							chapter.add(sentence);
							break;
						case "token":
							sentence.add(annotatedWord);
							break;
						default:
							break;
						}
						break;
					}
					parser.next();
				}
			} catch (FileNotFoundException e){
				e.printStackTrace();
			} catch (XMLStreamException e){
				e.printStackTrace();
			}

		}
	}
	public void parseIndex(File indexFile){
		try{
			Chain currChn = null;
			EntityReference currRef = null;
			InputStream	in = new FileInputStream(indexFile);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(in);

			while(parser.hasNext()){
				switch(parser.getEventType())
				{
				case XMLStreamConstants.END_DOCUMENT:
					parser.close();
					break;

				case XMLStreamConstants.START_ELEMENT:
					switch(parser.getLocalName()){
					case "chain":
						currChn = new Chain();
						currChn.setText(parser.getAttributeValue(0));
						break;
					case "coreference":
						currRef = new EntityReference();					
						break;
					case "id":
						currRef.setId(Integer.parseInt(parser.getElementText()));
						break;
					case "chapter":
						currRef.setChapterNumber(Integer.parseInt(parser.getElementText()));
						break;
					default:
						break;
					}
				case XMLStreamConstants.END_ELEMENT:
					switch(parser.getLocalName()){
					case "chain":
						chains.add(currChn);
						break;

					case "coreference":
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
