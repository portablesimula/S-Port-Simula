package bec.inlineTest;

import bec.util.Global;
import bec.BecCompiler;

public class MiniInlineTest {
	
	public static void main(String[] argv) {
		String scodeSource = Pick_Source();		
		
		Global.verbose = true;
//		Global.traceMode = 4;
//		Global.SCODE_INPUT_TRACE = true;
		Global.PRINT_SYNTAX_TREE = true;
		Global.ATTR_INPUT_TRACE = true;
		Global.ATTR_OUTPUT_TRACE = true;
		Global.ATTR_INPUT_DUMP = true;
		Global.ATTR_OUTPUT_DUMP = true;
//		Global.SEGMENT_INPUT_DUMP = true;
//		Global.SEGMENT_OUTPUT_DUMP = true;

		new BecCompiler(scodeSource);
	}

	private static String Pick_Source() {
//		return "C:/Simuletta/SCode/simulettaTestPrograms/ENVIR0.scd";
//		return "C:/Simuletta/SCode/simulettaTestPrograms/MODL01.scd";
//		return "C:/Simuletta/SCode/simulettaTestPrograms/MODL02.scd";
		return "C:/Simuletta/SCode/simulettaTestPrograms/TEST6.scd";
	}


}
