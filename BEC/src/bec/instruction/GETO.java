package bec.instruction;

public class GETO extends Instruction {
	
	/**
	 * temp_control ::= t-geto
	 */
	public GETO() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "GETO";
	}
	

}
