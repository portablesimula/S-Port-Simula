package bec.descriptor;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.InsertStatement;
import bec.ModuleIO;
import bec.compileTimeStack.DataType;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.util.Scode;
import bec.util.Tag;

public class Attribute extends Descriptor {
	int rela;
	int size;
	int repCount;
	
	private Attribute(int kind, Tag tag) {
		super(kind, tag);
	}
	
	public Attribute(int rela) {
		super(Kind.K_Attribute, Tag.inTag());
		this.rela = rela;
		Type type = new Type();
//		this.size = type.size();
		this.size = DataType.typeSize(type.tag);
		this.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
	}
	
//	private Type DataType.typeSize(Type type) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public static Attribute ofLocalVariable(Tag tag, Tag type) {
		Util.IERR("DETTE MÅ RETTES");
		return new Attribute(Kind.K_LocalVar, type);
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
		oupt.writeShort(rela);
		oupt.writeShort(repCount);
		oupt.writeShort(size);
	}

	public static Attribute read(AttributeInputStream inpt, int kind) throws IOException {
//		int tag = inpt.readShort();
//		tag = InsertStatement.current.chgInType(tag);
		Tag tag = Tag.read(inpt);
		Attribute loc = new Attribute(kind, tag);
		loc.rela = inpt.readShort();
		loc.repCount = inpt.readShort();
		loc.size = inpt.readShort();
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("LocDescr.Read: " + loc);
		return loc;
	}


}
