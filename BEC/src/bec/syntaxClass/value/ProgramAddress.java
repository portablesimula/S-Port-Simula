package bec.syntaxClass.value;

import bec.util.Scode;

public class ProgramAddress extends Value {
	boolean isNOWHERE;
	int tag;
	
	public ProgramAddress(boolean isNOWHERE) {
		this.isNOWHERE = isNOWHERE;
		parse();
	}

	/**
	 * program_address
	 * 		::= c-paddr label:tag
	 * 		::= NOWHERE
	 */
	public void parse() {
		if(! isNOWHERE) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isNOWHERE) return "NOWHERE";
		return("C-PADDR " + Scode.edTag(tag));
	}
	

}
