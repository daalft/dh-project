package preface.parser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import preface.parser.element.coreference.Entity;
import preface.parser.element.coreference.Mention;
import preface.parser.element.text.Text;
/**
 * Basic STaX parser for wrapping xml into java-classes
 * @author Julian
 *
 */
public class Parser {

	private List<Entity> entities;
	private Text text;
	
	
	public Parser () {
		entities = new ArrayList<Entity>();
		text = new Text();
	}
	
	public List<Entity> getEntities () {
		return entities;
	}
	
	public Text getText () {
		return text;
	}
	
	public void dispose () {
		entities = null;
		text = null;
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
							case "coreference":
								currEnt = new Entity();
								currEnt.setId(Integer.parseInt(parser.getAttributeValue(0))); // übernimmt die ID's aus <coreference id="xx">
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
			//for (Mention m: currEnt){System.out.println(m.toString());}
			for (Entity e: entities){System.out.println("Entities list: " + e.toString());}
			
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
