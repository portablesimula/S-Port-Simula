package bec.syntaxClass.instruction;

public class LOCATE extends Instruction {
	
	/**
	 * addressing_instruction ::= locate
	 */
	public LOCATE() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "LOCATE";
	}
	

}
