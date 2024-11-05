package bec.syntaxClass.programElement.routine;

import java.util.Vector;

import bec.compileTimeStack.CTStack;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.syntaxClass.instruction.Instruction;
import bec.syntaxClass.programElement.ProgramElement;
import bec.syntaxClass.programElement.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_RETURN;

public class ROUTINE extends ProgramElement {
	int bodyTag;
	int profileTag;
	Vector<Variable> localQuantities;
	Vector<Instruction> instructions;
	
	public DataSegment DSEG;
	public ProgramSegment PSEG;
	
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
		
		if(Global.SCODE_INPUT_TRACE) printTree(2);
//		compile(); // TODO: TESTING
	}
	
	
	@Override
	public void doCode() {
		String id = Global.moduleID + '_' + Scode.TAGTABLE[profileTag];
		DSEG = new DataSegment("DSEG_" + id, Segment.SEG_DATA);
		PSEG = new ProgramSegment("PSEG_" + id, Segment.SEG_CODE);
		ProgramSegment prevPSEG = Global.PSEG; Global.PSEG = PSEG;
		System.out.println("\n-------------------------------------------------- BEGIN PRINT ROUTINE Definition");
		printTree(2);
		System.out.println("-------------------------------------------------- ENDOF PRINT ROUTINE Definition");
		PROFILE prf = (PROFILE) Global.getMeaning(profileTag);
		if(prf.exit != null)
			 prf.exit.emit(DSEG, "EXIT");
		else DSEG.emit(null, "RETUR");

		for(Variable var:prf.imports)
			var.emit(DSEG, "IMPORT");
		if(prf.export != null) prf.export.emit(DSEG, "EXPORT");
		for(Variable var:localQuantities) {
			var.emit(DSEG, "LOCAL");
		}
		for(Instruction instr:instructions) {
//			System.out.println("=========================================================================="+instr);
			instr.doCode();
		}
//		PROFILE prf = (PROFILE) Global.getMeaning(profileTag);
		Global.PSEG.emit(new SVM_RETURN(prf), "");
		CTStack.checkStackEmpty();

		DSEG.dump("ROUTINE.doCode: ");
		PSEG.dump("ROUTINE.doCode: ");
		Global.PSEG = prevPSEG;
//		Util.IERR("");
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
		for(Variable quant:localQuantities)
			sLIST(indent+1, quant.toString());
//		System.out.println("instructions "+instructions.size());
		for(Instruction instr:instructions)
			sLIST(indent+1, instr.toString());
	}
	
	public String toString() {
		return "ROUTINE " + Scode.edTag(bodyTag) + " " + Scode.edTag(profileTag);
	}

}
