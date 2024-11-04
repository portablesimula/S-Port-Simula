package bec.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.segment.MemAddr;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_GOTO;

public class FDEST extends Instruction {
	int destination;
	
	public FDEST() {
		parse();
	}

	/**
	 * forward_destination ::= fdest destination:index
	 */
	public void parse() {
		destination = Scode.inByte();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}

	@Override
	public void doCode() {
		CTStack.dumpStack();
		MemAddr addr = Global.DESTAB[destination];
		SVM_GOTO instr = (SVM_GOTO) Global.PSEG.instructions.get(addr.ofst);
		instr.destination = Global.PSEG.nextAddress();
		Global.PSEG.dump();
//		Util.IERR(""+this);
	}
	
	public String toString() {
		return "FDEST " + destination;
	}
	

}
