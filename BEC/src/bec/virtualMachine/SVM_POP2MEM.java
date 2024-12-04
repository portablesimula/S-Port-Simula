package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.RTStack;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

// POP RT-Stack'TOS --> MemAddr

//The count values on the top of the operand stack is popped off and stored at addr...
public class SVM_POP2MEM extends SVM_Instruction {
	ObjectAddress addr;
	int count;
	
	public SVM_POP2MEM(ObjectAddress addr, int count) {
		this.opcode = SVM_Instruction.iPOP2MEM;
		this.addr = addr;
		this.count = count;
	}
	
	@Override
	public void execute() {
		ObjectAddress target = addr.ofset(0);
		target.ofst = target.ofst + count;
		for(int i=0;i<count;i++) {
			Value value = RTStack.pop();
			target.ofst--;
			target.store(value);
		}
		Global.PSC.ofst++;
//		target.segment().dump("POP2MEM.execute: ");
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
		this.addr = (ObjectAddress) Value.read(inpt);
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
