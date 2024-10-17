package bec.syntaxClass.instruction;

import java.util.Vector;

import bec.util.Scode;
import bec.util.Util;

public class CallInstruction extends Instruction {
	int n; // Kind
	int profileTag;
	int routineTag;
	Vector<ParameterEval> argumentEvaluation;
	Vector<Instruction> CALL_TOS_Instructions;
	
	public CallInstruction(int n) {
		this.n = n;
		argumentEvaluation = new Vector<ParameterEval>();
		parse();
	}

	/**
	 * call_instruction
	 * 		::= connect_profile <parameter_eval>*
	 * 				connect_routine
	 * 
	 * 		connect_profile
	 * 			::= precall profile:tag
	 * 			::= asscall profile:tag
	 * 			::= repcall n:byte profile:tag
	 * 
	 * 		connect_routine ::= call body:tag | <instruction>+ call-tos
	 * 
	 * 		parameter_eval
	 * 			::= <instruction>+ asspar
	 * 			::= <instruction>+ assrep n:byte
	 */
	public void parse() {
		profileTag = Scode.inTag();
		Scode.inputInstr();
		
		if(Scode.inputTrace > 3) System.out.println("CallInstruction: n="+n+", Curinstr="+Scode.edInstr(Scode.curinstr));
		
		LOOP:while(Scode.curinstr != Scode.S_CALL) {
			Vector<Instruction> instructions = Instruction.inInstructionSet();
			if(instructions.isEmpty()) break LOOP;
			
			if(Scode.curinstr == Scode.S_ASSPAR) {
				Scode.inputInstr();
				argumentEvaluation.add(new ParameterEval(instructions,-1));
			}
			else if(Scode.curinstr == Scode.S_ASSREP) {
				int nRep = Scode.inByte();
				Scode.inputInstr();
				argumentEvaluation.add(new ParameterEval(instructions,nRep));
//				System.out.println("CallInstruction: ASSREP: NextInstr="+Scode.edInstr(Scode.nextByte()));
			}
			else if(Scode.curinstr == Scode.S_CALL_TOS) {
//				Scode.inputInstr();
				CALL_TOS_Instructions = instructions;
				break LOOP;
			}
			else Util.IERR("Syntax error in call Instruction");
		}
	    //  ---------  Call Routine  ---------
		if(CALL_TOS_Instructions == null) routineTag = Scode.inTag();
		
		if(Scode.inputTrace > 3) {
			System.out.println("-------------------------------------------------- BEGIN PRINT CALL Instruction");
			printTree(2);
			System.out.println("-------------------------------------------------- ENDOF PRINT CALL Instruction");
		}
	}


	
	@Override
	public void printTree(final int indent) {
//		System.out.println(edLead(indent) + this);
		String lead = null;
		switch(n) {
			case 0:  lead = "PRECALL "; break;
			case 1:  lead = "ASSCALL "; break;
			default: lead = "REPCALL " + n + " "; break;
		}
		sLIST(indent, lead + Scode.edTag(profileTag));
		for(ParameterEval p:argumentEvaluation) p.printTree(indent + 1);
		if(CALL_TOS_Instructions != null) {
			for(Instruction instr:CALL_TOS_Instructions)
				sLIST(indent + 1, instr.toString());
			sLIST(indent, "CALL-TOS");
//			Util.IERR("SJEKK DETTE");
		} else {
			sLIST(indent, "CALL " + Scode.edTag(routineTag));
		}
	}
	
	public String toString() {
		String lead = null;
		switch(n) {
			case 0:  lead = "PRECALL "; break;
			case 1:  lead = "ASSCALL "; break;
			default: lead = "REPCALL " + n + " "; break;
		}
		return lead + " ...";
	}
	

}
