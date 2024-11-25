package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Type;
import bec.virtualMachine.SVM_CONVERT;

public class CONVERT extends Instruction {
	Type toType;
	
	/**
	 * convert_instruction ::= convert simple_type
	 * 
	 * TOS must be of simple type, otherwise: error.
	 * 
	 * The TYPE of TOS is changed to the type specified in the instruction, this may imply code generation.
	 */
	public CONVERT() {
		toType = Type.ofScode();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		StackItem tos = CTStack.takeTOS();
//		if(tos instanceof Coonst) {
//			ConvConst(totype);
//		} else {
			Global.PSEG.emit(new SVM_CONVERT(toType), "");
//		}

		CTStack.pushTemp(toType, "CONVERT: ");

//		CTStack.dumpStack();
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "CONVERT " + toType;
	}
	

}
