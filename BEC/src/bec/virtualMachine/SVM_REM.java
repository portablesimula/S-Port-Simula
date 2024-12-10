package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.value.Value;

/**
 * Remove two items on the Runtime-Stack and push the value (SOS / TOS)
 */
public class SVM_REM extends SVM_Instruction {
	Type type;

	public SVM_REM(Type type) {
		this.opcode = SVM_Instruction.iREM;
		this.type = type;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value sos = RTStack.pop();
		Value res = (tos == null)? null : tos.mult(sos);
		System.out.println("SVM_MULT: " + tos + " + " + sos + " = " + res);
		RTStack.push(type, res);
		Global.PSC.ofst++;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "MULT     " + type;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(SVM_Instruction.iREM);
		type.write(oupt);;
	}

	public static SVM_REM read(AttributeInputStream inpt) throws IOException {
		SVM_REM instr = new SVM_REM(Type.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}