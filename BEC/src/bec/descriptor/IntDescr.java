package bec.descriptor;

import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.value.MemAddr;

//Record IntDescr:Descriptor;      -- K_Globalvar
//begin infix(MemAddr) adr;        -- K_IntLabel
//end;                             -- K_IntRoutine   Local Routine
public class IntDescr extends Descriptor {
	MemAddr adr;

	public IntDescr(int kind, int tag) {
		super(kind, tag);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * routine_specification
	 *		::= routinespec body:newtag profile:tag
	 * @return
	 */
	public static IntDescr ofRoutineSpec() {
//		%+S Visible Routine SpecRut; import Boolean inHead;
//		%+S begin infix(WORD) tag,ptag; infix(WORD) smb;
//		%+S       infix(string) s; ref(IntDescr) v;
//		%+S       InTag(%tag%); InXtag(%ptag%);
//		%+S       if inHead
//		%+S       then if TagIdent.val <> 0
//		%+S            then EdSymb(sysedit,PRFXID); -- edchar(sysedit,'@');
//		%+S                 edsymb(sysedit,TagIdent); s:=pickup(sysedit);
//		%+S                 if s.nchr>16 then smb:=NewPubl
//		%+S                 else smb:=DefPubl(s) endif;
//		%+S            else smb:=NewPubl endif;
//		%+S       else smb.val:=0 endif;
//		%+S       v:=NEWOBJ(K_IntRoutine,size(IntDescr));
//		%+S       v.adr:=NewFixAdr(CSEGID,smb); IntoDisplay(v,tag);
//		%+S end;
		int tag = Scode.inTag();
		int ptag = Scode.inTag();
		IntDescr lab = (IntDescr) Global.DISPL.get(tag);
		if(lab != null) Util.IERR("");
		lab = new IntDescr(Kind.K_IntRoutine,tag);
		lab.adr = null;
		Util.IERR("TEST DETTE");
		return lab;
	}
	
	public static IntDescr ofLabelSpec() {
		int tag = Scode.inTag();
		IntDescr lab = (IntDescr) Global.DISPL.get(tag);
		if(lab != null) Util.IERR("");
		lab = new IntDescr(Kind.K_IntLabel,tag);
		lab.adr = null;
		return lab;
	}
	
	public static IntDescr ofLabel(int tag) {
		IntDescr lab = (IntDescr) Global.DISPL.get(tag);
		if(lab == null) lab = new IntDescr(Kind.K_IntLabel,tag);
		lab.adr = Global.PSEG.nextAddress();
		return lab;
	}

}
