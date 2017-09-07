//package wordNet;

import java.util.ArrayList;

//import edu.princeton.cs.algs4.Digraph;
//import edu.princeton.cs.algs4.DirectedCycle;
//import edu.princeton.cs.algs4.Queue;
//import edu.princeton.cs.introcs.In;
//import edu.princeton.cs.introcs.StdOut;

public class WordNet {
	
	   private Digraph wordGraph;
	   private int V;
	   
	   private ArrayList<Integer> id;
	   private ArrayList<String> noun;

	   // constructor takes the name of the two input files
	   public WordNet(String synsets, String hypernyms) 
	   {
		   //read hypernyms.txt
		   this.V = 0;
		   ArrayList<Integer> s = new ArrayList<Integer>();
		   ArrayList<Integer> d = new ArrayList<Integer>();
		   In h = new In(hypernyms);
		   while (h.hasNextLine()) {
			   // 1,0,2
			   String line = h.readLine();
			   String[] line1 = line.split(",");
			   for (int i=1;i<line1.length;i++) {
				   s.add(Integer.parseInt(line1[0]));
				   d.add(Integer.parseInt(line1[i]));
				   if (Integer.parseInt(line1[0]) > this.V) this.V = Integer.parseInt(line1[0]);
				   if (Integer.parseInt(line1[1]) > this.V) this.V = Integer.parseInt(line1[1]);
			   }
		   }
		  
		  wordGraph = new Digraph(this.V+1);  //construct V points directed graph
		  for (int j = 0;j<s.size();j++) {
			  wordGraph.addEdge(s.get(j), d.get(j));
		  }
		  //check for multi root
		  int rootNum = 0;
		  for (int i=0;i<wordGraph.V();i++) {
			  if (! wordGraph.adj(1).iterator().hasNext()) rootNum++;
		  }
		  
		  if (rootNum >=2) throw new java.lang.IllegalArgumentException();
		  
		  //check for cycle
		  DirectedCycle dc = new DirectedCycle(wordGraph);
		  if (dc.hasCycle()) throw new java.lang.IllegalArgumentException();
		  
		 //read synsets.txt;
		  this.id = new ArrayList<Integer>();
		  this.noun = new ArrayList<String>();
		  In syn = new In(synsets);
		  while (syn.hasNextLine()) {
			  String line_syn = syn.readLine();
			  String[] line_syn1 = line_syn.split(",");
			  String[] nouns = line_syn1[1].split(" ");
			  for (int k=0;k<nouns.length;k++) {
				  this.id.add(Integer.parseInt(line_syn1[0]));
				  this.noun.add(nouns[k]);
				  //System.out.println(Integer.parseInt(line_syn1[0])+"->"+nouns[k]);
			  }
		  }
		  //System.out.println(this.id.size());
		  //System.out.println(this.noun.size());
		  
		   
	   }

	   // returns all WordNet nouns
	   public Iterable<String> nouns()
	   {
		   Queue<String> q = new Queue<String>();
		   for (int i = 0;i<this.noun.size();i++) 
		   {
			   q.enqueue(this.noun.get(i));
		   }
		   
		   return q; 
	   }

	   // is the word a WordNet noun?
	   public boolean isNoun(String word)
	   {
		   boolean flag = false;
		   for (int i = 0;i<this.noun.size();i++) 
		   {
			   if (this.noun.get(i).equals(word))
				   flag = true;
		   }
		   return flag;
	   }

	   // distance between nounA and nounB (defined below)
	   public int distance(String nounA, String nounB)
	   {
		   SAP sap = new SAP(this.wordGraph);

		   if (!isNoun(nounA) | !isNoun(nounB)) {
			   throw new IllegalArgumentException();
		   }
		   
		   return sap.length(getID(nounA), getID(nounB));
		   
	   }

	   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	   // in a shortest ancestral path (defined below)
	   public String sap(String nounA, String nounB)
	   {
		   if (!isNoun(nounA) | !isNoun(nounB)) {
			   throw new IllegalArgumentException();
		   }
		   
		   SAP sap = new SAP(this.wordGraph);
		   //Queue<String> nameQueue = new Queue<String>();
		   //String name = null;
		   StringBuilder name = new StringBuilder();
		   int id = sap.ancestor(getID(nounA), getID(nounB));
		   //System.out.println("id= "+id);
		   for (int i=0;i<this.id.size();i++) {
			   if (this.id.get(i) == id) {
				   //nameQueue.enqueue(this.noun.get(i));
				   name.append(this.noun.get(i));
				   name.append(" ");
			   }
		   }
		   
		   //for (String one : nameQueue) {
		   //	   name = one+" "+name;
		   // }
		   //StringBuilder s = new StringBuilder();
		   //s.append("xyz");
		   //s.append("abc");
		   //System.out.println(s);
		   return name.toString();
	   }
	   
	   private Iterable<Integer> getID (String in)
	   {
		   boolean flag=false;
		   
		   Queue<Integer> qId = new Queue<Integer>();

		   for (int i = 0;i<this.noun.size();i++) 
			   if (this.noun.get(i).equals(in)) {
				   flag = true;
				   qId.enqueue(this.id.get(i));
			   }
		   if (flag) return qId;
		   else return null;
			  
	   }

	   // do unit testing of this class
	   public static void main(String[] args)
	   {
//		   WordNet wd = new WordNet("synsets.txt", "hypernyms.txt");
//		   WordNet wd = new WordNet("synsets3.txt", "hypernyms3InvalidCycle.txt");
//		   WordNet wd = new WordNet("synsets3.txt", "hypernyms3InvalidTwoRoots.txt");
		   WordNet wd = new WordNet("synsets15.txt", "hypernyms15Tree.txt");
		   StdOut.println(wd.distance("a","invalid"));
		   
		   //StdOut.println(wd.isNoun("worm"));
		   //StdOut.println(wd.getID("worm"));
		   //StdOut.println(wd.distance("white_marlin", "mileage"));
		   //StdOut.println(wd.distance("catafalque", "hardcover"));
		   //StdOut.println(wd.sap("catafalque", "hardcover"));
		   //StdOut.println(wd.distance("American_water_spaniel", "histology"));
		   //StdOut.println(wd.distance("Brown_Swiss", "barrel_roll"));
		   //StdOut.println(wd.distance("phosphoprotein", "electric_doublet"));
		   //StdOut.println(wd.sap("phosphoprotein", "electric_doublet"));
		   //
		   //WordNet wd = new WordNet("synsets15.txt", "hypernyms15Path.txt");
		   //StdOut.println(wd.distance("a", "o"));
		   
	   }
	}
