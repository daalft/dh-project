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
public class Chain implements Iterable<EntityReference> {
	
	private String text;
	private List<EntityReference> entityRefs;

	public Chain() {
		entityRefs = new ArrayList<EntityReference>();
	}
	
	public void add(EntityReference ref) {
		entityRefs.add(ref);
	}
	
	public EntityReference get(int index) {
		return entityRefs.get(index);
	}
	
	public Iterator<EntityReference> iterator() {
		return entityRefs.iterator();
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public List<EntityReference> getEntityReferences() {
		return entityRefs;
	}
	
	public void setEntityReferences(List<EntityReference> entityRefs) {
		this.entityRefs = entityRefs;
	}
	
	@Override
	public String toString() {
		return "Chain [text=" + text + ", entities=" + entityRefs + "]";
	}
}
