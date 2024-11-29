package bec.descriptor;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_SWITCH;

/**
 * forward_jump ::= switch switch:newtag size:number
 */
public class SwitchDescr extends Descriptor {
	int size;
	public MemAddr[] DESTAB;
	
	/**
	 * forward_jump ::= switch switch:newtag size:number
	 */
	private SwitchDescr(int kind, Tag tag) {
		super(kind, tag);
		size = Scode.inNumber();
//		if(size >= MxpSdest) Util.ERROR("Too large Case-Statement");
		DESTAB = new MemAddr[size];
		CTStack.checkTosInt();
//      if TOS.type < T_WRD2 then GQconvert(T_WRD2) endif;
//      a:=sw.swtab;
		int qEBX = 1; // MÅ RETTES
		CTStack.getTosAdjustedIn86(qEBX);
		CTStack.pop();
    	Global.PSEG.emit(new SVM_SWITCH(DESTAB), "");
//    	Global.PSEG.dump("SWITCH: ");
	}
	
	public static SwitchDescr ofScode() {
		Tag tag = Tag.ofScode();
		SwitchDescr sw = new SwitchDescr(Kind.K_SwitchDescr, tag);
		return sw;
	}
	
	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "SWITCH " + tag + " " + size;
	}
	

}
