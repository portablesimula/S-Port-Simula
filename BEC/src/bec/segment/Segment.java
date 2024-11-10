package bec.segment;

import java.util.HashMap;

import PREV.syntaxClass.SyntaxClass;

public class Segment extends SyntaxClass {
	public String ident;
	int segmentKind; // SEG_DATA, SEG_CONST, SEG_CODE

	public final static int SEG_DATA  = 1;
	public final static int SEG_CONST = 2;
	public final static int SEG_CODE  = 3;

	static HashMap<String, Segment> SEGMAP = new HashMap<String, Segment>();

	public Segment(String ident, int segmentKind) {
		SEGMAP.put(ident, this);
		this.ident = ident.toUpperCase();
		this.segmentKind = segmentKind;
	}

	public static Segment lookup(String ident) {
		return SEGMAP.get(ident);
	}

}
