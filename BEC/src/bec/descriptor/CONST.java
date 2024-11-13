package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.InsertStatement;
import bec.ModuleIO;
import bec.util.Global;
import bec.util.Type;
import bec.util.Scode;
import bec.util.Util;
import bec.value.MemAddr;
import bec.value.RepetitionValue;
import bec.value.Value;
import removed.java.Coasm;

public class CONST extends Descriptor {
//	int tag;
	public MemAddr address;
//	public QuantityDescriptor quant;
	RepetitionValue value;
	
	private CONST(int kind, int tag) {
		super(kind, tag);
//		this.tag = tag;
////		Global.Display.set(tag, this);
//		Global.intoDisplay(this, tag);
	}

//	%title ***   C o n s t   and   C o n s t s p e c   ***
	/**
	 *	constant_specification
	 *		::= constspec const:newtag quantity_descriptor
	 *
	 *	constant_definition
	 *		::= const const:spectag quantity_descriptor repetition_value
	 */
	public static void inConstant(boolean constDef) {
		int tag = Scode.inTag();
		CONST cnst = (CONST) Global.DISPL.get(tag);
		if(cnst == null) {
			if(constDef) Util.IERR("New CONSPEC but constDef="+constDef);
			cnst = new CONST(Kind.K_Coonst, tag);
		}
//		cnst.quant = new QuantityDescriptor();
		Type type = new Type();
		int repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;

//		if(constDef) cnst.value = new RepetitionValue();
		if(constDef) {
			String comment = Scode.edTag(tag) + " type=" + type;
//			System.out.println("NEW CONST: "+comment);
			cnst.address = Global.CSEG.emitValue(comment);
//			Global.CSEG.dump("CONST.inConstant: ");
//			Util.IERR("");
		}
		
//		System.out.println("CONST.inConstant: " + cnst);
		if(Global.traceMode > 3) cnst.print("   ");
//		Util.IERR("");
//		return cnst;
	}
	
	public static void XXXX_inConstant(boolean constDef) {
//	begin ref(RepValue) cnst; ref(IntDescr) v; infix(WORD) tag,count;
//	      range(0:MaxWord) nbyte; range(0:MaxType) type;
//	      InTag(%tag%); TypeLength:=0; type:=intype; nbyte:=TypeLength;
		Scode.inTag();
		Scode.inType();
		if(Scode.accept(Scode.S_REP)) {
			Scode.inNumber();
//	      if NextByte = S_REP
//	      then inputInstr;
//	%+D        count:=InputNumber;
//	%-D        InNumber(%count%);
//	%+C        if count.val=0
//	%+C        then IERR("Illegal 'REP 0'"); count.val:=1 endif;
//	      else count.val:=1 endif;
		}
//	      if type < T_Max then nbyte:=TTAB(type).nbyte endif;
//	%+C   if nbyte=0 then IERR("Illegal Type on Constant") endif;
//	      v:=if DISPL(tag.HI)=none then none else DISPL(tag.HI).elt(tag.LO);
//	      if v=none
//	      then v:=NEWOBJ(K_GlobalVar,size(IntDescr));
//	           v.type:=type; v.adr:=noadr; IntoDisplay(v,tag);
//	%+C   else if v.kind <> K_GlobalVar
//	%+C        then
//	%+CD            edtag(errmsg,tag);
//	%+C             IERR("Display-entry is not defined as a constant:");
//	%+C        endif;
//	%+C        if v.type <> type
//	%+C        then IERR("Type not same as given by CONSTSPEC") endif;
//	      endif;
	      if(constDef) {
	    	  System.out.println("Minut.inConstant'constDef");
//	      then
//	    	  EmitRepValue(v,nbyte);
	    	  Coasm.emitValue();
//	%+S        if NextByte = S_SYSTEM
//	%+S        then inputInstr;
//	%+S             v.adr.kind:=extadr; v.adr.rela.val:=0;
//	%+S             v.adr.smbx:=InExtr('G',DGROUP);
//	%+S %-E         v.adr.sbireg:=0;
//	%+SE            v.adr.sibreg:=NoIBREG;
//	%+S        endif;
	    	  Scode.accept(Scode.S_SYSTEM);
	      }
    	  System.out.println("Minut.inConstant FINISH");
	}

	@Override
	public void print(final String indent) {
		if(value != null) {
			boolean done = false;
			if(value.values instanceof Vector<Value> vector) {
				if(vector instanceof Vector<?> elts) {
					boolean first = true;
					for(Object rVal:elts) {
						if(first) System.out.println(indent + "CONST " + Scode.edTag(tag));
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
			 return "CONST " + Scode.edTag(tag) + " " + address;
		} else if(value != null) {
			 return "CONST " + Scode.edTag(tag) + " " + value;
		} else return "CONSTSPEC " + Scode.edTag(tag);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("CONST.Write: " + this);
		oupt.writeKind(kind);
		oupt.writeShort(ModuleIO.chgType(tag));
		address.write(oupt);
//		quant.write(oupt);
	}

	public static CONST read(AttributeInputStream inpt) throws IOException {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  BEGIN CONST.Read");
		int tag = inpt.readShort();
		tag = InsertStatement.current.chgInType(tag);
		CONST cns = new CONST(Kind.K_Coonst, tag);
		System.out.println("AFTER NEW CONST: "+cns);
		cns.address = MemAddr.read(inpt);
		System.out.println("AFTER NEW MEMADDR: "+cns);
//		Util.IERR("Static Method 'readObject' needs a redefiniton");
		return(cns);
	}


}
