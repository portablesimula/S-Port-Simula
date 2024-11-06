package bec.virtualMachine;

import bec.syntaxClass.value.Value;

//The value is pushed onto the operand stack.
public class SVM_PUSHC extends SVM_Instruction {
	Value value;
	
	public SVM_PUSHC(Value value) {
		this.value = value;
	}
	
	public String toString() {
		return "PUSHC    " + value;
	}
}
