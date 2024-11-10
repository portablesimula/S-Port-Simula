package bec.descriptor;

import java.io.IOException;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.programElement.AttributeDefinition;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.ModuleIO;
import bec.util.QuantityDescriptor;
import bec.util.Util;

//Record LocDescr:Descriptor;	-- K_Attribute  Offset = rela    SIZE = 8 bytes
//								-- K_Parameter  Address = [BP] + rela
//								-- K_Export     Address = [BP] + rela
//								-- K_LocalVar   Address = [BP] - rela
//begin range(0:MaxWord) rela;
//	infix(WORD) nextag;        -- NOTE: Only used for Parameters
//	-- NOTE: And only in 'inprofile'
//	range(0:MaxWord) UNUSED;   -- To 4-byte align record in all cases
//end;  
public class LocDescr extends Descriptor {
	int rela;
	int nextag;
	
//	AttributeDefinition attr;
	Object obj;
	
	public LocDescr(int kind, int tag) {
		super(kind, tag);
	}
	
	public static LocDescr ofAttribute(int comnSize) {
		AttributeDefinition attr = new AttributeDefinition(comnSize);
		LocDescr loc = new LocDescr(Kind.K_Attribute,attr.tag);
		loc.rela = comnSize;
		loc.obj = attr;
//		Global.intoDisplay(loc, attr.tag);
		return loc;
	}
	
	public static LocDescr ofParameter(int tag, QuantityDescriptor quant, int rela) {
		AttributeDefinition attr = new AttributeDefinition(tag, quant, rela);
		LocDescr loc = new LocDescr(Kind.K_Parameter, attr.tag);
		loc.rela = rela;
		loc.obj = attr;
//		Global.intoDisplay(loc, attr.tag);
		return loc;
	}
	
	public int size() {
		if(obj instanceof AttributeDefinition attr) return attr.size();
		else Util.IERR("");
		return 0;
	}
	
	public String toString() {
		if(obj != null) return obj.toString();
		return "LocDescr rela=" + rela;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
//        when K_Attribute,K_Parameter,K_Export:
//%+D            if ModuleTrace <> 0
//%+D            then if d.kind=K_Attribute then S:="Attribute: "
//%+D              elsif d.kind=K_Parameter then S:="Parameter: "
//%+D              else S:="Export: " endif; outstring(S); Print(d);
//%+D            endif;
//             -- Modify: d qua IntDescr.type
//             ld.kind:=d.kind; ld.type:=ChgType(d.type);
//             ld.rela:=d qua LocDescr.rela; ld.nextag.val:=0;
//             buf.nchr:=Size2Word(size(LocDescr))
//%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-4") endif;
//             buf.chradr:=@ld; EnvOutBytes(modoupt,buf);
//             if status<>0 then FILERR(modoupt,"Wdescr-8") endif;
		oupt.writeKind(kind);
		oupt.writeShort(ModuleIO.chgType(tag));
		oupt.writeShort(rela);
//		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

	public static SyntaxClass read(AttributeInputStream inpt) throws IOException {
		Util.IERR("Static Method 'readObject' needs a redefiniton");
		return(null);
	}


}
