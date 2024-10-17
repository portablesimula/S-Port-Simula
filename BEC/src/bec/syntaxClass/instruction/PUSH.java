package bec.syntaxClass.instruction;

import bec.util.Scode;

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
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr) + " " + Scode.edTag(tag);
	}
	

}
