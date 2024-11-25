package bec.util;

import java.util.Vector;

import bec.instruction.Instruction;

public class ParameterEval { // extends SyntaxClass {
	Vector<Instruction> instructions;
	int nRep;
	
	/**
	 * 	parameter eval
	 * 		::= <instruction>+ asspar
	 * 		::= <instruction>+ assrep n:byte
	 */
	public ParameterEval(Vector<Instruction> instructions,int nRep) {
		this.instructions = instructions;
		this.nRep = nRep;
//		System.out.println("NEW ParameterEvaluation: " + this);
//		print();
	}
	
//	@Override
	public void doCode() {
		for(Instruction instr:instructions)
			instr.doCode();
	}

	public void print() {
		for(Instruction instr:instructions)
			System.out.println("   " + instr);
		System.out.println((nRep < 0) ? "   ASSPAR" : "   ASSREP " + nRep);
	}

//	@Override
	public void printTree(final String indent) {
		
		for(Instruction instr:instructions)
			instr.print(indent);
//		// Modify Scode.STACK
//		int nPop = (nRep < 0) ? 1 : nRep;
//		System.out.println("ParameterEval.printTree: nPop="+nPop);
//		for(int i=0;i<nPop;i++) Scode.STACK.pop();
		
		System.out.println(indent + ((nRep < 0) ? "ASSPAR" : "ASSREP " + nRep));
		
	}
		
	public String toString() {
		return "Instruction ... " + ((nRep < 0) ? "ASSPAR " : "ASSREP " + nRep);
	}
	

}
