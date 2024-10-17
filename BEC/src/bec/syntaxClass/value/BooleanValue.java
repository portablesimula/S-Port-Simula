package bec.syntaxClass.value;

import bec.util.Scode;

public class BooleanValue extends Value {
	boolean value;
	
	/**
	 * boolean_value ::= true | false
	 */
	public BooleanValue(boolean value) {
		this.value = value;
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "" + value;
	}
	

}
