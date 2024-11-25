package bec.instruction;

public class EMPTY extends Instruction {
	
	/**
	 * stack_instruction ::= empty
	 */
	public EMPTY() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "EMPTY";
	}
	

}
