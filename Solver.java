//package puzzle;
import java.util.Comparator;

//import edu.princeton.cs.introcs.In;
//import edu.princeton.cs.introcs.StdOut;

public class Solver {
	
		private searchNode goal;
		private searchNode goalTwin;
	
	private class searchNode {
		
		private Board board;
		private int moves;
		private searchNode preNode;
		
		public searchNode(Board b, int moves, searchNode preNode) {
			board = b;
			this.moves = moves;
			this.preNode = preNode;
		}
	}
	
    public Solver(Board initial) {           // find a solution to the initial board (using the A* algorithm)
    	
       searchNode minNode     = null;    
       searchNode minNodeTwin = null;    
    	priorityOrder po      = new priorityOrder();
    	priorityOrder poTwin  = new priorityOrder();
    MinPQ<searchNode> pq      = new MinPQ<searchNode>(po);
    MinPQ<searchNode> pqTwin  = new MinPQ<searchNode>(poTwin);
    	   searchNode sn      = new searchNode(initial,0,null);
    	   searchNode snTwin  = new searchNode(initial.twin(),0,null);
    	   pq.insert(sn);
    	   pqTwin.insert(snTwin);
    	   //System.out.println(sn.board.toString());
    	   minNode     = (searchNode) pq.delMin();
    	   minNodeTwin = (searchNode) pqTwin.delMin();
    	   //System.out.println(i+":"+minNode.board.toString());
    	   
    	   while (!minNode.board.isGoal() && !minNodeTwin.board.isGoal()) {
    		   //System.out.println("======================");
    		   for (Board b: minNode.board.neighbors()) {
    			   if ((minNode.preNode == null) || (! b.equals(minNode.preNode.board))) {
    			   //System.out.println("neighbor:"+b.toString()+"manhattan:"+b.manhattan()+" moves:"+(minNode.moves+1));
    				   searchNode s = new searchNode(b, minNode.moves+1 ,minNode);
    				   pq.insert(s); 
    				   //System.out.println("priority queue becomes:"+pq.size());
    				   //System.out.println(" ");
    			   }
    		   }
    		  minNode = (searchNode) pq.delMin(); 
    	      //System.out.println("deleted min: when moves ="+minNode.moves+"\n"+minNode.board.toString()); 
    		   
    	   for (Board b: minNodeTwin.board.neighbors()) {
    			   if ((minNodeTwin.preNode == null) || (! b.equals(minNodeTwin.preNode.board))) {
    				   searchNode s = new searchNode(b, minNodeTwin.moves+1 ,minNodeTwin);
    				   pqTwin.insert(s); 
    			   }
    		   }
    		  minNodeTwin = (searchNode) pqTwin.delMin(); 
    	   }
    	   
    	   if (minNode.board.isGoal()) {
    		   this.goal=minNode;
    	   }
    	   else {
    		   this.goal=null;
    	   }
    	   
    	   
    	   if (minNodeTwin.board.isGoal()) {
    		   this.goalTwin = minNodeTwin;
    	   }
    	   else {
    		   this.goalTwin=null;
    	   }

    }

	private class priorityOrder implements Comparator<searchNode> {
		
		public int compare (searchNode s1, searchNode s2) {
			int total1 = s1.board.manhattan()+s1.moves;
			int total2 = s2.board.manhattan()+s2.moves;
			if (total1>total2) return +1;
			else if (total1<total2) return -1;
			else return 0; }
		
	}
	
    public boolean isSolvable() {            // is the initial board solvable?
    	if (goalTwin != null )
    		return false;
    	else return (goal!=null);
    }
    
    public int moves()  {                     // min number of moves to solve initial board; -1 if no solution
        if (this.isSolvable()) 
        	return goal.moves;
        else
        	return -1;
    }
    
    public Iterable<Board> solution() {      // sequence of boards in a shortest solution; null if no solution
    	if (this.isSolvable()) {
    		Stack <Board> boardSeq = new Stack<Board>();
    		
    		boardSeq.push(goal.board);
    		searchNode nextNode = goal.preNode;
    		while (nextNode !=null) {
    			boardSeq.push(nextNode.board);
    			nextNode = nextNode.preNode;
    		}
    		return boardSeq;
    		
    	}
    	else
    		return null;
    }	
    public static void main(String[] args)  // solve a slider puzzle (given below)
    {
        In in = new In("puzzle2x2-unsolvable1.txt");
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        
        Board initial  = new Board(tiles);
        Solver s = new Solver(initial);
        if (s.isSolvable()) {
        	System.out.println("Solvable? "+s.isSolvable());
        	System.out.println("Minimum number of moves = "+s.moves());
        	for (Board one : s.solution()) {
        		System.out.println(one);
        	}

        }
        else 
        	System.out.println("No solution possible");
        
    }	
}