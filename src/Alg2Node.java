
public class Alg2Node extends Node {

	private boolean occupancy;
	public boolean getOccupancy(){
		return occupancy;
	}
	public void setOccupancy(boolean val){
		occupancy = val;
	}

	// get a value at the leaf
	public boolean getLeafValue(double x, double y, double z){
		// out of range
		if(x < 0 || 1 < x || y < 0 || 1 < y || z < 0 || 1 < z){
			System.out.println("ERROR in getLeafValue: out of range");
			return false;
		}
		
		if(this.hasChildren()==false){// if no children
			return occupancy;
		}else{// otherwise
			// index to the next child
			int i=0;
			// x, y, z with respect to the next child
			double x_next, y_next, z_next;
			
			// based on x, y, z, the next child is determined
			if(x > 0.5){
				i+=1;
				x_next = (x-.5)*2;
			}else{
				x_next = x*2;
			}
			if(y > 0.5){
				i+=2;
				y_next = (y-.5)*2;
			}else{
				y_next = y*2;
			}
			if(z > 0.5){
				i+=4;
				z_next = (z-.5)*2;
			}else{
				z_next = z*2;
			}
			return ((Alg2Node)this.children[i]).getLeafValue(x_next, y_next, z_next);
		}
	}
	
	// constructor for root node
	// creates a node with a random value
	public Alg2Node(Alg2Tree T){
		super(T);
		occupancy = (Math.random()>= .5)? true: false;
	}
	// constructor for root node
	// creates a node with a specific value
	public Alg2Node(Alg2Tree T, boolean val){
		super(T);
		occupancy = val;
	}
	
	// constructor
	// creates a node with a random value
	public Alg2Node(Alg2Node p){
		super(p,p.getTree());
		occupancy = (Math.random()>= .5)? true: false;
	}
	// constructor
	// creates a node with a specific value
	public Alg2Node(Alg2Node p, boolean val){
		super(p,p.getTree());
		occupancy = val;
	}
	
	// expansion
	// copies a value
	public void expand_spcval() {
		// if depth is maximum or more
		if(this.depth >= h){
			// do nothing and return
			return;
		}
		// create children
		this.children = new Alg1Node[8];
		// create each child
		for(int i = 0; i < 8; i++){
			this.children[i] = new Alg2Node(this,occupancy);
		}
	}

	// expansion
	// a value is determined randomly
	public void expand_randval(){
		// if depth is maximum or more
		if(this.depth >= h){
			// do nothing and return
			return;
		}
		// create children
		this.children = new Alg2Node[8];
		// create each child
		for(int i = 0; i < 8; i++){
			this.children[i] = new Alg2Node(this);
		}
	}
	
	/*
	// update parent
	public void updateParent(Alg2Node parent){
		this.parent = parent;
	}

	// delete subtree
	public void deleteSubtree(){
		for(int i = 0; i < 8; i++){
			((Alg2Node)this.children[i]).updateParent(null);
			this.children[i] = null;
		}
		this.children = null;
	}*/
}
