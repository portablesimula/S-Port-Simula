package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;
import bec.util.Type;

public class CharacterValue extends Value {
	int value;
	
	public CharacterValue(int value) {
		this.value = value;
	}

	/**
	 * character_value ::= c-char byte
	 */
	public CharacterValue() {
		this.type = Type.T_CHAR;
		this.value = Scode.inByte();
	}

//	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}
	
	public String toString() {
		return "C-CHAR " + value;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_CHAR);
		oupt.writeChar(value);
	}

	public static CharacterValue read(AttributeInputStream inpt) throws IOException {
		return new CharacterValue(inpt.readChar());
	}


}
