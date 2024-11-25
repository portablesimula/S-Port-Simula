package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;
import bec.util.Type;

public class AttributeValue extends Value {
	int tag;
	public Type type;
	public RepetitionValue value;
	
	private AttributeValue() {
//		parse();
	}

	/**
	 * 	attribute_value
	 * 		::= attr attribute:tag type repetition_value
	 */
	public static AttributeValue ofScode() {
		AttributeValue aval = new AttributeValue();
		aval.tag = Scode.inTag();
		aval.type = Type.ofScode();
		aval.value = RepetitionValue.ofScode();
//		System.out.println("AttributeValue.parse: value=" + value);
		return aval;
	}

	public static AttributeValue of(int tag, Type type, RepetitionValue value) {
		AttributeValue aval = new AttributeValue();
		aval.tag = tag;
		aval.type = type;
		aval.value = value;
//		System.out.println("AttributeValue.parse: value=" + value);
		return aval;
	}

//	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}
	
	public String toString() {
		return "ATTR " + Scode.edTag(tag) + " " + type + " "+ value;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private AttributeValue(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag();
		type = Type.read(inpt);
		value = RepetitionValue.read(inpt);
		System.out.println("NEW ATTR-VALUE: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_ATTR);
		oupt.writeTag(tag);
		type.write(oupt);
		value.write(oupt);
		
//		this.printTree(2);
//		Util.IERR("");
	}

	public static AttributeValue read(AttributeInputStream inpt) throws IOException {
		return new AttributeValue(inpt);
	}
	

}
