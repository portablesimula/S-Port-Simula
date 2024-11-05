package bec.syntaxClass.programElement.routine;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.syntaxClass.SyntaxClass;
import bec.syntaxClass.programElement.ProgramElement;
import bec.syntaxClass.programElement.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class PROFILE extends ProgramElement {
	int profileTag;

	int kind;      // peculiar
	public int bodyTag;   // peculiar
	String nature; // peculiar
	public String ident;  // peculiar
	
	public DataSegment DSEG;
//	public Vector<IMPORT> imports;
//	EXIT exit;
//	EXPORT export;
	public Vector<Variable> imports;
	public Variable exit;
	public Variable export;

	public int type; // Export type ?????
	public int npar() {
		return(imports.size());
	}
	
	public PROFILE() {
		imports = new Vector<Variable>();
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
		profileTag = Scode.inTag(this);
		
//		Global.Display.set(profileTag, this);
		
		
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
		
//		String pID = "Name";//Scode.TAGTABLE[profileTag];
		String pID = Scode.edTag(profileTag);
		DSEG = new DataSegment("DSEG_" + Global.moduleID + '_' + pID, Segment.SEG_DATA);
		while(Scode.accept(Scode.S_IMPORT)) {
			imports.add(Variable.ofIMPORT(DSEG));
		}
		if(Scode.accept(Scode.S_EXIT)) exit = Variable.ofEXIT(DSEG);
		else if(Scode.accept(Scode.S_EXPORT)) export = Variable.ofEXPORT(DSEG);
		Scode.expect(Scode.S_ENDPROFILE);
//		if(exit == null) exit = Variable.ofRETUR(DSEG);
		
		if(Global.SCODE_INPUT_TRACE) {
			System.out.println("-------------------------------------------------- BEGIN PRINT PROFILE Definition");
			printTree(2);
			System.out.println("-------------------------------------------------- ENDOF PRINT PROFILE Definition");
		}
//		DSEG.dump();
//		if(Scode.inputTrace > 3) printTree(2);
//		Util.IERR("");
	}

	@Override
	public void printTree(final int indent) {
		String profile = "PROFILE " + Scode.edTag(profileTag);
		switch(kind) {
		case Scode.S_KNOWN ->     profile += " KNOWN "     + Scode.edTag(bodyTag) + " \"" + ident + '"';
		case Scode.S_SYSTEM ->    profile += " SYSTEM "    + Scode.edTag(bodyTag) + " " + ident;
		case Scode.S_EXTERNAL ->  profile += " EXTERNAL "  + Scode.edTag(bodyTag) + " " + nature + " " + ident;
		case Scode.S_INTERFACE -> profile += " INTERFACE " + ident;
		}
		sLIST(indent, profile);
		for(Variable imprt:imports) sLIST(indent + 1, imprt.toString());
		if(exit != null)   sLIST(indent + 1, exit.toString());
		if(export != null) sLIST(indent + 1, export.toString());
		sLIST(indent, "ENDPROFILE");		
	}
	
	public String toString() {
		String profile = "PROFILE " + Scode.edTag(profileTag);
		switch(kind) {
			case Scode.S_KNOWN ->     profile += " KNOWN " + Scode.edTag(bodyTag) + " \"" + ident + '"';
			case Scode.S_SYSTEM ->    profile += " SYSTEM " + Scode.edTag(bodyTag) + " " + ident;
			case Scode.S_EXTERNAL ->  profile += " EXTERNAL " + Scode.edTag(bodyTag) + " " + nature + " " + ident;
			case Scode.S_INTERFACE -> profile += " INTERFACE " + ident;
		}
//		for(Variable imprt:imports) profile += " " + imprt;
//		if(exit != null)   profile += " " + exit;
//		if(export != null) profile += " " + export;
		profile += " ...";
		return profile;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	public PROFILE(AttributeInputStream inpt) throws IOException {
		imports = new Vector<Variable>();
		profileTag = inpt.readTag(this);
		inpt.readInstr();
//		System.out.println("NEW PROFILE(1): inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		switch(inpt.curinstr) {
		case Scode.S_KNOWN ->     { kind = Scode.S_KNOWN;     bodyTag = inpt.readTag(); ident = inpt.readString(); inpt.readInstr(); }
		case Scode.S_SYSTEM ->    { kind = Scode.S_SYSTEM;    bodyTag = inpt.readTag(); ident = inpt.readString(); inpt.readInstr(); }
		case Scode.S_EXTERNAL ->  { kind = Scode.S_EXTERNAL;  bodyTag = inpt.readTag(); nature = inpt.readString(); ident = inpt.readString(); inpt.readInstr(); }
		case Scode.S_INTERFACE -> { kind = Scode.S_INTERFACE; ident = inpt.readString(); inpt.readInstr(); }
		}
//		System.out.println("NEW PROFILE(2): inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		while(inpt.curinstr == Scode.S_IMPORT) {
			imports.add(Variable.readObject(inpt, Scode.S_IMPORT));
			inpt.readInstr();
//			System.out.println("NEW PROFILE(3): inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		}
		if(inpt.curinstr == Scode.S_EXIT)   { exit = Variable.readObject(inpt, Scode.S_EXIT); inpt.readInstr(); }
		if(inpt.curinstr == Scode.S_EXPORT) { export = Variable.readObject(inpt, Scode.S_EXPORT); inpt.readInstr(); }
		
//		System.out.println("NEW PROFILE(4): inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		if(inpt.curinstr != Scode.S_ENDPROFILE) Util.IERR("IMPOSSIBLE: " + Scode.edTag(inpt.curinstr));

		if(Global.ATTR_INPUT_TRACE) this.printTree(2);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.TRACE_OUTPUT("BEGIN Write PROFILE: " + Scode.edTag(profileTag)); // + ", Declared in: " + declaredIn);
		oupt.writeKind(Scode.S_PROFILE); // Mark: This is a ClassDeclaration
		oupt.writeTag(profileTag);
		switch(kind) {
			case Scode.S_KNOWN ->     { oupt.writeInstr(Scode.S_KNOWN); oupt.writeTag(bodyTag); oupt.writeString(ident); }
			case Scode.S_SYSTEM ->    { oupt.writeInstr(Scode.S_SYSTEM); oupt.writeTag(bodyTag); oupt.writeString(ident); }
			case Scode.S_EXTERNAL ->  { oupt.writeInstr(Scode.S_EXTERNAL); oupt.writeTag(bodyTag); oupt.writeString(nature); oupt.writeString(ident); }
			case Scode.S_INTERFACE -> { oupt.writeInstr(Scode.S_INTERFACE); oupt.writeString(ident); }
		}
		for(Variable imprt:imports) imprt.write(oupt);
		if(exit != null)   exit.write(oupt);
		if(export != null) export.write(oupt);
		oupt.writeInstr(Scode.S_ENDPROFILE);		
	}

	public static SyntaxClass readObject(AttributeInputStream inpt) throws IOException {
		return new PROFILE(inpt);
	}


}
