package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

public class SVM_NOT_IMPL extends SVM_Instruction {

	public SVM_NOT_IMPL() {
		this.opcode = SVM_Instruction.iNOT_IMPL;
	}
	
	@Override	
	public String toString() {
		return "NOT_IMPL ";
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_NOT_IMPL(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iNOT_IMPL;
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_NOT_IMPL(inpt);
	}

}
