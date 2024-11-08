package bec.descriptor;

import bec.syntaxClass.programElement.AttributeDefinition;
import bec.util.Global;
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
	
	public static LocDescr ofAttribute(int comnSize) {
		LocDescr loc = new LocDescr();
		loc.rela = comnSize;
		AttributeDefinition attr = new AttributeDefinition(comnSize);
		loc.obj = attr;
		Global.intoDisplay(loc, attr.tag);
		return loc;
	}
	
	public int size() {
		if(obj instanceof AttributeDefinition attr) return attr.size();
		else Util.IERR("");
		return 0;
	}
	
	public String toString() {
		return obj.toString();
	}
}
