package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.RTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.value.Value;

//The value is pushed onto the operand stack.
public class SVM_PUSHC extends SVM_Instruction {
	Type type;
	Value value;
	
	public SVM_PUSHC(Type type, Value value) {
		this.opcode = SVM_Instruction.iPUSHC;
		this.type = type;
		this.value = value;
	}
	
	@Override
	public void execute() {
		RTStack.push(type, value);
		Global.PSC.ofst++;
	}
	
	@Override
	public String toString() {
		return "PUSHC    " + Scode.edTag(type.tag) + " " + value;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PUSHC(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSHC;
		this.type = Type.read(inpt);
		boolean present = inpt.readBoolean();
		if(present)	this.value = Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		type.write(oupt);
		if(value != null) {
			oupt.writeBoolean(true);
			value.write(oupt);
		} else oupt.writeBoolean(false);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_PUSHC(inpt);
	}

}
