package kd_Trees;

import java.awt.Point;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.introcs.StdDraw;


public class PointSET {
	
	   private SET<Point2D> t;
	
	   public         PointSET()                               // construct an empty set of points 
	   {
		   t = new SET<Point2D>(); 
	   }
	   public           boolean isEmpty()                      // is the set empty? 
	   {
		   return t.isEmpty();
	   }
	   public               int size()                         // number of points in the set 
	   {
		   return t.size();
	   }
	   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	   {
		   t.add(p);
	   }
	   public           boolean contains(Point2D p)            // does the set contain point p? 
	   {
		   return t.contains(p);
	   }
	   public              void draw()                         // draw all points to standard draw 
	   {
		   for (Point2D p : t) {
			   StdDraw.point(p.x(),p.y());
		   }

		   StdDraw.show(0);
		   
	   }
	   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
	   {
		   Queue<Point2D> q = new Queue<Point2D>();
		   
		   for (Point2D p : t) {
			   if (rect.contains(p)) {
				   q.enqueue(p);
			   }
		   }
		   
		   return q;
	   }

	   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
	   {
		  Point2D tmpPoint = null;
		  double tmpDis = Double.MAX_VALUE;
		  
		  for (Point2D pt : t) {
			  if (pt.distanceTo(p) < tmpDis) {
				  tmpPoint = pt;
				  tmpDis = pt.distanceTo(p);
			  }
		  }
		  return tmpPoint;
		   
	   }

	   public static void main(String[] args)                  // unit testing of the methods (optional) 
	   {
		   
	   }
	}
