package bec.syntaxClass.instruction;

import bec.util.Scode;

public class SDEST extends Instruction {
	int tag;
	int which;
	
	/**
	 * forward_destination ::= sdest switch:tag which:number
	 */
	public SDEST() {
		tag = Scode.inTag();
		which = Scode.inNumber();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "SDEST " + Scode.edTag(tag) + " " + which;
	}
	

}
