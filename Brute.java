//package PatternRecognition;
import java.util.Arrays;

//import edu.princeton.cs.introcs.In;
//import edu.princeton.cs.introcs.StdDraw;


public class Brute {
	
	private static void drawSetup()
	{
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
        //StdDraw.show(0);
        //StdDraw.setPenRadius(0.01);  // make the points a bit larger
	}
	
    private static Point[] readInput(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        return points;
    }
	
	private static boolean collinear (Point[] points)
	{
		if (points.length == 2)
			return true;
		else if (points.length > 2)
		{
			Point origin = points[0];
			for (int i = 2; i < points.length; i++)
			{
				if (origin.SLOPE_ORDER.compare(points[1], points[i]) != 0)
					return false;
			}

/*			for (int i = 2; i < points.length; i++)
			{
				System.out.print(origin.slopeTo(points[i])+" ");
				System.out.print(origin.SLOPE_ORDER.compare(points[1], points[i]));
				System.out.println(" ");
			}*/
			return true;
		}
		else return false;  // only one point
	}
	
	
	private static boolean isSorted(Point[] points)
	{
		if (points.length >= 2 )
		{
			for (int i = 0; i<points.length-1; i++)
			{
				int j = i+1;
				if (points[i].compareTo(points[j]) == -1)
					return false;
			}
			return true;
		}
		
		else // one point
			return true;
		
	}
	
	private static Point[] sort(Point[] points)
	{
		for (int i=1; i<points.length; i++)
		//left of  i: sorted
		//right of i: unsorted	
		{
			int j = i-1;
			
			for (int k=j;k>=0;k--)
			{
				if (points[k+1].compareTo(points[k]) > 0)
				{
					Point tmp = points[k+1];
					points[k+1] = points[k];
					points[k]   = tmp;
				}
				
			}
		}
		return points;	
			
	}
	
	
	private static void drawLine(Point[] points)   // sorted points
	{
		int N = points.length;
		assert N >= 2;
		
		for (int i = 0; i < N; i++ )
		{
			points[i].draw();
		}
			points[0].drawTo(points[N-1]);	
		
	}
	
	private static void printLine(Point[] points)
	{
		int N = points.length;
		assert N >= 2;
		
		for (int i = 0; i < N-1; i++)
		{
			System.out.printf (points[i].toString() + " -> ");
		}
			System.out.println (points[N-1].toString());
	}
	
	public static void main(String[] args) {
		
		//Point[] P = new Point[4];
		//Point[] points = new Point[6];
    	//points[0] = new Point(0,0);
    	//points[1] = new Point(1,1);
        //points[2] = new Point(2,2);
        //points[3] = new Point(3,3);
        //points[4] = new Point(4,4);
        //points[5] = new Point(5,5);
        
    	//Brute b = new Brute();
    	//Point[] PX = b.sort(P);
    	//System.out.println(b.isSorted(PX));
		
		drawSetup();
		Point[] P = new Point[4];
    	Point[] points = readInput(args[0]);
    	int N = points.length;
    		//System.out.println(N);
    	for (int i=0; i<=N-1; i++)
    		for (int j=i+1; j<=N-1; j++)
    			for (int k=j+1; k<=N-1; k++)
    				for (int t=k+1;t<=N-1;t++)
    				{
    					P[0] = points[i];
    					P[1] = points[j];
    					P[2] = points[k];
    					P[3] = points[t];
    					//System.out.printf(P[0].toString());
    					//System.out.printf(P[1].toString());
    					//System.out.printf(P[2].toString());
    					//System.out.printf(P[3].toString());
    					//System.out.print(collinear(P));
    					//System.out.println(isSorted(P));
    					//System.out.printf("\n");
    					if (collinear(P) && isSorted(P))
    						{
    						    //System.out.printf("collinear and sorted");
    							drawLine(P);
    							printLine(P);
    						}
    					else if (collinear(P) && !isSorted(P))
    						{
    							Point[] Psort = sort(P);
    							drawLine(Psort);
    							printLine(Psort);
    						}
    				}
    	StdDraw.show(0);
		
	}


}
