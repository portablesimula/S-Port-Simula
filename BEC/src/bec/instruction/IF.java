package bec.instruction;

import java.util.Vector;

import bec.S_Module;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT_IMPL;

public abstract class IF extends Instruction {
	Relation relation;
	
//	// NOT SAVED:
//	Vector<Instruction> thenElements;
//	Vector<Instruction> elseElements;

	/**
	 * 	if_statement ::= if relation <program_element>* else_part
	 * 
	 * 		else_part
	 * 			::= else <program_element>* endif
	 * 			::= endif
	 */
	public static void ofScode() {
		Relation relation = Relation.ofScode();
		Scode.inputInstr();
//		thenElements = new Vector<Instruction>();
		
		// DIV JUMP-Instruksjoner mangler
		
//		LOOP: while(true) {
//			switch(Scode.nextByte()) {
//				case Scode.S_ELSE -> {
////					System.out.println("IF: BEGIN ELSE");
//					Scode.inputInstr();
////					elseElements = new Vector<Instruction>();
////					while(Scode.nextByte() != Scode.S_ENDIF) {
////						readElement(elseElements);
////					}
//					S_Module.programElements();
//					break LOOP;
//				}
//				case Scode.S_ENDIF -> { break LOOP; }
//				default -> {
////					readElement(thenElements);
//					S_Module.programElements();
//				}
//			}
//		}
		S_Module.programElements();
		if(Scode.curinstr == Scode.S_ELSE) {
			Scode.inputInstr();
			S_Module.programElements();
		}
		if(Scode.curinstr != Scode.S_ENDIF) Util.IERR("Missing ENDIF: " + Scode.edInstr(Scode.curinstr));
		
		
//		Scode.inputInstr();  // ????
	
//		if(Scode.inputTrace > 3) printTree(0);
		Global.PSEG.emit(new SVM_NOT_IMPL(), "IF Statement");
//		Util.IERR("SJEKK DETTE");
	}
	
//	private void readElement(Vector<Instruction> programElements) {
//		Instruction elt = (Instruction) Instruction.inInstruction();
//		if(elt == null) Util.IERR("Inconsistent instruction in IF");
//		programElements.add(elt);
//	}

}
