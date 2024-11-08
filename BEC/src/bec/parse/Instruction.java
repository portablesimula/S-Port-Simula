package bec.parse;

import bec.util.Util;

public class Instruction {

	
//	%title ***   I n s t r u c t i o n   ***

	public static boolean instruction() {
		boolean result = false; // Export
		Util.IERR("Parse.instruction: NOT IMPLEMENTED");
//	begin range(0:MaxWord) n,s,ofst; ref(Temp) tmp;
//	      infix(WORD) tag,i,sx,wrd,ndest; Boolean OldTSTOFL;
//	      range(0:MaxByte) cond,b,b1,b2; short integer repdist;
//	      ref(Descriptor) v; infix(ValueItem) itm;
//	      infix(MemAddr) d,a; ref(Qpkt) LL;
//	      range(0:MaxType) type; ref(Temp) x,y,z;
//	      ref(RecordDescr) fixrec; ref(LocDescr) attr;
//	      ref(Address) adr; ref(SwitchDescr) sw;
//
//		System.out.println("Parse.instruction: "+Scode.edInstr(Scode.curinstr));
//		result = true;
//		switch(Scode.curinstr) {
//		case Scode.S_CONSTSPEC: Minut.inConstant(false); break;
//		case Scode.S_CONST:     Minut.inConstant(true); break;
//		case Scode.S_RECORD:    Minut.inRecord(); break;
//	%+S   when S_ROUTINESPEC: SpecRut(false)
//	%+D   when S_SETOBJ:
//	%+D        CheckTosInt; CheckSosValue; CheckSosType(T_OADDR);
//	%+D        IERR("SSTMT.SETOBJ is not implemented");
//	%+D   when S_GETOBJ:
//	%+D        CheckTosInt;
//	%+D        IERR("SSTMT.GETOBJ is not implemented");
//	%+D   when S_ACCESS,S_ACCESSV:
//	%+D        IERR("SSTMT.ACCESS is not implemented");
//	      when S_PUSH,S_PUSHV:
//	           InTag(%tag%); v:=DISPL(tag.HI).elt(tag.LO);
//	           case 0:K_Max (v.kind)
//	           when K_GlobalVar: d:=v qua IntDescr.adr
//	%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//	%-E                 if AdrSegid(d)=DGROUP.val then d.sbireg:=oSS endif;
//	%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//	           when K_ExternVar:
//	                    d.kind:=extadr;
//	%-E                 d.sbireg:=0;
//	%+E                 d.sibreg:=NoIBREG;
//	                    d.rela.val:=v qua ExtDescr.adr.rela;
//	                    d.smbx:=v qua ExtDescr.adr.smbx;
//	%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//	%-E                 if AdrSegid(d)=DGROUP.val then d.sbireg:=oSS endif;
//	%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//	           when K_LocalVar:
//	                d.kind:=locadr; d.rela.val:=0;
//	                d.loca:=v qua LocDescr.rela;
//	%-E             d.sbireg:=bOR(oSS,rmBP);
//	%+E             d.sibreg:=bEBP+NoIREG;
//	           when K_Parameter:
//	                d.kind:=reladr; d.segmid.val:=0;
//	                d.rela.val:=v qua LocDescr.rela;
//	%-E             d.sbireg:=bOR(oSS,rmBP);
//	%+E             d.sibreg:=bEBP+NoIREG;
//	           when K_Export:
//	                d.kind:=reladr; d.segmid.val:=0;
//	                d.rela.val:=v qua LocDescr.rela;
//	%-E             d.sbireg:=bOR(oSS,rmBP);
//	%+E             d.sibreg:=bEBP+NoIREG;
//	           otherwise a:=noadr;
//	%+D                  edit(errmsg,v);
//	                     IERR("Illegal push of: ")
//	           endcase;
//	           Push(NewAddress(v.type,0,d));
//	           if v.kind=K_Parameter
//	           then TOS.repdist:= - wAllign(%TOS.repdist%) endif;
//	           if CurInstr=S_PUSHV then GQfetch endif;
//
//		case Scode.S_PUSHC: Coder.pushConst(); break;
//	      when S_INDEX,S_INDEXV:
//	%+C        CheckTosInt; CheckSosRef;
//	           adr:=TOS.suc; repdist:=adr.repdist;
//	           if repdist=0 then IERR("PARSE.INDEX: Not info type") endif;
//	           if TOS.kind=K_Coonst
//	           then itm:=TOS qua Coonst.itm;
//	                adr.Offset:=adr.Offset+(repdist*itm.wrd);
//	                GQpop;
//	                if adr.AtrState=FromConst
//	                then adr.AtrState:=NotStacked;
//	                     qPOPKill(AllignFac);
//	                endif;
//	           else
//	%-E             if TOS.type <> T_WRD2 then GQconvert(T_WRD2) endif;
//	%-E             GetTosAdjustedIn86(qAX); Pop; AssertObjStacked;
//	%-E             GQiMultc(repdist); -- AX:=AX*repdist
//	%-E             if    adr.AtrState=FromConst then qPOPKill(2)
//	%-E             elsif adr.AtrState=Calculated
//	%-E             then Qf1(qPOPR,qBX,cVAL); Qf2(qDYADR,qADDF,qAX,cVAL,qBX) endif;
//	%-E             Qf1(qPUSHR,qAX,cVAL); adr.AtrState:=Calculated;
//	%+E             if TOS.type <> T_WRD4 then GQconvert(T_WRD4) endif;
//	%+E             GetTosAdjustedIn86(qEAX); Pop; AssertObjStacked;
//	%+E             GQeMultc(repdist); -- EAX:=EAX*repdist
//	%+E             if    adr.AtrState=FromConst then qPOPKill(4)
//	%+E             elsif adr.AtrState=Calculated
//	%+E             then Qf1(qPOPR,qEBX,cVAL);
//	%+E                  Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
//	%+E             endif;
//	%+E             Qf1(qPUSHR,qEAX,cVAL); adr.AtrState:=Calculated;
//	           endif;
//	           if CurInstr=S_INDEXV then GQfetch endif;
//	      when S_SELECT,S_SELECTV:
//	%+C        CheckTosRef;
//	           InTag(%tag%); attr:=DISPL(tag.HI).elt(tag.LO); adr:=TOS;
//	           adr.Offset:=adr.Offset+attr.rela; adr.type:=attr.type;
//	           adr.repdist:=TTAB(adr.type).nbyte;
//	           if adr.AtrState=FromConst
//	           then adr.AtrState:=NotStacked;
//	                qPOPKill(AllignFac);
//	           endif;
//	           if CurInstr=S_SELECTV then GQfetch endif;
//	      when S_REMOTE,S_REMOTEV:
//	%+C        CheckTosType(T_OADDR);
//	           GQfetch; InTag(%tag%);
//	           attr:=DISPL(tag.HI).elt(tag.LO); Pop;
//	           a.kind:=reladr; a.rela.val:=0; a.segmid.val:=0;
//	%-E        a.sbireg:=0;
//	%+E        a.sibreg:=NoIBREG;
//	           adr:=NewAddress(attr.type,attr.rela,a);
//	           adr.ObjState:=Calculated; Push(adr);
//	           if CurInstr=S_REMOTEV then GQfetch endif;
//	      when S_FETCH: GQfetch;
//	      when S_DEREF:
//	%+C        CheckTosRef;
//	           adr:=TOS;
//	%+S        if SYSGEN <> 0
//	%+S        then if adr.repdist <> (TTAB(adr.type).nbyte)
//	%+S             then WARNING("DEREF on parameter") endif;
//	%+S        endif;
//	           AssertAtrStacked; Pop; pushTemp(T_GADDR);
//	      when S_REFER:
//	           type:=intype;
//	%+C        CheckTosType(T_GADDR);
//	           a.kind:=reladr; a.rela.val:=0; a.segmid.val:=0;
//	%-E        a.sbireg:=0;
//	%+E        a.sibreg:=NoIBREG;
//	           adr:=NewAddress(type,0,a);
//	           GQfetch; adr.ObjState:=adr.AtrState:=Calculated;
//	           Pop; Push(adr);
//	      when S_DSIZE:
//	           InTag(%tag%); fixrec:=DISPL(tag.HI).elt(tag.LO);
//	           if fixrec.nbrep <> 0
//	           then n:=fixrec.nbrep;
//	%+C             CheckTosInt;
//	                if TOS.kind=K_Coonst
//	                then itm:=TOS qua Coonst.itm; GQpop;
//	                     n:=wAllign(%(n*(itm.wrd))+fixrec.nbyte%);
//	%-E                  Qf2(qPUSHC,0,qAX,cVAL,n); itm.int:=n;
//	%+E                  Qf2(qPUSHC,0,qEAX,cVAL,n); itm.int:=n;
//	                     pushCoonst(T_SIZE,itm);
//	                else
//	%-E                  if TOS.Type = T_WRD4
//	%-E                  then GQfetch; Qf1(qPOPR,qAX,cVAL);
//	%-E                       Qf1(qPOPR,qDX,cVAL);
//	%-E                       NotMindMask:=uDX;
//	%-E                       Qf2(qDYADR,qORM,qDX,cVAL,qDX);
//	%-E                       PreReadMask:=uAX;
//	%-E                       PreMindMask:=wOR(PreMindMask,uAX);
//	%-E                       LL:=ForwJMP(q_WEQ);
//	%-E                       Qf2(qDYADR,qXOR,qAX,cVAL,qAX);
//	%-E                       Qf2(qMONADR,qDEC,qAX,cVAL,0);
//	%-E                       PreReadMask:=uAX;
//	%-E                       PreMindMask:=wOR(PreMindMask,uAX);
//	%-E                       DefFDEST(LL); Qf1(qPUSHR,qAX,cVAL);
//	%-E                       Pop; pushTemp(T_BYT2);
//	%-E                  elsif TOS.type <> T_BYT2
//	%-E                  then GQconvert(T_BYT2) endif;
//	%-E                  GetTosAdjustedIn86(qAX); Pop;
//	%-E                  OldTSTOFL:=TSTOFL; TSTOFL:=true;
//	%-E                  if n > 1
//	%-E                  then GQwMultc(n); -- AX:=AX*n
//	%-E                       Qf2(qDYADC,qADDF,qAX,cVAL,fixrec.nbyte);
//	%-E                  else Qf2(qDYADC,qADDF,qAX,cVAL,fixrec.nbyte+1);
//	%-E                       Qf2(qDYADC,qAND,qAX,cVAL,65534);
//	%-E                  endif;
//	%-E                  TSTOFL:=OldTSTOFL;
//	%-E                  Qf1(qPUSHR,qAX,cVAL);
//
//	%+E                  GetTosAdjustedIn86(qEAX); Pop;
//	%+E                  OldTSTOFL:=TSTOFL; TSTOFL:=true;
//	%+E                  if n > 3
//	%+E                  then GQeMultc(n); -- EAX:=EAX*n
//	%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte);
//	%+E                  else if n>1 then GQeMultc(n) endif; -- EAX:=EAX*n
//	%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte+3);
//	%+E                       Qf2(qDYADC,qAND,qEAX,cVAL,-4);
//	%+E                  endif;
//	%+E                  TSTOFL:=OldTSTOFL;
//	%+E                  Qf1(qPUSHR,qEAX,cVAL);
//	                     pushTemp(T_SIZE);
//	                endif;
//	           else
//	%+D             edit(errmsg,fixrec);
//	                IERR("Illegal DSIZE on: ");
//	                GQpop; itm.int:=0; pushCoonst(T_SIZE,itm);
//	           endif;
//	      when S_DUP: GQdup
//	      when S_POP: if TOS <> none then GQpop
//	                  else IERR("POP -- Stack is empty") endif;
//	      when S_POPALL:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	           repeat while TOS <> none
//	           do
//	%+C           if TOS.kind = K_ProfileItem
//	%+C           then b:=(b-1)+TOS qua ProfileItem.nasspar
//	%+C           elsif TOS.kind <> K_Result then b:=b-1 endif;
//	%+D           if TraceMode <> 0
//	%+D           then outstring("*** Pop: "); edit(sysout,TOS);
//	%+D                outstring(", n:"); outword(b); printout(sysout);
//	%+D           endif;
//	              --- do not generate superfluous POPRs
//	              if nextbyte<>S_ENDSKIP then GQpop else pop endif;
//	           endrepeat;
//	%+C        if b <> 0 then IERR("POPALL n  --  wrong value of n") endif;
//	%+C        CheckStackEmpty;
//	      when S_ASSIGN:     GQassign
//	      when S_UPDATE:     GQupdate
//	      when S_RUPDATE:    if NextByte = S_POP
//	                         then inputInstr; GQrassign -- can't use skip here!
//	                         else GQrupdate endif;
//	      when S_BSEG:       BSEGInstruction
//	      when S_IF:         IfConstruction(false)
//	      when S_SKIPIF:     SkipifConstruction(false)
//		case Scode.S_PRECALL: callInstruction(0); break;
//		case Scode.S_ASSCALL: callInstruction(1); break;
//		case Scode.S_REPCALL: int b = Scode.inByte(); callInstruction(b); break;
//	      when S_GOTO:
//	%-E        if TOS.type = T_NPADR
//	%-E        then
//	%-E             if TOS.kind=K_Coonst
//	%-E             then qPOPKill(2);
//	%-E                  a:=TakeTOS qua Coonst.itm.base;
//	%-E                  if InsideRoutine
//	%-E                  then PreReadMask:=uSP; Qf4c(qLOAD,0,qSP,cSTP,0,X_INITSP,0);
//	%-E %+D                    showWARN("SP set to INITSP");
//	%-E                       PreReadMask:=uBP; Qf2(qLOADC,0,qBP,cSTP,0);
//	%-E                  endif;
//	%-E                  Qf5(qJMP,0,0,0,a);
//	%-E             else if InsideRoutine
//	%-E                  then GetTosValueIn86(qAX); Pop;
//	%-E                       PreReadMask:=uSP; Qf4c(qLOAD,0,qSP,cSTP,0,X_INITSP,0);
//	%-E %+D                    showWARN("SP set to INITSP");
//	%-E                       PreReadMask:=uBP; Qf2(qLOADC,0,qBP,cSTP,0);
//	%-E                       Qf1(qPUSHR,qCS,cANY); Qf1(qPUSHR,qAX,cANY);
//	%-E                  else Qf1(qPUSHR,qCS,cANY); GQfetch; Pop endif;
//	%-E                  Qf2(qRET,0,0,0,0);
//	%-E             endif;
//	%-E        else
//	%+C             CheckTosType(T_PADDR);
//	                if TOS.kind=K_Coonst
//	                then qPOPKill(AllignFac);
//	%-E                  qPOPKill(2);
//	                     a:=TakeTOS qua Coonst.itm.base;
//	                     if InsideRoutine
//	                     then
//	%-E                     PreReadMask:=uSP;  Qf4c(qLOAD,0,qSP,cSTP,0,X_INITSP,0);
//	%+E                     PreReadMask:=uESP; Qf4c(qLOAD,0,qESP,cSTP,0,X_INITSP,0);
//	%-E                     PreReadMask:=uBP;  Qf2(qLOADC,0,qBP,cSTP,0);
//	%+E                     PreReadMask:=uEBP; Qf2(qLOADC,0,qEBP,cSTP,0);
//	%+D                        showWARN("SP set to INITSP");
//	                     endif;
//	                     Qf5(qJMP,0,0,0,a)
//	%-E             elsif (BNKLNK=0) and (not InsideRoutine)
//	%-E             then GQfetch; Pop; Qf2(qRET,0,0,0,0);
//	                else
//	%-E %+D              showWARN("E-GOTO called");
//	%-E                  GetTosValueIn86R3(qAX,qBX,0); Pop; a:=X_GOTO;
//	%-E                  PreReadMask:=wOR(uAX,uBX); Qf5(qJMP,0,0,0,a)
//	-- ????? %+E                  GQfetch; Pop; Qf2(qRET,0,0,0,0);
//	%+E                  if InsideRoutine
//	%+E                  then GetTosAdjustedIn86(qEAX); Pop;
//	%+E                     PreReadMask:=uESP; Qf4c(qLOAD,0,qESP,cSTP,0,X_INITSP,0);
//	%+E %+D                    showWARN("SP set to INITSP");
//	%+E                     PreReadMask:=uEBP; Qf2(qLOADC,0,qEBP,cSTP,0);
//	%+E                     Qf1(qPUSHR,qEAX,cANY);
//	%+E                  else GQfetch; Pop endif;
//	%+E                  Qf2(qRET,0,0,0,0);
//	                endif;
//	%-E        endif;
//	%+C        CheckStackEmpty;
//	      when S_PUSHLEN: itm.int:=Pushlen;
//	                      if not SkipProtect
//	                      then pushCoonst(T_SIZE,itm);
//	%-E                        Qf2(qPUSHC,0,qAX,cVAL,itm.wrd);
//	%+E                        Qf2(qPUSHC,0,qEAX,cVAL,itm.wrd);
//	                      endif;
//	      when S_SAVE:    ProtectConstruction(false)
//	%+S   when S_T_INITO:
//	%+SC                     CheckTosType(T_OADDR);
//	%+S                      GQfetch; Pop;
//	%+S                      Qf5(qCALL,0,0,4,X_INITO);
//	%+S   when S_T_GETO:
//	%+S %-E                  Qf2(qDYADC,qSUB,qSP,cSTP,4);
//	%+SE                     Qf2(qDYADC,qSUB,qESP,cSTP,4);
//	%+S                      Qf5(qCALL,0,0,4,X_GETO);
//	%+S                      Qf2(qADJST,0,0,0,4);
//	%+S                      pushTemp(T_OADDR);
//	%+S   when S_T_SETO:
//	%+SC                     CheckTosType(T_OADDR);
//	%+S                      GQfetch; Pop;
//	%+S                      Qf5(qCALL,0,0,4,X_SETO);
//		case Scode.S_DECL:       setLine(0); break;
//		case Scode.S_STMT:       setLine(0); break;
//		case Scode.S_LINE:       setLine(0); break;
//	      when S_EMPTY:
//	%+C                      CheckStackEmpty
//	      when S_SETSWITCH:  SetSwitch
//	      when S_INFO:       Ed(errmsg,InString); WARNING("Unknown info: ");
//	      when S_DELETE:
//	           i:=GetLastTag; InTag(%tag%);
//	%+C        if (tag.val<MinTag) or (tag.val>i.val)
//	%+C        then IERR("Argument to DELETE is out of range")
//	%+C        else
//	                repeat if DISPL(i.HI) = none then -- Nothing
//	                       elsif DISPL(i.HI).elt(i.LO) <> none
//	                       then DELETE(DISPL(i.HI).elt(i.LO));
//	                            DISPL(i.HI).elt(i.LO):=none;
//	                       endif;
//	                while i.val>tag.val do i.val:=i.val-1 endrepeat;
//	%+C        endif;
//	      when S_ZEROAREA:
//	%+C        CheckTosType(T_OADDR); CheckSosValue; CheckSosType(T_OADDR);
//	%-E        GQfetch; Qf1(qPOPR,qCX,cVAL); qPOPKill(2); Pop;
//	%-E        GQfetch; Qf1(qPOPR,qDI,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E        PreMindMask:=wOR(PreMindMask,uES); Qf1(qPUSHR,qES,cOBJ);
//	%-E        PreMindMask:=wOR(PreMindMask,uDI); Qf1(qPUSHR,qDI,cOBJ);
//	%-E        Qf2(qLOADC,0,qAX,cVAL,0); Qf2(qRSTRW,qZERO,qCLD,cVAL,qREP);
//	%+E        GQfetch; Qf1(qPOPR,qECX,cVAL); Pop; GQfetch; Qf1(qPOPR,qEDI,cOBJ);
//	%+E        PreMindMask:=wOR(PreMindMask,uEDI); Qf1(qPUSHR,qEDI,cOBJ);
//	%+E        Qf2(qLOADC,0,qEAX,cVAL,0); Qf2(qRSTRW,qZERO,qCLD,cVAL,qREP);
//	      when S_INITAREA:    intype;
//	%+C                       CheckTosType(T_OADDR);
//	      when S_EVAL:        --  Qf1(qEVAL,0) -- REMOVED FOR AD'HOC TEST ???????
//	      when S_FJUMPIF:
//	%-E        cond:=GQrelation(true); -- doJUMPrel);
//	%+E        cond:=GQrelation;
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%-E        if xFJUMP<>none
//	%-E        then if bAND(cond,7)=q_WNE
//	%-E             then FWRTAB(b):=xFJUMP; xFJUMP:=none endif endif
//	%+C        if DESTAB(b) <> none then IERR("PARSE:FJUMPIF") endif;
//	           if TOS=SAV then DESTAB(b):=ForwJMP(cond)
//	           else LL:=ForwJMP(NotQcond(cond));
//	%-E             if xFJUMP<>none
//	%-E             then if bAND(cond,7)=q_WNE 
//	%-E                  then defFDEST(xFJUMP); xFJUMP:=none endif endif
//	                ClearSTK;
//	                DESTAB(b):=ForwJMP(0); DefFDEST(LL);
//	           endif;
//	%-E        if xFJUMP<>none
//	%-E        then if bAND(cond,7)=q_WEQ then defFDEST(xFJUMP)
//	%-E             else FWRTAB(b):=xFJUMP endif endif
//	      when S_FJUMP:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b) <> none then IERR("PARSE:FJUMP") endif;
//	           DESTAB(b):=ForwJMP(0);
//	%+C        CheckStackEmpty;
//	      when S_FDEST:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b)=none then IERR("PARSE.FDEST") endif;
//	           DefFDEST(DESTAB(b)); DESTAB(b):=none;
//	%-E        if FWRTAB(b)<>none then DefFDEST(FWRTAB(b)); FWRTAB(b):=none; endif
//	%+C        CheckStackEmpty;
//	      when S_BDEST:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b) <> none then IERR("PARSE.BDEST") endif;
//	           DESTAB(b):=DefBDEST;
//	%+C        CheckStackEmpty;
//	      when S_BJUMP:
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b)=none then IERR("PARSE.BJUMP") endif;
//	           BackJMP(0,DESTAB(b)); DESTAB(b):=none;
//	%+C        CheckStackEmpty;
//	      when S_BJUMPIF:
//	%-E        cond:=GQrelation(false);
//	%+E        cond:=GQrelation;
//	%+D        b:=InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b)=none then IERR("PARSE.BJUMPIF") endif;
//	           if TOS=SAV then BackJMP(cond,DESTAB(b))
//	           else LL:=ForwJMP(NotQcond(cond)); ClearSTK;
//	                BackJMP(0,DESTAB(b)); DefFDEST(LL);
//	           endif;
//	           DESTAB(b):=none;
//	      when S_SWITCH:
//	           InTag(%tag%);
//	%+D        ndest:=InputNumber;
//	%-D        InNumber(%ndest%);
//	           sw:=NEWOBJ(K_SwitchDescr,size(SwitchDescr));
//	           sw.ndest:=ndest.val; sw.nleft:=ndest.val;
//	           if ndest.HI >= MxpSdest
//	           then ERROR("Too large Case-Statement") endif;
//	           i.val:=0; sw.swtab:=NewFixAdr(DSEGID,i); IntoDisplay(sw,tag);
//	%+C        CheckTosInt;
//	           if TOS.type < T_WRD2 then GQconvert(T_WRD2) endif;
//	           a:=sw.swtab;
//	%-E        if DSEGID=DGROUP then a.sbireg:=bOR(oSS,rmBX);
//	%-E        else Qf2b(qLOADSC,qDS,qBX,cOBJ,0,sw.swtab);
//	%-E             a.sbireg:=bOR(oDS,rmBX);
//	%-E        endif;
//	%-E        GetTosAdjustedIn86(qBX); Pop;
//	%+E        GetTosAdjustedIn86(qEBX); Pop;
//
//	%+D        if IDXCHK <> 0 then --- pje 22.10.90
//	%+D           PreMindMask:=wOR(PreMindMask,uBX);
//	%+D %-E       Qf2(qDYADC,qCMP,qBX,cVAL,ndest.val);
//	%+DE          Qf2(qDYADC,qCMP,qEBX,cVAL,ndest.val);
//	%+D %-E       if DSEGID=DGROUP then PreReadMask:=uBX
//	%+D %-E       else PreReadMask:=wOR(uDS,uBX) endif;
//	%+DE          PreReadMask:=uBX;
//	%+D           LL:=ForwJMP(q_WLT);
//	%+D           Qf5(qCALL,0,0,0,X_ECASE); -- OutOfRange ==> ERROR
//	%+D %-E       Qf2(qLOADC,0,qBX,cVAL,0);
//	%+DE          Qf2(qLOADC,0,qEBX,cVAL,0);
//	%+D           PreReadMask:=uBX;
//	%+D %-E       if DSEGID=DGROUP then PreMindMask:=wOR(PreMindMask,uBX)
//	%+D %-E       else PreMindMask:=wOR(PreMindMask,wOR(uDS,uBX)) endif;
//	%+DE          PreMindMask:=wOR(PreMindMask,uBX);
//	%+D           DefFDEST(LL);
//	%+D        endif; --- pje 22.10.90
//
//	%-E        Qf2(qDYADR,qADD,qBX,cVAL,qBX);
//	%+E        a.sibreg:=bOR(bOR(128,bEBX),iEBX); -- swtab+[4*EBX] 
//	           Qf3(qJMPM,0,0,0,a);
//	      when S_SDEST:
//	           InTag(%tag%); sw:=DISPL(tag.HI).elt(tag.LO);
//	%+C        if sw.kind <> K_SwitchDescr
//	%+C        then IERR("Display-entry is not defined as a switch") endif;
//	%+D        sx:=InputNumber;
//	%-D        InNumber(%sx%);
//	%+C        if sx.val >= sw.ndest then IERR("Illegal switch index")
//	%+C        else
//	                i.val:=0; a:=NewFixAdr(CSEGID,i);
//	                if sw.DESTAB(sx.HI)=none
//	                then sw.DESTAB(sx.HI):=
//	                        NEWOBJ(K_AddrBlock,size(AddrBlock)) endif;
//	                sw.DESTAB(sx.HI).elt(sx.LO):=a;
//	                DefLABEL(0,a.fix.val,0);
//	%+C        endif;
//	           sw.nleft:=sw.nleft-1;
//	           if sw.nleft=0
//	           then EmitSwitch(sw); DELETE(sw);
//	                DISPL(tag.HI).elt(tag.LO):=none;
//	           endif;
//	      when S_CONVERT:  type:=intype;
//	           if type < T_REAL
//	           then if TOS.type=T_CHAR then type:=T_BYT1 endif endif;
//	           GQconvert(type)
//	      when S_NEG:  GQneg
//	      when S_ADD:  GQadd
//	      when S_SUB:  GQsub
//	      when S_MULT: GQmult
//	      when S_DIV:  GQdiv
//	      when S_REM:  GQrem
//	      when S_NOT:  GQnot
//	      when S_AND:  GQandxor(qAND)
//	      when S_OR:   GQandxor(qOR )
//	      when S_XOR:  GQandxor(qXOR)
//	      when S_EQV:  GQeqv
//	      when S_IMP:  GQimp
//	%+S   when S_LSHIFTL: GQshift(qSHL); -- Extension to S-Code: Left shift logical
//	%+S   when S_LSHIFTA: GQshift(qSHL); -- Extension to S-Code: Left shift arithm.
//	%+S   when S_RSHIFTL: GQshift(qSHR); -- Extension to S-Code: Right shift logical
//	%+S   when S_RSHIFTA: GQshift(qSAR); -- Extension to S-Code: Right shift arithm.
//	      when S_LOCATE:
//	%+C        CheckTosType(T_AADDR); CheckSosValue;
//	%+C        CheckSosType2(T_OADDR,T_GADDR);
//	%-E        GetTosValueIn86(qAX); Pop; GQfetch;
//	%+E        GetTosValueIn86(qEAX); Pop; GQfetch;
//	           if TOS.type=T_GADDR
//	           then
//	%-E             Qf1(qPOPR,qBX,cVAL); Qf2(qDYADR,qADDF,qAX,cVAL,qBX);
//	%+E             Qf1(qPOPR,qEBX,cVAL); Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
//	           endif;
//	%-E        Qf1(qPUSHR,qAX,cVAL);
//	%+E        Qf1(qPUSHR,qEAX,cVAL);
//	           Pop; pushTemp(T_GADDR);
//	      when S_INCO: GQinco_deco(true)
//	      when S_DECO: GQinco_deco(false)
//	      when S_DIST:
//	%+C        CheckTosType(T_OADDR); CheckSosValue; CheckSosType(T_OADDR);
//	%-E        GQfetch; Qf1(qPOPR,qDX,cOBJ); qPOPKill(2); Pop;
//	%-E        GQfetch; Qf1(qPOPR,qAX,cOBJ); qPOPKill(2); Pop;
//	%-E        Qf2(qDYADR,qSUBF,qAX,cVAL,qDX); Qf1(qPUSHR,qAX,cVAL);
//	%+E        GQfetch; Qf1(qPOPR,qEDX,cOBJ); Pop;
//	%+E        GQfetch; Qf1(qPOPR,qEAX,cOBJ); Pop;
//	%+E        Qf2(qDYADR,qSUBF,qEAX,cVAL,qEDX); Qf1(qPUSHR,qEAX,cVAL);
//	           pushTemp(T_SIZE);
//	      when S_COMPARE:
//	%-E        cond:=GQrelation(false);
//	%+E        cond:=GQrelation;
//	           Qf2(qLOADC,0,qAL,cVAL,0); Qf2(qCONDEC,cond,qAL,cVAL,0);
//	           Qf1(qPUSHR,qAL,cVAL);     pushTemp(T_BOOL);
//	      otherwise result:=false;
//		default: result = false;
//		}
//	      endcase;
//
//	%+D   if TraceMode <> 0 then DumpStack endif;
		return result;
	}
	

}
