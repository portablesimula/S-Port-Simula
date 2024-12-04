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
 * Remove to items on the Runtime-Stack and push the value SOS or TOS
 */
public class SVM_OR extends SVM_Instruction {
	Type type;

	public SVM_OR(Type type) {
		this.opcode = SVM_Instruction.iOR;
		this.type = type;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? sos : tos.or(sos);
//		System.out.println("SVM_ADD: " + tos + " + " + sos + " = " + res);
		RTStack.push(type, res);
		Global.PSC.ofst++;
//		Util.IERR(""+res);
	}
	
	@Override	
	public String toString() {
		return "OR       " + type;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		type.write(oupt);;
	}

	public static SVM_OR read(AttributeInputStream inpt) throws IOException {
		SVM_OR instr = new SVM_OR(Type.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
