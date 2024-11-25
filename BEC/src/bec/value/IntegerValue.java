package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class IntegerValue extends Value {
	public int value;
	
	public IntegerValue(int value) {
		this.type = Type.T_INT;
		this.value = value;
		if(value == 0) Util.IERR("");
	}

	/**
	 * integer_value   ::= c-int integer_literal:string
	 */
	public IntegerValue() {
		this.type = Type.T_INT;
		this.value = Integer.valueOf(Scode.inString());
	}

//	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}

	@Override
	public Value neg() {
		return new IntegerValue(- value);
	}

	@Override
	public Value add(Value other) {
		if(other == null) return this;
		IntegerValue val2 = (IntegerValue) other;
		int res = value + val2.value;
		if(res == 0) return null;
		return new IntegerValue(res);
	}

	@Override
	public Value sub(Value other) {
		int res = 0;
		if(other != null) {
			IntegerValue val2 = (IntegerValue) other;
			res = this.value - val2.value;
		} else res = this.value;
		System.out.println("IntegerValue.sub: " + this.value + " - " + other + " = " + res);
		if(res == 0) return null;
		return new IntegerValue(res);
	}

	@Override
	public Value mult(Value other) {
		if(other == null) return null;
		IntegerValue val2 = (IntegerValue) other;
		int res = value * val2.value;
		if(res == 0) return null;
		return new IntegerValue(res);
	}

	@Override
	public Value div(Value other) {
		System.out.println("IntegerValue.div: " + other + " / " + this.value);
		int res = 0;
		if(other != null) {
			IntegerValue val2 = (IntegerValue) other;
			res = val2.value / this.value;
		} else res = 0;
		System.out.println("IntegerValue.div: " + other + " / " + this.value + " = " + res);
		if(res == 0) return null;
		return new IntegerValue(res);
	}

	public String toString() {
		return "C-INT " + value;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private IntegerValue(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_INT;
		value = inpt.readInt();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_INT);
		oupt.writeInt(value);
	}

	public static IntegerValue read(AttributeInputStream inpt) throws IOException {
		return new IntegerValue(inpt);
	}


}
