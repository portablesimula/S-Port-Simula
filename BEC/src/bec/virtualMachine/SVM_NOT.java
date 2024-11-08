package bec.virtualMachine;

import bec.util.Scode;

/**
 * 
 * Remove the top item on the Runtime-Stack and push the NOT value
 */
public class SVM_NOT extends SVM_Instruction {
	int type;

	public SVM_NOT(int type) {
		this.type = type;
	}
	
	@Override	
	public String toString() {
		return "NOT      " + Scode.edTag(type);
	}

}
