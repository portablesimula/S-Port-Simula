package bec.syntaxClass.value;

import bec.util.Scode;

public class CharacterValue extends Value {
	int value;
	
	public CharacterValue() {
		parse();
	}

	/**
	 * character_value ::= c-char byte
	 */
	public void parse() {
		value = Scode.inByte();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "C-CHAR " + value;
	}
	

}
