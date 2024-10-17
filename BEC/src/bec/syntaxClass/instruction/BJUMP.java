package bec.syntaxClass.instruction;

import bec.util.Scode;

public class BJUMP extends Instruction {
	int destination;
	
	public BJUMP() {
		parse();
	}

	/**
	 * backward_jump ::= bjump destination:index
	 */
	public void parse() {
		destination = Scode.inByte();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "BJUMP " + destination;
	}
	

}
