package bec.instruction;

public class GOTO extends Instruction {
	
	/**
	 * goto_instruction ::= goto
	 */
	public GOTO() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "GOTO";
	}
	

}
