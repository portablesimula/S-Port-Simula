package bec.instruction;

import bec.S_Module;
import bec.util.Scode;
import bec.util.Util;

public class SAVE extends Instruction {
//	Vector<ProgramElement> programElements;
	
	/**
	 * protect_statement ::= save <program_element>* restore
	 */
	public SAVE() {
		parse();
	}

	/**
	 * protect_statement ::= save <program_element>* restore
	 * 
	 * End-Condition: Scode'nextByte = First byte after ESEG
	 */
	public void parse() {
//		programElements = new Vector<ProgramElement>();
//		while(Scode.nextByte() != Scode.S_RESTORE) {
//			ProgramElement elt = ProgramElement.inProgramElement();
//			if(elt == null) Util.IERR("Inconsistent instruction in SAVE-RESTORE");
//			programElements.add(elt);
//		}
		S_Module.programElements();
		Scode.inputInstr();
//		printTree(0);
		Util.IERR("NOT IMPLEMENTED");
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + "SAVE");
//		for(ProgramElement elt:programElements) {
//			elt.printTree(indent + 1);
//		}
		System.out.println(indent + "RESTORE");
	}
	
	public String toString() {
		return "SAVE .... RESTORE";
	}

}

