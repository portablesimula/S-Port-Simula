package bec.syntaxClass.instruction;

public class NOT extends Instruction {
	
	/**
	 * arithmetic_instruction ::= and
	 */
	public NOT() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "NOT";
	}
	

}
