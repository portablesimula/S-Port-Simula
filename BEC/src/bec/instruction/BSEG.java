package bec.instruction;

import java.util.Vector;

import bec.util.Scode;
import bec.util.Util;

public class BSEG extends Instruction {
	Vector<Instruction> Instructions;
	
	public BSEG() {
		parse();
	}

	/**
	 * segment_instruction ::= bseg <program_element>* eseg
	 * 
	 * End-Condition: Scode'nextByte = First byte after ESEG
	 */
	public void parse() {
		// BSEGInstruction
		Instructions = new Vector<Instruction>();
		while(Scode.nextByte() != Scode.S_ESEG) {
			Instruction elt = (Instruction) Instruction.inInstruction();
			if(elt == null) Util.IERR("Inconsistent instruction in BSE-ESEG");
			Instructions.add(elt);
		}
		Scode.inputInstr();
		
//		if(Scode.inputTrace > 3) print();
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + "BSEG");
		for(Instruction elt:Instructions) {
			elt.print(indent + "   ");
		}
		System.out.println(indent + "ESEG");
	}
	
	public void print() {
		System.out.println("BSEG");
		for(Instruction elt:Instructions) {
			System.out.println("   " + elt);
		}
		System.out.println("ESEG");
	}
	
	public String toString() {
		return "BSEG";
	}

}
