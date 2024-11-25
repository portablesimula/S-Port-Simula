package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.descriptor.Attribute;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT_IMPL;

/**
 * select attr:tag
 * 
 * check TOS ref;
 * TOS.TYPE := attr.TYPE;
 * "TOS.OFFSET := TOS.OFFSET ++ attr.OFFSET";
 * 
 * (note that the BASE component of TOS is unchanged)
 * The area described by TOS is considered to be holding a record of the type, say 'REC', in which
 * the instruction argument attr is an attribute. TOS is modified to describe the designated
 * component of that record. Note that no qualification check is implied, i.e. TOS.TYPE may be
 * different from 'REC'.
 */
public class SELECT extends Instruction {
	int instr;
	int tag;
	
	public SELECT(int instr) {
		this.instr = instr;
		parse();
	}

	/**
	 * addressing_instruction ::= select attribute:tag | selectv attribute:tag
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		tag = Scode.inTag();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkTosRef();
//		AttributeDefinition attr = (AttributeDefinition) Global.getMeaning(tag);
		Attribute attr = (Attribute) Global.getMeaning(tag);
		CTStack.TOS.type = attr.type;
		AddressItem adr = (AddressItem) CTStack.TOS;
		adr.offset = adr.offset + attr.rela;
		adr.type = attr.type;
//		adr.size = DataType.typeSize(adr.type);
		adr.size = attr.size;
		if(adr.atrState == AddressItem.State.FromConst) {
			adr.atrState = AddressItem.State.NotStacked;
			Global.PSEG.emit(new SVM_NOT_IMPL(), ""+this);
//             qPOPKill(AllignFac);
		}
		if(instr == Scode.S_SELECTV) Util.GQfetch("SELECTV " + Scode.edTag(tag) + ": ");
//		CTStack.dumpStack();
//		Util.IERR("");
	}
	
	@Override
	public void print(final String indent) {
//		System.out.println(indent + toString() + Scode.edTag(tag));
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr) + " " + Scode.edTag(tag);
	}
	

}
