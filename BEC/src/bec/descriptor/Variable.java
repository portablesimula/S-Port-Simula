package bec.descriptor;

import java.io.IOException;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.value.IntegerValue;
import PREV.syntaxClass.value.LongRealValue;
import PREV.syntaxClass.value.ObjectAddress;
import PREV.syntaxClass.value.RealValue;
import PREV.syntaxClass.value.RoutineAddress;
import PREV.syntaxClass.value.SizeValue;
import PREV.syntaxClass.value.Value;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.MemAddr;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.QuantityDescriptor;
import bec.util.Scode;
import bec.util.Util;

public class Variable extends Descriptor {
	public int instr; // S_GLOBAL, S_LOCAL, S_IMPORT, S_EXPORT, S_EXIT
	public MemAddr address;
	public QuantityDescriptor quant;
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
		Variable var = new Variable(Kind.K_Parameter, tag);
		var.instr = Scode.S_IMPORT;
		var.quant = new QuantityDescriptor();
		var.address = seg.nextAddress();
		if(var.address.seg == null) Util.IERR("");
		var.quant.type.emitDefaultValue(seg, "IMPORT " + var.quant);
//		System.out.println("Variable ofIMPORT: "+var);
//		Global.intoDisplay(var,var.tag);
		return var;
	}
	
	public static Variable ofEXPORT(DataSegment seg) {
		if(seg == null) Util.IERR("");
		int tag = Scode.inTag();
		Variable var = new Variable(Kind.K_Export, tag);
		var.instr = Scode.S_EXPORT;
		var.quant = new QuantityDescriptor();
		var.address = seg.nextAddress();
		var.quant.type.emitDefaultValue(seg, "EXPORT " + var.quant);
//		Global.intoDisplay(var,var.tag);
		return var;
	}
	
	public static Variable ofEXIT(DataSegment seg) {
		if(seg == null) Util.IERR("");
		int tag = Scode.inTag();
		Variable var = new Variable(Kind.K_Exit, tag);
		var.instr = Scode.S_EXIT;
		var.address = seg.nextAddress();
		var.quant.type.emitDefaultValue(seg, "EXIT " + var.quant);
//		Global.intoDisplay(var,var.tag);
		return var;
	}
	
	public static Variable ofRETUR(DataSegment seg) {
		Variable var = new Variable(Kind.K_Retur, 0);
		var.instr = Scode.S_EXIT;
		var.address = seg.nextAddress();
//		var.quant.type.emitDefaultValue(seg, "RETUR ");
		seg.emit(null, "RETUR");
//		Global.intoDisplay(var,var.tag);
		return var;
	}
	
	public static Variable ofGlobal(DataSegment seg) {
		int tag = Scode.inTag();
		Variable var = new Variable(Kind.K_GlobalVar, tag);
		var.instr = Scode.S_GLOBAL;
		var.quant = new QuantityDescriptor();
		var.address = seg.nextAddress();
		System.out.println("Variable.ofGlobal: quant="+var.quant);
//		var.quant.type.emitDefaultValue(seg, "RETUR ");
//		seg.emit(null, "RETUR");
//		Global.intoDisplay(var,var.tag);
		Descriptor test = Global.DISPL.get(tag);
		System.out.println("NEW Global Variable: TEST=" + test);

		if(Scode.accept(Scode.S_SYSTEM)) {
			String system = Scode.inString();
			System.out.println("NEW Global Variable: " + Scode.edTag(var.tag) + " " + var.quant + " SYSTEM " + system);
			Value value = null;
			if(system.equalsIgnoreCase("CURINS")) value = new ObjectAddress(true);
			else if(system.equalsIgnoreCase("STATUS")) value = new IntegerValue(0);
			else if(system.equalsIgnoreCase("ITSIZE")) value = new IntegerValue(0);
			else if(system.equalsIgnoreCase("MAXLEN")) value = new SizeValue(20);
			else if(system.equalsIgnoreCase("INPLTH")) value = new IntegerValue(80);
			else if(system.equalsIgnoreCase("OUTLTH")) value = new IntegerValue(172);
			else if(system.equalsIgnoreCase("BIOREF")) value = new ObjectAddress(true);
			else if(system.equalsIgnoreCase("MAXINT")) value = new IntegerValue(Integer.MAX_VALUE);
			else if(system.equalsIgnoreCase("MININT")) value = new IntegerValue(Integer.MIN_VALUE);
			else if(system.equalsIgnoreCase("MAXRNK")) value = new IntegerValue(Byte.MAX_VALUE);
			else if(system.equalsIgnoreCase("MAXREA")) value = new RealValue(Float.MAX_VALUE);
			else if(system.equalsIgnoreCase("MINREA")) value = new RealValue(Float.MIN_VALUE);
			else if(system.equalsIgnoreCase("MAXLRL")) value = new LongRealValue(Double.MAX_VALUE);
			else if(system.equalsIgnoreCase("MINLRL")) value = new LongRealValue(Double.MIN_VALUE);
			else if(system.equalsIgnoreCase("INIERR")) value = new RoutineAddress(true);
			else if(system.equalsIgnoreCase("ALLOCO")) value = new RoutineAddress(true);
			else if(system.equalsIgnoreCase("FREEOB")) value = new RoutineAddress(true);
			else Util.IERR("MISSING: " + system);
			Global.DSEG.emit(value, var.toString());
		} else {
			var.quant.type.emitDefaultValue(Global.DSEG, var.toString());			
			if(var.quant.repCount > 1) {
				Util.IERR("");
			}
		}

		Global.dumpDISPL("Variable.ofGlobal: ");
		seg.dump("Variable.ofGlobal: ");
//		Util.IERR("");
		return var;
	}
	
//	public Variable(int kind, int tag, int instr) {
//		super(kind, tag);
//
//		this.instr = instr;
////		tag = Scode.inTag();
//		if(instr != Scode.S_EXIT)
//			quant = new QuantityDescriptor();
//
//		address = Global.DSEG.nextAddress();
//		if(Scode.accept(Scode.S_SYSTEM)) {
//			String system = Scode.inString();
////			System.out.println("NEW Variable: " + Scode.edInstr(instr) + " " + Scode.edTag(tag) + " " + quant + " SYSTEM " + system);
//			Value value = null;
//			if(system.equalsIgnoreCase("CURINS")) value = new ObjectAddress(true);
//			else if(system.equalsIgnoreCase("STATUS")) value = new IntegerValue(0);
//			else if(system.equalsIgnoreCase("ITSIZE")) value = new IntegerValue(0);
//			else if(system.equalsIgnoreCase("MAXLEN")) value = new SizeValue(20);
//			else if(system.equalsIgnoreCase("INPLTH")) value = new IntegerValue(80);
//			else if(system.equalsIgnoreCase("OUTLTH")) value = new IntegerValue(172);
//			else if(system.equalsIgnoreCase("BIOREF")) value = new ObjectAddress(true);
//			else if(system.equalsIgnoreCase("MAXINT")) value = new IntegerValue(Integer.MAX_VALUE);
//			else if(system.equalsIgnoreCase("MININT")) value = new IntegerValue(Integer.MIN_VALUE);
//			else if(system.equalsIgnoreCase("MAXRNK")) value = new IntegerValue(Byte.MAX_VALUE);
//			else if(system.equalsIgnoreCase("MAXREA")) value = new RealValue(Float.MAX_VALUE);
//			else if(system.equalsIgnoreCase("MINREA")) value = new RealValue(Float.MIN_VALUE);
//			else if(system.equalsIgnoreCase("MAXLRL")) value = new LongRealValue(Double.MAX_VALUE);
//			else if(system.equalsIgnoreCase("MINLRL")) value = new LongRealValue(Double.MIN_VALUE);
//			else if(system.equalsIgnoreCase("INIERR")) value = new RoutineAddress(true);
//			else if(system.equalsIgnoreCase("ALLOCO")) value = new RoutineAddress(true);
//			else if(system.equalsIgnoreCase("FREEOB")) value = new RoutineAddress(true);
//			else Util.IERR("MISSING: " + system);
//			Global.DSEG.emit(value, this.toString());
//		} else {
//			quant.type.emitDefaultValue(Global.DSEG, this.toString());			
//			if(quant.repCount > 1) {
//				Util.IERR("");
//			}
//		}
		
//		System.out.println("NEW Variable: Line "+this.lineNumber+": "+this);
//		Global.DSEG.dump();
//		Util.IERR("");
		
//		Global.Display.set(tag, this);
		
//	}
	
	public void emit(DataSegment dseg, String cmnt) {
//		Value value = quant.type.defaultValue();
		String comment = Scode.edTag(tag) + " Quant=" + quant + "  " + cmnt;
//		dseg.emit(value, comment);
		
		quant.type.emitDefaultValue(dseg, comment);
	}

//	@Override
//	public void printTree(final int indent) {
//		sLIST(indent, toString());
//	}
	
	public String toString() {
		String s = Scode.edInstr(instr) + " " + Scode.edTag(tag) + " " + address;
//		if(system != null) s += " " + "SYSTEM " + system;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(instr); // S_GLOBAL, S_LOCAL, S_IMPORT, S_EXPORT, S_EXIT
		address.write(oupt);
		quant.write(oupt);
	}

	public static SyntaxClass read(AttributeInputStream inpt) throws IOException {
		Util.IERR("Static Method 'readObject' needs a redefiniton");
		return(null);
	}


}
