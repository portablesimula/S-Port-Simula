package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.ProfileDescr;
import bec.util.Util;

public class SVM_RETURN extends SVM_Instruction {
	ProfileDescr prf;

	public SVM_RETURN(ProfileDescr prf2) {
		this.opcode = SVM_Instruction.iRETURN;
		this.prf = prf2;
	}
	
	@Override	
	public String toString() {
		return "RETURN   " + prf;
	}

	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	public SVM_RETURN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iRETURN;
		this.prf = ProfileDescr.read(inpt);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
		prf.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RETURN(inpt);
	}

}
