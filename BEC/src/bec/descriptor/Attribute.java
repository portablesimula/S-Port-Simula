package bec.descriptor;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.util.Scode;
import bec.util.Tag;

public class Attribute extends Descriptor {
	public Type type;
	public int rela;
	public int size;
	int repCount;
	
	private Attribute(int kind, Tag tag, Type type) {
		super(kind, tag);
		this.type = type;
		if(type == null) Util.IERR("NEW Attribute: Missing type");
	}
	
	public Attribute(int rela) {
		super(Kind.K_Attribute, Tag.inTag());
		this.rela = rela;
		this.type = Type.ofScode();
		this.size = type.size();
		this.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
	}
	
//	private Type DataType.typeSize(Type type) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public static Attribute ofLocalVariable(Tag tag, Type type) {
		Util.IERR("DETTE MÅ RETTES");
		return new Attribute(Kind.K_LocalVar, tag, type);
	}
	
	@Override
	public void print(final String indent) {
		System.out.println(indent + this);
	}
	
	@Override
	public String toString() {
		return "Attribute: " + tag + " rela=" + rela + ", size=" + size;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("LocDescr.Write: " + this);
		oupt.writeKind(kind);
//		oupt.writeShort(ModuleIO.chgType(tag));
		tag.write(oupt);
		
//		if(type != null) {
//			oupt.writeBoolean(true);
//			type.write(oupt);
//		} else oupt.writeBoolean(false);
		type.write(oupt);
		
		oupt.writeShort(rela);
		oupt.writeShort(repCount);
		oupt.writeShort(size);
	}

	public static Attribute read(AttributeInputStream inpt, int kind) throws IOException {
//		int tag = inpt.readShort();
//		tag = InsertStatement.current.chgInType(tag);
		Tag tag = Tag.read(inpt);
		
//		boolean present = inpt.readBoolean();
//		Type type = (present)? type = Type.read(inpt) : null;
		Type type = Type.read(inpt);
		
		Attribute loc = new Attribute(kind, tag, type);
		loc.rela = inpt.readShort();
		loc.repCount = inpt.readShort();
		loc.size = inpt.readShort();
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("LocDescr.Read: " + loc);
		return loc;
	}


}
