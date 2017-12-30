import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


class Result{
	double P; // probability to extend nodes (just for record)
	double ave_n1; // average number of nodes of tree1
	double ave_n2; // average number of nodes of tree2
	double ave_n3; // average number of merged nodes
	double ave_calls; // average calls of the recursion
	double ave_time_mrg; // average time of merging two trees
	double ave_time_acc; // average time to get a value of a leaf
}

public class TreeTest {

	public static void main(String[] args) {
		ArrayList<Result> alg1 = new ArrayList<Result>();
		ArrayList<Result> alg2 = new ArrayList<Result>();
		ArrayList<Result> alg3 = new ArrayList<Result>();
		
		for(int i = 1; i <= 20; i++){
			System.out.println("===================================");
			double P = i*0.05;
			System.out.println("P = " + P);
			// set a probability to extend nodes
			Node.setP(P);
			
			// test
			test(alg1,alg2,alg3);
		}
		
		display(alg1,"Algo1.csv");
		display(alg2,"Algo2.csv");
		display(alg3,"Algo3.csv");
		
	}
	public static void display(ArrayList<Result> alg, String filename) {
		try{
			FileWriter fw = new FileWriter(new File(filename));
			
			Iterator<Result> it = alg.iterator();
			while(it.hasNext()){
				Result res = it.next();
				fw.write(res.P + "," +
						res.ave_n1 + "," +
						res.ave_n2 + "," +
						res.ave_n3 + "," +
						res.ave_calls + "," +
						res.ave_time_mrg + "," +
						res.ave_time_acc + System.lineSeparator());
			}
			
			fw.close();
		}catch(java.io.IOException e){
			System.out.println("IOException: " + e);
			System.exit(-1);
		}
	}
	public static void test(ArrayList<Result> alg1, ArrayList<Result> alg2, ArrayList<Result> alg3){
		int ntrials = 100000;
		int ntrials_acc = 0;
		long calls; // number of calls
		long n1; // number of nodes of tree1
		long n2; // number of nodes of tree2
		long n3; // number of merged nodes
		long start, end; // for timing
		long time_mrg; // cumulative elapsed time to mrg (in ms)
		long time_acc; // cumulative elapsed time to access (in ms)
		
		long depth; // depth
		Result res;
		
		Random rand = new Random(15); // random generator
		
		System.out.println("=== algorithm 1 ===");
		calls = 0;
		n1 = 0;
		n2 = 0;
		n3 = 0;
		time_mrg = 0;
		time_acc = 0;
		depth = 0;
		for(int i = 0; i < ntrials; i++){
			// creating trees
			Alg1Tree T1 = new Alg1Tree(); // create an initial tree
			T1.randomTree(); // create a random tree structure
			Alg1Tree T2 = new Alg1Tree(); // create an initial tree
			T2.randomTree(); // create a random tree structure
			n1 += T1.getTotalNumNodes();
			n2 += T2.getTotalNumNodes();

			// merging
			start = System.currentTimeMillis();
			calls += T1.mergeTrees(T2);
			end = System.currentTimeMillis();
			time_mrg += (end - start);
			
			n3 += T1.getTotalNumNodes();

			// accessing
			Alg1Node node = (Alg1Node)T1.getRoot();
			double x = rand.nextDouble();
			double y = rand.nextDouble();
			double z = rand.nextDouble();
			start = System.currentTimeMillis();
			for(int j = 0; j < ntrials_acc; j++){
				double buff = node.getLeafValue(x, y, z);
				//depth += node.randAcc(x, y, z);
			}
			end = System.currentTimeMillis();
			time_acc += (end - start);
			
			int dem = ntrials/100;
			if(dem == 0) dem = 1;
			if(i % dem == 0)
				System.out.print(".");
		}
		System.out.println("");
		res = new Result();
		res.P = Node.getP();
		res.ave_n1 = (double)n1/ntrials;
		res.ave_n2 = (double)n2/ntrials;
		res.ave_n3 = (double)n3/ntrials;
		res.ave_calls = (double)calls/ntrials;
		res.ave_time_mrg = time_mrg * (1000000.0/ntrials);
		res.ave_time_acc = time_acc * (1000000.0/ntrials/ntrials_acc);
		System.out.println("ave_mrg: " + res.ave_time_mrg + ", ave_acc: " + res.ave_time_acc);
		alg1.add(res);

		//System.out.println("depth: " + (double)depth/ntrials/ntrials_acc);
		
		System.out.println("=== algorithm 2 ===");
		n1 = 0;
		n2 = 0;
		n3 = 0;
		calls = 0;
		time_mrg = 0;
		time_acc = 0;
		depth = 0;
		for(int i = 0; i < ntrials; i++){
			// creating trees
			Alg2Tree T1 = new Alg2Tree(); // create an initial tree
			T1.randomTree(); // create a random tree structure
			Alg2Tree T2 = new Alg2Tree(); // create an initial tree
			T2.randomTree(); // create a random tree structure
			n1 += T1.getTotalNumNodes();
			n2 += T2.getTotalNumNodes();

			// merging
			start = System.currentTimeMillis();
			calls += T1.mergeTrees(T2);
			end = System.currentTimeMillis();
			time_mrg += (end - start);
			
			n3 += T1.getTotalNumNodes();
			
			// accessing
			Alg2Node node = (Alg2Node)T1.getRoot();
			double x = rand.nextDouble();
			double y = rand.nextDouble();
			double z = rand.nextDouble();
			start = System.currentTimeMillis();
			for(int j = 0; j < ntrials_acc; j++){
				boolean buff = node.getLeafValue(x, y, z);
				//depth += node.randAcc(x, y, z);
			}
			end = System.currentTimeMillis();
			time_acc += (end - start);

			int dem = ntrials/100;
			if(dem == 0) dem = 1;
			if(i % dem == 0)
				System.out.print(".");
		}
		System.out.println("");
		res = new Result();
		res.P = Node.getP();
		res.ave_n1 = (double)n1/ntrials;
		res.ave_n2 = (double)n2/ntrials;
		res.ave_n3 = (double)n3/ntrials;
		res.ave_calls = (double)calls/ntrials;
		res.ave_time_mrg = time_mrg * (1000000.0/ntrials);
		res.ave_time_acc = time_acc * (1000000.0/ntrials/ntrials_acc);
		System.out.println("ave_mrg: " + res.ave_time_mrg + ", ave_acc: " + res.ave_time_acc);
		alg2.add(res);

		//System.out.println("depth: " + (double)depth/ntrials/ntrials_acc);

		System.out.println("=== proposed algorithm ===");
		n1 = 0;
		n2 = 0;
		n3 = 0;
		calls = 0;
		time_mrg = 0;
		time_acc = 0;
		depth = 0;
		for(int i = 0; i < ntrials; i++){
			// creating trees
			Alg3Tree T1 = new Alg3Tree(); // create an initial tree
			T1.randomTree(); // create a random tree structure
			Alg3Tree T2 = new Alg3Tree(); // create an initial tree
			T2.randomTree(); // create a random tree structure
			n1 += T1.getTotalNumNodes();
			n2 += T2.getTotalNumNodes();

			// merging
			start = System.currentTimeMillis();
			calls += T1.mergeTrees(T2);
			end = System.currentTimeMillis();
			time_mrg += (end - start);
			
			n3 += T1.getTotalNumNodes();
			
			// accessing
			Alg3Node node = (Alg3Node)T1.getRoot();
			double x = rand.nextDouble();
			double y = rand.nextDouble();
			double z = rand.nextDouble();
			start = System.currentTimeMillis();
			for(int j = 0; j < ntrials_acc; j++){
				double buff = node.getLeafValue(x, y, z);
				//depth += node.randAcc(x, y, z);
			}
			end = System.currentTimeMillis();
			time_acc += (end - start);

			int dem = ntrials/100;
			if(dem == 0) dem = 1;
			if(i % dem == 0)
				System.out.print(".");
		}
		System.out.println("");
		res = new Result();
		res.P = Node.getP();
		res.ave_n1 = (double)n1/ntrials;
		res.ave_n2 = (double)n2/ntrials;
		res.ave_n3 = (double)n3/ntrials;
		res.ave_calls = (double)calls/ntrials;
		res.ave_time_mrg = time_mrg * (1000000.0/ntrials);
		res.ave_time_acc = time_acc * (1000000.0/ntrials/ntrials_acc);
		System.out.println("ave_mrg: " + res.ave_time_mrg + ", ave_acc: " + res.ave_time_acc);
		alg3.add(res);

		//System.out.println("depth: " + (double)depth/ntrials/ntrials_acc);
	
	}
}
