package bec.syntaxClass.instruction;

import bec.util.Scode;

public class INDEX extends Instruction {
	int instr;
	
	/**
	 * addressing_instruction ::= ::= index | indexv
	 */
	public INDEX(int instr) {
		this.instr = instr;
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr);
	}
	

}
