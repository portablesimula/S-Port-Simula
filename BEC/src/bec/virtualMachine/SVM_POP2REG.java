package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.value.IntegerValue;
import bec.value.Value;

// POP RT-Stack'TOS --> Register 
// The value on the top of the operand stack is popped off and stored in reg.
public class SVM_POP2REG extends SVM_Instruction {
	int reg;
	
	public SVM_POP2REG(int reg) {
		this.opcode = SVM_Instruction.iPOP2REG;
		this.reg = reg;
	}
	
	@Override
	public void execute() {
		int ival = IntegerValue.intValue((IntegerValue) RTStack.pop());
		RTRegister.putIndex(reg, ival);
		Global.PSC.ofst++;
//		target.segment().dump("POP2MEM.execute: ");
//		Util.IERR("");
	}
	
	public String toString() {
		return "POP2REG  " + RTRegister.edReg(reg);
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_POP2REG(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPOP2REG;
		this.reg = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		oupt.writeShort(reg);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_POP2REG(inpt);
	}

}
