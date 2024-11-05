package bec.virtualMachine;

import bec.util.Scode;

/**
 * Remove to items on the Runtime-Stack and push the value (SOS / TOS)
 */
public class SVM_MULT extends SVM_Instruction {
	int type;

	public SVM_MULT(int type) {
		this.type = type;
	}
	
	@Override	
	public String toString() {
		return "DIV      " + Scode.edTag(type);
	}

}
