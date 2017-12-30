
public abstract class Tree {
	// root node
	private Node root;
	public void setRoot(Node node){
		root = node;
	}
	public Node getRoot(){
		return root;
	}
	
	// random structure
	public void randomTree(){
		root.expand_chain();
	}
	
	// get the total number of nodes
	public int getTotalNumNodes(){
		return root.getNumNodes();
	}
	
	// merging function
	//  T2 is the tree to add to the tree
	public int mergeTrees(Tree T2) {
		return mergeSubtrees(this.getRoot(),T2.getRoot());
	}
	// abstract function to merge subtrees in a specific algorithm
	protected abstract int mergeSubtrees(Node node1, Node node2);
	

	// replace child with another child
	public void replaceChild(Node node2remove, Node node2ins){
		// if node2remove is not in the tree, just return
		if(this != node2remove.getTree()){
			System.out.println("no node to replace!!!");
			return;
		}
		
		// get parent
		Node p = node2remove.getParent();
		
		// if node2remove is root
		if(p == null){
			// replace root with node2ins
			this.setRoot(node2ins);
			// update node2ins
			node2ins.setParent(null);
			node2ins.setTree(this);
		}else{
			// replace the corresponding child
			for(int i = 0; i < 8; i++){
				if(p.getChild(i) == node2remove){
					// replace
					p.replaceChild(i, node2ins);
					break;
				}
			}
		}
	}
	
}
