package bec.virtualMachine;

import bec.segment.MemAddr;

public class SVM_PUSH extends SVM_Instruction {
	MemAddr addr;
	int count;
	
	public SVM_PUSH(MemAddr addr, int count) {
		this.addr = addr;
		this.count = count;
	}
	
	public String toString() {
		return "PUSH     " + addr + ", " + count;
	}
}
