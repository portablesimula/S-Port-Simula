package bec.virtualMachine;

// POP RT-Stack'TOS --> Register 
// The value on the top of the operand stack is popped off and stored in reg.
public class SVM_POPtoREG extends SVM_Instruction {
	int reg;
	
	public SVM_POPtoREG(int reg) {
		this.reg = reg;
	}
	
	public String toString() {
		return "POPtoREG R" + reg;
	}
}
