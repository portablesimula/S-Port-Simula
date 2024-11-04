package bec.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.virtualMachine.SVM_GOTO;

public class BDEST extends Instruction {
	int destination;
	
	public BDEST() {
		parse();
	}

	/**
	 * backward_destination ::= bdest destination:newindex
	 */
	public void parse() {
		destination = Scode.inByte();
	}

	@Override
	public void doCode() {
		CTStack.dumpStack();
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
