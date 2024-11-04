package bec.syntaxClass.instruction;

import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.Coonst;
import bec.compileTimeStack.StackItem;
import bec.syntaxClass.value.IntegerValue;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class INDEX extends Instruction {
	int instr;
	
	/**
	 * addressing_instruction ::= ::= index | indexv
	 */
	public INDEX(int instr) {
		this.instr = instr;
	}

	@Override
	public void doCode() {
		CTStack.dumpStack();

		CTStack.checkTosInt();
		CTStack.checkSosRef();
//        adr:=TOS.suc; repdist:=adr.repdist;
		Address adr = (Address) CTStack.TOS.suc;
		int repdist = adr.repdist;
		if(repdist == 0) Util.IERR("PARSE.INDEX: Not info type");
//        if TOS.kind=K_Coonst
		StackItem tos = CTStack.TOS;
		if(tos instanceof Coonst itm) {
//        then itm:=TOS qua Coonst.itm;
			IntegerValue ival = (IntegerValue) itm.value;
			adr.offset = adr.offset + (repdist * ival.value);
			Util.GQpop();
//             if adr.AtrState=FromConst
//             then adr.AtrState:=NotStacked;
//                  qPOPKill(AllignFac);
//             endif;
//        else
		} else {
//%+E             if TOS.type <> T_WRD4 then GQconvert(T_WRD4) endif;
//%+E             GetTosAdjustedIn86(qEAX); Pop; AssertObjStacked;
//%+E             GQeMultc(repdist); -- EAX:=EAX*repdist
//%+E             if    adr.AtrState=FromConst then qPOPKill(4)
//%+E             elsif adr.AtrState=Calculated
//%+E             then Qf1(qPOPR,qEBX,cVAL);
//%+E                  Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
//%+E             endif;
//%+E             Qf1(qPUSHR,qEAX,cVAL); adr.AtrState:=Calculated;
			Util.IERR("NOT IMPL");
//        endif;
		}
		if(instr == Scode.S_INDEXV) Util.GQfetch();

//		Global.PSEG.dump();
//		CTStack.dumpStack();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr);
	}
	

}
