package bec.syntaxClass.module;

import java.util.Vector;

import bec.syntaxClass.programElement.Variable;
import bec.syntaxClass.programElement.ProgramElement;
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
		Scode.expect(Scode.S_MAIN);
		while(Scode.accept(Scode.S_LOCAL)) programElements.add(new Variable(Scode.S_LOCAL));
		
		LOOP: while(true) {
			ProgramElement elt = ProgramElement.inProgramElement();
			if(elt == null) break LOOP;
			programElements.add(elt);
		}
		if(Scode.curinstr != Scode.S_ENDPROGRAM)
			Util.IERR("Improper termination of s-program");
 		
  		if(Scode.listing)
  			printTree(0);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(0, 0, "MAIN");
		for(ProgramElement elt:programElements) elt.printTree(indent + 1);
		sLIST(0, Scode.curline, "ENDPROGRAM");
	}
	
}
