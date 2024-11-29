package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RoutineDescr;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.RTStack;
import bec.util.Global;
import bec.util.Util;
import bec.value.AddressValue;
import bec.value.MemAddr;

public class SVM_CALL extends SVM_Instruction {
	MemAddr rutAddr;
	MemAddr prfAddr;
	
//	// NOT SAVED:
//	RoutineDescr rut;
//	ProfileDescr prf;
//
//	public SVM_CALL(RoutineDescr rut, ProfileDescr prf) {
//		this.opcode = SVM_Instruction.iCALL;
////		this.rut = rut;
////		this.prf = prf;
//		this.rutAddr = rut.adr;
//		this.prfAddr = new MemAddr(prf.DSEG, 0);
//	}

	public SVM_CALL(MemAddr rutAddr, MemAddr prfAddr) {
		this.opcode = SVM_Instruction.iCALL;
		this.rutAddr = rutAddr;
		this.prfAddr = prfAddr;
	}
	
	public static SVM_CALL ofTOS(MemAddr prfAddr) {
		return new SVM_CALL(null, prfAddr);
	}
	
	@Override	
	public void execute() {
//		System.out.println("SVM_CALL.execute: " + this);
//		prf.DSEG.dump("SVM_CALL.execute: ");
		MemAddr retur = Global.PSC;
		retur.ofst++;
//		System.out.println("SVM_CALL.execute: ReturnAddress=" + retur);
		DataSegment DSEG = (DataSegment) prfAddr.segment();
		DSEG.store(0, retur);
//		DSEG.dump("SVM_CALL.execute: ");
//		System.out.println("SVM_CALL.execute: rut=" + rut.adr);
		if(rutAddr == null) {
			AddressValue addrValue = (AddressValue) RTStack.pop();
			RoutineDescr rut = (RoutineDescr) addrValue.tag.getMeaning();
			ProgramSegment PSEG = (ProgramSegment) rut.adr.segment();
//			PSEG.dump("SVM_CALL.execute: ");
//			System.out.println("SVM_CALL_TOS.execute: PSC=" + rut.adr);
			
			Global.PSC = rut.adr;
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
		this.prfAddr = MemAddr.read(inpt);
		boolean present = inpt.readBoolean();
		if(present)	this.rutAddr = MemAddr.read(inpt);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
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
