package bec.syntaxClass.value;

import bec.util.Scode;

public class GeneralAddress extends Value {
	boolean isGNONE;
	int tag;
	
	public GeneralAddress(boolean isGNONE) {
		this.isGNONE = isGNONE;
		parse();
	}

	/**
	 * 	general_address
	 * 		::= c-gaddr global_or_const:tag
	 * 		::= gnone
	 */
	public void parse() {
		if(! isGNONE) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isGNONE) return "GNONE";
		return("C-GADDR " + Scode.edTag(tag));
	}
	

}
