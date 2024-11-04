package bec.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.segment.MemAddr;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_GOTOIF;
import bec.virtualMachine.SVM_POP;

public class RUPDATE extends Instruction {
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 */
	public RUPDATE() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	

	@Override
	public void doCode() {
		CTStack.dumpStack();
//		Visible Routine GQrupdate;
//		begin infix(MemAddr) opr; range(0:MaxByte) cTYP;
//		      range(0:MaxWord) nbyte; range(0:MaxType) st,dt;
//		%-E   range(0:nregs) segreg;
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
//		      st:=TOS.suc.type; dt:=TOS.type; nbyte:=TTAB(dt).nbyte;
//		      if dt<=T_MAX then cTYP:=cTYPE(dt) else cTYP:=cANY endif;
//		%+C   if nbyte=0 then IERR("CODER.GQrupdate-1") endif;
		MemAddr opr = Util.getTosDstAdr(); CTStack.pop();
		Global.PSEG.emit(new SVM_POP(opr, 1), ""+this);
		Global.PSEG.dump();
//		Util.IERR("");
//		      if TOS.kind=K_Address
//		      then
//		%-E        Qf3(qLOADA,0,qDI,cADR,opr);
//		%-E        opr.sbireg:=bOR(bAND(opr.sbireg,oSREG),rmDI);
//		%+E        Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg:=bEDI+iEDI;
//		           opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//		      endif;
//		%-E   case 0:8  (if nbyte<=8  then nbyte else 0)
//		%+E   case 0:12 (if nbyte<=12 then nbyte else 0)
//		      when 1: GetTosAsBYT1(qAL);
//		              PreMindMask:=wOR(PreMindMask,uAL); Qf1(qPUSHR,qAL,cTYP);
//		              Qf3(qSTORE,0,qAL,cTYP,opr)
//		      when 2: GetTosAsBYT2(qAX);
//		              PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//		              Qf3(qSTORE,0,qAX,cTYP,opr)
//		%+E   when 4: GetTosAsBYT4(qEAX);
//		%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//		%+E           Qf3(qSTORE,0,qEAX,cTYP,opr)
//		%+E   when 8: GetTosValueIn86R3(qEAX,qECX,0);
//		%+E           PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//		%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//		%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//		%+E           opr.rela.val:=opr.rela.val+AllignFac;
//		%+E           Qf3(qSTORE,0,qECX,cTYP,opr);
//		%+E   when 12: GetTosValueIn86R3(qEAX,qECX,qEDX);
//		%+E           PreMindMask:=wOR(PreMindMask,uEDX); Qf1(qPUSHR,qEDX,cTYP);
//		%+E           PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//		%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//		%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//		%+E           opr.rela.val:=opr.rela.val+AllignFac;
//		%+E           PresaveOprRegs(opr); Qf3(qSTORE,0,qECX,cTYP,opr);
//		%+E           opr.rela.val:=opr.rela.val+AllignFac;
//		%+E           Qf3(qSTORE,0,qEDX,cTYP,opr);
//		      otherwise
//		           if TOS.kind <> K_Address
//		           then
//		%-E             Qf3(qLOADA,0,qDI,cADR,opr);
//		%-E             opr.sbireg:=bOR(bAND(opr.sbireg,oSREG),rmDI);
//		%+E             Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg:=bEDI+iEDI;
//		                opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//		           endif;
//		%-E        if bAND(opr.sbireg,oSREG) <> oES
//		%-E        then segreg:=GetSreg(opr);
//		%-E             PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//		%-E             Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qES,cOBJ);
//		%-E        endif;
//		           GQfetch;
//		%-E        Qf2(qMOV,0,qSI,cSTP,qSP); Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//		%+E        Qf2(qMOV,0,qESI,cSTP,qESP);
//		%-E        Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//		%+E        Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//		           Qf2(qRSTRW,qRMOV,qCLD,cTYP,qREP);
//		      endcase;
//		end;
	}
	
	public String toString() {
		return "RUPDATE";
	}
	

}
