package bec.virtualMachine;

import bec.value.MemAddr;

// The count values at addr... is pushed onto the operand stack.
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
