package bec.parse;

import java.io.IOException;

import bec.InsertStatement;
import bec.ModuleIO;
import bec.compileTimeStack.CTStack;
import bec.descriptor.CONST;
import bec.descriptor.IntDescr;
import bec.descriptor.Kind;
import bec.descriptor.ProfileDescr;
import bec.descriptor.ROUTINE;
import bec.descriptor.RecordDescr;
import bec.descriptor.Variable;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.Array;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_LINE;
import bec.virtualMachine.SVM_NOT_IMPL;

public class MONITOR {

	public static void parse() {
//		System.out.println("Parse.MONITOR");
//	begin infix(WORD) tag,entx; infix(MemAddr) Ltab;
//	      range(0:MaxByte) n; infix(MemAddr) opr;
//	%-S   warnDEBUG3:= DEBMOD > 2;  -- TEMP FIX
//		IfDepth=0;
		Scode.inputInstr();
		if(Scode.curinstr == Scode.S_PROGRAM) {
//	  		System.out.println("Parse.MONITOR: S_PROGRAM");
	  		Global.progIdent = Scode.inString();
			Scode.inputInstr();
			if(Scode.curinstr == Scode.S_GLOBAL) {
				interfaceModule();
				Scode.inputInstr();
			}
			while(Scode.curinstr == Scode.S_MODULE) {
				moduleDefinition();
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
		Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		if(Global.PROGID == null) Global.PROGID = Global.modident;
//	%+S    BEGASM(CSEGNAM,DSEGNAM); nXtag:=0;
		LOOP: while(true) {
			Scode.inputInstr();
			switch(Scode.curinstr) {
				case Scode.S_GLOBAL:	Variable.ofGlobal(Global.DSEG); break;
				case Scode.S_CONSTSPEC: CONST.inConstant(false); break;
				case Scode.S_CONST:		CONST.inConstant(true);	break;
				case Scode.S_RECORD:	RecordDescr.of(); break;
				case Scode.S_PROFILE:   ProfileDescr.inProfile(Kind.P_VISIBLE); break;
				case Scode.S_ROUTINE:	ROUTINE.inRoutine();	break;
				case Scode.S_INFO:
//        	    	Ed(errmsg,InString);
//        	    	WARNING("Unknown info: ");
					Util.IERR("NOT IMPL");
					break;
				case Scode.S_LINE:  	    setLine(0); break;
				case Scode.S_DECL:
//        		    CheckStackEmpty;
//        		    SetLine(qDCL)
					Util.IERR("NOT IMPL");
					break;
				case Scode.S_STMT:
//        	    	CheckStackEmpty;
//        	    	SetLine(qSTM)
					Util.IERR("NOT IMPL");
					break;
				case Scode.S_SETSWITCH:
//        	    	SetSwitch
					Util.IERR("NOT IMPL");
					break;
				case Scode.S_INSERT, Scode.S_SYSINSERT:
//        		    Combine; TERMINATE
					Util.IERR("NOT IMPL");
					break;
				default: break LOOP;
			}
		}
//
//	%+S E:  repeat while CurInstr=S_TAG
		Global.TAGTAB = new Array<Integer>();
		int nXtag = 0;
		while(Scode.curinstr == Scode.S_TAG) {
			int itag = Scode.inTag();
			int xtag = Scode.inNumber();
			Global.TAGTAB.set(xtag, itag);
			Scode.inputInstr();
			if(xtag > nXtag) nXtag = xtag;
		}
			
			
		try {
			ModuleIO.outputModule(nXtag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Global.DSEG.dump("MONITOR.interfaceModule'END: ");
		Global.CSEG.dump("MONITOR.interfaceModule'END: ");
		Global.dumpDISPL("MONITOR.interfaceModule'END: ");
		if(Scode.curinstr != Scode.S_BODY) Util.IERR("Illegal termination of module head");
		Scode.inputInstr();
//	%+SC    repeat InputInstr while CurInstr=S_INIT
//	%+SC    do IERR("InterfaceModule: Init values is not supported");
//	%+SC       InTag(%wrd%); intype; SkipRepValue;
//	%+SC    endrepeat;

		if(Scode.curinstr != Scode.S_ENDMODULE) Util.IERR("Improper termination of module");
	}
	
//	%title ***   M o d u l e   D e f i n i t i o n   ***

	public static void moduleDefinition() {
//	       range(0:MaxWord) nXtag; ref(ModElt) m;
//	       infix(WORD) itag,xtag; infix(Fixup) Fx;
//	       modident:=inMsymb; modcheck:=inSymb;
//	       if PROGID.val = 0
//	       then edsymb(sysedit,modident);
//	            PROGID:=DefSymb(pickup(sysedit));
//	       endif;
//	       BEGASM(CSEGNAM,DSEGNAM); nXtag:=0;
//	       InputInstr;
//	       m:=DICREF(modident);
//	%+S    if SYSGEN=0
//	%+S    then
//	            m.RelElt:=RELID;
//	%+S    endif;
//	       if LtabEntry.kind=0 then -- No LineNumberTable
//	       else
//	%+D         if LtabEntry.kind<>fixadr then IERR("PARSE:Md-1") endif
//	            Fx:=FIXTAB(LtabEntry.fix.HI).elt(LtabEntry.fix.LO);
//	%+D         if Fx.smbx.val=0 then IERR("PARSE:Md-2") endif;
//	            m.LinTab:=Fx.smbx;
//	       endif;
		Global.modident = Scode.inString();
		Global.modcheck = Scode.inString();
		Global.moduleID = Global.modident;
		String sourceID = Global.getSourceID();
		Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		if(Global.PROGID == null) Global.PROGID = Global.modident;

		Scode.inputInstr();
		while(viisible()) { Scode.inputInstr(); }
		
		
//	       repeat while CurInstr=S_TAG
//	       do InTag(%itag%);
//	%+D       xtag:=InputNumber;
//	%-D       InNumber(%xtag%);
//	%+D       if xtag.HI >= MxpXtag then CAPERR(CapTags) endif;
//	          if   TAGTAB(xtag.HI)=none
//	          then TAGTAB(xtag.HI):=
//	               NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	          TAGTAB(xtag.HI).elt(xtag.LO):=itag;
//	          InputInstr;
//	          if xtag.val>nXtag then nXtag:=xtag.val endif;
//	       endrepeat;
//	       OutputModule(nXtag);
//
//	       if CurInstr <> S_BODY then
//	       IERR("Illegal termination of module head") endif;
//
//	       repeat while NextByte = S_LOCAL
//	       do inputInstr; inGlobal endrepeat;
//
//	       programElements;
//
//	       if CurInstr <> S_ENDMODULE then
//	       IERR("Improper termination of module") endif;
//	       peepExhaust(true);
//	       ENDASM;
		Util.IERR("Parse.moduleDefinition: NOT IMPLEMENTED");
	}

	/**
	 * 	visible
	 * 		::= record_descriptor | routine_profile
	 * 		::= routine_specification | label_specification
	 * 		::= constant_specification | insert_statement
	 * 		::= info_setting
	 */
	private static boolean viisible() {
		System.out.println("MONITOR.viisible: CurInstr="+Scode.edInstr(Scode.curinstr));
		switch(Scode.curinstr) {
			case Scode.S_CONSTSPEC:		CONST.inConstant(false); break;
			case Scode.S_LABELSPEC:		IntDescr.ofLabelSpec(); break;
			case Scode.S_RECORD:		RecordDescr.of(); break;
			case Scode.S_PROFILE:		ProfileDescr.inProfile(Kind.P_VISIBLE); break;
			case Scode.S_ROUTINESPEC:	IntDescr.ofRoutineSpec(); break;
			case Scode.S_INSERT:		new InsertStatement(false); break;
			case Scode.S_SYSINSERT:		new InsertStatement(true); break;
			case Scode.S_LINE:			setLine(0); break;
			case Scode.S_DECL:			CTStack.checkStackEmpty(); setLine(Kind.qDCL); break;
			case Scode.S_STMT:			CTStack.checkStackEmpty(); setLine(Kind.qSTM); break;
			case Scode.S_SETSWITCH:		setSwitch(); break;
			case Scode.S_INFO:			Util.WARNING("Unknown info: " + Scode.inString());
			default: return false;
		}
		return true;
	}

	public static void setSwitch() {
		Util.IERR("NOT IMPL");
	}

	public static void setLine(int type) {
		Global.curline = Scode.inNumber();
		Global.PSEG.emit(new SVM_LINE(type, Global.curline), "MONITOR.setLine: ");
	}

}
