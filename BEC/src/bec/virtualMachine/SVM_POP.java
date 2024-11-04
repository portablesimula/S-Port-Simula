package bec.virtualMachine;

import bec.segment.MemAddr;

// POP RT-Stack'TOS --> MemAddr
public class SVM_POP extends SVM_Instruction {
	MemAddr addr;
	int count;
	
	public SVM_POP(MemAddr addr, int count) {
		this.addr = addr;
		this.count = count;
	}
	
	public String toString() {
		return "POP      " + addr + ", "+ count;
	}
}
