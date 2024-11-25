package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.value.MemAddr;

public class SVM_RETURN extends SVM_Instruction {
	public MemAddr returAddr;

	public SVM_RETURN(MemAddr returAddr) {
		this.opcode = SVM_Instruction.iRETURN;
		this.returAddr = returAddr;
	}
	
	@Override	
	public String toString() {
		return "RETURN   " + returAddr;
	}
	
	@Override	
	public void execute() {
//		System.out.println("SVM_CALL.execute: ReturnAddress=" + retur);
//		prf.DSEG.store(0, retur);
//		prf.DSEG.dump("SVM_CALL.execute: ");
		System.out.println("SVM_RETURN.execute: returAddr=" + returAddr);
		DataSegment DSEG = (DataSegment) returAddr.segment();
		DSEG.dump("SVM_RETURN.execute: ");
		MemAddr padr = (MemAddr) DSEG.load(returAddr.ofst);
		System.out.println("SVM_RETURN.execute: padr=" + padr);
		Global.PSC = padr;
//		Util.IERR("");
	}

	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_RETURN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iRETURN;
		this.returAddr = MemAddr.read(inpt);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
		returAddr.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RETURN(inpt);
	}

}
