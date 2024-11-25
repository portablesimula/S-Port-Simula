package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;

public class SVM_NOOP extends SVM_Instruction {

	// No Operation
	public SVM_NOOP() {
		this.opcode = SVM_Instruction.iNOOP;
	}
	
	@Override	
	public String toString() {
		return "NOOP ";
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_NOOP(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iNOOP;
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_NOOP(inpt);
	}

}
