import edu.princeton.cs.introcs.BinaryIn;
import edu.princeton.cs.introcs.BinaryStdIn;
import edu.princeton.cs.introcs.BinaryStdOut;
import edu.princeton.cs.introcs.In;

public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
	
	private static final int CHAR_BITS = 8;
	
    public static void encode() {
//    	String in = BinaryStdIn.readString();
    	String in = "ABRACADABRA!";
    	BWT bwt = new BWT(in);
    	BinaryStdOut.write(bwt.first());

    	BinaryStdOut.write("    ");
    	
    	for (char c: bwt.t())
    		BinaryStdOut.write(c,CHAR_BITS);
    
    	BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        //int first = 3;
        String s = BinaryStdIn.readString();
        //String s = "ARD!RCAAAABB";
        char[] t = s.toCharArray();
        
        IBWT ibwt = new IBWT(t, first);
        for (char c : ibwt.recover())
        	BinaryStdOut.write(c);
        
        BinaryStdOut.close();
    	
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args)  {
        /*if (args.length == 0) 
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
         if (args[0].equals("-")) 
            encode();
         else if (args[0].equals("+")) 
            decode();
         else 
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");*/
    	decode();
    	
    }
}