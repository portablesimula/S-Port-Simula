package bec.descriptor;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_SWITCH;

//Record SwitchDescr:Descriptor;
//begin range(0:MaxSdest) ndest;   -- No. of Sdest in this switch
//      range(0:MaxSdest) nleft;   -- No. of Sdest left to be defined
//      infix(MemAddr) swtab;      -- Start of Sdest-Table
//      ref(AddrBlock) DESTAB(MxpSdest); -- All SDEST addresses
//end;
public class SwitchDescr extends Descriptor {

	int tag;
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
//		doCode();
	}
	
	public static SwitchDescr ofScode() {
		Tag tag = Tag.inTag();
		SwitchDescr sw = new SwitchDescr(Kind.K_SwitchDescr, tag);
		return sw;
	}

	@Override
	public void doCode() {
//        InTag(%tag%);
//%+D        ndest:=InputNumber;
//        sw:=NEWOBJ(K_SwitchDescr,size(SwitchDescr));
//        sw.ndest:=ndest.val; sw.nleft:=ndest.val;
//        i.val:=0; sw.swtab:=NewFixAdr(DSEGID,i); IntoDisplay(sw,tag);
		CTStack.checkTosInt();
//        if TOS.type < T_WRD2 then GQconvert(T_WRD2) endif;
//        a:=sw.swtab;
		int qEBX = 1; // MÅ RETTES
		CTStack.getTosAdjustedIn86(qEBX);
		CTStack.pop();
//
//%+D        if IDXCHK <> 0 then --- pje 22.10.90
//%+D           PreMindMask:=wOR(PreMindMask,uBX);
//%+DE          Qf2(qDYADC,qCMP,qEBX,cVAL,ndest.val);
//%+DE          PreReadMask:=uBX;
//%+D           LL:=ForwJMP(q_WLT);
//%+D           Qf5(qCALL,0,0,0,X_ECASE); -- OutOfRange ==> ERROR
//%+DE          Qf2(qLOADC,0,qEBX,cVAL,0);
//%+D           PreReadMask:=uBX;
//%+DE          PreMindMask:=wOR(PreMindMask,uBX);
//%+D           DefFDEST(LL);
//%+D        endif; --- pje 22.10.90
//
//%+E        a.sibreg:=bOR(bOR(128,bEBX),iEBX); -- swtab+[4*EBX] 
//        Qf3(qJMPM,0,0,0,a);
      	Global.PSEG.emit(new SVM_SWITCH(DESTAB), "");
      	Global.PSEG.dump("SWITCH: ");
	}
	
	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "SWITCH " + Scode.edTag(tag) + " " + size;
	}
	

}
