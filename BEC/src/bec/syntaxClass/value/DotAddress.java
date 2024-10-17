package bec.syntaxClass.value;

import java.util.Vector;

import bec.util.Scode;
import bec.util.Util;

public class DotAddress extends Value {
	Vector<Integer> attrTags;
	int globalOrConstTag;
	int terminator;
	
	public DotAddress() {
		attrTags = new Vector<Integer>();
		parse();
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
			attrTags.add(Scode.inTag());
			Scode.inputInstr();
		} while (Scode.curinstr == Scode.S_C_DOT);

           terminator = Scode.curinstr;
           if(terminator == Scode.S_C_AADDR || terminator == Scode.S_C_GADDR) {
        	   globalOrConstTag = Scode.inTag();
           }
           else Util.IERR("Illegal termination of C-DOT value");
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Integer tag:attrTags) {
			sb.append("C-DOT ").append(tag);
		}
		sb.append(" " + Scode.edInstr(terminator) + " " + Scode.edTag(globalOrConstTag));
		return(sb.toString());
	}
	

}
