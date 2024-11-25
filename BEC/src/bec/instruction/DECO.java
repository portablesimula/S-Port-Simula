package bec.instruction;

public class DECO extends Instruction {
	
	/**
	 * addressing_instruction ::= deco
	 */
	public DECO() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "DECO";
	}
	

}
