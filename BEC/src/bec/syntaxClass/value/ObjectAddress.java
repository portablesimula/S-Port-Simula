package bec.syntaxClass.value;

import bec.util.Scode;

public class ObjectAddress extends Value {
	boolean isONONE;
	int tag;
	
	public ObjectAddress(boolean isONONE) {
		this.isONONE = isONONE;
		parse();
	}

	/**
	 * object_address
	 * 		::= c-oaddr global_or_const:tag
	 * 		::= onone
	 */
	public void parse() {
		if(! isONONE) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isONONE) return "ONONE";
		return("C-OADDR " + Scode.edTag(tag));
	}
	

}
