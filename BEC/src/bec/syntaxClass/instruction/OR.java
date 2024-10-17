package bec.syntaxClass.instruction;

public class OR extends Instruction {
	
	/**
	 * arithmetic_instruction ::= or
	 */
	public OR() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "OR";
	}
	

}
