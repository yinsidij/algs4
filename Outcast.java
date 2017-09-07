//package wordNet;

//import edu.princeton.cs.introcs.In;
//import edu.princeton.cs.introcs.StdOut;

public class Outcast {
	
	   private WordNet wNet;
	   public Outcast(WordNet wordnet)         // constructor takes a WordNet object
	   {
		   this.wNet = wordnet;
	   }
	   public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
	   {
		   int[] d = new int[nouns.length];
		   int max = Integer.MIN_VALUE;
		   int index = -1;
		   
		   for (int i=0;i<nouns.length;i++)
		   {
			   d[i] = 0;
			   for (int j=0;j<nouns.length;j++)
			   {
				  d[i] = d[i] + wNet.distance(nouns[i], nouns[j]);
			   }
			   
			   if (d[i]>max) {
				   index = i;
				   max = d[i];
			   }
		   }
		   
		   return nouns[index];
		   
	   }
	   
	   public static void main(String[] args) {
		    WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
		    Outcast outcast = new Outcast(wordnet);
		    //for (int t = 2; t < args.length; t++) {
		        In in = new In("outcast5.txt");
		        String[] nouns = in.readAllStrings();
		        //for (int i=0;i<nouns.length;i++) {
		        //	StdOut.println(nouns[i]);
		        //	StdOut.println(wordnet.isNoun(nouns[i]));
		        //}
		        StdOut.println("RESULT" + ": " + outcast.outcast(nouns));
		    //}
		}
	}