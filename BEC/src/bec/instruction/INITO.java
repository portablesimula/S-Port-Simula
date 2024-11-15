package bec.instruction;

public class INITO extends Instruction {
	
	/**
	 * temp_control ::= t-inito
	 */
	public INITO() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "INITO";
	}
	

}
