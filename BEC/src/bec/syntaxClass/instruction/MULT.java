package bec.syntaxClass.instruction;

public class MULT extends Instruction {
	
	/**
	 * arithmetic_instruction ::= mult
	 */
	public MULT() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "MULT";
	}
	

}
