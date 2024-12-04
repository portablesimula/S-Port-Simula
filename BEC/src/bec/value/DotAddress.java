package bec.value;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;

public class DotAddress extends Value {
	Vector<Tag> attrTags;
	int globalOrConstTag;
	int terminator;
	
	public DotAddress() {
		attrTags = new Vector<Tag>();
		parse();
		this.type = (terminator == Scode.S_C_AADDR) ? Type.T_AADDR : Type.T_GADDR;
	}

	/**
	 * 	attribute_address ::= < c-dot attribute:tag >* c-aaddr attribute:tag
	 * 		::= anone
	 * 
	 * 	general_address
	 * 		::= < c-dot attr:tag >* c-gaddr global_or_const:tag
	 * 		::= gnone
	 */
	public void parse() {
		do {
			attrTags.add(Tag.ofScode());
			Scode.inputInstr();
		} while (Scode.curinstr == Scode.S_C_DOT);

           terminator = Scode.curinstr;
           if(terminator == Scode.S_C_AADDR || terminator == Scode.S_C_GADDR) {
        	   globalOrConstTag = Scode.ofScode();
           }
           else Util.IERR("Illegal termination of C-DOT value");
	}

//	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Tag tag:attrTags) {
			sb.append("C-DOT ").append(tag);
		}
		sb.append(" " + Scode.edInstr(terminator) + " " + Scode.edTag(globalOrConstTag));
		return(sb.toString());
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private DotAddress(AttributeInputStream inpt) throws IOException {
		int n = inpt.readShort();
		attrTags = new Vector<Tag>();
		for(int i=0;i<n;i++) {
			Tag tag = Tag.read(inpt);
			attrTags.add(tag);
		}
		globalOrConstTag = inpt.readTag();
		terminator = inpt.readKind();
		this.type = (terminator == Scode.S_C_AADDR) ? Type.T_AADDR : Type.T_GADDR;
//		System.out.println("NEW IMPORT: " + this);
	}

//	Vector<Integer> attrTags;
//	int globalOrConstTag;
//	int terminator;
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_DOT);
		oupt.writeShort(attrTags.size());
		for(Tag tag:attrTags) tag.write(oupt);
		oupt.writeTag(globalOrConstTag);
		oupt.writeKind(terminator);
	}

	public static DotAddress read(AttributeInputStream inpt) throws IOException {
		return new DotAddress(inpt);
	}
	

}
