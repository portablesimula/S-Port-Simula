package bec.syntaxClass.instruction;

public class FETCH extends Instruction {
	
	/**
	 * addressing_instruction ::= fetch
	 */
	public FETCH() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "FETCH";
	}
	

}
