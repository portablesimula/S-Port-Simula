package bec.instruction;

public class ASSIGN extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 */
	public ASSIGN() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "ASSIGN";
	}
	

}
