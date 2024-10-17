package bec.syntaxClass.instruction;

import bec.util.Scode;

public class BDEST extends Instruction {
	int destination;
	
	public BDEST() {
		parse();
	}

	/**
	 * backward_destination ::= bdest destination:newindex
	 */
	public void parse() {
		destination = Scode.inByte();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "BDEST " + destination;
	}
	

}
