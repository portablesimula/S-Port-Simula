package bec.syntaxClass.instruction;

public class PUSHLEN extends Instruction {
	
	/**
	 * stack_instruction ::= pushlen
	 */
	public PUSHLEN() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "PUSHLEN";
	}
	

}
