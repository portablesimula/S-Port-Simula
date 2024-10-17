package bec.syntaxClass.value;

import java.util.Vector;

import bec.util.Scode;

public class RecordValue extends Value {
	int tag;
	Vector<AttributeValue> attrValues;
	
	public RecordValue() {
		attrValues = new Vector<AttributeValue>();
		parse();
	}

	/**
	 * record_value
	 * 		::= c-record structured_type
	 * 			<attribute_value>+ endrecord
	 * 
	 * 		structured_type ::= record_tag:tag
	 * 
	 * 		attribute_value
	 * 			::= attr attribute:tag type repetition_value
	 * 
	 * End-Condition: Scode'nextByte = First byte after ENDRECORD
	 */
	public void parse() {
		tag = Scode.inTag();
		while(Scode.nextByte() == Scode.S_ATTR) {
			Scode.inputInstr();
			attrValues.add(new AttributeValue());
		}
//		if(Scode.inputTrace > 3) printTree(0);
//		System.out.println("MainProgram.parse: Curinstr = "+Scode.edInstr(Scode.curinstr));
//		System.out.println("MainProgram.parse: Nxtinstr = "+Scode.edInstr(Scode.nextByte()));
		Scode.expect(Scode.S_ENDRECORD);
//		Scode.checkEqual(Scode.S_ENDRECORD);
		
		if(Scode.inputTrace > 3) printTree(0);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, "C-RECORD " + Scode.edTag(tag));
		for(AttributeValue value:attrValues) {
			sLIST(indent, "   ATTR " + value);
		}
		sLIST(indent, "ENDRECORD");
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("C-RECORD " + Scode.edTag(tag));
		for(AttributeValue value:attrValues) {
			sb.append(" ATTR " + value);
		}
		sb.append(" ENDRECORD");
		return sb.toString();
	}
	
}
