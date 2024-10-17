package bec.syntaxClass.value;

import bec.util.Scode;

public class TextValue extends Value {
	String value;
	
	public TextValue() {
		parse();
	}

	/**
	 * text_value ::= text long_string
	 */
	public void parse() {
//		System.out.println("TextValue.parse: curinstr=" + Scode.edInstr(Scode.curinstr));
		value = Scode.inLongString();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "\"" + value + '"';
	}
	

}
