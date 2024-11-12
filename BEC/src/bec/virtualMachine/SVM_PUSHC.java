package bec.virtualMachine;

import PREV.syntaxClass.value.PREV_Value;

//The value is pushed onto the operand stack.
public class SVM_PUSHC extends SVM_Instruction {
	PREV_Value value;
	
	public SVM_PUSHC(PREV_Value value) {
		this.value = value;
	}
	
	public String toString() {
		return "PUSHC    " + value;
	}
}
