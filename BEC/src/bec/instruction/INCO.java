package bec.instruction;

public class INCO extends Instruction {
	
	/**
	 * addressing_instruction ::= inco
	 */
	public INCO() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "INCO";
	}
	

}
