package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.ProgramAddress;
import bec.value.Value;

public class SVM_GOTO extends SVM_Instruction {
	public ProgramAddress destination;

	public SVM_GOTO(ProgramAddress destination) {
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
		this.destination = (ProgramAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		destination.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_GOTO(inpt);
	}

}
