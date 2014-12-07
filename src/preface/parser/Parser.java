package preface.parser;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.*;

import preface.parser.element.coreference.Entity;
import preface.parser.element.coreference.Mention;
import preface.parser.element.text.Chapter;

public class Parser {

	private List<Entity> entities;
	private Chapter chapter;
	
	
	public Parser () {
		entities = new ArrayList<Entity>();
		chapter = new Chapter();
	}
	
	public List<Entity> getEntities () {
		return entities;
	}
	
	public Chapter getChapter () {
		return chapter;
	}
	
	public void dispose () {
		entities = null;
		chapter = null;
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void parse() {
		try {
			List<Entity> entities= new ArrayList<Entity>(); //enthält eine Liste von entities (entity = liste von zusammengehörigen Mentions. Realisiert ist diese liste in entity.java
			Entity currEnt = null;		//enthält die entity (coreference) mentions werden per .add(mention) zu der entity hinzugefügt.
			Mention currMen = null;
			
			InputStream	in = new FileInputStream("test.xml");
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(in);
			
		
			//StringBuilder spacer = new StringBuilder();
			while (parser.hasNext())
			{
				//System.out.println("Event: "+ parser.getEventType());
				
				switch (parser.getEventType() )
				{
					/**case XMLStreamConstants.START_DOCUMENT:
						System.out.println("START_DOCUMENT: " + parser.getVersion() );
						break;
					**/
					case XMLStreamConstants.END_DOCUMENT:
						System.out.println("END_DOCUMENT: ");
						parser.close();
						break;
					
						
					case XMLStreamConstants.START_ELEMENT:
						//spacer.append("  ");
						switch(parser.getLocalName())
						{
							case "coreference": //equivalent zu entity
								//System.out.println("New Coreference"); 
								currEnt = new Entity();
							break;
								
							case "mention":
								currMen = new Mention();
								if(parser.getAttributeValue(0) != null){currMen.setRepresentative(true);} //set if <mention representative = "true">
							break; // wie wird das dann in der ToString abgefragt? NACHSCHAUEN!
							
							case "start":
								currMen.setWordNumberStart(Integer.parseInt(parser.getElementText()));
								System.out.println("Variable currMen.WordNRStart: "+ currMen.getWordNumberStart());
							break;
								
							case "end":
								currMen.setWordNumberEnd(Integer.parseInt(parser.getElementText()));
								System.out.println("Variable currMen.end: "+ currMen.getWordNumberEnd());
							break;
							
							case "head":
								currMen.setWordNumberHead(Integer.parseInt(parser.getElementText()));
								System.out.println("Variable currMen.WordNRHead: "+currMen.getWordNumberHead());
							break;
							
							case "text":
								currMen.setTextMention(parser.getElementText());
								System.out.println("Variable currMen.Text: "+ currMen.getTextMention()); 
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
							
							case "coreferences": //Was soll hier passieren? Liste zuende?
								
							break;
							
							case "mention":
								currEnt.add(currMen); //WArum geht das nicht?
								System.out.println("Close Mention: " + currEnt.iterator()); // wtf muss weg
							break;	
								
							default:
							break;
						}
					break;
				}
				parser.next();
			}
			for (Mention m: currEnt){System.out.println(m.toString());}
			for (Entity e: entities){System.out.println(e.toString());}
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (XMLStreamException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		new Parser().parse();
	}
}
