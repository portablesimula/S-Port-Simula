package bec.syntaxClass.instruction;

public class SUB extends Instruction {
	
	/**
	 * arithmetic_instruction ::= sub
	 */
	public SUB() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "SUB";
	}
	

}
