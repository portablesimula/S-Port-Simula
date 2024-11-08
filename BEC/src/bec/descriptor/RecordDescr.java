package bec.descriptor;

import java.util.Vector;

import bec.compileTimeStack.DataType;
import bec.segment.DataSegment;
import bec.syntaxClass.SyntaxClass;
import bec.syntaxClass.programElement.AttributeDefinition;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

//Record RecordDescr:Descriptor;   -- K_RecordDescr                 SIZE = 8 bytes
//begin range(0:MaxWord) nbyte;    -- Record size information
//      range(0:MaxWord) nbrep;    -- Size of rep(0) attribute
//      infix(WORD) pntmap;        -- Only used by TypeRecord
//end;
public class RecordDescr extends Descriptor {
	int nbyte;    // Record size information
	int nbrep;    // Size of rep(0) attribute
	Vector<Integer> pntmap; // Only used by TypeRecord

	RECORD RECORD_OBJECT;
	
	public RecordDescr() {
		RECORD_OBJECT = new RECORD();
		Global.intoDisplay(this, RECORD_OBJECT.tag);
		nbyte = RECORD_OBJECT.size();
		Global.dumpDISPL("NEW RecordDescr: ");
	}

	public String toString() {
		return RECORD_OBJECT.toString();
	}
	
	public class RECORD { // extends PREV_Instruction {
		public int tag;
		String info;
		int prefixTag;
//		Vector<AttributeDefinition> attributes;
		Vector<LocDescr> attributes;
		Vector<AlternatePart> alternateParts;
		
		Vector<Integer> pntmap; // Only used by TypeRecord

		
		public RECORD() {
			parse();
		}
		
//		%title ***   I n p u t   R e c o r d   ***
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
		private void parse() {
//			Vector<AttributeDefinition> curAttrs;
//			Vector<AlternatePart> alternateParts = null;

			tag = Scode.inTag();	
//			Global.Display.set(tag, this);
			int comnSize = 0;
			
			if(Scode.accept(Scode.S_INFO))   info = Scode.inString();
			if(Scode.accept(Scode.S_PREFIX)) {
				prefixTag = Scode.inTag();
//				RECORD prefix = (RECORD) Global.getMeaning(prefixTag);
				RECORD prefix = getPrefix(prefixTag);
				comnSize = prefix.size();
			}
			
//			attributes = new Vector<AttributeDefinition>();
			attributes = new Vector<LocDescr>();
			while(Scode.accept(Scode.S_ATTR)) {
//				AttributeDefinition attr = new AttributeDefinition(comnSize);
				LocDescr attr = LocDescr.ofAttribute(comnSize);
				comnSize = comnSize + attr.size();
				attributes.add(attr);
			}
			int maxAlt = comnSize;
			while(Scode.accept(Scode.S_ALT)) {
				if(alternateParts == null) alternateParts = new Vector<AlternatePart>();
				AlternatePart alt = new AlternatePart();
				alternateParts.add(alt);
				int altSize = comnSize;
				while(Scode.accept(Scode.S_ATTR)) {
					AttributeDefinition attr = new AttributeDefinition(altSize);
					altSize = altSize + attr.size();
					alt.attributes.add(attr);
				}
				maxAlt = Math.max(maxAlt, altSize);
			}
			printTree("   ");
			if(maxAlt != size()) Util.IERR("maxAlt="+maxAlt+", size="+size());
			
//			curAttrs = attributes;
//			do {
//				if(attributes == null) {
//					curAttrs = attributes = new Vector<AttributeDefinition>();
//				} else {
//					if(alternateParts == null) alternateParts = new Vector<AlternatePart>();
//					AlternatePart alt = new AlternatePart();
//					alternateParts.add(alt);
//					curAttrs = alt.attributes;
//				}
//				while(Scode.accept(Scode.S_ATTR)) {
////					Scode.inTag(); Scode.inType();
////					if(Scode.accept(Scode.S_REP)) Scode.inNumber();
//					AttributeDefinition attr = new AttributeDefinition(size);
//					size = size + attr.size();
//					curAttrs.add(attr);
//				}
//			} while(Scode.accept(Scode.S_ALT));

//			if(Scode.inputTrace > 3) printTree(2);
			Scode.expect(Scode.S_ENDRECORD);
			
			if("TYPE".equalsIgnoreCase(info)) {
				buildPointerMap();
				printTree("   ");
				DataType.newRecType(tag, size());
//				Util.IERR("");
			}
		}
		
		private void buildPointerMap() {
			
		}
		
		private RECORD getPrefix(int prefixTag) {
			RecordDescr prefix = (RecordDescr) Global.DISPL.get(prefixTag);
			return prefix.RECORD_OBJECT;
		}
		
		public int size() {
			int np = 0;
			if(prefixTag != 0) {
//				RECORD prefix = (RECORD) Global.Display.get(prefixTag);
				RECORD prefix = getPrefix(prefixTag);
				np = prefix.size();
			}
			int n = 0;
//			for(AttributeDefinition attr:attributes) {
			for(LocDescr attr:attributes) {
				n = n + attr.size();
			}
			int maxAlt = 0;
			if(alternateParts != null) {
				for(AlternatePart alt:alternateParts) {
					int altSize = alt.size();
					maxAlt = Math.max(altSize, maxAlt);
				}
			}
			int tot = np + n + maxAlt;
//			System.out.println("RECORD.size: "+Scode.edTag(tag)+" "+tot+"    prefix="+np+", n="+n+", maxAlt="+maxAlt);
//			if(tot > 50) Util.IERR("");
			return tot;
		}

		public void emitDefaultValues(DataSegment dseg, int repCount, String comment) {
//			System.out.println("RECORD.emitDefaultValue: " + dseg + "  " + this);
//			printTree(2);
			
			int rep = (repCount > 0) ? repCount : 1;
			for(int i=0;i<rep;i++) {
				for(int j=0;j<size();j++)
					dseg.emit(null, comment);
			}
//			Global.DSEG.dump();
//			Util.IERR("");
		}
		
//		@Override
		public void printTree(final String indent) {
			String head = "RECORD " + Scode.edTag(tag);
			if(info != null)  head = head + " INFO " + info;
			if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
//			sLIST(indent, head);
			System.out.println(indent + head);
//			for(AttributeDefinition attr:attributes) {
			for(LocDescr attr:attributes) {
//				sLIST(indent + 1, attr.toString());
				System.out.println(indent + "   " + attr.toString());
			}
			if(alternateParts != null) {
				for(AlternatePart alt:alternateParts) {
					alt.printTree(indent + "   ");
				}
			}
//			sLIST(indent, "ENDRECORD");
			System.out.println("   " + "ENDRECORD");
		}
		
		public String toString() {
			String head = "RECORD " + Scode.edTag(tag);
			if(info != null)  head = head + " INFO " + info;
			if(prefixTag > 0) head = head + " PREFIX " + Scode.edTag(prefixTag);
			return head + " ...";
		}

	}
	
	/**
	 *	alternate_part ::= alt <attribute_definition>*
	 *		attribute_definition ::= attr attr:newtag quantity_descriptor
	 *		resolved_structure ::= structured_type < fixrep count:ordinal >?
	 *			structured_type ::= record_tag:tag
	 *
	 *		quantity_descriptor ::= resolved_type < Rep count:number >?
	 * 
	 */
	class AlternatePart extends SyntaxClass {
		Vector<AttributeDefinition> attributes;	
		
		public AlternatePart() {
			attributes = new Vector<AttributeDefinition>();
		}
	
		public int size() {
			int n = 0;
			for(AttributeDefinition attr:attributes) {
				n = n + attr.size();
			}
			return n;
		}
		
		public void printTree(final String indent) {
			boolean first = true;
			for(AttributeDefinition attr:attributes) {
				if(first) {
//					sLIST(indent, "ALT " + attr);
					System.out.println(indent + "ALT " + attr);
					first = false;
				}
//				else sLIST(indent + 1, attr.toString());
				else System.out.println(indent + "  " + attr);

			}
		}
	}

}
