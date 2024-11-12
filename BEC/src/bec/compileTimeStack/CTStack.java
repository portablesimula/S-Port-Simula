package bec.compileTimeStack;

import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_CALL;
import bec.virtualMachine.SVM_GOTO;
import bec.virtualMachine.SVM_NOT_IMPL;
import bec.virtualMachine.SVM_PUSH;
import bec.virtualMachine.SVM_POPtoREG;

public class CTStack {
	
//  --- Current Stack ---
//  range(0:8) StackDepth87; -- initial(0)
	public static StackItem TOS; // Top of Compile-time stack
	static StackItem BOS; // Bot of Compile-time stack
	public static StackItem SAV; // Last Compile-time stack-item for which
                          // the corresponding Runtime-item is saved.
                          // NOTE: SAV =/= none implies TOS =/= none


	public static void push(StackItem s) {
		if(s.suc != null || s.pred != null) STKERR("CODER.CheckPush");
		if(TOS == null) {
			TOS = BOS = s; s.suc = null;
		} else {
			s.suc = TOS;
			TOS = s.suc.pred = s;
		}
//	%+D   if TraceMode > 1 then DumpStack endif;
	} // endmacro;
	
	public static void pushTemp(int type) {
		Temp tmp = new Temp(type);
//		Util.IERR("NOT IMPL");
//	      tmp.kind:=K_Temp; tmp.type:=type;
//	      tmp qua StackItem.repdist:=TTAB(type).nbyte;
//	%+C   if TTAB(type).nbyte=0 then IERR("No info TYPE-3") endif;
		push(tmp);
	}


	public static void precede(StackItem newItem, StackItem item) {
//	begin
//	%+D   RST(R_Precede);
		if(item == null || item == SAV) STKERR("CODER.Precede");
//	%+D   if TraceMode > 1
//	%+D   then setpos(sysout,14); outstring("***PRECEDE:  "); print(item);
//	%+D        setpos(sysout,14); outstring("        BY:  "); print(new);
//	%+D   endif;
	      newItem.suc = item.suc; newItem.pred = item; item.suc = newItem;
	      if(newItem.suc != null) newItem.suc.pred = newItem;
	      else if(BOS == item) BOS = newItem;
//	%+D   if TraceMode > 1 then DumpStack endif;
	}

	public static StackItem pop() {
		if(TOS == null) STKERR("CODER.CheckPop");
		StackItem x = TOS;
		TOS = x.suc;
		if(TOS == null) BOS = null; else TOS.pred = null;
		x.suc = x.pred = null;
		return x;
	}

	public static StackItem takeTOS() { //; export ref(StackItem) x;
		return pop();
	}
//
//	--- private static void TakeRef; export ref(Address) x;
//	--- begin
//	--- %+D   RST(R_TakeRef);
//	--- %+C   if TOS = none then IERR("CODER.TakeRef"); x:=none;
//	--- %+C   else
//	--- %+C        checkRef(TOS);
//	---            x:=TakeTOS;
//	--- %+C   endif;
//	--- end;

	private static void checkRef(StackItem s) {
		if(! (s instanceof Address)) STKERR("CheckRef fails");
	}
	
	private static void STKERR(String msg) {
		System.out.println("\nERROR: " + msg + " ================================================");
		CTStack.dumpStack("STKERR: ");
		Global.PSEG.dump("STKERR: ");
		Util.IERR("FORCED EXIT");
	}

	public static void checkTosRef() {
		checkRef(TOS);
	}

	public static void checkSosRef() {
		checkRef(TOS.suc);		
	}

	public static void checkSosValue() {
		if(TOS.suc instanceof Address) STKERR("CheckSosValue fails");
	}

	public static void checkTosType(int t) {
		if(TOS.type != t) STKERR("Illegal type of TOS");
	}

	public static void checkSosType(int t) {
		if(TOS.suc.type != t) STKERR("Illegal type of TOS");
	}

	public static void checkTosInt() {
		switch(TOS.type) {
			case Scode.TAG_INT, Scode.TAG_SINT: break; 
			default: STKERR("Illegal type of TOS");
		}
	}

	public static void checkTosArith() {
		switch(TOS.type) {
			case Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL: break; 
			default: STKERR("Illegal type of TOS");
		}
	}

	public static void checkSosInt() {
		switch(TOS.suc.type) {
			case Scode.TAG_INT, Scode.TAG_SINT: break; 
			default: STKERR("Illegal type of TOS");
		}
	}

	public static void checkSosArith() {
		switch(TOS.suc.type) {
			case Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL: break; 
			default: STKERR("Illegal type of TOS");
		}
	}
	
//	public final static int TAG_BOOL  = 1;
//	public final static int TAG_CHAR  = 2;
//	public final static int	TAG_INT   = 3;
//	public final static int TAG_SINT  = 4;
//	public final static int TAG_REAL  = 5;
//	public final static int TAG_LREAL = 6;
//	public final static int TAG_AADDR = 7;
//	public final static int TAG_OADDR = 8;
//	public final static int TAG_GADDR = 9;
//	public final static int TAG_PADDR = 10;
//	public final static int TAG_RADDR = 11;
//	public final static int TAG_SIZE  = 12;

	public static void checkSosType2(int t1, int t2) {
		if(TOS.suc.type == t1) ; // OK
		else if(TOS.suc.type == t2) ; // OK
		else STKERR("Illegal type of SOS");
	}

	public static void checkTypesEqual() {
//	%+C begin range(0:MaxType) t1,t2;
		int t1 = TOS.type;
		int t2 = TOS.suc.type;
		if(t1 == t2) return;
		if(t1 > Scode.TAG_SIZE) STKERR("CODER.CheckTypesEqual-1");
		if(t2 > Scode.TAG_SIZE) STKERR("CODER.CheckTypesEqual-2");
		t1 = arithType(t1,t1); t2 = arithType(t2,t2);
		if(t1 == t2) return;
//	%+C       if (t1>T_BYT1) or (t2>T_BYT1)
		STKERR("Different types of TOS and SOS");
	}

	public static void checkStackEmpty() {
		if(TOS != null) STKERR("Stack should be empty");
		TOS = BOS = null;
	}

	public static void assertObjStacked() {
		Address tos = (Address) CTStack.TOS;
		if(tos.objState == Address.State.NotStacked) {
			tos.objState = Address.State.FromConst;
			MemAddr adr = tos.objadr;
//	           case 0:adrMax (adr.kind)
//	           when reladr,locadr: 
//	%+E             Qf3(qPUSHA,0,qEBX,cOBJ,adr);
//	           when segadr,fixadr,extadr:
//	%+E             Qf2b(qPUSHC,0,qEBX,cOBJ,0,adr);
//	%+C        otherwise IERR("CODER.AssertObjStacked-2")
//	           endcase;
			Global.PSEG.emit(new SVM_NOT_IMPL(), "assertObjStacked: "+tos);
		}
	}

	public static void assertAtrStacked() {
		assertObjStacked();
		Address tos = (Address) CTStack.TOS;
		if(tos.atrState == Address.State.NotStacked) {
//	           TOS qua Address.AtrState:=FromConst;
//	           Qf2(qPUSHC,0,FreePartReg,cVAL,TOS qua Address.Offset);
			Global.PSEG.emit(new SVM_NOT_IMPL(), "assertAtrStacked-1: "+tos);
		} else if(tos.atrState == Address.State.Calculated) {
			if(tos.offset != 0) {
//	%+E             Qf2(qLOADC,0,qEAX,cVAL,TOS qua Address.Offset);
//	%+E             Qf1(qPOPR,qEBX,cVAL);
//	%+E             Qf2(qDYADR,qADD,qEAX,cVAL,qEBX); Qf1(qPUSHR,qEAX,cVAL);
//	                TOS qua Address.Offset:=0;
				
//				CTStack.dumpStack("assertAtrStacked");
				Global.PSEG.emit(new SVM_NOT_IMPL(), "assertAtrStacked-2: "+tos);
			}
		}	      
	}
	
	public static void getTosAdjustedIn86(int reg) {
//	begin range(0:255) nbyte; infix(ValueItem) itm; range(0:MaxByte) type,cTYP;
		if(TOS == null) Util.IERR("CODER.GetTosAdjusted-1");
//	      type:=TOS.type; nbyte:=TTAB(type).nbyte;
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	%+C   if nbyte=0 then IERR("CODER.GetTosAdjustedIn86-1") endif;
//	%+C   if nbyte > AllignFac
//	%+C   then WARNING("CODER.GetTosAdjusted-2");
//	%+C        repeat while nbyte > AllignFac
//	%+C        do qPOPKill(AllignFac); nbyte:=nbyte-AllignFac endrepeat
//	%+C   endif;
//	      if TOS.kind=K_Coonst
//	      then qPOPKill(nbyte); itm:=TOS qua Coonst.itm;
//	%-E        if type=T_NPADR
//	%-E        then case 0:adrMax (itm.base.kind)
//	%-E             when 0: Qf2(qLOADC,0,reg,cTYP,0) -- NOWHERE/NOBODY
//	%-E             when reladr,locadr: Qf3(qLOADA,0,reg,cTYP,itm.base);
//	%-E             when segadr,fixadr,extadr:
//	%-E                           Qf2b(qLOADC,0,reg,cTYP,F_OFFSET,itm.base);
//	%-E %+C         otherwise IERR("CODER.GetTosAdjusted-4")
//	%-E             endcase;
//	%-E        else Qf2(qLOADC,0,reg,cTYP,itm.wrd) endif;
//	%+E        case 0:T_Max (type)
//	%+E        when T_OADDR,T_PADDR,T_RADDR:
//	%+E             case 0:adrMax (itm.base.kind)
//	%+E             when 0: Qf2(qLOADC,0,reg,cTYP,0) -- NONE/NOWHERE/NOBODY
//	%+E             when reladr,locadr: Qf3(qLOADA,0,reg,cTYP,itm.base);
//	%+E             when segadr,fixadr,extadr: Qf2b(qLOADC,0,reg,cTYP,0,itm.base)
//	%+E %+C         otherwise IERR("CODER.GetTosAdjusted-4")
//	%+E             endcase;
//	%+E        when T_BOOL,T_CHAR,T_BYT1,T_BYT2,T_WRD2,T_WRD4,
//	%+E             T_REAL,T_SIZE,T_AADDR:
//	%+E             Qf2(qLOADC,0,reg,cTYP,itm.int);
//	%+E %+C    otherwise IERR("CODER:GetTosAdjusted-6");Qf2(qLOADC,0,reg,cTYP,0);
//	%+E        endcase;
//	      else case 0:AllignFac (nbyte)
//	           when 1: GetTosValueIn86(LowPart(%reg%));
//	                   if RegSize(reg) > 1
//	                   then
//	%-E                     Qf2(qLOADC,0,HighPart(%reg%),cTYP,0);
//	%+E                     Qf2(qMOV,qZEXT,LowPart(%reg%),cTYP,LowPart(%reg%));
//	                   endif;
//	           when 2: GetTosValueIn86(WordReg(reg));
//	%+E                if RegSize(reg) > 2
//	%+E                then Qf2(qMOV,qZEXT,WordReg(reg),cTYP,WordReg(reg)) endif;
//	%+E        when 4: GetTosValueIn86(WholeReg(reg));
//	%+C        otherwise IERR("CODER.GetTosAdjusted-5")
//	           endcase;
//	      endif;
		
		getTosValueIn86(reg);
//		Util.IERR("");
	}

	public static void getTosValueIn86(int reg) { // import range(0:255) reg;
//	--     /* M} ikke bruke qDI p.g.a. RUPDATE. */
//	begin infix(MemAddr) opr; range(0:MaxType) type; range(0:MaxByte) cTYP;
//	      type:=TOS.type;
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	      case 0:K_Max (TOS.kind)
//	      when K_Temp,K_Result,K_Coonst: Qf1(qPOPR,reg,cTYP)
//	      when K_Address:
//	           opr:=GetTosSrcAdr;
//	           Qf4c(qLOAD,0,reg,cTYP,0,opr,0);
//	           Pop; pushTemp(type);
//	      endcase;
		StackItem tos = CTStack.TOS;
		if(tos instanceof Address) {
			Util.getTosSrcAdr();
			Global.PSEG.emit(new SVM_POPtoREG(reg), "getTosValueIn86'Address: ");
			Global.PSEG.dump("getTosValueIn86'Address: ");
			Util.IERR("NOT IMPL");
		} else if(tos instanceof Temp) {
			Global.PSEG.emit(new SVM_POPtoREG(reg), "getTosValueIn86'Temp: ");
			Global.PSEG.dump("getTosValueIn86'Temp: ");
//			Util.IERR("NOT IMPL");			
		} else if(tos instanceof Coonst) {
			Global.PSEG.dump("getTosValueIn86: ");
			Util.IERR("NOT IMPL");			
		}
	}

	public static void dumpStack(String title) {
		StackItem item;
		StringBuilder sb = new StringBuilder();
//	%+D   if InputTrace <> 0 then printout(inptrace) endif;
		String lead = title + ": Current Stack";
		if(TOS == null) {
			System.out.println(lead + ": **Empty**");
		} else {
			System.out.println(lead + ":"); item = TOS;
			lead ="  TOS: ";
			do {
//				if(item.type != 0) System.out.print(Scode.edTag(item.type) + '(' + item.repdist + ")-");
				if(item instanceof ProfileItem) System.out.println(lead+"PROFILE:  " + item);
				else if(item instanceof Address) System.out.println(lead+"REF:      " + item);
				else System.out.println(lead+"VAL:      " + item.getClass().getSimpleName() + "  " + item);
				lead ="       ";
				item = item.suc;
				if(item == BOS) lead ="  BOS: ";
			} while(item != null);
		}
	}

	public static int arithType(int t1, int t2) { // export range(0:MaxType) ct;
	switch(t1) {
	      case Scode.TAG_LREAL:
	    	  switch(t2) {
		    	  case Scode.TAG_LREAL: return Scode.TAG_LREAL;
		    	  case Scode.TAG_REAL:  return Scode.TAG_LREAL;
		    	  case Scode.TAG_INT:   return Scode.TAG_LREAL;
		    	  default: return t1;
	    	  }
	      case Scode.TAG_REAL:
	    	  switch(t2) {
		    	  case Scode.TAG_LREAL: return Scode.TAG_LREAL;
		    	  case Scode.TAG_REAL:  return Scode.TAG_REAL;
		    	  case Scode.TAG_INT:   return Scode.TAG_REAL;
		    	  default: return t1;
	    	  }
	      case Scode.TAG_INT:
	    	  switch(t2) {
		    	  case Scode.TAG_LREAL: return Scode.TAG_LREAL;
		    	  case Scode.TAG_REAL:  return Scode.TAG_REAL;
		    	  case Scode.TAG_INT:   return Scode.TAG_INT;
		    	  default: return t1;
	    	  }
	      default: return t1;
	}
	}

}
