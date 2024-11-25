package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_GOTO;
import bec.virtualMachine.SVM_NOOP;

public class FDEST extends Instruction {
	int destination;
	
	public FDEST() {
		parse();
	}

	/**
	 * forward_destination ::= fdest destination:index
	 * 
	 * check stack empty;
	 * 
	 * The destination must have been defined by a fjump or fjumpif instruction, otherwise: error.
	 * The current program point becomes the destination of the jump-instruction and the destination becomes
	 * undefined.
	 */
	public void parse() {
		destination = Scode.inByte();
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}

	@Override
	public void doCode() {
		CTStack.checkStackEmpty();

//		CTStack.dumpStack();
		MemAddr addr = Global.DESTAB[destination];
		Global.DESTAB[destination] = null;
		SVM_GOTO instr = (SVM_GOTO) Global.PSEG.instructions.get(addr.ofst);
		instr.destination = Global.PSEG.nextAddress();
      	Global.PSEG.emit(new SVM_NOOP(), "FDEST " + destination);
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}
	
	public String toString() {
		return "FDEST " + destination;
	}
	

}
