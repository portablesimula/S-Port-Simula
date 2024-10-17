package bec.syntaxClass.instruction;

import bec.util.Scode;

public class SWITCH extends Instruction {
	int tag;
	int size;
	
	/**
	 * forward_jump ::= switch switch:newtag size:number
	 */
	public SWITCH() {
		tag = Scode.inTag();
		size = Scode.inNumber();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "SWITCH " + Scode.edTag(tag) + " " + size;
	}
	

}
