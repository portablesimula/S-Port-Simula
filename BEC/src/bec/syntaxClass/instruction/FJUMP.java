package bec.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.virtualMachine.SVM_GOTO;

public class FJUMP extends Instruction {
	int destination;
	
	public FJUMP() {
		parse();
	}

	/**
	 * forward_jump ::= fjump destination:newindex
	 */
	public void parse() {
		destination = Scode.inByte();
//		printTree(2);
	}

	@Override
	public void doCode() {
		CTStack.dumpStack();
		Global.DESTAB[destination] = Global.PSEG.nextAddress();
		Global.PSEG.emit(new SVM_GOTO(null), ""+this);
		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "FJUMP " + destination;
	}
	

}
