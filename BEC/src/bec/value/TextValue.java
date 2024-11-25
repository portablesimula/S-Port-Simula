package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;

public class TextValue extends Value {
	public String value;
	public MemAddr addr;
	
	public TextValue() {
		this.type = Type.T_TEXT;
		parse();
	}

	/**
	 * text_value ::= text long_string
	 */
	public void parse() {
//		System.out.println("TextValue.parse: curinstr=" + Scode.edInstr(Scode.curinstr));
		value = Scode.inLongString();
		addr = Global.CSEG.emit(this, null);
	}

//	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}
	
	public String toString() {
		return "TEXT \"" + value + "\" at " + addr;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private TextValue(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_CHAR;
		value = inpt.readString();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_TEXT);
		oupt.writeString(value);
	}

	public static TextValue read(AttributeInputStream inpt) throws IOException {
		return new TextValue(inpt);
	}


}
