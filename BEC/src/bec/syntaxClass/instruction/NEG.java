package bec.syntaxClass.instruction;

public class NEG extends Instruction {
	
	/**
	 * arithmetic_instruction ::= neg
	 */
	public NEG() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "NEG";
	}
	

}
