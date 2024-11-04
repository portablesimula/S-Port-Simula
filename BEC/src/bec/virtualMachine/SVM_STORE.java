package bec.virtualMachine;

import bec.segment.MemAddr;
import bec.syntaxClass.value.Value;

public class SVM_STORE extends SVM_Instruction {
	Value value;
	MemAddr addr;
	
	public SVM_STORE(Value value, MemAddr addr) {
		this.value = value;
		this.addr = addr;
	}
	
	public String toString() {
		return "STORE    " + value + ", "+ addr;
	}
}
