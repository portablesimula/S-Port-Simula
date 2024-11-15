package bec.instruction;

public class DIST extends Instruction {
	
	/**
	 * addressing_instruction ::= dist
	 */
	public DIST() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "DIST";
	}
	

}
