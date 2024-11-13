package bec.util;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.RecordDescr;
import bec.segment.DataSegment;

public class Type {
	public int tag;
//	Range range;
	
	/**
	 *	 type ::= structured_type | simple_type | range_type
	 * 
	 *	 	simple_type ::= BOOL | CHAR | INT | REAL | LREAL | SIZE | OADDR | AADDR | GADDR | PADDR | RADDR
	 * 
	 *	 	structured_type ::= record_tag:tag
	 *
	 *		 range_type
	 *			::= INT range lower:number upper:number  -- NOTE: DETTE ER NYTT
	 *			::= SINT                                 -- NOTE: DETTE ER NYTT
	 *
	 */
	public Type() {
		tag = Scode.inTag();
		if(tag == Scode.TAG_INT) {
			if(Scode.accept(Scode.S_RANGE)) {
				//range = new Range();
				Scode.inNumber(); // low
				Scode.inNumber(); // high
			}
		}
		if(Scode.accept(Scode.S_FIXREP)) Util.IERR("DETTE ER EN 'ResolvedType' - HVA NÅ ?");
	}
	
	public boolean isSimple() {
		return tag <= Scode.TAG_SIZE;
	}
	
//	public PREV_Value defaultValue() {
//		switch(tag) {
//			case Scode.TAG_BOOL:  return new BooleanValue(true);
//			case Scode.TAG_CHAR:  return new CharacterValue(0);
//			case Scode.TAG_INT:   return new IntegerValue(0);
//			case Scode.TAG_SINT:  return new IntegerValue(0);
//			case Scode.TAG_REAL:  return new RealValue(0);
//			case Scode.TAG_LREAL: return new LongRealValue(0);
//			case Scode.TAG_SIZE:  return new SizeValue(true);
//			case Scode.TAG_OADDR: return new ObjectAddress(true);
//			case Scode.TAG_AADDR: return new AttributeAddress(true);
//			case Scode.TAG_GADDR: return new GeneralAddress(true);
//			case Scode.TAG_PADDR: return new ProgramAddress(true);
//			case Scode.TAG_RADDR: return new RoutineAddress(true);
//			default: Util.IERR("MISSING: " + Scode.edTag(tag)); return null;
//		}
//	}
	
	public void emitDefaultValue(DataSegment dseg, String comment) {
		if(this.isSimple()) {
//			dseg.emit(defaultValue(), comment);
			dseg.emit(null, comment);
		} else {
			Object obj = Global.DISPL.get(tag);
//			System.out.println("Type.emitDefaultValue: tag="+tag+", obj="+obj.getClass().getSimpleName());
			if(obj instanceof RecordDescr rec) {
				rec.emitDefaultValues(dseg, 1, comment);
			} else Util.IERR(""+obj);
			
		}
	}
	
	public int size() {
		if(this.isSimple()) return(1);
		Object obj = Global.DISPL.get(tag);
//		if(obj instanceof RECORD rec) {
//			return rec.size();
		if(obj instanceof RecordDescr rec) {
			return rec.nbyte;
//		} else Util.IERR("IMPOSSIBLE: " + Scode.edTag(tag) + "  " + obj.getClass().getSimpleName());
		} else Util.IERR("IMPOSSIBLE: " + Scode.edTag(tag));
		return 0;
	}
	
	public String toString() {
		return Scode.edTag(tag);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	protected Type(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag();
		System.out.println("NEW ResolvedType(inpt): " + Scode.edInstr(tag));
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeTag(tag);
	}

	public static Type read(AttributeInputStream inpt) throws IOException {
		return new Type(inpt);
	}


}
