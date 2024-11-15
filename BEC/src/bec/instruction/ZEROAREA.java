package bec.instruction;

public class ZEROAREA extends Instruction {
	
	/**
	 * area_initialisation ::= zeroarea
	 */
	public ZEROAREA() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "ZEROAREA";
	}
	

}
