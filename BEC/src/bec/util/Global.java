package bec.util;

import java.io.File;

import bec.descriptor.Descriptor;
import bec.segment.DataSegment;
import bec.segment.MemAddr;
import bec.segment.ProgramSegment;
import bec.syntaxClass.SyntaxClass;

public class Global {
	public static boolean INLINE_TESTING = false;
	public static boolean verbose = false;
	public static int traceMode = 0;
	public static boolean SCODE_INPUT_TRACE = false;
	public static boolean PRINT_SYNTAX_TREE = false;
	public static boolean ATTR_INPUT_TRACE = false;
	public static boolean ATTR_OUTPUT_TRACE = false;
	public static boolean ATTR_INPUT_DUMP = false;
	public static boolean ATTR_OUTPUT_DUMP = false;
	public static boolean SEGMENT_INPUT_DUMP = false;
	public static boolean SEGMENT_OUTPUT_DUMP = false;
	

	// C:/Simuletta/SCode/simulaRTS/RT.AT2
	public final static String rtsDir = "C:/Simuletta/SCode/simulaRTS/Attr/";

	public static Array<SyntaxClass> Display = new Array<SyntaxClass>();
	public static SyntaxClass getMeaning(int tag) {
		SyntaxClass x = Display.get(tag);
		if(x == null) Util.IERR("Missing meaning: " + Scode.edTag(tag));
		return(x);
	}
	
	public static Array<Descriptor> DISPL = new Array<Descriptor>();
	public static void intoDisplay(Descriptor d, int tag) {
		if(tag != 0) {
			Descriptor prev = DISPL.get(tag);
			if(prev == null) ; // OK
			else if(prev != d) Util.IERR("Display-entry is already defined: " + Scode.edTag(tag) + "  " + prev);
			DISPL.set(tag, d);
			if(Global.traceMode != 0) System.out.println("Display(" + Scode.edTag(tag) + ") = " +d);
		}
	}

	public static void dumpDISPL(String title) {
		System.out.println("============ "+title+" BEGIN Dump Display ================");
		for(int i=32;i<DISPL.size();i++) {
			Descriptor elt =DISPL.get(i);
			System.out.println("  " + i + ": " + elt);
		}
		System.out.println("============ "+title+"ENDOF Dump Display ================");
	}

	public static void dumpDisplay(String title) {
		System.out.println("============ "+title+" BEGIN Dump Display ================");
		for(int i=0;i<Display.size();i++) {
			SyntaxClass elt =Display.get(i);
			System.out.println("  " + i + ": " + elt);
		}
		System.out.println("============ "+title+"ENDOF Dump Display ================");
	}
	
	public static String scodeSource;	// S-Code source file name;
	public static String progIdent;		// S-Code PROG String
	public static String moduleID;		// S-Module ident String or MAIN
	public static String modident;   	// Ident of module being defined
	public static String modcheck;		// Check code of module being defined
	public static String PROGID;		// Ident of program being defined
	public static int sourceLineNumber;
	public static int curline;		// Current source line number
	public static int nTags; // See: INSERT

	public static boolean insideRoutine;  // Inside Routine Body indicator

	public static DataSegment CSEG; // Constant Segment
	public static DataSegment DSEG; 
	public static ProgramSegment PSEG; // Current PSEG

	public static MemAddr[] DESTAB = new MemAddr[64];

	public static String getSourceID() {
		File file = new File(scodeSource);
		String name = file.getName();
		int p = name.indexOf('.');
		String s = name.substring(0, p);
		return s;
	}

	public static String getAttrFileName(String modident) {
		if(modident == null) {
			int p = scodeSource.indexOf('.');
			String s = scodeSource.substring(0, p);
//			Util.IERR(s + ".AT2");
			return s + ".AT2";
		} else {
			return rtsDir + modident + ".AT2";
		}
	}
}
