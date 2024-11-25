package bec.instruction;

public class DIST extends Instruction {
	
	/**
	 * addressing_instruction ::= dist
	 */
	public DIST() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "DIST";
	}
	

}
