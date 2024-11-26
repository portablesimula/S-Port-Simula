package bec.instruction;

import bec.S_Module;
import bec.util.Relation;
import bec.util.Util;

public abstract class SKIPIF extends Instruction {

	/**
	 * skip_statement ::= skipif relation <program_element>* endskip
	 */
	public static void ofScode() {
		Relation relation = Relation.ofScode();
//		programElements = new Vector<ProgramElement>();
//		while(Scode.nextByte() != Scode.S_ENDSKIP) {
//			ProgramElement elt = ProgramElement.inProgramElement();
//			if(elt == null) Util.IERR("Inconsistent instruction in SKIPIF");
//			programElements.add(elt);
//		}
//		Scode.inputInstr();
		S_Module.programElements();
	
//		if(Scode.inputTrace > 3) print();
		Util.IERR("NOT IMPLEMENTED");
	}

}
