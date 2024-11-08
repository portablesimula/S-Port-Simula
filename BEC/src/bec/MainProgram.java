package bec;

import java.util.Vector;

import bec.syntaxClass.programElement.Variable;
import bec.syntaxClass.programElement.routine.PREV_PROFILE;
import bec.syntaxClass.programElement.routine.PREV_ROUTINE;
import bec.syntaxClass.value.CONST;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.syntaxClass.instruction.CallInstruction;
import bec.syntaxClass.programElement.BODY;
import bec.syntaxClass.programElement.INSERT;
import bec.syntaxClass.programElement.ProgramElement;
import bec.syntaxClass.programElement.TAGLIST;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class MainProgram extends S_Module {
	Vector<ProgramElement> programElements;

	public MainProgram() {
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
	public void parse() {
		Global.CSEG = new DataSegment("CSEG_" + modident, Segment.SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + modident, Segment.SEG_DATA);
		Scode.expect(Scode.S_MAIN);
		while(Scode.accept(Scode.S_LOCAL)) programElements.add(new Variable(Scode.S_LOCAL));
		
		LOOP: while(true) {
			ProgramElement elt = ProgramElement.inProgramElement();
			if(elt == null) break LOOP;
			programElements.add(elt);
		}
		if(Scode.curinstr != Scode.S_ENDPROGRAM)
			Util.IERR("Improper termination of s-program");
 		
  		if(Global.PRINT_SYNTAX_TREE) printTree(0);
  		
		Global.dumpDisplay("END MainProgram: ");
		
		for(ProgramElement elt:programElements) {
			boolean ok = false;
			if(elt instanceof CallInstruction) ok = true;
//			else if(elt instanceof CONST) ok = true;
//			else if(elt instanceof PROFILE) ok = true;
//			else if(elt instanceof ROUTINE) ok = true;
			
			else if(elt instanceof INSERT) ; // Nothing
			else if(elt instanceof TAGLIST) ; // Nothing
			else if(elt instanceof BODY) ; // Nothing
			else Util.IERR("MISSING: "+elt.getClass().getSimpleName());
			if(ok) elt.doCode();
		}

  		
  		Global.PSEG.dump("EXEC: ");
	}

	@Override
	public void printTree(final int indent) {
		sLIST(0, 0, "MAIN");
		for(ProgramElement elt:programElements) elt.printTree(indent + 1);
		sLIST(0, Scode.curline, "ENDPROGRAM");
	}
	
}
