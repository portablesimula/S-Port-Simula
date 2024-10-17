package bec.syntaxClass.instruction;

import bec.util.Scode;

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
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr) + Scode.edTag(tag);
	}
	

}
