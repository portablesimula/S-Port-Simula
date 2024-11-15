package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.virtualMachine.SVM_DIV;

public class DIV extends Instruction {
	
	/**
	 * arithmetic_instruction ::= div
	 * 
	 * add, sub, mult, div (dyadic)
	 * 
	 * force TOS value; check TOS type(INT,REAL,LREAL);
	 * force SOS value; check SOS type(INT,REAL,LREAL);
	 * check types equal;
	 * 
	 * pop; pop;
	 * push( VAL, type, "value(SOS) op value(TOS)" );
	 * 
	 * SOS and TOS are replaced by a description of the value of the application of the operator. The
	 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
	 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic. 
	 */
	public DIV() {
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
//		Global.PSEG.dump();
		CTStack.checkTosArith(); CTStack.checkSosArith(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
		StackItem tos = CTStack.TOS;
	    int at = CTStack.arithType(tos.type, tos.suc.type);
		Global.PSEG.emit(new SVM_DIV(at), "");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTemp(at);
//		CTStack.dumpStack();
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "MULT";
	}
	

}
