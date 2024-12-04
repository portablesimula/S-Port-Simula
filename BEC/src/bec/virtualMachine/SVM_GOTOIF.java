package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Relation;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;

public class SVM_GOTOIF extends SVM_GOTO {
	Relation relation;
//	MemAddr destination;

	public SVM_GOTOIF(Relation relation, ProgramAddress destination) {
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
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		destination.write(oupt);
		relation.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_GOTOIF(inpt);
	}

}
