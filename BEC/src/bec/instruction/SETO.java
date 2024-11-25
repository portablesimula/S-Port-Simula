package bec.instruction;

public class SETO extends Instruction {
	
	/**
	 * temp_control ::= t-seto
	 */
	public SETO() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "SETO";
	}
	

}
