package bec.syntaxClass.instruction;

import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.syntaxClass.programElement.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class PUSH extends Instruction {
	int instr;
	int tag;
	
	public PUSH(int instr) {
		this.instr = instr;
		parse();
	}

	/**
	 * stack_instruction ::= push obj:tag | pushv obj:tag
	 * 
	 * End-Condition: Scode'nextByte = First byte after the tag
	 */
	public void parse() {
		tag = Scode.inTag();
	}
	
	@Override
	public void doCode() {
		System.out.println("PUSH.doCode: tag="+Scode.edTag(tag)+"  "+tag);
		Variable var = (Variable) Global.getMeaning(tag);
		System.out.println("PUSH.doCode: var="+var);
		CTStack.push(new Address(var.quant.type.tag,0,var.address));
//        if v.kind=K_Parameter
//        then TOS.repdist:= - wAllign(%TOS.repdist%) endif;
        if(instr == Scode.S_PUSHV) Util.GQfetch();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr) + " " + Scode.edTag(tag);
	}
	

}
