package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.segment.RTStack;
import bec.util.Global;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;

public class SVM_CALL extends SVM_Instruction {
	ProgramAddress rutAddr;
	ObjectAddress prfAddr;

	public SVM_CALL(ProgramAddress rutAddr, ObjectAddress prfAddr) {
		this.opcode = SVM_Instruction.iCALL;
		this.rutAddr = rutAddr;
		this.prfAddr = prfAddr;
	}
	
	public static SVM_CALL ofTOS(ObjectAddress prfAddr) {
		return new SVM_CALL(null, prfAddr);
	}
	
	@Override	
	public void execute() {
		ProgramAddress retur = Global.PSC;
		retur.ofst++;
		DataSegment DSEG = (DataSegment) prfAddr.segment();
		DSEG.store(0, retur);
		if(rutAddr == null) {
			// CALL-TOS
			Global.PSC = (ProgramAddress) RTStack.pop();;
//			Util.IERR("");			
		} else Global.PSC = rutAddr;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "CALL     " + rutAddr + " Profile=" + prfAddr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_CALL(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iCALL;
		this.prfAddr = (ObjectAddress) Value.read(inpt);
		boolean present = inpt.readBoolean();
		if(present)	this.rutAddr = (ProgramAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		prfAddr.write(oupt);
		if(rutAddr != null) {
			oupt.writeBoolean(true);
			rutAddr.write(oupt);
		} else oupt.writeBoolean(false);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_CALL(inpt);
	}


}
