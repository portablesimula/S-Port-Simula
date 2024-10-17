package bec.syntaxClass.instruction;

public class IMP extends Instruction {
	
	/**
	 * arithmetic_instruction ::= imp
	 */
	public IMP() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "IMP";
	}
	

}
