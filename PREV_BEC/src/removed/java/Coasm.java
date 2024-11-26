package removed.java;

import bec.util.Scode;
import bec.util.Util;

public class Coasm {
//	Module COASM("iAPX");
//	begin
//	%+A   insert SCOMN,SKNWN,SBASE,ASMLIST;
//	%-A   insert SCOMN,SKNWN,SBASE;
//	       -----------------------------------------------------------------
//	       ---  COPYRIGHT 1988 by                                        ---
//	       ---  Simula a.s.                                              ---
//	       ---  Oslo, Norway                                             ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---              P O R T A B L E     S I M U L A              ---
//	       ---                                                           ---
//	       ---                   F O R    I B M    P C                   ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---           S   -   C   O   M   P   I   L   E   R           ---
//	       ---                                                           ---
//	       ---              Q - C o d e    U t i l i t i e s             ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---  Selection Switches:                                      ---
//	       ---                                                           ---
//	       ---     A - Includes Assembly Output                          ---
//	       ---     C - Includes Consistency Checks                       ---
//	       ---     D - Includes Tracing Dumps                            ---
//	       ---     S - Includes System Generation                        ---
//	       ---     E - Extended mode -- 32-bit 386                       ---
//	       ---     F - Optionally Produce COFF output                    ---
//	       ---     V - New version. (+V:New, -V:Previous)                ---
//	       -----------------------------------------------------------------
//	%page
//
//	    Define N_THEADR=128; -- 80H -- Relocatable Module Header
//	    Define N_COMENT=136; -- 88H -- Comment Record
//	    Define N_MODEND=138; -- 8AH -- 16 bit Module end record
//	%+E Define N_386END=139; -- 8BH -- 32 bit Module end record
//	    Define N_EXTDEF=140; -- 8CH -- External Reference
//	    Define N_PUBDEF=144; -- 90H -- 16 bit Public Definition
//	%+E Define N_PUB386=145; -- 91H -- 32 bit Public Definition
//	    Define N_LINNUM=148; -- 94H -- 16 bit Source Line Number
//	%+E Define N_LIN386=149; -- 95H -- 32 bit Source Line Number
//	    Define N_LNAMES=150; -- 96H -- Name List Record
//	    Define N_SEGDEF=152; -- 98H -- 16 bit Segment Definition
//	%+E Define N_SEG386=153; -- 99H -- 32 bit Segment Definition
//	    Define N_GRPDEF=154; -- 9AH -- Group Definition
//	    Define N_FIXUPP=156; -- 9CH -- Fixup previous 16 bit data image
//	%+E Define N_FIX386=157; -- 9DH -- Fixup previous 32 bit data image
//	    Define N_LEDATA=160; -- A0H -- 16 bit Enumerated Data Image
//	%+E Define N_LED386=161; -- A1H -- 32 bit Enumerated Data Image
//	    Define N_LIDATA=162; -- A2H -- 16 bit Iterated Data Image
//	%+E Define N_LID386=163; -- A3H -- 32 bit Iterated Data Image
//	%+D Define N_max=170;
//
//	Infix(wWORD) BlkBase;    -- Used by RELUT
//	Infix(WORD)  BlkOfst;    -- Used by RELUT
//
//	%+F Define MaxSectn=3;
//	%+F Ref(CoffSectionHeader) Shdr(MaxSectn); -- Section Headers
//	%+F Integer RawDataPos; -- File pointer to RawData (I.e. pAddr=0)
//	%+F Infix(DWORD) StrLng; -- Length of String Table
//	%+F Ref(StringTabEntry) FstStr,LstStr; -- String Table
//	%title ***   E X H A U S T    C O D E   ***
//	Visible Routine Exhaust;
//	import infix(WORD) segid; Boolean all;
//	begin ref(Qpkt) qi; range(0:MaxWord) cnt,lim,i; ref(Segment) Seg;
//	      infix(WORD) oldseg; range(0:MaxLine) oldline;
//	      oldseg:=CSEGID; oldline:=curline; seg:=DICREF(segid);
//	      if segid=CSEGID then qi:=qfirst; cnt:=qcount;
//	      else qi:=Seg.qfirst; cnt:=Seg.qcount endif;
//	      if qi=none then goto E1 endif;
//	%+D   if (listq2 > 1) or (TLIST > 2)
//	%+D   then outstring("****** Begin - Exhaust "); outsymb(segid);
//	%+D        if all then outstring(", ALL") else outstring(", HALF") endif
//	%+D        outstring(", Count:"); outword(cnt);
//	%+D        outstring(" ******"); outimage;
//	%+D   endif;
//	      CSEGID:=segid; InitCode(segid);
//	%+A   if asmgen then AsmSection(CSEGID) endif;
//	      if all
//	      then qi:=QtoI(qi,cnt);
//	%+D        if qi <> none then IERR("COASM:Exhaust-1") endif;
//	           if segid=oldseg
//	           then qfirst:=none; qlast:=none; qcount:=0 endif;
//	           Seg.qfirst:=none; Seg.qlast:=none; Seg.qcount:=0;
//	      else lim:=cnt/2; qi:=QtoI(qi,lim);
//	%+D        if qi = none then IERR("COASM:Exhaust-2") endif;
//	           if segid=oldseg then qfirst:=qi; qcount:=cnt-lim endif;
//	           Seg.qfirst:=qi; Seg.qcount:=cnt-lim; qi.pred:=none;
//	      endif;
//	%+D   if listq2 > 1
//	%+D   then outstring("****** End - Exhaust "); outsymb(segid);
//	%+D        if all then outstring(", ALL") else outstring(", HALF") endif
//	%+D        outstring(" ******"); outimage;
//	%+D   endif;
//	      CSEGID:=oldseg; curline:=oldline;
//	E1:end;
//	%title ***   E M I T   D A T A   U T I L I T I E S   ***
//	-- DataCounter == LineCounter == DBUF.ofst+DBUF.nxt
//
//	Routine InitData; import infix(WORD) NewSegid;
//	begin infix(WORD) OldSegid,NewSegx,OldSegx;
//	%+E   infix(wWORD) ofst32;
//	      NewSegx:=PutSegx(NewSegid);
//	%-E   OldSegx:=DBUF.segx
//	%+E   OldSegx.val:=DBUF.segx
//	      if OldSegx <> NewSegx
//	      then if OldSegx.val <> 0
//	           then OldSegid:=DIC.Segm(OldSegx.HI).elt(OldSegx.LO);
//	                OutDbuffer;
//	%-E             DICREF(OldSegid) qua Segment.rela.LowWord:=DBUF.ofst;
//	%+E             ofst32.HighWord.val:=DBUF.ofstHI
//	%+E             ofst32.LowWord.val:=DBUF.ofstLO
//	%+E             DICREF(OldSegid) qua Segment.rela:=ofst32;
//	           endif;
//	%-E        DBUF.segx:=NewSegx;
//	%+E        DBUF.segx:=NewSegx.val;
//	           if NewSegx.val <> 0
//	           then
//	%-E             DBUF.ofst:=DICREF(NewSegid) qua Segment.rela.LowWord;
//	%+E             ofst32:=DICREF(NewSegid) qua Segment.rela;
//	%+E             DBUF.ofstHI:=ofst32.HighWord.val;
//	%+E             DBUF.ofstLO:=ofst32.LowWord.val;
//	                DBUF.hed:=2 qua character; DREL.hed:=3 qua character;
//	           endif;
//	      endif;
//	end;
//
//	Routine OutDbuffer;
//	begin infix(String) bf;
//	%-E   integer ofst;
//	%+E   infix(wWORD) ofst;
//	%+D   if DBUF.nxt > 1024 then IERR("COASM:OutDbuffer-0") endif;
//	%-E   ofst:=DBUF.ofst.val; ofst:=ofst+DBUF.nxt;
//	%+E   ofst.HighWord.val:=DBUF.ofstHI;
//	%+E   ofst.LowWord.val:=DBUF.ofstLO; ofst.val:=ofst.val+DBUF.nxt;
//	%-E   if ofst>MaxWord then ERROR("Data Segment larger than 65K") endif;
//	%+A   if asmgen then -- Nothing
//	%+A   else
//	           if DBUF.nxt <> 0
//	           then if NXTYPE=0 -- First buffer is treated different --
//	                then bf.chradr:=@DBUF.hed(4);
//	                     bf.nchr:=DBUF.nxt+2+AllignFac;
//	                     NXTYPE:=2; NXTLNG.val:=bf.nchr+4;
//	                else bf.chradr:=@DBUF.hed;
//	                     bf.nchr:=DBUF.nxt+6+AllignFac;
//	                endif;
//	%+D             if TLIST > 3
//	%+D             then outstring("*** OutDbuffer1: type=");
//	%+D                  outword(DBUF.hed qua integer);
//	%+D                  outstring(", lng="); outword(bf.nchr);
//	%+D                  Printout(sysout);
//	%+D             endif;
//	                DBUF.nxt:=bf.nchr; EnvOutBytes(scrfile,bf);
//	                if Status<>0 then FILERR(scrfile,"OutDbuffer-1") endif;
//	                if DREL.nxt <> 0
//	                then bf.chradr:=@DREL.hed;
//	                     bf.nchr:=(DREL.nxt*Size2Word(size(RelocPkt)))+4;
//	%+D                  if TLIST > 3
//	%+D                  then outstring("*** OutDbuffer2: type=");
//	%+D                       outword(DREL.hed qua integer);
//	%+D                       outstring(", lng="); outword(bf.nchr);
//	%+D                       Printout(sysout);
//	%+D                  endif;
//	                     DREL.nxt:=bf.nchr; EnvOutBytes(scrfile,bf);
//	                     if Status <> 0
//	                     then FILERR(scrfile,"OutDbuffer-2") endif;
//	                endif;
//	           elsif DREL.NXT<>0 then FILERR(scrfile,"OutDbuffer-3") endif;
//	%+A   endif;
//	      DBUF.nxt:=0; DREL.nxt:=0;
//	%-E   DBUF.ofst.val:=ofst;
//	%+E   DBUF.ofstHI:=ofst.HighWord.val; DBUF.ofstLO:=ofst.LowWord.val;
//	end;
//	%page
//
//	Macro DataSpace(1);
//	begin if DBUF.nxt >= (1025-(%1)) then OutDbuffer endif; endmacro;
//
//	Macro DataRelSpace(1);
//	begin if DBUF.nxt >= (1025-(%1)) then OutDbuffer
//	      elsif DREL.nxt >= 256 then OutDbuffer endif;
//	endmacro;
//
//	Routine EmitData; import range(0:MaxWord) n; name() src;
//	begin range(0:MaxWord) rest;
//	%+A   if asmgen or (listsw>0) then ListData(n,src) endif;
//	L:    rest:=1024-DBUF.nxt;
//	      if n < rest
//	      then APX_SMOVEI(n,@DBUF.chr(DBUF.nxt),src);
//	           DBUF.nxt:=DBUF.nxt+n;
//	      else APX_SMOVEI(rest,@DBUF.chr(DBUF.nxt),src);
//	           DBUF.nxt:=DBUF.nxt+rest; OutDbuffer;
//	           src:=name(var(src qua name(character))(rest));
//	           n:=n-rest; if n<>0 then goto L endif;
//	      endif;
//	end;
//
//	Macro Emit1Data(1);
//	begin
//	%+D   Output1Data(%1);
//	%-D   DBUF.byt(DBUF.nxt):=%1; DBUF.nxt:=DBUF.nxt+1;
//	endmacro;
//	%+D Routine Output1Data; import range(0:MaxByte) i;
//	%+D begin
//	%+AD  if asmgen or (listsw>0) then List1Data(i) endif;
//	%+D   DBUF.byt(DBUF.nxt):=i; DBUF.nxt:=DBUF.nxt+1;
//	%+D end;
//
//	Routine Emit2Data; import range(0:MaxWord) i;
//	begin
//	%+AD  if asmgen or (listsw>0) then List2Data(i) endif;
//	      DataSpace(%2%);
//	      DBUF.byt(DBUF.nxt):=GetLow(%i%); DBUF.nxt:=DBUF.nxt+1;
//	      DBUF.byt(DBUF.nxt):=GetHigh(%i%); DBUF.nxt:=DBUF.nxt+1;
//	end;
//
//	Visible Routine EmitZero; import range(0:MaxWord) n;
//	begin range(0:MaxWord) rest;
//	%+AD  if asmgen or (listsw>0) then ListZero(n) endif;
//	L:    rest:=1024-DBUF.nxt;
//	      if n < rest
//	      then APX_SFILL(0 qua character,n,@DBUF.chr(DBUF.nxt));
//	           DBUF.nxt:=DBUF.nxt+n;
//	      else APX_SFILL(0 qua character,rest,@DBUF.chr(DBUF.nxt));
//	           DBUF.nxt:=DBUF.nxt+rest; OutDbuffer;
//	           n:=n-rest; if n<>0 then goto L endif;
//	      endif;
//	end;
//	%page
//
//	Routine Emit2Zero;
//	begin
//	%+AD  if asmgen or (listsw>0) then List2Data(0) endif;
//	      DataSpace(%2%);
//	      DBUF.byt(DBUF.nxt):=0; DBUF.nxt:=DBUF.nxt+1;
//	      DBUF.byt(DBUF.nxt):=0; DBUF.nxt:=DBUF.nxt+1;
//	end;
//
//
//	Routine EmitAddrData; import infix(MemAddr) x;
//	begin infix(RelocPkt) pkt;
//	%+AD  if asmgen or (listsw>0) then ListAddrData(x) endif;
//	      case 0:adrMax (x.kind) when 0: x.rela.val:=0;
//	      when segadr: pkt.segx:=PutSegx(x.segmid); DataRelSpace(%4%);
//	                   pkt.mrk:=wOR(wOR(mfPOINTER,mrSEG),DBUF.nxt);
//	                   DREL.elt(DREL.nxt):=pkt; DREL.nxt:=DREL.nxt+1;
//	      when extadr: pkt.extx:=PutExtern(x.smbx); DataRelSpace(%4%);
//	                   pkt.mrk:=wOR(wOR(mfPOINTER,mrEXT),DBUF.nxt);
//	                   DREL.elt(DREL.nxt):=pkt; DREL.nxt:=DREL.nxt+1;
//	      when fixadr: pkt.fix:=x.fix; DataRelSpace(%4%);
//	                   pkt.mrk:=wOR(wOR(mfPOINTER,mrFIX),DBUF.nxt);
//	                   DREL.elt(DREL.nxt):=pkt; DREL.nxt:=DREL.nxt+1;
//	      otherwise x.rela.val:=0; IERR("EmitAddrData-2") endcase;
//	      DataSpace(%4%);
//	%-E   DBUF.byt(DBUF.nxt):=x.rela.LO; DBUF.nxt:=DBUF.nxt+1;
//	%-E   DBUF.byt(DBUF.nxt):=x.rela.HI; DBUF.nxt:=DBUF.nxt+1;
//	%-E   DBUF.byt(DBUF.nxt):=0; DBUF.nxt:=DBUF.nxt+1;
//	%-E   DBUF.byt(DBUF.nxt):=0; DBUF.nxt:=DBUF.nxt+1;
//	%+DE  if x.rela.HighWord.val <> 0
//	%+DE  then IERR("EmitAddrData-3"); x.rela.HighWord.val:=0 endif;
//	%+E   DBUF.byt(DBUF.nxt):=x.rela.LO;   DBUF.nxt:=DBUF.nxt+1;
//	%+E   DBUF.byt(DBUF.nxt):=x.rela.LOHI; DBUF.nxt:=DBUF.nxt+1;
//	%+E   DBUF.byt(DBUF.nxt):=x.rela.HILO; DBUF.nxt:=DBUF.nxt+1;
//	%+E   DBUF.byt(DBUF.nxt):=x.rela.HI;   DBUF.nxt:=DBUF.nxt+1;
//	end;
//
//	%-E Routine EmitOfstData; import infix(MemAddr) x;
//	%-E begin infix(RelocPkt) pkt;
//	%-E %+AD  if asmgen or (listsw>0) then ListOfstData(x) endif;
//	%-E       case 0:adrMax (x.kind) when 0: x.rela.val:=0;
//	%-E       when segadr: pkt.segx:=PutSegx(x.segmid); DataRelSpace(%2%);
//	%-E                    pkt.mrk:=wOR(wOR(mfOFFSET,mrSEG),DBUF.nxt);
//	%-E                    DREL.elt(DREL.nxt):=pkt; DREL.nxt:=DREL.nxt+1;
//	%-E       when extadr: pkt.extx:=PutExtern(x.smbx); DataRelSpace(%2%);
//	%-E                    pkt.mrk:=wOR(wOR(mfOFFSET,mrEXT),DBUF.nxt);
//	%-E                    DREL.elt(DREL.nxt):=pkt; DREL.nxt:=DREL.nxt+1;
//	%-E       when fixadr: pkt.fix:=x.fix; DataRelSpace(%2%);
//	%-E                    pkt.mrk:=wOR(wOR(mfOFFSET,mrFIX),DBUF.nxt);
//	%-E                    DREL.elt(DREL.nxt):=pkt; DREL.nxt:=DREL.nxt+1;
//	%-E       otherwise x.rela.val:=0; IERR("EmitAddrData-2") endcase;
//	%-E       DataSpace(%2%);
//	%-E       DBUF.byt(DBUF.nxt):=x.rela.LO; DBUF.nxt:=DBUF.nxt+1;
//	%-E       DBUF.byt(DBUF.nxt):=x.rela.HI; DBUF.nxt:=DBUF.nxt+1;
//	%-E end;
//	%page
//
//	Visible Routine EmitStructConst;
//	import range(0:MaxType) type; range(0:MaxWord) nbyte;
//	export infix(MemAddr) adr;
//	begin range(0:AllignFac) n;
//	%+E   infix(wWORD) ofst32;
//	%-E   n:=AllignDiff(%DBUF.ofst.val+DBUF.nxt%);
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   n:=AllignDiff(%ofst32.val+DBUF.nxt%);
//	      if n<>0 then EmitZero(n) endif;
//	%+A   if asmgen then AsmSection(DSEGID)
//	%+AD  elsif listsw>0 then AsmSection(DSEGID)
//	%+A   endif;
//	      adr.kind:=segadr;
//	%-E   adr.rela.val:=DBUF.ofst.val+DBUF.nxt;
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   adr.rela.val:=ofst32.val+DBUF.nxt;
//	%-E   adr.sbireg:=0;
//	%+E   adr.sibreg:=NoIBREG;
//	      adr.segmid:=DSEGID; EmitStructValue(nbyte);
//	end;

	
	public static void emitStructValue() {
//	import range(0:MaxWord) nbyte;
//	begin range(0:MaxType) atype; infix(WORD) atag; ref(LocDescr) atr;
//	      infix(wWORD) rStart,aStart;
//	%+E   infix(wWORD) ofst32;
//	%+D   if listq2 > 1
//	%+D   then outstring("EmitStructValue("); outword(nbyte);
//	%+D        outchar(')'); outimage;
//	%+D   endif;
//	%-E   rStart.val:=DBUF.ofst.val+DBUF.nxt;
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   rStart.val:=ofst32.val+DBUF.nxt;
		
		System.out.println("Coasm.emitStructValue: ");
//	      repeat InputInstr while CurInstr = S_ATTR
		Scode.inputInstr();
		while(Scode.curinstr == Scode.S_ATTR) {
			System.out.println("Coasm.emitStructValue'S_ATTR: ");
//	      do InTag(%atag%); atr:=DISPL(atag.HI).elt(atag.LO);
//	         TypeLength:=0; atype:=intype;
			Scode.inString(); Scode.inType();
//	         if atype<T_Max then TypeLength:=TTAB(atr.type).nbyte endif;
//	%+D      if listq2 > 1
//	%+D      then outstring("Attribute "); EdType(sysout,atr.type);
//	%+D           outstring(" <== "); EdType(sysout,atype); outimage;
//	%+D      endif;
//	         repeat
//	%-E             aStart.val:=DBUF.ofst.val+DBUF.nxt-rStart.val;
//	%+E             ofst32.HighWord.val:=DBUF.ofstHI
//	%+E             ofst32.LowWord.val:=DBUF.ofstLO
//	%+E             aStart.val:=ofst32.val+DBUF.nxt-rStart.val;
//	         while aStart.val < atr.rela
//	         do EmitZero(atr.rela-aStart.val) endrepeat;
//	         if aStart.val <> atr.rela then IERR("EmitStructValue-1") endif;
//	         TreatValue(TypeLength);
			Coasm.emitValue();
			Util.IERR("HVA NÅ");
//	      endrepeat;
		}
		Scode.expect(Scode.S_ENDRECORD);
//	      if CurInstr <> S_ENDRECORD
//	      then IERR("Syntax error in record-constant") endif;
//	      repeat
//	%-E          aStart.val:=DBUF.ofst.val+DBUF.nxt-rStart.val;
//	%+E          ofst32.HighWord.val:=DBUF.ofstHI
//	%+E          ofst32.LowWord.val:=DBUF.ofstLO
//	%+E          aStart.val:=ofst32.val+DBUF.nxt-rStart.val;
//	      while aStart.val < nbyte do EmitZero(nbyte-aStart.val) endrepeat;
//	      if aStart.val <> nbyte
//	      then
//	%+D        EdWrd(errmsg,aStart.val); Ed(errmsg," <> ");
//	%+D        EdWrd(errmsg,nbyte);
//	           IERR(" -- EmitStructValue-2");
//	      endif;
	}

	
//	Visible Routine EmitLiteral;
//	import name() lit; range(0:MaxWord) nbyte; export infix(MemAddr) adr;
//	begin range(0:AllignFac) n;
//	%+E   infix(wWORD) ofst32;
//	%-E   n:=AllignDiff(%DBUF.ofst.val+DBUF.nxt%);
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   n:=AllignDiff(%ofst32.val+DBUF.nxt%);
//	      if n<>0 then EmitZero(n) endif;
//	%+A   if asmgen then AsmSection(DSEGID);
//	%+AD  elsif listsw>0 then AsmSection(DSEGID)
//	%+A   endif;
//	      adr.kind:=segadr;
//	%-E   adr.rela.val:=DBUF.ofst.val+DBUF.nxt;
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   adr.rela.val:=ofst32.val+DBUF.nxt;
//	%-E   adr.sbireg:=0;
//	%+E   adr.sibreg:=NoIBREG;
//	      adr.segmid:=DSEGID; EmitData(nbyte,lit);
//	end;
//	                
//	Visible Routine EmitSwitch; import ref(SwitchDescr) sw;
//	begin infix(MemAddr) adr; infix(WORD) n; range(0:AllignFac) na;
//	%+E   infix(wWORD) ofst32;
//	%-E   na:=AllignDiff(%DBUF.ofst.val+DBUF.nxt%);
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   na:=AllignDiff(%ofst32.val+DBUF.nxt%);
//	      if na<>0 then EmitZero(na) endif;
//	%+A   if asmgen then AsmSection(DSEGID);
//	%+AD  elsif listsw>0 then AsmSection(DSEGID)
//	%+A   endif;
//	      DefDataFixup(sw.swtab); n.val:=0;
//	      repeat while n.val < sw.ndest
//	      do adr:=sw.DESTAB(n.HI).elt(n.LO); n.val:=n.val+1;
//	%-E      EmitOfstData(adr);
//	%+E      EmitAddrData(adr);
//	      endrepeat;
//	      n.val:=sw.ndest;
//	      repeat DELETE(sw.DESTAB(n.HI)); sw.DESTAB(n.HI):=none;
//	      while n.HI <> 0 do n.HI:=n.HI-1 endrepeat;
//	end;
//
//	Visible routine EmitRepValue;
//	import ref(IntDescr) v; range(0:MaxWord) nbyte;
//	begin range(0:AllignFac) n;
//	%+E   infix(wWORD) ofst32;
//	%-E   n:=AllignDiff(%DBUF.ofst.val+DBUF.nxt%);
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   n:=AllignDiff(%ofst32.val+DBUF.nxt%);
//	      if n<>0 then EmitZero(n) endif;
//	%+A   if asmgen then AsmSection(DSEGID);
//	%+AD  elsif listsw>0 then AsmSection(DSEGID)
//	%+A   endif;
//	      if v.adr.kind=fixadr then DefDataFixup(v.adr) endif;
//	      v.adr.kind:=segadr;
//	%-E   v.adr.rela.val:=DBUF.ofst.val+DBUF.nxt;
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   v.adr.rela.val:=ofst32.val+DBUF.nxt;
//	%-E   v.adr.sbireg:=0;
//	%+E   v.adr.sibreg:=NoIBREG;
//	      v.adr.segmid:=DSEGID; TreatValue(nbyte);
//	end;
//
//	%title ***   T r e a t   V a l u e   I t e m   ***
	

	public static void emitValue() {
//	import range(0:MaxWord) nbyte;
//	begin infix(MemAddr) addr; ref(Descriptor) d,lab,rut;
//	      range(0:MaxType) type;
//	      infix(WORD) tag,rtag,lng,smbx;
//	      range(0:MaxWord) ofst,i,rest; ref(RecordDescr) rec;
//	      ref(LocDescr) atr; infix(ValueItem) itm;
//	%+D   infix(String) s;
//	%+D   if listq2 > 1
//	%+D   then outstring("TreatValue("); outword(nbyte);
//	%+D        outchar(')'); outimage;
//	%+D   endif;
//
//	NXT:
	LOOP:while(true) {
		System.out.println("Coasm.treatValue: "+Scode.edInstr(Scode.nextByte()));
		switch(Scode.nextByte()) {
		case Scode.S_TEXT: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	%+D        lng:=InputNumber;
//	%-D        InNumber(%lng%);
//	           repeat rest:=sBufLen-SBUF.nxt while lng.val >= rest
//	           do
//	%+D           if InputTrace <> 0
//	%+D           then s.nchr:=rest; s.chradr:=@SBUF.chr(SBUF.nxt);
//	%+D                EdChar(inptrace,'"'); Ed(inptrace,s);
//	%+D                EdChar(inptrace,'"'); ITRC("LongStringPart");
//	%+D           endif;
//	              EmitData(rest,@SBUF.byt(SBUF.nxt));
//	              SBUF.nxt:=SBUF.nxt+rest; InSbuffer; lng.val:=lng.val-rest;
//	           endrepeat;
//	           if lng.val > 0
//	           then
//	%+D             if InputTrace <> 0
//	%+D             then s.nchr:=lng.val; s.chradr:=@SBUF.chr(SBUF.nxt);
//	%+D                  EdChar(inptrace,'"'); Ed(inptrace,s);
//	%+D                  EdChar(inptrace,'"'); ITRC("LongString");
//	%+D             endif;
//	                EmitData(lng.val,@SBUF.byt(SBUF.nxt));
//	                SBUF.nxt:=SBUF.nxt+lng.val;
//	           endif;
	    case Scode.S_C_INT: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           itm.int:=inint; DataSpace(%4%);
//	           if nbyte=1 then Emit1Data(%itm.byt%)
//	           elsif nbyte=2 then Emit2Data(itm.wrd)
//	           elsif nbyte=4 then EmitData(4,@itm) endif;
	    case Scode.S_C_REAL: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           itm.rev:=inreal;  EmitData(4,@itm)
	    case Scode.S_C_LREAL: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           itm.lrv:=inlreal; EmitData(8,@itm)
	    case Scode.S_C_CHAR: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	%+D        itm.byt:=InputByte;
//	%-D        InByte(%itm.byt%);
//	           DataSpace(%1%); Emit1Data(%itm.byt%)
	    case Scode.S_C_SIZE: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           TypeLength:=0; type:=intype;
//	           if type < T_Max
//	           then TypeLength:=TTAB(type).nbyte endif;
//	           Emit2Data(TypeLength);
//	%+E        Emit2Zero;
	    case Scode.S_TRUE: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           DataSpace(%1%); Emit1Data(%255%)
	    case Scode.S_FALSE: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           DataSpace(%1%); Emit1Data(%0%)
	    case Scode.S_C_AADDR: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           InTag(%tag%); atr:=DISPL(tag.HI).elt(tag.LO);
//	           Emit2Data(atr.rela);
//	%+E        Emit2Zero;
	    case Scode.S_C_PADDR: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           InTag(%tag%); lab:=DISPL(tag.HI).elt(tag.LO);
//	           if lab.kind <> K_IntLabel
//	           then addr.kind:=extadr;
//	%-E             addr.sbireg:=0;
//	%+E             addr.sibreg:=NoIBREG;
//	                addr.rela.val:=lab qua ExtDescr.adr.rela;
//	                addr.smbx:=lab qua ExtDescr.adr.smbx;
//	                EmitAddrData(addr);
//	           else EmitAddrData(lab qua IntDescr.adr) endif;
	    case Scode.S_C_RADDR: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           InTag(%tag%); rut:=DISPL(tag.HI).elt(tag.LO);
//	           if rut.kind=K_IntRoutine then addr:=rut qua IntDescr.adr
//	           else addr.kind:=extadr;
//	%-E             addr.sbireg:=0;
//	%+E             addr.sibreg:=NoIBREG;
//	                addr.rela.val:=rut qua ExtDescr.adr.rela;
//	                addr.smbx:=rut qua ExtDescr.adr.smbx;
//	           endif;
//	           addr.rela.val:=addr.rela.val+3; EmitAddrData(addr);
	    case Scode.S_NOSIZE,Scode.S_ANONE: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           Emit2Zero;
//	%+E        Emit2Zero;
	    case Scode.S_NOWHERE,Scode.S_NOBODY,Scode.S_ONONE: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           EmitZero(4);
	    case Scode.S_GNONE: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           EmitZero(6);
//	%+E        Emit2Zero;
	    case Scode.S_C_OADDR: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           InTag(%tag%); d:=DISPL(tag.HI).elt(tag.LO);
//	           if d.kind=K_GlobalVar
//	           then addr:=d qua IntDescr.adr
//	                if addr.kind=0  -- No address attached (Const-spec)
//	                then smbx.val:=0; addr:=NewFixAdr(DSEGID,smbx);
//	                     d qua IntDescr.adr:=addr;
//	                endif;
//	           elsif d.kind=K_ExternVar
//	           then addr.kind:=extadr;
//	%-E             addr.sbireg:=0;
//	%+E             addr.sibreg:=NoIBREG;
//	                addr.rela.val:=d qua ExtDescr.adr.rela;
//	                addr.smbx:=d qua ExtDescr.adr.smbx;
//	           else IERR("MINUT: Illegal tag for C-OADDR") endif;
//	           EmitAddrData(addr);
	    case Scode.S_C_GADDR: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           InTag(%tag%); d:=DISPL(tag.HI).elt(tag.LO);
//	           if d.kind=K_GlobalVar
//	           then addr:=d qua IntDescr.adr
//	                if addr.kind=0  -- No address attached (Const-spec)
//	                then smbx.val:=0; addr:=NewFixAdr(DSEGID,smbx);
//	                     d qua IntDescr.adr:=addr;
//	                endif;
//	           elsif d.kind=K_ExternVar
//	           then addr.kind:=extadr;
//	%-E             addr.sbireg:=0;
//	%+E             addr.sibreg:=NoIBREG;
//	                addr.rela.val:=d qua ExtDescr.adr.rela;
//	                addr.smbx:=d qua ExtDescr.adr.smbx;
//	           else IERR("MINUT: Illegal tag for C-GADDR") endif;
//	%+E        Emit2Zero;
//	           Emit2Zero; EmitAddrData(addr);
	    case Scode.S_C_DOT: Scode.inputInstr(); Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           ofst:=0;
//	           repeat InTag(%tag%); atr:=DISPL(tag.HI).elt(tag.LO);
//	                  ofst:=ofst+atr.rela; InputInstr;
//	           while CurInstr=S_C_DOT do endrepeat;
//	           if CurInstr=S_C_AADDR
//	           then InTag(%tag%); atr:=DISPL(tag.HI).elt(tag.LO);
//	                ofst:=ofst+atr.rela; Emit2Data(ofst);
//	%+E             Emit2Zero;
//	           elsif CurInstr=S_C_GADDR
//	           then InTag(%tag%); d:=DISPL(tag.HI).elt(tag.LO);
//	                if d.kind=K_GlobalVar
//	                then addr:=d qua IntDescr.adr
//	                     if addr.kind=0  -- No address attached (Const-spec)
//	                     then smbx.val:=0; addr:=NewFixAdr(DSEGID,smbx);
//	                          d qua IntDescr.adr:=addr;
//	                     endif;
//	                elsif d.kind=K_ExternVar
//	                then addr.kind:=extadr;
//	%-E                  addr.sbireg:=0;
//	%+E                  addr.sibreg:=NoIBREG;
//	                     addr.rela.val:=d qua ExtDescr.adr.rela;
//	                     addr.smbx:=d qua ExtDescr.adr.smbx;
//	                else IERR("MINUT: Illegal tag for C-GADDR") endif;
//	                Emit2Data(ofst);
//	%+E             Emit2Zero;
//	                EmitAddrData(addr);
//	           else IERR("Illegal termination of C-DOT value") endif;
	    case Scode.S_C_RECORD: Scode.inputInstr();
//	    Util.IERR("Coasm.treatValue: NOT IMPLEMENTED: "+Scode.edInstr(Scode.curinstr));
//	           InTag(%rtag%); EmitStructValue(nbyte);
	    Scode.inTag();
//	    Coasm.emitValue();  ???
	    break;
//	      otherwise goto E endcase;
		default: break LOOP;
//	      goto NXT;
//	E:end;
			}
		}
	}

	
	//	%title ***   S k i p   V a l u e   I t e m   ***
//	%+S Visible routine SkipRepValue;
//	%+S begin infix(WORD) lng,dum;
//	%+S NXT:  InputInstr; case 0:S_max (CurInstr)
//	%+S  when S_TRUE,S_FALSE,S_ONONE,S_GNONE,S_ANONE,
//	%+S       S_NOWHERE,S_NOBODY,S_NOSIZE: -- Nothing
//	%+S  when S_TEXT:
//	%+S       lng.HI:=SBUF.byt(SBUF.nxt); SBUF.nxt:=SBUF.nxt+1;
//	%+S       if SBUF.nxt >= (sBufLen-1) then InSbuffer endif;
//	%+S       lng.LO:=SBUF.byt(SBUF.nxt); SBUF.nxt:=SBUF.nxt+1;
//	%+S       repeat while lng.val<>0
//	%+S       do lng.val:=lng.val-1; SBUF.nxt:=SBUF.nxt+1;
//	%+S          if SBUF.nxt >= sBufLen then InSbuffer endif;
//	%+S       endrepeat;
//	%+S  when S_C_INT,S_C_REAL,S_C_LREAL: InString
//	%+S  when S_C_CHAR:  InputByte
//	%+S  when S_C_SIZE:  intype;
//	%+S  when S_C_AADDR,S_C_PADDR,S_C_RADDR,S_C_OADDR,S_C_GADDR:InTag(%dum%)
//	%+S  when S_C_DOT:
//	%+S       repeat InTag(%dum%); InputInstr
//	%+S       while CurInstr=S_C_DOT do endrepeat;
//	%+S       if CurInstr=S_C_AADDR then InTag(%dum%)
//	%+S       elsif CurInstr=S_C_GADDR then InTag(%dum%)
//	%+S       else IERR("Illegal termination of C-DOT value") endif;
//	%+S  when S_C_RECORD: InTag(%dum%)
//	%+S       repeat InputInstr while CurInstr <> S_ENDRECORD
//	%+S       do if CurInstr=S_ATTR then InTag(%dum%)
//	%+S          else IERR("Syntax error in record-constant") endif;
//	%+S          intype; SkipRepValue;
//	%+S       endrepeat;
//	%+S  otherwise goto E endcase;
//	%+S  goto NXT;
//	%+S E:end;
//	%title
//
//	Routine DefDataFixup;
//	import infix(MemAddr) x; export infix(MemAddr) v;
//	begin infix(Fixup) Fx; range(0:AllignFac) n;
//	%+E   infix(wWORD) ofst32;
//	%+A   infix(WORD) segx,smbx;
//	%+D   if x.kind <> fixadr then IERR("COASM:DefDataFixup") endif;
//	      Fx:=FIXTAB(x.fix.HI).elt(x.fix.LO);
//	%+C   if Fx.Matched
//	%+C   then EdWrd(errmsg,x.fix.val); ed(errmsg,") at line ");
//	%+C        EdInt(errmsg,curline);
//	%+C        ed(errmsg," - Already defined or never referenced");
//	%+C        IERR("Define FIXUP(");
//	%+C   elsif Fx.segid <> DSEGID
//	%+C   then EdSymb(errmsg,Fx.segid);
//	%+C        Ed(errmsg," <> "); EdSymb(errmsg,DSEGID);
//	%+C        IERR(" -- FIXUP Segment Check failed");
//	%+C   endif;
//	%-E   n:=AllignDiff(%DBUF.ofst.val+DBUF.nxt%);
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   n:=AllignDiff(%ofst32.val+DBUF.nxt%);
//	      if n<>0 then EmitZero(n) endif;
//	      v.kind:=segadr;
//	%-E   v.rela.val:=DBUF.ofst.val+DBUF.nxt;
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   v.rela.val:=ofst32.val+DBUF.nxt;
//	%-E   v.sbireg:=0;
//	%+E   v.sibreg:=NoIBREG;
//	      v.segmid:=DSEGID;
//	%+A   smbx:=Fx.smbx;
//	      if Fx.smbx.val > 0
//	      then PutPublic(Fx.smbx,DSEGID,v.rela) endif;
//	%-E   Fx.rela.val:=DBUF.ofst.val+DBUF.nxt;
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   Fx.rela.val:=ofst32.val+DBUF.nxt;
//	      Fx.Matched:=true; FIXTAB(x.fix.HI).elt(x.fix.LO):=Fx;
//	%+A   if asmgen then ListDataLabel(smbx,Fx.fixno)
//	%+AD  elsif listsw>0 then ListDataLabel(smbx,Fx.fixno)
//	%+A   endif;
//	end;
//
//	Visible Routine NewFixAdr;
//	import infix(WORD) segid,smbx; export infix(MemAddr) x;
//	begin infix(Fixup) Fx; Fx.Matched:=false; Fx.smbx:=smbx; Fx.segid:=segid;
//	      x.kind:=fixadr; x.rela.val:=0; x.fix.val:=nFix;
//	%-E   x.sbireg:=0;
//	%+E   x.sibreg:=NoIBREG;
//	%+D   Fx.line:=curline; Fx.fixno:=nFix;
//	      if x.fix.HI >= MxpFix then CAPERR(CapFixs)
//	      elsif FIXTAB(x.fix.HI)=none
//	      then  FIXTAB(x.fix.HI):=NEWOBX(size(FixBlock)) endif;
//	      FIXTAB(x.fix.HI).elt(x.fix.LO):=Fx; nFix:=x.fix.val+1;
//	end;
//	%title ***   E M I T   L I N E   U T I L I T I E S   ***
//	Routine EmitLine;
//	begin ref(Segment) seg; ref(LinePkt) p; range(0:MaxWord) cnt;
//	%+E   infix(wWORD) ofst32;
//	      p:=NEWOBX(size(LinePkt)); p.next:=none; p.line:=curline;
//	%-E   p.rela.val:=CBUF.ofst.val+CBUF.nxt;
//	%+E   ofst32.HighWord.val:=CBUF.ofstHI
//	%+E   ofst32.LowWord.val:=CBUF.ofstLO
//	%+E   p.rela.val:=ofst32.val+CBUF.nxt;
//	      seg:=DICREF(CSEGID); cnt:=seg.lcount;
//	      if cnt=0 then seg.lfirst:=p else seg.llast.next:=p endif;
//	      seg.llast:=p; seg.lcount:=cnt+1;
//	end;
//
//	Routine EmitLineInfo;
//	begin infix(WORD) segx,segid,SavDSEGID; range(0:Maxword) nPkt,SegLng;
//	      infix(MemAddr) SegBase; ref(LinePkt) Pkt;
//	      ref(ModElt) mod; infix(MemAddr) ModInf; ref(SmbElt) smb;
//	      range(0:132) lng,lnx,j; infix(WORD) modx,smbx;
//	      infix(MemAddr) MainInf; -- Start address of MAIN's LineTable
//	%+E   infix(wWORD) ofst32;
//
//	%-E   segx:=CBUF.segx;
//	%+E   segx.val:=CBUF.segx;
//	      if segx.val <> 0
//	      then Segid:=DIC.Segm(segx.HI).elt(segx.LO);
//	%-E        DICREF(Segid) qua Segment.rela.val:=CBUF.ofst.val+CBUF.nxt;
//	%+E        ofst32.HighWord.val:=CBUF.ofstHI
//	%+E        ofst32.LowWord.val:=CBUF.ofstLO
//	%+E        DICREF(Segid) qua Segment.rela.val:=ofst32.val+CBUF.nxt;
//	      endif;
//	      segx.val:=0; SavDSEGID:=DSEGID; DSEGID:=LSEGID; InitData(LSEGID);
//	      if MainEntry.kind <> 0
//	      then smbx.val:=0; modx.val:=0; MainInf:=NewFixadr(LSEGID,smbx);
//	           EmitAddrData(MainInf); Ed(SysEdit,"MAIN");
//	           lng:=SysEdit.Pos; SysEdit.Pos:=0; lnx:=wAllign(%lng+1%)-1;
//	           DataSpace(%lnx+1%); Emit1Data(%lng%);
//	           j:=0; repeat while j < lnx
//	           do Emit1Data(%SysEdit.chr(j) qua integer%); j:=j+1 endrepeat;
//	           repeat while modx.val < DIC.nModl
//	           do modx.val:=modx.val+1;
//	              mod:=DICREF(DIC.Modl(modx.HI).elt(modx.LO));
//	              if mod.LinTab.val <> 0
//	              then ModInf.kind:=extadr; ModInf.rela.val:=0;
//	%-E                ModInf.sbireg:=0;
//	%+E                ModInf.sibreg:=NoIBREG;
//	                   ModInf.smbx:=mod.LinTab; EmitAddrData(ModInf);
//	                   lng:=mod.nchr; lnx:=wAllign(%lng+1%)-1;
//	                   DataSpace(%lnx+1%); Emit1Data(%lng%);
//	                   j:=0; repeat while j < lnx
//	                   do Emit1Data(%mod.chr(j) qua integer%);
//	                      j:=j+1;
//	                   endrepeat;
//	              endif;
//	           endrepeat;
//	           EmitZero(4); DefDataFixup(MainInf);
//	      else DefDataFixup(LtabEntry) endif;
//	      repeat while segx.val < DIC.nSegm
//	      do segx.val:=segx.val+1; segid:=DIC.Segm(segx.HI).elt(segx.LO);
//	         nPkt:=DICREF(segid) qua Segment.lcount;
//	         if nPkt <> 0
//	         then SegBase.kind:=segadr; SegBase.rela.val:=0;
//	%-E           SegBase.sbireg:=0;
//	%+E           SegBase.sibreg:=NoIBREG;
//	              SegBase.segmid:=segid; EmitAddrData(SegBase);
//	              SegLng:=DICREF(segid) qua Segment.rela.val;
//	              Emit2Data(SegLng); Emit2Data(nPkt);
//	              Pkt:=DICREF(segid) qua Segment.lfirst;
//	              repeat while Pkt <> none
//	              do Emit2Data(Pkt.line); Emit2Data(Pkt.rela.val);
//	                 Pkt:=Pkt.next;
//	%+C              nPkt:=nPkt-1;
//	              endrepeat;
//	%+C           if nPkt <> 0 then IERR("COASM.EmitLineInfo") endif;
//	         endif;
//	      endrepeat;
//	      EmitZero(4); DSEGID:=SavDSEGID;
//	end;
//	%title ***   I - I N S T R U C T I O N S   ***
//	Define iA=0,iCX=1,iSP=4,iBP=5,iSI=6,iDI=7,iST=0,iST1=1;
//
//	Define
//	%-E    iSOP    =  38, -- 046oct=00 100 110B -- Segment Override Prefix
//	%+E    iOSP    = 102, -- 146oct=01 100 110B -- Operand Size Prefix
//	       iNOP    = 144, -- 220oct=10 010 000B
//	       iMOV    = 136, -- 210oct=10 001 000B
//	       iMOVI   = 176, -- 260oct=10 110 000B
//	       iMOVA   = 160, -- 240oct=10 100 000B
//	       iMOVMI  = 198, -- 306oct=11 000 110B
//	%-E    iMOVSM  = 142, -- 216oct=10 001 110B
//	%-E    iMOVMS  = 140, -- 214oct=10 001 100B
//	       iPUSH   =  80, -- 120oct=01 010 000B
//	%-E    iPUSHS  =   6, -- 006oct=00 000 110B
//	       iPUSHC  = 104, -- 150oct=01 101 000B  -- iAPX186 or later
//	       iPUSHF  = 156, -- 234oct=10 011 100B
//	       iGRP2   = 254, -- 376oct=11 111 110B
//	%+E                   iCALLI  = 2, -- 2oct=010B
//	                      iCALLFI = 3, -- 3oct=011B
//	                      iJMPI   = 4, -- 4oct=100B
//	                      iJMPFI  = 5, -- 5oct=101B
//	                      iPUSHM  = 6, -- 6oct=110B
//	       iGRP1   = 246, -- 366oct=11 110 110B
//	       iPOP    =  88, -- 130oct=01 011 000B
//	       iPOPM   = 143, -- 217oct=10 001 111B
//	%-E    iPOPS   =   7, -- 007oct=00 000 111B
//	       iPOPF   = 157, -- 235oct=10 011 101B
//	       iXCHG   = 134, -- 206oct=10 000 110B
//	       iXCHGA  = 144, -- 220oct=10 010 000B
//	       iLEA    = 141, -- 215oct=10 001 101B
//	%-E    iLDS    = 197, -- 305oct=11 000 101B
//	%-E    iLES    = 196, -- 304oct=11 000 100B
//	       iLAHF   = 159, -- 237oct=10 011 111B
//	       iSAHF   = 158, -- 236oct=10 011 110B
//	       iDYAD   =   2, -- 002oct=00 000 010B
//	                      iADD = 0, -- 0oct=000B
//	                      iOR  = 1, -- 1oct=001B
//	                      iADC = 2, -- 2oct=010B
//	                      iSBB = 3, -- 3oct=011B
//	                      iAND = 4, -- 4oct=100B
//	                      iSUB = 5, -- 5oct=101B
//	                      iXOR = 6, -- 6oct=110B
//	                      iCMP = 7, -- 7oct=111B
//	       iDYADI  = 128, -- 200oct=10 000 000B
//	       iDYADA  =   4, -- 004oct=00 000 100B
//	       iTEST   = 133, -- 205oct=10 000 101B
//	       iINC    =  64, -- 100oct=01 000 000B
//	       iDEC    =  72, -- 110oct=01 001 000B
//	       iSHIFT1 = 208, -- 320oct=11 010 000B
//	       iSHIFT  = 210, -- 322oct=11 010 010B
//	                      iSHL = 4, -- 4oct=100B
//	                      iSHR = 5, -- 5oct=101B
//	                      iSAR = 7, -- 7oct=111B
//	       iCBW    = 152, -- 230oct=10 011 000B
//	       iCWD    = 153, -- 231oct=10 011 001B
//	       iREP    = 243, -- 363oct=11 110 011B
//	       iREPNE  = 242, -- 362oct=11 110 010B
//	       iSTR    = 160, -- 240oct=10 100 000B
//	       iCALL   = 232, -- 350oct=11 101 000B
//	       iCALLF  = 154, -- 232oct=10 011 010B
//	       iJMP    = 233, -- 351oct=11 101 001B
//	       iJMPS   = 235, -- 353oct=11 101 011B
//	       iJMPF   = 234, -- 352oct=11 101 010B
//	       iENTER  = 200, -- 310oct=11 001 000B  -- iAPX186 or later
//	       iLEAVE  = 201, -- 311oct=11 001 001B  -- iAPX186 or later
//	       iRET    = 194, -- 302oct=11 000 010B
//	       iRET0   = 195, -- 303oct=11 000 011B  -- no parameters
//	       iRETF   = 202, -- 312oct=11 001 010B
//	       iRETF0  = 203, -- 313oct=11 001 011B  -- no parameters
//	       iINTO   = 206, -- 316oct=11 001 110B
//	       iINT    = 205, -- 315oct=11 001 101B
//	       iBRK    = 204, -- 314oct=11 001 100B
//	       iIRET   = 207, -- 317oct=11 001 111B
//	       iLOOP   = 226, -- 342oct=11 100 010B
//	       iJCOND  = 112, -- 160oct=01 110 000B
//	                      iJE  =  4, -- 04oct=0100B
//	                      iJL  = 12, -- 14oct=1100B
//	                      iJLE = 14, -- 16oct=1110B
//	                      iJB  =  2, -- 02oct=0010B
//	                      iJBE =  6, -- 06oct=0110B
//	                      iJNE =  5, -- 05oct=0101B
//	                      iJGE = 13, -- 15oct=1101B
//	                      iJG  = 15, -- 17oct=1111B
//	                      iJAE =  3, -- 03oct=0011B
//	                      iJA  =  7, -- 07oct=0111B
//	%+E    iGRP3   =  15, --  17oct=00 001 111B  -- iAPX386 or later
//	%+E    iJMPC   = 128, -- 200oct=10 000 000B  -- iAPX386 or later
//	%+E    iSETB   = 144, -- 220oct=10 010 000B  -- iAPX386 or later
//	%+E    iMOVSX  = 190, -- 276oct=10 111 110B  -- iAPX386 or later
//	%+E    iMOVZX  = 182, -- 266oct=10 110 110B  -- iAPX386 or later
//	       iCLD    = 252, -- 374oct=11 111 100B
//	       iSTD    = 253, -- 375oct=11 111 101B
//
//	       iFCHS   = 224, -- 340oct=11 100 000B
//	       iFLDC   = 232, -- 350oct=11 101 000B
//	       iFSQRT  = 250, -- 372oct=11 111 010B
//	       iFABS   = 225, -- 341oct=11 100 001B
//	       iFRND   = 252, -- 374oct=11 111 100B
//	       iFPREM  = 248, -- 370oct=11 111 000B
//	       iFFREE  = 192, -- 300oct=11 000 000B
//	       iESC    = 216, -- 330oct=11 011 000B
//	       iESC1   = 217, -- 331oct=11 011 001B
//	                      iFLD    = 0,   -- 000B
//	                      iFLDT   = 5,   -- 101B
//	                      iFST    = 2,   -- 010B
//	                      iFSTP   = 3,   -- 011B
//	                      iFSTPT  = 7,   -- 111B
//	                      iFADD   = 0,   -- 000B
//	                      iFSUB   = 4,   -- 100B
//	                      iFSUBR  = 5,   -- 101B
//	                      iFMUL   = 1,   -- 001B
//	                      iFDIV   = 6,   -- 110B
//	                      iFDIVR  = 7,   -- 111B
//	                      iFCOMP  = 3,   -- 011B
//	       iWAIT   = 155; -- 233oct=10 011 011B
//	%title ***   W T L x 1 6 7 - I N S T R U C T I O N S   ***
//
//	%+E Define
//	%+E    wsADD   =     0,    wdADD   = 32768, -- 0000H  8000H -- 
//	%+E    wsLOAD  =  1024,    wdLOAD  = 33792, -- 0400H  8400H -- 
//	%+E    wsMUL   =  2048,    wdMUL   = 34816, -- 0800H  8800H -- 
//	%+E    wsSTOR  =  3072,    wdSTOR  = 35840, -- 0C00H  8C00H -- 
//	%+E
//	%+E    wsSUB   =  4096,    wdSUB   = 36864, -- 1000H  9000H -- 
//	%+E    wsDIV   =  5120,    wdDIV   = 37888, -- 1400H  9400H -- 
//	%+E    wsMULN  =  6144,    wdMULN  = 38912, -- 1800H  9800H -- 
//	%+E    wsFLOAT =  7168,    wdFLOAT = 39936, -- 1C00H  9C00H -- 
//	%+E
//	%+E    wsCMPT  =  8192,    wdCMPT  = 40960, -- 2000H  A000H -- 
//	%+E    wsTSTT  =  9216,    wdTSTT  = 41984, -- 2400H  A400H -- 
//	%+E    wsNEG   = 10240,    wdNEG   = 43008, -- 2800H  A800H -- 
//	%+E    wsABS   = 11264,    wdABS   = 44032, -- 2C00H  AC00H -- 
//	%+E
//	%+E    wsCMP   = 12288,    wdCMP   = 45056, -- 3000H  B000H -- 
//	%+E    wsTST   = 13312,    wdTST   = 46080, -- 3400H  B400H -- 
//	%+E    wsAMUL  = 14336,    wdAMUL  = 47104, -- 3800H  B800H -- 
//	%+E    wsFIX   = 15360,    wdFIX   = 48128, -- 3C00H  BC00H -- 
//	%+E
//	%+E    wdCVTS  = 16384,    wsLDCTX = 49152, -- 4000H  C000H -- 
//	%+E    wsCVTD  = 17408,    wsSTCTX = 50176, -- 4400H  C400H -- 
//	%+E    wsMAC   = 18432,    wsMACD  = 51200, -- 4800H  C800H -- 
//	%+E    wsSQRT  = 19456,    wdSQRT  = 52224, -- 4C00H  CC00H -- NOT 1167
//	%+E
//	%+E    wdMACD  = 20480,    wdBMOV  = 53248, -- 5000H  D000H -- NOT 1167
//	%+E    wsRSUB  = 21504,    wdRSUB  = 54272; -- 5400H  D400H -- NOT 1167
//	%title ***   I - C o d e    S i z e   ***
//	%-E Routine CountLEA;
//	--- import infix(MemAddr) opr; export range(0:MaxByte) lng;
//	%-E import ref(Qfrm3) qi;      export range(0:MaxByte) lng;
//	%-E begin infix(wWORD) Ofst; infix(memaddr) opr;
//	%-E       opr:=qi.opr; Ofst:=opr.rela;
//	%-E       if opr.kind = locadr then Ofst.val:=Ofst.val-opr.loca;
//	%-E       elsif opr.kind<>reladr then lng:=3; goto E endif;
//	%-E       if bAND(opr.sbireg,rmBIREG)=0 then lng:=3;
//	%-E       elsif Ofst.val=0
//	%-E       then if bAND(opr.sbireg,7)=6 then lng:=2 else lng:=1 endif
//	%-E       elsif Ofst.HI=BOOL2BYTE(Ofst.LO>127)
//	%-E       then lng:=2 else lng:=3 endif;
//	%-E E:end;
//
//	Routine CountEA;
//	import infix(MemAddr) opr; export range(0:MaxByte) lng;
//	begin infix(wWORD) Ofst;
//	%+E   infix(wWORD) ofx; range(0:nregs) rm,ir;
//	      Ofst:=opr.rela;
//	      if opr.kind = locadr then Ofst.val:=Ofst.val-opr.loca;
//	%-E   elsif opr.kind<>reladr then lng:=3; goto E endif;
//	%+E   endif;
//
//	%-E   if bAND(opr.sbireg,rmBIREG)=0 then lng:=3;
//	%-E   elsif Ofst.val=0
//	%-E   then if bAND(opr.sbireg,7)=6 then lng:=2 else lng:=1 endif
//	%-E   elsif Ofst.HI=BOOL2BYTE(Ofst.LO>127)
//	%-E   then lng:=2 else lng:=3 endif;
//
//	%+E   if opr.sibreg=NoIBREG then lng:=5;
//	%+E   else rm:=bAND(opr.sibreg,BaseREG); ir:=bAND(opr.sibreg,IndxREG);
//	%+E        if (rm<>bESP) and (rm=bSHR(ir,3))
//	%+E        then lng:=5; rm:=bESP; -- No Base, Scaled Index only --
//	%+E        elsif Ofst.val <> 0
//	%+E        then ofx:=Ofst;
//	%+E             if ofx.LO < 128 then ofx.LO:=0
//	%+E             else ofx.LO:=255; ofx.val:=ofx.val+1 endif;
//	%+E             if ofx.val=0 then lng:=2 else lng:=5 endif;
//	%+E        elsif rm=bEBP then lng:=2 else lng:=1 endif;
//	%+E        if ir <> iESP then lng:=lng+1;         --- Scaled Index
//	%+E        elsif rm=bESP then lng:=lng+1 endif;   --- Special Case
//	%+E   endif;
//	    
//	%-E E:if OverrideSreg(opr.sbireg) then lng:=lng+1 endif;
//	end;
//
//	%-D Macro CountModifySP(1);
//	%-D begin
//	%-D %-E   2+CountConstTail( qSP,%1);
//	%-D %+E   2+CountConstTail(qESP,%1);
//	%-D endmacro;
//
//	%+D Routine CountModifySP;
//	%+D import infix(wWORD) cnst; export range(0:MaxByte) isize;
//	%+D begin
//	%+D %-E   isize:=2+CountConstTail( qSP,cnst);
//	%+D %+E   isize:=2+CountConstTail(qESP,cnst);
//	%+D end;
//
//	Routine CountLoadCnst;
//	import ref(Qfrm2) qi; -- range(0:nregs) qreg; infix(wWORD) cnst;
//	export range(0:MaxByte) isize;
//	begin if qi.reg<qAX then isize:=2
//	      elsif qi.aux.val <> 0 then isize:=1+AllignFac;
//	      elsif RegDies(qi,uF) then isize:=2
//	      else isize:=1+AllignFac endif;
//	end;
//
//	Routine CountConstTail;
//	import range(0:nregs) qreg; infix(wWORD) cnst;
//	export range(0:MaxByte) isize;
//	begin if qreg < qAX then isize:=1              -- Byte Value
//	      elsif SignExt(cnst) > 0 then isize:=1    -- Sign-extendable
//	      else
//	%-E        isize:=2;                           -- 2-Byte Value
//	%+E        if bAND(qreg,qw_W)<>0 then isize:=2 -- 2-Byte Value
//	%+E        else isize:=4 endif;                -- 4-Byte Value
//	      endif;
//	end;
//	%title ***   D e c i d e    S h o r t    J M P   ***
//
//	Routine ShrtJMP;
//	import
//	%-E    range(0:MaxWord) c; ref(Qfrm5) jmp;
//	%+E    integer          c; ref(Qfrm5) jmp;
//	export Boolean shrt;
//	begin ref(Qpkt) dst,qi; range(0:MaxWord) count; range(0:255) mode;
//	      range(0:MaxByte) isize;
//	      range(0:nregs) qreg;  -- Q-Code registers
//	      range(0:1) w;         -- I-Code memory operand width
//	      range(0:20) subc;
//	      infix(wWORD) cnst;
//	      range(0:MaxWord) ofst,i;
//	      infix(MemAddr) opr;
//	%+D   RST(R_ShrtJMP);
//	%+D   CheckJMP(jmp);
//	%+D   if listsw > 1
//	%+D   then printout(sysout); outstring("*** ShortJMP: ");
//	%+D        outint(c); outstring(" -- ");
//	%+D        edref(sysout,jmp); printout(sysout);
//	%+D   endif;
//	      dst:=jmp.dst; count:=2; qi:=jmp;
//	%+D   if dst=none          then IERR("ShrtJMP-x1") endif; 
//	%+D   if dst=jmp           then IERR("ShrtJMP-x2") endif; 
//	%+D   if dst.fnc <> qFDEST then IERR("ShrtJMP-x3") endif; 
//	      -- FJUMP to Generated FDEST --
//	      repeat qi:=qi.next while qi <> dst
//	      do if qi=none
//	         then IERR("ShrtJMP-x"); goto L1 endif;
//	         isize:=qi.isize; if isize <> 0 then goto L2 endif;
//	         subc:=qi.subc;
//	%+D      RST(R_iCodeSize);
//	         case 0:qMXX (qi.fnc);
//	%+E      when qCWD: -------- CWD    width ------------------  Format 1
//	%+E           isize:=1;
//	%+E           if bAND(subc,qw_W)<>0 then isize:=2 endif;
//	         when qPUSHR, ------ PUSHR   reg -------------------  Format 1
//	              qPOPR, ------- POPR    reg -------------------  Format 1
//	%-E           qCWD, -------- CWD ---------------------------  Format 1
//	              qLAHF, ------- LAHF --------------------------  Format 1
//	              qSAHF, ------- SAHF --------------------------  Format 1
//	              qIRET, ------- IRET --------------------------  Format 1
//	              qTSTOFL, ----- INTO --------------------------  Format 1
//	              qWAIT: ------- WAIT --------------------------  Format 1
//	              isize:=1;
//	%+E      when qMOV: -------- MOV     subc reg reg2 ---------  Format 2
//	%+E           if subc<>0 then isize:=3
//	%+E           else isize:=2;
//	%+E                if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	%+E           endif;
//	         when qSHIFT, ------ SHIFT   subc reg CL -----------  Format 2
//	%-E           qMOV, -------- MOV     reg reg2 --------------  Format 2
//	              qINT: -------- INT     const -----------------  Format 2
//	              isize:=2;
//	         when qCONDEC: ----- CONDEC  subc reg --------------  Format 2
//	              isize:=3;
//	%-E      when qLOADSC: ----- LOADSC  sreg reg fld addr -----  Format 2b
//	%-E           isize:=5;
//	         when qDOS2: ------- DOS2 --------------------------  Format 1
//	              isize:=8;
//	         when qENTER, ------ ENTER   const -----------------  Format 2
//	              qLEAVE, ------ LEAVE   const -----------------  Format 2
//	              qRET: -------- RET     const -----------------  Format 2
//	              isize:=20; -- Doesn't matter
//	         when qPUSHC: ------ PUSHC   reg const -------------  Format 2
//	                      ------ PUSHC   reg fld addr ----------  Format 2b
//	              if qi.kind = K_Qfrm2
//	              then cnst:=qi qua Qfrm2.aux;
//	%-E                if CPUID<iAPX186
//	%-E                then isize:=1+CountLoadCnst(qi) -- ,qi.reg,cnst)
//	%-E                else
//	                        qreg:=qi.reg;
//	%+E                     if qreg>=qw_W then qreg:=qEAX endif;
//	                        isize:=1+CountConstTail(qreg,cnst);
//	%-E                endif;
//	              else 
//	%-E                if CPUID<iAPX186 then isize:=4
//	%-E                else
//	                        isize:=1+AllignFac;
//	%-E                endif;
//	              endif;
//	         when qPUSHA: ------ PUSHA   reg opr ---------------  Format 3
//	%-E           isize:=2+CountLEA(qi qua Qfrm3) -- .opr);
//	%+E           isize:=2+CountEA(qi qua Qfrm3.opr);
//	         when qPUSHM: ------ PUSHM   const opr -------------  Format 4
//	              cnst.val:=wAllign(%qi qua Qfrm4.aux.val%);
//	              opr:=qi qua Qfrm4.opr; ofst:=opr.rela.val; isize:=0;
//	              repeat while cnst.val>0
//	              do cnst.val:=cnst.val-AllignFac;
//	                 opr.rela.val:=ofst+cnst.val;
//	                 isize:=isize+1+CountEA(opr);
//	              endrepeat;
//	         when qPOPK: ------- POPK    const -----------------  Format 2
//	              cnst.val:=wAllign(%qi qua Qfrm2.aux.val%);
//	              isize:=CountModifySP(cnst);
//	         when qPOPM: ------- POPM    reg const opr ---------  Format 4
//	              cnst:=qi qua Qfrm4.aux; opr:=qi qua Qfrm4.opr;
//	              ofst:=opr.rela.val; i:=0; isize:=0; 
//	              repeat while cnst.val>0
//	              do opr.rela.val:=ofst+i;
//	                 if cnst.val >= AllignFac
//	                 then isize:=isize+1; cnst.val:=cnst.val-AllignFac;
//	%+E              elsif cnst.val=2 then isize:=isize+3; cnst.val:=0;
//	                 else isize:=isize+2; cnst.val:=0; endif;
//	                 isize:=isize+CountEA(opr); i:=i+AllignFac;
//	              endrepeat;
//	         when qLOADC: ------ LOADC   reg const -------------  Format 2
//	                      ------ LOADC   reg fld addr ----------  Format 2b
//	              if qi.kind = K_Qfrm2
//	              then isize:=CountLoadCnst(qi) -- ,qi.reg,qi qua Qfrm2.aux);
//	              else isize:=1+AllignFac endif;
//	         when qLOADA: ------ LOADA   reg opr ---------------  Format 3
//	%-E           isize:=1+CountLEA(qi qua Qfrm3) -- .opr);
//	%+E           isize:=1+CountEA(qi qua Qfrm3.opr);
//	%-E      when qLDS, -------- LDS     reg ofst opr nrep -----  Format 4c
//	%-E           qLES: -------- LES     reg ofst opr nrep -----  Format 4c
//	%-E           isize:=1+CountEA(qi qua Qfrm3.opr)+(5*qi qua Qfrm4c.nrep);
//	%+E      when qBOUND: ------ BOUND   reg opr ---------------  Format 3
//	%+E           opr:=qi qua Qfrm3.opr; qreg:=qi.reg; isize:=CountEA(opr)+1;
//	%+E           if opr.sibreg <> NoIBREG then isize:=isize+1 endif;
//	%+E           if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	         when qLOAD, ------- LOAD    subc reg ofst opr nrep   Format 4c
//	              qSTORE: ------ STORE   reg opr ---------------  Format 3
//	              opr:=qi qua Qfrm3.opr; qreg:=qi.reg; isize:=CountEA(opr);
//	%-E           if qreg>=qES then isize:=isize+1
//	%-E           elsif bAND(opr.sbireg,rmBIREG) <> 0 then isize:=isize+1
//	%+E           if subc <> 0 then isize:=isize+1
//	%+E           elsif opr.sibreg <> NoIBREG then isize:=isize+1
//	              elsif iReg(%qreg%) <> iA then isize:=isize+1 endif;
//	%+E           if subc <> 0 then isize:=isize+1
//	%+E           elsif bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	%+E           if qi.fnc=qLOAD then isize:=isize+(6*qi qua Qfrm4c.nrep) endif;
//	         when qMOVMC: ------ MOVMC   width const opr -------  Format 4
//	                      ------ MOVMC   width fld opr addr ----  Format 4b
//	              opr:= qi qua Qfrm4.opr;
//	-- ?????      if qi.kind = K_Qfrm4
//	-- ?????      then
//	                   isize:=1+CountEA(opr)+RegSize(qi.reg);
//	-- ?????      else isize:=(1+AllignFac)+CountEA(opr) endif;
//	%+E           if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	         when qXCHG: ------- XCHG    reg reg2 --------------  Format 2
//	              qreg:=qi.reg;
//	              if qi.reg < qAX then isize:=2
//	              elsif iReg(%qreg%)=iA then isize:=1
//	              elsif iReg(%qi qua Qfrm2.aux.val%)=iA then isize:=1
//	              else isize:=2 endif;
//	         when qXCHGM: ------ XCHGM   reg opr ---------------  Format 3
//	              isize:=1+CountEA(qi qua Qfrm3.opr);
//	%+E           if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	         when qMONADR: ----- MONADR  subc reg --------------  Format 2
//	              if subc>8
//	              then subc:=subc-7; if TSTOFL then isize:=1 endif endif;
//	              if subc=qINC then goto ID6 elsif subc=qDEC
//	              then ID6: qreg:=qi.reg; w:=wBIT(qreg);
//	                   if (w=0) and (iReg(%qreg%)<4)  -- 4=100B
//	                   then if RegDies(qi,uMask(HighPartL(qreg)))
//	                        then w:=1 endif endif;
//	                   if w=1 then isize:=isize+1 else isize:=isize+2 endif;
//	              else isize:=isize+2 endif; -- NOT/NEG/NEGM/SHL/SHR/SAR
//	         when qMONADM: ----- MONADM  subc width opr --------  Format 3
//	              isize:=1+CountEA(qi qua Qfrm3.opr);
//	              if subc>8 then if TSTOFL then isize:=isize+1 endif endif;
//	%+E           if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	         when qDYADR: ------ DYADR   subc reg reg2 ---------  Format 2
//	%-E           if    subc=qDECO then isize:=6
//	%-E           elsif subc=qINCO then isize:=7
//	%-E           else
//	                   isize:=2;
//	%-E           endif;
//	              if subc<qADDF then elsif TSTOFL then isize:=isize+1 endif;
//	%+E           if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	         when qDYADC: ------ DYADC   subc reg const --------  Format 2
//	                      ------ DYADC   subc reg fld addr -----  Format 2b
//	              qreg:=qi.reg;
//	              if iReg(%qreg%)=iA
//	              then cnst.val:=128; isize:=1+CountConstTail(qreg,cnst)
//	%+E                if bAND(qreg,qw_W)<>0 then isize:=isize+1 endif;
//	              else if qi.kind=K_Qfrm2b then cnst.val:=128
//	                   else cnst:=qi qua Qfrm2.aux endif;
//	%-E                if (qreg=qSP)  and (cnst.val<=(2*AllignFac))
//	%+E                if (qreg=qESP) and (cnst.val<=(2*AllignFac))
//	                      and ((subc=qADD) or (subc=qSUB))
//	                   then isize:=CountModifySP(cnst)
//	                   else isize:=2+CountConstTail(qreg,cnst);
//	%+E                     if bAND(qreg,qw_W)<>0 then isize:=isize+1 endif;
//	                   endif;
//	              endif;
//	%-E           if    subc=qINCO then isize:=isize+5
//	%-E           elsif subc=qDECO then isize:=isize+4 endif;
//	              if subc<qADDF then elsif TSTOFL then isize:=isize+1 endif;
//	         when qDYADM: ------ DYADM   subc reg opr ----------  Format 3
//	              isize:=1+CountEA(qi qua Qfrm3.opr);
//	%-E           if subc=qDECO then isize:=isize+4;
//	%-E           elsif subc=qINCO then isize:=isize+5 endif;
//	              if subc<qADDF then elsif TSTOFL then isize:=isize+1 endif;
//	%+E           if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	         when qDYADMC: -- DYADMC  subc width const opr -----  Format 4
//	                       -- DYADMC  subc width fld opr addr --  Format 4b
//	              if qi.kind=K_Qfrm4b then cnst.val:=128
//	              else cnst:= qi qua Qfrm4.aux endif;
//	              isize:=1+CountEA(qi qua Qfrm4.opr);
//	              if qi.reg < qAX then isize:=isize+1
//	              elsif SignExt(cnst) > 0 then isize:=isize+1
//	              else isize:=isize+RegSize(qi.reg) endif;
//	              if subc<qADDF then elsif TSTOFL then isize:=isize+1 endif;
//	%+E           if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	         when qDYADMR: ----- DYADMR  subc reg opr ----------  Format 3
//	              isize:=1+CountEA(qi qua Qfrm3.opr);
//	              if subc<qADDF then elsif TSTOFL then isize:=isize+1 endif;
//	%+E           if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	         when qTRIADR: ----- TRIADR  subc reg --------------  Format 2
//	              isize:=2; subc:=bAND(subc,11); -- 11=1011B
//	              if subc=qi.subc then elsif TSTOFL then isize:=isize+1 endif;
//	         when qTRIADM: ----- TRIADM  subc width opr --------  Format 3
//	              isize:=1+CountEA(qi qua Qfrm3.opr);
//	              subc:=bAND(subc,11); -- 11=1011B
//	              if subc=qi.subc then elsif TSTOFL then isize:=isize+1 endif;
//	%+E           if bAND(qi.reg,qw_W)<>0 then isize:=isize+1 endif;
//	         when qRSTRB: ------ RSTRB   subc dir rep ----------  Format 2
//	              case 0:6 (subc)
//	--- pje       when qZERO: isize:=10  when qRCMPS:isize:=8
//	--- pje       when qRSCAS:isize:=6   otherwise isize:=3 endcase;
//	              when qZERO: isize:=10; -- NOTE STD generated always:
//	                   if qi.reg<>qCLD then isize:=isize-1 endif;
//	              when qRCMPS:isize:=7
//	              when qRSCAS:isize:=5   otherwise isize:=2 endcase;
//	              if qi.reg <> qCLD then isize:=isize+2 endif; -- STD - CLD
//	         when qRSTRW: ------ RSTRW   subc dir rep ----------  Format 2
//	              case 0:6 (subc)
//	--- pje       when qZERO: isize:=12  when qRCMPS:isize:=8
//	--- pje       when qZERO: isize:=14  when qRCMPS:isize:=8
//	--- pje       when qRSCAS:isize:=6   otherwise isize:=3 endcase;
//	%-E           when qZERO: isize:=12; -- NOTE STD generated always:
//	%+E           when qZERO: isize:=14; -- NOTE STD generated always:
//	                   if qi.reg<>qCLD then isize:=isize-1 endif;
//	              when qRCMPS:isize:=7
//	              when qRSCAS:isize:=6   otherwise isize:=2 endcase;
//	              if qi.reg <> qCLD then isize:=isize+2 endif; -- STD - CLD
//	---      when qBDEST: ------ BDEST   labno rela ------------  Format 6
//
//	         when qJMP: -------- JMP     subc addr dst ---------  Format 5
//	%-E           if AdrSegid(qi qua Qfrm5.addr)=CSEGID.val
//	%-E           then isize:=3 else isize:=5 endif;
//	%-E           if subc<>0 then isize:=isize+2 endif;
//	%+E           isize:=5;
//	%+E           if subc<>0 then isize:=isize+1 endif;
//	              goto L3;
//	         when
//	%-E           qJMPFM, ------ JMPFM   opr -------------------  Format 3
//	              qJMPM: ------- JMPM    opr -------------------  Format 3
//	              isize:=1+CountEA(qi qua Qfrm3.opr); 
//	         when qCALL: ------- CALL    subc pxlng addr -------  Format 5
//	%-E           if AdrSegid(qi qua Qfrm5.addr)=CSEGID.val
//	%-E           then isize:=3 else isize:=5 endif; -- Near/Far  Call
//	%+E           if qi qua Qfrm5.addr.kind=reladr
//	%+E           then isize:=2  -- Special Case: CALL [reg]
//	%+E           else isize:=5 endif; -- Near  Call
//	              if subc = qOS2 then isize:=isize+4
//	              elsif subc = qXNX
//	              then if qi qua Qfrm5.aux <> 0
//	%-E                then isize:=isize+12 else isize:=isize+9 endif;
//	%-E                if CSEGID<>EnvCSEG then isize:=isize+2 endif;
//	%+E                then isize:=isize+12 else isize:=isize+9 endif;
//	              elsif subc = qKNL
//	              then if qi qua Qfrm5.aux <> 0
//	%-E                then isize:=isize+12 else isize:=isize+9 endif;
//	%+E                then isize:=isize+12 else isize:=isize+9 endif;
//	              elsif subc = qC
//	              then if qi qua Qfrm5.aux <> 0
//	                   then isize:=isize+5 else isize:=isize+2 endif;
//	              endif;
//	         when qFPUSH: ------ FPUSH   fSD fmf ---------------  Format 1
//	%+E           if NUMID=WTLx167 then isize:=26  -- Should be enough (pje: 24->26)
//	%+E           else
//	                   isize:=12;
//	%-E                if NUMID < iAPX287 then isize:=isize+1 endif;
//	                   isize:=isize+1; --- P.G.A. Explicit Term WAIT -- TEMP
//	%+E           endif;
//	         when qFPOP: ------- FPOP    fSD fmf ---------------  Format 1
//	%+E           if NUMID=WTLx167 then isize:=26  -- Should be enough (pje: 24->26)
//	%+E           else
//	                   isize:=12;
//	%-E                if NUMID < iAPX287 then isize:=isize+1 endif;
//	%+E           endif;
//	%-E      when qFLDC: ------- FLDC    sreg fmf val ----------  Format 3+
//	%+E      when qFLDC: ------- FLDC    fSD  fmf val ----------  Format 3+
//	%-E           isize:=5; if NUMID < iAPX287 then isize:=isize+1 endif;
//	%+E           if NUMID=WTLx167 then isize:=22  -- Should be enough
//	%+E           else isize:=6 endif;
//	         when qFLD: -------- FLD     fSD fmf opr -----------  Format 3
//	%+E           if NUMID=WTLx167 then isize:=26  -- Should be enough (pje: 24->26)
//	%+E           else
//	                   isize:=1+CountEA(qi qua Qfrm3.opr);
//	%-E                if NUMID < iAPX287 then isize:=isize+1 endif;
//	%+E           endif;
//	         when qFST, -------- FST     fSD fmf opr -----------  Format 3
//	              qFSTP: ------- FSTP    fSD fmf opr -----------  Format 3
//	%+E           if NUMID=WTLx167 then isize:=26  -- Should be enough (pje: 24->26)
//	%+E           else
//	                   isize:=1+CountEA(qi qua Qfrm3.opr);
//	                   isize:=isize+1; --- P.G.A. Explicit Term WAIT -- TEMP
//	%-E                if NUMID < iAPX287 then isize:=isize+1 endif;
//	%+E           endif;
//	         when qFDUP, ------- FDUP    fSD -------------------  Format 1
//	              qFLDCK, ------ FLDCK   subc ------------------  Format 1
//	              qFMONAD: ----- FMONAD  subc fSD --------------  Format 1
//	%-E           if NUMID < iAPX287 then isize:=3 else isize:=2 endif;
//	%+E           if NUMID=WTLx167
//	%+E           then isize:=10  -- Should be enough
//	%+E           else isize:=2 endif;
//	         when qFDYAD: ------ FDYAD   subc fSD --------------  Format 1
//	%+E           if NUMID=WTLx167 then isize:=15  -- Should be enough
//	%+E           else
//	                   isize:=2; goto LFDYADM;
//	%+E           endif;
//	         when qFDYADM: ----- FDYADM  subc fmf fSD opr ------  Format 4
//	%+E           if NUMID=WTLx167 then isize:=20  -- Should be enough
//	%+E           else
//	                   isize:=1+CountEA(qi qua Qfrm3.opr);
//	%+E           endif;
//	     LFDYADM:
//	%-E           if NUMID < iAPX287
//	%-E           then if subc = qFCOM
//	%-E                then isize:=isize+13 else isize:=isize+1 endif;
//	%-E           elsif subc = qFCOM then isize:=isize+3 endif;
//	%+E           if subc = qFCOM then isize:=isize+3 endif;
//	         when qLINE: ------- LINE    subc const ------------  Format 2
//	              isize:=0;
//	%+D           if DEBMOD > 2 then if subc=qSTM then isize:=2 endif endif;
//	%-D           if DEBMOD > 2 then                   isize:=2       endif;
//	         otherwise isize:=0 endcase;
//	         qi.isize:=isize;
//
//	  L2:L3: count:=count+isize;
//	         if count>=130 then L1: shrt:=false; goto E1 endif;
//	      endrepeat;
//	      shrt:=true;
//	E1:
//	%+D   if listsw > 1
//	%+D   then outstring("*** End   ShortJMP: "); edref(sysout,jmp);
//	%+D        outstring(" Result:"); EdBool(sysout,shrt); printout(sysout)
//	%+D   endif;
//	end;
//	%title ***   E M I T   C O D E   U T I L I T I E S   ***
//	-- CodeCounter == CBUF.ofst+CBUF.nxt
//
//	Routine InitCode; import infix(WORD) NewSegid;
//	begin infix(WORD) OldSegid,NewSegx,OldSegx;
//	%+E   infix(wWORD) ofst32;
//	      NewSegx:=PutSegx(NewSegid);
//	%-E   OldSegx:=CBUF.segx
//	%+E   OldSegx.val:=CBUF.segx
//	      if OldSegx <> NewSegx
//	      then if OldSegx.val <> 0
//	           then OldSegid:=DIC.Segm(OldSegx.HI).elt(OldSegx.LO);
//	                OutCbuffer;
//	%-E             DICREF(OldSegid) qua Segment.rela.LowWord:=CBUF.ofst;
//	%+E             ofst32.HighWord.val:=CBUF.ofstHI
//	%+E             ofst32.LowWord.val:=CBUF.ofstLO
//	%+E             DICREF(OldSegid) qua Segment.rela:=ofst32;
//	           endif;
//	%-E        CBUF.segx:=NewSegx;
//	%+E        CBUF.segx:=NewSegx.val;
//	           if NewSegx.val <> 0
//	           then
//	%-E             CBUF.ofst:=DICREF(NewSegid) qua Segment.rela.LowWord;
//	%+E             ofst32:=DICREF(NewSegid) qua Segment.rela;
//	%+E             CBUF.ofstHI:=ofst32.HighWord.val;
//	%+E             CBUF.ofstLO:=ofst32.LowWord.val;
//	                CBUF.hed:=2 qua character; CREL.hed:=3 qua character;
//	           endif;
//	      endif;
//	end;
//
//	Routine OutCbuffer;
//	begin infix(String) bf;
//	%-E   integer ofst;
//	%+E   infix(wWORD) ofst;
//	%+D   if CBUF.nxt > 1024 then IERR("COASM:OutCbuffer-0") endif;
//	%-E   ofst:=CBUF.ofst.val; ofst:=ofst+CBUF.nxt;
//	%+E   ofst.HighWord.val:=CBUF.ofstHI;
//	%+E   ofst.LowWord.val:=CBUF.ofstLO; ofst.val:=ofst.val+CBUF.nxt;
//	%-E   if ofst>MaxWord then ERROR("Code Segment larger than 65K") endif;
//	%+A   if asmgen then -- Nothing
//	%+A   else
//	           if CBUF.nxt <> 0
//	           then if NXTYPE=0 -- First buffer is treated different --
//	                then bf.chradr:=@CBUF.hed(4);
//	                     bf.nchr:=CBUF.nxt+2+AllignFac;
//	                     NXTYPE:=2; NXTLNG.val:=bf.nchr+4;
//	                else bf.chradr:=@CBUF.hed;
//	                     bf.nchr:=CBUF.nxt+6+AllignFac;
//	                endif;
//	%+D             if TLIST > 3
//	%+D             then outstring("*** OutCbuffer1: type=");
//	%+D                  outword(CBUF.hed qua integer);
//	%+D                  outstring(", lng="); outword(bf.nchr);
//	%+D                  Printout(sysout);
//	%+D             endif;
//	                CBUF.nxt:=bf.nchr; EnvOutBytes(scrfile,bf);
//	                if Status<>0 then FILERR(scrfile,"OutCbuffer-1") endif;
//	                if CREL.nxt <> 0
//	                then bf.nchr:=(CREL.nxt*Size2Word(size(RelocPkt)))+4;
//	                     bf.chradr:=@CREL.hed;
//	%+D                  if TLIST > 3 
//	%+D                  then outstring("*** OutCbuffer2: type=");
//	%+D                       outword(CREL.hed qua integer);
//	%+D                       outstring(", lng="); outword(bf.nchr);
//	%+D                       Printout(sysout);
//	%+D                  endif;
//	                     CREL.nxt:=bf.nchr; EnvOutBytes(scrfile,bf);
//	                     if Status <> 0
//	                     then FILERR(scrfile,"OutCbuffer-2") endif;
//	                endif;
//	           elsif CREL.NXT<>0 then FILERR(scrfile,"OutCbuffer-3") endif;
//	%+A   endif;
//	      CBUF.nxt:=0; CREL.nxt:=0;
//	%-E   CBUF.ofst.val:=ofst;
//	%+E   CBUF.ofstHI:=ofst.HighWord.val; CBUF.ofstLO:=ofst.LowWord.val;
//	end;
//	%page
//
//	Macro CodeSpace(1);
//	begin if CBUF.nxt >= (1025-(%1)) then OutCbuffer endif; endmacro;
//
//	Macro CodeRelSpace(1);
//	begin if CBUF.nxt >= (1025-(%1)) then OutCbuffer
//	      elsif CREL.nxt >= 256 then OutCbuffer endif;
//	endmacro;
//
//	Macro Emit1Code(1);
//	begin CBUF.byt(CBUF.nxt):=%1; CBUF.nxt:=CBUF.nxt+1; endmacro;
//
//	Macro Encode2(2); -- import range(0:nregs) reg; range(0:255) rm;
//	begin CBUF.byt(CBUF.nxt) := bOR(192,bOR(bSHL(bAND(%1,7),3),%2));
//	      CBUF.nxt:=CBUF.nxt+1;  -- 192=300oct=11 000 000B
//	endmacro;
//
//	Macro Emit2Code(1);
//	begin CBUF.byt(CBUF.nxt):=%1 .LO; CBUF.nxt:=CBUF.nxt+1;
//	      CBUF.byt(CBUF.nxt):=%1 .HI; CBUF.nxt:=CBUF.nxt+1;
//	endmacro;
//
//	Macro Emit4Code(1);
//	begin CBUF.byt(CBUF.nxt):=%1 .LO; CBUF.nxt:=CBUF.nxt+1;
//	      CBUF.byt(CBUF.nxt):=%1 .LOHI; CBUF.nxt:=CBUF.nxt+1;
//	      CBUF.byt(CBUF.nxt):=%1 .HILO; CBUF.nxt:=CBUF.nxt+1;
//	      CBUF.byt(CBUF.nxt):=%1 .HI; CBUF.nxt:=CBUF.nxt+1;
//	endmacro;
//
//	Macro Emit2CodeNull(0);
//	begin CBUF.byt(CBUF.nxt):=0; CBUF.nxt:=CBUF.nxt+1;
//	      CBUF.byt(CBUF.nxt):=0; CBUF.nxt:=CBUF.nxt+1;
//	endmacro;
//
//	Macro Emit4CodeNull(0);
//	begin CBUF.byt(CBUF.nxt):=0; CBUF.nxt:=CBUF.nxt+1;
//	      CBUF.byt(CBUF.nxt):=0; CBUF.nxt:=CBUF.nxt+1;
//	      CBUF.byt(CBUF.nxt):=0; CBUF.nxt:=CBUF.nxt+1;
//	      CBUF.byt(CBUF.nxt):=0; CBUF.nxt:=CBUF.nxt+1;
//	endmacro;
//	%page
//	const range(0:7)
//	      icDYAD(20)=(0,iAND,iOR,iXOR,iAND,iOR,iXOR,iADD,iSUB,iCMP,
//	                    iADD,iSUB,iADD,iSUB,iADC,iSBB,iADD,iSUB,iADC,iSBB);
//	const range(0:7)
//	      icFDYAD(8)=(0,iFCOMP,iFADD,iFSUB,iFSUBR,iFMUL,iFDIV,iFDIVR);
//
//	const range(0:255) icCOND(16) = (
//	      -- 0       q_WLT   q_WLE   q_WEQ   q_WGE   q_WGT   q_WNE   7  --
//	         0,      iJB,    iJBE,   iJE,    iJAE,   iJA,    iJNE,   0,
//	      -- 8       q_ILT   q_ILE   q_IEQ   q_IGE   q_IGT   q_INE   15 --
//	         0,      iJL,    iJLE,   iJE,    iJGE,   iJG,    iJNE,   0);
//
//
//	Routine SignExt;
//	import infix(wWORD) cnst; export range(0:2) res;
//	begin
//	%-E   res := bAND(BOOL2BYTE( cnst.HI=BOOL2BYTE(cnst.LO>127) ),2);
//	%+E   if cnst.LO < 128 then cnst.LO:=0
//	%+E   else cnst.LO:=255; cnst.val:=cnst.val+1 endif;
//	%+E   res := bAND(BOOL2BYTE( cnst.val=0 ),2);
//	end;
//
//	%-E Routine EmitSOP; import range(0:MaxByte) sbireg;
//	%-E begin
//	%-E %+D   RST(R_EmitSOP);
//	%-E       if OverrideSreg(sbireg)
//	%-E       then CodeSpace(%1%);
//	%-E            Emit1Code(%bOR(iSOP,bSHR(bAND(sbireg,48),1))%);
//	%-E       endif;
//	%-E end;
//
//	Routine EmitCall; import infix(MemAddr) addr;
//	begin
//	%-E   range(0:MaxWord) segid; segid:=AdrSegid(addr);
//	%+D   RST(R_EmitCall);
//	      CodeSpace(%5%);
//	%-E   if segid=CSEGID.val
//	%-E   then Emit1Code(%iCALL%);                --- Near Call
//	%-E        EmitFullDisp(addr);                --- Self-relative Offset
//	%-E   else if segid <> 0 then addr.rela.val:=addr.rela.val+3 endif;
//	%-E        Emit1Code(%iCALLF%);               --- Far Call
//	%-E        EmitAddr(addr,F_POINTER);          --- Segm-relative Offset
//	%-E   endif;
//	%+E   if addr.kind=reladr   --- Special Case: --- CALL [reg]  No Offset
//	%+E   then Emit1Code(%iGRP2+1%);
//	%+E        Encode2(%iCALLI%,%bAND(addr.sibreg,BaseREG)%);
//	%+E   else Emit1Code(%iCALL%);                --- Near Call
//	%+E        EmitFullDisp(addr);                --- Self-relative Offset
//	%+E   endif;
//	end;
//
//	Routine EmitRutCall; import ref(Qfrm5) qi;
//	-------------- CALL    subc pxlng addr ----------  Format 5
//	begin range(0:MaxByte) subc; range(0:MaxWord) cnst;
//	      infix(wWORD) knlx;
//	      subc:=qi.subc;
//	%-E   if (subc=qXNX) or (subc=qC)
//	%-E   then CodeSpace(%2%);
//	%-E        Emit1Code(%22%);                 -- PUSH SS         -- 1
//	%-E        Emit1Code(%31%);                 -- POP  DS         -- 1
//	%-E   endif;
//	      if subc = qC
//	      then EmitCall(qi qua Qfrm5.addr);
//	           cnst:=qi qua Qfrm5.aux;
//	           if cnst <> 0
//	           then CodeSpace(%3%);             -- ADD  SP,cnst    -- 3
//	                Emit1Code(%bOR(iDYADI,3)%); -- (ws=11)
//	                Encode2(%iADD%,%iSP%);
//	                Emit1Code(%cnst%);
//	           endif;
//	      elsif subc = qXNX
//	      then EmitCall(qi qua Qfrm5.addr);
//	           cnst:=qi qua Qfrm5.aux;
//	           if cnst <> 0
//	           then CodeSpace(%3%);             -- ADD  SP,cnst    -- 3
//	                Emit1Code(%bOR(iDYADI,3)%); -- (ws=11)
//	                Encode2(%iADD%,%iSP%);
//	                Emit1Code(%cnst%);
//	           endif;
//	           CodeSpace(%9%);
//	           Emit1Code(%iINC%);               -- INC  AX         -- 1
//	           Emit1Code(%117%);                -- JNZ  $+5/7      -- 2
//	%-E        Emit1Code(%if EnvCSEG=CSEGID then 3 else 5%);
//	%+E        Emit1Code(%5%);
//	           EmitCall(X_SSTAT);
//	           Emit1Code(%iDEC%);               -- DEC  AX         -- 1
//	      elsif subc = qKNL
//	      then knlx.val:=qi qua Qfrm5.addr.knlx.val;
//	%-E        CodeSpace(%18%);
//	%-E        Emit1Code(%bOR(iMOVI,8)%);         -- MOV  AX,knlx       -- 3
//	%-E        Emit2Code(%knlx%);
//	%-E        Emit1Code(%iCALLF%);               -- CALLF 144:0        -- 5
//	%-E        Emit2CodeNull;
//	%-E        knlx.val:=144; Emit2Code(%knlx%);
//	%-E        Emit1Code(%115%); Emit1Code(%4%);    -- JNC  $+6         -- 2
//	%-E        Emit1Code(%bOR(iSOP,16)%);
//	%-E        Emit1Code(%bOR(iMOVA,3)%);       -- MOV  SS:G.OSSTAT,AX  -- 4
//	%-E        EmitAddr(X_OSSTAT,F_OFFSET);
//	%-E        Emit1Code(%bOR(iSOP,16)%);
//	%-E        Emit1Code(%bOR(iMOVA,3)%);       -- MOV  SS:G.KNLAUX,AX  -- 4
//	%-E        EmitAddr(X_KNLAUX,F_OFFSET);
//
//	%+E        CodeSpace(%26%);
//	%+E        Emit1Code(%bOR(iMOVI,8)%);         -- MOV  EAX,knlx      -- 5
//	%+E        Emit4Code(%knlx%);
//	%+E        Emit1Code(%iCALLF%);               -- CALLF 7:0          -- 9
//	%+E        Emit4CodeNull;
//	%+E        knlx.val:=7; Emit4Code(%knlx%);
//	%+E        Emit1Code(%115%); Emit1Code(%5%);  -- JNC  $+7           -- 2
//	%+E        Emit1Code(%bOR(iMOVA,3)%);         -- MOV  G.OSSTAT,EAX  -- 5
//	%+E        EmitAddr(X_OSSTAT);
//	%+E        Emit1Code(%bOR(iMOVA,3)%);         -- MOV  G.KNLAUX,EAX  -- 5
//	%+E        EmitAddr(X_KNLAUX);
//
//	           cnst:=qi qua Qfrm5.aux;
//	           if cnst <> 0
//	           then CodeSpace(%3%);             -- ADD  SP,cnst    -- 3
//	                Emit1Code(%bOR(iDYADI,3)%); -- (ws=11)
//	                Encode2(%iADD%,%iSP%);
//	                Emit1Code(%cnst%);
//	           endif;
//	%-E   elsif subc=qOS2
//	%-E   then EmitCall(qi qua Qfrm5.addr);
//	%-E        CodeSpace(%4%);              -- MOV  SS:G.OSSTAT,AX -- 4
//	%-E        Emit1Code(%bOR(iSOP,16)%);
//	%-E        Emit1Code(%bOR(iMOVA,3)%);
//	%-E        EmitAddr(X_OSSTAT,F_OFFSET);
//	      else EmitCall(qi qua Qfrm5.addr) endif;
//	end;
//
//
//	Routine EmitENTER; import ref(Qfrm2) qi;
//	-------------- ENTER   const --------------------  Format 2
//	begin range(0:MaxByte) s;
//	      infix(wWORD) cnst; cnst:=qi.aux;
//	      CodeSpace(%10%);
//	%-E   if qi.subc=0 -- called FAR and NEAR
//	%-E   then Emit1Code(%88%);                                -- POP  AX
//	%-E        Emit1Code(%14%);                                -- PUSH CS
//	%-E        Emit1Code(%80%);                                -- PUSH AX
//	%-E   endif
//	%-E   if CPUID >= iAPX186
//	%-E   then
//	           Emit1Code(%iENTER%);
//	%-E        Emit2Code(%cnst%);
//	%+E        Emit2Code(%cnst.LowWord%);
//	           Emit1Code(%0%);
//	%-E   else Emit1Code(%bOR(iPUSH,iBP)%)              -- PUSH BP
//	%-E        Emit1Code(%bOR(iMOV,3)%); -- iMOV or 11B -- MOV  BP,SP
//	%-E        Encode2(%iBP%,%iSP%);
//	%-E        if cnst.val > 0
//	%-E        then s:=SignExt(cnst);
//	%-E             Emit1Code(%bOR(bOR(iDYADI,1),s)%); -- SUB  SP,const
//	%-E             Encode2(%iSUB%,%iSP%);
//	%-E             if s=0 then Emit2Code(%cnst%)
//	%-E             else Emit1Code(%cnst.LO%) endif
//	%-E        endif;
//	%-E   endif;
//	%-E %+CS  if CHKSTK
//	%-E %+CS  then CodeSpace(%10%);
//	%-E %+CS    Emit1Code(%bOR(iDYADI,1)%); -- CMP SP,OFFSET G.STKBEG -- 4
//	%-E %+CS    Encode2(%iCMP%,%iSP%);
//	%-E %+CS    EmitAddr(X_STKBEG,F_OFFSET);
//	%-E %+CS    Emit1Code(%bOR(iJCOND,iJA)%); -- JA   $+5/7
//	%-E %+CS    if EnvCSEG=CSEGID then Emit1Code(%3%)
//	%-E %+CS    else Emit1Code(%5%) endif;
//	%-E %+CS    EmitCall(X_STKOFL);
//	%-E %+CS  endif;
//	end;
//
//	Routine EmitLEAVE; import ref(Qfrm2) qi;
//	-------------- LEAVE   const --------------------  Format 2
//	begin CodeSpace(%3%);
//	%-E   if CPUID < iAPX186
//	%-E   then if qi.aux.val <> 0
//	%-E        then Emit1Code(%bOR(iMOV,3)%); -- I.e. iMOV or 11B
//	%-E             Encode2(%iSP%,%iBP%);                 -- MOV  SP,BP
//	%-E        endif;
//	%-E        Emit1Code(%bOR(iPOP,iBP)%)                 -- POP  BP
//	%-E   else
//	           Emit1Code(%iLEAVE%);
//	%-E   endif;
//	end;
//
//	%-E Routine EmitDOS2; import ref(Qfrm1) qi;
//	%-E -------------- DOS2 -----------------------------  Format 1
//	%-E begin CodeSpace(%8%);
//	%-E       Emit1Code(%iINT%); Emit1Code(%33%);  -- INT  33          -- 2
//	%-E       Emit1Code(%115%); Emit1Code(%4%);    -- JNC  $+6         -- 2
//	%-E       Emit1Code(%bOR(iSOP,16)%);     -- MOV  SS:G.OSSTAT,AL    -- 4
//	%-E       Emit1Code(%bOR(iMOVA,2)%);
//	%-E       EmitAddr(X_OSSTAT,F_OFFSET);
//	%-E end;
//
//	%page
//	Routine EmitJMP; import ref(Qfrm5) jmp;
//	begin Boolean shrt; ref(Qpkt) dst; infix(RelocPkt) pkt;
//	      infix(MemAddr) adr; infix(wWORD) ofst;
//	%-E   range(0:MaxWord) disp; -- NOTE: Unsigned
//	%+E   integer          disp; -- NOTE: Unsigned
//	%+D   RST(R_EmitJMP); CheckJMP(jmp);
//	      adr:=jmp.addr; dst:=jmp.dst;
//	      if dst=none then shrt:=false      -- Jump to LABEL
//	      elsif dst=jmp then shrt:=false    -- FJUMP to Ungen.
//	      elsif dst qua Qfrm6.fnc=qBDEST    -- Backward Jump to BDEST
//	      then
//	%-E        disp:= (CBUF.ofst.val+CBUF.nxt+2) - (dst qua Qfrm6.rela.val);
//	%+E        ofst.HighWord.val:=CBUF.ofstHI
//	%+E        ofst.LowWord.val:=CBUF.ofstLO
//	%+E        disp:= (ofst.val+CBUF.nxt+2) - (dst qua Qfrm6.rela.val);
//	           shrt:=disp <= 128; adr.kind:=segadr;
//	%-E        adr.sbireg:=0;
//	%+E        adr.sibreg:=NoIBREG;
//	           adr.rela:=dst qua Qfrm6.rela; adr.segmid:=CSEGID;
//	           DELETE(dst); jmp.dst:=none;
//	%+A        jmp.addr:=adr;
//	      else
//	%-E        shrt:=ShrtJMP(CBUF.ofst.val+CBUF.nxt,jmp);
//	%+E        ofst.HighWord.val:=CBUF.ofstHI
//	%+E        ofst.LowWord.val:=CBUF.ofstLO
//	%+E        shrt:=ShrtJMP(ofst.val+CBUF.nxt,jmp);
//	      endif;
//	%-E   CodeSpace(%5%);
//	%+E   CodeSpace(%6%);
//	      if shrt
//	      then -- Short Jump -- Selfrelative
//	%+A        -- aux: 0/1 short, 2/3 near, 4/5 far  (even: cond. jump)
//	           if jmp.subc=0 then Emit1Code(%iJMPS%)
//	%+A           jmp.aux:=1;
//	           else Emit1Code(%bOR(iJCOND,icCOND(jmp.subc))%)
//	%+A           jmp.aux:=0;
//	           endif
//	           case 0:adrMax (adr.kind)
//	           when segadr:
//	%-E             ofst.val:=adr.rela.val-(CBUF.ofst.val+CBUF.nxt+1);
//	%+E             ofst.HighWord.val:=CBUF.ofstHI
//	%+E             ofst.LowWord.val:=CBUF.ofstLO
//	%+E             ofst.val:=adr.rela.val-(ofst.val+CBUF.nxt+1);
//	%+D             if SignExt(ofst)=0
//	%+D             then EdWrd(errmsg,ofst.val);
//	%+D                  IERR(" COASM:EmitJMP -- Short is wrong decision");
//	%+D             endif;
//	                Emit1Code(%ofst.LO%)
//	           when fixadr: pkt.fix:=adr.fix; CodeRelSpace(%1%);
//	                        pkt.mrk:=wOR(wOR(mfBYTEDISP,mrFIX),CBUF.nxt);
//	                        CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	                        Emit1Code(%0%);
//	           otherwise IERR("EmitDispByte"); Emit1Code(%0%) endcase;
//	%-E   elsif AdrSegid(adr) <> CSEGID.val
//	%-E   then -- Far Jump -- Segment Relative
//	%-E        if jmp.subc<>0
//	%-E        then Emit1Code(%bOR(iJCOND,icCOND(NotQcond(jmp.subc)))%);
//	%-E             Emit1Code(%5%); CodeSpace(%5%);
//	%+A %-E         jmp.aux:=4 else jmp.aux:=5
//	%-E        endif;
//	%-E        Emit1Code(%iJMPF%); EmitAddr(adr,F_POINTER); -- Seg-rel pntr
//	      else -- Near Jump -- Selfrelative
//	           if jmp.subc<>0
//	           then
//	%+A             jmp.aux:=2;
//	%-E             Emit1Code(%bOR(iJCOND,icCOND(NotQcond(jmp.subc)))%);
//	%-E             Emit1Code(%3%); Emit1Code(%iJMP%);
//	%+E             Emit1Code(%iGRP3%);
//	%+E             Emit1Code(%bOR(iJMPC,icCOND(jmp.subc))%);
//	           else
//	%+A             jmp.aux:=3;
//	                Emit1Code(%iJMP%); endif;
//	           EmitFullDisp(adr); -- Selfrelative
//	      endif;
//	end;
//
//	%+D Routine CheckJMP; import ref(Qfrm5) jmp;
//	%+D begin ref(Qpkt) dst; infix(MemAddr) adr;
//	%+D    dst:=jmp.dst; adr:=jmp.addr;
//	%+D    if dst=none then        -- Jump to LABEL
//	%+D    elsif dst=jmp           -- Foreward Jump to Ungenerated FDEST
//	%+D    then if adr.kind<>fixadr then IERR("CheckJMP-2") endif;
//	%+D    elsif dst.fnc=qFDEST    -- Foreward Jump to Generated FDEST
//	%+D    then if dst qua Qfrm6.jmp<>jmp then IERR("CheckJMP-4") endif;
//	%+D    elsif dst.fnc=qBDEST    -- Backward Jump to BDEST
//	%+D    then
//	%+D    endif;
//	%+D end;
//
//	%+D Routine CheckFDEST; import ref(Qfrm6) dst;
//	%+D begin ref(Qfrm5) jmp; --- Check: dst.jmp.dst = dst  ---
//	%+D       if dst=none              then IERR("COASM.CheckFDEST-1") endif
//	%+D       if dst.fnc   <> qFDEST   then IERR("COASM.CheckFDEST-2") endif
//	%+D       jmp:=dst.jmp; if jmp=none  then goto E1 endif;
//	%+D       if jmp.fnc   <> qJMP     then IERR("COASM.CheckFDEST-3") endif
//	%+D       if jmp.dst   <> dst      then IERR("COASM.CheckFDEST-4") endif
//	%+D E1:end;
//
//
//	Routine EmitFDEST; ------ FDEST   subc fix rela jmp --------  Format 6
//	import ref(Qfrm6) qi;
//	begin infix(Fixup) Fx; infix(WORD) fix;
//	%+D   CheckFDEST(qi);
//	      DELETE(qi.jmp); fix.val:=qi.aux; Fx:=FIXTAB(fix.HI).elt(fix.LO);
//	%+D   if Fx.segid <> CSEGID
//	%+D   then EdSymb(errmsg,Fx.segid);
//	%+D        Ed(errmsg," <> "); EdSymb(errmsg,CSEGID);
//	%+D        IERR(" -- FIXUP Segment Check failed");
//	%+D   endif;
//	%+C   if Fx.Matched then IERR("FIXUP already defined") endif;
//	      Fx.Matched:=true;
//	%-E   Fx.rela.val:=CBUF.ofst.val+CBUF.nxt;
//	%+E   Fx.rela.HighWord.val:=CBUF.ofstHI
//	%+E   Fx.rela.LowWord.val:=CBUF.ofstLO
//	%+E   Fx.rela.val:=Fx.rela.val+CBUF.nxt;
//	      FIXTAB(fix.HI).elt(fix.LO):=Fx;
//	end;
//
//	Routine EmitLABEL; ------ LABEL   subc fix smbx ------------  Format 6
//	import ref(Qfrm6) qi;
//	begin infix(Fixup) Fx; infix(WORD) fix; infix(wWORD) cnst;
//	%+S   range(0:maxByte) n;
//	      if qi.subc <> qEPROC
//	      then fix.val:=qi.aux; Fx:=FIXTAB(fix.HI).elt(fix.LO);
//	%+C        if Fx.segid.val = 0 then -- OK (Label-spec)
//	%+C        elsif Fx.segid <> CSEGID
//	%+C        then EdSymb(errmsg,Fx.segid);
//	%+C             Ed(errmsg," <> "); EdSymb(errmsg,CSEGID);
//	%+C             IERR(" -- FIXUP Segment Check failed");
//	%+C        endif;
//	%+C        if Fx.Matched then IERR("FIXUP already defined") endif;
//	%+A        qi.smbx:=Fx.smbx.val;
//	%-E        cnst.val:=CBUF.ofst.val+CBUF.nxt;
//	%+E        cnst.HighWord.val:=CBUF.ofstHI
//	%+E        cnst.LowWord.val:=CBUF.ofstLO
//	%+E        cnst.val:=cnst.val+CBUF.nxt;
//	%+S        if SYSGEN<>0 then if qi.subc=qBPROC then
//	%+S           if Fx.smbx.val=0             -- local routine
//	%+S           then n:=AllignDiff(cnst.val) -- align NEAR
//	%+S           elsif Alligned(cnst.val) then n:=1 else n:=0 endif
//	%+S           if n<>0 -- align NEAR or FAR entry as indicated above
//	%+S           then qi.reg:=n; repeat while n<>0
//	%+S                do codespace(1); emit1code(iNOP); n:=n-1 endrepeat
//	%+S %-E            cnst.val:=CBUF.ofst.val+CBUF.nxt;
//	%+S %+E            cnst.HighWord.val:=CBUF.ofstHI
//	%+S %+E            cnst.LowWord.val:=CBUF.ofstLO
//	%+S %+E            cnst.val:=cnst.val+CBUF.nxt;
//	%+S           endif
//	%+S        endif endif
//	           if Fx.smbx.val<>0 then PutPublic(Fx.smbx,CSEGID,cnst) endif;
//	           ------   U P D A T E    F I X U P   ------
//	           Fx.Matched:=true; Fx.segid:=CSEGID; Fx.rela.val:=cnst.val;
//	-- %-E        Fx.rela.val:=CBUF.ofst.val+CBUF.nxt;
//	-- %+E        Fx.rela.HighWord.val:=CBUF.ofstHI
//	-- %+E        Fx.rela.LowWord.val:=CBUF.ofstLO
//	-- %+E        Fx.rela.val:=Fx.rela.val+CBUF.nxt;
//	           FIXTAB(fix.HI).elt(fix.LO):=Fx;
//	     endif;
//	end;
//	%page
//
//	Routine EmitAddr;
//	import infix(MemAddr) x;
//	%-E    range(0:4) fld;
//	begin infix(RelocPkt) pkt;
//	%-E   infix(WORD) segid;
//	%+D   RST(R_EmitAddr);
//	%-E   case 0:4 (fld)
//	%-E   when F_POINTER:
//	           case 0:adrMax (x.kind)
//	           when segadr: pkt.segx:=PutSegx(x.segmid); CodeRelSpace(%4%);
//	                        pkt.mrk:=wOR(wOR(mfPOINTER,mrSEG),CBUF.nxt);
//	                        CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	           when extadr: pkt.extx:=PutExtern(x.smbx); CodeRelSpace(%4%);
//	                        pkt.mrk:=wOR(wOR(mfPOINTER,mrEXT),CBUF.nxt);
//	                        CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	           when fixadr: pkt.fix:=x.fix; CodeRelSpace(%4%);
//	                        pkt.mrk:=wOR(wOR(mfPOINTER,mrFIX),CBUF.nxt);
//	                        CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	           otherwise x.rela.val:=0; IERR("EmitAddr-1") endcase;
//	%-E        Emit2Code(%x.rela%); Emit2CodeNull;
//	%+DE       if x.rela.HighWord.val <> 0
//	%+DE       then IERR("EmitAddr-2x"); x.rela.HighWord.val:=0 endif;
//	%+E        Emit4Code(%x.rela%);
//	%-E   when F_BASE: case 0:adrMax (x.kind)
//	%-E        when segadr: LL: pkt.segx:=PutSegx(x.segmid);
//	%-E                     CodeRelSpace(%2%);
//	%-E                     pkt.mrk:=wOR(wOR(mfSEGMENT,mrSEG),CBUF.nxt);
//	%-E                     CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	%-E        when extadr: segid.val:=AdrSegid(x);
//	%-E                     if segid.val <> 0
//	%-E                     then x.segmid:=segid; goto LL endif;
//	%-E                     pkt.extx:=PutExtern(x.smbx); CodeRelSpace(%2%);
//	%-E                     pkt.mrk:=wOR(wOR(mfSEGMENT,mrEXT),CBUF.nxt);
//	%-E                     CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	%-E        when fixadr: pkt.fix:=x.fix; CodeRelSpace(%2%);
//	%-E                     pkt.mrk:=wOR(wOR(mfSEGMENT,mrFIX),CBUF.nxt);
//	%-E                     CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	%-E        otherwise IERR("EmitAddr-2") endcase;
//	%-E        Emit2CodeNull;
//	%-E   when F_OFFSET: case 0:adrMax (x.kind) when 0: x.rela.val:=0;
//	%-E        when reladr:
//	%-E        when locadr: x.rela.val:=x.rela.val-x.loca;
//	%-E        when segadr: pkt.segx:=PutSegx(x.segmid); CodeRelSpace(%2%);
//	%-E                     pkt.mrk:=wOR(wOR(mfOFFSET,mrSEG),CBUF.nxt);
//	%-E                     CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	%-E        when extadr: pkt.extx:=PutExtern(x.smbx); CodeRelSpace(%2%);
//	%-E                     pkt.mrk:=wOR(wOR(mfOFFSET,mrEXT),CBUF.nxt);
//	%-E                     CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	%-E        when fixadr: pkt.fix:=x.fix; CodeRelSpace(%2%);
//	%-E                     pkt.mrk:=wOR(wOR(mfOFFSET,mrFIX),CBUF.nxt);
//	%-E                     CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	%-E        otherwise x.rela.val:=0; IERR("EmitAddr-3") endcase;
//	%-E        Emit2Code(%x.rela%);
//	%-E   endcase;
//	end;
//	%page
//	Routine EmitFullDisp; import infix(MemAddr) x;
//	begin infix(RelocPkt) pkt; infix(wWORD) ofst;
//	%-E   CodeRelSpace(%4%); --- ?????????????????????????????????
//	%+E   CodeRelSpace(%6%); --- ?????????????????????????????????
//	%-E   CodeRelSpace(%2%);
//	%+E   CodeRelSpace(%4%);
//	      case 0:adrMax (x.kind)
//	      when segadr: pkt.segx:=PutSegx(x.segmid);
//	                   pkt.mrk:=wOR(wOR(mfFULLDISP,mrSEG),CBUF.nxt);
//	                   CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	      when extadr: pkt.extx:=PutExtern(x.smbx);
//	                   pkt.mrk:=wOR(wOR(mfFULLDISP,mrEXT),CBUF.nxt);
//	                   CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	      when fixadr: pkt.fix:=x.fix;
//	                   pkt.mrk:=wOR(wOR(mfFULLDISP,mrFIX),CBUF.nxt);
//	                   CREL.elt(CREL.nxt):=pkt; CREL.nxt:=CREL.nxt+1;
//	      otherwise IERR("EmitFullDisp") endcase;
//	%-E   ofst:=x.rela; Emit2Code(%ofst%);
//	%+E   ofst:=x.rela; Emit4Code(%ofst%);
//	end;
//	%page
//
//	Routine EncodeEA;
//	import infix(MemAddr) opr; range(0:nregs) ttt;
//	begin range(0:255) mode,rm,l; Boolean reloc;
//	      infix(wWORD) Ofst;
//	%+E   infix(wWORD) ofx; range(0:nregs) ir;
//	%+D   RST(R_EncodeEA);
//	      Ofst:=opr.rela;
//	      if opr.kind=locadr then reloc:=false; Ofst.val:=Ofst.val-opr.loca;
//	      else reloc:= opr.kind<>reladr endif;
//	%-E   CodeSpace(%3%);
//	%-E   if bAND(opr.sbireg,rmBIREG)=0
//	%-E   then Emit1Code(%bOR(bSHL(bAND(ttt,7),3),6)%); l:=2; -- 6=110B
//	%-E   else rm:=bAND(opr.sbireg,7)
//	%-E        if reloc then mode:=2; l:=2
//	%-E        elsif Ofst.val=0
//	%-E        then if rm=biBP then mode:=1; l:=1 else mode:=0; l:=0 endif;
//	%-E        elsif Ofst.HI=BOOL2BYTE(Ofst.LO>127)
//	%-E        then mode:=1; l:=1 else mode:=2; l:=2 endif;
//	%-E        Emit1Code(%bOR(bSHL(mode,6),bOR(bSHL(bAND(ttt,7),3),rm))%);
//	%-E   endif;
//	%-E   if reloc then EmitAddr(opr,F_OFFSET)
//	%-E   elsif l=1 then Emit1Code(%Ofst.LO%);
//	%-E   elsif l=2 then Emit2Code(%Ofst%) endif;
//
//	%+E   CodeSpace(%6%);
//	%+E   if opr.sibreg=NoIBREG
//	%+E   then Emit1Code(%bOR(bSHL(bAND(ttt,7),3),5)%); l:=4 -- 5=101B
//	%+E   else rm:=bAND(opr.sibreg,BaseREG); ir:=bAND(opr.sibreg,IndxREG);
//	%+E        if (rm<>bESP) and (rm=bSHR(ir,3))
//	%+E        then -- No Base, Scaled Index only --
//	%+E             mode:=0; l:=4; rm:=bESP;
//	%+E             opr.sibreg:=bOR(bAND(opr.sibreg,248),bEBP);
//	%+E        elsif reloc then mode:=2; l:=4
//	%+E        elsif Ofst.val <> 0
//	%+E        then ofx:=Ofst;
//	%+E             if ofx.LO < 128 then ofx.LO:=0
//	%+E             else ofx.LO:=255; ofx.val:=ofx.val+1 endif;
//	%+E             if ofx.val=0 then mode:=1;l:=1 else mode:=2;l:=4 endif;
//	%+E        elsif rm=bEBP then rm:=bESP; mode:=1; l:=1
//	%+E        else mode:=0; l:=0 endif;
//	%+E        if ir <> iESP then rm:=bESP endif;
//	%+E        Emit1Code(%bOR(bSHL(mode,6),bOR(bSHL(bAND(ttt,7),3),rm))%);
//	%+E        if ir <> iESP then Emit1Code(%opr.sibreg%); --- Scaled Index
//	%+E        elsif rm=bESP then Emit1Code(%bAND(opr.sibreg,63)%) endif;
//	%+E   endif;
//	%+E   if reloc then EmitAddr(opr)
//	%+E   elsif l=1 then Emit1Code(%Ofst.LO%);
//	%+E   elsif l=4 then Emit4Code(%Ofst%) endif;
//	end;
//	%page
//	%+E Routine EncodeWTL;
//	%+E import range(0:MaxWord) opc; range(0:MaxByte) wT,wF;
//	%+E begin infix(wWORD) WTL; WTL.HighWord.val:=65472; -- FFC0H
//	%+E       WTL.LowWord.val:=opc+bSHL(wT,2)+wSHL(bAND(wF,28),5)+bAND(wF,3)
//	%+E       CodeSpace(%4%); Emit4Code(%WTL%);
//	%+ED      if opc=wdLOAD then IERR("wdLOAD") endif;
//	%+E end;
//
//	%+E Routine EmitWTO; -- Perform WTLx167-operation
//	%+E import range(0:MaxWord) opc; range(0:MaxByte) wT,wF;
//	%+E begin --- Produce: MOV  WTLbase+opc[T+F],AL ---
//	%+E       if opc=wdLOAD
//	%+E       then opc:=wsLOAD; CodeSpace(%5%); Emit1Code(%iMOVA+2%);
//	%+E            EncodeWTL(opc,wT,wF); wT:=wT+1; wF:=wF+1;
//	%+E       endif;
//	%+E       CodeSpace(%5%); Emit1Code(%iMOVA+2%); EncodeWTL(opc,wT,wF);
//	%+E end;
//
//	%+E Routine EmitWTLI; -- Load WTLx167-reg from 386-Immediate
//	%+E import range(0:MaxWord) opc; range(0:MaxByte) wT; infix(wWORD) cnst;
//	%+E begin --- Produce: MOV  WTLbase+opc[T],cnst ---
//	%+E       CodeSpace(%10%); Emit1Code(%iMOVMI+1%); Emit1Code(%5%);
//	%+E       EncodeWTL(opc,wT,0); Emit4Code(%cnst%);
//	%+E end;
//
//	%+E Routine EmitWTLR; -- Load WTLx167-reg from 386-Register
//	%+E import range(0:MaxWord) opc; range(0:MaxByte) wT,reg
//	%+E begin --- Produce: MOV  WTLbase+opc[T],reg ---
//	%+E       CodeSpace(%6%); reg:=iReg(%reg%);
//	%+E       if reg=iA then Emit1Code(%iMOVA+3%)
//	%+E       else Emit1Code(%iMOV+1%); Emit1Code(%bSHL(reg,3)+5%) endif;
//	%+E       EncodeWTL(opc,wT,0);
//	%+E end;
//
//	%+E Routine EmitWTSR; -- Store WTLx167-reg into 386-Register
//	%+E import range(0:MaxWord) opc; range(0:MaxByte) wT,reg
//	%+E begin --- Produce: MOV  reg,WTLbase+opc[T] ---
//	%+E       CodeSpace(%6%); reg:=iReg(%reg%);
//	%+E       if reg=iA then Emit1Code(%iMOVA+1%)
//	%+E       else Emit1Code(%iMOV+3%); Emit1Code(%bSHL(reg,3)+5%) endif;
//	%+E       EncodeWTL(opc,wT,0);
//	%+E end;
//
//	%+E Routine EmitWTPUSH; -- Push WTLx167-reg onto 386-Stack
//	%+E import range(0:MaxByte) wT;
//	%+E begin --- Produce: PUSH  WTLbase+wsSTOR[T] ---
//	%+E       CodeSpace(%6%); Emit1Code(%iGRP2+1%);
//	%+E       Emit1Code(%bSHL(iPUSHM,3)+5%); EncodeWTL(wsSTOR,wT,0);
//	%+E end;
//
//	%+E Routine EmitWTPOP; -- Pop 386-Stack into WTLx167-reg
//	%+E import range(0:MaxByte) wT;
//	%+E begin --- Produce: POP  WTLbase+wsLOAD[T] ---
//	%+E       CodeSpace(%6%); Emit1Code(%iPOPM%);
//	%+E       Emit1Code(%5%); EncodeWTL(wsLOAD,wT,0);
//	%+E end;
//	%page
//
//	%+E Routine WxCONV;
//	%+E import range(0:6) from,to; export range(0:MaxWord) opc;
//	%+E begin case 0:6 (from)
//	%+E       when FMF_INT,FMF_SINT: case 0:6 (to)
//	%+E            when FMF_INT,FMF_SINT: opc:=wsLOAD
//	%+E            when FMF_REAL:         opc:=wsFLOAT
//	%+E            when FMF_LREAL:        opc:=wdFLOAT
//	%+EC           otherwise IERR("COASM:WxCONV-1")
//	%+E            endcase;
//	%+E       when FMF_REAL: case 0:6 (to)
//	%+E            when FMF_INT,FMF_SINT: opc:=wsFIX
//	%+E            when FMF_REAL:         opc:=wsLOAD
//	%+E            when FMF_LREAL:        opc:=wsCVTD
//	%+EC           otherwise IERR("COASM:WxCONV-2")
//	%+E            endcase;
//	%+E       when FMF_LREAL: case 0:6 (to)
//	%+E            when FMF_INT,FMF_SINT: opc:=wdFIX
//	%+E            when FMF_REAL:         opc:=wdCVTS
//	%+E            when FMF_LREAL:        opc:=wdLOAD
//	%+EC           otherwise IERR("COASM:WxCONV-3")
//	%+E            endcase;
//	%+EC      otherwise IERR("COASM:WxCONV-4")
//	%+E       endcase;
//	%+E end;
//
//	%+E Routine EmitWFLDC; ----- FLDC    fSD fmf val -----------  Format 3
//	%+E import ref(Qfrm3) qi;
//	%+E begin range(0:MaxByte) fSD,fwf,fSP,fmf,qereg,ereg; infix(wWORD) cnst;
//	%+E       fmf:=qi.reg; qereg:=RegAvailable(qi);
//	%+E                                              qereg:=qEAX; --- TEMP ?????
//	%+E       if qereg<>0 then ereg:=iReg(%qereg%)
//	%+E       else CodeSpace(%1%); Emit1Code(%iPUSH%); ereg:=iA endif;
//	%+E       fSD:=qi.subc; fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E       cnst.val:=qi.val.int(0);
//	%+E       if fmf=FMF_LREAL
//	%+E       then CodeSpace(%1%); Emit1Code(%iPUSH%);  ---- TEMP ????????????
//	%+E            LoadCnst(qEAX+ereg,cnst,RegDies(qi,uF))
//	%+E            if fwf=FMF_LREAL
//	%+E            then EmitWTLR(wsLOAD,fSP+1,ereg);
//	%+E                 fwf:=FMF_REAL; fmf:=fwf;  -- JUKS  JUKS
//	%+E            else EmitWTLR(wsLOAD,1,ereg) endif;
//	%+E            CodeSpace(%1%); Emit1Code(%iPOP%);  ---- TEMP ????????????
//	%+E            cnst.val:=qi.val.int(1); LoadCnst(qEAX+ereg,cnst,RegDies(qi,uF))
//	%+E       else LoadCnst(qEAX+ereg,cnst,RegDies(qi,uF))
//	%+E       endif;
//	%+E       EmitWTLR(WxCONV(fmf,fwf),fSP,ereg);
//	%+E       if qereg=0 then CodeSpace(%1%); Emit1Code(%iPOP%) endif;
//	%+E end;
//
//	%+E Routine EmitWFLD; ----- FLD     fSD fmf opr --------------  Format 3
//	%+E import range(0:MaxByte) fSD,fmf,qereg; infix(MemAddr) opr;
//	%+E begin range(0:MaxByte) fwf,fSP,ereg;
//	%+E       if qereg<>0 then ereg:=iReg(%qereg%)
//	%+E       else CodeSpace(%1%); Emit1Code(%iPUSH%); ereg:=iA endif;
//	%+E       fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E       if fmf=FMF_SINT then EmitLOAD(qSEXT,qAX+ereg,opr)
//	%+E       else
//	%+E            if fmf=FMF_LREAL
//	%+E            then CodeSpace(%1%); Emit1Code(%iPUSH%);  ---- TEMP ????????????
//	%+E                 EmitLOAD(0,qEAX+ereg,opr)
//	%+E                 if fwf=FMF_LREAL
//	%+E                 then EmitWTLR(wsLOAD,fSP+1,ereg);
//	%+E                      fwf:=FMF_REAL; fmf:=fwf;  -- JUKS  JUKS
//	%+E                 else EmitWTLR(wsLOAD,1,ereg) endif;
//	%+E                 CodeSpace(%1%); Emit1Code(%iPOP%);  ---- TEMP ????????????
//	%+E                 opr.rela.val:=opr.rela.val+4;
//	%+E                 EmitLOAD(0,qEAX+ereg,opr);
//	%+E            else EmitLOAD(0,qEAX+ereg,opr)
//	%+E            endif;
//	%+E       endif;
//	%+E       EmitWTLR(WxCONV(fmf,fwf),fSP,ereg);
//	%+E       if qereg=0 then CodeSpace(%1%); Emit1Code(%iPOP%) endif;
//	%+E end;
//
//	%+E Routine EmitWFST; ----- FST(P)  fSD fmf opr --------------  Format 3
//	%+E import range(0:MaxByte) fSD,fmf,qereg; infix(MemAddr) opr;
//	%+E begin range(0:MaxByte) fwf,fSP,ereg;
//	%+E       if qereg<>0 then ereg:=iReg(%qereg%)
//	%+E       else CodeSpace(%1%); Emit1Code(%iPUSH%); ereg:=iA endif;
//	%+E       fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E       if fwf<>fmf then EmitWTO(WxCONV(fwf,fmf),fSP,fSP) endif;
//	%+E       if fmf=FMF_SINT then ereg:=qAX+ereg
//	%+E       else ereg:=qEAX+ereg;
//	%+E            if fmf=FMF_LREAL
//	%+E            then EmitWTSR(wsSTOR,fSP+1,ereg); EmitSTORE(ereg,opr);
//	%+E                 opr.rela.val:=opr.rela.val+4;
//	%+E            endif;
//	%+E       endif;
//	%+E       EmitWTSR(wsSTOR,fSP,ereg); EmitSTORE(ereg,opr);
//	%+E       if qereg=0 then CodeSpace(%1%); Emit1Code(%iPOP%) endif;
//	%+E end;
//	%page
//
//	Routine ModifySP;
//	import ref(Qpkt) qi; range(0:255) subc; infix(wWORD) cnst;
//	begin range(0:255) fnc1,fnc2,rx,s;
//	%+D   RST(R_ModifySP);
//	%+D   if AllignDiff(%cnst.val%)<>0 then IERR("COASM:ModifySP") endif;
//	      if cnst.val <= (2*AllignFac)
//	      then if subc=qSUB
//	           then rx:=qAX; fnc1:=bOR(iPUSH,iA); fnc2:=bOR(iDEC,iSP);
//	           else rx:=RegAvailable(qi) fnc1:=bOR(iPOP,iReg(%rx%));
//	                fnc2:=bOR(iINC,iSP);
//	           endif;
//	           if rx <> 0
//	           then repeat while cnst.val >= AllignFac
//	                do CodeSpace(%1%); Emit1Code(%fnc1%);
//	                   cnst.val:=cnst.val-AllignFac;
//	                endrepeat;
//	           elsif cnst.val>3 then goto L1 endif;
//	           CodeSpace(%cnst.val%);
//	           repeat while cnst.val>0
//	           do Emit1Code(%fnc2%); cnst.val:=cnst.val-1 endrepeat;
//	      else L1: s:=SignExt(cnst);
//	%-E        CodeSpace(%4%);
//	%+E        CodeSpace(%6%);
//	           Emit1Code(%bOR(bOR(iDYADI,1),s)%); Encode2(%icDYAD(subc)%,%iSP%);
//	%-E        if s=0 then Emit2Code(%cnst%) else Emit1Code(%cnst.LO%) endif;
//	%+E        if s=0 then Emit4Code(%cnst%) else Emit1Code(%cnst.LO%) endif;
//	      endif;
//	end;
//
//	Routine LoadCnst;
//	import range(0:nregs) qreg; infix(wWORD) cnst; Boolean FreeFlg;
//	begin range(0:2) w; range(0:nregs) reg;
//	%+D   RST(R_LoadCnst);
//	      CodeSpace(%3%); reg:=iReg(%qreg%); w:=wBIT(qreg);
//	      if (w=1) and (cnst.val=0) and FreeFlg
//	      then Emit1Code(%bOR(iDYAD,bOR(bSHL(iXOR,3),w))%);
//	           Encode2(%reg%,%reg%);
//	      else Emit1Code(%bOR(iMOVI,bOR(bSHL(w,3),reg))%); 
//	%-E        CodeSpace(%2%);
//	%-E        if w=1 then Emit2Code(%cnst%)
//	%-E        else Emit1Code(%cnst.LO%) endif
//	%+E        CodeSpace(%4%);
//	%+E        if w=1 then Emit4Code(%cnst%)
//	%+E        else Emit1Code(%cnst.LO%) endif;
//	      endif;
//	end;
//
//	Visible Routine ConvertFLDCtoFLD; import ref(Qfrm3) qi;
//	begin range(0:MaxByte) n; infix(MemAddr) opr; range(0:AllignFac) na;
//	%+E   infix(wWORD) ofst32;
//	%+C   if qi.fnc <> qFLDC then IERR("COASM.ConvertFLDCtoFLD-1") endif;
//	      case 0:6 (qi.reg) when FMF_REAL,FMF_INT: n:=4
//	      when FMF_LREAL: n:=8  when FMF_SINT:  n:=2
//	%+C   otherwise IERR("COASM.ConvertFLDCtoFLD-2")
//	      endcase;
//	%-E   na:=AllignDiff(%DBUF.ofst.val+DBUF.nxt%);
//	%+E   ofst32.HighWord.val:=DBUF.ofstHI
//	%+E   ofst32.LowWord.val:=DBUF.ofstLO
//	%+E   na:=AllignDiff(%ofst32.val+DBUF.nxt%);
//	      if na<>0 then EmitZero(na) endif;
//	%+A   if asmgen then AsmSection(DSEGID);
//	%+AD  elsif listsw>0 then AsmSection(DSEGID)
//	%+A   endif;
//	      opr.kind:=segadr; opr.segmid:=DSEGID;
//	%-E   opr.rela.val:=DBUF.ofst.val+DBUF.nxt
//	%+E   opr.rela.HighWord.val:=DBUF.ofstHI
//	%+E   opr.rela.LowWord.val:=DBUF.ofstLO
//	%+E   opr.rela.val:=opr.rela.val+DBUF.nxt
//
//	%-E   opr.sbireg:=if qi.subc=0 then 0
//	%-E   else bSHL(bOR(bAND(qi.subc,3),8),4);
//	%+E   opr.sibreg:=NoIBREG;
//	      EmitData(n,@qi.val); qi.fnc:=qFLD; qi.opr:=opr;
//	%+A   if asmgen then AsmSection(CSEGID);
//	%+AD  elsif listsw>0 then AsmSection(CSEGID)
//	%+A   endif;
//	end;
//
//	%-E Routine EmitInco; import range(0:19) subc; range(0:7) reg;
//	%-E begin CodeSpace(%5%);
//	%-E       Emit1Code(%115%)                           -- JNC  $+5/4     -- 2
//	%-E       if subc=qINCO then Emit1Code(%3%)
//	%-E       else Emit1Code(%2%) endif;
//	%-E       Emit1Code(%51%); Encode2(%reg%,%reg%);     -- XOR  reg,reg   -- 2
//	%-E       if subc=qINCO
//	%-E       then Emit1Code(%bOR(iDEC,reg)%) endif;     -- DEC  reg       -- 1
//	%-E end;
//
//	Routine EmitPUSHC; import ref(Qfrm2) qi;
//	----------- PUSHC   reg const ----------------  Format 2
//	----------- PUSHC   reg fld addr -------------  Format 2b
//	begin range(0:MaxByte) qreg,reg,w,s; infix(wWORD) cnst;
//	      qreg:=qi.reg; reg:=iReg(%qreg%);
//	      if qi.kind = K_Qfrm2
//	      then
//	%-E        if CPUID >= iAPX186
//	%-E        then
//	                cnst:=qi qua Qfrm2.aux; s:=SignExt(cnst);
//	                w:=wBIT(qreg); if w=0 then s:=2 endif;
//	%-E             CodeSpace(%3%);
//	%+E             CodeSpace(%5%);
//	                Emit1Code(%bOR(iPUSHC,s)%);
//	%-E             if (w=1) and (s=0) then Emit2Code(%cnst%)
//	%-E             else Emit1Code(%cnst.LO%) endif;
//	%+E             if (w=1) and (s=0) then Emit4Code(%cnst%)
//	%+E             else Emit1Code(%cnst.LO%) endif;
//	%-E        else LoadCnst(qi.reg,qi qua Qfrm2.aux,RegDies(qi,uF));
//	%-E             CodeSpace(%1%); Emit1Code(%bOR(iPUSH,reg)%);
//	%-E        endif;
//	      else
//	%-E        if CPUID < iAPX186
//	%-E        then CodeSpace(%6%);
//	%-E             Emit1Code(%bOR(bOR(iMOVI,8),reg)%); -- i.e. w=1
//	%-E             EmitAddr(qi qua Qfrm2b.addr,qi qua Qfrm2b.aux.val)
//	%-E             Emit1Code(%bOR(iPUSH,reg)%);
//	%-E        else
//	                CodeSpace(%5%); Emit1Code(%iPUSHC%);
//	%-E             EmitAddr(qi qua Qfrm2b.addr,qi qua Qfrm2b.aux.val)
//	%+E             EmitAddr(qi qua Qfrm2b.addr)
//	%-E        endif;
//	      endif;
//	end;
//
//	Routine EmitPUSHM; import ref(Qfrm4) qi;
//	----------- PUSHM   const opr ----------------  Format 4
//	begin infix(MemAddr) opr; range(0:MaxWord) cnst; infix(wWORD) ofst;
//	      cnst:=wAllign(%qi qua Qfrm4.aux.val%); opr:=qi qua Qfrm4.opr;
//	      ofst.val:=opr.rela.val;
//	      if cnst=0 then IERR("COASM:PUSHM 0") endif;
//	      repeat while cnst<>0
//	      do cnst:=cnst-AllignFac; opr.rela.val:=ofst.val+cnst;
//	         CodeSpace(%4%);
//	%-E      EmitSOP(opr.sbireg);
//	         Emit1Code(%iGRP2+1%); EncodeEA(opr,iPUSHM);
//	      endrepeat;
//	end;
//
//
//	Routine EmitPOPM; import ref(Qfrm4) qi;
//	----------- POPM    reg const opr ------------  Format 4
//	begin infix(MemAddr) opr; range(0:MaxByte) reg; range(0:MaxWord) cnst;
//	      cnst:=qi qua Qfrm4.aux.val; opr:=qi qua Qfrm4.opr;
//	      if cnst=0 then IERR("COASM:POPM 0") endif;
//	      repeat while cnst >= AllignFac
//	      do
//	%-E      CodeSpace(%5%);
//	%+E      CodeSpace(%7%);
//	%-E      EmitSOP(opr.sbireg);
//	         Emit1Code(%iPOPM%); EncodeEA(opr,0);
//	         opr.rela.val:=opr.rela.val+AllignFac; cnst:=cnst-AllignFac;
//	      endrepeat;
//	      if cnst = 0 then -- Nothing
//	%+E   elsif cnst=2
//	%+E   then CodeSpace(%9%); reg:=iReg(%qi.reg%);
//	%+E        Emit1Code(%bOR(iPOP,reg)%); Emit1Code(%iOSP%);
//	%+E        Emit1Code(%bOR(iMOV,1)%); EncodeEA(opr,reg);
//	%+E   elsif cnst=3 then IERR("COASM:POPM 3")
//	      else
//	%-E        CodeSpace(%6%);
//	%+E        CodeSpace(%8%);
//	           reg:=iReg(%qi.reg%); Emit1Code(%bOR(iPOP,reg)%);
//	%-E        EmitSOP(opr.sbireg);
//	           Emit1Code(%iMOV%); EncodeEA(opr,reg);
//	      endif;
//	end;
//
//	Routine EmitLOAD; ------- LOAD    subc reg opr -------------  Format 3
//	import range(0:MaxByte) subc,qreg; infix(MemAddr) opr;
//	begin range(0:MaxByte) reg,w;
//	      reg:=iReg(%qreg%); CodeSpace(%5%);
//	%-E   EmitSOP(opr.sbireg);
//	%-E   if qreg >= qES
//	%-E   then Emit1Code(%iMOVSM%); EncodeEA(opr,sReg(%qreg%));
//	%-E   else
//	           w:=wBIT(qreg);
//	%+E        if subc = 0 then                        --- TEMP FOR TEST
//	%+E        if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	%+E        endif;                                  --- TEMP FOR TEST
//	%+E        if subc <> 0
//	%+E        then Emit1Code(%iGRP3%);
//	%+E             if subc=qSEXT then Emit1Code(%bOR(iMOVSX,w)%);
//	%+E             else Emit1Code(%bOR(iMOVZX,w)%) endif;
//	%+E             EncodeEA(opr,reg);
//	%-E        if (reg=iA) and (bAND(opr.sbireg,rmBIREG)=0)
//	%+E        elsif (reg=iA) and (opr.sibreg=NoIBREG)
//	           then Emit1Code(%bOR(iMOVA,w)%);
//	%-E             EmitAddr(opr,F_OFFSET);
//	%+E             EmitAddr(opr);
//	           else Emit1Code(%bOR(bOR(iMOV,2),w)%); --iMOV or 10B or w
//	                EncodeEA(opr,reg);
//	           endif;
//	%-E   endif;
//	end;
//
//	Routine EmitSTORE; ------- STORE   reg opr -----------------  Format 3
//	import range(0:MaxByte) qreg; infix(MemAddr) opr;
//	begin range(0:MaxByte) reg,w;
//	      reg:=iReg(%qreg%); CodeSpace(%5%);
//	%-E   EmitSOP(opr.sbireg);
//	%+E   if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	%-E   if qreg >= qES
//	%-E   then Emit1Code(%iMOVMS%); EncodeEA(opr,sReg(%qreg%));
//	%-E   else
//	           w:=wBIT(qreg);
//	%-E        if (reg=iA) and (bAND(opr.sbireg,rmBIREG)=0)
//	%+E        if (reg=iA) and (opr.sibreg=NoIBREG)
//	           then Emit1Code(%bOR(bOR(iMOVA,2),w)%); -- iMOVA or 10B or w
//	%-E             EmitAddr(opr,F_OFFSET);
//	%+E             EmitAddr(opr);
//	           else Emit1Code(%bOR(iMOV,w)%);
//	                EncodeEA(opr,reg);
//	           endif;
//	%-E   endif;
//	end;
//
//	Routine EmitRSTR; import ref(Qfrm2) qi; range(0:1) w;
//	----------- RSTRB   subc dir rep -------------  Format 2
//	----------- RSTRW   subc dir rep -------------  Format 2
//	begin boolean STDemitted;
//	%-E   CodeSpace(%13%); -- pje (%12%);
//	%+E   CodeSpace(%15%); -- pje (%14%);
//	--- pje  if qi.reg=qCLD then Emit1Code(%iCLD%)
//	--- pje  else Emit1Code(%iSTD%) endif;
//	      if qi.reg=qCLD then STDemitted:=false
//	      else Emit1Code(%iSTD%); STDemitted:=true endif;
//	      if qi.subc=qZERO
//	      then Emit1Code(%043%) -- 053oct=00 101 011B -- SUB CX,DI -- 2
//	           Emit1Code(%207%) -- 317oct=11 001 111B
//	           Emit1Code(%115%) -- 163oct=01 110 011B -- JNB .L1   -- 2
//	           Emit1Code(%3%)   -- 003oct=3
//	           Emit1Code(%253%) -- 375oct=11 111 101B -- STD       -- 1
//	           Emit1Code(%247%) -- 367oct=11 110 111B -- NEG CX    -- 2
//	           Emit1Code(%217%) -- 331oct=11 011 001B
//	                                                -- L1: --
//	           if w=1
//	           then Emit1Code(%bOR(iSHIFT1,1)%); Encode2(%iSHR%,%iCX%);
//	%+E             Emit1Code(%bOR(iSHIFT1,1)%); Encode2(%iSHR%,%iCX%);
//	           endif;
//	           STDemitted:=true; -- pje
//	      endif;
//	      if qi qua Qfrm2.aux.val = qREPNE
//	      then Emit1Code(%iREPNE%) else Emit1Code(%iREP%) endif;
//	      case 0:6 (qi.subc)
//	      when qRMOV:  Emit1Code(%bOR(bOR(iSTR,4),w)%)  --  4=0100B  MOVSB/W
//	      when qRCMP:  Emit1Code(%bOR(bOR(iSTR,6),w)%)  --  6=0110B  CMPSB/W
//	      when qZERO:  Emit1Code(%bOR(bOR(iSTR,10),w)%) -- 10=1010B  STOSB/W
//	      when qRCMPS: Emit1Code(%bOR(bOR(iSTR,6),w)%)  --  6=0110B  CMPSB/W
//	           Emit1Code(%176%) -- 260oct=10 110 000 -- MOV  AL,0 -- 2
//	           Emit1Code(%0%)
//	           Emit1Code(%117%) -- 165oct=01 110 101 -- JNE  $+3  -- 2
//	           Emit1Code(%  1%) -- 001oct=1
//	           Emit1Code(% 72%) -- 110oct=01 001 000 -- DEC  AX   -- 1
//	      when qRSCAS: Emit1Code(%bOR(bOR(iSTR,14),w)%) -- 14=1110B  SCASB/W
//	           if qi qua Qfrm2.aux.val = qREPNE
//	           then Emit1Code(%117%) -- 165oct=01 110 101  -- JNE  $+3
//	           else Emit1Code(%116%) -- 164oct=01 110 100  -- JE   $+3
//	           endif;
//	           Emit1Code(%  1%)      -- 001oct=1
//	           Emit1Code(% 65%)      -- 101oct=01 000 001  -- INC  CX
//	      when qRSTOS: Emit1Code(%bOR(bOR(iSTR,10),w)%) -- 10=1010B  STOSB/W
//	      endcase;
//	      if STDemitted then Emit1Code(iCLD) endif; -- pje
//	end;
//	%page
//
//	Routine EmitFPUSH; import ref(Qfrm1) qi;
//	----------- FPUSH   fSD fmf ------------------  Format 1
//	begin infix(MemAddr) opr; infix(wWORD) fs; range(0:MaxByte) fmf;
//	%+E   range(0:MaxByte) fSD,fwf,fSP;
//	%+C   if NUMID=NoNPX then IERR("COASM:NoNPX-FPUSH") endif;
//	      CodeSpace(%6%); fmf:=qi.reg;
//	%+E   if NUMID=WTLx167
//	%+E   then fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E        fSD:=qi.subc; fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E        if fwf<>fmf then EmitWTO(WxCONV(fwf,fmf),fSP,fSP) endif;
//	%+E        EmitWTPUSH(fSP); if fmf=FMF_LREAL then EmitWTPUSH(fSP+1) endif;
//	%+E   else
//	           opr.kind:=reladr; opr.rela.val:=0;
//	%-E        opr.sbireg:=SetSBIreg(oSS,qDI);
//	%+E        opr.sibreg:=bESP+NoIREG;
//	           case 0:6 (fmf)
//	           when FMF_SINT:  fs.val:=AllignFac    when FMF_INT:   fs.val:=4
//	           when FMF_REAL:  fs.val:=4            when FMF_LREAL: fs.val:=8
//	%-E        otherwise fs.val:=10 endcase;
//	%+E        otherwise fs.val:=12 endcase;
//	           ModifySP(qi,qSUB,fs); CodeSpace(%8%);
//	%-E        Emit1Code(%bOR(iMOV,3)%); Encode2(%iDI%,%iSP%); -- MOV  DI,SP
//	%-E        if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	%-E        EmitSOP(opr.sbireg);
//	           Emit1Code(%bOR(iESC1,fmf)%);
//	           if fmf=FMF_TEMP then EncodeEA(opr,iFSTPT)
//	           else EncodeEA(opr,iFSTP) endif;
//	           Emit1Code(%iWAIT%); --- Explicit Term WAIT -- TEMP
//	%+E   endif;
//	end;
//
//
//	Routine EmitFPOP; import ref(Qfrm1) qi;
//	----------- FPOP   fSD fmf ------------------  Format 1
//	begin infix(MemAddr) opr; infix(wWORD) fs; range(0:MaxByte) fmf;
//	%+E   range(0:MaxByte) fSD,fwf,fSP;
//	%+C   if NUMID=NoNPX then IERR("COASM:NoNPX-FPUSH") endif;
//	      CodeSpace(%6%); fmf:=qi.reg;
//	%+E   if NUMID=WTLx167
//	%+E   then fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E        fSD:=qi.subc; fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E        if fmf=FMF_LREAL then EmitWTPOP(fSP+1) endif; EmitWTPOP(fSP);
//	%+E        if fwf<>fmf then EmitWTO(WxCONV(fmf,fwf),fSP,fSP) endif;
//	%+E   else
//	           opr.kind:=reladr; opr.rela.val:=0;
//	%-E        opr.sbireg:=SetSBIreg(oSS,qDI);
//	%+E        opr.sibreg:=bESP+NoIREG;
//	           case 0:6 (fmf)
//	           when FMF_SINT:  fs.val:=AllignFac    when FMF_INT:   fs.val:=4
//	           when FMF_REAL:  fs.val:=4            when FMF_LREAL: fs.val:=8
//	%-E        otherwise fs.val:=10 endcase;
//	%+E        otherwise fs.val:=12 endcase;
//	           CodeSpace(%8%);
//	%-E        Emit1Code(%bOR(iMOV,3)%); Encode2(%iDI%,%iSP%); -- MOV  DI,SP
//	%-E        if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	%-E        EmitSOP(opr.sbireg);
//	           Emit1Code(%bOR(iESC1,fmf)%);
//	           if fmf=FMF_TEMP then EncodeEA(opr,iFLDT)
//	           else EncodeEA(opr,iFLD) endif;
//	           ModifySP(qi,qADD,fs);
//	%+E   endif;
//	end;
//
//	Routine EmitFMONAD; import ref(Qfrm1) qi;
//	----------- FMONAD  subc fSD -----------------  Format 1
//	begin
//	%+E   range(0:MaxWord) opc; range(0:MaxByte) fSD,fwf,fSP;
//	%+C   if NUMID=NoNPX then IERR("COASM:NoNPX-FMONAD") endif;
//	%+E   if NUMID=WTLx167
//	%+E   then fSD:=qi.reg; fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E        case 0:5 (qi.subc)
//	%+E        when qFNEG:   opc:=wsNEG  
//	%+E        when qFSQRT:  opc:=wsSQRT
//	%+E        when qFABS:   opc:=wsABS
//	%+E -- ??? when qFRND:   opc:=ws???
//	%+E -- ??? when qFREM:   opc:=ws???
//	%+E        otherwise IERR("COASM:WTL-FMONAD")
//	%+E        endcase;
//	%+E        if fwf=FMF_LREAL then opc:=opc+wdADD endif;
//	%+E        EmitWTO(opc,fSP,fSP);
//	%+E   else
//	           CodeSpace(%3%);
//	%-E        if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	           Emit1Code(%iESC1%);
//	           case 0:5 (qi.subc)
//	           when qFNEG:  Emit1Code(%iFCHS%)
//	           when qFSQRT: Emit1Code(%iFSQRT%)
//	           when qFABS:  Emit1Code(%iFABS%)
//	           when qFRND:  Emit1Code(%iFRND%)
//	           when qFREM:  Emit1Code(%iFPREM%)
//	           endcase;
//	%+E   endif;
//	end;
//	%page
//
//	Routine EmitFDYAD; import ref(Qfrm1) qi;
//	----------- FDYAD   subc fSD -----------------  Format 1
//	begin
//	%+E   range(0:MaxWord) opx,opc; range(0:MaxByte) fSD,fwf,fSP,tx,fx;
//	%+C   if NUMID=NoNPX then IERR("COASM:NoNPX-FDYAD") endif;
//	%+E   if NUMID=WTLx167
//	%+E   then fSD:=qi.reg; fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E        opx:=wsLOAD; tx:=fSP-2; fx:=fSP;
//	%+E        case 0:7 (qi.subc)
//	%+E        when qFCOM:   opc:=wsCMP  
//	%+E        when qFADD:   opc:=wsADD  
//	%+E        when qFSUB:   opc:=wsSUB;
//	%+E        when qFSUBR:  opc:=wsSUB; fx:=tx; tx:=fSP;
//	%+E        when qFMUL:   opc:=wsMUL  
//	%+E        when qFDIV:   opc:=wsDIV  
//	%+E        when qFDIVR:  opc:=wsDIV; fx:=tx; tx:=fSP;
//	%+E        endcase;
//	%+E        if fwf=FMF_LREAL then opx:=wdLOAD; opc:=opc+wdADD endif;
//	%+E        EmitWTO(opc,tx,fx);
//	%+E        if tx>fx then EmitWTO(opx,fx,tx) 
//	%+E        elsif qi.subc=qFCOM
//	%+E        then EmitWTSR(wsSTCTX,iA,0);
//	%+E             CodeSpace(%1%); Emit1Code(%iSAHF%);
//	%+E        endif;
//	%+E   else
//	           CodeSpace(%3%);
//	%-E        if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	           Emit1Code(%bOR(iESC,6)%); Encode2(%icFDYAD(qi.subc)%,%iST1%);
//	           if qi.subc = qFCOM
//	           then
//	%-E             if NUMID < iAPX287
//	%-E             then CodeSpace(%12%);
//	%-E                  Emit1Code(%iWAIT%);        -- WAIT
//	%-E                  Emit1Code(%bOR(iSOP,16)%); -- FSTSW  SS:G.TMPAREA
//	%-E                  Emit1Code(%bOR(iESC1,4)%);
//	%-E                  Emit1Code(%62%); -- EA
//	%-E                  EmitAddr(TMPAREA,F_OFFSET);
//	%-E
//	%-E                  Emit1Code(%iWAIT%);        -- WAIT
//	%-E                  Emit1Code(%bOR(iSOP,16)%); -- MOV   AX,SS:G.TMPAREA
//	%-E                  Emit1Code(%bOR(iMOVA,1)%);
//	%-E                  EmitAddr(TMPAREA,F_OFFSET);
//	%-E
//	%-E             else
//	                     CodeSpace(%3%);
//	                     Emit1Code(%bOR(iESC,7)%);  -- FSTSW  AX
//	                     Emit1Code(%224%);
//	%-E             endif;
//	                Emit1Code(%iSAHF%);             -- SAHF
//	           endif;
//	%+E   endif;
//	end;
//	%page
//
//	Routine EmitFDYADM; import ref(Qfrm4) qi;
//	----------- FDYADM  subc fmf fSD opr ---------  Format 4
//	begin infix(MemAddr) opr; range(0:MaxByte) fmf;
//	%+E   range(0:MaxWord) opx,opc;
//	%+E   range(0:MaxByte) fSD,fwf,fSP,tx,fx,qereg,ereg;
//	      opr:=qi qua Qfrm4.opr; fmf:=qi.reg;
//	%+C   if NUMID=NoNPX then IERR("COASM:NoNPX-FDYADM") endif;
//	%+E   if NUMID=WTLx167
//	%+E   then fSD:=qi qua Qfrm4.aux.LO;
//	%+E        fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E        qereg:=RegAvailable(qi);
//	%+E                                              qereg:=qEAX; --- TEMP ?????
//	%+E        if qereg<>0 then ereg:=iReg(%qereg%)
//	%+E        else CodeSpace(%1%); Emit1Code(%iPUSH%); ereg:=iA endif;
//	%+E        if fmf=FMF_SINT then EmitLOAD(qSEXT,qAX+ereg,opr)
//	%+E        else EmitLOAD(0,qEAX+ereg,opr)
//	%+E             if fmf=FMF_LREAL
//	%+E             then EmitWTLR(wsLOAD,1,ereg);
//	%+E                  opr.rela.val:=opr.rela.val+4;
//	%+E                  EmitLOAD(0,qEAX+ereg,opr);
//	%+E             endif;
//	%+E        endif;
//	%+E        opx:=wsLOAD; tx:=fSP-2; fx:=fSP;
//	%+E        case 0:7 (qi.subc)
//	%+E        when qFCOM:   opc:=wsCMP  
//	%+E        when qFADD:   opc:=wsADD  
//	%+E        when qFSUB:   opc:=wsSUB;  fx:=tx; tx:=fSP;
//	%+E        when qFSUBR:  opc:=wsSUB;
//	%+E        when qFMUL:   opc:=wsMUL  
//	%+E        when qFDIV:   opc:=wsDIV;  fx:=tx; tx:=fSP;
//	%+E        when qFDIVR:  opc:=wsDIV  
//	%+E        endcase;
//	%+E        if fwf=FMF_LREAL then opx:=wdLOAD; opc:=opc+wdADD endif;
//	%+E        if (fwf <> fmf) or (tx > fx)
//	%+E        then EmitWTLR(WxCONV(fmf,fwf),fSP,ereg);
//	%+E             EmitWTO(opc,tx,fx);
//	%+E             if tx>fx then EmitWTO(opx,fx,tx) endif;
//	%+E        else EmitWTLR(opc,tx,ereg) endif;
//	%+E        if qereg=0 then CodeSpace(%1%); Emit1Code(%iPOP%) endif;
//	%+E        if qi.subc=qFCOM
//	%+E        then EmitWTSR(wsSTCTX,iA,0);
//	%+E             CodeSpace(%1%); Emit1Code(%iSAHF%);
//	%+E        endif;
//	%+E   else
//	           CodeSpace(%6%);
//	%+D        if fmf=FMF_TEMP then IERR("COASM.FDYADM treal") endif;
//	%-E        if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	%-E        EmitSOP(opr.sbireg);
//	           Emit1Code(%bOR(iESC,fmf)%);
//	           EncodeEA(opr,icFDYAD(qi.subc));
//	           if qi.subc = qFCOM
//	           then
//	%-E             if NUMID < iAPX287
//	%-E             then CodeSpace(%12%);
//	%-E                  Emit1Code(%iWAIT%);        -- WAIT
//	%-E                  Emit1Code(%bOR(iSOP,16)%); -- FSTSW  SS:G.TMPAREA
//	%-E                  Emit1Code(%bOR(iESC1,4)%);
//	%-E                  Emit1Code(%62%); -- EA
//	%-E                  EmitAddr(TMPAREA,F_OFFSET);
//	%-E
//	%-E                  Emit1Code(%iWAIT%);        -- WAIT
//	%-E                  Emit1Code(%bOR(iSOP,16)%); -- MOV   AX,SS:G.TMPAREA
//	%-E                  Emit1Code(%bOR(iMOVA,1)%);
//	%-E                  EmitAddr(TMPAREA,F_OFFSET);
//	%-E
//	%-E             else
//	                     CodeSpace(%3%);
//	                     Emit1Code(%bOR(iESC,7)%);  -- FSTSW  AX
//	                     Emit1Code(%224%);
//	%-E             endif;
//	                Emit1Code(%iSAHF%);             -- SAHF
//	           endif;
//	%+E   endif;
//	end;
//	%title ***   Q - C o d e   ===>   I - C o d e   ***
//	Routine QtoI;
//	import ref(Qpkt) qi; range(0:MaxWord) qcnt; export ref(Qpkt) qnxt;
//	begin range(0:nregs) qreg,qreg2;  -- Q-Code registers
//	      range(0:7) reg,reg2;        -- I-Code registers
//	      range(0:1) w;               -- I-Code memory operand width
//	      range(0:255) s;             -- I-Code sign extension bit 0/2
//	      range(0:20) subc;           -- Operation SubCode
//	      range(0:MaxByte) fmf;       -- Floating Memory Format
//	      infix(wWORD) cnst;
//	      range(0:MaxWord) Offset,i;
//	      infix(MemAddr) opr;
//	      Boolean CnstTail,OvflTail;
//	      infix(WORD) segid,ix,fix; infix(Fixup) Fx;
//	      ref(Qfrm6) dst;
//	%+E   range(0:MaxWord) opc; range(0:MaxByte) fSD,fwf,fSP,qereg,ereg;
//	%+D   infix(wWORD) rela; range(0:MaxWord) write;
//	%+E   infix(wWORD) ofst32;
//
//	NXT:
//
//	%+D   RST(R_QtoI);
//	%+D   if qi=none then IERR("COASM:QtoI(none)") endif;
//	%+D %-E  rela.val:=CBUF.ofst.val+CBUF.nxt;
//	%+DE  rela.HighWord.val:=CBUF.ofstHI;
//	%+DE  rela.LowWord.val:=CBUF.ofstLO;
//	%+DE  rela.val:=rela.val+CBUF.nxt;
//	      qnxt:=qi.next; subc:=qi.subc;
//	      case 0:qMXX (qi.fnc);
//	      when qPUSHR: ------ PUSHR   reg ----------------------  Format 1
//	           CodeSpace(%1%);
//	%-E        if subc < qES then
//	                Emit1Code(%bOR(iPUSH,iReg(%subc%))%)
//	%-E        else Emit1Code(%bOR(iPUSHS,bSHL(sReg(%subc%),3))%) endif;
//	      when qPOPR: ------- POPR    reg ----------------------  Format 1
//	           CodeSpace(%1%);
//	%-E        if subc < qES then
//	                Emit1Code(%bOR(iPOP,iReg(%subc%))%)
//	%-E        else Emit1Code(%bOR(iPOPS,bSHL(sReg(%subc%),3))%) endif;
//	      when qPUSHC: ------ PUSHC   reg const ----------------  Format 2
//	                   ------ PUSHC   reg fld addr -------------  Format 2b
//	           EmitPUSHC(qi);
//	      when qPUSHA: ------ PUSHA   reg opr ------------------  Format 3
//	           reg:=iReg(%qi.reg%);
//	           opr:=qi qua Qfrm3.opr; CodeSpace(%5%);
//	           Emit1Code(%iLEA%); EncodeEA(opr,reg);
//	           Emit1Code(%bOR(iPUSH,reg)%)
//	      when qPUSHM: ------ PUSHM   const opr ----------------  Format 4
//	           EmitPUSHM(qi);
//	      when qPOPK: ------- POPK    const --------------------  Format 2
//	           cnst.val:=wAllign(%qi qua Qfrm2.aux.val%);
//	%+D        if cnst.val=0 then IERR("COASM:POPK 0") endif;
//	           ModifySP(qi,qADD,cnst);
//	      when qPOPM: ------- POPM    reg const opr ------------  Format 4
//	           EmitPOPM(qi);
//	      when qLOADC: ------ LOADC   reg const ----------------  Format 2
//	                   ------ LOADC   reg fld addr -------------  Format 2b
//	           if qi.kind=K_Qfrm2
//	           then LoadCnst(qi.reg,qi qua Qfrm2.aux,RegDies(qi,uF));
//	           else CodeSpace(%5%); reg:=iReg(%qi.reg%);
//	                Emit1Code(%bOR(bOR(iMOVI,8),reg)%); -- 8=1000B  i.e. w=1
//	%-E             EmitAddr(qi qua Qfrm2b.addr,qi qua Qfrm2b.aux.val);
//	%+E             EmitAddr(qi qua Qfrm2b.addr);
//	           endif;
//	%-E   when qLOADSC: ------ LOADSC sreg reg fld addr --------  Format 2b
//	%-E        reg:=iReg(%qi.reg%);
//	%-E        CodeSpace(%7%);
//	%-E        Emit1Code(%bOR(bOR(iMOVI,8),reg)%); -- 8=1000B  i.e. w=1
//	%-E        EmitAddr(qi qua Qfrm2b.addr,F_BASE);
//	%-E        Emit1Code(%iMOVSM%); Encode2(%sReg(%subc%)%,%reg%)
//	%-E %+D    if AdrSegid(qi qua Qfrm2b.addr)=DGROUP.val
//	%-E %+D    then IERR("LOADSC r,DGROUP") endif;
//	      when qLOADA: ------ LOADA   reg opr ------------------  Format 3
//	           CodeSpace(%4%); Emit1Code(%iLEA%);
//	           EncodeEA(qi qua Qfrm3.opr,iReg(%qi.reg%));
//	%-E   when qLDS: -------- LDS     reg ofst opr nrep --------  Format 4c
//	%-E        i:=qi qua Qfrm4c.nrep;
//	%-E        opr:=qi qua Qfrm3.opr; CodeSpace(%5%);
//	%-E        EmitSOP(opr.sbireg); Emit1Code(%iLDS%);
//	%-E        EncodeEA(opr,iReg(%qi.reg%));
//	%-E        if i <> 0
//	%-E        then opr.kind:=reladr; opr.rela:=qi qua Qfrm4c.aux;
//	%-E             opr.sbireg:=SetSBIreg(oDS,qi.reg);
//	%-E             repeat i:=i-1; CodeSpace(%5%);
//	%-E                    EmitSOP(opr.sbireg); Emit1Code(%iLDS%);
//	%-E                    EncodeEA(opr,iReg(%qi.reg%));
//	%-E             while i <> 0 do endrepeat;
//	%-E        endif;
//	%-E   when qLES: -------- LES     reg ofst opr nrep --------  Format 4c
//	%-E        i:=qi qua Qfrm4c.nrep;
//	%-E        opr:=qi qua Qfrm3.opr; CodeSpace(%5%);
//	%-E        EmitSOP(opr.sbireg); Emit1Code(%iLES%);
//	%-E        EncodeEA(opr,iReg(%qi.reg%));
//	%-E        if i <> 0
//	%-E        then opr.kind:=reladr; opr.rela:=qi qua Qfrm4c.aux;
//	%-E             opr.sbireg:=SetSBIreg(oES,qi.reg);
//	%-E             repeat i:=i-1; CodeSpace(%5%);
//	%-E                    EmitSOP(opr.sbireg); Emit1Code(%iLES%);
//	%-E                    EncodeEA(opr,iReg(%qi.reg%));
//	%-E             while i <> 0 do endrepeat;
//	%-E        endif;
//	%+E   when qBOUND: ------ BOUND   reg opr ------------------  Format 3
//	%+E        reg:=iReg(%qi.reg%); CodeSpace(%5%);
//	%+E        if bAND(qi.reg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	%+E        Emit1Code(%98%); EncodeEA(qi qua Qfrm3.opr,reg);
//	      when qLOAD: ------- LOAD    subc reg ofst opr nrep ---  Format 4c
//	%+E        i:=qi qua Qfrm4c.nrep;
//	           EmitLOAD(subc,qi.reg,qi qua Qfrm3.opr);
//	%+E        if i <> 0
//	%+E        then opr.kind:=reladr; opr.rela:=qi qua Qfrm4c.aux;
//	%+E             opr.sibreg:=iReg(%qi.reg%)+NoIREG;
//	%+E             repeat i:=i-1; EmitLOAD(0,qi.reg,opr);
//	%+E             while i <> 0 do endrepeat;
//	%+E        endif;
//	      when qSTORE: ------ STORE   reg opr ------------------  Format 3
//	           EmitSTORE(qi.reg,qi qua Qfrm3.opr);
//	      when qMOV: -------- MOV     subc reg reg2 ------------  Format 2
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           qreg2:=qi qua Qfrm2.aux.val;  reg2:=iReg(%qreg2%);
//	           CodeSpace(%3%);
//	%-E        if    qreg  >= qES
//	%-E        then if qreg2 >= qES -- MOV Sr1,Sr2 ==> PUSH Sr2, POP Sr1
//	%-E             then Emit1Code(%bOR(iPUSHS,bSHL(sReg(%qreg2%),3))%);
//	%-E                  Emit1Code(%bOR(iPOPS,bSHL(sReg(%qreg%),3))%);
//	%-E             else Emit1Code(%iMOVSM%); reg:=sREG(%qreg%)
//	%-E                  Encode2(%reg%,%reg2%)
//	%-E             endif;
//	%-E        elsif qreg2 >= qES
//	%-E        then Emit1Code(%iMOVMS%); reg2:=reg; reg:=sREG(%qreg2%);
//	%-E             Encode2(%reg%,%reg2%)
//	%-E        else
//	                w:=wBIT(qreg);
//	%+E             if subc <> 0
//	%+E             then Emit1Code(%iGRP3%);
//	%+E                  if subc=qSEXT
//	%+E                  then Emit1Code(%bOR(iMOVSX,w)%);
//	%+E                  else Emit1Code(%bOR(iMOVZX,w)%) endif;
//	%+E             else
//	%+E                  if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	                     Emit1Code(%bOR(bOR(iMOV,2),w)%); -- iMOV or d or w
//	%+E             endif;
//	                Encode2(%reg%,%reg2%)
//	%-E        endif;
//	      when qMOVMC: ------ MOVMC   width const opr ----------  Format 4
//	                   ------ MOVMC   width fld opr addr -------  Format 4b
//	           opr:=qi qua Qfrm4.opr; CodeSpace(%7%);
//	%-E        EmitSOP(opr.sbireg);
//	%+E        if bAND(qi.reg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	           w:=wBIT(qi.reg);
//	           if qi.kind = K_Qfrm4
//	           then cnst:=qi qua Qfrm4.aux;
//	                Emit1Code(%bOR(iMOVMI,w)%);
//	                EncodeEA(opr,0);
//	%-E             CodeSpace(%2%);
//	%-E             if w=1 then Emit2Code(%cnst%)
//	%-E             else Emit1Code(%cnst.LO%) endif;
//	%+E             CodeSpace(%4%);
//	%+E             if w=1
//	%+E             then if qi.reg >= qw_D then Emit4Code(%cnst%)
//	%+E                  else Emit2Code(%cnst.LowWord%) endif;
//	%+E             else Emit1Code(%cnst.LO%) endif;
//	           else Emit1Code(%bOR(iMOVMI,w)%); EncodeEA(opr,0);
//	                if w=0 then IERR("COASM.MOVMC Low-byte relocate") endif;
//	%-E             EmitAddr(qi qua Qfrm4b.addr,qi qua Qfrm4b.aux.val);
//	%+E             EmitAddr(qi qua Qfrm4b.addr);
//	           endif;
//	      when qXCHG: ------- XCHG    reg reg2 -----------------  Format 2
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           qreg2:=qi qua Qfrm2.aux.val;  reg2:=iReg(%qreg2%);
//	           w:=wBIT(qreg);
//	           CodeSpace(%2%);
//	           if (w=1) and ((reg=iA) or (reg2=iA))
//	           then Emit1Code(%bOR(iXCHGA,reg+reg2-iA)%);
//	           else Emit1Code(%bOR(iXCHG,w)%); Encode2(%reg%,%reg2%);
//	           endif;
//	      when qXCHGM: ------ XCHGM   reg opr ------------------  Format 3
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           opr:=qi qua Qfrm3.opr; CodeSpace(%5%);
//	           w:=wBIT(qreg);
//	%-E        EmitSOP(opr.sbireg);
//	%+E        if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	           Emit1Code(%bOR(iXCHG,w)%); EncodeEA(opr,reg);
//	      when qMONADR: ----- MONADR  subc reg -----------------  Format 2
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           CodeSpace(%3%); OvflTail:=false;
//	           w:=wBIT(qreg);
//	           if subc>8 then subc:=subc-7; OvflTail:=TSTOFL endif;
//	           if subc=qINC then goto ID1 elsif subc=qDEC
//	           then ID1: if (w=0) and (reg<4)  -- 4=100B
//	                then if RegDies(qi,uMask(HighPartL(qreg)))
//	                     then w:=1 endif endif;
//	                if w=1
//	                then if subc=qDEC then Emit1Code(%bOR(iDEC,reg)%);
//	                     else Emit1Code(%bOR(iINC,reg)%) endif;
//	                else Emit1Code(%iGRP2%);
//	                     if subc=qDEC then Encode2(%1%,%reg%)
//	                     else Encode2(%0%,%reg%) endif;
//	                endif;
//	           elsif (subc >= qSHL1) and (subc <> qNEGM)
//	           then Emit1Code(%bOR(iSHIFT1,w)%);
//	                if subc=qSHL1 then Encode2(%iSHL%,%reg%);
//	                elsif subc=qSHR1 then Encode2(%iSHR%,%reg%);
//	                else Encode2(%iSAR%,%reg%) endif;
//	           else -- NOT or NEG or NEGM --
//	                Emit1Code(%bOR(iGRP1,w)%);
//	                if subc=qNOT then Encode2(%2%,%reg%)         -- 2=010B
//	                             else Encode2(%3%,%reg%) endif;  -- 3=011B
//	           endif;
//	           if OvflTail then CodeSpace(%1%); Emit1Code(%iINTO%) endif;
//	      when qMONADM: ----- MONADM  subc width opr -----------  Format 3
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           OvflTail:=false;
//	           w:=wBIT(qreg);
//	           if subc>8 then subc:=subc-7; OvflTail:=TSTOFL endif;
//	           opr:=qi qua Qfrm3.opr; CodeSpace(%6%);
//	%-E        EmitSOP(opr.sbireg);
//	%+E        if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	           if subc=qINC then goto ID2 elsif subc=qDEC
//	           then ID2: Emit1Code(%bOR(iGRP2,w)%);
//	                if subc=qDEC then reg:=1 else reg:=0 endif;
//	           elsif (subc >= qSHL1) and (subc <> qNEGM)
//	           then Emit1Code(%bOR(iSHIFT1,w)%);
//	                if subc=qSHL1 then reg:=iSHL;
//	                elsif subc=qSHR1 then reg:=iSHR;
//	                else reg:=iSAR endif;
//	           else -- NOT or NEG or NEGM --
//	                Emit1Code(%bOR(iGRP1,w)%);
//	                if subc=qNOT then reg:=2     --2=010B
//	                else reg:=3 endif;           --3=011B
//	           endif;
//	           EncodeEA(opr,reg);
//	           if OvflTail then CodeSpace(%1%); Emit1Code(%iINTO%) endif;
//	      when qDYADR: ------ DYADR   subc reg reg2 ------------  Format 2
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           OvflTail:=false;
//	           if subc>=qADDF then OvflTail:=TSTOFL endif;
//	           qreg2:=qi qua Qfrm2.aux.val;  reg2:=iReg(%qreg2%);
//	           w:=wBIT(qreg);
//	           CodeSpace(%7%);
//	%+E        if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	           Emit1Code(%bOR(iDYAD,bOR(bSHL(icDYAD(subc),3),w))%);
//	           Encode2(%reg%,%reg2%);
//	%-E        if subc=qINCO then goto ID3 elsif subc=qDECO
//	%-E        then ID3: EmitInco(subc,reg) endif;
//	           if OvflTail then CodeSpace(%1%); Emit1Code(%iINTO%) endif;
//	      when qSHIFT: ------ SHIFT   subc reg CL --------------  Format 2
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           w:=wBIT(qreg);
//	           CodeSpace(%2%); Emit1Code(%bOR(iSHIFT,w)%);
//	           if subc=qSHL then Encode2(%iSHL%,%reg%);
//	           elsif subc=qSHR then Encode2(%iSHR%,%reg%);
//	           else Encode2(%iSAR%,%reg%) endif;
//	      when qDYADC: ------ DYADC   subc reg const -----------  Format 2
//	                   ------ DYADC   subc reg fld addr --------  Format 2b
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           CnstTail:=false; OvflTail:=false;
//	           w:=wBIT(qreg);
//	           if subc>=qADDF then OvflTail:=TSTOFL endif;
//	           if qi.kind=K_Qfrm2
//	           then cnst:=qi qua Qfrm2.aux;
//	                s:=SignExt(cnst); CnstTail:=true;
//	           else s:=0; w:=1 endif;
//	           if reg=iA
//	           then CodeSpace(%2%);
//	%+E             if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	                Emit1Code(%bOR(iDYADA,bOR(bSHL(icDYAD(subc),3),w))%);
//	                s:=0;
//	           elsif (reg=iSP) and CnstTail
//	                 and ((subc=qADD) or (subc=qSUB))
//	           then ModifySP(qi,subc,cnst); CnstTail:=false;
//	           else CodeSpace(%3%);
//	%+E             if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	                Emit1Code(%bOR(iDYADI,bOR(s,w))%);
//	                Encode2(%icDYAD(subc)%,%reg%);
//	           endif;
//	           if qi.kind=K_Qfrm2b
//	           then
//	%-E             EmitAddr(qi qua Qfrm2b.addr,qi qua Qfrm2b.aux.val);
//	%+E             if bAND(qreg,qw_W)<>0 then IERR("DYADC-16a") endif;
//	%+E             EmitAddr(qi qua Qfrm2b.addr);
//	           endif
//	           if CnstTail
//	           then
//	%-E             CodeSpace(%2%);
//	%-E             if (w=1) and (s=0) then Emit2Code(%cnst%)
//	%-E             else Emit1Code(%cnst.LO%) endif;
//	%+E             CodeSpace(%4%);
//	%+E             if (w=1) and (s=0)
//	%+E             then if bAND(qreg,qw_W)=0 then Emit4Code(%cnst%)
//	%+E                  else Emit2Code(%cnst.LowWord%) endif;
//	%+E             else Emit1Code(%cnst.LO%) endif;
//	           endif;
//	%-E        if subc=qINCO then goto ID4 elsif subc=qDECO
//	%-E        then ID4: EmitInco(subc,reg) endif;
//	           if OvflTail then CodeSpace(%1%); Emit1Code(%iINTO%) endif;
//	      when qDYADM: ------ DYADM   subc reg opr -------------  Format 3
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           OvflTail:=false;
//	           if subc>=qADDF then OvflTail:=TSTOFL endif;
//	           opr:=qi qua Qfrm3.opr; CodeSpace(%2%);
//	           w:=wBIT(qreg);
//	%-E        EmitSOP(opr.sbireg);
//	%+E        if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	           Emit1Code(%bOR(iDYAD,bOR(bSHL(icDYAD(subc),3),w))%);
//	           EncodeEA(opr,reg);
//	%-E        if subc=qINCO then goto ID5 elsif subc=qDECO
//	%-E        then ID5: EmitInco(subc,reg) endif;
//	           if OvflTail then CodeSpace(%1%); Emit1Code(%iINTO%) endif;
//	      when qDYADMC: ----- DYADMC  subc width const opr -----  Format 4
//	                    ----- DYADMC  subc width fld opr addr --  Format 4b
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           OvflTail:=false;
//	           if subc>=qADDF then OvflTail:=TSTOFL endif;
//	           opr:= qi qua Qfrm4.opr;
//	%-E        CodeSpace(%8%);
//	%+E        CodeSpace(%10%);
//	           w:=wBIT(qreg);
//	%-E        EmitSOP(opr.sbireg);
//	%+E        if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	           if qi.kind = K_Qfrm4
//	           then cnst:=qi qua Qfrm4.aux; s:=SignExt(cnst);
//	                Emit1Code(%bOR(iDYADI,bOR(s,w))%);
//	                EncodeEA(opr,icDYAD(subc));
//	%-E             if (w=1) and (s=0) then Emit2Code(%cnst%)
//	%-E             else Emit1Code(%cnst.LO%) endif;
//	%+E             if (w=1) and (s=0)
//	%+E             then if qi.reg >= qw_D then Emit4Code(%cnst%)
//	%+E                  else Emit2Code(%cnst.LowWord%) endif;
//	%+E             else Emit1Code(%cnst.LO%) endif;
//	           else Emit1Code(%bOR(iDYADI,w)%);
//	                EncodeEA(opr,icDYAD(subc));
//	                if w=0 then IERR("COASM.DYADMC Low-byte relocate") endif;
//	%-E             EmitAddr(qi qua Qfrm4b.addr,qi qua Qfrm4b.aux.val)
//	%+E             EmitAddr(qi qua Qfrm4b.addr)
//	           endif;
//	%+D        if (subc=qINCO) or (subc=qDECO)
//	%+D        then IERR("COASM: DYADMC INCO/DECO") endif;
//	           if OvflTail then CodeSpace(%1%); Emit1Code(%iINTO%) endif;
//	      when qDYADMR: ----- DYADMR  subc reg opr -------------  Format 3
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           OvflTail:=false;
//	           if subc>=qADDF then OvflTail:=TSTOFL endif;
//	           opr:=qi qua Qfrm3.opr; CodeSpace(%6%);
//	           w:=wBIT(qreg);
//	%-E        EmitSOP(opr.sbireg);
//	%+E        if bAND(qreg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	           Emit1Code(%bAND(253,    --- 253 = 375oct = 11 111 101B
//	                    bOR(iDYAD,bOR(bSHL(icDYAD(subc),3),w)) )%);
//	           EncodeEA(opr,reg);
//	%+D        if (subc=qINCO) or (subc=qDECO)
//	%+D        then IERR("COASM: DYADMR INCO/DECO") endif;
//	           if OvflTail then CodeSpace(%1%); Emit1Code(%iINTO%) endif;
//	      when qTRIADR: ----- TRIADR  subc reg -----------------  Format 2
//	           qreg:=qi.reg; reg:=iReg(%qreg%);
//	           OvflTail:=false; subc:=bAND(subc,11); -- 11=1011B
//	           if subc <> qi.subc then OvflTail:=TSTOFL endif;
//	           w:=wBIT(qreg);
//	           CodeSpace(%3%); Emit1Code(%bOR(iGRP1,w)%);
//	              i:=BOOL2BYTE(bAND(subc,7) <> qWMUL);
//	              i:=bAND(i,2);
//	              i:=bOR(i,bAND(BOOL2BYTE(subc>7),1));
//	           i:=bOR(i,4);    -- 4=100B
//	           Encode2(%i%,%reg%);
//	           if OvflTail then CodeSpace(%1%); Emit1Code(%iINTO%) endif;
//	      when qTRIADM: ----- TRIADM  subc width opr -----------  Format 3
//	           OvflTail:=false; subc:=bAND(subc,11); -- 11=1011B
//	           if subc <> qi.subc then OvflTail:=TSTOFL endif;
//	           opr:=qi qua Qfrm3.opr; CodeSpace(%6%);
//	           w:=wBIT(qi.reg);
//	%-E        EmitSOP(opr.sbireg);
//	%+E        if bAND(qi.reg,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	           Emit1Code(%bOR(iGRP1,w)%);
//	                i:=BOOL2BYTE(bAND(subc,7) <> qWMUL);
//	                i:=bAND(i,2);
//	                i:=bOR(i,bAND(BOOL2BYTE(subc>7),1));
//	           EncodeEA(opr,bOR(i,4));    -- 4=100B
//	           if OvflTail then CodeSpace(%1%); Emit1Code(%iINTO%) endif;
//	      when qCWD: -------- CWD  width -----------------------  Format 1
//	%-E        CodeSpace(%1%); Emit1Code(%iCWD%);
//	%+E        CodeSpace(%2%);
//	%+E        if bAND(subc,qw_W)<>0 then Emit1Code(%iOSP%) endif;
//	%+E        Emit1Code(%bOR(iCBW,wBIT(subc))%);
//	      when qCONDEC: ----- CONDEC  subc reg -----------------  Format 2
//	           reg:=iReg(%qi.reg%);
//	           CodeSpace(%3%);
//	%-E        Emit1Code(%bOR(iJCOND,icCOND(NotQcond(subc)))%);
//	%-E        Emit1Code(%1%); Emit1Code(%bOR(iDEC,reg)%);
//
//	%+E        Emit1Code(%bOR(iJCOND,icCOND(NotQcond(subc)))%);
//	%+E        Emit1Code(%1%); Emit1Code(%bOR(iDEC,reg)%);
//
//	--??? %+E        Emit1Code(%iGRP3%);
//	--??? %+E        Emit1Code(%bOR(iSETB,icCOND(subc))%);
//	--??? %+E        Encode2(%0%,%reg%);
//	      when qRSTRB: ------ RSTRB   subc dir rep -------------  Format 2
//	           EmitRSTR(qi,0)
//	      when qRSTRW: ------ RSTRW   subc dir rep -------------  Format 2
//	           EmitRSTR(qi,1)
//	      when qLAHF: ------- LAHF -----------------------------  Format 1
//	           CodeSpace(%1%); Emit1Code(%iLAHF%);
//	      when qSAHF: ------- SAHF -----------------------------  Format 1
//	           CodeSpace(%1%); Emit1Code(%iSAHF%);
//	      when qFDEST: ------ FDEST   subc fix rela jmp --------  Format 6
//	           EmitFDEST(qi);
//	      when qBDEST: ------ BDEST   subc labno rela ----------  Format 6
//	%-E        qi qua Qfrm6.rela.val:=CBUF.ofst.val+CBUF.nxt;
//	%+E        ofst32.HighWord.val:=CBUF.ofstHI;
//	%+E        ofst32.LowWord.val:=CBUF.ofstLO;
//	%+E        qi qua Qfrm6.rela.val:=ofst32.val+CBUF.nxt;
//	%-D        goto E1;
//	      when qLABEL: ------ LABEL   subc fix smbx ------------  Format 6
//	           EmitLABEL(qi);
//	      when qJMP: -------- JMP     subc addr dst ------------  Format 5
//	           EmitJMP(qi);
//	%-D        if qi qua Qfrm5.dst<>none then qi.next:=none; goto E2 endif;
//	      when qJMPM: ------- JMPM    opr ----------------------  Format 3
//	           opr:=qi qua Qfrm3.opr; CodeSpace(%2%);
//	%-E        EmitSOP(opr.sbireg);
//	           Emit1Code(%iGRP2+1%); EncodeEA(opr,iJMPI);
//	%-E   when qJMPFM: ------ JMPFM   opr ----------------------  Format 3
//	%-E        opr:=qi qua Qfrm3.opr; CodeSpace(%2%);
//	%-E        EmitSOP(opr.sbireg);
//	%-E        Emit1Code(%iGRP2+1%); EncodeEA(opr,iJMPFI);
//	      when qCALL: ------- CALL    subc pxlng addr ----------  Format 5
//	           EmitRutCall(qi);
//	      when qADJST: ------ ADJST   const --------------------  Format 2
//	      when qENTER: ------ ENTER   const --------------------  Format 2
//	           EmitENTER(qi);
//	      when qLEAVE: ------ LEAVE   const --------------------  Format 2
//	           EmitLEAVE(qi);
//	      when qRET: -------- RET     const --------------------  Format 2
//	           cnst:=qi qua Qfrm2.aux; CodeSpace(%3%);
//	%-E        if cnst.val=0
//	%-E        then if qi.subc=0
//	%-E             then Emit1Code(%iRETF0%) else Emit1Code(%iRET0%) endif;
//	%-E        else if qi.subc=0
//	%-E             then Emit1Code(%iRETF%) else Emit1Code(%iRET%) endif;
//	%-E             Emit2Code(%cnst.LowWord%);
//	%-E        endif
//	---        Emit1Code(%iRETF%); Emit2Code(%cnst.LowWord%);
//	%+E        Emit1Code(%iRET%);  Emit2Code(%cnst.LowWord%);
//	%-E   when qDOS2: ------- DOS2 -----------------------------  Format 1
//	%-E        EmitDOS2(qi);
//	      when qINT: -------- INT     const --------------------  Format 2
//	           CodeSpace(%2%); Emit1Code(%iINT%);
//	           Emit1Code(%qi qua Qfrm2.aux.val%);
//	      when qIRET: ------- IRET -----------------------------  Format 1
//	           CodeSpace(%1%); Emit1Code(%207%)
//	      when qFPUSH: ------ FPUSH   fSD fmf ------------------  Format 1
//	           EmitFPUSH(qi);
//	      when qFPOP: ------- FPOP    fSD fmf ------------------  Format 1
//	           EmitFPOP(qi);
//	%-E   when qFLDC: ------- FLDC    sreg fmf val -------------  Format 3+
//	%+E   when qFLDC: ------- FLDC    fSD  fmf val -------------  Format 3+
//	%+C        if NUMID=NoNPX then IERR("COASM:NoNPX-FLDC") endif;
//	%+E        if NUMID=WTLx167
//	%+E        then EmitWFLDC(qi)
//	%+E        else
//	                ConvertFLDCtoFLD(qi); goto LFLD;
//	%+E        endif;
//	      when qFLD: -------- FLD     fSD fmf opr --------------  Format 3
//	%+C        if NUMID=NoNPX then IERR("COASM:NoNPX-FLD") endif;
//	LFLD:      opr:=qi qua Qfrm3.opr; fmf:=qi.reg;
//	%+E        if NUMID=WTLx167
//	--   %+E        then EmitWFLD(subc,fmf,RegAvailable(qi),opr)
//	%+E        then EmitWFLD(subc,fmf,qEAX,opr)   ---  TEMP  ???????
//	%+E        else
//	                CodeSpace(%6%);
//	%-E             if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	%-E             EmitSOP(opr.sbireg);
//	                Emit1Code(%bOR(iESC1,fmf)%);
//	                if fmf=FMF_TEMP then EncodeEA(opr,iFLDT)
//	                else EncodeEA(opr,iFLD) endif;
//	%+E        endif;
//	      when qFDUP: ------- FDUP    fSD ----------------------  Format 1
//	%+C        if NUMID=NoNPX then IERR("COASM:NoNPX-FDUP") endif;
//	%+E        if NUMID=WTLx167
//	%+E        then fSD:=subc; fwf:=bSHR(fSD,5); fSP:=bAND(fSD,31);
//	%+E             if fwf=FMF_LREAL then EmitWTO(wdLOAD,fSP,fSP-2)
//	%+E             else EmitWTO(wsLOAD,fSP,fSP-2) endif;
//	%+E        else
//	                CodeSpace(%3%);
//	%-E             if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	                Emit1Code(%iESC1%); Encode2(%iFLD%,%iST%);
//	%+E        endif;
//	      when qFST: -------- FST     fSD fmf opr --------------  Format 3
//	%+C        if NUMID=NoNPX then IERR("COASM:NoNPX-FST") endif;
//	           opr:=qi qua Qfrm3.opr; fmf:=qi.reg;
//	%+E        if NUMID=WTLx167
//	--   %+E        then EmitWFST(subc,fmf,RegAvailable(qi),opr)
//	%+E        then EmitWFST(subc,fmf,qEAX,opr)   ---  TEMP  ?????
//	%+E        else
//	                CodeSpace(%6%);
//	%-E             if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	%-E             EmitSOP(opr.sbireg);
//	                Emit1Code(%bOR(iESC1,fmf)%); EncodeEA(opr,iFST);
//	                Emit1Code(%iWAIT%); --- Explicit Term WAIT -- TEMP
//	%+E        endif;
//	      when qFSTP: ------- FSTP    fSD fmf opr --------------  Format 3
//	%+C        if NUMID=NoNPX then IERR("COASM:NoNPX-FSTP") endif;
//	           CodeSpace(%6%); opr:=qi qua Qfrm3.opr; fmf:=qi.reg;
//	%+E        if NUMID=WTLx167
//	--   %+E        then EmitWFST(subc,fmf,RegAvailable(qi),opr)
//	%+E        then EmitWFST(subc,fmf,qEAX,opr)   --  TEMP  ?????
//	%+E        else
//	%-E             if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	%-E             EmitSOP(opr.sbireg);
//	                Emit1Code(%bOR(iESC1,fmf)%);
//	                if fmf=FMF_TEMP then EncodeEA(opr,iFSTPT)
//	                else EncodeEA(opr,iFSTP) endif;
//	                Emit1Code(%iWAIT%); --- Explicit Term WAIT -- TEMP
//	%+E        endif;
//	      when qFLDCK: ------ FLDCK   subc ---------------------  Format 1
//	%+C        if NUMID=NoNPX then IERR("COASM:NoNPX-FLDCK") endif;
//	%+E        if NUMID=WTLx167 then IERR("COASM:WTL-FLDCK")
//	%+E        else
//	                CodeSpace(%3%);
//	%-E             if NUMID < iAPX287 then Emit1Code(%iWAIT%) endif;
//	                Emit1Code(%iESC1%); Emit1Code(%bOR(iFLDC,subc-1)%);
//	%+E        endif;
//	      when qFMONAD: ----- FMONAD  subc fSD -----------------  Format 1
//	           EmitFMONAD(qi);
//	      when qFDYAD: ------ FDYAD   subc fSD -----------------  Format 1
//	           EmitFDYAD(qi);
//	      when qFDYADM: ----- FDYADM  subc fmf fSD opr ---------  Format 4
//	           EmitFDYADM(qi);
//	      when qWAIT: ------- WAIT -----------------------------  Format 1
//	%+C        if NUMID=NoNPX then IERR("COASM:NoNPX-WAIT") endif;
//	%+E        if NUMID=WTLx167 then IERR("COASM:WTL-WAIT")
//	%+E        else
//	                CodeSpace(%1%); Emit1Code(%iWAIT%);
//	%+E        endif;
//	---   when qEVAL: ------- EVAL -----------------------------  Format 1
//	      when qTSTOFL: ----- INTO -----------------------------  Format 1
//	           CodeSpace(%1%); Emit1Code(%iINTO%);
//	      when qLINE: ------- LINE    subc const ---------------  Format 2
//	           curline:=qi qua Qfrm2.aux.val;
//	           if LtabEntry.kind <> 0 then EmitLine endif;
//	%+D        if DEBMOD > 2 then if subc=qSTM
//	%-D        if DEBMOD > 2
//	            then CodeSpace(%2%);
//	                 Emit1Code(%iPOPF%);          -- POPF                -- 1
//	                 Emit1Code(%iPUSHF%);         -- PUSHF               -- 1
//	            endif
//	%+D        endif;
//	      endcase;
//	%+A   if asmgen or (listq2>1) then AsmListing(rela,qi,true) endif;
//	%+D   write:=wAND(qi.write,wNOT(uM));
//	%+D   if wAND(write,wNOT(uSP+uBP)) <> 0
//	%+D   then if RegDies(qi,write)
//	%+D        then case 0:qMXX (qi.fnc)
//	%+D             when qRSTRB,qRSTRW,qPOPM,qMONADM,qDYADMC,qDYADMR,
//	%+D                  qFDEST,qBDEST,qLABEL,qENTER,
//	%+D                  qJMP,qJMPM,qCALL,qINT,qRET,qIRET,qLEAVE:   -- OK
//	%+D %-E         when qDOS2,qPUSHC,qFPUSH,qFPOP,qJMPFM:          -- OK
//	%+D %+E         when qFLD,qFLDC,qFST,qFSTP,qFDYAD,qFDYADM:      -- OK
//	%+D             otherwise WARNING("Register Scope = Single Instruction");
//	%+D             endcase;
//	%+D        endif;
//	%+D   endif;
//	%+D   ChkIsize(qi,rela.val);
//	%+D   if qi.fnc = qJMP
//	%+D   then dst:=qi qua Qfrm5.dst;
//	%+D        if dst=none then DELETE(qi) else qi.next:=none endif;
//	%+D   elsif qi.fnc <> qBDEST then DELETE(qi) endif;
//	%-D   qi qua FreeObject.next:=FreeObj(qi.kind); FreeObj(qi.kind):=qi;
//	%-D   E1:E2:
//	      qcnt:=qcnt-1; if qcnt<>0 then qi:=qnxt; goto NXT endif;
//	end;
//
//	%+D Routine ChkIsize; import ref(Qpkt) qi; range(0:MaxWord) rela;
//	%+D begin range(0:MaxWord) GenSize,CalSize;
//	%+DE      infix(wWORD) ofst32;
//	%+D       CalSize:=qi.isize;
//	%+D %-E   GenSize:=CBUF.ofst.val+CBUF.nxt-rela;
//	%+DE      ofst32.HighWord.val:=CBUF.ofstHI;
//	%+DE      ofst32.LowWord.val:=CBUF.ofstLO;
//	%+DE      GenSize:=ofst32.val+CBUF.nxt-rela;
//	%+D       if (CalSize<>0) and (GenSize<>CalSize)
//	%+D       then if GenSize > CalSize
//	%+D            then EdWrd(errmsg,CalSize); Ed(errmsg," <> ");
//	%+D                 EdWrd(errmsg,GenSize); IERR(" Wrong iCodeSize ")
//	-- ??? %+D     else case 0:qMXX (qi.fnc)
//	-- ??? %+D          when qJMP,qENTER,qLEAVE,qRET,qFLDC: -- OK
//	-- ??? %+DE         when qFLD,qFST,qFSTP,qFDYAD,qFDYADM: -- OK
//	-- ??? %+D          otherwise EdWrd(errmsg,CalSize); Ed(errmsg," <> ");
//	-- ??? %+D                    EdWrd(errmsg,GenSize);
//	-- ??? %+D                    WARNING(" Wrong iCodeSize ")
//	-- ??? %+D          endcase;
//	%+D            endif;
//	%+D       endif;
//	%+D end;
//
//	%title ***   B E G A S M  /  E N D A S M   ***
//
//	Visible Routine BEGASM; import infix(WORD) CSEGNAM,DSEGNAM;
//	begin infix(wWORD) rela;
//	      if DSEGNAM=DefSymb("DGROUP") then DSEGID:=DGROUP
//	      else if DSEGNAM.val = 0 then EdSymb(sysedit,PROGID)
//	           else EdSymb(sysedit,DSEGNAM) endif;
//	           ed(sysedit,"_GLOBAL");
//	           DSEGID:=DefSegm(pickup(sysedit),aDATA);
//	      endif;
//	      InitData(DSEGID); DsegEntry:=NewPubl;
//	%-E   rela.val:=DBUF.ofst.val+DBUF.nxt
//	%+E   rela.HighWord.val:=DBUF.ofstHI;
//	%+E   rela.LowWord.val:=DBUF.ofstLO;
//	%+E   rela.val:=rela.val+DBUF.nxt
//	      PutPublic(DsegEntry,DSEGID,rela);
//	%+A   if asmgen then ListDataLabel(DsegEntry,0)
//	%+AD  elsif listsw>0 then ListDataLabel(DsegEntry,0)
//	%+A   endif;
//	      if CSEGNAM.val = 0 then EdSymb(sysedit,PROGID)
//	      else EdSymb(sysedit,CSEGNAM) endif;
//	      ed(sysedit,"_TEXT"); CSEGID:=DefSegm(pickup(sysedit),aCODE);
//	      curseg.segid:=CSEGID;
//	      if LINTAB=0 then LtabEntry:=noadr
//	      else EdSymb(sysedit,PROGID); ed(sysedit,"_LINE");
//	           LSEGID:=DefSegm(pickup(sysedit),aLINE);
//	           LtabEntry:=NewFixAdr(LSEGID,NewPubl);
//	      endif;
//	%+S   if RELID.val = 0
//	%+S   then EdSymb(sysedit,PROGID); Ed(sysedit,".obj");
//	%+S        RELID:=DefSymb(pickup(sysedit));
//	%+S   endif;
//	end;
//
//	Visible Routine ENDASM;
//	begin infix(WORD) segid,segx; range(0:3) n;
//	      --- already exhausted: Exhaust(CSEGID,true);
//	      segx.val:=DIC.nSegm;
//	      repeat while segx.val <> 0
//	      do segid:=DIC.Segm(segx.HI).elt(segx.LO); segx.val:=segx.val-1;
//	         if segid <> DSEGID then Exhaust(segid,true) endif;
//	      endrepeat;
//	      if LtabEntry.kind <> 0 then EmitLineInfo endif;
//	      segid.val:=0; InitData(segid); -- Terminate Data Buffer
//	      segx.val:=0; repeat while segx.val < DIC.nSegm
//	      do segx.val:=segx.val+1; segid:=DIC.Segm(segx.HI).elt(segx.LO);
//	-- ????  if DICREF(segid) qua Segment.rela.val <> 0 --- SEGMENT TAIL !!!
//	-- ????  then InitCode(segid); CodeSpace(%2%);
//	-- ????       Emit1Code(%144%); Emit1Code(%144%)
//	-- ????  endif;
//	         n:=AllignDiff(%DICREF(segid) qua Segment.rela.val%); -- SEGMENT TAIL
//	         if n <> 0
//	         then InitCode(segid); CodeSpace(%n%);
//	              repeat while n>0 do Emit1Code(iNOP); n:=n-1 endrepeat;
//	         endif;
//	      endrepeat;
//	      segid.val:=0; InitCode(segid); -- Terminate Code Buffer
//	%+A   if listq2 > 1 then ListENDASM endif;
//	      EnvOutByte(scrfile,0); EnvOutByte(scrfile,0); -- EOF-Mark !!!
//	      EnvClose(scrfile,nostring);
//	      if status<>0 then FILERR(scrfile,"COASM.ENDASM") endif; scrfile:=0;
//	end;
//	%title *********    I n p u t    R o u t i n e s    *********
//	 
//	Routine ReadCBUF;
//	begin infix(String) bf; range(0:MaxWord) n;
//	%+D   if TLIST > 3
//	%+D   then outstring("*** ReadCBUF: type="); outword(NXTYPE);
//	%+D        outstring(", lng="); outword(NXTLNG.val);
//	%+D        Printout(sysout);
//	%+D   endif;
//	      if NXTYPE=0 then CBUF.nxt:=0; goto E1 endif;
//	      if NXTYPE <> 2 then IERR("ReadCBUF"); CBUF.nxt:=0; goto E2 endif;
//	      CBUF.hed:=2 qua character; CBUF.nxt:=NXTLNG.val-(6+AllignFac);
//	      bf.chradr:=@CBUF.hed(4); bf.nchr:=NXTLNG.val;
//	      --- Read Buffer from File ---
//	      n:=EnvInBytes(scrfile,bf); if Status <> 0 then
//	      if Status=13 then Status:=0 else IERR("ReadCBUF") endif endif;
//	      if n <> bf.nchr then NXTYPE:=0; NXTLNG.val:=0;
//	      else NXTYPE:=CBUF.hed(CBUF.nxt+(6+AllignFac)) qua integer;
//	           NXTLNG.LO:=CBUF.hed(CBUF.nxt+(8+AllignFac)) qua integer;
//	           NXTLNG.HI:=CBUF.hed(CBUF.nxt+(9+AllignFac)) qua integer;
//	      endif;
//	E1:E2:end;
//	 
//	Routine ReadCREL;
//	begin infix(String) bf; range(0:MaxWord) n,lng;
//	      if NXTYPE <> 3 then CREL.nxt:=0; goto E1 endif;
//	%+D   if TLIST > 3
//	%+D   then outstring("*** ReadCREL: type="); outword(NXTYPE);
//	%+D        outstring(", lng="); outword(NXTLNG.val);
//	%+D        Printout(sysout);
//	%+D   endif;
//	      CREL.hed:=3 qua character; lng:=NXTLNG.val;
//	      CREL.nxt:=(NXTLNG.val-4)/Size2Word(size(RelocPkt));
//	      bf.chradr:=@CREL.hed(4); bf.nchr:=NXTLNG.val;
//	      --- Read Buffer from File ---
//	      n:=EnvInBytes(scrfile,bf); if Status <> 0 then
//	      if Status=13 then Status:=0 else IERR("ReadCBUF") endif endif;
//	      if n <> bf.nchr then NXTYPE:=0; NXTLNG.val:=0;
//	      else NXTYPE:=CREL.hed(lng) qua integer;
//	           NXTLNG.LO:=CREL.hed(lng+2) qua integer;
//	           NXTLNG.HI:=CREL.hed(lng+3) qua integer;
//	      endif;
//	E1:end;
//	%title ***  New Records for COFF Output  ***
//
//	%+F Record Ident8; info "TYPE";
//	%+F begin variant character Char(8);  -- Zero padded Symbol Name
//	%+F       variant integer Zero;       -- =0 to indicate this variant
//	%+F               integer Index;      -- String index
//	%+F end;
//
//	%+F Record CoffFileHeader;
//	%+F begin range(0:MaxWord) Magic;  -- Magic number
//	%+F       range(0:MaxWord) nSectn; -- Number of Sections
//	%+F       integer TimDat;          -- Time and Date stamp
//	%+F       integer SymPtr;          -- File pointer to Symbol Table
//	%+F       integer nSmb;            -- Number of entries in Symbol Table
//	%+F       range(0:MaxWord) OptHdr; -- Number of byte in Optional Header
//	%+F       range(0:MaxWord) Flags;  -- Flags
//	%+F end;
//
//	%+F Record CoffSectionHeader;
//	%+F begin infix(Ident8) Ident;     -- Zero padded Section Name
//	%+F       integer pAddr;           -- Physical address of Section
//	%+F       integer vAddr;           -- Virtual address of Section
//	%+F       integer Lng;             -- Section size in bytes
//	%+F       integer ScnPtr;          -- File pointer to raw Data
//	%+F       integer RelPtr;          -- File pointer to Relocation entries
//	%+F       integer LnoPtr;          -- File pointer to Line Number entries
//	%+F       range(0:MaxWord) nReloc; -- Number of Relocation entries
//	%+F       range(0:MaxWord) nLno;   -- Number of Line Number entries
//	%+F       integer Flags            -- Flags
//	%+F end;
//
//	%+F Record CoffSymbolEntry;
//	%+F begin infix(Ident8) Ident;    -- Zero padded Symbol Name or String index
//	%+F       integer val;            -- Symbol value -- Storage Class Dependent
//	%+F       range(0:MaxWord) Sectn; -- Section number of Symbol
//	%+F       range(0:MaxWord) Type;  -- Type specification
//	%+F       range(0:MaxByte) Scls;  -- Storage Class of Symbol
//	%+F       range(0:MaxByte) nAux;  -- Number of auxiliary entries
//	%+F end;
//
//	%+F Record CoffAuxSymbolEntry;
//	%+F begin
//	%+F    variant ---- FileName ----
//	%+F       character fnam(14);      -- Zero padded File Name
//	%+F       range(0:MaxWord) dum0;   -- Not Used (:=0)
//	%+F       range(0:MaxWord) dum1;   -- Not Used (:=0)
//	%+F    variant ---- Section ----
//	%+F       integer Lng;             -- Section size in bytes
//	%+F       range(0:MaxWord) nReloc; -- Number of Relocation entries
//	%+F       range(0:MaxWord) nLno;   -- Number of Line Number entries
//	%+F       integer dum2;            -- Not Used (:=0)
//	%+F       integer dum3;            -- Not Used (:=0)
//	%+F       range(0:MaxWord) dum4;   -- Not Used (:=0)
//	%+F    variant ---- Block and Function ----
//	%+F       integer dum5;            -- Not Used (:=0)
//	%+F       range(0:MaxWord) Lno;    -- Source Line Number
//	%+F       range(0:MaxWord) dum6;   -- Not Used (:=0)
//	%+F       integer dum7;            -- Not Used (:=0)
//	%+F       integer EndNdx;          -- Index of next entry past this block
//	%+F       range(0:MaxWord) dum8;   -- Not Used (:=0)
//	%+F end;
//
//	%+F Record StringTabEntry;
//	%+F begin infix(WORD) smbx;
//	%+F       ref(StringTabEntry) next;
//	%+F end;
//
//	%title *********    O u t p u t    R o u t i n e s    *********
//
//	Routine BegRECORD; import range(0:MaxByte) type;
//	begin
//	%+D   if listsw > 0 then ListRecType(type) endif;
//	      DBUF.hed:=type qua character; DBUF.nxt:=5;
//	end;
//
//	%+D Routine ListRecType; import range(0:N_max) type;
//	%+D begin printout(ouptrace);
//	%+D       case 0:N_max (type)
//	%+D       when N_THEADR: ed(ouptrace,"THEADR")
//	%+D       when N_COMENT: ed(ouptrace,"COMENT")
//	%+D       when N_MODEND: ed(ouptrace,"MODEND")
//	%+DE      when N_386END: ed(ouptrace,"386END")
//	%+D       when N_EXTDEF: ed(ouptrace,"EXTDEF")
//	%+D       when N_PUBDEF: ed(ouptrace,"PUBDEF")
//	%+DE      when N_PUB386: ed(ouptrace,"PUB386")
//	%+D       when N_LINNUM: ed(ouptrace,"LINNUM")
//	%+DE      when N_LIN386: ed(ouptrace,"LIN386")
//	%+D       when N_LNAMES: ed(ouptrace,"LNAMES")
//	%+D       when N_SEGDEF: ed(ouptrace,"SEGDEF")
//	%+DE      when N_SEG386: ed(ouptrace,"SEG386")
//	%+D       when N_GRPDEF: ed(ouptrace,"GRPDEF")
//	%+D       when N_FIXUPP: ed(ouptrace,"FIXUPP")
//	%+DE      when N_FIX386: ed(ouptrace,"FIX386")
//	%+D       when N_LEDATA: ed(ouptrace,"LEDATA")
//	%+DE      when N_LED386: ed(ouptrace,"LED386")
//	%+D       when N_LIDATA: ed(ouptrace,"LIDATA")
//	%+DE      when N_LID386: ed(ouptrace,"LID386")
//	%+D       otherwise IERR("RELUT.Outcode-1") endcase;
//	%+D       -- OTRC("Outcode");
//	%+D end;
//
//	Routine OutRECORD;
//	begin infix(String) bf; infix(WORD) lng; range(0:MaxWord) i,ChkPos;
//	      range(0:MaxByte) ChkSum; ChkSum:=0;
//	      lng.val:=DBUF.nxt-4; if lng.val=1 then goto E1 endif;
//	      DBUF.hed(DBUF.nxt):=0 qua character; -- Check Sum
//	      
//	      DBUF.hed(2):=DBUF.hed(0); -- Record Type
//	      DBUF.hed(3):=lng.LO qua character;
//	      DBUF.hed(4):=lng.HI qua character;
//	      bf.chradr:=@DBUF.hed(2); bf.nchr:=lng.val+3;
//	           ChkPos:=bf.nchr+1;
//	      --- Calculate and Set Check-Sum --- P.g.a  XENIX:ranlib
//	      i:=1; repeat i:=i+1 while i < ChkPos
//	      do ChkSum:=ChkSum+DBUF.hed(i) qua integer endrepeat;
//	      DBUF.hed(ChkPos):= (-ChkSum) qua character;
//	      --- Write Buffer to File ---
//	      EnvOutBytes(objfile,bf);
//	      if Status <> 0 then FILERR(objfile,"OutRECORD") endif;
//	E1:end;
//
//	Routine OutLEDATA;
//	begin infix(String) bf; infix(WORD) lng,segx; range(0:MaxWord) i,ChkPos;
//	%+D   ref(Segment) seg; range(0:MaxByte) ChkSum;
//	%-E   segx:=CBUF.segx;
//	%+E   segx.val:=CBUF.segx;
//	      lng.val:=CBUF.nxt;
//	%+D   seg:=DICREF(DIC.Segm(segx.HI).elt(segx.LO));
//	%+D   seg.rela.val:=seg.rela.val+lng.val; ChkSum:=0;
//	      CBUF.byt(CBUF.nxt):=0;
//	      if segx.val < 128
//	      then ---  LEDATA Head -- Short Segment Index --
//	           lng.val:=lng.val+(2+AllignFac);
//	           CBUF.hed(1):=0 qua character;
//	%-E        CBUF.hed(2):=N_LEDATA qua character;
//	%+E        CBUF.hed(2):=N_LED386 qua character;
//	           CBUF.hed(3):=lng.LO qua character;
//	           CBUF.hed(4):=lng.HI qua character;
//	           CBUF.hed(5):=segx.LO qua character;
//	           bf.chradr:=@CBUF.hed(2); bf.nchr:=lng.val+3;
//	           ChkPos:=bf.nchr+1;
//	      else ---  LEDATA Head -- Long Segment Index --
//	           lng.val:=lng.val+(3+AllignFac);
//	%-E        CBUF.hed(1):=N_LEDATA qua character;
//	%+E        CBUF.hed(1):=N_LED386 qua character;
//	           CBUF.hed(2):=lng.LO qua character;
//	           CBUF.hed(3):=lng.HI qua character;
//	           CBUF.hed(4):=bOR(128,segx.HI) qua character;
//	           CBUF.hed(5):=segx.LO qua character;
//	           bf.chradr:=@CBUF.hed(1); bf.nchr:=lng.val+3;
//	           ChkPos:=bf.nchr;
//	      endif;
//	%+D   --- Calculate and Set Check-Sum --- P.g.a  XENIX:ranlib
//	%+D   i:=0; repeat i:=i+1 while i < ChkPos
//	%+D   do ChkSum:=ChkSum+CBUF.hed(i) qua integer endrepeat;
//	%+D   CBUF.hed(ChkPos):= (-ChkSum) qua character;
//	      --- Write Buffer to ObjFile ---
//	      EnvOutBytes(objfile,bf);
//	      if Status <> 0 then FILERR(objfile,"OutLEDATA") endif;
//	end;
//
//	%+F Macro CoffSpace(1);
//	%+F begin if DBUF.nxt >= (1025-(%1)) then OutCOFFBLK endif; endmacro;
//
//	%+F Routine BegCOFFBLK;
//	%+F begin DBUF.nxt:=4 end;
//
//	%+F Routine OutCOFFBLK; -- COFF: Output portion of File
//	%+F begin infix(String) bf;
//	%+F       bf.chradr:=@DBUF.hed(4); bf.nchr:=DBUF.nxt-4;
//	%+F       --- Write Buffer to ObjFile ---
//	%+F       EnvOutBytes(objfile,bf);
//	%+F       if Status <> 0 then FILERR(objfile,"OutCOFFBLK") endif;
//	%+F       DBUF.nxt:=4;
//	%+F end;
//
//	%+F Routine OutRawData; -- COFF: Output portion of raw data
//	%+F begin infix(String) bf; infix(WORD) lng,segx;
//	%+F       ref(Segment) seg; integer FilPos;
//	%+FE      infix(wWORD) ofst32;
//	%+FD      if TLIST > 3
//	%+FD      then outstring("*** OutRawData: lng=");
//	%+FD           outword(CBUF.nxt); Printout(sysout);
//	%+FD      endif;
//	%+F %-E   segx:=CBUF.segx;
//	%+FE      segx.val:=CBUF.segx;
//	%+F       lng.val:=CBUF.nxt;
//	%+F       seg:=DICREF(DIC.Segm(segx.HI).elt(segx.LO));
//	%+FD      seg.rela.val:=seg.rela.val+lng.val;
//	%+F       --- Set Location in ObjFile ---
//	%+F %-E   FilPos:=RawDataPos+seg.pAddr+CBUF.Ofst.val;
//	%+FE      ofst32.HighWord.val:=CBUF.ofstHI;
//	%+FE      ofst32.LowWord.val:=CBUF.ofstLO;
//	%+FE      FilPos:=RawDataPos+seg.pAddr+ofst32.val
//	%+FD      if TLIST > 3
//	%+FD      then outstring("***             FilPos=");
//	%+FD           outword(FilPos); Printout(sysout);
//	%+FD      endif;
//	%+F       EnvLocate(objfile,FilPos+1);
//	%+F       if Status <> 0 then IERR("OutRawData-1") endif;
//	%+F       --- Write Buffer to ObjFile ---
//	%+F       bf.chradr:=@CBUF.chr(0); bf.nchr:=lng.val;
//	%+F       EnvOutBytes(objfile,bf);
//	%+F       if Status <> 0 then FILERR(objfile,"OutRawData-2") endif;
//	%+F end;
//
//	%+F Routine NewReloc;
//	%+F import ref(Segment) seg; integer Ofst,Smbx; range(0:MaxWord) Type;
//	%+F begin ref(RelocObj) x; x:=NEWOBX(size(RelocObj)); x.next:=none;
//	%+F       if seg.LstRel=none then seg.FstRel:=seg.LstRel:=x
//	%+F       else seg.LstRel.next:=x; seg.LstRel:=x endif;
//	%+F       x.Cpkt.vAddr:=seg.pAddr+Ofst;
//	%+F       x.Cpkt.SymNdx:=Smbx; x.Cpkt.Type:=Type;
//	%+F end;
//
//	Macro PutByte(1);
//	begin DBUF.hed(DBUF.nxt):=%1 qua character;
//	      DBUF.nxt:=DBUF.nxt+1;
//	endmacro;
//
//	Macro PutWord(1);
//	begin DBUF.hed(DBUF.nxt):=%1 .LO   qua character; DBUF.nxt:=DBUF.nxt+1;
//	      DBUF.hed(DBUF.nxt):=%1 .HI   qua character; DBUF.nxt:=DBUF.nxt+1;
//	endmacro;
//
//	Macro PutDWord(1);
//	begin DBUF.hed(DBUF.nxt):=%1 .LO   qua character; DBUF.nxt:=DBUF.nxt+1;
//	      DBUF.hed(DBUF.nxt):=%1 .LOHI qua character; DBUF.nxt:=DBUF.nxt+1;
//	      DBUF.hed(DBUF.nxt):=%1 .HILO qua character; DBUF.nxt:=DBUF.nxt+1;
//	      DBUF.hed(DBUF.nxt):=%1 .HI   qua character; DBUF.nxt:=DBUF.nxt+1;
//	endmacro;
//
//	Macro PutwWord(1);
//	begin DBUF.hed(DBUF.nxt):=%1 .LO   qua character; DBUF.nxt:=DBUF.nxt+1;
//	%+E   DBUF.hed(DBUF.nxt):=%1 .LOHI qua character; DBUF.nxt:=DBUF.nxt+1;
//	%+E   DBUF.hed(DBUF.nxt):=%1 .HILO qua character; DBUF.nxt:=DBUF.nxt+1;
//	      DBUF.hed(DBUF.nxt):=%1 .HI   qua character; DBUF.nxt:=DBUF.nxt+1;
//	endmacro;
//
//	Macro PutIndex(1);
//	begin if %1 .val > 127
//	      then DBUF.hed(DBUF.nxt):=bOR(%1 .HI,128) qua character;
//	           DBUF.nxt:=DBUF.nxt+1;
//	      endif;
//	      DBUF.hed(DBUF.nxt):=%1 .LO qua character; DBUF.nxt:=DBUF.nxt+1;
//	endmacro;
//
//	Routine PutString; import infix(string) s;
//	begin range(0:MaxWord) n,k; n:=s.nchr; k:= 0;
//	      PutByte(%n%);
//	      repeat while k < n
//	      do PutByte(%var(s.chradr)(k) qua integer%); k:=k+1 endrepeat;
//	end;
//
//	Routine PutSymb; import infix(WORD) i
//	begin edsymb(sysedit,i); PutString(pickup(sysedit)) end;
//
//	%+F Routine PutIdent8; import infix(Ident8) id8;
//	%+F begin range(0:MaxWord) n; n:=0;
//	%+F       repeat while n < 8
//	%+F       do PutByte(%id8.Char(n) qua integer%); n:=n+1 endrepeat;
//	%+F end;
//
//	%+F Routine PutSymb8; import infix(WORD) i
//	%+F begin PutIdent8(Symb2Id8(i)) end;
//
//	%+F Routine PutChars;
//	%+F import range(0:MaxWord) nchr; name(character) chradr;
//	%+F begin range(0:MaxWord) n; n:=0;
//	%+F       repeat while n < nchr
//	%+F       do PutByte(%var(chradr)(n) qua integer%); n:=n+1 endrepeat;
//	%+F end;
//
//	%+F Routine String2Id8;
//	%+F import infix(String) s; export infix(Ident8) id8;
//	%+F begin if s.nchr>8 then IERR("COFF:LongString2Id8"); s.nchr:=8 endif;
//	%+F       APX_SMOVEI(s.nchr,@id8.Char,s.chradr);
//	%+F       if s.nchr >= 8 then goto E1 endif;
//	%+F       APX_SFILL(0 qua character,8-s.nchr,@id8.Char(s.nchr));
//	%+F E1:end;
//
//	%+F Routine Symb2Id8;
//	%+F import infix(WORD) i; export infix(Ident8) id8;
//	%+F begin ref(StringTabEntry) x; infix(String) s;
//	%+F       EdSymb(sysedit,i); s:=Pickup(sysedit);
//	%+F       if s.nchr > 8
//	%+F       then id8.Zero:=0; id8.Index:=StrLng.val+4;
//	%+F            StrLng.val:=StrLng.val+s.nchr+1;
//	%+F            x:=NEWOBX(size(StringTabEntry));
//	%+F            if LstStr=none then FstStr:=LstStr:=x
//	%+F            else LstStr.next:=x; LstStr:=x endif;
//	%+F            x.next:=none; x.smbx:=i;
//	%+F       else APX_SMOVEI(s.nchr,@id8.Char,s.chradr);
//	%+F            if s.nchr >= 8 then goto E1 endif;
//	%+F            APX_SFILL(0 qua character,8-s.nchr,@id8.Char(s.nchr));
//	%+F       endif;
//	%+F E1:end;
//
//	%title ***  R E L O C A T A B L E    O U T P U T  ***
//
//	Visible routine RELOUT;
//	begin ---   Set initial list and trace switches  ---
//	%+D   integer trc,txx;
//	%+D   listsw:=0; InputTrace:=0; tracemode:=0; ModuleTrace:=0;
//	%+D   if SK2LIN = 1
//	%+D   then trc:=SK2TRC; txx:=trc/10; InputTrace:=  trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    TraceMode:=   trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    ModuleTrace:= trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    listsw:=      trc-(10*txx);
//	%+D        SK2LIN:=0; SK2TRC:=0;
//	%+D   endif;
//	%+A   if asmgen then ASMUT else
//	%+F      if OSID=oUNIX386     then COFFUT;  -- AT&T-UNIX-COFF OUTPUT
//	%+F      elsif OSID=oUNIX386W then COFFUT;  -- AT&T-UNIX-COFF OUTPUT
//	%+F      else
//	                                   iRELUT;  -- INTEL RELOCATABLE OUTPUT
//	%+F      endif;
//	%+A   endif;
//	end;
//
//
//	Routine iRELUT;
//	begin range(0:MaxWord) i,k,n,code;
//	      infix(string) filid,action; infix(Fixup) Fx;
//	      infix(WORD) wrd,ix,segid,pubid,extid,segx,extx,grpx;
//	      range(0:MaxByte) byt,atr; infix(WORD) ident;
//	      ref(Segment) seg; infix(MemAddr) addr;
//	      ref(Public) pub; ref(Extern) ext;
//	      infix(RelocPkt) pkt; range(0:2) RelType; range(0:4) FieldType;
//	      range(0:MaxByte) dat,bt; infix(wWORD) disp,cdisp,dd,dx;
//	%+D   integer trc,txx;
//
//	      --- Open Scratch Input File ---
//	%+S   if envpar then
//	                     edtextinfo(sysedit,7);
//	%+S   else if SCRID.val = 0
//	%+S        then ed(sysedit,"ICODE.TMP")
//	%+S        else EdSymb(sysedit,SCRID) endif;
//	%+S   endif;
//	      action:="!0!!1!!1!!0!!1!!2!!0!!0!!0!!0!";  -- in(byte)file
//	      scrfile:=open(pickup(sysedit),5,action,0);
//
//	      --- Open Relocatable Output File ---
//	      EdSymb(sysedit,RELID); filid:=pickup(sysedit);
//	----  action:="!1!!1!!2!!1!!1!!2!!0!!0!!0!!8!NOBUFFER!0!";  -- out(byte)file
//	      action:=
//	      "!1!!1!!2!!1!!1!!2!!0!!0!!0!!8!!78!!79!!66!!85!!70!!70!!69!!82!!0!";
//	      objfile:=open(filid,6,action,0);
//	%+D   if TLIST > 0
//	%+D   then outstring("RELOCATABLE OUTPUT: ");
//	%+D        outstring(filid); outimage;
//	%+D   endif;
//
//	      --- Treat location counter table ---
//	      ix.val:=0; repeat while ix.val < DIC.nSegm
//	      do ix.val:=ix.val+1; segid:=DIC.Segm(ix.HI).elt(ix.LO);
//	         seg:=DICREF(segid); seg.lng:=seg.rela; seg.rela.val:=0;
//	      endrepeat;
//	      CSEGID.val:=0;
//
//	      ----  Output T-Header  ----
//	      BegRECORD(N_THEADR); EdSymb(sysedit,PROGID);
//	      PutString(pickup(sysedit)); OutRECORD;
//
//	      ----  Output Linker Names  ----
//	      -- 1:NoName, 2:DGROUP, 3:DATA, 4:FAR_CODE, 5:FAR_DATA, 6:LIN_CODE
//	      -- 7:segment(segx=1)'name  ... etc.
//	      BegRECORD(N_LNAMES); PutByte(%0%);
//	      PutString("DGROUP"); PutString("DATA");
//	%-E   PutString("FAR_CODE");
//	%+E   PutString("CODE");
//	      PutString("FAR_DATA");
//	      PutString("LIN_CODE");
//	      ix.val:=0; repeat while ix.val < DIC.nSegm
//	      do ix.val:=ix.val+1; segid:=DIC.Segm(ix.HI).elt(ix.LO);
//	         PutSymb(segid);
//	      endrepeat;
//	      OutRECORD;
//
//	      ----  Output Segment Definitions  ----
//	      ix.val:=0; repeat while ix.val < DIC.nSegm
//	      do ix.val:=ix.val+1; segid:=DIC.Segm(ix.HI).elt(ix.LO);
//	         segx:=ix; seg:=DICREF(segid);
//	         case 0:3 (seg.type)
//	%-E      when aDGRP: atr:=72  --(A=2,C=2) SEGMENT WORD PUBLIC 'DATA'
//	%+E      when aDGRP: atr:=173 --(A=5,C=2) SEGMENT DWORD PUBLIC 'DATA'
//	%-E      when aDATA: atr:=72  --(A=2,C=2) SEGMENT WORD PUBLIC 'FAR_DATA'
//	%+E      when aDATA: atr:=173 --(A=5,C=2) SEGMENT DWORD PUBLIC 'DATA'
//	                     --- CODE word aligned on 286
//	%-E      when aCODE: atr:=72  --(A=2,C=2) SEGMENT WORD PUBLIC 'FAR_CODE'
//	%+E      when aCODE: atr:=173 --(A=5,C=2) SEGMENT DWORD PUBLIC 'CODE'
//	%-E      when aLINE: atr:=72  --(A=2,C=2) SEGMENT WORD PUBLIC 'LIN_CODE'
//	%+E      when aLINE: atr:=173 --(A=5,C=2) SEGMENT DWORD PUBLIC 'CODE'
//	--- %-E %-S  when aCODE: atr:=40  --(A=1,C=2) SEGMENT BYTE PUBLIC 'FAR_CODE'
//	--- %-E %+S  when aCODE: if SYSGEN<>0
//	--- %-E %+S       then atr:=72       --(A=2,C=2) SEGMENT WORD PUBLIC 'FAR_CODE'
//	--- %-E %+S       else atr:=40 endif --(A=1,C=2) SEGMENT BYTE PUBLIC 'FAR_CODE'
//	         endcase;
//	         BegRECORD(N_SEGDEF);
//	         PutByte(%atr%);                       -- Segment Attributes
//	-- ????? PutwWord(%seg.lng%);                  -- Segment Length
//	%-E      PutwWord(%seg.lng%);                  -- Segment Length
//	%+E      PutWord(%seg.lng.LowWord%);           -- Segment Length
//	         wrd.val:=segx.val+6; PutIndex(%wrd%); -- Segment Name Index
//	         case 0:3 (seg.type)                   -- Class Name Index
//	         when aDGRP: PutByte(%3%); -- SEGMENT  WORD PUBLIC 'DATA'
//	%-E      when aDATA: PutByte(%5%); -- SEGMENT  WORD PUBLIC 'FAR_DATA'
//	%+E      when aDATA: PutByte(%3%); -- SEGMENT  WORD PUBLIC 'DATA'
//	%-E      when aCODE: PutByte(%4%); -- SEGMENT  BYTE PUBLIC 'FAR_CODE'
//	%+E      when aCODE: PutByte(%4%); -- SEGMENT  BYTE PUBLIC 'CODE'
//	%-E      when aLINE: PutByte(%6%); -- SEGMENT  WORD PUBLIC 'LIN_CODE'
//	%+E      when aLINE: PutByte(%3%); -- SEGMENT  WORD PUBLIC 'DATA'
//	         endcase;
//	         PutByte(%1%);            -- Overlay Name Index
//	         OutRECORD;
//	      endrepeat;
//
//	      ----  Output: DGROUP GROUP _DATA  ----
//	      segx:=DICREF(DGROUP) qua Segment.segx;
//	      if segx.val <> 0
//	      then BegRECORD(N_GRPDEF);
//	           PutByte(%2%);       --- GroupName Index
//	           PutByte(%255%);     --- ???
//	           PutIndex(%segx%);   --- Segment Index
//	           OutRECORD;
//	      endif;
//	      ----  Output External References  ----
//	      BegRECORD(N_EXTDEF);
//	      ix.val:=0; repeat while ix.val < DIC.nExtr
//	      do ix.val:=ix.val+1;
//	         if DBUF.nxt+72 >= BufLng
//	         then OutRECORD; BegRECORD(N_EXTDEF) endif;
//	         PutSymb(DIC.Extr(ix.HI).elt(ix.LO));
//	         PutByte(%0%);
//	      endrepeat;
//	      OutRECORD;
//
//	      repeat ReadCBUF; ReadCREL while CBUF.nxt <> 0
//	      do ----  PROCESS ALL BUFFERS FROM PASS 1  ----
//	%-E      BlkBase.LowWord:=CBUF.ofst;
//	%+E      BlkBase.HighWord.val:=CBUF.ofstHI;
//	%+E      BlkBase.LowWord.val:=CBUF.ofstLO;
//	         k:=0;
//	%-E      BegRECORD(N_FIXUPP); ---- Initiate FIXUPP Record ----
//	%+E      BegRECORD(N_FIX386); ---- Initiate FIXUPP Record ----
//	         repeat while k < CREL.nxt
//	         do ----  PROCESS ALL RELOCATION PACKETS  ----
//	            pkt:=CREL.elt(k); k:=k+1;
//	            BlkOfst.val:=wAND(pkt.mrk,1023);
//	            RelType:=wAND(wSHR(pkt.mrk,10),3);
//	            FieldType:=wAND(wSHR(pkt.mrk,12),7);
//
//	%+D         if FieldType>4
//	%+D         then IERR("RELUT:FieldType"); FieldType:=0 endif;
//	%+D         if RelType>2 then IERR("RELUT:RelType"); RelType:=0 endif;
//	            case 0:2 (RelType)
//	            when rSEG: -- Local Segment (+disp) rel Composed Segment
//	                       segx:=pkt.segx; disp.val:=0;
//	                   L1: segid:=DIC.Segm(segx.HI).elt(segx.LO); -- DGROUP TEST
//	                       case 0:4 (FieldType)
//	                       when fPOINTER: -- Segment Relative Relocation --
//	%-E                         PutByte(%bOR(204,BlkOfst.HI)%);
//	%+E                         PutByte(%bOR(228,BlkOfst.HI)%);
//	                            PutByte(%BlkOfst.LO%);
//	                            cdisp.LO:=CBUF.byt(BlkOfst.val);
//	%-E                         cdisp.HI:=CBUF.byt(BlkOfst.val+1);
//	%+E                         cdisp.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+E                         cdisp.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+E                         cdisp.HI:=CBUF.byt(BlkOfst.val+3);
//	                            CBUF.byt(BlkOfst.val):=0;
//	                            CBUF.byt(BlkOfst.val+1):=0;
//	%+E                         CBUF.byt(BlkOfst.val+2):=0;
//	%+E                         CBUF.byt(BlkOfst.val+3):=0;
//	                            disp.val:=disp.val+cdisp.val;
//	%-E                         if segid=DGROUP
//	%-E                         then grpx.val:=1;
//	%-E                              if disp.val > 0
//	%-E                              then PutByte(%16%);     -- FIXDAT
//	%-E                                   PutIndex(%grpx%);  -- FRAME  DATUM
//	%-E                                   PutIndex(%segx%);  -- TARGET DATUM
//	%-E                                   PutwWord(%disp%);  -- TARGET DISPL
//	%-E                              else PutByte(%20%);     -- FIXDAT
//	%-E                                   PutIndex(%grpx%);  -- FRAME  DATUM
//	%-E                                   PutIndex(%segx%);  -- TARGET DATUM
//	%-E                              endif;
//	%-E                         else
//	                                 if disp.val > 0
//	                                 then PutByte(%80%); PutIndex(%segx%);
//	                                      PutwWord(%disp%);
//	                                 else PutByte(%84%); PutIndex(%segx%) endif;
//	%-E                         endif;
//	                       when fSEGMENT: -- Segment Relative Relocation --
//	%-E                         PutByte(%bOR(200,BlkOfst.HI)%);
//	%+E                         PutByte(%bOR(228,BlkOfst.HI)%);
//	                            PutByte(%BlkOfst.LO%);
//	                            cdisp.LO:=CBUF.byt(BlkOfst.val);
//	%-E                         cdisp.HI:=CBUF.byt(BlkOfst.val+1);
//	%+E                         cdisp.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+E                         cdisp.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+E                         cdisp.HI:=CBUF.byt(BlkOfst.val+3);
//	                            CBUF.byt(BlkOfst.val):=0;
//	                            CBUF.byt(BlkOfst.val+1):=0;
//	%+E                         CBUF.byt(BlkOfst.val+2):=0;
//	%+E                         CBUF.byt(BlkOfst.val+3):=0;
//	%-E                         if segid=DGROUP
//	%-E                         then grpx.val:=1;
//	%-E                              PutByte(%20%);     -- FIXDAT
//	%-E                              PutIndex(%grpx%);  -- FRAME  DATUM
//	%-E                              PutIndex(%segx%);  -- TARGET DATUM
//	%-E                         else
//	                                 PutByte(%84%); PutIndex(%segx%);
//	%-E                         endif;
//	                       when fOFFSET: -- Segment Relative Relocation --
//	%-E                         PutByte(%bOR(196,BlkOfst.HI)%);
//	%+E                         PutByte(%bOR(228,BlkOfst.HI)%);
//	                            PutByte(%BlkOfst.LO%);
//	                            cdisp.LO:=CBUF.byt(BlkOfst.val);
//	%-E                         cdisp.HI:=CBUF.byt(BlkOfst.val+1);
//	%+E                         cdisp.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+E                         cdisp.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+E                         cdisp.HI:=CBUF.byt(BlkOfst.val+3);
//	                            CBUF.byt(BlkOfst.val):=0;
//	                            CBUF.byt(BlkOfst.val+1):=0;
//	%+E                         CBUF.byt(BlkOfst.val+2):=0;
//	%+E                         CBUF.byt(BlkOfst.val+3):=0;
//	                            disp.val:=disp.val+cdisp.val;
//	%-E                         if segid=DGROUP
//	%-E                         then grpx.val:=1;
//	%-E                              if disp.val > 0
//	%-E                              then PutByte(%16%);     -- FIXDAT
//	%-E                                   PutIndex(%grpx%);  -- FRAME  DATUM
//	%-E                                   PutIndex(%segx%);  -- TARGET DATUM
//	%-E                                   PutwWord(%disp%);  -- TARGET DISPL
//	%-E                              else PutByte(%20%);     -- FIXDAT
//	%-E                                   PutIndex(%grpx%);  -- FRAME  DATUM
//	%-E                                   PutIndex(%segx%);  -- TARGET DATUM
//	%-E                              endif;
//	%-E                         else
//	                                 if disp.val > 0
//	                                 then PutByte(%80%); PutIndex(%segx%);
//	                                      PutwWord(%disp%);
//	                                 else PutByte(%84%); PutIndex(%segx%) endif;
//	%-E                         endif;
//	                       when fFULLDISP: -- SelfRelative Relocation --
//	%+E                         if segx.val=CBUF.segx
//	%+E                         then
//	                                 dd.LO:=CBUF.byt(BlkOfst.val);
//	%-E                              dd.HI:=CBUF.byt(BlkOfst.val+1);
//	%+E                              dd.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+E                              dd.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+E                              dd.HI:=CBUF.byt(BlkOfst.val+3);
//	                                 dx.val:=(disp.val+dd.val)
//	                                   -(BlkBase.val+BlkOfst.val+AllignFac)
//	                                 CBUF.byt(BlkOfst.val):=dx.LO;
//	%-E                              CBUF.byt(BlkOfst.val+1):=dx.HI;
//	%+E                              CBUF.byt(BlkOfst.val+1):=dx.LOHI;
//	%+E                              CBUF.byt(BlkOfst.val+2):=dx.HILO;
//	%+E                              CBUF.byt(BlkOfst.val+3):=dx.HI;
//	%+E                         else -- InterSegment Selfrelative Reloc
//	%+E                              PutByte(%bOR(164,BlkOfst.HI)%);
//	%+E                              PutByte(%BlkOfst.LO%);
//	%+E                              cdisp.LO:=CBUF.byt(BlkOfst.val);
//	%+E                              cdisp.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+E                              cdisp.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+E                              cdisp.HI:=CBUF.byt(BlkOfst.val+3);
//	%+E                              CBUF.byt(BlkOfst.val):=0;
//	%+E                              CBUF.byt(BlkOfst.val+1):=0;
//	%+E                              CBUF.byt(BlkOfst.val+2):=0;
//	%+E                              CBUF.byt(BlkOfst.val+3):=0;
//	%+E                              disp.val:=disp.val+cdisp.val;
//	%+E                              if disp.val > 0
//	%+E                              then PutByte(%80%); PutIndex(%segx%);
//	%+E                                   PutwWord(%disp%);
//	%+E                              else PutByte(%84%); PutIndex(%segx%) endif;
//	%+E                         endif;
//	                       when fBYTEDISP: -- SelfRelative Relocation --
//	                            dd.val:=CBUF.byt(BlkOfst.val);
//	                            dx.val:=(disp.val+dd.val)
//	                                   -(BlkBase.val+BlkOfst.val+1)
//	                            if dx.HI <> BOOL2BYTE(dx.LO>127)
//	                            then Ed(errmsg,"Byte Disp Overflow --");
//	%+D                              EdHex(errmsg,dd.val,4);
//	%+D                              EdHex(errmsg,dx.val,4);
//	%+D                              if RelType = rFIX
//	%+D                              then Ed(errmsg," Fix(");
//	%+D                                   edint(errmsg,pkt.fix.val);
//	%+D                                   ed(errmsg,") created at line ");
//	%+D                                   edwrd(errmsg,Fx.line);
//	%+D                              endif;
//	                                 IERR(" ");
//	                            endif;
//	                            CBUF.byt(BlkOfst.val):=dx.val;
//	                       endcase;
//	            when rEXT: -- External Symbol (+disp) rel Composed Segment
//	                       cdisp.LO:=CBUF.byt(BlkOfst.val);
//	%-E                    cdisp.HI:=CBUF.byt(BlkOfst.val+1);
//	%+E                    cdisp.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+E                    cdisp.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+E                    cdisp.HI:=CBUF.byt(BlkOfst.val+3);
//	                       CBUF.byt(BlkOfst.val):=0;
//	                       CBUF.byt(BlkOfst.val+1):=0;
//	%+E                    CBUF.byt(BlkOfst.val+2):=0;
//	%+E                    CBUF.byt(BlkOfst.val+3):=0;
//	                       case 0:4 (FieldType)
//	                       when fPOINTER:
//	%-E                                    PutByte(%bOR(204,BlkOfst.HI)%);
//	%+E                                    PutByte(%bOR(228,BlkOfst.HI)%);
//	                       when fSEGMENT:  cdisp.val:=0;
//	%-E                                    PutByte(%bOR(200,BlkOfst.HI)%);
//	%+E                                    PutByte(%bOR(228,BlkOfst.HI)%);
//	                       when fOFFSET:
//	%-E                                    PutByte(%bOR(196,BlkOfst.HI)%);
//	%+E                                    PutByte(%bOR(228,BlkOfst.HI)%);
//	                       when fFULLDISP:
//	%-E                                    PutByte(%bOR(132,BlkOfst.HI)%);
//	%+E                                    PutByte(%bOR(164,BlkOfst.HI)%);
//	                       when fBYTEDISP:
//	%-E                                    PutByte(%bOR(128,BlkOfst.HI)%);
//	                                       IERR("Ext BYTEDISP");
//	                       endcase;
//	                       PutByte(%BlkOfst.LO%);
//
//	% ************************************************************************
//	                       extx:=pkt.extx;
//	                       extid:=DIC.Extr(extx.HI).elt(extx.LO);
//	                       ext:=DIC.Symb(extid.HI).elt(extid.LO);
//	                       segid:=ext.segid;
//	%-E                    if segid=DGROUP
//	%-E                    then grpx.val:=1;
//	%-E                         if cdisp.val > 0
//	%-E                         then PutByte(%18%);     -- FIXDAT
//	%-E                              PutIndex(%grpx%);  -- FRAME  DATUM
//	%-E                              PutIndex(%extx%);  -- TARGET DATUM
//	%-E                              PutwWord(%cdisp%); -- TARGET DISPL
//	%-E                         else PutByte(%22%);     -- FIXDAT
//	%-E                              PutIndex(%grpx%);  -- FRAME  DATUM
//	%-E                              PutIndex(%extx%);  -- TARGET DATUM
//	%-E                         endif;
//	%-E                    else
//	                            if cdisp.val > 0
//	                            then PutByte(%82%); PutIndex(%pkt.extx%);
//	%+DE                             if cdisp.HighWord.val <> 0
//	%+DE                             then IERR("Reloc-External");
//	%+DE    -- ?????                      cdisp.HighWord.val:=0;
//	%+DE                             endif;
//	                                 PutwWord(%cdisp%);
//	                            else PutByte(%86%); PutIndex(%pkt.extx%) endif;
//	%-E                    endif;
//	            when rFIX: Fx:=FIXTAB(pkt.fix.HI).elt(pkt.fix.LO);
//	%+C              if not Fx.Matched
//	%+C              then edint(errmsg,pkt.fix.val);
//	%+C                   ed(errmsg,") created at line ");
//	%+C                   edwrd(errmsg,Fx.line);
//	%+C                   IERR("Missing FIXUP(");
//	%+C              else
//	                      segx:=GetSegx(Fx.segid); disp:=Fx.rela; goto L1;
//	%+C              endif;
//	            endcase;
//	         endrepeat;
//	         OutLEDATA; OutRECORD;
//	      endrepeat;
//
//	      ----  Output Public Definitions  ----
//	      ix.val:=0; repeat while ix.val < DIC.nPubl
//	      do ix.val:=ix.val+1; pubid:=DIC.Publ(ix.HI).elt(ix.LO);
//	         pub:=DICREF(pubid); segx:=pub.segx;
//	         BegRECORD(N_PUBDEF);
//	         PutByte(%0%);          -- Group Index (no group)  -- ?????????
//	         PutIndex(%segx%);      -- Segment Index
//	         PutSymb(pubid);        -- Public Name
//	%-E      PutwWord(%pub.rela%);  -- Public Offset
//	%+E      PutWord(%pub.rela.LowWord%);  -- Public Offset
//	         PutByte(%0%);          -- Type Index (ignored by Linker)
//	         OutRECORD;
//	      endrepeat;
//
//	      ---- Output Module End ----
//	      BegRECORD(N_MODEND);
//	      PutByte(%0%)  -- Sub-Module
//	      OutRECORD;
//
//	      --- Check Location Counters ---
//	%+C   ix.val:=0; repeat while ix.val < DIC.nSegm
//	%+C   do ix.val:=ix.val+1;
//	%+C      segid:=DIC.Segm(ix.HI).elt(ix.LO);
//	%+C      seg:=DICREF(segid);
//	%+C      if seg.rela <> seg.lng
//	%+C      then Ed(errmsg,"Segment-Check "); EdWrd(errmsg,ix.val);
//	%+C           EdChar(errmsg,':'); EdSymb(errmsg,segid);
//	%+C           Ed(errmsg," (pass1="); EdWrd(errmsg,seg.lng.val);
//	%+C           Ed(errmsg,",pass2="); EdWrd(errmsg,seg.rela.val);
//	%+C           IERR(") fails");
//	%+C      endif;
//	%+C   endrepeat;
//
//	      EnvClose(scrfile,nostring);
//	      if status<>0 then FILERR(scrfile,"RELOUT-6") endif; scrfile:=0;
//	      EnvClose(objfile,nostring);
//	      if status<>0 then FILERR(objfile,"RELOUT-7") endif; objfile:=0;
//	end;
//	%title ***  U N I X - C O F F    O U T P U T  ***
//
//	%+F Routine COFFUT;
//	%+F begin range(0:MaxWord) i,k,n,code;
//	%+F       infix(string) s,bf,filid,action; infix(Fixup) Fx;
//	%+F       infix(WORD) wrd,ix,segid,pubid,extid,segx;
//	%+F       range(0:MaxByte) byt,atr; infix(WORD) ident;
//	%+F       ref(Segment) seg,relseg; infix(MemAddr) addr;
//	%+F       ref(Public) pub; ref(Extern) ext;
//	%+F       infix(RelocPkt) pkt; range(0:2) RelType; range(0:4) FieldType;
//	%+F       range(0:MaxByte) dat,bt; infix(wWORD) disp,cdisp,dd,dx;
//	%+FD      integer trc,txx;
//	%+F 
//	%+F       infix(CoffFileHeader) Fhdr;      -- COFF File Header
//	%+F       infix(CoffRelocPkt) Cpkt;
//	%+F       infix(CoffSymbolEntry) Csmb;
//	%+F       infix(CoffAuxSymbolEntry) Caux;
//	%+F       ref(RelocObj) RelObj;
//	%+F       integer FilLng; -- File size in byte until now
//	%+F       integer nReloc; -- No.of relocatons in this section
//	%+F       integer SecDisp; -- COFF:Symbol index first Section
//	%+F       integer PubDisp; -- COFF:Symbol index first Public
//	%+F       integer ExtDisp; -- COFF:Symbol index first External
//	%+F 
//	%+F       --- Open Scratch Input File ---
//	%+F %+S   if envpar then
//	%+F                      edtextinfo(sysedit,7);
//	%+F %+S   else if SCRID.val = 0
//	%+F %+S        then ed(sysedit,"ICODE.TMP")
//	%+F %+S        else EdSymb(sysedit,SCRID) endif;
//	%+F %+S   endif;
//	%+F       action:="!0!!1!!1!!0!!1!!2!!0!!0!!0!!0!";  -- in(byte)file
//	%+F       scrfile:=open(pickup(sysedit),5,action,0);
//	%+F 
//	%+F       --- Open Relocatable Output File ---
//	%+F       EdSymb(sysedit,RELID); filid:=pickup(sysedit);
//	%+F ----  action:="!1!!1!!2!!1!!1!!2!!0!!0!!0!!8!NOBUFFER!0!";  -- out(byte)file
//	%+F       action:=
//	%+F       "!1!!1!!2!!1!!1!!2!!0!!0!!0!!8!!78!!79!!66!!85!!70!!70!!69!!82!!0!";
//	%+F       objfile:=open(filid,6,action,0);
//	%+FD      if TLIST > 1
//	%+FD      then
//	%+FD %-E       outstring("AT&T-COFF/286 RELOCATABLE OUTPUT: ");
//	%+FDE          outstring("AT&T-COFF/386 RELOCATABLE OUTPUT: ");
//	%+FD           outstring(filid); outimage;
//	%+FD      endif;
//	%+F 
//	%+F       CSEGID.val:=0; StrLng.val:=0; FstStr:=LstStr:=none;
//	%+F 
//	%+F       ----  Initiate File Header  ---
//	%+F %-E   Fhdr.Magic:=338;  -- Magic number (AT&T-COFF 286)
//	%+FE      Fhdr.Magic:=332;  -- Magic number (AT&T-COFF 386)
//	%+F       Fhdr.nSectn:=3;   -- Number of Sections
//	%+F       Fhdr.TimDat:=0;   -- Time and Date stamp
//	%+F       Fhdr.SymPtr:=0;   -- File pointer to Symbol Table
//	%+F       Fhdr.nSmb:=0;     -- Number of entries in Symbol Table
//	%+F       Fhdr.OptHdr:=0;   -- Number of byte in Optional Header
//	%+F       Fhdr.Flags:=260;  -- Flags
//	%+F 
//	%+F       ----  Initiate .text Section Header ---
//	%+F       Shdr(0):=NEWOBX(size(CoffSectionHeader));
//	%+F       Shdr(0).Ident:=String2Id8(".text"); -- Zero padded Name
//	%+F       Shdr(0).pAddr:=0;       -- Physical address of Section
//	%+F       Shdr(0).pAddr:=0;       -- Virtual address of Section
//	%+F       Shdr(0).Lng:=0;         -- Section size in bytes
//	%+F       Shdr(0).ScnPtr:=0;      -- File pointer to raw Data
//	%+F       Shdr(0).RelPtr:=0;      -- File pointer to Relocation entries
//	%+F       Shdr(0).LnoPtr:=0;      -- File pointer to Line Number entries
//	%+F       Shdr(0).nReloc:=0;      -- Number of Relocation entries
//	%+F       Shdr(0).nLno:=0;        -- Number of Line Number entries
//	%+F       Shdr(0).Flags:=32;      -- Flags STYP_TEXT
//	%+F 
//	%+F       ----  Initiate .data Section Header ---
//	%+F       Shdr(1):=NEWOBX(size(CoffSectionHeader));
//	%+F       Shdr(1).Ident:=String2Id8(".data"); -- Zero padded Name
//	%+F       Shdr(1).pAddr:=0;       -- Physical address of Section
//	%+F       Shdr(1).pAddr:=0;       -- Virtual address of Section
//	%+F       Shdr(1).Lng:=0;         -- Section size in bytes
//	%+F       Shdr(1).ScnPtr:=0;      -- File pointer to raw Data
//	%+F       Shdr(1).RelPtr:=0;      -- File pointer to Relocation entries
//	%+F       Shdr(1).LnoPtr:=0;      -- File pointer to Line Number entries
//	%+F       Shdr(1).nReloc:=0;      -- Number of Relocation entries
//	%+F       Shdr(1).nLno:=0;        -- Number of Line Number entries
//	%+F       Shdr(1).Flags:=64;      -- Flags STYP_DATA
//	%+F 
//	%+F       ----  Initiate .bss  Section Header ---
//	%+F       Shdr(2):=NEWOBX(size(CoffSectionHeader));
//	%+F       Shdr(2).Ident:=String2Id8(".bss"); -- Zero padded Name
//	%+F       Shdr(2).pAddr:=0;       -- Physical address of Section
//	%+F       Shdr(2).pAddr:=0;       -- Virtual address of Section
//	%+F       Shdr(2).Lng:=0;         -- Section size in bytes
//	%+F       Shdr(2).ScnPtr:=0;      -- File pointer to raw Data
//	%+F       Shdr(2).RelPtr:=0;      -- File pointer to Relocation entries
//	%+F       Shdr(2).LnoPtr:=0;      -- File pointer to Line Number entries
//	%+F       Shdr(2).nReloc:=0;      -- Number of Relocation entries
//	%+F       Shdr(2).nLno:=0;        -- Number of Line Number entries
//	%+F       Shdr(2).Flags:=128;     -- Flags STYP_BSS
//	%+F 
//	%+F       ----  Map Segments  ==>  Sections  ----
//	%+F       ix.val:=0; repeat while ix.val < DIC.nSegm
//	%+F       do ix.val:=ix.val+1; segid:=DIC.Segm(ix.HI).elt(ix.LO);
//	%+F          seg:=DICREF(segid); seg.lng:=seg.rela; seg.rela.val:=0;
//	%+F          seg.FstRel:=none; seg.LstRel:=none;
//	%+F          case 0:3 (seg.type)
//	%+F          when aDGRP,aDATA,aLINE: seg.Sectn:=1
//	%+F          when aCODE: seg.Sectn:=0 endcase;
//	%+F          Shdr(seg.Sectn).Lng:=Shdr(seg.Sectn).Lng+seg.Lng.val;
//	%+F       endrepeat;
//	%+F       Shdr(0).Lng:=Shdr(0).Lng+AllignDiff(%Shdr(0).Lng%);
//	%+F       Shdr(1).Lng:=Shdr(1).Lng+AllignDiff(%Shdr(1).Lng%);
//	%+F       Shdr(2).Lng:=Shdr(2).Lng+AllignDiff(%Shdr(2).Lng%);
//	%+F       Shdr(0).ScnPtr:=RawDataPos:=(Fhdr.nSectn*40)+20;
//	%+F       Shdr(1).ScnPtr:=Shdr(0).ScnPtr+Shdr(0).Lng;
//	%+F       Shdr(0).vAddr:=Shdr(0).pAddr:=0;
//	%+F       Shdr(1).vAddr:=Shdr(1).pAddr:=Shdr(0).pAddr+Shdr(0).Lng;
//	%+F       Shdr(2).vAddr:=Shdr(2).pAddr:=Shdr(1).pAddr+Shdr(1).Lng;
//	%+F       FilLng:=RawDataPos+Shdr(2).pAddr+Shdr(2).Lng;
//	%+F       ix.val:=0; repeat while ix.val < DIC.nSegm
//	%+F       do ix.val:=ix.val+1; segid:=DIC.Segm(ix.HI).elt(ix.LO);
//	%+F          seg:=DICREF(segid); seg.pAddr:=Shdr(seg.Sectn).pAddr;
//	%+F          Shdr(seg.Sectn).pAddr:=Shdr(seg.Sectn).pAddr+seg.Lng.val;
//	%+F       endrepeat
//	%+F       Shdr(0).pAddr:=Shdr(0).vAddr;
//	%+F       Shdr(1).pAddr:=Shdr(1).vAddr;
//	%+F       Shdr(2).pAddr:=Shdr(2).vAddr;
//	%+F       if Shdr(0).Lng=0 then Shdr(0).ScnPtr:=0 endif;
//	%+F       if Shdr(1).Lng=0 then Shdr(1).ScnPtr:=0 endif;
//	%+F       if Shdr(2).Lng=0 then Shdr(2).ScnPtr:=0 endif;
//	%+F       SecDisp:=2;
//	%+F       PubDisp:=SecDisp+(2*Fhdr.nSectn);
//	%+F       ExtDisp:=PubDisp+DIC.nPubl;
//	%+F 
//	%+F       ----  Complete Buffer Scanning  ----
//	%+F       repeat ReadCBUF; ReadCREL while CBUF.nxt <> 0
//	%+F       do ----  PROCESS ALL BUFFERS FROM PASS 1  ----
//	%+F %-E      BlkBase:=CBUF.ofst; segx:=CBUF.segx;
//	%+FE         BlkBase.HighWord.val:=CBUF.ofstHI; 
//	%+FE         BlkBase.LowWord.val:=CBUF.ofstLO; 
//	%+FE         segx.val:=CBUF.segx;
//	%+F          k:=0; seg:=DICREF(DIC.Segm(segx.HI).elt(segx.LO));
//	%+F          repeat while k < CREL.nxt
//	%+F          do ----  PROCESS ALL RELOCATION PACKETS  ----
//	%+F             pkt:=CREL.elt(k); k:=k+1;
//	%+F             BlkOfst.val:=wAND(pkt.mrk,1023);
//	%+F             RelType:=wAND(wSHR(pkt.mrk,10),3);
//	%+F             FieldType:=wAND(wSHR(pkt.mrk,12),7);
//	%+F 
//	%+FD            if FieldType>4
//	%+FD            then IERR("COFFUT:FieldType"); FieldType:=0 endif;
//	%+FD            if RelType>2 then IERR("COFFUT:RelType"); RelType:=0 endif;
//
//	%+F             case 0:2 (RelType)
//	%+F             when rSEG: -- Local Segment (+disp) rel Composed Segment
//	%+F                        segx:=pkt.segx; disp.val:=0;
//	%+F                    L1: relseg:=DICREF(DIC.Segm(segx.HI).elt(segx.LO));
//	%+F                        case 0:4 (FieldType)
//	%+F                        when fPOINTER: -- Segment Relative Reloc --
//	%+F                             dd.LO:=CBUF.byt(BlkOfst.val);
//	%+F %-E                         dd.HI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dd.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dd.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+FE                            dd.HI:=CBUF.byt(BlkOfst.val+3);
//	%+F                             dx.val:=(disp.val+dd.val)+relseg.pAddr;
//	%+F                             CBUF.byt(BlkOfst.val):=dx.LO;
//	%+F %-E                         CBUF.byt(BlkOfst.val+1):=dx.HI;
//	%+FE                            CBUF.byt(BlkOfst.val+1):=dx.LOHI;
//	%+FE                            CBUF.byt(BlkOfst.val+2):=dx.HILO;
//	%+FE                            CBUF.byt(BlkOfst.val+3):=dx.HI;
//	%+F                             NewReloc(seg,BlkBase.val+BlkOfst.val,
//	%+F                                  SecDisp+(2*relseg.Sectn),R_DIR32)
//	%+F                        when fSEGMENT: -- Segment Relative Reloc --
//	%+F                             dd.LO:=CBUF.byt(BlkOfst.val);
//	%+F %-E                         dd.HI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dd.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dd.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+FE                            dd.HI:=CBUF.byt(BlkOfst.val+3);
//	%+F                             dx.val:=(disp.val+dd.val)+relseg.pAddr;
//	%+F                             CBUF.byt(BlkOfst.val):=dx.LO;
//	%+F %-E                         CBUF.byt(BlkOfst.val+1):=dx.HI;
//	%+FE                            CBUF.byt(BlkOfst.val+1):=dx.LOHI;
//	%+FE                            CBUF.byt(BlkOfst.val+2):=dx.HILO;
//	%+FE                            CBUF.byt(BlkOfst.val+3):=dx.HI;
//	%+F                             NewReloc(seg,BlkBase.val+BlkOfst.val,
//	%+F                                  SecDisp+(2*relseg.Sectn),R_DIR32)
//	%+F                        when fOFFSET: -- Segment Relative Reloc --
//	%+F                             dd.LO:=CBUF.byt(BlkOfst.val);
//	%+F %-E                         dd.HI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dd.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dd.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+FE                            dd.HI:=CBUF.byt(BlkOfst.val+3);
//	%+F                             dx.val:=(disp.val+dd.val)+relseg.pAddr;
//	%+F                             CBUF.byt(BlkOfst.val):=dx.LO;
//	%+F %-E                         CBUF.byt(BlkOfst.val+1):=dx.HI;
//	%+FE                            CBUF.byt(BlkOfst.val+1):=dx.LOHI;
//	%+FE                            CBUF.byt(BlkOfst.val+2):=dx.HILO;
//	%+FE                            CBUF.byt(BlkOfst.val+3):=dx.HI;
//	%+F                             NewReloc(seg,BlkBase.val+BlkOfst.val,
//	%+F                                  SecDisp+(2*relseg.Sectn),R_DIR32)
//	%+F                        when fFULLDISP: -- SelfRelative Relocation --
//	%+E                             if segx.val <> CBUF.segx
//	%+E                             then -- InterSegment Selfrelative Reloc
//	%+E                                  disp.val:=disp.val
//	%+E                                          + (relseg.pAddr-seg.pAddr);
//	%+E                             endif;
//	%+F                             dd.LO:=CBUF.byt(BlkOfst.val);
//	%+F %-E                         dd.HI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dd.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dd.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+FE                            dd.HI:=CBUF.byt(BlkOfst.val+3);
//	%+F                             dx.val:=(disp.val+dd.val)
//	%+F                                 -(BlkBase.val+BlkOfst.val+AllignFac)
//	%+F                             CBUF.byt(BlkOfst.val):=dx.LO;
//	%+F %-E                         CBUF.byt(BlkOfst.val+1):=dx.HI;
//	%+FE                            CBUF.byt(BlkOfst.val+1):=dx.LOHI;
//	%+FE                            CBUF.byt(BlkOfst.val+2):=dx.HILO;
//	%+FE                            CBUF.byt(BlkOfst.val+3):=dx.HI;
//	%+F                        when fBYTEDISP: -- SelfRelative Relocation --
//	%+F                             dd.val:=CBUF.byt(BlkOfst.val);
//	%+F                             dx.val:=(disp.val+dd.val)
//	%+F                                    -(BlkBase.val+BlkOfst.val+1)
//	%+F                             if dx.HI <> BOOL2BYTE(dx.LO>127)
//	%+F                             then Ed(errmsg,"Byte Disp Overflow --");
//	%+FD                                 EdHex(errmsg,dd.val,4);
//	%+FD                                 EdHex(errmsg,dx.val,4);
//	%+FD                                 if RelType = rFIX
//	%+FD                                 then Ed(errmsg," Fix(");
//	%+FD                                      edint(errmsg,pkt.fix.val);
//	%+FD                                      ed(errmsg,") created at line ");
//	%+FD                                      edwrd(errmsg,Fx.line);
//	%+FD                                 endif;
//	%+F                                  IERR(" ");
//	%+F                             endif;
//	%+F                             CBUF.byt(BlkOfst.val):=dx.val;
//	%+F                        endcase;
//	%+F             when rEXT: -- External Symbol (+dx)
//	%+F                        case 0:4 (FieldType)
//	%+F                        when fPOINTER:
//	%+F                             NewReloc(seg,BlkBase.val+BlkOfst.val,
//	%+F                                ExtDisp+pkt.extx.val-1,R_DIR32)
//	%+F                        when fSEGMENT:
//	%+F                             NewReloc(seg,BlkBase.val+BlkOfst.val,
//	%+F                                ExtDisp+pkt.extx.val-1,R_DIR32)
//	%+F                        when fOFFSET:
//	%+F                             NewReloc(seg,BlkBase.val+BlkOfst.val,
//	%+F                                ExtDisp+pkt.extx.val-1,R_DIR32)
//	%+F                        when fFULLDISP:
//	%+F                             dx.LO:=CBUF.byt(BlkOfst.val);
//	%+F %-E                         dx.HI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dx.LOHI:=CBUF.byt(BlkOfst.val+1);
//	%+FE                            dx.HILO:=CBUF.byt(BlkOfst.val+2);
//	%+FE                            dx.HI:=CBUF.byt(BlkOfst.val+3);
//	%+F                             dx.val:=dx.val
//	%+F                                 -(BlkBase.val+BlkOfst.val
//	%+F                                                +seg.pAddr+AllignFac)
//	%+FD                            if TLIST > 0
//	%+FD                            then outstring("** RELOC:");
//	%+FD                                 outint(seg.pAddr);
//	%+FD                                 printout(sysout);
//	%+FD                            endif;
//	%+F                             CBUF.byt(BlkOfst.val):=dx.LO;
//	%+F %-E                         CBUF.byt(BlkOfst.val+1):=dx.HI;
//	%+FE                            CBUF.byt(BlkOfst.val+1):=dx.LOHI;
//	%+FE                            CBUF.byt(BlkOfst.val+2):=dx.HILO;
//	%+FE                            CBUF.byt(BlkOfst.val+3):=dx.HI;
//	%+F                             NewReloc(seg,BlkBase.val+BlkOfst.val,
//	%+F                                ExtDisp+pkt.extx.val-1,R_PCRLONG)
//	%+F                        when fBYTEDISP:
//	%+F                                        IERR("Ext BYTEDISP");
//	%+F                        endcase;
//	%+F             when rFIX: Fx:=FIXTAB(pkt.fix.HI).elt(pkt.fix.LO);
//	%+FC                 if not Fx.Matched
//	%+FC                 then edint(errmsg,pkt.fix.val);
//	%+FC                      ed(errmsg,") created at line ");
//	%+FC                      edwrd(errmsg,Fx.line);
//	%+FC                      IERR("Missing FIXUP(");
//	%+FC                 else
//	%+F                       segx:=GetSegx(Fx.segid); disp:=Fx.rela; goto L1;
//	%+FC                 endif;
//	%+F             endcase;
//	%+F          endrepeat;
//	%+F          OutRawData;
//	%+F       endrepeat;
//	%+F 
//	%+F       ----  Output Relocations for each Section  ----
//	%+F       BegCOFFBLK;
//	%+F       EnvLocate(ObjFile,FilLng+1);
//	%+F       if Status <> 0 then IERR("COFFUT-xx") endif;
//	%+F       n:=0; repeat while n < Fhdr.nSectn
//	%+F       do Shdr(n).RelPtr:=FilLng;
//	%+F          ix.val:=0; repeat while ix.val < DIC.nSegm
//	%+F          do ix.val:=ix.val+1; segid:=DIC.Segm(ix.HI).elt(ix.LO);
//	%+F             seg:=DICREF(segid);
//	%+F             if seg.Sectn=n
//	%+F             then RelObj:=seg.FstRel;
//	%+F                  repeat while RelObj <> none
//	%+F                  do CoffSpace(%10%);
//	%+F                     Shdr(n).nReloc:=Shdr(n).nReloc+1;
//	%+F                     PutChars(10,@RelObj.Cpkt.vAddr);
//	%+F                     RelObj:=RelObj.next;
//	%+F                  endrepeat;
//	%+F             endif;
//	%+F          endrepeat
//	%+F          if Shdr(n).nReloc=0 then Shdr(n).RelPtr:=0
//	%+F          else
//	%+F               nReloc:=Shdr(n).nReloc;
//	%+F               FilLng:=FilLng+(nReloc*10);
//	%+F          endif;
//	%+F          n:=n+1;
//	%+F       endrepeat
//	%+F       OutCOFFBLK;
//	%+F 
//	%+F       ----  Output Line Numbers for each Section  ----
//	%+F 
//	%+F       ----  Output Symbol Table  ----
//	%+F       EnvLocate(ObjFile,FilLng+1); Fhdr.SymPtr:=FilLng;
//	%+F       if Status <> 0 then IERR("COFFUT-xx") endif;
//	%+F
//	%+F       -- Output  .file <FileName>
//	%+F       CoffSpace(%18%);
//	%+F       Csmb.Ident:=String2Id8(".file");
//	%+F       Csmb.val:=0;
//	%+F       Csmb.Sectn:=65534;
//	%+F       Csmb.Type:=0;
//	%+F       Csmb.Scls:=103;
//	%+F       Csmb.nAux:=1;
//	%+F       PutChars(18,@Csmb.Ident.Char); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F       CoffSpace(%18%);
//	%+F       edsymb(sysedit,RELID); filid:=Pickup(sysedit);
//	%+F       if filid.nchr > 14 then filid.nchr:=14 endif;
//	%+F       APX_SMOVEI(filid.nchr,@Caux.Fnam,filid.chradr);
//	%+F       APX_SFILL(0 qua character,18-filid.nchr,@Caux.Fnam(filid.nchr));
//	%+F       Caux.dum0:=0; Caux.dum1:=0;
//	%+F       PutChars(18,@Caux.Fnam); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F
//	%+F       -- Output  .text <SectionSpec>
//	%+F       if Fhdr.nSmb <> SecDisp then IERR("COFF:SymbDisp-1") endif;
//	%+F       CoffSpace(%18%);
//	%+F       Csmb.Ident:=String2Id8(".text");
//	%+F       Csmb.val:=Shdr(0).pAddr;
//	%+F       Csmb.Sectn:=1;
//	%+F       Csmb.Type:=0;
//	%+F       Csmb.Scls:=3;
//	%+F       Csmb.nAux:=1;
//	%+F       PutChars(18,@Csmb.Ident.Char); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F       CoffSpace(%18%);
//	%+F       Caux.Lng:=Shdr(0).Lng;
//	%+F       Caux.nReloc:=Shdr(0).nReloc;
//	%+F       Caux.nLno:=Shdr(0).nLno;
//	%+F       Caux.dum2:=0; Caux.dum3:=0; Caux.dum4:=0;
//	%+F       PutChars(18,@Caux.Fnam); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F
//	%+F       -- Output  .data <SectionSpec>
//	%+F       CoffSpace(%18%);
//	%+F       Csmb.Ident:=String2Id8(".data");
//	%+F       Csmb.val:=Shdr(1).pAddr;
//	%+F       Csmb.Sectn:=2;
//	%+F       Csmb.Type:=0;
//	%+F       Csmb.Scls:=3;
//	%+F       Csmb.nAux:=1;
//	%+F       PutChars(18,@Csmb.Ident.Char); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F       CoffSpace(%18%);
//	%+F       Caux.Lng:=Shdr(1).Lng;
//	%+F       Caux.nReloc:=Shdr(1).nReloc;
//	%+F       Caux.nLno:=Shdr(1).nLno;
//	%+F       Caux.dum2:=0; Caux.dum3:=0; Caux.dum4:=0;
//	%+F       PutChars(18,@Caux.Fnam); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F
//	%+F       -- Output  .bss <SectionSpec>
//	%+F       CoffSpace(%18%);
//	%+F       Csmb.Ident:=String2Id8(".bss");
//	%+F       Csmb.val:=Shdr(2).pAddr;
//	%+F       Csmb.Sectn:=3;
//	%+F       Csmb.Type:=0;
//	%+F       Csmb.Scls:=3;
//	%+F       Csmb.nAux:=1;
//	%+F       PutChars(18,@Csmb.Ident.Char); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F       CoffSpace(%18%);
//	%+F       Caux.Lng:=Shdr(2).Lng;
//	%+F       Caux.nReloc:=Shdr(2).nReloc;
//	%+F       Caux.nLno:=Shdr(2).nLno;
//	%+F       Caux.dum2:=0; Caux.dum3:=0; Caux.dum4:=0;
//	%+F       PutChars(18,@Caux.Fnam); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F 
//	%+F       ----  Output Public Definitions  ----
//	%+F       if Fhdr.nSmb <> PubDisp then IERR("COFF:SymbDisp-2") endif;
//	%+F       ix.val:=0; repeat while ix.val < DIC.nPubl
//	%+F       do ix.val:=ix.val+1; pubid:=DIC.Publ(ix.HI).elt(ix.LO);
//	%+F          pub:=DICREF(pubid); segx:=pub.segx;
//	%+F          seg:=DICREF(DIC.Segm(segx.HI).elt(segx.LO));
//	%+F          CoffSpace(%18%);
//	%+F          Csmb.Ident:=Symb2Id8(pubid);
//	%+F          Csmb.val:=pub.rela.val+seg.pAddr;
//	%+F          Csmb.Sectn:=seg.Sectn+1;
//	%+F          Csmb.Type:=0;
//	%+F          Csmb.Scls:=2;
//	%+F          Csmb.nAux:=0;
//	%+F          PutChars(18,@Csmb.Ident.Char); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F       endrepeat;
//	%+F 
//	%+F       ----  Output External References  ----
//	%+F       if Fhdr.nSmb <> ExtDisp then IERR("COFF:SymbDisp-3") endif;
//	%+F       ix.val:=0; repeat while ix.val < DIC.nExtr
//	%+F       do ix.val:=ix.val+1;
//	%+F          CoffSpace(%18%);
//	%+F          Csmb.Ident:=Symb2Id8(DIC.Extr(ix.HI).elt(ix.LO));
//	%+F          Csmb.val:=0;
//	%+F          Csmb.Sectn:=0;
//	%+F          Csmb.Type:=0;
//	%+F          Csmb.Scls:=2;
//	%+F          Csmb.nAux:=0;
//	%+F          PutChars(18,@Csmb.Ident.Char); Fhdr.nSmb:=Fhdr.nSmb+1;
//	%+F       endrepeat;
//	%+F       OutCOFFBLK;
//
//	%+F       if StrLng.val <> 0
//	%+F       then ----  Output String Table  ----
//	%+F            CoffSpace(%4%);
//	%+F            StrLng.val:=StrLng.val+4; PutDWord(%StrLng%);
//	%+F            repeat while FstStr <> none
//	%+F            do EdSymb(sysedit,FstStr.smbx); s:=Pickup(sysedit);
//	%+F               FstStr:=FstStr.next; CoffSpace(%s.nchr+1%);
//	%+F               PutChars(s.nchr,s.chradr); PutByte(%0%);
//	%+F            endrepeat; 
//	%+F            OutCOFFBLK;
//	%+F       endif;
//	%+F 
//	%+F       ----  Output File and Section Headers  ----
//	%+F       EnvLocate(ObjFile,1);
//	%+F       if Status <> 0 then IERR("COFFUT-xx") endif;
//	%+F       bf.chradr:=@Fhdr.Magic; bf.nchr:=20;
//	%+F       EnvOutBytes(ObjFile,bf);
//	%+F       if Status <> 0 then FILERR(ObjFile,"COFFUT-xx") endif;
//	%+F       n:=0; repeat while n < Fhdr.nSectn
//	%+F       do bf.chradr:=@Shdr(n).Ident.Char; bf.nchr:=40;
//	%+F          EnvOutBytes(ObjFile,bf);
//	%+F          if Status <> 0 then FILERR(ObjFile,"COFFUT-xx") endif;
//	%+F          n:=n+1;
//	%+F       endrepeat;
//	%+F 
//	%+F       --- Check Location Counters ---
//	%+FC      ix.val:=0; repeat while ix.val < DIC.nSegm
//	%+FC      do ix.val:=ix.val+1;
//	%+FC         segid:=DIC.Segm(ix.HI).elt(ix.LO);
//	%+FC         seg:=DICREF(segid);
//	%+FC         if seg.rela <> seg.lng
//	%+FC         then Ed(errmsg,"Segment-Check "); EdWrd(errmsg,ix.val);
//	%+FC              EdChar(errmsg,':'); EdSymb(errmsg,segid);
//	%+FC              Ed(errmsg," (pass1="); EdWrd(errmsg,seg.lng.val);
//	%+FC              Ed(errmsg,",pass2="); EdWrd(errmsg,seg.rela.val);
//	%+FC              IERR(") fails");
//	%+FC         endif;
//	%+FC      endrepeat;
//	%+F 
//	%+F       EnvClose(scrfile,nostring);
//	%+F       if status<>0 then FILERR(scrfile,"COFFUT-6") endif; scrfile:=0;
//	%+F       EnvClose(objfile,nostring);
//	%+F       if status<>0 then FILERR(objfile,"COFFUT-7") endif; objfile:=0;
//	%+F end;
//
//	end;
//
}