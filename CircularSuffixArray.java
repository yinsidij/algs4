
public class CircularSuffixArray {
	private int N;
	private String a;
	private int[] index;
	private final int R = 256;

    public CircularSuffixArray(String s)  // circular suffix array of s
    {
    	this.N = s.length();
    	this.a = s;
    	this.index = new int[N];

    }
    public int length()                   // length of s
    {
    	return N;
    }
    public int index(int j)               // returns index of ith sorted suffix
//    public void index()               // returns index of ith sorted suffix
    {
    	for (int k=0;k<N;k++) {
    		this.index[k] = k;
    	}
    	
    	int[] index_aux = new int[N];
    	
        for (int d = N-1; d >= 0; d--) {

        	int[] count = new int[R+1];
    	
        	for (int i = 0; i < N; i++)
        		count[a.charAt((index[i]+d)%N) +1]++;

        	for (int r = 0; r < R; r++)
        		count[r+1] += count[r];
    	
        	for (int i = 0; i < N; i++) {
        		index_aux[count[a.charAt((index[i]+d)%N)]] = index[i];
        		count[a.charAt((index[i]+d)%N)]++;
        	}

        	for (int i = 0; i < N; i++) {
        		index[i] = index_aux[i];
        	}
    	
    		//System.out.println(" ");
    		//for (int i=0; i < N; i++) {
    		//	System.out.print(index[i]+" ");
    		//}
        }
        
        return index[j];
    }

    public static void main(String[] args)// unit testing of the methods (optional)
    {
    	char[] a = new char[12];
        a[0]='d';
        a[1]='a';
        a[2]='c';
        a[3]='f';
        a[4]='f';
        a[5]='b';
        a[6]='d';
        a[7]='b';
        a[8]='f';
        a[9]='b';
        a[10]='e';
        a[11]='a';
        String b = "ABRACADABRA!";
    	CircularSuffixArray csa = new CircularSuffixArray(b);
    	System.out.println(csa.index(11));
    }
}