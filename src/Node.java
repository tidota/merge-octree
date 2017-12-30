import java.util.Random;


public abstract class Node {
	static Random rand = new Random(10);
	
	// probability to expand
	private static double P = 0.8;
	public static void setP(double newP){
		if(0<=newP && newP <= 1.0){
			P = newP;
		}else{
			P = 1.0;
		}
	}
	public static double getP(){
		return P;
	}
	
	// maximum height
	protected static final int h = 4;
	
	// tree
	protected Tree T;
	public void setTree(Tree newT){
		T = newT;
	}
	public Tree getTree(){
		return T;
	}
	
	// parent node
	protected Node parent;
	public void setParent(Node p){
		parent = p;
	}
	public Node getParent(){
		return parent;
	}
	// list of children
	protected Node[] children;
	public void replaceChild(int i, Node newNode){
		if(0<=i && i < 8 && children != null){
			children[i] = newNode;
			newNode.setParent(this);
		}
	}
	public Node getChild(int i){
		if(0<=i && i < 8){
			return children[i];
		}else{
			return null;
		}
	}
	// get a child given coordinates
	//  x, y, z are coordinates with respect to the space which the node represents
	public Node getChild(double x, double y, double z){
		// out of range
		if(x < 0 || 1 < x || y < 0 || 1 < y || z < 0 || 1 < z){
			return null;
		}
		
		if(this.hasChildren()==false){// if no children
			return this;
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
			return this.children[i].getChild(x_next, y_next, z_next);
		}
	}
	
	// depth from the root
	protected int depth;
	public int getDepth(){
		return depth;
	}
	
	
	// constructor for root
	public Node(Tree newT){
		this(null,newT);
	}
	// constructor
	public Node(Node p, Tree newT){
		T = newT;
		
		if(p == null){ // if it is the root
			parent = null;
			depth = 0;
		}else{ // if it is an internal or leaf
			parent = p;
			depth = p.getDepth() + 1;
		}
		children = null;
	}

	// returns true if it has children
	public boolean hasChildren(){
		if(children == null)
			return false;
		else
			return true;
	}
	// returns true if it is a leaf
	public boolean isLeaf(){
		if(children == null)
			return true;
		else
			return false;
	}
	
	// === statistics === //
	// count all nodes including the node itself
	public int getNumNodes(){
		int count = 1; // representing the node itself
		
		// if it has children
		if(children != null){
			for(int i = 0; i < 8; i++){
				// count all nodes in each subtree
				count += this.children[i].getNumNodes();
			}
		}
		return count;
	}


	// === regular expansion === //
	// copy the parent's value to the children
	public abstract void expand_spcval();
	
	// === random expansion === //
	// value is determined randomly
	public abstract void expand_randval();
	
	// === random expansion chain === //
	public void expand_chain(){
		// if depth is less than to the maximum height - 1 and it is a leaf
		if(depth < h && children == null){
			// get random number
			double rand = Node.rand.nextDouble();//Math.random();
			
			// if rand is less than P
			if(rand <= P){
				// expand the node
				expand_randval();
				// randomly expand each child
				for(int i = 0; i < 8; i++)
					children[i].expand_chain();
			}
		}
	}
	// randomly access a leaf and return the depth
	public int randAcc(double x, double y, double z){
		// out of range
		if(x < 0 || 1 < x || y < 0 || 1 < y || z < 0 || 1 < z){
			System.out.println("ERROR in getLeafValue: out of range");
			return 0;
		}
		
		if(this.hasChildren()==false){// if no children
			return 0;
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
			return 1+this.children[i].randAcc(x_next, y_next, z_next);
		}
	}
}
