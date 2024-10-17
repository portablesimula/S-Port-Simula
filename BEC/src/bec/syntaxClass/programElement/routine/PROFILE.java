package bec.syntaxClass.programElement.routine;

import java.util.Vector;

import bec.syntaxClass.programElement.ProgramElement;
import bec.util.Scode;

public class PROFILE extends ProgramElement {
	int profileTag;

	int kind;      // peculiar
	int bodyTag;   // peculiar
	String nature; // peculiar
	String ident;  // peculiar
	
	Vector<IMPORT> imports;
	EXIT exit;
	EXPORT export;
	
	public PROFILE() {
		imports = new Vector<IMPORT>();
		parse();
	}

	/**
	 * 	routine_profile
	 * 		 ::= profile profile:newtag <peculiar>?
	 * 			   <import_definition>* <export or exit>? endprofile
	 * 
	 * 		peculiar
	 * 			::= known body:newtag kid:string
	 * 			::= system body:newtag sid:string
	 * 			::= external body:newtag nature:string xid:string
	 * 			::= interface pid:string
	 * 
	 * 		import_definition
	 * 			::= import parm:newtag quantity_descriptor
	 * 
	 * 		export_or_exit
	 * 			::= export parm:newtag resolved_type
	 * 			::= exit return:newtag
	 */
	public void parse() {
		profileTag = Scode.inTag();
		
		if(Scode.nextByte() == Scode.S_KNOWN) {
			Scode.inputInstr();
			kind = Scode.S_KNOWN;
			bodyTag = Scode.inTag();
			ident = Scode.inString();  // kid'String
		} else if(Scode.nextByte() == Scode.S_SYSTEM) {
			Scode.inputInstr();
			kind = Scode.S_SYSTEM;
			bodyTag = Scode.inTag();
			ident = Scode.inString();  // sid'String
		} else if(Scode.nextByte() == Scode.S_EXTERNAL) {
			Scode.inputInstr();
			kind = Scode.S_EXTERNAL;
			bodyTag = Scode.inTag();
			nature = Scode.inString(); // Nature'String
			ident = Scode.inString();  // xid'String
		} else if(Scode.nextByte() == Scode.S_INTERFACE) {
			Scode.inputInstr();
			kind = Scode.S_INTERFACE;
			ident = Scode.inString();  // pid'String
		}
		
		while(Scode.accept(Scode.S_IMPORT)) imports.add(new IMPORT());
		if(Scode.accept(Scode.S_EXIT)) exit = new EXIT();
		else if(Scode.accept(Scode.S_EXPORT)) export = new EXPORT();
		Scode.expect(Scode.S_ENDPROFILE);
		
		if(Scode.inputTrace > 3) printTree(2);
	}

	@Override
	public void printTree(final int indent) {
		String profile = "PROFILE" + Scode.edTag(profileTag);
		switch(kind) {
		case Scode.S_KNOWN ->     profile += " KNOWN " + Scode.edTag(bodyTag) + " \"" + ident + '"';
		case Scode.S_SYSTEM ->    profile += " SYSTEM " + Scode.edTag(bodyTag) + " " + ident;
		case Scode.S_EXTERNAL ->  profile += " EXTERNAL " + Scode.edTag(bodyTag) + " " + nature + " " + ident;
		case Scode.S_INTERFACE -> profile += " INTERFACE " + ident;
		}
		sLIST(indent, profile);
		for(IMPORT imprt:imports) sLIST(indent + 1, imprt.toString());
		if(exit != null)   sLIST(indent + 1, exit.toString());
		if(export != null) sLIST(indent + 1, export.toString());
		sLIST(indent, "ENDPROFILE");		
	}
	
	public String toString() {
		return "ROUTINE "; // + lab;
	}

}
