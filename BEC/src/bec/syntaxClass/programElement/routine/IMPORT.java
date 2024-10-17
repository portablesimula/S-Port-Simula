package bec.syntaxClass.programElement.routine;

import bec.util.QuantityDescriptor;
import bec.util.Scode;

public class IMPORT {
	int tag;
	QuantityDescriptor quant;

	/**
	 * 	import_definition
	 * 		::= import parm:newtag quantity_descriptor
	 */
	public IMPORT() {
		tag = Scode.inTag();
		quant = new QuantityDescriptor();
//		System.out.println("NEW IMPORT: " + this);
	}
	
	public String toString() {
		return "IMPORT " + Scode.edTag(tag) + " " + quant;
	}
}
