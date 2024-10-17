package bec.syntaxClass.instruction;

import bec.util.Scode;

public class FDEST extends Instruction {
	int destination;
	
	public FDEST() {
		parse();
	}

	/**
	 * forward_destination ::= fdest destination:index
	 */
	public void parse() {
		destination = Scode.inByte();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "FDEST " + destination;
	}
	

}
