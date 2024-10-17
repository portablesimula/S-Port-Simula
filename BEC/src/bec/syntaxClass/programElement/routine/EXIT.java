package bec.syntaxClass.programElement.routine;

import bec.util.Scode;

public class EXIT {
	int tag;

	/**
	 * exit return:newtag
	 */
	public EXIT() {
		tag = Scode.inTag();
//		System.out.println("NEW EXIT: " + this);
	}
	
	public String toString() {
		return "EXIT " + Scode.edTag(tag);
	}
}
