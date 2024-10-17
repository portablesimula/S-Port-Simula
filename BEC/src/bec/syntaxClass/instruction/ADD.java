package bec.syntaxClass.instruction;

import bec.util.Scode;

public class ADD extends Instruction {
	
	/**
	 * arithmetic_instruction ::= add
	 */
	public ADD() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "ADD";
	}
	

}
