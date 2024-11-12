package bec.compileTimeStack;

import bec.util.Global;
import bec.util.Scode;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_POPtoMEM;

public class Temp extends StackItem {

	// Value is pushed on RT-stack
	public Temp(int type) { //, int repdist) {
		this.type = type;
//		this.repdist = repdist;
	}
	
	public String toString() {
		return "Temp " + Scode.edTag(type);
	}
}
