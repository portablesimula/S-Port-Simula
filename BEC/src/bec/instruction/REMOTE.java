package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.descriptor.Attribute;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.MemAddr;

/**
 * 
 */
public class REMOTE extends Instruction {
	int instr;
	int tag;
	
	public REMOTE(int instr) {
		this.instr = instr;
		parse();
	}

	/**
	 * addressing_instruction ::= remote attribute:tag | remotev attribute:tag
	 * 
	 * remote attr:tag
	 * force TOS value; check TOS type(OADDR);
	 * pop;
	 * push( REF, attr.TYPE, BASE = value(TOS), OFFSET = attr.OFFSET" );
	 * 
	 * This instruction uses one step of indirection. The value is considered to be the address of an
	 * object of the type 'REC' in which attr is an attribute. TOS is replaced by a descriptor of the
	 * designated component of that object. Note again that no qualification check is implied (neither
	 * could it be done).
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		tag = Scode.inTag();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkTosRef();
		CTStack.checkTosType(Type.T_OADDR); // CheckTosType(T_OADDR);
		AddressItem adr = (AddressItem) CTStack.TOS;
		Util.GQfetch("REMOTE-1 " + Scode.edTag(tag) + ": ");
		Attribute attr = (Attribute) Global.getMeaning(tag);
		CTStack.pop();
		MemAddr memAddr = new MemAddr(null,0); // a
		adr = new AddressItem(attr.type, attr.rela, memAddr);
        adr.objState = AddressItem.State.Calculated;
//        System.out.println("REMOTE.doCode: adr="+adr);
		CTStack.push(adr);
        if(instr == Scode.S_REMOTEV)
        	Util.GQfetch("REMOTE-2 " + Scode.edTag(tag) + ": ");
//		CTStack.dumpStack();
//		Global.PSEG.dump();
	}
	
	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr) + ' ' + Scode.edTag(tag);
	}
	

}
