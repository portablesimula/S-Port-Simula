package bec.descriptor;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.MemAddr;

//Record IntDescr:Descriptor;      -- K_Globalvar
//begin infix(MemAddr) adr;        -- K_IntLabel
//end;                             -- K_IntRoutine   Local Routine
public class IntDescr extends Descriptor {
	MemAddr adr;

	public IntDescr(int kind, Tag tag) {
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
//		Tag tag = Tag.inTag();
//		int ptag = Scode.inTag();
		Tag tag = Tag.inTag();
		Tag ptag = Tag.inTag();
		IntDescr rut = (IntDescr) Global.DISPL.get(tag.val);
		if(rut != null) Util.IERR("");
		rut = new IntDescr(Kind.K_IntRoutine,tag);
		rut.adr = null;
//		Util.IERR("TEST DETTE");
		return rut;
	}
	
	public static IntDescr ofLabelSpec() {
//		Tag tag = Tag.inTag();
		Tag tag = Tag.inTag();
		IntDescr lab = (IntDescr) Global.DISPL.get(tag.val);
		if(lab != null) Util.IERR("");
		lab = new IntDescr(Kind.K_IntLabel,tag);
		lab.adr = null;
		return lab;
	}
	
//	Routine DefLab;
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	begin infix(WORD) tag,smbx; ref(IntDescr) v; InTag(%tag%);
//	%+D   RST(R_DefLab);
//	      v:=if DISPL(tag.HI)=none then none else DISPL(tag.HI).elt(tag.LO);
//	      if v = none
//	      then v:=NEWOBJ(K_IntLabel,size(IntDescr)); smbx.val:=0;
//	           v.adr:=NewFixAdr(CSEGID,smbx); IntoDisplay(v,tag);
//	%+C   else v:=DISPL(tag.HI).elt(tag.LO);
//	%+C        if v.adr.kind <> fixadr then IERR("Parse.DefLAB-1") endif;
//	      endif;
//	      DefLABEL(0,v.adr.fix.val,0);
//	%+C   CheckStackEmpty;
//	end;
	public static IntDescr ofLabel(Tag tag) {
		IntDescr lab = (IntDescr) Global.DISPL.get(tag.val);
		if(lab == null) lab = new IntDescr(Kind.K_IntLabel,tag);
		lab.adr = Global.PSEG.nextAddress();
		CTStack.checkStackEmpty();
		return lab;
	}
	
	public String toString() {
		return "IntDescr " + Kind.edKind(kind) + " " + tag + " " + adr;
	}

}
