package bec.descriptor;

import bec.compileTimeStack.CTStack;
import bec.parse.Instruction;
import bec.segment.MemAddr;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.syntaxClass.instruction.PREV_Instruction;
import bec.syntaxClass.programElement.Variable;
import bec.syntaxClass.programElement.routine.PREV_PROFILE;
import bec.util.Global;
import bec.util.ResolvedType;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_RETURN;

public class ROUTINE {

	public static void specRut(boolean inHead) {
//	%+S begin infix(WORD) tag,ptag; infix(WORD) smb;
//	%+S       infix(string) s; ref(IntDescr) v;
//	%+S       InTag(%tag%); InXtag(%ptag%);
		int tag = Scode.inTag();
		int ptag = Scode.inTag();
//	%+S       if inHead
//	%+S       then if TagIdent.val <> 0
//	%+S            then EdSymb(sysedit,PRFXID); -- edchar(sysedit,'@');
//	%+S                 edsymb(sysedit,TagIdent); s:=pickup(sysedit);
//	%+S                 if s.nchr>16 then smb:=NewPubl
//	%+S                 else smb:=DefPubl(s) endif;
//	%+S            else smb:=NewPubl endif;
//	%+S       else smb.val:=0 endif;
//	%+S       v:=NEWOBJ(K_IntRoutine,size(IntDescr));
//	%+S       v.adr:=NewFixAdr(CSEGID,smb);
		IntDescr v = new IntDescr();
		v.kind = Kind.K_IntRoutine;
		Global.intoDisplay(v,tag);
	}

//	%title ***   R o u t i n e    B o d y   ***
//
//	--- %+S removed to be able to handle "call-back" routines -- pje
//
	public static void inRoutine() {
//	Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	    begin ref(ProfileDescr) p; ref(IntDescr) r;
//	      range(0:MaxWord) nlocbyte; ref(LocDescr) locvar;
//	      infix(WORD) smbx,tag,prftag; range(0:MaxWord) xrela;
//	      range(0:MaxType) xt,type; infix(MemAddr) a; range(0:1) visflag;
//
//	      InTag(%tag%); InTag(%prftag%);

		int tag = Scode.inTag();
		int prftag = Scode.inTag();
		
		String id = Global.moduleID + '_' + Scode.TAGTABLE[prftag];
		ProgramSegment PSEG = new ProgramSegment("PSEG_" + id, Segment.SEG_CODE);
		ProgramSegment prevPSEG = Global.PSEG; Global.PSEG = PSEG;
		IntDescr rut = (IntDescr) Global.DISPL.get(tag);
		if(rut == null) rut = new IntDescr();
		rut.adr = new MemAddr(PSEG,0);

		ProfileDescr prf = (ProfileDescr) Global.DISPL.get(tag);
		boolean visflag = prf.kind == Kind.P_ROUTINE;
		rut.type = prf.type;
		int nlocbyte = 0;
		Global.insideRoutine = true;

		Scode.inputInstr();
		while(Scode.curinstr == Scode.S_LOCAL) {
			nlocbyte = InLocal(nlocbyte);
			Scode.inputInstr();
		}
	
//	%+S   repeat while Instruction do InputInstr endrepeat;
		while(Instruction.instruction()) { Scode.inputInstr(); }
	
		if(Scode.curinstr != Scode.S_ENDROUTINE) Util.IERR("Missing - endroutine");
		CTStack.checkStackEmpty();
	
//	%+E   Qf2(qLEAVE,0,0,0,nlocbyte); Qf2(qRET,0,0,0,p.nparbyte)
//	      DefLABEL(qEPROC,r.adr.fix.val,smbx.val);
//	      peepExhaust(true); InsideRoutine:=false;
		Global.PSEG.emit(new SVM_RETURN(prf), "");
		CTStack.checkStackEmpty();

//		prf.DSEG.dump("ROUTINE.doCode: ");
		PSEG.dump("ROUTINE.doCode: ");
		Global.PSEG = prevPSEG;
//		Util.IERR("");
	}

	
	/**
	 * 	local_quantity ::= local var:newtag quantity_descriptor
	 * 
	 * quantity_descriptor ::= resolved_type < Rep count:number >?
	 * 
	 * resolved_type
	 * 		::= resolved_structure | simple_type
	 * 		::= INT range lower:number upper:number
	 * 		::= SINT
	 */
	private static int InLocal(int nlocbyte) {
		//--- Input Local Variable in Routine Body ---
		int tag = Scode.inTag();
		
//		TypeLength = 0;
//		type = intype;
//		nbyte = TypeLength;
		ResolvedType resolvedType = new ResolvedType();
		int type = resolvedType.tag;
		int nbyte = 0; // ?????
		Util.IERR("SJEKK DETTE");

		int count = 1;
		if(Scode.nextByte() == Scode.S_REP) {
			Scode.inputInstr(); count = Scode.inNumber();
			if(count == 0) {
				Util.IERR("Illegal 'REP 0'"); count = 1;
			}
		}
//	%+S      if type < T_Max then nbyte:=TTAB(type).nbyte endif;
		
//	%+S      locvar:=NEWOBJ(K_LocalVar,size(LocDescr));
//	%+S      locvar.type:=type; IntoDisplay(locvar,tag);
		LocDescr locvar = new LocDescr();
		locvar.type = type; Global.intoDisplay(locvar,tag);
		
		nlocbyte = nlocbyte + (nbyte*count);
		locvar.rela = nlocbyte;
		return nlocbyte;
	}


}
