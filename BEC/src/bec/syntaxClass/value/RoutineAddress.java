package bec.syntaxClass.value;

import bec.util.Scode;

public class RoutineAddress extends Value {
	boolean isNOBODY;
	int tag;
	
	public RoutineAddress(boolean isNOBODY) {
		this.isNOBODY = isNOBODY;
		parse();
	}

	/**
	 * 	routine_address
	 * 		::= c-raddr body:tag
	 * 		::= nobody
	 */
	public void parse() {
		if(! isNOBODY) tag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		if(isNOBODY) return "NOBODY";
		return("C-RADDR " + Scode.edTag(tag));
	}
	

}
