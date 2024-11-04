package bec.syntaxClass.instruction;

import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.DataType;
import bec.syntaxClass.programElement.AttributeDefinition;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

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
//		CTStack.checkTosRef();
//        InTag(%tag%); attr:=DISPL(tag.HI).elt(tag.LO); adr:=TOS;
		AttributeDefinition attr = (AttributeDefinition) Global.getMeaning(tag);
		Address adr = (Address) CTStack.TOS;
		adr.offset = adr.offset + attr.rela;
		adr.type = attr.quant.type.tag;
		adr.repdist = DataType.typeSize(adr.type);
		if(adr.atrState == Address.State.FromConst) {
			adr.atrState = Address.State.NotStacked;
			Util.IERR("NOT IMPL");
//             qPOPKill(AllignFac);
		}
		if(instr == Scode.S_SELECTV) Util.GQfetch();
//		CTStack.dumpStack();
//		Util.IERR("");
	}
	
	@Override
	public void printTree(final int indent) {
//		sLIST(indent, toString() + Scode.edTag(tag));
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr) + " " + Scode.edTag(tag);
	}
	

}
