package bec.instruction;

public class SETO extends Instruction {
	
	/**
	 * temp_control ::= t-seto
	 */
	public SETO() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "SETO";
	}
	

}
