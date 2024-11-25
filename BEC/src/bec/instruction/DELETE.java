package bec.instruction;

import bec.util.Scode;

public class DELETE extends Instruction {
	int tag;
	
	public DELETE() {
		parse();
	}

	/**
	 * delete_statement ::= delete from:tag
	 * 
     * check stacks empty;
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		tag = Scode.inTag();
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "DELETE " + Scode.edTag(tag);
	}
	

}
