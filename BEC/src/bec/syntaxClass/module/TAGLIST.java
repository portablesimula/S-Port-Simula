package bec.syntaxClass.module;

import java.util.Vector;

import bec.syntaxClass.programElement.ProgramElement;
import bec.util.Scode;

public class TAGLIST extends ProgramElement {
	Vector<TAG> tagList;

	public TAGLIST() {
		tagList = new Vector<TAG>();
		parse();
	}

	public void parse() {
		while(Scode.curinstr == Scode.S_TAG) {
			tagList.add(new TAG());
			Scode.inputInstr();
		}
		
		if(Scode.inputTrace > 3) printTree(2);
	}

	@Override
	public void printTree(final int indent) {
		for(TAG tag:tagList)
			sLIST(indent, tag.toString());
	}

	public String toString() {
		return "TAGLIST ...";
	}
	

}
