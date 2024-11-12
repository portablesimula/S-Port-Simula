package bec.virtualMachine;

import PREV.syntaxClass.value.PREV_Value;
import bec.value.MemAddr;

//The value is stored at addr...
public class SVM_STOREC extends SVM_Instruction {
	PREV_Value value;
	MemAddr addr;
	
	public SVM_STOREC(PREV_Value value, MemAddr addr) {
		this.value = value;
		this.addr = addr;
	}
	
	public String toString() {
		return "STOREC   " + value + ", "+ addr;
	}
}
