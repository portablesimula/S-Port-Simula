package bec.syntaxClass.module;

import java.util.Vector;

import bec.syntaxClass.instruction.LINE;
import bec.syntaxClass.instruction.RECORD;
import bec.syntaxClass.programElement.BODY;
import bec.syntaxClass.programElement.INFO;
import bec.syntaxClass.programElement.INSERT;
import bec.syntaxClass.programElement.LABELSPEC;
import bec.syntaxClass.programElement.Variable;
import bec.syntaxClass.programElement.ProgramElement;
import bec.syntaxClass.programElement.SETSWITCH;
import bec.syntaxClass.programElement.routine.PROFILE;
import bec.syntaxClass.programElement.routine.ROUTINESPEC;
import bec.syntaxClass.value.CONST;
import bec.util.Scode;
import bec.util.Util;

public class ModuleDefinition extends S_Module {
	String modident;
	String modcheck;
	Vector<ProgramElement> localQuantities;
	Vector<ProgramElement> programElements;
	boolean withEndProgram;

	public ModuleDefinition() {
		localQuantities = new Vector<ProgramElement>();
		programElements = new Vector<ProgramElement>();
		parse();
	}
	
	/**
	 * 	MainPprogram ::= main <local_quantity>* <program_element>*
	 * 
	 * 		local_quantity ::= local var:newtag quantity_descriptor
	 * 
	 *			quantity_descriptor ::= resolved_type < Rep count:number >?
	 *
	 *		program_element
	 *			::= instruction
	 *			::= label_declaration
	 *			::= routine_profile
	 *			::= routine_definition
	 *			::= if_statement
	 *			::= skip_statement
	 *			::= insert_statement
	 *			::= protect_statement
	 *			::= delete_statement
	 */
	/**
	 * 	module_definition ::= module module_id:string check_code:string
	 * 							visible_existing
	 * 							body <local_quantity>* <program_element>* endmodule
	 * 
	 * 		visible_existing ::= <visible>* tag_list | existing
	 * 
	 * 			visible
	 * 				::= record_descriptor | routine_profile
	 * 				::= routine_specification | label_specification
	 * 				::= constant_specification | insert_statement
	 * 				::= info_setting
	 * 
	 * 			tag_list ::= < tag internal:tag external:number >+
	 * 
	 * 			local_quantity
	 * 				::= local var:newtag quantity_descriptor
	 * 				::= constant_definition                                             // DETTE ER NYTT
	 * 
	 *				constant_definition                                                 // DETTE ER NYTT
	 *					::= const const:spectag quantity_descriptor repetition_value    // DETTE ER NYTT
	 */
	private void parse() {
		Scode.expect(Scode.S_MODULE);
		modident = Scode.inString();
		modcheck = Scode.inString();

		Scode.inputInstr();
		while(visible()) Scode.inputInstr();
		
		programElements.add(new TAGLIST());

		if(Scode.curinstr != Scode.S_BODY)
			Util.IERR("Illegal termination of module head");
		programElements.add(new BODY());

		LOOP:while(true) {
			switch(Scode.nextByte()) {
				case Scode.S_LOCAL: Scode.inputInstr(); localQuantities.add(new Variable(Scode.S_LOCAL)); break;
				case Scode.S_CONST: Scode.inputInstr(); localQuantities.add(new CONST(true)); break;
				default: break LOOP;
			}
		}

		LOOP:while(true) {
			ProgramElement elt = ProgramElement.inProgramElement();
			if(elt == null) break LOOP;
			programElements.add(elt);
		}

		if(Scode.curinstr != Scode.S_ENDMODULE)
			Util.IERR("Improper termination of module");
		if(Scode.accept(Scode.S_ENDPROGRAM))
			withEndProgram = true;
	}
	
	private boolean visible() {
//		System.out.println("ModuleDefinition.visible: curinstr = " + Scode.edInstr(Scode.curinstr));
		boolean result = true;
		switch(Scode.curinstr) {
			case Scode.S_CONST->   		new CONST(true);
//			case Scode.S_CONSTSPEC->    new CONSTSPEC();
			case Scode.S_CONSTSPEC->    new CONST(false);
			case Scode.S_LABELSPEC->    new LABELSPEC();
			case Scode.S_RECORD->       new RECORD();
			case Scode.S_PROFILE->      new PROFILE(); // InProfile(P_VISIBLE);
			case Scode.S_ROUTINESPEC->  new ROUTINESPEC(); // SpecRut(true);
			case Scode.S_INSERT->       new INSERT(false);
			case Scode.S_SYSINSERT->    new INSERT(true);
			case Scode.S_DECL->			new LINE(1);
			case Scode.S_STMT->			new LINE(2);
			case Scode.S_LINE->			new LINE(0);
			case Scode.S_SETSWITCH->    new SETSWITCH(); // SetSwitch();
			case Scode.S_INFO->         new INFO(); // Ed(errmsg,InString); WARNING("Unknown info: ");
		default-> result = false;
		}
		return(result);
	}


	@Override
	public void printTree(final int indent) {
//		System.out.println("\nModuleDefinition.printTree: nLocalQuantities="+localQuantities.size());
//		System.out.println("ModuleDefinition.printTree: nProgramElements="+programElements.size());
		sLIST(0, 0, "PROGRAM " + modident + " " + modcheck);
		sLIST(0, 0, "MODULE");
		for(ProgramElement quant:localQuantities) quant.printTree(indent + 1);
		for(ProgramElement elt:programElements) elt.printTree(indent + 1);
		sLIST(0, Scode.curline, "ENDMODULE");
		if(withEndProgram) sLIST(0, Scode.curline, "ENDPROGRAM");
	}
	
}
