package bec.statement;

import bec.util.Util;

public class SkipifStatement { // extends ProgramElement {
	
	public SkipifStatement() {
		parse();
	}

	public void parse() {
		Util.IERR("NOT IMPLEMENTED");
		// SkipifConstruction(true);
	}
	
	public String toString() {
		return "SKIPIF "; // + lab;
	}
	

}
