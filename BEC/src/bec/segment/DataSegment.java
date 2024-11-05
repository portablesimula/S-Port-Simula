package bec.segment;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.syntaxClass.value.AttributeAddress;
import bec.syntaxClass.value.BooleanValue;
import bec.syntaxClass.value.CharacterValue;
import bec.syntaxClass.value.DotAddress;
import bec.syntaxClass.value.GeneralAddress;
import bec.syntaxClass.value.IntegerValue;
import bec.syntaxClass.value.LongRealValue;
import bec.syntaxClass.value.ObjectAddress;
import bec.syntaxClass.value.ProgramAddress;
import bec.syntaxClass.value.RealValue;
import bec.syntaxClass.value.RoutineAddress;
import bec.syntaxClass.value.SizeValue;
import bec.syntaxClass.value.TextValue;
import bec.syntaxClass.value.Value;
import bec.util.Global;
import bec.util.ResolvedType;
import bec.util.Scode;
import bec.util.Util;

public class DataSegment extends Segment {
	Vector<Value> values;
	Vector<String> comment;
	
	public DataSegment(String ident, int segmentKind) {
		super(ident, segmentKind);
		SEGMAP.put(ident, this);
		this.ident = ident.toUpperCase();
		this.segmentKind = segmentKind;
		values = new Vector<Value>();
		comment = new Vector<String>();
	}
	
	public MemAddr nextAddress() {
		return new MemAddr(this,values.size());
	}
	
	public void emit(Value value,String cmnt) {
		values.add(value);
		comment.add(cmnt);
	}

	public MemAddr emitValue(String comment) {
		MemAddr addr = Global.DSEG.nextAddress();
	LOOP:while(true) {
			if(Global.ATTR_OUTPUT_TRACE)
				System.out.println("DataSegment.emitValue: "+Scode.edInstr(Scode.nextByte())+"  Comment="+comment);
			switch(Scode.nextByte()) {
				case Scode.S_TEXT:     Scode.inputInstr(); emit(new TextValue(), comment); break;
			    case Scode.S_C_INT:    Scode.inputInstr(); emit(new IntegerValue(), comment); break;
			    case Scode.S_C_REAL:   Scode.inputInstr(); emit(new RealValue(), comment); break;
			    case Scode.S_C_LREAL:  Scode.inputInstr(); emit(new LongRealValue(), comment); break;
			    case Scode.S_C_CHAR:   Scode.inputInstr(); emit(new CharacterValue(), comment); break;
			    case Scode.S_C_SIZE:   Scode.inputInstr(); emit(new SizeValue(false), comment); break;
			    case Scode.S_TRUE:     Scode.inputInstr(); emit(new BooleanValue(true), comment); break;
			    case Scode.S_FALSE:    Scode.inputInstr(); emit(new BooleanValue(false), comment); break;
			    case Scode.S_C_AADDR:  Scode.inputInstr(); emit(new AttributeAddress(false), comment); break;
			    case Scode.S_C_PADDR:  Scode.inputInstr(); emit(new ProgramAddress(false), comment); break;
			    case Scode.S_C_RADDR:  Scode.inputInstr(); emit(new RoutineAddress(false), comment); break;
			    case Scode.S_NOSIZE:   Scode.inputInstr(); emit(new SizeValue(true), comment); break;
			    case Scode.S_ANONE:    Scode.inputInstr(); emit(new AttributeAddress(true), comment); break;
			    case Scode.S_NOWHERE:  Scode.inputInstr(); emit(new ProgramAddress(true), comment); break;
			    case Scode.S_NOBODY:   Scode.inputInstr(); emit(new RoutineAddress(true), comment); break;
			    case Scode.S_ONONE:    Scode.inputInstr(); emit(new ObjectAddress(true), comment); break;
			    case Scode.S_GNONE:    Scode.inputInstr(); emit(new GeneralAddress(true), comment); break;
			    case Scode.S_C_OADDR:  Scode.inputInstr(); emit(new ObjectAddress(false), comment); break;
			    case Scode.S_C_GADDR:  Scode.inputInstr(); emit(new GeneralAddress(false), comment); break;
			    case Scode.S_C_DOT:    Scode.inputInstr(); emit(new DotAddress(), comment); break;
			    case Scode.S_C_RECORD: Scode.inputInstr(); emitRecordValue(comment); break;
				default: break LOOP;
			}
		}
		return addr;
	}

	/**
	 *	record_value
	 *		::= c-record structured_type
	 *				 <attribute_value>+
	 *			endrecord
	 *
	 * 			attribute_value
	 * 				::= attr attribute:tag type repetition_value
	 */
	private void emitRecordValue(String comment) {
		Scode.inTag(); 
		Scode.inputInstr();
		while(Scode.curinstr == Scode.S_ATTR) {
			int atag = Scode.inTag();
			ResolvedType type = new ResolvedType();
//			System.out.println("DataSegment.emitRecordValue'S_ATTR: "+Scode.edTag(atag)+"  "+type);

			emitValue(comment + "  " + Scode.edTag(atag));
			Scode.inputInstr();
//			System.out.println("DataSegment.emitRecordValue: Curinstr="+Scode.edInstr(Scode.curinstr));
		}
		if(Scode.curinstr != Scode.S_ENDRECORD) Util.IERR("Syntax error in record-constant");
	}


	
	public void dump(String title) {
		if(values.size() == 0) return;
		System.out.println("==================== " + title + ident + " DUMP ====================");
		for(int i=0;i<values.size();i++) {
			String line = "" + i + ": ";
			while(line.length() < 8) line = " " +line;
			String value = ""+values.get(i);
			while(value.length() < 25) value = value + ' ';
			System.out.println(line + value + "   " + comment.get(i));
		}
		System.out.println("==================== " + title + ident + " END  ====================");
	}
	
	public String toString() {
		return "DataSegment " + ident;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private DataSegment(String ident, int segmentKind, AttributeInputStream inpt) throws IOException {
//		this.ident = ident;
//		this.segmentKind = segmentKind;
		super(ident, segmentKind);
		SEGMAP.put(ident, this);
		values = new Vector<Value>();
		comment = new Vector<String>();
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			comment.add(inpt.readString());
			values.add(Value.read(inpt));
		}
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_BSEG);
		oupt.writeKind(segmentKind);
		oupt.writeString(ident);
		oupt.writeShort(values.size());
		for(int i=0;i<values.size();i++) {
			oupt.writeString(comment.get(i));
			Value val = values.get(i);
			if(val == null)
				 oupt.writeInstr(Scode.S_NULL);
			else val.write(oupt);
		}
	}

	public static DataSegment readObject(AttributeInputStream inpt) throws IOException {
		int segmentKind = inpt.readKind();
		String ident = inpt.readString();
		System.out.println("DataSegment.readObject: ident="+ident+", segmentKind="+segmentKind);
		DataSegment seg = new DataSegment(ident, segmentKind, inpt);
//		seg.dump();
//		Util.IERR("");
		return seg;
	}
	

}
