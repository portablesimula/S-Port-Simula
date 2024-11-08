package bec.parse;

import bec.descriptor.ROUTINE;
import bec.descriptor.RecordDescr;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_LINE;
import bec.virtualMachine.SVM_NOT_IMPL;

public class MONITOR {

	public static void parse() {
		System.out.println("Parse.MONITOR");
//	begin infix(WORD) tag,entx; infix(MemAddr) Ltab;
//	      range(0:MaxByte) n; infix(MemAddr) opr;
//	%-S   warnDEBUG3:= DEBMOD > 2;  -- TEMP FIX
//		IfDepth=0;
		Scode.inputInstr();
		if(Scode.curinstr == Scode.S_PROGRAM) {
	  		System.out.println("Parse.MONITOR: S_PROGRAM");
	  		Global.progIdent = Scode.inString();
			Scode.inputInstr();
			if(Scode.curinstr == Scode.S_GLOBAL) {
				interfaceModule();
				Scode.inputInstr();
			}
			while(Scode.curinstr == Scode.S_MODULE) {
				Util.IERR("NOT IMPL");
//				ModuleDefinition;
				Scode.inputInstr();
			}
			if(Scode.curinstr == Scode.S_MAIN) {
				//  M a i n   P r o g r a m  ---
//	                if PROGID.val=0 then PROGID:=DefSymb("MAIN") endif;
//	                BEGASM(CSEGNAM,DSEGNAM); ed(sysedit,"SIM_");
//	                EdSymb(sysedit,PROGID); entx:=DefPubl(pickup(sysedit));
//	                MainEntry:=NewFixAdr(CSEGID,entx);
//	                DefLABEL(qBPROC,MainEntry.fix.val,entx.val);
				
				while(Scode.nextByte() == Scode.S_LOCAL) {
					Scode.inputInstr(); 
					Util.IERR("NOT IMPL");
//					Minut.inGlobal();
				}
//	                if LtabEntry.kind <> 0
//	                then Ltab.kind:=segadr; Ltab.rela.val:=0;
//	                     Ltab.segmid:=LSEGID;
//	                     opr.kind:=extadr; opr.rela.val:=0;
//	                     opr.smbx:=DefExtr("G@PRGINF",DGROUP);
//	%-E                  Ltab.sbireg:=0;       opr.sbireg:=oSS;
//	%+E                  Ltab.sibreg:=NoIBREG; opr.sibreg:=NoIBREG;
//	%-E                  Qf2b(qLOADC,0,qAX,cOBJ,F_OFFSET,Ltab);
//	%-E                  Qf3(qSTORE,0,qAX,cOBJ,opr);
//	%-E                  opr.rela.val:=opr.rela.val+2;
//	%-E                  Qf2b(qLOADC,0,qAX,cOBJ,F_BASE,Ltab);
//	%-E                  Qf3(qSTORE,0,qAX,cOBJ,opr);
//	%+E                  Qf2b(qLOADC,0,qEAX,cOBJ,0,Ltab);
//	%+E                  Qf3(qSTORE,0,qEAX,cOBJ,opr);
//	                endif;

				Util.IERR("NOT IMPL");
//				programElements();

//	                Qf2(qRET,0,0,0,0);
//	                DefLABEL(qEPROC,MainEntry.fix.val,entx.val);
//	                peepExhaust(true); ENDASM;
			}
			if(Scode.curinstr != Scode.S_ENDPROGRAM)
				Util.IERR("Illegal termination of program");
		} else Util.IERR("Illegal S-Program");
//	%+D   --- Release Display ---
//	%+D   tag:=GetLastTag; n:=tag.HI;
//	%+D   repeat DELETE(DISPL(n)); DISPL(n):=none
//	%+D   while n<>0 do n:=n-1 endrepeat;

	}
	
	
//	%title ***   I n t e r f a c e    M o d u l e   ***

	private static void interfaceModule() {
//	%+S begin range(0:MaxWord) nXtag; infix(WORD) itag,xtag,wrd;
//	%+S       range(0:MaxByte) b1,b2;
		Scode.inputInstr();
		if(Scode.curinstr != Scode.S_MODULE) Util.IERR("Missing - MODULE");
//	%+S    modident:=inMsymb; modcheck:=inSymb;
		Global.modident = Scode.inString();
		Global.modcheck = Scode.inString();
		Global.moduleID = Global.modident;
		String sourceID = Global.getSourceID();
		Global.CSEG = new DataSegment("CSEG_" + sourceID, Segment.SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + sourceID, Segment.SEG_DATA);
		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Segment.SEG_CODE);
		if(Global.PROGID == null) Global.PROGID = Global.modident;
//	%+S    BEGASM(CSEGNAM,DSEGNAM); nXtag:=0;
		LOOP: while(true) {
			Scode.inputInstr();
			switch(Scode.curinstr) {
			case Scode.S_GLOBAL:
//				inGlobal
				Util.IERR("NOT IMPL");
				break;
			case Scode.S_CONSTSPEC:
//				inConstant(false)
				Util.IERR("NOT IMPL");
				break;
			case Scode.S_CONST:
//				inConstant(true)
				Util.IERR("NOT IMPL");
				break;
			case Scode.S_RECORD:
//				RecordDescr.InRecord();
				new RecordDescr();
				break;
           case Scode.S_PROFILE:
//        	    InProfile(P_VISIBLE)
				Util.IERR("NOT IMPL");
				break;
           case Scode.S_ROUTINE:
        	   ROUTINE.inRoutine();
				break;
           case Scode.S_INFO:
//        	    Ed(errmsg,InString);
//        	    WARNING("Unknown info: ");
				Util.IERR("NOT IMPL");
				break;
           case Scode.S_LINE:
        	    setLine(0);
				break;
           case Scode.S_DECL:
//        	    CheckStackEmpty;
//        	    SetLine(qDCL)
				Util.IERR("NOT IMPL");
				break;
           case Scode.S_STMT:
//        	    CheckStackEmpty;
//        	    SetLine(qSTM)
				Util.IERR("NOT IMPL");
				break;
           case Scode.S_SETSWITCH:
//        	    SetSwitch
				Util.IERR("NOT IMPL");
				break;
           case Scode.S_INSERT, Scode.S_SYSINSERT:
//        	    Combine; TERMINATE
				Util.IERR("NOT IMPL");
				break;
           default: break LOOP;
			}
		}
//
//	%+S E:  repeat while CurInstr=S_TAG
//	%+S     do InTag(%itag%); xtag:=InputNumber;
//	%+SD       if xtag.HI >= MxpXtag then CAPERR(CapTags) endif;
//	%+S        if   TAGTAB(xtag.HI)=none
//	%+S        then TAGTAB(xtag.HI):=
//	%+S             NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	%+S        TAGTAB(xtag.HI).elt(xtag.LO):=itag;
//	%+S        InputInstr;
//	%+S        if xtag.val>nXtag then nXtag:=xtag.val endif;
//	%+S     endrepeat;
//	%+S     OutputModule(nXtag);
//
//	%+S     if CurInstr <> S_BODY then
//	%+S     IERR("Illegal termination of module head") endif;
//
//	%+SC    repeat InputInstr while CurInstr=S_INIT
//	%+SC    do IERR("InterfaceModule: Init values is not supported");
//	%+SC       InTag(%wrd%); intype; SkipRepValue;
//	%+SC    endrepeat;
//
//	%+S     if CurInstr <> S_ENDMODULE then
//	%+S     IERR("Improper termination of module") endif;
//
//	---     peepExhaust(); --- nothing to work on
//	%+S     ENDASM;
		Util.IERR("NOT IMPL");
	}

	public static void setLine(int type) {
		Global.curline = Scode.inNumber();
		Global.PSEG.emit(new SVM_LINE(type, Global.curline), "MONITOR.setLine: ");
	}

}
