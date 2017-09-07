//package wordNet;

//import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
//import edu.princeton.cs.algs4.Digraph;
//import edu.princeton.cs.introcs.In;
//import edu.princeton.cs.introcs.StdIn;
//import edu.princeton.cs.introcs.StdOut;

public class SAP {
	
	private Digraph g;
	private int ancestor;
	private boolean ancestorFound;
	private int ancestor_group;
	private boolean ancestor_groupFound;

	   // constructor takes a digraph (not necessarily a DAG)
	   public SAP(Digraph G) {
		  this.g = G; 
		  this.ancestor = -1;
		  this.ancestorFound = false;
		  this.ancestor_group= -1;
		  this.ancestor_groupFound = false;
	   }

	   // length of shortest ancestral path between v and w; -1 if no such path
	   public int length(int v, int w)
	   {
		   if (v<0 | w<0) {
			   throw new java.lang.IndexOutOfBoundsException();
		   }
		   
	       this.ancestor = -1;
		   this.ancestorFound = false;
		   BreadthFirstDirectedPaths bfs_v = new BreadthFirstDirectedPaths(this.g, v);
		   BreadthFirstDirectedPaths bfs_w = new BreadthFirstDirectedPaths(this.g, w);
		   int tmpDist = Integer.MAX_VALUE;
		   int[] dist_vw = new int[this.g.V()];    //sum of v->i and w->i
		   for (int i=0;i<g.V();i++) {
			   if (bfs_v.hasPathTo(i) & bfs_w.hasPathTo(i)) {
				   this.ancestorFound = true;
				   dist_vw[i] = bfs_v.distTo(i) + bfs_w.distTo(i);
				   if (dist_vw[i] < tmpDist) {
					   tmpDist = dist_vw[i];
					   this.ancestor = i;
				   }
			   }
		   }
		   
		   if (this.ancestorFound) {
			  return tmpDist; 
		   } else {
			   return -1;
		   }
	   }

	   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	   public int ancestor(int v, int w)
	   {
		   if (v<0 | w<0) {
			   throw new java.lang.IndexOutOfBoundsException();
		   }
	       this.ancestor = -1;
		   this.ancestorFound = false;
		   BreadthFirstDirectedPaths bfs_v = new BreadthFirstDirectedPaths(this.g, v);
		   BreadthFirstDirectedPaths bfs_w = new BreadthFirstDirectedPaths(this.g, w);
		   int tmpDist = Integer.MAX_VALUE;
		   int[] dist_vw = new int[this.g.V()];    //sum of v->i and w->i
		   for (int i=0;i<g.V();i++) {
			   if (bfs_v.hasPathTo(i) & bfs_w.hasPathTo(i)) {
				   this.ancestorFound = true;
				   dist_vw[i] = bfs_v.distTo(i) + bfs_w.distTo(i);
				   if (dist_vw[i] < tmpDist) {
					   tmpDist = dist_vw[i];
					   this.ancestor = i;
				   }
			   }
		   }
		   if (this.ancestorFound) {
			   return this.ancestor;
		   } else {
			   return -1;
		   }
	   }

	   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	   public int length(Iterable<Integer> v, Iterable<Integer> w)
	   {
		   if (v==null | w==null) {
			   throw new java.lang.NullPointerException();
		   }
		   this.ancestor_group= -1;
		   this.ancestor_groupFound = false;
		   BreadthFirstDirectedPaths bfs_group_v = new BreadthFirstDirectedPaths(this.g, v);
		   BreadthFirstDirectedPaths bfs_group_w = new BreadthFirstDirectedPaths(this.g, w);
		   int tmpDist = Integer.MAX_VALUE;
		   int[] dist_groupvw = new int[this.g.V()];
		   for (int i=0;i<g.V();i++) {
			   if (bfs_group_v.hasPathTo(i) & bfs_group_w.hasPathTo(i)) {
				   dist_groupvw[i] = bfs_group_v.distTo(i) + bfs_group_w.distTo(i);
				   this.ancestor_groupFound = true;
				   if (dist_groupvw[i] < tmpDist)
				   {
					  tmpDist = dist_groupvw[i];
					  this.ancestor_group = i; 
				   }
			   }
			   
		   }
		   
		   if (this.ancestor_groupFound) {
			   return tmpDist;
		   } else {
			   return -1;
		   }
	   }

	   // a common ancestor that participates in shortest ancestral path; -1 if no such path
	   public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
	   {
		   if (v==null | w==null) {
			   throw new java.lang.NullPointerException();
		   }
		   this.ancestor_group= -1;
		   this.ancestor_groupFound = false;
		   BreadthFirstDirectedPaths bfs_group_v = new BreadthFirstDirectedPaths(this.g, v);
		   BreadthFirstDirectedPaths bfs_group_w = new BreadthFirstDirectedPaths(this.g, w);
		   int tmpDist = Integer.MAX_VALUE;
		   int[] dist_groupvw = new int[this.g.V()];
		   for (int i=0;i<g.V();i++) {
			   if (bfs_group_v.hasPathTo(i) & bfs_group_w.hasPathTo(i)) {
				   dist_groupvw[i] = bfs_group_v.distTo(i) + bfs_group_w.distTo(i);
				   this.ancestor_groupFound = true;
				   if (dist_groupvw[i] < tmpDist)
				   {
					  tmpDist = dist_groupvw[i];
					  this.ancestor_group = i; 
				   }
			   }
			   
		   }
		   if (this.ancestor_groupFound) {
			   return this.ancestor_group;
		   } else {
			   return -1;
		   }
	   }

	   // do unit testing of this class
	   public static void main(String[] args) {
		    In in = new In("digraph-wordnet.txt");
//		    In in = new In("digraph3.txt");
//		    In in = new In("hypernyms15Path.txt");
//		    In in = new In(args[0]);
		    Digraph G = new Digraph(in);
		    SAP sap = new SAP(G);
		    while (!StdIn.isEmpty()) {
		        int v = StdIn.readInt();
		        int w = StdIn.readInt();
		        int length   = sap.length(v, w);
		        int ancestor = sap.ancestor(v, w);
		        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		    }
		}
	}