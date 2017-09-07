

public class BWT {
	
	public int first;
	public char[] t;
	private int N;
	public BWT (String s) {
		CircularSuffixArray cs = new CircularSuffixArray(s);
		this.N = cs.length();
		for (int i=0;i<N;i++) {
			if (cs.index(i) == 0) {
				this.first = i;
				break;
			}
		}
		
		t = new char[this.N];
		for (int i=0;i<N;i++) {
			//System.out.println((cs.index(i)+N-1)%N);
			t[i] = s.charAt((cs.index(i)+N-1)%N);
		}
		
	}
	
	
	
	public int first() {
		return this.first;
	}
	
	public char[] t() {
		return this.t;
	}

}
