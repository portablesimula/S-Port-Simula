package bec.syntaxClass.instruction;

import bec.util.Scode;

public class RUPDATE extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 */
	public RUPDATE() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "RUPDATE";
	}
	

}
