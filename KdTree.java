package kd_Trees;

import javax.xml.soap.Node;

public class KdTree {
	
	   private Node root;
	   private int N;
		   
	   private class Node 
	   {
		   private Point2D p;
		   private RectHV r;
		   private Node left;
		   private Node right;
		   
		   public Node(Point2D p, RectHV r)
		   {
			   this.p = p;
			   this.r = r;
		   }
	   }

	   public KdTree()                            // construct an empty set of points
	   {
		   root = null;
		   N = 0;
	   }
	   
	   
	   public           boolean isEmpty()              // is the set empty?
	   {
		   return N == 0;
	   }
	   
	   public               int size()                 // number of points in the set
	   {
		   return N;
	   }

	   public              void insert(Point2D p)      // add the point p to the set (if it is not already in the set)
	   {
		   
	   }
	   public           boolean contains(Point2D p)    // does the set contain the point p?
	   public              void draw()                 // draw all of the points to standard draw
	   public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
	   public    Point2D nearest(Point2D p)            // a nearest neighbor in the set to p; null if set is empty
	}