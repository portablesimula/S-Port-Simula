package bec.instruction;

import bec.util.Scode;

public class DSIZE extends Instruction {
	int tag;
	
	/**
	 * addressing_instruction ::= dsize structured_type
	 * 
	 *		structured_type ::= record_tag:tag
	 */
	public DSIZE() {
		tag = Scode.inTag();
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "DSIZE " + Scode.edTag(tag);
	}
	

}
