

public class IBWT {
	
	public int[] next;
	public char[] sorted; //first column
	public int first;
	private final int R =256;
	private int N;
	
	public IBWT(char[] t, int first) {
		this.first = first;
		N = t.length;
		sorted = new char[N];
		next = new int[N];
		
		int[] count = new int[R+1];
		for (int i=0;i<N;i++) {
			count[t[i]+1]++;
		}
		
		for (int r = 0; r < R; r++)
			count[r+1] +=count[r];
		
		for (int i = 0; i < N; i++) {
			next[count[t[i]]] = i;
			sorted[count[t[i]]] = t[i];
			count[t[i]]++;
		}
	}
	
	public char[] recover() {
		char[] orig = new char[N];
		int n = first;
		for (int i=0;i<N;i++) {
			orig[i]=sorted[n];
			n = next[n];
		}
		
		return orig;
	}
	
    public static void main(String[] args)  {

    	String s = "ABRACADABRA!";
    	BWT bwt = new BWT(s);
    	//char[] t = bwt.t();
    	IBWT ibwt = new IBWT(bwt.t(), 3);
    	for (int i=0;i<s.length();i++) {
    		System.out.print(ibwt.sorted[i]+" ");
    		System.out.println(ibwt.next[i]);
    	}
    	ibwt.recover();
    }
	
	

}
