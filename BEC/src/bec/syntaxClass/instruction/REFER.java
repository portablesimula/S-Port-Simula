package bec.syntaxClass.instruction;

import bec.util.ResolvedType;

public class REFER extends Instruction {
	ResolvedType type;
	
	/**
	 * addressing_instruction ::= refer resolved_type
	 */
	public REFER() {
		type = new ResolvedType();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "REFER " + type;
	}
	

}
