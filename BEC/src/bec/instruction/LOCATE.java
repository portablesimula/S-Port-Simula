package bec.instruction;

public class LOCATE extends Instruction {
	
	/**
	 * addressing_instruction ::= locate
	 */
	public LOCATE() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "LOCATE";
	}
	

}
