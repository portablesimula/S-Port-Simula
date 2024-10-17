package bec.syntaxClass.programElement.routine;

import bec.util.ResolvedType;
import bec.util.Scode;

public class EXPORT {
	int tag;
	ResolvedType type;

	/**
	 * export parm:newtag resolved_type
	 */
	public EXPORT() {
		tag = Scode.inTag();
		type = new ResolvedType();
//		System.out.println("NEW EXPORT: " + this);
	}
	
	public String toString() {
		return "EXPORT " + Scode.edTag(tag) + " " + type;
	}
}
