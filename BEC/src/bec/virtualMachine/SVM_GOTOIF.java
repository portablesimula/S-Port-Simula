package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Relation;
import bec.value.MemAddr;

public class SVM_GOTOIF extends SVM_GOTO {
	Relation relation;
//	MemAddr destination;

	public SVM_GOTOIF(Relation relation, MemAddr destination) {
		super(destination);
		this.opcode = SVM_Instruction.iGOTOIF;
		this.relation = relation;
//		this.destination = destination;
	}
	
	@Override	
	public String toString() {
		return "GOTOIF   " + relation + " " + destination;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_GOTOIF(AttributeInputStream inpt) throws IOException {
		super(inpt);
		this.opcode = SVM_Instruction.iGOTOIF;
		this.relation = Relation.read(inpt);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
		destination.write(oupt);
		relation.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_GOTOIF(inpt);
	}

}
