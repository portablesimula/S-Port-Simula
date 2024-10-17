package bec.syntaxClass.instruction;

public class EVAL extends Instruction {
	
	/**
	 * eval_instruction ::= eval
	 */
	public EVAL() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "EVAL";
	}
	

}
