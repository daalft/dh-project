package parser;

import java.util.ArrayList;
import java.util.List;

import parser.element.coreference.Entity;
import parser.element.text.Chapter;

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

	}
}
