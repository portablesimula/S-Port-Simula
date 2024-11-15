package bec.instruction;

public class UPDATE extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 */
	public UPDATE() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "UPDATE";
	}
	

}
