package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.AddressItem;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

// POP RT-Stack'TOS --> MemAddr

//The count values on the top of the operand stack is popped off and stored at addr...
public class SVM_POP2MEM extends SVM_Instruction {
	RTAddress addr;
	int count;
	
	public SVM_POP2MEM(AddressItem itm, int count) {
		this.opcode = SVM_Instruction.iPOP2MEM;
		this.addr = new RTAddress(itm);
		this.count = count;
	}
	
	public SVM_POP2MEM(ObjectAddress addr, int count) {
		this.opcode = SVM_Instruction.iPOP2MEM;
		this.addr = new RTAddress(addr);
		this.count = count;
	}
	
	@Override
	public void execute() {
		Global.PSC.segment().dump("POP2MEM.execute: ");
//		ObjectAddress target = addr.objadr.ofset(addr.offset);
//		if(addr.objReg > 0) target.ofst += RTRegister.getIndex(addr.objReg);
//		if(addr.atrReg > 0) target.ofst += RTRegister.getIndex(addr.atrReg);
		ObjectAddress target = addr.toObjectAddress();
		target.ofst = target.ofst + count;
		for(int i=0;i<count;i++) {
			Value value = RTStack.pop();
			target.ofst--;
			target.store(value);
		}
		Global.PSC.ofst++;
		target.segment().dump("POP2MEM.execute: ");
//		Util.IERR("");
	}
	
	public String toString() {
		return "POP2MEM  " + addr + ", "+ count;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_POP2MEM(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPOP2MEM;
		this.addr = RTAddress.read(inpt);
		this.count = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		addr.write(oupt);
		oupt.writeShort(count);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_POP2MEM(inpt);
	}

}
