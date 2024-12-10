package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.value.Value;

/**
 * 
 * Remove the top item on the Runtime-Stack and push the negative value
 */
public class SVM_NEG extends SVM_Instruction {
	Type type;

	public SVM_NEG(Type type) {
		this.opcode = SVM_Instruction.iNEG;
		this.type = type;
	}

	@Override
	public void execute() {
		Value tos = RTStack.pop();
		Value res = (tos == null)? null : tos.neg();
		System.out.println("SVM_NEG:  -" + tos + " = " + res);
		RTStack.push(type, res);
		Global.PSC.ofst++;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "NEG      " + type;
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
		SVM_NEG instr = new SVM_NEG(Type.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
