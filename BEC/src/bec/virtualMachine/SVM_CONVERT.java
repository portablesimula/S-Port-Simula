package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Type;

public class SVM_CONVERT extends SVM_Instruction {
	Type toType;
	
	public SVM_CONVERT(Type toType) {
		this.opcode = SVM_Instruction.iCONVERT;
		this.toType = toType;
	}
	
	public String toString() {
		return "CONVERT  " + toType;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
		toType.write(oupt);;
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_CONVERT(Type.read(inpt));
	}

}
