package bec.segment;

import java.io.IOException;
import java.util.Vector;

import PREV.syntaxClass.value.Value;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;
import bec.virtualMachine.SVM_Instruction;

public class ProgramSegment extends Segment {
	public Vector<SVM_Instruction> instructions;
	Vector<String> comment;

	public ProgramSegment(String ident, int segmentKind) {
		super(ident, segmentKind);
		SEGMAP.put(ident, this);
		this.ident = ident.toUpperCase();
		this.segmentKind = segmentKind;
		instructions = new Vector<SVM_Instruction>();
		comment = new Vector<String>();
	}
	
	public MemAddr nextAddress() {
		return new MemAddr(this,instructions.size());
	}
	
	public void emit(SVM_Instruction value,String cmnt) {
		instructions.add(value);
		comment.add(cmnt);
	}
	
	public void dump(String title) {
		System.out.println("========================== " + title + ident + " DUMP ==========================");
		for(int i=0;i<instructions.size();i++) {
			String line = "" + i + ": ";
			while(line.length() < 8) line = " " +line;
			String value = ""+instructions.get(i);
			while(value.length() < 50) value = value + ' ';
			System.out.println(line + value + "   " + comment.get(i));
		}
		System.out.println("========================== " + title + ident + " END  ==========================");
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private ProgramSegment(String ident, int segmentKind, AttributeInputStream inpt) throws IOException {
//		this.ident = ident;
//		this.segmentKind = segmentKind;
		super(ident, segmentKind);
		SEGMAP.put(ident, this);
		instructions = new Vector<SVM_Instruction>();
		comment = new Vector<String>();
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			comment.add(inpt.readString());
			instructions.add(SVM_Instruction.readObject(inpt));
		}
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_BSEG);
		oupt.writeKind(segmentKind);
		oupt.writeString(ident);
		oupt.writeShort(instructions.size());
		for(int i=0;i<instructions.size();i++) {
			oupt.writeString(comment.get(i));
			SVM_Instruction val = instructions.get(i);
			if(val == null)
				 oupt.writeInstr(Scode.S_NULL);
			else val.write(oupt);
		}
	}

	public static ProgramSegment readObject(AttributeInputStream inpt) throws IOException {
		int segmentKind = inpt.readKind();
		String ident = inpt.readString();
		System.out.println("DataSegment.readObject: ident="+ident+", segmentKind="+segmentKind);
		ProgramSegment seg = new ProgramSegment(ident, segmentKind, inpt);
//		seg.dump();
//		Util.IERR("");
		return seg;
	}
	

}
