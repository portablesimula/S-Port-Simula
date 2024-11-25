package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_NEG;

public class NEG extends Instruction {
	
	/**
	 * arithmetic_instruction ::= neg
	 * 
	 * force TOS value; check TOS type(INT,REAL,LREAL);
	 * value(TOS) := - value(TOS);
	 * 
	 * TOS is replaced by a description of the TOS value with its sign inverted.
	 */
	public NEG() {
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
//		Global.PSEG.dump();
		CTStack.checkTosArith();
		Type at = CTStack.TOS.type;
		Global.PSEG.emit(new SVM_NEG(at), "");
		CTStack.pop();
	    CTStack.pushTemp(at, "NEG: ");
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "NEG";
	}
	

}
