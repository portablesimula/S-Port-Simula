package bec.syntaxClass.instruction;

import bec.util.Relation;
import bec.util.Scode;

public class COMPARE extends Instruction {
	Relation relation;
	
	public COMPARE() {
		parse();
	}

	/**
	 * arithmetic_instruction ::= compare relation
	 */
	public void parse() {
		relation = new Relation();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "COMPARE " + relation;
	}
	

}
