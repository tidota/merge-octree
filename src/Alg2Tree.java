
public class Alg2Tree extends Tree {
	
	public Alg2Tree(){
		this.setRoot(new Alg2Node(this));
	}
	public Alg2Tree(boolean val){
		this.setRoot(new Alg2Node(this,val));
	}
	
	// the first tree will be updated by the second
	// some subtree of the second tree could be moved to the first tree
	protected int mergeSubtrees(Node n1, Node n2) {
		Alg2Node node1 = (Alg2Node)n1;
		Alg2Node node2 = (Alg2Node)n2;
		
		// count of calls of this function
		int count = 1;
		
		
		// both have subtrees
		if(node1.hasChildren() && node2.hasChildren()){
			// call merging proc on each pair
			for(int i = 0; i < 8; i++){
				count += mergeSubtrees((Alg2Node)node1.getChild(i),(Alg2Node)node2.getChild(i));
			}
		// node1 has a subtree and node2 is a leaf
		}else if(node1.hasChildren()){
			// if node2 is free
			if(node2.getOccupancy() == false){
				// replace
				this.replaceChild(node1, node2);
			}
			// if node2 is occupied
				// do nothing
		// node1 is a leaf and node2 has a subtree
		}else if(node2.hasChildren()){
			// if node1 is occupied
			if(node1.getOccupancy() == true){
				// replace
				this.replaceChild(node1, node2);
			}
			// if node1 is free
				// do nothing
		// both are leaves
		}else{
			// merge them
			// occ1 occ2 result
			// T    T    T
			// T    F    F
			// F    T    F
			// F    F    F
			node1.setOccupancy(node1.getOccupancy() & node2.getOccupancy());
		}
		
		return count;
		
	}
	
}
