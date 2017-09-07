//package puzzle;
import java.lang.Math;



//import edu.princeton.cs.introcs.In;
//import edu.princeton.cs.introcs.StdOut;



public class Board {
	
	private int[][] blocks; 
	private int N;
	
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
    	this.N      = blocks[0].length;
    	this.blocks = new int[N][N];      //Have to new int[][]. this.blocks = blocks <-- WRONG
    	for (int i=0;i<N;i++)
    		for (int j=0;j<N;j++)
    			this.blocks[i][j] = blocks[i][j];
    }
    
    public int dimension()                 // board dimension N
    {
    	return N;
    }
    public int hamming()                   // number of blocks out of place
    {
    	int count = 0;
    	for (int i=0;i<N;i++)
    		for (int j=0;j<N;j++)
    		{
    			if (blocks[i][j] != dimension()*i+j+1) {
    				//System.out.println("number: "+blocks[i][j]);
    				//int expected = dimension()*i+j+1;
    				//System.out.println("expected: "+expected);
    				count = count + 1;
    			}
    		}
    	
    	return (count-1);
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
    	int manhattan = 0;
    	for (int i=0;i<N;i++)
    		for (int j=0;j<N;j++)
    			if (blocks[i][j] != 0)     //exclude empty square
    			{
    				int ii = (blocks[i][j]-1) / N;         
    				int jj = blocks[i][j] - ii*N - 1;             //8 --> ii=2 jj=1 6--> ii=1 jj=2
    				int  m = Math.abs(ii-i) + Math.abs(jj-j);              
    				//System.out.println(blocks[i][j]+" "+i+" "+j+" "+ii+" "+jj+" "+m);
    				manhattan = manhattan + m;
     			}	
    	return manhattan;
    }

	public boolean isGoal()                // is this board the goal board?
	{
		return (manhattan() == 0) ;
	}
	
    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
    	Board twinBoard = new Board(blocks);
    	boolean flag = false;
    	
    	for (int i=0;i<N;i++) {
    		if (flag == true)  break;
    		for (int j=0; j<N-1; i++) {
    			if ((blocks[i][j] != 0)&&(blocks[i][j+1] != 0))
    			{
    				int tmp = twinBoard.blocks[i][j+1];
    				twinBoard.blocks[i][j+1] = blocks[i][j];
    				twinBoard.blocks[i][j]   = tmp;    	   //exchange two numbers in the first row.	
    				flag = true;
    				break;
    			}
    		}
    	}
    	return twinBoard;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
    	 if (y == null) return false;
    	 if (y.getClass() != this.getClass()) return false;

    	Board that = (Board) y;
    	if (this.dimension() != that.dimension()) return false;
    	for (int i=0;i<this.dimension();i++)
    		for (int j=0;j<this.dimension();j++)
    		{
    			if ( this.blocks[i][j] != that.blocks[i][j] ) 
    				return false;
    		}
    	
    	return true;
    			
    }
    
    public Iterable<Board> neighbors() {     // all neighboring boards
    	
    	int zeroi=0; int zeroj=0;
    	Board newBoard;
    	Queue<Board> boardSeq = new Queue<Board>();
    	// find location 0
    	for (int i=0;i<N;i++)
    		for (int j=0;j<N;j++)
    			if (blocks[i][j] == 0)
    			{	
    				zeroi = i;
    				zeroj = j;
    				break;
    			}
    	//if not left side != 0,N,2N ... there exists left neighbor
    	if (zeroj>0)
    	{
    		//System.out.println("has left");
    		newBoard = new Board(blocks); 
    		exch(newBoard.blocks, zeroi,zeroj,zeroi,zeroj-1);
    		boardSeq.enqueue(newBoard);
    		newBoard.toString();
    	}
    	//if not right side --> != N-1.. there exists right neighbor
    	if (zeroj<N-1)
    	{
    		//System.out.println("has right");
    		newBoard= new Board(blocks);
    		exch(newBoard.blocks, zeroi,zeroj,zeroi,zeroj+1);
    		boardSeq.enqueue(newBoard);
    		newBoard.toString();
    	}
    	// up 
    	if (zeroi>0)
    	{
    		//System.out.println("has up");
    		newBoard= new Board(blocks);
    		exch(newBoard.blocks, zeroi,zeroj,zeroi-1,zeroj);
    		boardSeq.enqueue(newBoard);
    		newBoard.toString();
    	}
    	//down
    	if (zeroi<N-1)
    	{
    		//System.out.println("has down");
    		newBoard= new Board(blocks);
    		exch(newBoard.blocks, zeroi,zeroj,zeroi+1,zeroj);
    		boardSeq.enqueue(newBoard);
    		newBoard.toString();
    	}
    		
    	return boardSeq;
    }
    
    
    private void exch(int[][] a, int i, int j, int ii, int jj)
    {
    	int tmp  = a[i][j];
    	a[i][j]  = a[ii][jj];
    	a[ii][jj]= tmp;
    }
    
    public String toString() {               // string representation of the board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        int N = dimension();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
 
    public static void main(String[] args) {



            // read in the board specified in the filename
            In in = new In("puzzle17.txt");
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            
            Board b = new Board(tiles);
            System.out.println(b.toString());
            System.out.println(b.twin().toString());
            System.out.println(b.isGoal());
            
            for (Board one: b.neighbors()) {
            	StdOut.println("one neighbor");
            	StdOut.println(one.toString());
            	StdOut.println("manhattan = " + one.manhattan());
            	StdOut.println("Goal?" + one.isGoal());
            }
            System.out.println("===============================");
            System.out.println(b.toString());
            System.out.println("hamming:"+b.hamming());
    }
    	
    
}
