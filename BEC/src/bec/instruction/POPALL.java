package bec.instruction;

public class POPALL extends Instruction {
	
	/**
	 * stack_instruction ::= pop
	 */
	public POPALL() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "POPALL";
	}
	

}
