package bec.instruction;

public class UPDATE extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 */
	public UPDATE() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "UPDATE";
	}
	

}
