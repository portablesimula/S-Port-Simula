package bec.segment;

import java.io.IOException;
import java.util.HashMap;

import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.util.Util;

public class Segment { // extends Descriptor {
	public String ident;
	int segmentKind; // K_SEG_DATA, K_SEG_CONST, K_SEG_CODE

	static HashMap<String, Segment> SEGMAP = new HashMap<String, Segment>();

	public Segment(String ident, int segmentKind) {
		SEGMAP.put(ident, this);
		this.ident = ident.toUpperCase();
		this.segmentKind = segmentKind;
	}

	public static Segment lookup(String ident) {
		return SEGMAP.get(ident);
	}
	
	public static void writeSegments(AttributeOutputStream oupt) {
		try {
			for(Segment seg:SEGMAP.values()) {
				seg.write(oupt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Util.IERR("");
	}
	
	public String toString() {
		return Kind.edKind(segmentKind) + " " + ident;
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

//	public static Descriptor read(AttributeInputStream inpt, int kind) throws IOException {
//		Util.IERR("Static Method 'readObject' needs a redefiniton");
//		return(null);
//	}


}
