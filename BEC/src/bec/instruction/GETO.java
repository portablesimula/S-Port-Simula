package bec.instruction;

public class GETO extends Instruction {
	
	/**
	 * temp_control ::= t-geto
	 */
	public GETO() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "GETO";
	}
	

}
