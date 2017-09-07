//package PatternRecognition;

import java.util.ArrayList;
import java.util.Arrays;

//import edu.princeton.cs.introcs.In;
//import edu.princeton.cs.introcs.StdDraw;

public class Fast {
	
	private static ArrayList<Point> drawStart = new ArrayList<Point>();
	private static ArrayList<Point>   drawEnd = new ArrayList<Point>();
	
	
    private static Point[] readInput(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        return points;
    }
    
	private static void drawSetup()
	{
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
        //StdDraw.setPenRadius(0.01);  // make the points a bit larger
	}

    private static int[] toSlopeSort(int k, Point[] points) // points[k] is the origin, sort by points[k] --> points[i]
    {
    	int N = points.length;
    	//if (N >= 3)
    	//{
    		// p is the base
    		double[] slope = new double[N-1];   // slope array:
    		   int[] index = new    int[N-1];   // index array: 1 2 3 4 5 6 ...
    		  
    		for (int j=0; j<points.length;j++)   // initialization
    		{
    			if (j < k)
    			{
    				slope[j] = points[k].slopeTo(points[j]);
    				//System.out.print(points[k].toString()+" "+points[j].toString()+" "+slope[j]);
    				//System.out.print("\n");
    				index[j] = j;
    			}
    			if (j > k)
    			{
    				slope[j-1] = points[k].slopeTo(points[j]);
    				//System.out.print(points[k].toString()+" "+points[j].toString()+" "+slope[j-1]);
    				//System.out.print("\n");
    				index[j-1] = j;
    			}
    				
    		}

/*    		for (int i=1;i<slope.length;i++)
    		{
    			System.out.print(slope[i]+" ");
    		}
    		System.out.print(" "+points[k].toString()+"\n");*/
    		
    		for (int i=1;i<slope.length;i++)
    		{
    			for (int j=i-1;j>=0;j--)
    			{
    				if (slope[j+1]<slope[j])
    				{
    					double tmpSlope = slope[j+1];
    					slope[j+1] = slope[j];
    					slope[j]   = tmpSlope;
    					int tmpIndex    = index[j+1];
    					index[j+1] = index[j];
    					index[j]   = tmpIndex;
     				}
    				
    			}
    		}
    		
/*    		for (int i=1;i<slope.length;i++)
    		{
    			System.out.print(slope[i]+" ");
    		}
    		System.out.print("\n");*/
  		
    	//}
    	return index;
    }	

    private static Point[] toSort(Point[] points)    // sort input according to its coordinates
    {
    	for (int i=1;i<points.length;i++)
    	{
			for (int j=i-1;j>=0;j--)
			{
				if (points[j+1].compareTo(points[j]) == 1)  //if smaller, swap
				{
					Point tmpPoint = points[j+1];
					points[j+1] = points[j];
					points[j]   = tmpPoint;

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
		
		StdDraw.show(0);
	}
		
	private static void printLine(Point[] points)
	{
		int N = points.length;
		assert N >= 2;
		
		for (int i = 0; i < N-1; i++)
		{
			System.out.printf (points[i].toString() + " -> ");
		}
			System.out.printf (points[N-1].toString()+"\n");
	}
    	
    	
	private static void checkAndDraw(Point pointK, Point[] restpoints) //input base point and the rest points which has the save slopes    
	{
		
		Point p ;
		Point q ;
		Point[] points = toSort(restpoints);
		Point[] pointsNew = new Point[points.length+1];                    //merge with pointK
		//compare pointK to the rest sorted points
		boolean insert = false;
		if (pointK.compareTo(points[points.length-1]) != 1)     //if pointK is larger than the rest
				pointsNew[pointsNew.length-1] = pointK;
					
		for (int i=0;i<points.length;i++)
		{
			if ((pointK.compareTo(points[i]) == 1) && (!insert) ) //means smaller
			{
				pointsNew[i] = pointK;
				pointsNew[i+1] = points[i];
				insert = true;             //inserted
			}
			else if ((pointK.compareTo(points[i]) != 1) && (!insert) )
				pointsNew[i] = points[i];
			else
				pointsNew[i+1] = points[i];
		}
/*		for (int i=0;i<pointsNew.length;i++)
		{
			System.out.print(pointsNew[i]+" ");
		}
		System.out.print("\n");*/
		
		p = pointsNew[0];
		q = pointsNew[pointsNew.length-1];
		
		boolean flag = false;  //true: exist
		
		
		for (int i=0;i<drawStart.size();i++)
		{
			
			if ((drawStart.get(i).compareTo(p) == 0)&&(drawEnd.get(i).compareTo(q) == 0))
				flag = true;
		}
		
		if (!flag)                      //draw
		{
			drawStart.add(p);
			  drawEnd.add(q);
			  
			drawLine(pointsNew);
			printLine(pointsNew);
		}
	}
 
    
    
	public static void main(String[] args) {
		final int MINI_POINT= 4;
		int start;
		int end;
		boolean flag;   // found
		Point[] points = readInput (args[0]);
		drawSetup();
		for (int k=0;k<points.length;k++)
		{
			//int k = 1
			int[] index = toSlopeSort(k, points);
			//initial
			start = 0; end = 0; flag = false;   //flag: start found
			for (int i=0;i<index.length-1;i++)
			{
				//System.out.print(points[k].slopeTo(points[index[i]])+" "+points[k].slopeTo(points[index[i+1]])+"\n");

				if ( ( points[k].slopeTo(points[index[i]]) == points[k].slopeTo(points[index[i+1]]) ) && (!flag) )
					{
						
						start = i;
						flag  = true;
						//System.out.print(start+"\n");
					}
				
				if ( ( ( points[k].slopeTo(points[index[i]]) < points[k].slopeTo(points[index[i+1]]) ) && flag ) )
					{
						end = i;
						flag = false;
						//System.out.print(end+"\n");
					}
				else if ((i+1==index.length-1) && flag)
					{
						end = i+1;
						flag = false;
						//System.out.print(end+"\n");
					}
				
				if (end-start+1>=MINI_POINT-1) // MINI_POINT = 4 --> 3 slopes
				{
					Point[] pointsFound = new Point[end-start+1];
					for (int ii=0;ii<pointsFound.length;ii++)
					{
						pointsFound[ii] = points[index[start+ii]];
					}
					
/*					for (int x=0;x<pointsFound.length;x++)
					{
						System.out.print(pointsFound[x]+" ");
					}
					System.out.print("\n");*/
					
					checkAndDraw(points[k],pointsFound);
				}
				
				
				
			}
			
			
		}
		
		
        // display to screen all at once
        StdDraw.show(0);

		
		
		

	}

}
