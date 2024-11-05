package bec;

import java.io.FileOutputStream;
import java.io.IOException;

import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.syntaxClass.SyntaxClass;
import bec.syntaxClass.instruction.RECORD;
import bec.syntaxClass.programElement.Variable;
import bec.syntaxClass.programElement.routine.PROFILE;
import bec.syntaxClass.value.CONST;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class S_Module extends SyntaxClass {
	public static String modident;     // Module ident String
	public static String modcheck;     // Module check String
	
	public S_Module() {
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write() throws IOException {
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("**************   Begin  -  Output-module  " + modident + "  " + modcheck + "   **************");
		AttributeOutputStream modoupt = new AttributeOutputStream(new FileOutputStream(Global.getAttrFileName(modident)));
		modoupt.writeKind(Scode.S_MODULE);
		modoupt.writeString(modident);
		modoupt.writeString(modcheck);
		
		// Write Segments
		Global.DSEG.write(modoupt);
		Global.CSEG.write(modoupt);

		for(SyntaxClass elt:Global.Display) {
//			if(elt != null) elt.write(modoupt);
			boolean doWrite = false;
			if(elt instanceof Segment) doWrite = true;
			else if(elt instanceof RECORD) doWrite = true;
			else if(elt instanceof PROFILE) doWrite = true;
			else if(elt instanceof CONST) doWrite = true;
			else if(elt instanceof Variable var) {
				if(var.instr == Scode.S_GLOBAL) doWrite = true;
				if(var.instr == Scode.S_LOCAL) doWrite = true;
			}
			
			if(doWrite) elt.write(modoupt);
		}

		modoupt.writeKind(Scode.S_ENDMODULE);
		modoupt.close();
		
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("**************   End of  -  Output-module  " + modident + "  " + modcheck + "   **************");
		
		if(Global.SEGMENT_OUTPUT_DUMP & Global.ATTR_OUTPUT_DUMP) {
			Global.DSEG.dump("S_Module.write: ");
			Global.CSEG.dump("S_Module.write: ");
		}
//		Util.IERR("");
	}

}
