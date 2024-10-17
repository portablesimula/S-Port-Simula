package bec.syntaxClass.instruction;

import bec.util.Scode;

public class FJUMP extends Instruction {
	int destination;
	
	public FJUMP() {
		parse();
	}

	/**
	 * forward_jump ::= fjump destination:newindex
	 */
	public void parse() {
		destination = Scode.inByte();
//		printTree(2);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "FJUMP " + destination;
	}
	

}
