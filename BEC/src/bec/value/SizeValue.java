package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;
import bec.util.Type;

public class SizeValue extends Value {
	int size;
	
	public SizeValue(int size) {
		this.type = Scode.TAG_SIZE;
		this.size = size;
	}

	/**
	 * 	size_value
	 * 		::= c-size type
	 * 		::= NOSIZE
	 */
	public SizeValue() {
		this.type = Scode.TAG_SIZE;
		Type type = new Type();
		size = type.size();
	}

//	@Override
//	public void printTree(final int indent) {
//		sLIST(indent, toString());
//	}
	
	public String toString() {
		if(size == 0) return "NOSIZE";
		return("C-SIZE " + size);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SizeValue(AttributeInputStream inpt) throws IOException {
		this.type = Scode.TAG_SIZE;
		size = inpt.readShort();
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_SIZE);
		oupt.writeShort(size);
	}

	public static SizeValue read(AttributeInputStream inpt) throws IOException {
		return new SizeValue(inpt);
	}
	

}
