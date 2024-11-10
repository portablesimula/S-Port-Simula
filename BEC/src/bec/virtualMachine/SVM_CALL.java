package bec.virtualMachine;

import PREV.syntaxClass.programElement.routine.PREV_ROUTINE;

public class SVM_CALL extends SVM_Instruction {
	PREV_ROUTINE rut;

	public SVM_CALL(PREV_ROUTINE rut) {
		this.rut = rut;
	}
	
	@Override	
	public String toString() {
		return "CALL  " + rut;
	}

}
