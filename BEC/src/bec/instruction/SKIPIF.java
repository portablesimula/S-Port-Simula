package bec.instruction;

import bec.S_Module;
import bec.util.Relation;
import bec.util.Util;

public class SKIPIF extends Instruction {
	Relation relation;
//	Vector<ProgramElement> programElements;
	
	public SKIPIF() {
		parse();
	}

	/**
	 * skip_statement ::= skipif relation <program_element>* endskip
	 */
	public void parse() {
		relation = Relation.ofScode();
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

	@Override
	public void print(final String indent) {
		System.out.println(indent + "SKIPIF");
//		for(ProgramElement elt:programElements) {
//			elt.printTree(indent + 1);
//		}
		System.out.println(indent + "ENDSKIP");
	}

	public void print() {
		System.out.println("SKIPIF " + relation);
//		for(ProgramElement elt:programElements) {
//			System.out.println("   " + elt);
//		}
		System.out.println("ENDSKIP");
	}

	public String toString() {
		return "SKIPIF ... ENDSKIP";
	}

}
