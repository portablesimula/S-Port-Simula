package bec.syntaxClass.value;

import bec.util.Scode;

public class RealValue extends Value {
	String value;
	
	public RealValue() {
		parse();
	}

	/**
	 * real_value ::= c-real real_literal:string
	 */
	public void parse() {
		value = Scode.inString();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "C-REAL " + value;
	}
	

}
