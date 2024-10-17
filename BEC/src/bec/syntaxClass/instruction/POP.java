package bec.syntaxClass.instruction;

public class POP extends Instruction {
	
	/**
	 * stack_instruction ::= pop
	 */
	public POP() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "POP";
	}
	

}
