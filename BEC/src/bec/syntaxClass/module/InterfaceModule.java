package bec.syntaxClass.module;

import java.util.Vector;

import bec.syntaxClass.instruction.LINE;
import bec.syntaxClass.instruction.RECORD;
import bec.syntaxClass.programElement.INFO;
import bec.syntaxClass.programElement.INSERT;
import bec.syntaxClass.programElement.Variable;
import bec.syntaxClass.programElement.ProgramElement;
import bec.syntaxClass.programElement.SETSWITCH;
import bec.syntaxClass.programElement.routine.PROFILE;
import bec.syntaxClass.programElement.routine.ROUTINE;
import bec.syntaxClass.value.CONST;
import bec.util.Scode;
import bec.util.Util;

public class InterfaceModule extends S_Module {
	String modident;
	String modcheck;
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
		
		ProgramElement elt = inGlobalElement();
		while(elt != null) {
			programElements.add(elt);
			elt = inGlobalElement();
		}

		programElements.add(new TAGLIST());

		if(Scode.curinstr != Scode.S_BODY) {
			Util.IERR("Illegal termination of module head");
		}
//		System.out.println("InterfaceModule.parse: Curinstr = " + Scode.edInstr(Scode.curinstr));
		while(Scode.accept(Scode.S_INIT)) {
			Util.IERR("InterfaceModule: Init values is not supported");
//			int wrd = Scode.inTag();
//			int type = Scode.inType();
//			SkipRepValue;
		}
		Scode.expect(Scode.S_ENDMODULE);
 		
  		if(Scode.listing)
  			printTree(0);
	}

	private ProgramElement inGlobalElement() {
		Scode.inputInstr();
		switch(Scode.curinstr) {
			case Scode.S_GLOBAL:	    return new Variable(Scode.S_GLOBAL);
			case Scode.S_CONSTSPEC:		return new CONST(false);
			case Scode.S_CONST:			return new CONST(true);
			case Scode.S_RECORD:		return new RECORD();
			case Scode.S_PROFILE:		return new PROFILE(); // InProfile(P_VISIBLE);
			case Scode.S_ROUTINE:		return new ROUTINE(); // InRoutine
			case Scode.S_SETSWITCH:		return new SETSWITCH(); // SetSwitch();
			case Scode.S_INFO:			return new INFO(); // Ed(errmsg,InString); WARNING("Unknown info: ");
			case Scode.S_DECL:			return new LINE(1);
			case Scode.S_STMT:			return new LINE(2);
			case Scode.S_LINE:			return new LINE(0);
			case Scode.S_INSERT:		return new INSERT(false);
			case Scode.S_SYSINSERT:		return new INSERT(true);
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
