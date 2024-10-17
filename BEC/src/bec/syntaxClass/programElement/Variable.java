package bec.syntaxClass.programElement;

import bec.util.QuantityDescriptor;
import bec.util.Scode;

public class Variable extends ProgramElement {
	int instr; // S_GLOBAL or S_LOCAL
	int tag;
	QuantityDescriptor quant;
	String system;

	/**
	 * 	global_definition ::= global internal:newtag quantity_descriptor
	 * 
	 * 	local_quantity ::= local var:newtag quantity_descriptor
	 */
	public Variable(int instr) {
		tag = Scode.inTag();
		quant = new QuantityDescriptor();
		if(Scode.accept(Scode.S_SYSTEM)) {
			system = Scode.inString();
		}
//		System.out.println("NEW Variable: Line "+this.lineNumber+": "+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		String s = Scode.edInstr(instr) + " " + Scode.edTag(tag) + " " + quant;
		if(system != null) s += " " + "SYSTEM " + system;
		return s;
	}
}
