package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;
import bec.util.Util;

public class Value {
	public int type;

	/**
	 * Utility print method.
	 * 
	 * @param indent number of spaces leading the lines
	 */
	public void print(final String indent) {
		Util.IERR("Method printTree need a redefinition in "+this.getClass().getSimpleName());
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

	public static Value read(AttributeInputStream inpt) throws IOException {
		int kind = inpt.readKind();
//		System.out.println("Value.read: kind="+Scode.edInstr(kind));
		switch(kind) {
			case Scode.S_NULL:		return null;
			case Scode.S_TRUE:		return new BooleanValue(true);
			case Scode.S_FALSE:		return new BooleanValue(false);
			case Scode.S_C_INT:		return IntegerValue.read(inpt);
			case Scode.S_C_REAL:	return RealValue.read(inpt);
			case Scode.S_C_LREAL:	return LongRealValue.read(inpt);
			case Scode.S_C_CHAR:	return CharacterValue.read(inpt);
			case Scode.S_C_SIZE:	return SizeValue.read(inpt);
			case Scode.S_TEXT:		return TextValue.read(inpt);
			case Scode.S_C_RECORD:	return RecordValue.read(inpt);
//			case Scode.S_C_OADDR:	return ObjectAddress.read(inpt);
//			case Scode.S_C_AADDR:	return AttributeAddress.read(inpt);
//			case Scode.S_C_GADDR:	return GeneralAddress.read(inpt);
//			case Scode.S_C_PADDR:	return ProgramAddress.read(inpt);
//			case Scode.S_C_RADDR:	return RoutineAddress.read(inpt);
//			case Scode.S_C_DOT:		return DotAddress.read(inpt);
			default: Util.IERR("MISSING: " + Scode.edInstr(kind));
		}
		Util.IERR("Method 'readObject' needs a redefiniton");
		return(null);
	}


}
