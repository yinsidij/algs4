import edu.princeton.cs.introcs.BinaryStdOut;

public class MoveToFront {

	private static final int R = 256;
	
	// apply move-to-front encoding, reading from standard input and writing to
	// standard output
	public static void encode() {
		char[] chars = radixList();
		char count, ch, tmpin, tmpout;
		while (!BinaryStdIn.isEmpty()) {
			ch = BinaryStdIn.readChar();
			for (count = 0, tmpout = chars[0]; ch != chars[count]; count++) {
				tmpin = chars[count];
				chars[count] = tmpout;
				tmpout = tmpin;
			}
			chars[count] = tmpout;
			BinaryStdOut.write(count);
			chars[0] = ch;
		}
		BinaryStdOut.close();
	}

	// apply move-to-front decoding, reading from standard input and writing to
	// standard output
	public static void decode()  {
		char[] chars = radixList();
		char count, ch;
		while (!BinaryStdIn.isEmpty()) {
			count = BinaryStdIn.readChar();
			for (ch = chars[count]; count > 0; chars[count] = chars[--count]);
			chars[count] = ch; // assert count == 0;
			BinaryStdOut.write(ch);
		}
		BinaryStdOut.close();
	}
	
	private static char[] radixList() {
		char[] rl = new char[R];
		for (char i = 0; i < R; rl[i] = i++);
		return rl;
	}

	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		if (args[0].equals("-"))
			encode();
		else if (args[0].equals("+"))
			decode();
		else
			return;
	}
}