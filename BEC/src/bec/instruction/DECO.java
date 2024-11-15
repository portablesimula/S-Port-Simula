package bec.instruction;

public class DECO extends Instruction {
	
	/**
	 * addressing_instruction ::= deco
	 */
	public DECO() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "DECO";
	}
	

}
