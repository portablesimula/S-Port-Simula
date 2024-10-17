package bec.syntaxClass.value;

import bec.util.Scode;

public class SizeValue extends Value {
	boolean isNOSIZE;
	int tag;
	
	public SizeValue(boolean isNOSIZE) {
		this.isNOSIZE = isNOSIZE;
		parse();
	}

	/**
	 * 	size_value
	 * 		::= c-size type
	 * 		::= NOSIZE
	 */
	public void parse() {
		if(! isNOSIZE) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isNOSIZE) return "NOSIZE";
		return("C-OADDR " + Scode.edTag(tag));
	}
	

}
