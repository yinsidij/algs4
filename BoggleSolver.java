




public class BoggleSolver
{
	private TrieST2<Integer> st;
	private int col;
	private int row;
	private SET<String> set;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary)
    {
    	st = new TrieST2<Integer>();
    	
    	//for (int i=0;i<dictionary.length;i++) {
    		
    	//	if (dictionary[i].contains("QU")) st.put(dictionary[i], i);
    	//	else if (!dictionary[i].contains("Q")) st.put(dictionary[i], i);
    	//}
    	
    	for (String s:dictionary) {
    		st.put(s, 1);
    	}
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
    	this.set = new SET<String>();
    	for (int i=0;i<board.rows();i++) {
    		for (int j=0;j<board.cols();j++) {
    			DFS(board, i, j);
    		}
    	}
    	return set;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word)
    {
    	if (st.contains(word)) {
    		//if (word.contains("QU")) word.replace("Q", "QU");
    		/*if (word.length()<=2 && word.length()>=0)
    			score = 0;
    		else if (word.length()>=3 && word.length() <=4)
    			score = 1;
    		else if (word.length() == 5)
    			score = 2;
    		else if (word.length() == 6)
    			score = 3;
    		else if (word.length() == 7)
    			score = 5;
    		else if (word.length() >= 8)
    			score = 11;*/
    		switch (word.length()) {
            	case 0:
            	case 1:
            	case 2:
            		return 0;
            	case 3:
            	case 4:
            		return 1;
            	case 5:
            		return 2;
            	case 6:
            		return 3;
            	case 7:
            		return 5;
            	default:
            		return 11;
            	}
        	} else {
        		return 0;
    	}
    }
    
    
    	private boolean[][] marked;
    	private BoggleBoard b;
    	
    	private void DFS(BoggleBoard board, int i, int j)
    	{
    		this.row = board.rows();
    		this.col = board.cols();
    		this.marked = new boolean[row][col];
    		this.b = board;
    		String prefix = "";
    		dfs(prefix, i, j);
    	}
    	
    	private void dfs (String prefix, int i, int j)
    	{
    		if ((i<0 || i>=this.row) ||
    		   (j<0 || j>=this.col) || 
    		   marked[i][j] ||
    		   !st.isPrefix(prefix) ) 
    			return;
    		
    		marked[i][j] = true;
    		char letter = b.getLetter(i, j);
    		prefix = prefix + (letter == 'Q' ? "QU" : letter);
//    		prefix = prefix + letter;
//    		String prefix1 = prefix.replace("Q", "QU");
    		if (st.contains(prefix)&prefix.length()>2) set.add(prefix); 
    		for (int ii = -1; ii <= 1; ii++)
    			for (int jj = -1; jj <= 1; jj++)
    			dfs(prefix, i + ii, j + jj);
    			
    		marked[i][j] = false;	
    	}
    	
    	
    	
    
    public static void main(String[] args)
    {
    	long startTime = System.currentTimeMillis();
          In in = new In("dictionary-yawl.txt");
//        In in = new In("dictionary-algs4.txt");
//        In in = new In("dictionary-16q.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("board-q.txt");
        System.out.println(board.toString());
        //System.out.println(solver.getAllValidWords(board));
        int score = 0;
        int count = 0;
        for (String word : solver.getAllValidWords(board))
        {
             StdOut.println(word);
            score += solver.scoreOf(word);
            count += 1;
        }
        StdOut.println("Score = " + score);
        StdOut.println("Count = " + count);
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }

    

}