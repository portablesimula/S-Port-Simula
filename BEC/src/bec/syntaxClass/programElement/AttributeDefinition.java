package bec.syntaxClass.programElement;

import bec.util.QuantityDescriptor;
import bec.util.Scode;

public class AttributeDefinition extends ProgramElement {
	int tag;
	QuantityDescriptor quant;
	
	public AttributeDefinition() {
		parse();
	}

	/**
	 *	attribute_definition ::= attr attr:newtag quantity_descriptor
	 */
	public void parse() {
		tag = Scode.inTag();
		quant = new QuantityDescriptor();
//		System.out.println("NEW AttributeDefinition: " + this);
	}

	public String toString() {
		return "ATTR " + Scode.edTag(tag) + " " + quant;
	}
	

}
