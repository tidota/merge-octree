
public class Alg3Node extends Node {

	
	
	// differential log odds
	private double dlogodds;
	public double getDLogodds(){
		return dlogodds;
	}
	public void setDLogodds(double val){
		dlogodds = val;
	}

	// get a value at the leaf
	public double getLeafValue(double x, double y, double z){
		// out of range
		if(x < 0 || 1 < x || y < 0 || 1 < y || z < 0 || 1 < z){
			System.out.println("ERROR in getLeafValue: out of range");
			return 0;
		}
		
		if(this.hasChildren()==false){// if no children
			return dlogodds;
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
			return dlogodds + ((Alg3Node)this.children[i]).getLeafValue(x_next, y_next, z_next);
		}
	}
	
	// constructor for root
	// creates a node with a random value
	public Alg3Node(Alg3Tree T){
		super(T);
		// assign a value
		dlogodds = (Math.random()*2-1)*100.0;
	}
	// constructor for root
	// creates a node with a specific value
	public Alg3Node(Alg3Tree T, double copiedDLogodds){
		super(T);
		// assign a value
		dlogodds = copiedDLogodds;
	}
	
	// constructor
	// creates a node with a random value
	public Alg3Node(Alg3Node p){
		super(p,p.getTree());
		// assign a value
		dlogodds = (Math.random()*2-1)*100.0;
	}
	// constructor
	// creates a node with a specific value
	public Alg3Node(Alg3Node p, double copiedDLogodds){
		super(p,p.getTree());
		// assign a value
		dlogodds = copiedDLogodds;
	}
	
	// expansion
	// value is copied
	public void expand_spcval(){
		// if depth is maximum or more
		if(this.depth >= h){
			// do nothing and return
			return;
		}
		// create children
		this.children = new Alg3Node[8];
		// create each child
		for(int i = 0; i < 8; i++){
			this.children[i] = new Alg3Node(this,0);
		}
	}
	// expansion
	// value is randomly determined
	public void expand_randval(){
		// if depth is maximum or more
		if(this.depth >= h){
			// do nothing and return
			return;
		}
		// create children
		this.children = new Alg3Node[8];
		// create each child
		for(int i = 0; i < 8; i++){
			this.children[i] = new Alg3Node(this);
		}		
	}
	
	/*
	// update parent
	public void updateParent(Alg3Node parent){
		this.parent = parent;
	}
	// replace child with another child
	public void replaceChild(Alg3Node node2remove, Alg3Node node2ins){
		for(int i = 0; i < 8; i++){
			if(this.children[i] == node2remove){
				// replace
				this.children[i] = node2ins;
				node2ins.updateParent(this);
			}
		}
	}*/
}
