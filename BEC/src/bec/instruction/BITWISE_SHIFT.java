package bec.instruction;

import bec.util.Scode;

public class BITWISE_SHIFT extends Instruction {
	int instr;
	
	public BITWISE_SHIFT(int instr) {
		this.instr = instr;
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr);
	}
	

}
