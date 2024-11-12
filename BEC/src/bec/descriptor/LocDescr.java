package bec.descriptor;

import java.io.IOException;

import PREV.syntaxClass.programElement.AttributeDefinition;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.InsertStatement;
import bec.ModuleIO;
import bec.util.Global;
import bec.util.PREV_QuantityDescriptor;
import bec.util.ResolvedType;
import bec.util.Scode;
import bec.util.Util;

//Record LocDescr:Descriptor;	-- K_Attribute	Offset = rela    SIZE = 8 bytes
//								-- K_Import		Address = [BP] + rela
//								-- K_Export		Address = [BP] + rela
//								-- K_LocalVar	Address = [BP] - rela
//begin range(0:MaxWord) rela;
//	infix(WORD) nextag;        -- NOTE: Only used for Parameters
//	-- NOTE: And only in 'inprofile'
//	range(0:MaxWord) UNUSED;   -- To 4-byte align record in all cases
//end;  
public class LocDescr extends Descriptor {
	int rela;
	int size;
	int nextag; // NOTE: Only used for Parameters
	
//	AttributeDefinition attr;
	Object obj;
	
	private LocDescr(int kind, int tag) {
		super(kind, tag);
	}
	
	public static LocDescr ofAttribute(int comnSize) {
//		AttributeDefinition attr = new AttributeDefinition(comnSize);
		int tag = Scode.inTag();
//		System.out.println("NEW AttributeDefinition: tag="+tag);
//		quant = new PREV_QuantityDescriptor();
		ResolvedType type = new ResolvedType();
		int repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
//		System.out.println("NEW AttributeDefinition: " + this);
		
		LocDescr loc = new LocDescr(Kind.K_Attribute,tag);
		loc.rela = comnSize;
		loc.size = type.size();
//		loc.obj = attr;
//		Global.intoDisplay(loc, attr.tag);
		return loc;
	}
	
//	public static LocDescr ofParameter(int tag, QuantityDescriptor quant, int rela) {
	public static LocDescr ofParameter(int tag, ResolvedType type, int rela) {
////		AttributeDefinition attr = new AttributeDefinition(tag, quant, rela);
//		LocDescr loc = new LocDescr(Kind.K_Import, attr.tag);
//		loc.rela = rela;
////		loc.obj = attr;
////		Global.intoDisplay(loc, attr.tag);
		Util.IERR("NOT IMPL");
		return null;
	}
	
	public int size() {
//		if(obj instanceof AttributeDefinition attr) return attr.size();
//		else Util.IERR("");
		return size;
	}
	
	public void print(final String indent) {
		System.out.println(indent + this);
	}
	
	public String toString() {
		if(obj != null) return obj.toString();
		return "LocDescr: " + Kind.edKind(kind) + " rela=" + rela;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
//        when K_Attribute,K_Import,K_Export:
//%+D            if ModuleTrace <> 0
//%+D            then if d.kind=K_Attribute then S:="Attribute: "
//%+D              elsif d.kind=K_Import then S:="Parameter: "
//%+D              else S:="Export: " endif; outstring(S); Print(d);
//%+D            endif;
//             -- Modify: d qua IntDescr.type
//             ld.kind:=d.kind; ld.type:=ChgType(d.type);
//             ld.rela:=d qua LocDescr.rela; ld.nextag.val:=0;
//             buf.nchr:=Size2Word(size(LocDescr))
//%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-4") endif;
//             buf.chradr:=@ld; EnvOutBytes(modoupt,buf);
//             if status<>0 then FILERR(modoupt,"Wdescr-8") endif;
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("LocDescr.Write: " + this);
		oupt.writeKind(kind);
		oupt.writeShort(ModuleIO.chgType(tag));
		oupt.writeShort(rela);
//		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

	public static LocDescr read(AttributeInputStream inpt, int kind) throws IOException {
		int tag = inpt.readShort();
		tag = InsertStatement.current.chgInType(tag);
		LocDescr loc = new LocDescr(kind, tag);
		loc.rela = inpt.readShort();
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("LocDescr.Read: " + loc);
		loc.print("   ");
//		Util.IERR("");
		return loc;
	}


}
