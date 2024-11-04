package bec.compileTimeStack;

import bec.segment.MemAddr;
import bec.syntaxClass.value.Value;
import bec.util.Global;
import bec.util.Util;
import bec.virtualMachine.SVM_CALL;
import bec.virtualMachine.SVM_STORE;

//Record Coonst:Temp;               --- Value is also in 'itm'
//begin infix(ValueItem) itm;
//end;
//
//Record ValueItem; info "TYPE";
//begin variant range(0:MaxByte) byt(8); -- ?? variant range(0:MaxWord) byt(8);
//      variant range(0:MaxWord) wrd(4);
//      variant integer int(2);
//      variant real rev(2);
//      variant long real lrv;
//      variant infix(MemAddr) base;     -- size 6(-E) / 8(+E)
//              range(0:MaxWord) Ofst;
//end;

public class Coonst extends Temp {
	public Value value;

	public Coonst(Value value) {
		super(value.type);
		this.value = value;
		if(type == 0) Util.IERR("NEW Coonst: NO TYPE: "+value);
	}
	
	@Override
    public void storeInto(MemAddr addr) {
	      Global.PSEG.emit(new SVM_STORE(value, addr), ""+value);
//	      Global.PSEG.dump();
    }

	public String toString() {
		return ""+value;
	}
}