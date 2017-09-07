import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.UF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
		
		private boolean[][] isOpen;
		private int N;
		private WeightedQuickUnionUF uf ;
		private WeightedQuickUnionUF uf1 ;  // one with no wash back
	   public Percolation(int N)               // create N-by-N grid, with all sites blocked
	   {
		   if (N<=0) throw new java.lang.IllegalArgumentException();
		   isOpen = new boolean[N][N];
		   uf = new WeightedQuickUnionUF(N*N+2);
		   uf1 = new WeightedQuickUnionUF(N*N+1);
		   this.N = N;
		   for (int i=0;i<N;i++)
			   for (int j=0;j<N;j++)
				   isOpen[i][j]=false;
		   
	   }
	   public void open(int i, int j)          // open site (row i, column j) if it is not open already
	   {
		   if (!isOpen[i-1][j-1]) {
			   isOpen[i-1][j-1]=true; // test case from 1 2 3 4 .. 10
			   if (isValid(i-1, j)&&isOpen(i-1, j)) {
				   uf.union(toOneD(i, j), toOneD(i-1, j));
				   uf1.union(toOneD(i, j), toOneD(i-1, j));
			   }
			   if (isValid(i+1, j)&&isOpen(i+1, j)) {
				   uf.union(toOneD(i, j), toOneD(i+1, j)); 
				   uf1.union(toOneD(i, j), toOneD(i+1, j)); 
			   }
			   if (isValid(i, j-1)&&isOpen(i, j-1)) {
				   uf.union(toOneD(i, j), toOneD(i, j-1)); 
				   uf1.union(toOneD(i, j), toOneD(i, j-1)); 
			   }
			   if (isValid(i, j+1)&&isOpen(i, j+1)) {
				   uf.union(toOneD(i, j), toOneD(i, j+1));
				   uf1.union(toOneD(i, j), toOneD(i, j+1));
			   }
			   if (i==1) {
				   uf.union(toOneD(i, j),N*N);
				   uf1.union(toOneD(i, j),N*N);
			   } 
			   if (i==N) uf.union(toOneD(i, j),N*N+1);
		   }
	   }
	   private boolean isValid (int i, int j)
	   {
		   if (i<1 || j<1 || i>N || j>N) return false;
		   else return true;
	   }
	   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
	   {
		   if (!isValid(i, j))     throw new java.lang.IndexOutOfBoundsException(); 
		   else return isOpen[i-1][j-1];
		   
	   }
	   public boolean isFull(int i, int j)     // is site (row i, column j) full?
	   {
		   if (!isValid(i, j)) 	 throw new java.lang.IndexOutOfBoundsException();
		   else return uf1.connected(N*N, toOneD(i, j));
		   
	   }
	   public boolean percolates()             // does the system percolate? 
	   {
		   return uf.connected(N*N, N*N+1) ; 
	   }
	   
	   private int toOneD (int i, int j) {
		  return (i-1)*N+j-1 ; 
	   }
	    
	   public static void main(String[] args) {
	        In in = new In("input6.txt");      // input file
	        int N = in.readInt();         // N-by-N percolation system

	        Percolation perc = new Percolation(N);
	        //while (!in.isEmpty()) {
	            int i = in.readInt();
	            int j = in.readInt();
	            perc.open(i, j);
	        //}
	         System.out.println(perc.percolates());
	        //System.out.println(perc.uf.connected(0,1));
	        //perc.open(1,1);
	        //System.out.println(perc.uf.connected(0,1));
	        //perc.open(2,2);
	        //System.out.println(perc.uf.connected(0,1));
	        //perc.open(1,2);
	        //System.out.println(perc.uf.connected(0,1));
	        //System.out.println(perc.uf.connected(1,5));


	}

}

