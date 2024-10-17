package bec.syntaxClass.value;

import bec.util.Scode;

public class AttributeAddress extends Value {
	boolean isANONE;
	int tag;
	
	public AttributeAddress(boolean isANONE) {
		this.isANONE = isANONE;
		parse();
	}

	/**
	 * 	attribute_address
	 * 		::= c-aaddr attribute:tag
	 * 		::= ANONE
	 */
	public void parse() {
		if(! isANONE) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isANONE) return "ANONE";
		return("C-AADDR " + Scode.edTag(tag));
	}
	

}
