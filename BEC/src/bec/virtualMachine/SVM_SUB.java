package bec.virtualMachine;

import bec.util.Scode;

/**
 * SOS and TOS are replaced by a description of the value of the application of the operator. The
 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic.
 * 
 * Remove to items on the Runtime-Stack and push the value SOS - TOS
 */
public class SVM_SUB extends SVM_Instruction {
	int type;

	public SVM_SUB(int type) {
		this.type = type;
	}
	
	@Override	
	public String toString() {
		return "SUB      " + Scode.edTag(type);
	}

}