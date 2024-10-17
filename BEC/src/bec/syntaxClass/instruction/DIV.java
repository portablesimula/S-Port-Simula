package bec.syntaxClass.instruction;

public class DIV extends Instruction {
	
	/**
	 * arithmetic_instruction ::= mult
	 */
	public DIV() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "MULT";
	}
	

}
