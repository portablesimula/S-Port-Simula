package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.RTStack;
import bec.util.Global;
import bec.util.Type;
import bec.value.Value;

/**
 * Remove two items on the Runtime-Stack and push the value (SOS + TOS)
 */
public class SVM_ADD extends SVM_Instruction {
	Type type;

	public SVM_ADD(Type type) {
		this.opcode = SVM_Instruction.iADD;
		this.type = type;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? sos : tos.add(sos);
//		System.out.println("SVM_ADD: " + tos + " + " + sos + " = " + res);
		RTStack.push(type, res);
		Global.PSC.ofst++;
//		Util.IERR(""+res);
	}

	@Override	
	public String toString() {
		return "ADD      " + type;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		type.write(oupt);;
	}

	public static SVM_ADD read(AttributeInputStream inpt) throws IOException {
		SVM_ADD instr = new SVM_ADD(Type.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
