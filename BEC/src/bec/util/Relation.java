package bec.util;

public class Relation {
	int relation;
	
	public Relation() {
		parse();
	}

	/**
	 * relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	 */
	public void parse() {
		Scode.inputInstr();
		relation = Scode.curinstr;
		if(relation == Scode.S_LT) ; // OK
		else if(relation == Scode.S_LE) ; // OK
		else if(relation == Scode.S_EQ) ; // OK
		else if(relation == Scode.S_GE) ; // OK
		else if(relation == Scode.S_GT) ; // OK
		else if(relation == Scode.S_NE) ; // OK
		else Util.IERR("Illegal Relation: " + Scode.edInstr(relation));
	}
	
	public String toString() {
		return Scode.edInstr(relation);
	}

}
