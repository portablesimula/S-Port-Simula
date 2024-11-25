package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.ModuleIO;
import bec.statement.InsertStatement;
import bec.util.Global;
import bec.util.Type;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.MemAddr;
import bec.value.RepetitionValue;
import bec.value.Value;

public class ConstDescr extends Descriptor {
//	int tag;
	public Type type;
	public MemAddr address;
//	public QuantityDescriptor quant;
	RepetitionValue value;
	
	private ConstDescr(int kind, Tag tag) {
		super(kind, tag);
//		this.tag = tag;
////		Global.Display.set(tag, this);
//		Global.intoDisplay(this, tag);
	}

//	%title ***   C o n s t   and   C o n s t s p e c   ***
	/**
	 * constant_declaration
	 * 		::= constant_specification | constant_definition
	 * 
	 *	constant_specification
	 *		::= constspec const:newtag quantity_descriptor
	 *
	 *	constant_definition
	 *		::= const const:spectag quantity_descriptor repetition_value
	 */
	public static ConstDescr ofConstSpec() {
//		Tag tag = Tag.inTag();
		Tag tag = Tag.inTag();
		ConstDescr cnst = (ConstDescr) Global.DISPL.get(tag.val);
		if(cnst != null) Util.IERR("New CONSPEC but cnst="+cnst);
		cnst = new ConstDescr(Kind.K_Coonst, tag);
		System.out.println("NEW ConstDescr.ofConstSpec: "+cnst);
		
//		cnst.quant = new QuantityDescriptor();
		cnst.type = Type.ofScode();
		int repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;

		
		System.out.println("CONST.inConstant: " + cnst);
//		if(Global.traceMode > 3)
			cnst.print("   ");
//		Util.IERR("");
		return cnst;
		
	}
	public static ConstDescr ofConstDef() {
//		Tag tag = Tag.inTag();
		Tag tag = Tag.inTag();
		ConstDescr cnst = (ConstDescr) Global.DISPL.get(tag.val);
		if(cnst == null) {
			cnst = new ConstDescr(Kind.K_Coonst, tag);
		}
		System.out.println("NEW ConstDescr.ofConstDef: "+cnst);
//		cnst.quant = new QuantityDescriptor();
		cnst.type = Type.ofScode();
		int repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;

//		if(constDef) cnst.value = new RepetitionValue();
			String comment = tag + " type=" + cnst.type;
//			System.out.println("NEW CONST: "+comment);
			cnst.address = Global.CSEG.emitValue(comment);
//			Global.CSEG.dump("CONST.inConstant: ");
//			Util.IERR("");
		
//		System.out.println("CONST.inConstant: " + cnst);
		if(Global.traceMode > 3) cnst.print("   ");
//		Util.IERR("");
		return cnst;
	}
	
	@Override
	public void print(final String indent) {
		if(value != null) {
			boolean done = false;
			if(value.values instanceof Vector<Value> vector) {
				if(vector instanceof Vector<?> elts) {
					boolean first = true;
					for(Object rVal:elts) {
						if(first) System.out.println(indent + "CONST " + tag);
						first = false;
						((Value)rVal).print(indent + "   ");							
					} done = true;
				}
			}
			if(! done) System.out.println(indent + "   " + toString());
		} else System.out.println(indent + "   " + toString());
	}
	
	public String toString() {
		if(address != null) {
			 return "CONST " + tag + " " + address;
		} else if(value != null) {
			 return "CONST " + tag + " " + value;
		} else return "CONSTSPEC " + tag;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("CONST.Write: " + this);
		oupt.writeKind(kind);
//		oupt.writeShort(ModuleIO.chgType(tag));
		tag.write(oupt);
		type.write(oupt);
		address.write(oupt);
//		quant.write(oupt);
	}

	public static ConstDescr read(AttributeInputStream inpt) throws IOException {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  BEGIN CONST.Read");
//		int tag = inpt.readShort();
//		tag = InsertStatement.current.chgInType(tag);
		Tag tag = Tag.read(inpt);
		ConstDescr cns = new ConstDescr(Kind.K_Coonst, tag);
		System.out.println("AFTER NEW CONST: "+cns);
		cns.type = Type.read(inpt);
		cns.address = MemAddr.read(inpt);
		System.out.println("AFTER NEW MEMADDR: "+cns);
//		Util.IERR("Static Method 'readObject' needs a redefiniton");
		return(cns);
	}


}
