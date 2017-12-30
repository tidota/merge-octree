
public class Alg1Node extends Node {

	// log odds
	private double logodds;
	public double getLogodds(){
		return logodds;
	}
	public void setLogodds(double val){
		logodds = val;
	}
	
	// get a value at the leaf
	public double getLeafValue(double x, double y, double z){
		// out of range
		if(x < 0 || 1 < x || y < 0 || 1 < y || z < 0 || 1 < z){
			System.out.println("ERROR in getLeafValue: out of range");
			return 0;
		}
		
		if(this.hasChildren()==false){// if no children
			return logodds;
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
			return ((Alg1Node)this.children[i]).getLeafValue(x_next, y_next, z_next);
		}
	}
	
	// constructor for root with a random value
	public Alg1Node(Alg1Tree T){
		super(T);
		// assign a value
		logodds = (Math.random()*2-1)*300.0;
	}
	// constructor for root with a specific value
	public Alg1Node(Alg1Tree T, double copiedLogodds){
		super(T);
		// assign a value
		logodds = copiedLogodds;
	}

	// constructor
	// creates a node with a random value
	public Alg1Node(Alg1Node p){
		super(p,p.getTree());
		// assign a value
		logodds = (Math.random()*2-1)*300.0;
	}
	// constructor
	// creates a node with a specific value
	public Alg1Node(Alg1Node p, double copiedLogodds){
		super(p,p.getTree());
		// assign a value
		logodds = copiedLogodds;
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
		this.children = new Alg1Node[8];
		// create each child
		for(int i = 0; i < 8; i++){
			this.children[i] = new Alg1Node(this,logodds);
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
		this.children = new Alg1Node[8];
		// create each child
		for(int i = 0; i < 8; i++){
			this.children[i] = new Alg1Node(this);
		}		
	}
}
