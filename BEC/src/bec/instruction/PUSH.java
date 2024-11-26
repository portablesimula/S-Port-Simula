package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.descriptor.ConstDescr;
import bec.descriptor.Descriptor;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public abstract class PUSH extends Instruction {
	int instr;
	int tag;
	
	/**
	 * stack_instruction ::= push obj:tag | pushv obj:tag
	 * 
	 * End-Condition: Scode'nextByte = First byte after the tag
	 */
	public static void ofScode(int instr) {
//		System.out.println("PUSH.doCode: tag="+Scode.edTag(tag)+"  "+tag);
		int tag = Scode.inTag();
		Descriptor x = Global.getMeaning(tag);
		if(x instanceof Variable var) {
//			System.out.println("PUSH.doCode: var="+var);
//			CTStack.push(new AddressItem(var.tag.val,0,var.address));
			CTStack.push(new AddressItem(var.type,0,var.address));
		} else if(x instanceof ConstDescr cns) {
//			CTStack.push(new AddressItem(cns.tag.val,0,cns.address));
			CTStack.push(new AddressItem(cns.type,0,cns.address));
		} else Util.IERR("");
//        if v.kind=K_Import
//        then TOS.size:= - wAllign(%TOS.size%) endif;
        if(instr == Scode.S_PUSHV) Util.GQfetch("PUSHV: ");
//      CTStack.dumpStack("PUSH: "+Scode.edInstr(instr));
	}

}
