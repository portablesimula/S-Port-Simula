package bec.value;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class RecordValue extends Value {
	int tag;
	public Vector<AttributeValue> attrValues;
	
	private RecordValue() {
		attrValues = new Vector<AttributeValue>();
//		parse();
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
	public static RecordValue ofScode() {
		RecordValue rec = new RecordValue();
		rec.tag = Scode.inTag();
		while(Scode.nextByte() == Scode.S_ATTR) {
			Scode.inputInstr();
			rec.attrValues.add(AttributeValue.ofScode());
		}
//		if(Scode.inputTrace > 3) printTree(0);
//		System.out.println("MainProgram.parse: Curinstr = "+Scode.edInstr(Scode.curinstr));
//		System.out.println("MainProgram.parse: Nxtinstr = "+Scode.edInstr(Scode.nextByte()));
		Scode.expect(Scode.S_ENDRECORD);
//		Scode.checkEqual(Scode.S_ENDRECORD);
		
//		if(Scode.inputTrace > 3) printTree(0);
		return rec;
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + "C-RECORD " + Scode.edTag(tag));
		for(AttributeValue value:attrValues) {
//			System.out.println(indent + "   ATTR " + value);
			System.out.println(indent + "   "+value);
		}
		System.out.println(indent + "ENDRECORD");
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

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RecordValue(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag();
		attrValues = new Vector<AttributeValue>();
		int kind = inpt.readKind();
		while(kind != Scode.S_ENDRECORD) {
			AttributeValue value = AttributeValue.read(inpt);
			attrValues.add(value);
			kind = inpt.readKind();
		}
		System.out.println("NEW RECORD VALUE: ");
//		printTree(2);
//		Util.IERR("");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_RECORD);
		oupt.writeTag(tag);
		for(AttributeValue value:attrValues) value.write(oupt);
		oupt.writeInstr(Scode.S_ENDRECORD);
		
//		this.printTree(2);
//		Util.IERR("");
	}

	public static RecordValue read(AttributeInputStream inpt) throws IOException {
		return new RecordValue(inpt);
	}

	
}
