package bec.instruction;

public class INITO extends Instruction {
	
	/**
	 * temp_control ::= t-inito
	 */
	public INITO() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "INITO";
	}
	

}
