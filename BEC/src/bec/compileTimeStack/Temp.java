package bec.compileTimeStack;

import bec.segment.MemAddr;
import bec.util.Global;
import bec.util.Scode;
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
