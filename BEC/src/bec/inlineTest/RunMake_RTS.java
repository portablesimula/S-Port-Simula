package bec.inlineTest;

import bec.util.Global;

import java.util.Vector;

import bec.BecCompiler;

public class RunMake_RTS {
	
	public static void main(String[] argv) {
		Vector<String> names=new Vector<String>();
		
		names.add("RT");
		names.add("SYSR");
		names.add("KNWN");
		names.add("UTIL");
		names.add("STRG");
		names.add("CENT");
		names.add("CINT");
		names.add("ARR");
		names.add("FORM");
		names.add("LIBR");
		names.add("FIL");
		names.add("SMST");
		names.add("SML");
		names.add("EDIT");
		names.add("MNTR");

		Global.verbose = true;
//		Global.traceMode = 4;
//		Global.SCODE_INPUT_TRACE = true;
//		Global.PRINT_SYNTAX_TREE = true;
//		Global.ATTR_INPUT_TRACE = true;
//		Global.ATTR_OUTPUT_TRACE = true;
//		Global.ATTR_INPUT_DUMP = true;
//		Global.ATTR_OUTPUT_DUMP = true;
//		Global.SEGMENT_INPUT_DUMP = true;
//		Global.SEGMENT_OUTPUT_DUMP = true;

		for(String name:names) {
			String fileName = "C:/Simuletta/SCode/simulaRTS/"+name+".scd";
			new BecCompiler(fileName);
		}
	}

}
