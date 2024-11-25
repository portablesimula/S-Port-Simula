package bec.instruction;

public class EVAL extends Instruction {
	
	/**
	 * eval_instruction ::= eval
	 */
	public EVAL() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "EVAL";
	}
	

}
