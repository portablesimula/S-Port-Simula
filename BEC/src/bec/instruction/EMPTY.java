package bec.instruction;

public class EMPTY extends Instruction {
	
	/**
	 * stack_instruction ::= empty
	 */
	public EMPTY() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "EMPTY";
	}
	

}
