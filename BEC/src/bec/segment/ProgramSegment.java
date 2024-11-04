package bec.segment;

import java.util.Vector;

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
	
	public void dump() {
		System.out.println("==================== " + ident + " DUMP ====================");
		for(int i=0;i<instructions.size();i++) {
			String line = "" + i + ": ";
			while(line.length() < 8) line = " " +line;
			String value = ""+instructions.get(i);
			while(value.length() < 25) value = value + ' ';
			System.out.println(line + value + "   " + comment.get(i));
		}
		System.out.println("==================== " + ident + " END  ====================");
	}

}
