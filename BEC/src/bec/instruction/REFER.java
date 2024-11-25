package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.MemAddr;

public class REFER extends Instruction {
	Type type;
	
	/**
	 * addressing_instruction ::= refer resolved_type
	 * 
	 * force TOS value; check TOS type(GADDR);
	 * TOS.MODE := REF; TOS.TYPE := type;
	 * 
	 * TOS is modified to describe a quantity of the given type, at the address described by TOS.
	 */
	public REFER() {
		type = Type.ofScode();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkTosType(Type.T_GADDR);
		MemAddr a = new MemAddr(null, 0);
		AddressItem adr = new AddressItem(type, 0, a);
        Util.GQfetch("REFER " + Scode.edTag(type.tag) + ": ");
		adr.objState = adr.atrState = AddressItem.State.Calculated;
        CTStack.pop(); 
        CTStack.push(adr);
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "REFER " + type;
	}
	

}
