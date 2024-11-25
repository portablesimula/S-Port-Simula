package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.RTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;

/**
 * Remove to items on the Runtime-Stack and push the value (SOS / TOS)
 */
public class SVM_DIV extends SVM_Instruction {
	Type type;

	public SVM_DIV(Type type) {
		this.opcode = SVM_Instruction.iDIV;
		this.type = type;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		if(tos == null) Util.IERR("DIV by zero: " + sos + " / 0");
		Value res = (tos == null)? null : tos.div(sos);
		System.out.println("SVM_DIV: " + tos + " / " + sos + " = " + res);
		RTStack.push(type, res);
		Global.PSC.ofst++;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "DIV      " + type;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
		type.write(oupt);;
	}

	public static SVM_DIV read(AttributeInputStream inpt) throws IOException {
		return new SVM_DIV(Type.read(inpt));
	}

}
