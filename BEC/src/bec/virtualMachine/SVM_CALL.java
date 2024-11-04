package bec.virtualMachine;

import bec.syntaxClass.programElement.routine.ROUTINE;

public class SVM_CALL extends SVM_Instruction {
	ROUTINE rut;

	public SVM_CALL(ROUTINE rut) {
		this.rut = rut;
	}
	
	@Override	
	public String toString() {
		return "CALL  " + rut;
	}

}
