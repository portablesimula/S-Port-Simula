package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

/**
 * Remove to items on the Runtime-Stack and push the value (SOS and TOS)
 */
public class SVM_ADD extends SVM_Instruction {
	int type;

	public SVM_ADD(int type) {
		this.type = type;
	}
	
	@Override	
	public String toString() {
		return "AND      " + Scode.edTag(type);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(SVM_Instruction.iADD);
		oupt.writeTag(type);
	}

	public static SVM_ADD read(AttributeInputStream inpt) throws IOException {
		int type = inpt.readTag();
		return new SVM_ADD(type);
	}

}
