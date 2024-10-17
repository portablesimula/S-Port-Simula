package bec.syntaxClass.value;

import bec.util.Scode;

public class IntegerValue extends Value {
	String value;
	
	public IntegerValue() {
		parse();
	}

	/**
	 * integer_value   ::= c-int integer_literal:string
	 */
	public void parse() {
		value = Scode.inString();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "C-INT " + value;
	}
	

}
