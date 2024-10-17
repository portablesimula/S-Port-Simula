package bec.syntaxClass.value;

import java.util.Vector;

import bec.syntaxClass.instruction.Instruction;
import bec.util.QuantityDescriptor;
import bec.util.Scode;
import bec.util.Util;

public class CONST extends Instruction {
	boolean constDef;
	int tag;
	QuantityDescriptor quant;
	RepetitionValue value;
	
	public CONST(boolean constDef) {
		this.constDef = constDef;
		parse();
	}

//	%title ***   C o n s t   and   C o n s t s p e c   ***
	/**
	 *	constant_specification
	 *		::= constspec const:newtag quantity_descriptor
	 *
	 *	constant_definition
	 *		::= const const:spectag quantity_descriptor repetition_value
	 */
	public void parse() {
		tag = Scode.inTag();
		quant = new QuantityDescriptor();
		if(constDef) value = new RepetitionValue();
	}

	@Override
	public void printTree(final int indent) {
		if(constDef) {
			boolean done = false;
//			System.out.println("ZZZZZZ"+value.values.getClass().getSimpleName());
			if(value.values instanceof Vector<Value> vector) {
//				System.out.println("ZZZZZZ"+vector.getClass().getSimpleName());
				if(vector instanceof Vector<?> elts) {
//					System.out.println("ZZZZZZ"+elts.getClass().getSimpleName());
//					System.out.println("ZZZZZZ"+elts.getFirst().getClass().getSimpleName());
					boolean first = true;
					for(Object rVal:elts) {
						if(first) sLIST(indent, "CONST " + Scode.edTag(tag) + " " + quant);
						first = false;
						
//						if(rVal instanceof TextValue text) {
//							text.printTree(indent + 1);							
//						} else if(rVal instanceof RecordValue recval) {
//							recval.printTree(indent + 1);
//						} else Util.IERR("Hva så ? " + rVal.getClass().getSimpleName());
						((Value)rVal).printTree(indent + 1);							
						
					} done = true;
				}
			}
			if(! done) sLIST(indent + 1, toString());
		} else sLIST(indent + 1, toString());
	}
	
	public String toString() {
		if(constDef)
			 return "CONST " + Scode.edTag(tag) + " " + quant + " " + value;
		else return "CONSTSPEC " + Scode.edTag(tag) + " " + quant;
	}

}
