package bec.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Util {

	public static void setLine(int type) {
		Scode.curline = Scode.inNumber();
	}

	public static void ITRC(String id, String msg) {
		if(Scode.inputTrace > 1) {
			Scode.traceBuff = new StringBuilder("Line " + Scode.curline + "  " + id + ": " + msg);
		}
	}


	public static void ERROR(String msg) {
		if(Scode.inputTrace != 0) {
			System.out.println(Scode.traceBuff);
		}
		System.out.println("ERROR: " + msg);
	}

	public static void IERR(String msg) {
		ERROR("Internal error: " + msg);
		Thread.dumpStack();
		System.exit(0);
	}

//	%title *********    D i c t i o n a r y    *********
	public static HashMap<Integer,String> dicMap = new HashMap<Integer,String>();
	public static int nSymb;
	
	public static int DefSymb(String symb) {
		int key = nSymb++;
		dicMap.put(key, symb);
		return key;
	}

	public static String DICSMB(int n) {
		String s = dicMap.get(n);
		return s;
	}

	public static String edSymb(int i) {
		return DICSMB(i);
	}

	
	// ***************************************************************
	// *** EXECUTE OS COMMAND
	// ***************************************************************
	public static int exec(String... cmd) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		String line="";
		for(int i=0;i<cmd.length;i++) line=line+" "+cmd[i];
        System.out.println("MakeCompiler.execute: command="+line);
//	    String cmd=command.trim()+'\n';
		Process process = runtime.exec(cmd);
		//try
		{ InputStream err=process.getErrorStream();
		  InputStream inp=process.getInputStream();
		  while(process.isAlive())
		  { while(err.available()>0) System.err.append((char)err.read());
		    while(inp.available()>0) System.out.append((char)inp.read());
			
		  }
		  // process.waitFor();
		} //catch(InterruptedException e) { e.printStackTrace(); }
		return(process.exitValue());
	}

}
