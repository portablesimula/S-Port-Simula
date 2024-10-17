package bec.syntaxClass.instruction;

import bec.util.Relation;
import bec.util.Scode;

public class BJUMPIF extends Instruction {
	Relation relation;
	int destination;
	
	public BJUMPIF() {
		parse();
	}

	/**
	 * backward_jump ::= bjumpif relation destination:index
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
		return "BJUMPIF " + relation + " " + destination;
	}
	

}
