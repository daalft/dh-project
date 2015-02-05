package preface.parser.element.coreference;
/**
 * Represents a reference to an entity which is unambiguously defined by the chapter number and coreference ID
 * 
 * @author David
 *
 */
public class EntityReference {
	private int id;
	private int chapterNumber;
	
	public EntityReference() {
	
	}
	
	public EntityReference(int id, int chapterNumber) {
		this.id = id;
		this.chapterNumber = chapterNumber;
	}
	
	public int getId () {
		return id;
	}
	
	public int getChapterNumber () {
		return chapterNumber;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setChapterNumber(int chapNum) {
		this.chapterNumber = chapNum;
	}
	

}
