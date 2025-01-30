package attr;

import static attr.Util.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("unused")
public class ByteInputStream {
	private InputStream inputStream;
	
	public ByteInputStream(String fileName) {
		try { inputStream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) { e.printStackTrace(); }
	}
	
	private int readbyte() {
		try { return(inputStream.read());
		} catch (IOException e) { e.printStackTrace(); }
		return(0);
	}
	
	public int inbyte() {
		int res=readbyte();
		if(TESTING>1) System.out.println("ByteInputStream.inbyte: "+edMaybeKey(res));
//		if(res==43) EXIT();
		return(res);
	}
	
	public int in2byte() {
		int b1=readbyte();
		int b2=readbyte();
		int res=((b1<<8) | b2);
//		if(TESTING>1) System.out.println("ByteInputStream.in2byte: b1="+b1+", b2="+b2+" ==> "+res);
		if(TESTING>1) System.out.println("ByteInputStream.in2byte: "+res);
		return(res);
	}
	
    public String intext() {
		int lng=readbyte();
		return(intext(lng));
    }
    
    public String intext(int lng) {
    	byte[] bb=new byte[lng];
//		String res=attrbuffer.sub(p+1,lng); p=p+lng;
    	for(int i=0;i<lng;i++) bb[i]=(byte) readbyte();
    	String res=new String(bb);
    	if(TESTING>1) System.out.println("ByteInputStream.intext: lng="+lng+", res=\""+res+'"');
    	if(TESTING>4) HexDump.hexDump(res, lng);
		return(res);
    }	
    
    public String gettext() {
    	String res=null;
    	nextKey();
    	if(key < lowkey) {
    		if(TESTING>1) System.out.println("ByteInputStream.gettext: key="+key);
    		res=intext(key);
    	} else if(key==longText) {
    		NOTIMPL("");
    		int tlength=in2byte();
    		res=intext(tlength);
    	} else if(key==longSwap) {
    		NOTIMPL("");
//              inspect CURF do begin
//                tlength:=nextNumber; simsymbol:-blanks(tlength);
//                simsymbol:-intext(simsymbol);
//                tlength:=inbyte*256 + inbyte;
//                intext(attrbuffer.sub(1,tlength)); p:=0;
//             end
    	} else ERROR("wrongLayout");
    	if(TESTING>1) System.out.println("ByteInputStream.gettext: \""+res+'"');
    	return(res);
    }

    public void nextKey() {
    	while((key=readbyte())==bufSwap) swapIbuffer();
    	if(TESTING>1) System.out.println("ByteInputStream.nextKey: key="+edMaybeKey(key));
    }


    public void swapIbuffer() {
    	in2byte(); // bufsize: SKIP it
    }

	
	public static String getCallChain() {
		StackTraceElement[] stackTrace=Thread.currentThread().getStackTrace(); 
//		String methodName=stackTrace[3].getMethodName();
		String callChain=""; String dot="";
		int n=stackTrace.length-1;
		for(int i=3; i<n; i++) {
			int line=stackTrace[i].getLineNumber();
			callChain=stackTrace[i].getMethodName()+"["+line+"]"+dot+callChain;
			dot=".";
		}
		return(callChain);
	}

}
