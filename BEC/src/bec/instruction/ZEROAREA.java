package bec.instruction;

public class ZEROAREA extends Instruction {
	
	/**
	 * area_initialisation ::= zeroarea
	 */
	public ZEROAREA() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "ZEROAREA";
	}
	

}
