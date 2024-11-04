package bec.compileTimeStack;

import bec.segment.MemAddr;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_GOTO;
import bec.virtualMachine.SVM_PUSH;

public class CTStack {
	
//  --- Current Stack ---
//  range(0:8) StackDepth87; -- initial(0)
	public static StackItem TOS; // Top of Compile-time stack
	static StackItem BOS; // Bot of Compile-time stack
	public static StackItem SAV; // Last Compile-time stack-item for which
                          // the corresponding Runtime-item is saved.
                          // NOTE: SAV =/= none implies TOS =/= none


	public static void push(StackItem s) {
		if(s.suc != null || s.pred != null) Util.IERR("CODER.CheckPush");
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
		if(item == null || item == SAV) Util.IERR("CODER.Precede");
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
		if(TOS == null) Util.IERR("CODER.CheckPop");
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
		if(! (s instanceof Address)) Util.IERR("CheckRef fails");
	}

	public static void checkTosRef() {
		checkRef(TOS);
	}

	public static void checkSosRef() {
		checkRef(TOS.suc);		
	}

	public static void checkSosValue() {
		if(TOS.suc instanceof Address) Util.IERR("CheckSosValue fails");
	}

	public static void checkTosType(int t) {
		if(TOS.type != t) Util.IERR("Illegal type of TOS");
	}

	public static void checkSosType(int t) {
		if(TOS.suc.type != t) Util.IERR("Illegal type of TOS");
	}

	public static void checkTosInt() {
		switch(TOS.type) {
			case Scode.TAG_INT, Scode.TAG_SINT: break; 
			default: Util.IERR("Illegal type of TOS");
		}
	}

	public static void checkTosArith() {
		switch(TOS.type) {
			case Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL: break; 
			default: Util.IERR("Illegal type of TOS");
		}
	}

	public static void checkSosInt() {
		switch(TOS.suc.type) {
			case Scode.TAG_INT, Scode.TAG_SINT: break; 
			default: Util.IERR("Illegal type of TOS");
		}
	}

	public static void checkSosArith() {
		switch(TOS.suc.type) {
			case Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL: break; 
			default: Util.IERR("Illegal type of TOS");
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
		else Util.IERR("Illegal type of SOS");
	}

	public static void checkTypesEqual() {
//	%+C begin range(0:MaxType) t1,t2;
		int t1 = TOS.type;
		int t2 = TOS.suc.type;
		if(t1 == t2) return;
		if(t1 > Scode.TAG_SIZE) Util.IERR("CODER.CheckTypesEqual-1");
		if(t2 > Scode.TAG_SIZE) Util.IERR("CODER.CheckTypesEqual-2");
		t1 = arithType(t1,t1); t2 = arithType(t2,t2);
		if(t1 == t2) return;
//	%+C       if (t1>T_BYT1) or (t2>T_BYT1)
		Util.IERR("Different types of TOS and SOS");
	}

	public static void checkStackEmpty() {
		if(TOS != null) Util.IERR("Stack should be empty");
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
			Global.PSEG.emit(new SVM_PUSH(adr, 1), "assertObjStacked: "+tos);

		}
	}

	public static void assertAtrStacked() {
		assertObjStacked();
		Address tos = (Address) CTStack.TOS;
		if(tos.atrState == Address.State.NotStacked) {
//	           TOS qua Address.AtrState:=FromConst;
//	           Qf2(qPUSHC,0,FreePartReg,cVAL,TOS qua Address.Offset);
			Util.IERR("NOT IMPL");
		} else if(tos.atrState == Address.State.Calculated) {
			if(tos.offset != 0) {
//	%+E             Qf2(qLOADC,0,qEAX,cVAL,TOS qua Address.Offset);
//	%+E             Qf1(qPOPR,qEBX,cVAL);
//	%+E             Qf2(qDYADR,qADD,qEAX,cVAL,qEBX); Qf1(qPUSHR,qEAX,cVAL);
//	                TOS qua Address.Offset:=0;
				CTStack.dumpStack();
				Util.IERR("NOT IMPL");
			}
		}	      
	}

	public static void dumpStack() {
		StackItem item;
		StringBuilder sb = new StringBuilder();
//	%+D   if InputTrace <> 0 then printout(inptrace) endif;
		String lead = " Current Stack";
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

	private static int arithType(int t1, int t2) { // export range(0:MaxType) ct;
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
