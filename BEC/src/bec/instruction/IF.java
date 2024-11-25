package bec.instruction;

import java.util.Vector;

import bec.S_Module;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT_IMPL;

public class IF extends Instruction {
	Relation relation;
	
	// NOT SAVED:
	Vector<Instruction> thenElements;
	Vector<Instruction> elseElements;
	
	public IF() {
		parse();
	}

	/**
	 * 	if_statement ::= if relation <program_element>* else_part
	 * 
	 * 		else_part
	 * 			::= else <program_element>* endif
	 * 			::= endif
	 */
	public void parse() {
		relation = Relation.ofScode();
		thenElements = new Vector<Instruction>();
		LOOP: while(true) {
			switch(Scode.nextByte()) {
				case Scode.S_ELSE -> {
//					System.out.println("IF: BEGIN ELSE");
					Scode.inputInstr();
					elseElements = new Vector<Instruction>();
					while(Scode.nextByte() != Scode.S_ENDIF) {
						readElement(elseElements);
					}
					break LOOP;
				}
				case Scode.S_ENDIF -> { break LOOP; }
				default -> { readElement(thenElements); }
			}
		}
		
		// PRØV NOE SÅNNT:
//		S_Module.programElements();
//		if(Scode.nextByte() == Scode.S_ELSE) {
//			
//		}
//		SJEKK S_ENDIF
//		
//		Scode.inputInstr();
	
//		if(Scode.inputTrace > 3) printTree(0);
//		Util.IERR("NOT IMPLEMENTED");
	}
	
	private void readElement(Vector<Instruction> programElements) {
		Instruction elt = (Instruction) Instruction.inInstruction();
		if(elt == null) Util.IERR("Inconsistent instruction in IF");
		programElements.add(elt);
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
//		Global.PSEG.dump();
		Global.PSEG.emit(new SVM_NOT_IMPL(), "IF Statement");
	}
	
	@Override
	public void print(final String indent) {
		System.out.println(indent + "IF " + relation);
//		for(ProgramElement elt:thenElements) {
//			elt.printTree(indent + 1);
//		}
//		if(elseElements != null) {
//			System.out.println(indent + "ELSE");
//			for(ProgramElement elt:elseElements) {
//				elt.printTree(indent + 1);
//			}			
//		}
		System.out.println(indent + "ENDIF");
	}

	public String toString() {
		return "SKIPIF ... ENDSKIP";
	}

}
