package bec.virtualMachine;

import bec.value.MemAddr;

// POP RT-Stack'TOS --> MemAddr

//The count values on the top of the operand stack is popped off and stored at addr...
public class SVM_POPtoMEM extends SVM_Instruction {
	MemAddr addr;
	int count;
	
	public SVM_POPtoMEM(MemAddr addr, int count) {
		this.addr = addr;
		this.count = count;
	}
	
	public String toString() {
		return "POPtoMEM " + addr + ", "+ count;
	}
}
