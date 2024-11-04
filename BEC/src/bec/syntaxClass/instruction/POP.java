package bec.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;

public class POP extends Instruction {
	
	/**
	 * stack_instruction ::= pop
	 */
	public POP() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}

	@Override
	public void doCode() {
		CTStack.dumpStack();
		CTStack.pop();
	}
	
	public String toString() {
		return "POP";
	}
	

}
