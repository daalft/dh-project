/**
 * 
 */
package preface.parser.element.coreference;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Julian
 *	Class holds all information about the index files for a book.
 */
public class Chain {
	private String text;
	private List<Entity> entities;

	

	public Chain() {
		entities = new ArrayList<Entity>();
	}
	public void add(Entity arg0) {
		entities.add(arg0);
	}
	public Entity get(int arg0) {
		return entities.get(arg0);
	}
	public Iterator<Entity> iterator() {
		return entities.iterator();
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Entity> getEntities() {
		return entities;
	}
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	@Override
	public String toString() {
		return "Chain [text=" + text + ", entities=" + entities + "]";
	}
	
	
}
