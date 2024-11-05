package bec.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class BDEST extends Instruction {
	int destination;
	
	public BDEST() {
		parse();
	}

	/**
	 * backward_destination ::= bdest destination:newindex
	 * 
	 * check stack empty;
	 * 
	 * The destination must be undefined, otherwise: error.
	 * The destination is defined to refer to the current program point.
	 */
	public void parse() {
		destination = Scode.inByte();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkStackEmpty();

		if(Global.DESTAB[destination] != null) Util.IERR("BJUMP dest. dest == null");

		Global.DESTAB[destination] = Global.PSEG.nextAddress();
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "BDEST " + destination;
	}
	

}
