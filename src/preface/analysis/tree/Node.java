package preface.analysis.tree;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a node in a tree
 * @author David
 *
 * @param <T> generic node class
 */
public class Node<T> {

	protected Node<?> parent;
	protected List<Node<?>> children;
	protected T content;

	/**
	 * No argument constructor
	 */
	public Node () {
		children = new ArrayList<Node<?>>();
	}
	
	/**
	 * Constructor with content
	 * @param content content
	 */
	public Node (T content) {
		this();
		this.content = content;
	}

	/**
	 * Set the parent of this node
	 * @param parent parent node
	 */
	private void setParent (Node<?> parent) {
		this.parent = parent;
	}
	
	/**
	 * Adds a child to this node
	 * <br/>
	 * <br/>
	 * (Automatically sets the parent of the <code>child</code>
	 * to <code>this</code> node)
	 * @param child child node
	 */
	public void addChild (Node<?> child) {
		child.setParent(this);
		children.add(child);
	}
	
	/**
	 * Checks whether this node has child nodes
	 * @return true if this node has child nodes
	 */
	public boolean hasChildren () {
		return children.size() > 0;
	}
	
	/**
	 * Returns the node at position i
	 * @param i position
	 * @return node at position i
	 */
	public Node<?> get (int i) {
		return children.get(i);
	}
	
	/**
	 * Returns the content of this node
	 * @return content
	 */
	public T getContent () {
		return content;
	}
	
	/**
	 * Checks whether this node contains
	 * the specified node as child
	 * @param n node
	 * @return true if this node contains specified node
	 */
	public boolean contains (Node<?> n) {
		for (Node<?> child : this.children) {
			if (child.equals(n))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns the index of the specified child node
	 * @param n node
	 * @return index
	 */
	public int indexOf (Node<?> n) {
		return children.indexOf(n);
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder();
		if (content != null)
			sb.append("Content: ").append(content).append("\n");
		sb.append("Child count: ").append(children.size());
		return sb.toString();
	}
}
