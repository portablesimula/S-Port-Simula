package bec.virtualMachine;

import bec.value.MemAddr;

public class SVM_GOTO extends SVM_Instruction {
	public MemAddr destination;

	public SVM_GOTO(MemAddr destination) {
		this.destination = destination;
	}
	
	@Override	
	public String toString() {
		return "GOTO     " + destination;
	}

}
