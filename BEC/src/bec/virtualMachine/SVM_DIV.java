package bec.virtualMachine;

import bec.util.Scode;

/**
 * Remove to items on the Runtime-Stack and push the value (SOS * TOS)
 */
public class SVM_DIV extends SVM_Instruction {
	int type;

	public SVM_DIV(int type) {
		this.type = type;
	}
	
	@Override	
	public String toString() {
		return "MULT     " + Scode.edTag(type);
	}

}
