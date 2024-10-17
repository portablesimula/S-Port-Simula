package bec.syntaxClass.instruction;

import bec.util.Scode;

public class DEREF extends Instruction {
	
	/**
	 * addressing_instruction ::= deref
	 */
	public DEREF() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "DEREF";
	}
	

}
