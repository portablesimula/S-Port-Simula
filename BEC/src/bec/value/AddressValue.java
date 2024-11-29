package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;

public class AddressValue extends Value {
	public Tag tag;
	
	private AddressValue(Tag tag, Type type) {
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
	public static AddressValue ofAADDR() { return new AddressValue(Tag.ofScode(), Type.T_AADDR); }
	public static AddressValue ofOADDR() { return new AddressValue(Tag.ofScode(), Type.T_OADDR); }
	public static AddressValue ofGADDR() { return new AddressValue(Tag.ofScode(), Type.T_GADDR); }
	public static AddressValue ofRADDR() { return new AddressValue(Tag.ofScode(), Type.T_RADDR); }
	public static AddressValue ofPADDR() { return new AddressValue(Tag.ofScode(), Type.T_PADDR); }

//	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}
	
	public String toString() {
		return("" + tag + " " + type);
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private AddressValue(AttributeInputStream inpt) throws IOException {
		tag = Tag.read(inpt);
		type = Type.read(inpt);
//		System.out.println("NEW AddressValue: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
//		oupt.writeTag(tag);
		tag.write(oupt);
		type.write(oupt);;
	}

	public static AddressValue read(AttributeInputStream inpt) throws IOException {
		return new AddressValue(inpt);
	}


}
