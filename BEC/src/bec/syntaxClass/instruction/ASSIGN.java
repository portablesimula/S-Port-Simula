package bec.syntaxClass.instruction;

public class ASSIGN extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 */
	public ASSIGN() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "ASSIGN";
	}
	

}