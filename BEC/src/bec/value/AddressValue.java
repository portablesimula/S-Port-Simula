package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class AddressValue extends Value {
	int tag;
	
	private AddressValue(int tag, int type) {
		this.tag = tag;
		this.type = type;
	}

	/**
	 * attribute_address	::= c-aaddr attribute:tag
	 * object_address		::= c-oaddr global_or_const:tag
	 * general_address		::= c-gaddr global_or_const:tag
	 * routine_address		::= c-raddr body:tag
	 * program_address		::= c-paddr label:tag
	 */
	public static AddressValue ofAADDR() { return new AddressValue(Scode.inTag(), Scode.TAG_AADDR); }
	public static AddressValue ofOADDR() { return new AddressValue(Scode.inTag(), Scode.TAG_OADDR); }
	public static AddressValue ofGADDR() { return new AddressValue(Scode.inTag(), Scode.TAG_GADDR); }
	public static AddressValue ofRADDR() { return new AddressValue(Scode.inTag(), Scode.TAG_RADDR); }
	public static AddressValue ofPADDR() { return new AddressValue(Scode.inTag(), Scode.TAG_PADDR); }

//	@Override
//	public void printTree(final int indent) {
//		sLIST(indent, toString());
//	}
	
	public String toString() {
		return(Scode.edTag(tag) + " " + Scode.edTag(type));
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private AddressValue(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag();
		type = inpt.readTag();
//		System.out.println("NEW AddressValue: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeTag(tag);
		oupt.writeTag(type);
	}

	public static AddressValue read(AttributeInputStream inpt) throws IOException {
		return new AddressValue(inpt);
	}


}
