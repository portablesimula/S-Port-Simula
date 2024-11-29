package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.Util;
import bec.virtualMachine.SVM_Instruction;

//%title ***   M e m o r y   A d d r e s s e s   ***
//Define reladr=1,locadr=2,segadr=3,extadr=4,fixadr=5,knladr=6;
//Define adrMax=6;
//
//Record MemAddr; info "TYPE";
//-- NOTE: All variants of MemAddr are treated as Compact and
//--       of the same size. So, test for equality between two
//--       MemAddrs may be performed directly on the type MemAddr.
//--       E.g.  'opr1' and 'opr2'  are equal iff  'opr1=opr2'.
//begin
//      infix(wWORD) rela;       -- Relative byte address
//%+E   range(0:MaxByte) sibreg; -- <ss>2<ireg>3<breg>3
//%+E                            -- ss: Scale Factor 00=1,01=2,10=4,11=8
//%+E                            -- ireg,breg: 000:[EAX]   001:[ECX]
//%+E                            --            010:[EDX]   011:[EBX]
//%+E                            --            100:[ESP]   101:[EBP]
//%+E                            --            110:[ESI]   111:[EDI]
//%+E                            -- E.g.   10 110 011=DS:[EBX]+[ESI]*4
//%+E                            --        11 111 101=SS:[EBP]+[EDI]*8
//%+E                            -- Note:  11 100 100=228 is ruled out,
//%+E                            --        meaning no breg or ireg
//      range(0:adrMax) kind;    -- Variant kind code
//                                       -- reladr: + rela
//      variant range(0:MaxWord) loca;   -- locadr: + rela - loca
//      variant infix(WORD) segmid;      -- segadr: SEG(segid)+rela
//      variant infix(WORD) smbx;        -- extadr: SYMBOL(smbx)+rela
//      variant infix(WORD) fix;         -- fixadr: FIXUP(fix)+rela
//      variant infix(WORD) knlx;        -- knladr: KERNEL(knlx)
//end;

public class MemAddr extends Value {
//	public Segment seg;
	String segID;
	public int ofst;
	
	private MemAddr(String segID,	int ofst) {
		this.segID = segID;
		this.ofst = ofst;
		if(ofst > 9000 || ofst < 0) Util.IERR("");
	}
	
	public MemAddr(Segment seg,	int ofst) {
//		this.seg = seg;
		if(seg != null)	this.segID = seg.ident;
		this.ofst = ofst;
		if(ofst > 9000 || ofst < 0) Util.IERR("");
	}
	
	public MemAddr ofset(int ofst) {
		return new MemAddr(segID, this.ofst + ofst);
	}
	
	public Segment segment() {
		if(segID == null) return null;
		return Segment.lookup(segID);
	}
	
	public void store(Value value) {
		DataSegment dseg = (DataSegment) segment();
		dseg.store(ofst, value);
//		dseg.dump("MemAddr.store: ");
//		Util.IERR("");
	}
	
	public Value load() {
		DataSegment dseg = (DataSegment) segment();
		Value value =  dseg.load(ofst);
//		dseg.dump("MemAddr.load: ");
//		Util.IERR("");	
		return value;
	}
	
	public void execute() {
		ProgramSegment seg = (ProgramSegment) segment();
		SVM_Instruction cur = seg.instructions.get(ofst);
//		System.out.println("MemAddr.execute: " + cur);
		cur.execute();
//		Util.IERR("");
	}
	
	public String toString() {
		String s = (segID == null) ? "RELADR" : segID;
		return s + '[' + ofst + ']';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private MemAddr(AttributeInputStream inpt) throws IOException {
//		String ident = inpt.readString();
		segID = inpt.readString();
//		ofst = inpt.readInt();
		ofst = inpt.readShort();
//		if(ident != null) seg = Segment.lookup(ident);

//		System.out.println("=============================================================================================================== " + this);
		if(ofst > 9000 || ofst < 0) Util.IERR(""+ofst);
//		Util.IERR(""+seg);
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
//		oupt.writeString((seg==null)?null:seg.ident);
		oupt.writeString(segID);
//		oupt.writeInt(ofst);
		oupt.writeShort(ofst);
		if(ofst > 9000 || ofst < 0) Util.IERR("");
//		System.out.println("=============================================================================================================== " + this);
	}

	public static MemAddr read(AttributeInputStream inpt) throws IOException {
		return new MemAddr(inpt);
	}

}
