package bec.syntaxClass.programElement.routine;

import java.util.Vector;

import bec.syntaxClass.instruction.Instruction;
import bec.syntaxClass.programElement.ProgramElement;
import bec.syntaxClass.programElement.Variable;
import bec.util.Scode;
import bec.util.Util;

public class ROUTINE extends ProgramElement {
	int bodyTag;
	int profileTag;
	Vector<Variable> localQuantities;
	Vector<Instruction> instructions;
	
	public ROUTINE() {
		parse();
	}

	/**
	 * 	routine_definition
	 * 		::= routine body:spectag profile:tag
	 * 				<local_quantity>* <instruction>*
	 * 			endroutine
	 */
	public void parse() {
		localQuantities = new Vector<Variable>();
		instructions = new Vector<Instruction>();
		bodyTag = Scode.inTag();
		profileTag = Scode.inTag();
		while(Scode.accept(Scode.S_LOCAL)) localQuantities.add(new Variable(Scode.S_LOCAL));
		Scode.inputInstr();
		instructions = Instruction.inInstructionSet();
		if(Scode.curinstr != Scode.S_ENDROUTINE)
			Util.IERR("Missing ENDROUTINE - Got " + Scode.edInstr(Scode.curinstr));
		
		if(Scode.inputTrace > 3) printTree(2);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
		for(Variable quant:localQuantities)
			sLIST(indent+1, quant.toString());
		System.out.println("instructions "+instructions.size());
		for(Instruction instr:instructions)
			sLIST(indent+1, instr.toString());
	}
	
	public String toString() {
		return "ROUTINE " + Scode.edTag(bodyTag) + " " + Scode.edTag(profileTag);
	}

}