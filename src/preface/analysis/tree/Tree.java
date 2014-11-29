package preface.analysis.tree;

import java.util.List;
/**
 * Represents a tree (i.e. a list of nodes) with additional
 * tree-pertaining methods
 * @author David
 *
 */
public class Tree {

	Node<?> root;

	/**
	 * No argument constructor
	 */
	public Tree() {

	}

	/**
	 * Sets the root node of this tree
	 * <br/><br/>
	 * No other nodes have to be specified
	 * as the nodes are already interlinked
	 * @param n root node
	 */
	public void setRoot (Node<?> n) {
		root = n;
	}

	/**
	 * Traverses the tree in a depth-first manner
	 * <br/><br/>
	 * If <code>null</code> is specified as the starting
	 * node, the starting node is set to the root of this tree
	 * @param fromNode starting node
	 */
	public void traverse (Node<?> fromNode) {
		if (fromNode == null) {
			fromNode = root;
		}
		System.err.println("Node content: " + fromNode.content);
		if (!fromNode.hasChildren()) {
			// break recursion if there are no children
			// -> this is a leaf node
			return; 
		}
		for (int i = 0; i < fromNode.children.size(); i++) {
			// recursive call for children of this node
			traverse(fromNode.get(i));
		}
	}

	/**
	 * Selects and returns all nodes where
	 * the node content is of the specified class
	 * @param c class
	 * @return nodes of class
	 */
	public <T> List<T> selectNodesByContentClass (Class<T> c) {
		return null;
	}
}
