package bec.syntaxClass.programElement.routine;

import bec.syntaxClass.programElement.ProgramElement;
import bec.util.Scode;

public class ROUTINESPEC extends ProgramElement {
	int bodyTag;
	int profileTag;
	
	public ROUTINESPEC() {
		parse();
	}

	/**
	 * routine_specification ::= routinespec body:newtag profile:tag
	 */
	public void parse() {
		bodyTag = Scode.inTag();
		profileTag = Scode.inTag();
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "ROUTINESPEC " + Scode.edTag(bodyTag) + " " + Scode.edTag(profileTag);
	}
	

}
