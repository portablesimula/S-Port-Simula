package bec.syntaxClass.instruction;

public class AND extends Instruction {
	
	/**
	 * arithmetic_instruction ::= and
	 */
	public AND() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "AND";
	}
	

}
