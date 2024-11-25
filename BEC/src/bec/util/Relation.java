package bec.util;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;

public class Relation {
	int relation;
	
	private Relation() {
//		parse();
	}

	/**
	 * relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	 */
	public static Relation ofScode() {
		Scode.inputInstr();
		Relation rel = new Relation();
		rel.relation = Scode.curinstr;
		if(rel.relation == Scode.S_LT) ; // OK
		else if(rel.relation == Scode.S_LE) ; // OK
		else if(rel.relation == Scode.S_EQ) ; // OK
		else if(rel.relation == Scode.S_GE) ; // OK
		else if(rel.relation == Scode.S_GT) ; // OK
		else if(rel.relation == Scode.S_NE) ; // OK
		else Util.IERR("Illegal Relation: " + rel.relation);
		return rel;
	}
	
	public String toString() {
		return Scode.edInstr(relation);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private Relation(AttributeInputStream inpt) throws IOException {
		relation = inpt.readShort();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(relation);
	}

	public static Relation read(AttributeInputStream inpt) throws IOException {
		return new Relation(inpt);
	}

}
