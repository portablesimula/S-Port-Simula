package attr;

import static comn.Util.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HexFormat;

@SuppressWarnings("unused")
public class ByteOutputStream {
	private OutputStream out;
	private int TESTING=2;

	public ByteOutputStream(String fileName) {
		try { out = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) { e.printStackTrace(); }
	}

	public void outbyte(int b) {
		if(TESTING>1) System.out.println("ByteOutputStream.outbyte: "+b);
		try { out.write(b);
		} catch (IOException e) { e.printStackTrace(); }
	}

	public void outtext(String s) {
		if(TESTING>1) System.out.println("ByteOutputStream.outtext: "+edString(s));
		try {
			int lng=(s==null)?0:s.length();
			out.write(lng);
			for(int i=0;i<lng;i++) out.write(s.charAt(i));
		} catch (IOException e) { e.printStackTrace(); }
	}

	public void out2byte(int n) {
		int hi=n>>8;
		int lo=n&0xFF;
		if(TESTING>1) System.out.println("ByteOutputStream.out2byte: "+n+" ==> hi="+hi+", lo="+lo);
		try { out.write(hi); out.write(lo);
		} catch (IOException e) { e.printStackTrace(); }
	}

	public void outKey(int k) {
		if(TESTING>1) System.out.println("ByteOutputStream.outkey: "+k);
		try { out.write(k);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	private static String edString(String buffer) {
		if(buffer==null || buffer.length()==0) return("");
		String digs="", chr="";
		byte[] bytes=buffer.getBytes();
		for(int i=0;i<buffer.length();i++) {
			byte b=bytes[i];
			digs=digs+b+" ";
//			if(!Character.isAlphabetic(b)) b='.';
			if(b==10) b='.';
			chr=chr+((char)b)+" ";
		}
		while(digs.length()<(16*4)) digs=digs+" ";
		return(digs+"   "+chr);
	}

}
