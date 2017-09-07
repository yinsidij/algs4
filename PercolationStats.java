import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
	private double[] xi;
	private int N;
	private int T;
	public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   {
	   if (N<=0 || T<=0)  throw new java.lang.IllegalArgumentException();
	   this.N=N;
	   this.T=T;
	   this.xi = new double[T];
	   for (int i=0;i<T;i++) {
		   Percolation p = new Percolation(N);
		   int x=0;
		   while (!p.percolates()) {
			   int a=StdRandom.uniform(N)+1;
			   int b=StdRandom.uniform(N)+1;
			   if (!p.isOpen(a, b)) {
				   p.open(a,b);
				   x++;
		   	   }
		   }
		   this.xi[i]=(double) x/(N*N);
	   }
	   
   }
   public double mean()                      // sample mean of percolation threshold
   {
	   return StdStats.mean(xi);
   }
   public double stddev()                    // sample standard deviation of percolation threshold
   {
	   return StdStats.stddev(xi);
	   
   }
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
	   return mean()-1.96*stddev()/Math.sqrt(T);
   }
   public double confidenceHi()              // high endpoint of 95% confidence interval
   {
	   return mean()+1.96*stddev()/Math.sqrt(T);
   }

   public static void main(String[] args)    // test client (described below)
   {
	   Stopwatch stopwatch = new Stopwatch();
	   PercolationStats pStats = new PercolationStats(200, 100);
	   //for (int i=0;i<pStats.N;i++) System.out.println(pStats.xi[i]);
	   System.out.println(stopwatch.elapsedTime());
	   //System.out.println(pStats.mean());
	   //System.out.println(pStats.stddev());
	   Stopwatch stopwatch1 = new Stopwatch();
	   PercolationStats pStats1 = new PercolationStats(200, 200);
	   System.out.println(stopwatch1.elapsedTime());
	   
	   
   }
}