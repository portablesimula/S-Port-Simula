package bec.virtualMachine;

import bec.syntaxClass.programElement.routine.PROFILE;

public class SVM_RETURN extends SVM_Instruction {
	PROFILE prf;

	public SVM_RETURN(PROFILE prf) {
		this.prf = prf;
	}
	
	@Override	
	public String toString() {
		return "RETURN   " + prf;
	}

}
