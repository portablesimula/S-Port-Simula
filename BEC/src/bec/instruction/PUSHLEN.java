package bec.instruction;

public class PUSHLEN extends Instruction {
	
	/**
	 * stack_instruction ::= pushlen
	 */
	public PUSHLEN() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "PUSHLEN";
	}
	

}
