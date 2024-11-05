package bec.virtualMachine;

import bec.util.Scode;

/**
 * 
 * Remove the top item on the Runtime-Stack and push the negative value
 */
public class SVM_NEG extends SVM_Instruction {
	int type;

	public SVM_NEG(int type) {
		this.type = type;
	}
	
	@Override	
	public String toString() {
		return "NEG      " + Scode.edTag(type);
	}

}
