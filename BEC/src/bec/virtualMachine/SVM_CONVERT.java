package bec.virtualMachine;

import bec.util.Scode;

public class SVM_CONVERT extends SVM_Instruction {
	int toType;
	
	public SVM_CONVERT(int toType) {
		this.toType = toType;
	}
	
	public String toString() {
		return "CONVERT  " + Scode.edTag(toType);
	}
}
