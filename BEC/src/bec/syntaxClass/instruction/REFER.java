package bec.syntaxClass.instruction;

import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.segment.MemAddr;
import bec.util.Global;
import bec.util.ResolvedType;
import bec.util.Scode;
import bec.util.Util;

public class REFER extends Instruction {
	ResolvedType type;
	
	/**
	 * addressing_instruction ::= refer resolved_type
	 */
	public REFER() {
		type = new ResolvedType();
	}

	@Override
	public void doCode() {
		CTStack.dumpStack();

//        type:=intype;
		CTStack.checkTosType(Scode.TAG_GADDR);
//        a.kind:=reladr; a.rela.val:=0; a.segmid.val:=0;
//%-E        a.sbireg:=0;
//%+E        a.sibreg:=NoIBREG;
		MemAddr a = new MemAddr(null, 0);
//        adr:=NewAddress(type,0,a);
		Address adr = new Address(type.tag, 0, a);
        Util.GQfetch();
		adr.objState = adr.atrState = Address.State.Calculated;
        CTStack.pop(); 
        CTStack.push(adr);

//		CTStack.dumpStack();
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "REFER " + type;
	}
	

}
