package bec.syntaxClass.value;

import bec.util.ResolvedType;
import bec.util.Scode;

public class AttributeValue extends Value {
	int tag;
	ResolvedType type;
	RepetitionValue value;
	
	public AttributeValue() {
		parse();
	}

	/**
	 * 	attribute_value
	 * 		::= attr attribute:tag type repetition_value
	 */
	public void parse() {
		tag = Scode.inTag();
		type = new ResolvedType();
		value = new RepetitionValue();
//		System.out.println("AttributeValue.parse: value=" + value);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "ATTR " + Scode.edTag(tag) + " "+ value;
	}
	

}
