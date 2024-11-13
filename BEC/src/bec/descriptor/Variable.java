package bec.descriptor;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.InsertStatement;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Type;
import bec.util.Scode;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.LongRealValue;
import bec.value.MemAddr;
import bec.value.RealValue;
import bec.value.SizeValue;
import bec.value.Value;

public class Variable extends Descriptor {
//	public int instr; // S_GLOBAL, S_LOCAL, S_IMPORT, S_EXPORT, S_EXIT
	public MemAddr address;
	public int repCount;
	
//	String system;

	/**
	 * 	global_definition ::= global internal:newtag quantity_descriptor
	 * 
	 * 	local_quantity ::= local var:newtag quantity_descriptor
	 * 
	 * 	import_definition ::= import parm:newtag quantity_descriptor
	 * 
	 *	export parm:newtag resolved_type
	 * 
	 *	exit return:newtag
	 */	
	private Variable(int kind, int tag) {
		super(kind, tag);
	}
	
	public static Variable ofIMPORT(DataSegment seg) {
		if(seg == null) Util.IERR("");
		int tag = Scode.inTag();
		Variable var = new Variable(Kind.K_Import, tag);
		Type type = new Type();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		var.address = seg.nextAddress();
		if(var.address.seg == null) Util.IERR("");
		type.emitDefaultValue(seg, "IMPORT " + type);
		return var;
	}
	
	public static Variable ofEXPORT(DataSegment seg) {
		if(seg == null) Util.IERR("");
		int tag = Scode.inTag();
		Variable var = new Variable(Kind.K_Export, tag);
		Type type = new Type();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		var.address = seg.nextAddress();
		type.emitDefaultValue(seg, "EXPORT " + type);
		return var;
	}
	
	public static Variable ofEXIT(DataSegment seg) {
		if(seg == null) Util.IERR("");
		int tag = Scode.inTag();
		Variable var = new Variable(Kind.K_Exit, tag);
		var.address = seg.nextAddress();
		seg.emit(null, "EXIT");
		return var;
	}
	
	public static Variable ofRETUR(DataSegment seg) {
		Variable var = new Variable(Kind.K_Retur, 0);
		var.address = seg.nextAddress();
		seg.emit(null, "RETUR");
		return var;
	}
	
	public static Variable ofLocal(DataSegment seg) {
		int tag = Scode.inTag();
		Variable var = new Variable(Kind.K_LocalVar, tag);
		Type type = new Type();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		var.address = seg.nextAddress();
		type.emitDefaultValue(Global.DSEG, var.toString());			
		if(var.repCount > 1) {
			Util.IERR("");
		}
//		Global.dumpDISPL("Variable.ofGlobal: ");
//		seg.dump("Variable.ofGlobal: ");
//		Util.IERR("");
		return var;
	}
	
	public static Variable ofGlobal(DataSegment seg) {
		int tag = Scode.inTag();
		Variable var = new Variable(Kind.K_GlobalVar, tag);
		Type type = new Type();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		var.address = seg.nextAddress();
		if(Scode.accept(Scode.S_SYSTEM)) {
			String system = Scode.inString();
//			System.out.println("NEW Global Variable: " + Scode.edTag(var.tag) + " " + var.quant + " SYSTEM " + system);
			Value value = null;
			if(system.equalsIgnoreCase("CURINS")) value = null;//new ObjectAddress(true);
			else if(system.equalsIgnoreCase("STATUS")) value = null;//new IntegerValue(0);
			else if(system.equalsIgnoreCase("ITSIZE")) value = null;//new IntegerValue(0);
			else if(system.equalsIgnoreCase("MAXLEN")) value = new SizeValue(20);
			else if(system.equalsIgnoreCase("INPLTH")) value = new IntegerValue(80);
			else if(system.equalsIgnoreCase("OUTLTH")) value = new IntegerValue(172);
			else if(system.equalsIgnoreCase("BIOREF")) value = null;//new ObjectAddress(true);
			else if(system.equalsIgnoreCase("MAXINT")) value = new IntegerValue(Integer.MAX_VALUE);
			else if(system.equalsIgnoreCase("MININT")) value = new IntegerValue(Integer.MIN_VALUE);
			else if(system.equalsIgnoreCase("MAXRNK")) value = new IntegerValue(Byte.MAX_VALUE);
			else if(system.equalsIgnoreCase("MAXREA")) value = new RealValue(Float.MAX_VALUE);
			else if(system.equalsIgnoreCase("MINREA")) value = new RealValue(Float.MIN_VALUE);
			else if(system.equalsIgnoreCase("MAXLRL")) value = new LongRealValue(Double.MAX_VALUE);
			else if(system.equalsIgnoreCase("MINLRL")) value = new LongRealValue(Double.MIN_VALUE);
			else if(system.equalsIgnoreCase("INIERR")) value = null;//new RoutineAddress(true);
			else if(system.equalsIgnoreCase("ALLOCO")) value = null;//new RoutineAddress(true);
			else if(system.equalsIgnoreCase("FREEOB")) value = null;//new RoutineAddress(true);
			else Util.IERR("MISSING: " + system);
			Global.DSEG.emit(value, var.toString());
		} else {
			type.emitDefaultValue(Global.DSEG, var.toString());			
			if(var.repCount > 1) {
				Util.IERR("");
			}
		}

//		Global.dumpDISPL("Variable.ofGlobal: ");
//		seg.dump("Variable.ofGlobal: ");
//		Util.IERR("");
		return var;
	}
	
//	private void emit(DataSegment dseg, String cmnt) {
////		Value value = quant.type.defaultValue();
//		String comment = Scode.edTag(tag) + " Quant=" + quant + "  " + cmnt;
////		dseg.emit(value, comment);
//		
//		quant.type.emitDefaultValue(dseg, comment);
//	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + this);
	}
	
	public String toString() {
		String s = "Variable " +Kind.edKind(kind) + " " + Scode.edTag(tag) + " " + address;
//		if(system != null) s += " " + "SYSTEM " + system;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Variable.Write: " + this);
		oupt.writeKind(kind); // K_GLOBAL, K_LOCAL, K_IMPORT, K_EXPORT, K_EXIT, K_RETUR
		oupt.writeShort(tag);
		oupt.writeShort(repCount);
		address.write(oupt);
	}

	public static Variable read(AttributeInputStream inpt, int kind) throws IOException {
		int tag = inpt.readShort();
		tag = InsertStatement.current.chgInType(tag);
		Variable var = new Variable(kind, tag);
		var.repCount = inpt.readShort();
		var.address = MemAddr.read(inpt);
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Variable.Read: " + var);
		return var;
	}


}
