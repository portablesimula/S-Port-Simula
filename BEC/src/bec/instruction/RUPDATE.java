package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Util;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_POP2MEM;

public class RUPDATE extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 * 
	 * rupdate (dyadic)
	 * 
	 * check TOS ref;
	 * force SOS value; check types identical;
	 * pop;
	 * 
	 * This instruction (“reverse update”) works almost like update with the sole exception that the
	 * roles of TOS and SOS are interchanged, i.e. the value transfer is from SOS to TOS.	 */
	public RUPDATE() {
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
		MemAddr adr = Util.getTosDstAdr();
		StackItem tos = CTStack.pop();
		CTStack.dumpStack("RUPDATE: ");
		Global.PSEG.emit(new SVM_POP2MEM(adr, tos.size), ""+this); // Store into adr
//		Global.PSEG.dump();
	}
	
	public String toString() {
		return "RUPDATE";
	}
	

}
