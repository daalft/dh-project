package parser;

import java.util.ArrayList;
import java.util.List;

import parser.element.coreference.Entity;
import parser.element.text.Text;

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
}
