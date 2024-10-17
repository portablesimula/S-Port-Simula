package bec.syntaxClass.instruction;

import bec.util.Scode;

public class CONVERT extends Instruction {
	int simpleType;
	
	/**
	 * convert_instruction ::= convert simple_type
	 */
	public CONVERT() {
		simpleType = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "CONVERT " + Scode.edTag(simpleType);
	}
	

}
