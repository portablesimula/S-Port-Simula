package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.RTStack;
import bec.util.Global;
import bec.util.Type;
import bec.value.Value;

/**
 * SOS and TOS are replaced by a description of the value of the application of the operator. The
 * type of the result is the same as the type of the operands. SOS is always the left operand, i.e.
 * SOS op TOS. All arithmetic on subranges of INT should be performed in full integer arithmetic.
 * 
 * Remove two items on the Runtime-Stack and push the value SOS - TOS
 */
public class SVM_SUB extends SVM_Instruction {
	Type type;

	public SVM_SUB(Type type) {
		this.opcode = SVM_Instruction.iSUB;
		this.type = type;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		System.out.println("SVM_SUB: " + sos + " - " + tos);
		Value res = (sos == null)? tos.neg() : sos.sub(tos);
		System.out.println("SVM_SUB: " + sos + " - " + tos + " = " + res);
		RTStack.push(type, res);
		Global.PSC.ofst++;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "SUB      " + type;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		type.write(oupt);;
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_SUB instr = new SVM_SUB(Type.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
