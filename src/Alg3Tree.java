
public class Alg3Tree extends Tree {

	public Alg3Tree(){
		this.setRoot(new Alg3Node(this));
	}
	public Alg3Tree(double val){
		this.setRoot(new Alg3Node(this,val));
	}
	
	// the first tree will be updated by the second
	// some subtree of the second tree could be moved to the first tree
	protected int mergeSubtrees(Node n1, Node n2) {
		Alg3Node node1 = (Alg3Node)n1;
		Alg3Node node2 = (Alg3Node)n2;
		
		// count of calls of this function
		int count = 1;
		
		// both have subtrees
		if(node1.hasChildren() && node2.hasChildren()){
			// update the value of node1
			node1.setDLogodds(node1.getDLogodds()+node2.getDLogodds());
			// call merging proc on each pair
			for(int i = 0; i < 8; i++){
				count += mergeSubtrees((Alg3Node)node1.getChild(i),(Alg3Node)node2.getChild(i));
			}
		// node1 is a leaf and node2 has a subtree
		}else if(node2.hasChildren()){
			// update the value of node2
			node2.setDLogodds(node1.getDLogodds()+node2.getDLogodds());
			// replace node1 with node2
			this.replaceChild(node1, node2);
		}else{
			// if node1 has a subtree and node2 is a leaf
			// or both are leaves

			// update the value of node1
			node1.setDLogodds(node1.getDLogodds()+node2.getDLogodds());
		}
		
		return count;
		
	}
}
