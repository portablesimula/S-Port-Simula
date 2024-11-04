package bec.virtualMachine;

// POP RT-Stack'TOS --> Register 
public class SVM_POPR extends SVM_Instruction {
	int reg;
	
	public SVM_POPR(int reg) {
		this.reg = reg;
	}
	
	public String toString() {
		return "POPR     R" + reg;
	}
}
