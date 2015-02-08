package preface.parser.element.text.interfaces;
/**
 * Polariseable interface for things that should be polariseable
 * @author David
 *
 */
public interface Polariseable {

	public String getString();
	public void setPolarity(double d);
	public double getPolarity();
	public boolean isPolarized();
	
}
