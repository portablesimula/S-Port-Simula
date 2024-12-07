package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class TextValue extends Value {
	public ObjectAddress addr; // Pointer to StringValue
	
	private TextValue() {
		this.type = Type.T_TEXT;
	}

	/**
	 * text_value ::= text long_string
	 */
	public static TextValue ofScode() {
//		System.out.println("TextValue.parse: curinstr=" + Scode.edInstr(Scode.curinstr));
		String str = Scode.inLongString();
		StringValue strval = new StringValue(str);

		TextValue txtval = new TextValue();
		txtval.addr = Global.CSEG.emit(strval, null);
		return txtval;
	}
	
	public String getString() {
		if(addr == null) return null;
		StringValue strval =  (StringValue) addr.load();
		return strval.value;
	}
	
	public String toString() {
		return "TEXT \""+ getString() +"\" at " + addr;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private TextValue(AttributeInputStream inpt) throws IOException {
		System.out.println("BEGIN TextValue.read: " + this);
		this.type = Type.T_TEXT;
		addr = (ObjectAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("TextValue.read: " + this);
		System.out.println("NEW TextValue: " + this);
//		Util.IERR("SJEKK DETTE");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_TEXT);
		System.out.println("TextValue.write: addr.segID" + addr.segID);
//		Util.IERR("");
		addr.write(oupt);
	}

	public static TextValue read(AttributeInputStream inpt) throws IOException {
		return new TextValue(inpt);
	}


}
