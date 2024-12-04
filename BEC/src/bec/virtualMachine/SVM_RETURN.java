package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;

public class SVM_RETURN extends SVM_Instruction {
	public ObjectAddress returAddr;

	public SVM_RETURN(ObjectAddress returAddr) {
		this.opcode = SVM_Instruction.iRETURN;
		this.returAddr = returAddr;
	}
	
	@Override	
	public String toString() {
		return "RETURN   " + returAddr;
	}
	
	@Override	
	public void execute() {
//		returAddr.segment().dump("SVM_RETURN.execute: ");
//		System.out.println("SVM_RETURN.execute: returAddr=" + returAddr);
		
		DataSegment DSEG = (DataSegment) returAddr.segment();
//		DSEG.dump("SVM_RETURN.execute: ");
		ProgramAddress padr = (ProgramAddress) DSEG.load(returAddr.ofst);
//		System.out.println("SVM_RETURN.execute: padr=" + padr);
		Global.PSC = padr;
//		Util.IERR("");
	}

	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_RETURN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iRETURN;
		this.returAddr = (ObjectAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		returAddr.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RETURN(inpt);
	}

}
