package bec.syntaxClass;

import bec.util.Scode;
import bec.util.Util;

public class SyntaxClass {
	protected int lineNumber;

	public SyntaxClass() {
		lineNumber = Scode.curline;
	}
	/**
	 * Utility print method.
	 * 
	 * @param indent number of spaces leading the line
	 */
	public void print(final int indent) {
		System.out.println(edLead(indent, lineNumber) + this);
	}

	/**
	 * Utility print syntax tree method.
	 * 
	 * @param indent number of spaces leading the lines
	 */
	public void printTree(final int indent) {
		Util.IERR("Method printTree need a redefinition in "+this.getClass().getSimpleName());
	}

	
	public void sLIST(final int indent, final int lineNumber, final String code) {
		String line = edLead(indent, lineNumber) + code;
		System.out.println(line);
	}
	
	public void sLIST(final int indent, final String code) {
		String line = edLead(indent, lineNumber) + code;
		System.out.println(line);
	}

	/**
	 * Utility: Returns a number of blanks.
	 * 
	 * @param indent the number of blanks requested
	 * @return a number of blanks.
	 */
	public String edLead(final int indent, final int lineNumber) {
		String s = "Line " + lineNumber + ": ";
		
		int i = indent;
		while ((i--) > 0)
			s = s + "    ";
		return (s);
	}

}
