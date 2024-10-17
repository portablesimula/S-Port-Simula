package bec.syntaxClass.value;

import bec.util.Scode;

public class LongRealValue extends Value {
	String value;
	
	public LongRealValue() {
		parse();
	}

	/**
	 * longreal_value ::= c-lreal real_literal:string
	 */
	public void parse() {
		value = Scode.inString();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "C-LREAL " + value;
	}
	

}
