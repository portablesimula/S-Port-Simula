package bec.virtualMachine;

import bec.util.Scode;

/**
 * Remove to items on the Runtime-Stack and push the value (SOS and TOS)
 */
public class SVM_ADD extends SVM_Instruction {
	int type;

	public SVM_ADD(int type) {
		this.type = type;
	}
	
	@Override	
	public String toString() {
		return "AND      " + Scode.edTag(type);
	}

}
