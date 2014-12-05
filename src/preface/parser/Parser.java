package preface.parser;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.*;

import preface.parser.element.coreference.Entity;
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
			InputStream	in = new FileInputStream("test.xml");
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(in);
		
			StringBuilder spacer = new StringBuilder();
			
			while (parser.hasNext())
			{
				System.out.println("Event: "+ parser.getEventType());
				
				switch (parser.getEventType() )
				{
					case XMLStreamConstants.START_DOCUMENT:
						System.out.println("START_DOCUMENT: " + parser.getVersion() );
						break;
					
					case XMLStreamConstants.END_DOCUMENT:
						System.out.println("END_DOCUMENT: ");
						parser.close();
						break;
					
					case XMLStreamConstants.NAMESPACE:
						System.out.println("NAMESPACE: " + parser.getNamespaceURI() );
						break;
						
					case XMLStreamConstants.START_ELEMENT:
						spacer.append("  ");
						if(parser.getLocalName()== "start")
							System.out.println("Wooho push start"); // here should the element be initialised instead of printing it
						System.out.println(spacer + "START_ELEMENT: " + parser.getLocalName());
						for (int i = 0; i < parser.getAttributeCount(); i++)
							System.out.println(spacer + "Attribut: "
												+ parser.getAttributeLocalName(i) 
												+" Wert: "+ parser.getAttributeValue(i) );
						break;
						
					case XMLStreamConstants.CHARACTERS:
						if (! parser.isWhiteSpace() )
							System.out.println(spacer + " CHARACTERS: " + parser.getText());
						break;
						
					case XMLStreamConstants.END_ELEMENT:
						System.out.println(spacer + "END_ELEMENT: " + parser.getLocalName() );
						spacer.delete((spacer.length() - 2), spacer.length() );
						break;
						
					default: 
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
	
	public static void main(String args[]) {
		new Parser().parse();
	}
}
