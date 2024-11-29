package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Variable;
import bec.segment.DataSegment;
import bec.segment.RTStack;
import bec.util.Global;
import bec.util.Type;
import bec.value.MemAddr;
import bec.value.Value;

// The count values at addr... is pushed onto the operand stack.
public class SVM_PUSH extends SVM_Instruction {
	Type type;
	MemAddr addr;
	int count;
	
	public SVM_PUSH(Type type, MemAddr addr, int count) {
		this.opcode = SVM_Instruction.iPUSH;
		this.type = type;
		this.addr = addr;
		this.count = count;
	}
	
	public SVM_PUSH(Variable var) {
		this.opcode = SVM_Instruction.iPUSH;
		this.type = var.type;
		this.addr = var.address;
		this.count = var.repCount;
	}
	
	@Override
	public void execute() {
		DataSegment dseg = (DataSegment) addr.segment();
//		dseg.dump("SVM_PUSH: " + addr + ", count=" + count);
		int ofst = addr.ofst;
		for(int i=0;i<count;i++) {
			Value value = dseg.load(ofst++);
//			System.out.println("SVM_PUSH: " + value);
			RTStack.push(type, value);
		}
		Global.PSC.ofst++;
	}
	
	@Override
	public String toString() {
		return "PUSH     " + addr + ", " + count;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PUSH(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSH;
		this.type = Type.read(inpt);
		this.addr = MemAddr.read(inpt);
		this.count = inpt.readShort();
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
		type.write(oupt);
		addr.write(oupt);
		oupt.writeShort(count);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_PUSH(inpt);
	}

}
