package bec.compileTimeStack;

import bec.segment.MemAddr;
import bec.util.Global;
import bec.util.Scode;
import bec.virtualMachine.SVM_POP;

public class Temp extends StackItem {

	// Value is pushed on RT-stack
	public Temp(int type) { //, int repdist) {
		this.type = type;
//		this.repdist = repdist;
	}
	
	@Override
    public void storeInto(MemAddr addr) {
		int size = DataType.typeSize(type);
		Global.PSEG.emit(new SVM_POP(addr, size), ""+Scode.edTag(type));
//	    Global.PSEG.dump();
    }
	
	public String toString() {
		return "Temp " + Scode.edTag(type);
	}
}
