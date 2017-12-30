
public class Alg1Tree extends Tree {

	public Alg1Tree(){
		this.setRoot(new Alg1Node(this));
	}
	public Alg1Tree(double val){
		this.setRoot(new Alg1Node(this,val));
	}
	
	// merging subtrees rooted at the given nodes
	// the first tree will be updated by the second
	// the second tree is also expanded so that both have the same structure
	protected int mergeSubtrees(Node n1, Node n2){
		Alg1Node node1 = (Alg1Node)n1;
		Alg1Node node2 = (Alg1Node)n2;
		
		// count of calls of this function
		int count = 1;
		
		// both are leaves
		if(node1.isLeaf() && node2.isLeaf()){
			// merge them
			node1.setLogodds(node1.getLogodds()+node2.getLogodds());
		}else{
			// if node1 is not a leaf
			if(node1.isLeaf()){
				// expand
				node1.expand_spcval();
			}
			// if node2 is not a leaf
			if(node2.isLeaf()){
				// expand
				node2.expand_spcval();
			}
			// call merging proc on each pair
			for(int i = 0; i < 8; i++){
				count += mergeSubtrees((Alg1Node)node1.getChild(i),(Alg1Node)node2.getChild(i));
			}
		}
		return count;
		
	}

}
