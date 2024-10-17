package bec.syntaxClass.instruction;

public class REM extends Instruction {
	
	/**
	 * arithmetic_instruction ::= rem
	 */
	public REM() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "REM";
	}
	

}
