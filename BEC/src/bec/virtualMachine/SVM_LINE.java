package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;

/**
 * 
 * 
 */
public class SVM_LINE extends SVM_Instruction {
	int type; // 0, DCL, STM
	int sourceLine;

	public SVM_LINE(int type, int sourceLine) {
		this.type = type;
		this.sourceLine = sourceLine;
	}
	
	@Override	
	public String toString() {
		return "LINE     " + sourceLine;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(iLINE);
		oupt.writeKind(type+1);
		oupt.writeShort(sourceLine);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		int type = inpt.readKind() - 1;
		int sourceline = inpt.readShort();
		return new SVM_LINE(type, sourceline);
	}

}
