package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Type;

/**
 * 
 * Remove the top item on the Runtime-Stack and push the NOT value
 */
public class SVM_NOT extends SVM_Instruction {
	Type type;

	public SVM_NOT(Type type) {
		this.opcode = SVM_Instruction.iNOT;
		this.type = type;
	}
	
	@Override	
	public String toString() {
		return "NOT      " + type;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_NOT(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iNOT;
		Type.read(inpt);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
		type.write(oupt);;
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_NOT(inpt);
	}

}
