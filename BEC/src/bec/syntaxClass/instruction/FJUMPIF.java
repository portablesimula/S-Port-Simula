package bec.syntaxClass.instruction;

import bec.util.Relation;
import bec.util.Scode;

public class FJUMPIF extends Instruction {
	Relation relation;
	int destination;
	
	public FJUMPIF() {
		parse();
	}

	/**
	 * forward_jump ::= fjumpif relation destination:newindex
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		relation = new Relation();
		destination = Scode.inByte();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "FJUMPIF " + relation + " " + destination;
	}
	

}
