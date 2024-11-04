package bec.util;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;

public class QuantityDescriptor {
	public ResolvedType type;
	public int repCount;
	
	public QuantityDescriptor() {
		repCount = 1;
		parse();
	}

	/**
	 * quantity_descriptor ::= resolved_type < Rep count:number >?
	 * 
	 * resolved_type
	 * 		::= resolved_structure | simple_type
	 * 		::= INT range lower:number upper:number
	 * 		::= SINT
	 */
	public void parse() {
		type = new ResolvedType();
		if(Scode.accept(Scode.S_REP))
			repCount = Scode.inNumber();
//		System.out.println("NEW QuantityDescriptor: " + this);
	}
	
	public int size() {
		int n = type.size();
		if(repCount > 1) n = n * repCount;
		return n;
	}
	
	public String toString() {
		if(repCount > 1 )
		return "" + type + " " + repCount;
		return "" + type;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	private QuantityDescriptor(AttributeInputStream inpt) throws IOException {
		type = ResolvedType.read(inpt);
		repCount = inpt.readShort();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		type.write(oupt);
		oupt.writeShort(repCount);
	}

	public static QuantityDescriptor read(AttributeInputStream inpt) throws IOException {
		return new QuantityDescriptor(inpt);
	}


}
