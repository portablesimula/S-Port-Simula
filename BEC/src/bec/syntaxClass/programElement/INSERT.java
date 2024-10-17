package bec.syntaxClass.programElement;

import bec.util.Global;
import bec.util.Scode;

public class INSERT extends ProgramElement {
	boolean sysMode;
    String modid;
    String check;
    String extid;
//    InTag(%bias%); InTag(%limit%);
    int biasTag;
    int limitTag;
	
	public INSERT(boolean sysMode) {
		this.sysMode = sysMode;
		parse();
	}

	public void parse() {
		modid = Scode.inString();
	    check = Scode.inString();
	    extid = Scode.inString();
	    biasTag = Scode.inTag();
	    limitTag = Scode.inTag();
	    Global.nTags = limitTag;
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		String res = (sysMode)?"SYS" : "";
		return res + "INSERT " + modid +" "+ check +" "+ extid +" "+ biasTag +" "+ limitTag;
	}
	

}
