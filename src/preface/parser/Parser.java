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

import preface.parser.element.text.Text;
import preface.parser.element.coreference.*;
/**
 * Basic STaX parser for wrapping XML into java-classes
 * @author Julian
 *
 */
public class Parser {

	private List<Entity> entities; //enthält eine Liste von entities (entity = liste von zusammengehörigen Mentions. Realisiert ist diese liste in entity.java
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
		int ChptNr = 0;
		for (File f : fileList){
		try {
			Entity currEnt = null;		//enthält die entity (coreference) mentions werden per .add(mention) zu der entity hinzugefügt.
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
								ChptNr= Integer.parseInt(parser.getAttributeValue(0)); // Takes the chapter ID from the XML file
								
							case "coreference":
								currEnt = new Entity();
								currEnt.setId(Integer.parseInt(parser.getAttributeValue(0))); // übernimmt die ID's aus <coreference id="xx">
								currEnt.setChapterNumber(ChptNr);
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
							
							default:
							break;
						}
					break;

					case XMLStreamConstants.END_ELEMENT:
						switch (parser.getLocalName())
						{
							case "coreference":
								entities.add(currEnt); //fügt currEnt der Liste der entities hinzu
							break;
							
							case "mention":
								currEnt.add(currMen);
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
			Entity currEnt = null;
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
								currEnt = new Entity();					
							break;
							case "id":
								currEnt.setId(Integer.parseInt(parser.getElementText()));
							break;
							case "chapter":
								currEnt.setChapterNumber(Integer.parseInt(parser.getElementText()));
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
								currChn.add(currEnt);
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
	
	public static void main(String args[]) {
		/**
		 * Development only. Not used in the later process.
		 *
		File dir = new File("data\\UncleTomsCabin\\chapters\\extracted");
		Parser p = new Parser();
		p.parse(dir);
		List<Entity> entities = p.getEntities();
		System.out.println(entities);
		**/
		
		Parser p = new Parser();
		p.parseIndex(new File("data\\coref_index.xml"));
		List<Chain> chains = p.getChains();
		System.out.println(chains);
		
	}
}
