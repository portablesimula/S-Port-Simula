package bec.util;

import removed.java.Scomn;

public class ResolvedType {
	int tag;
	Range range;
	Fixrep fixrep;
	
	public ResolvedType() {
		parse();
	}
	
	/**
	 *	 type ::= structured_type | simple_type
	 * 
	 *	 simple_type ::= BOOL | CHAR | INT | REAL | LREAL | SIZE | OADDR | AADDR | GADDR | PADDR | RADDR
	 * 
	 *	 structured_type ::= record_tag:tag
	 *
	 *	 resolved_type
	 *		::= resolved_structure | simple_type
	 *		::= INT range lower:number upper:number
	 *		::= SINT
	 *
	 *	 resolved_structure ::= structured_type < fixrep count:ordinal >?
	 */
	public void parse() {
		tag = Scode.inTag();
		if(tag <= Scomn.TAG_SIZE) {
			if(Scode.accept(Scode.S_RANGE)) range = new Range();
		} else {
			if(Scode.accept(Scode.S_FIXREP)) fixrep = new Fixrep();
		}
	}
	
	public String toString() {
		if(range != null) return "INT " + range;
		if(fixrep != null) return Scode.edTag(tag) + " " + fixrep;
		return Scode.edTag(tag);
	}

}
