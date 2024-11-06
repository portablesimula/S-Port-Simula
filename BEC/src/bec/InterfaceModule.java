package bec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.syntaxClass.SyntaxClass;
import bec.syntaxClass.instruction.LINE;
import bec.syntaxClass.instruction.RECORD;
import bec.syntaxClass.programElement.INFO;
import bec.syntaxClass.programElement.INSERT;
import bec.syntaxClass.programElement.Variable;
import bec.syntaxClass.programElement.ProgramElement;
import bec.syntaxClass.programElement.SETSWITCH;
import bec.syntaxClass.programElement.TAG;
import bec.syntaxClass.programElement.TAGLIST;
import bec.syntaxClass.programElement.routine.PROFILE;
import bec.syntaxClass.programElement.routine.ROUTINE;
import bec.syntaxClass.value.CONST;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class InterfaceModule extends S_Module {
	Vector<ProgramElement> programElements;
	Vector<TAG> tagList;

	public InterfaceModule() {
		programElements = new Vector<ProgramElement>();
		tagList = new Vector<TAG>();

		parse();		
	}
	
	/**
	 * 	interface_module
	 * 		::= global module module_id:string checkcode:string
	 * 					<global interface>* tag_list
	 * 					body < init global:tag type repetition_value >*
	 * 			endmodule
	 * 
	 * 		global_interface
	 * 			::= record_descriptor
	 * 			::= constant_definition < system sid:string >?
	 * 			::= global_definition < system sid:string >?
	 * 			::= routine_profile
	 * 			::= info_setting
	 * 
	 * 				global_definition ::= global internal:newtag quantity_descriptor
	 * 
	 * 		tag_list ::= < tag internal:tag external:number >+
	 * 
	 */
	private void parse() {
		Scode.expect(Scode.S_GLOBAL);
		Scode.expect(Scode.S_MODULE);
		modident = Scode.inString();
		modcheck = Scode.inString();
		Global.moduleID = modident;
		Global.CSEG = new DataSegment("CSEG_" + modident, Segment.SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + modident, Segment.SEG_DATA);
		
		ProgramElement elt = inGlobalElement();
		while(elt != null) {
			programElements.add(elt);
			elt = inGlobalElement();
		}

		programElements.add(new TAGLIST());

		if(Scode.curinstr != Scode.S_BODY) {
			Util.IERR("Illegal termination of module head. Curinstr = " + Scode.edInstr(Scode.curinstr));
		}
//		System.out.println("InterfaceModule.parse: Curinstr = " + Scode.edInstr(Scode.curinstr));
		while(Scode.accept(Scode.S_INIT)) {
			Util.IERR("InterfaceModule: Init values is not supported");
//			int wrd = Scode.inTag();
//			int type = Scode.inType();
//			SkipRepValue;
		}
		Scode.expect(Scode.S_ENDMODULE);
 		
  		if(Global.PRINT_SYNTAX_TREE) printTree(0);
  		
  		try {
			this.write();
			
			if(Global.ATTR_OUTPUT_DUMP) {
				INSERT.insertModule(Global.getAttrFileName(modident));  // TODO: TESTING
	  			System.out.println("InterfaceModule.parse: END TESTING");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		if(Global.SEGMENT_OUTPUT_DUMP) {
  			Global.DSEG.dump("Final: ");
  			Global.CSEG.dump("Final: ");
  		}
  	}

	private ProgramElement inGlobalElement() {
		Scode.inputInstr();
		switch(Scode.curinstr) {
			case Scode.S_GLOBAL:	    return new Variable(Scode.S_GLOBAL);
			case Scode.S_CONSTSPEC:		return CONST.of(false);
			case Scode.S_CONST:			return CONST.of(true);
			case Scode.S_RECORD:		return new RECORD();
			case Scode.S_PROFILE:		return new PROFILE(); // InProfile(P_VISIBLE);
//			case Scode.S_ROUTINE:		return new ROUTINE();
			case Scode.S_SETSWITCH:		return new SETSWITCH();
			case Scode.S_INFO:			return new INFO();
//			case Scode.S_DECL:			return new LINE(1);
//			case Scode.S_STMT:			return new LINE(2);
			case Scode.S_LINE:			return new LINE(0);
//			case Scode.S_INSERT:		return new INSERT(false);
//			case Scode.S_SYSINSERT:		return new INSERT(true);
			default: return null;
		}
	}

	@Override
	public void printTree(final int indent) {
		this.sLIST(0, 0, "GLOBAL MODULE");
		System.out.println("ModuleDefinition.printTree: nProgramElements="+programElements.size());
		for(ProgramElement elt:programElements) elt.printTree(indent + 1);
		this.sLIST(1, Scode.curline, "BODY");
		this.sLIST(0, Scode.curline, "ENDMODULE");
		this.sLIST(0, Scode.curline, "ENDPROGRAM");
	}

}
