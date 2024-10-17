package bec.syntaxClass.programElement;

import bec.syntaxClass.SyntaxClass;
import bec.syntaxClass.instruction.Instruction;
import bec.syntaxClass.programElement.routine.PROFILE;
import bec.syntaxClass.programElement.routine.ROUTINE;
import bec.util.Scode;

public class ProgramElement extends SyntaxClass {

	public static void programElements() {
		while(inProgramElement() != null) ;
	}

	
	public static ProgramElement inProgramElement() {
		Scode.inputInstr();
		Instruction elt = Instruction.inInstruction();
		if(elt != null) return elt;
		switch(Scode.curinstr) {
			case Scode.S_LABELSPEC: return new LABELSPEC();
			case Scode.S_LABEL:     return new LABEL();
			case Scode.S_PROFILE:   return new PROFILE();
			case Scode.S_ROUTINE:   return new ROUTINE();
			case Scode.S_IF:        return new IF();
			case Scode.S_SKIPIF:    return new SKIPIF();
			case Scode.S_SAVE:      return new SAVE();
			case Scode.S_INSERT:    return new INSERT(false);
			case Scode.S_SYSINSERT: return new INSERT(true);
			default: return null;
		}
	}

}
