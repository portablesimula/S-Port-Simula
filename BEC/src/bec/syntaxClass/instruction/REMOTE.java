package bec.syntaxClass.instruction;

import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.segment.MemAddr;
import bec.syntaxClass.programElement.AttributeDefinition;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class REMOTE extends Instruction {
	int instr;
	int tag;
	
	public REMOTE(int instr) {
		this.instr = instr;
		parse();
	}

	/**
	 * addressing_instruction ::= remote attribute:tag | remotev attribute:tag
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		tag = Scode.inTag();
	}

	@Override
	public void doCode() {
		CTStack.dumpStack();
		CTStack.checkTosRef();
		CTStack.checkTosType(Scode.TAG_OADDR); // CheckTosType(T_OADDR);
		Address adr = (Address) CTStack.TOS;
		Util.GQfetch();
//		InTag(%tag%); attr:=DISPL(tag.HI).elt(tag.LO);
		AttributeDefinition attr = (AttributeDefinition) Global.getMeaning(tag);
		CTStack.pop();
		MemAddr memAddr = new MemAddr(null,0); // a
//        a.kind:=reladr; a.rela.val:=0; a.segmid.val:=0;
//%-E        a.sbireg:=0;
//%+E        a.sibreg:=NoIBREG;
//        adr:=NewAddress(attr.type,attr.rela,a);
		
		adr = new Address(attr.quant.type.tag, attr.rela, memAddr);
        adr.objState = Address.State.Calculated;
        System.out.println("REMOTE.doCode: adr="+adr);
		CTStack.push(adr);
        if(instr == Scode.S_REMOTEV) Util.GQfetch();
		CTStack.dumpStack();
		Global.PSEG.dump();
//		Util.IERR("");
	}
	
	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr) + ' ' + Scode.edTag(tag);
	}
	

}
