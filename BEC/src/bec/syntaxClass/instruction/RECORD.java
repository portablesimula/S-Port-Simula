package bec.syntaxClass.instruction;

import java.util.Vector;

import bec.syntaxClass.programElement.AttributeDefinition;
import bec.util.Scode;
import bec.util.Util;

public class RECORD extends Instruction {
	int tag;
	String info;
	int prefixTag;
	Vector<AttributeDefinition> attributes;
	Vector<AlternatePart> alternateParts;
	
	public RECORD() {
		parse();
	}
	
//	%title ***   I n p u t   R e c o r d   ***
	/**
	 *	record_descriptor
     *		::= record record_tag:newtag <record_info>?
     *			<prefix_part>? common_part
     *			<alternate_part>*
     *			endrecord 
     *
	 *	record_info	::= info "TYPE" | info "DYNAMIC"
	 *	prefix_part	::= prefix resolved_structure
	 *	common_part	::= <attribute_definition>*
	 *	alternate_part ::= alt <attribute_definition>*
	 *		attribute_definition ::= attr attr:newtag quantity_descriptor
	 *		resolved_structure ::= structured_type < fixrep count:ordinal >?
	 *			structured_type ::= record_tag:tag
	 *
	 *		quantity_descriptor ::= resolved_type < Rep count:number >?
	 * 
	 */
	public void parse() {
		Vector<AttributeDefinition> curAttrs;
//		Vector<AlternatePart> alternateParts = null;

		tag = Scode.inTag();
		if(Scode.accept(Scode.S_INFO))   info = Scode.inString();
		if(Scode.accept(Scode.S_PREFIX)) prefixTag = Scode.inTag();
		curAttrs = attributes;
		do {
			if(attributes == null) {
				curAttrs = attributes = new Vector<AttributeDefinition>();
			} else {
				if(alternateParts == null) alternateParts = new Vector<AlternatePart>();
				AlternatePart alt = new AlternatePart();
				alternateParts.add(alt);
				curAttrs = alt.attributes;
			}
			while(Scode.accept(Scode.S_ATTR)) {
//				Scode.inTag(); Scode.inType();
//				if(Scode.accept(Scode.S_REP)) Scode.inNumber();
				curAttrs.add(new AttributeDefinition());
			}
		} while(Scode.accept(Scode.S_ALT));
		Scode.expect(Scode.S_ENDRECORD);
		
		if(Scode.inputTrace > 3) printTree(2);
	}

	class AlternatePart {
		Vector<AttributeDefinition> attributes = new Vector<AttributeDefinition>();	
		public void printTree(final int indent) {
			boolean first = true;
			for(AttributeDefinition attr:attributes) {
				if(first) { sLIST(indent, "ALT " + attr); first = false; }
				else sLIST(indent + 1, attr.toString());
			}
			
		}
	}
	
	@Override
	public void printTree(final int indent) {
		String head = "RECORD " + Scode.edTag(tag);
		if(info != null)  head = head + " INFO " + info;
		if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
		sLIST(indent, head);
		for(AttributeDefinition attr:attributes) {
			sLIST(indent + 1, attr.toString());
		}
		if(alternateParts != null) {
//			Util.IERR("HER MANGLER ALT-PARTENE");
			for(AlternatePart alt:alternateParts) {
				alt.printTree(indent + 1);
			}
		}
		sLIST(indent, "ENDRECORD");
		
	}
	
	public String toString() {
		String head = "RECORD " + Scode.edTag(tag);
		if(info != null)  head = head + " INFO " + info;
		if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
		return head + " ...";
	}

}
