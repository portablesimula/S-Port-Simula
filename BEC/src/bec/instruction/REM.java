package bec.instruction;

public class REM extends Instruction {
	
	/**
	 * arithmetic_instruction ::= rem
	 */
	public REM() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "REM";
	}
	

}
