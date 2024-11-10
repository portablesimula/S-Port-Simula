package bec.virtualMachine;

import PREV.syntaxClass.value.Value;
import bec.segment.MemAddr;

//The value is stored at addr...
public class SVM_STOREC extends SVM_Instruction {
	Value value;
	MemAddr addr;
	
	public SVM_STOREC(Value value, MemAddr addr) {
		this.value = value;
		this.addr = addr;
	}
	
	public String toString() {
		return "STOREC   " + value + ", "+ addr;
	}
}
