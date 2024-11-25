package bec.instruction;

public class DUP extends Instruction {
	
	/**
	 * stack_instruction ::= dup
	 */
	public DUP() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "DUP";
	}
	

}
