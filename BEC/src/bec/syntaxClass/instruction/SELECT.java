package bec.syntaxClass.instruction;

import bec.util.Scode;

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
	public void printTree(final int indent) {
		sLIST(indent, toString() + Scode.edTag(tag));
	}
	
	public String toString() {
		return Scode.edInstr(instr) + " " + Scode.edTag(tag);
	}
	

}
