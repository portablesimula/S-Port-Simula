package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.value.MemAddr;

public class SVM_GOTO extends SVM_Instruction {
	public MemAddr destination;

	public SVM_GOTO(MemAddr destination) {
		this.opcode = SVM_Instruction.iGOTO;
		this.destination = destination;
	}
	
	@Override	
	public String toString() {
		return "GOTO     " + destination;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	protected SVM_GOTO(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iGOTO;
		this.destination = MemAddr.read(inpt);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
		destination.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_GOTO(inpt);
	}

}
