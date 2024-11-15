package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.InsertStatement;
import bec.ModuleIO;
import bec.compileTimeStack.DataType;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Type;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;

//Record ProfileDescr:Descriptor;  -- K_ProfileDescr     SIZE = (6+npar*2) align 4
//begin range(0:MaxByte) npar;     -- No.of parameters
//      range(0:1) WithExit;
//      range(0:MaxParByte) nparbyte;
//      range(0:P_max) Pkind;
//      infix(ParamSpec) Par(0);   -- Parameter Specifications
//end;
public class ProfileDescr extends Descriptor {
//	int npar;     // No.of parameters
//	boolean withExit;
	int nparbyte;
	int Pkind; // Profile Kind
//	Vector<ParamSpec> params; // Parameter Specifications
	public Vector<Variable> imports;
	public Variable exit;
	public Variable export;
	
//	public int ptag;
	public int bodyTag;    // peculiar
	private String nature; // peculiar
	public String ident;   // peculiar
	public DataSegment DSEG;

	
//	Record ParamSpec; info "TYPE";
//	begin range(0:MaxType) type;
//	      range(0:MaxByte) count;
//	end;
	class ParamSpec {
		int type;
		int count;
		public void write(AttributeOutputStream oupt) throws IOException {
			oupt.writeShort(type);
			oupt.writeShort(count);
		}
	}
	
	private ProfileDescr(int kind, Tag tag) {
		super(kind, tag);
//		System.out.println("NEW ProfileDescr: " + Scode.edTag(tag));
	}

//	%title ***   I n p u t   P r o f i l e   ***
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
	public static ProfileDescr inProfile(int defkind) { // export ref(ProfileDescr) prf;
		Tag ptag = Tag.inTag();
//		System.out.println("ProfileDescr.inProfile: " + Scode.edTag(ptag));
//		prf.ptag = ptag;
		ProfileDescr prf = new ProfileDescr(Kind.K_ProfileDescr, ptag);
//		int kind = defkind;
		prf.Pkind = defkind;
		if(Scode.nextByte() == Scode.S_EXTERNAL) {
			 // peculiar ::= external body:newtag nature:string xid:string
			Scode.inputInstr();
			Tag.inTag();
			Scode.inString();
			// kind = Kind.P_EXTERNAL;
			// IntDescr rut = new IntDescr(Kind.K_IntRoutine, rtag);
			Util.IERR("External Routines is not part of this implementation");
		} else if(Scode.nextByte() == Scode.S_INTERFACE) {
			 // peculiar ::= interface pid:string
			Scode.inputInstr();
			String xid = Scode.inString();
//			prf.Pkind = Kind.P_INTERFACE;
			prf.Pkind = getSysKind(xid);
		} else if(Scode.nextByte() == Scode.S_KNOWN) {
			// peculiar	::= known body:newtag kid:string
			Scode.inputInstr();
			Tag rtag = Tag.inTag();
			String xid = Scode.inString();
			IntDescr rut = new IntDescr(Kind.K_IntRoutine, rtag);
			Util.IERR("");
//			prf.Pkind = Kind.P_KNOWN;
			prf.Pkind = getKnownKind(xid);
		} else if(Scode.nextByte() == Scode.S_SYSTEM) {
			 //	peculiar ::= system body:newtag sid:string
			Scode.inputInstr();
			Tag rtag = Tag.inTag();
			String xid = Scode.inString();
			IntDescr rut = new IntDescr(Kind.K_IntRoutine, rtag);
//			prf.Pkind = Kind.P_SYSTEM;
			prf.Pkind = getSysKind(xid);
		}
		prf.imports = new Vector<Variable>();
		prf.DSEG = new DataSegment("DSEG_" + Global.moduleID + '_' + ptag, Kind.K_SEG_DATA);
		Scode.inputInstr();
		while(Scode.curinstr == Scode.S_IMPORT) {
			prf.imports.add(Variable.ofIMPORT(prf.DSEG));
			Scode.inputInstr();
		}
		if(Scode.curinstr == Scode.S_EXIT) {
			prf.exit = Variable.ofEXIT(prf.DSEG);
			Scode.inputInstr();
		} else if(Scode.curinstr == Scode.S_EXPORT) {
			prf.export = Variable.ofEXPORT(prf.DSEG);				
			Scode.inputInstr();
		}
		if(Scode.curinstr != Scode.S_ENDPROFILE)
			Util.IERR("Missing ENDPROFILE. Got " + Scode.edInstr(Scode.curinstr));
		
//		Global.dumpDISPL("END PROFILE: ");
//		prf.print("   ");
//		Util.IERR("");
		return prf;
	}

	@Override
	public void print(final String indent) {
		String profile = "PROFILE " + tag;
		switch(kind) {
		case Scode.S_KNOWN ->     profile += " KNOWN "     + Scode.edTag(bodyTag) + " \"" + ident + '"';
		case Scode.S_SYSTEM ->    profile += " SYSTEM "    + Scode.edTag(bodyTag) + " " + ident;
		case Scode.S_EXTERNAL ->  profile += " EXTERNAL "  + Scode.edTag(bodyTag) + " " + nature + " " + ident;
		case Scode.S_INTERFACE -> profile += " INTERFACE " + ident;
		}
		System.out.println(indent + profile);
		if(imports != null) for(Variable imprt:imports) System.out.println(indent + "   " + imprt.toString());
		if(exit != null)   System.out.println(indent + "   " + exit.toString());
		if(export != null) System.out.println(indent + "   " + export.toString());
		if(DSEG != null) DSEG.dump("");
		System.out.println(indent + "ENDPROFILE");		
	}
	
	public String toString() {
		String profile = "PROFILE " + tag;
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

	
	private static int getKnownKind(String s) {
		Util.IERR("");
//%+S            if STEQ(s,"RLOG10")    then kind:=P_RLOG10
//%+S         elsif STEQ(s,"DLOG10")    then kind:=P_DLOG10
//%+S         elsif STEQ(s,"RCOSIN")    then kind:=P_RCOSIN
//%+S         elsif STEQ(s,"COSINU")    then kind:=P_COSINU
//%+S         elsif STEQ(s,"RTANGN")    then kind:=P_RTANGN
//%+S         elsif STEQ(s,"TANGEN")    then kind:=P_TANGEN
//%+S         elsif STEQ(s,"RARCOS")    then kind:=P_RARCOS
//%+S         elsif STEQ(s,"ARCCOS")    then kind:=P_ARCCOS
//%+S         elsif STEQ(s,"RARSIN")    then kind:=P_RARSIN
//%+S         elsif STEQ(s,"ARCSIN")    then kind:=P_ARCSIN
//%+S         elsif STEQ(s,"ERRNON")    then kind:=P_ERRNON
//%+S         elsif STEQ(s,"ERRQUA")    then kind:=P_ERRQUA
//%+S         elsif STEQ(s,"ERRSWT")    then kind:=P_ERRSWT
//%+S         elsif STEQ(s,"ERROR")     then kind:=P_ERROR
//%+S         elsif STEQ(s,"CBLNK")     then kind:=P_CBLNK
//%+S         elsif STEQ(s,"CMOVE")     then kind:=P_CMOVE
//%+S         elsif STEQ(s,"STRIP")     then kind:=P_STRIP
//%+S         elsif STEQ(s,"TXTREL")    then kind:=P_TXTREL
//%+S         elsif STEQ(s,"TRFREL")    then kind:=P_TRFREL
//%+S         elsif STEQ(s,"AR1IND")    then kind:=P_AR1IND
//%+S         elsif STEQ(s,"AR2IND")    then kind:=P_AR2IND
//%+S         elsif STEQ(s,"ARGIND")    then kind:=P_ARGIND
//%+S         elsif STEQ(s,"IABS")      then kind:=P_IABS
//%+S         elsif STEQ(s,"RABS")      then kind:=P_RABS
//%+S         elsif STEQ(s,"DABS")      then kind:=P_DABS
//%+S         elsif STEQ(s,"RSIGN")     then kind:=P_RSIGN
//%+S         elsif STEQ(s,"DSIGN")     then kind:=P_DSIGN
//%+S         elsif STEQ(s,"MODULO")    then kind:=P_MODULO
//%+S         elsif STEQ(s,"RENTI")     then kind:=P_RENTI
//%+S         elsif STEQ(s,"DENTI")     then kind:=P_DENTI
//%+S         elsif STEQ(s,"DIGIT")     then kind:=P_DIGIT
//%+S         elsif STEQ(s,"LETTER")    then kind:=P_LETTER
//%+S         elsif STEQ(s,"RIPOWR")    then kind:=P_RIPOWR
//%+S         elsif STEQ(s,"RRPOWR")    then kind:=P_RRPOWR
//%+S         elsif STEQ(s,"RDPOWR")    then kind:=P_RDPOWR
//%+S         elsif STEQ(s,"DIPOWR")    then kind:=P_DIPOWR
//%+S         elsif STEQ(s,"DRPOWR")    then kind:=P_DRPOWR
//%+S         elsif STEQ(s,"DDPOWR")    then kind:=P_DDPOWR
//%+S         endif;
		return 0;
	}

	private static int getSysKind(String s) {
		//--- Search for inline index ---
		if(s.equalsIgnoreCase("INTRHA")) return Kind.P_INTRHA;
		if(s.equalsIgnoreCase("STREQL")) return Kind.P_STREQL;
		if(s.equalsIgnoreCase("PRINTO")) return Kind.P_PRINTO;
//		if(s.equalsIgnoreCase("MOVEIN")) return Kind.P_MOVEIN;
//		if(s.equalsIgnoreCase("RSQROO")) return Kind.P_RSQROO;
//		if(s.equalsIgnoreCase("SQROOT")) return Kind.P_SQROOT;
//		if(s.equalsIgnoreCase("RLOGAR")) return Kind.P_RLOGAR;
//		if(s.equalsIgnoreCase("LOGARI")) return Kind.P_LOGARI;
//		if(s.equalsIgnoreCase("REXPON")) return Kind.P_REXPON;
//		if(s.equalsIgnoreCase("EXPONE")) return Kind.P_EXPONE;
//		if(s.equalsIgnoreCase("RSINUS")) return Kind.P_RSINUS;
//		if(s.equalsIgnoreCase("SINUSR")) return Kind.P_SINUSR;
//		if(s.equalsIgnoreCase("RARTAN")) return Kind.P_RARTAN;
//		if(s.equalsIgnoreCase("ARCTAN")) return Kind.P_ARCTAN;
//		if(s.equalsIgnoreCase("M?CREF")) return Kind.P_DOS_CREF;
//		if(s.equalsIgnoreCase("M?OPEN")) return Kind.P_DOS_OPEN;
//		if(s.equalsIgnoreCase("M?CLOSE")) return Kind.P_DOS_CLOSE;
//		if(s.equalsIgnoreCase("M?READ")) return Kind.P_DOS_READ;
//		if(s.equalsIgnoreCase("M?WRITE")) return Kind.P_DOS_WRITE;
//		if(s.equalsIgnoreCase("M?DELF")) return Kind.P_DOS_DELF;
//		if(s.equalsIgnoreCase("M?FPTR")) return Kind.P_DOS_FPTR;
//		if(s.equalsIgnoreCase("M?CDIR")) return Kind.P_DOS_CDIR;
//		if(s.equalsIgnoreCase("M?ALOC")) return Kind.P_DOS_ALOC;
//		if(s.equalsIgnoreCase("M?TERM")) return Kind.P_DOS_TERM;
//		if(s.equalsIgnoreCase("M?TIME")) return Kind.P_DOS_TIME;
//		if(s.equalsIgnoreCase("M?DATE")) return Kind.P_DOS_DATE;
//		if(s.equalsIgnoreCase("M?VERS")) return Kind.P_DOS_VERS;
//		if(s.equalsIgnoreCase("M?EXEC")) return Kind.P_DOS_EXEC;
//		if(s.equalsIgnoreCase("M?IOCTL")) return Kind.P_DOS_IOCTL;
//		if(s.equalsIgnoreCase("M?LOCK")) return Kind.P_DOS_LOCK;
//		if(s.equalsIgnoreCase("M?GDRV")) return Kind.P_DOS_GDRV;
//		if(s.equalsIgnoreCase("M?GDIR")) return Kind.P_DOS_GDIR;
//		if(s.equalsIgnoreCase("S?SCMPEQ")) return Kind.P_APX_SCMPEQ;
//		if(s.equalsIgnoreCase("S?SMOVEI")) return Kind.P_APX_SMOVEI;
//		if(s.equalsIgnoreCase("S?SMOVED")) return Kind.P_APX_SMOVED;
//		if(s.equalsIgnoreCase("S?SSKIP")) return Kind.P_APX_SSKIP;
//		if(s.equalsIgnoreCase("S?STRIP")) return Kind.P_APX_STRIP;
//		if(s.equalsIgnoreCase("S?SFINDI")) return Kind.P_APX_SFINDI;
//		if(s.equalsIgnoreCase("S?SFINDD")) return Kind.P_APX_SFINDD;
//		if(s.equalsIgnoreCase("S?SFILL")) return Kind.P_APX_SFILL;
//		if(s.equalsIgnoreCase("S?BOBY")) return Kind.P_APX_BOBY;
//		if(s.equalsIgnoreCase("S?BYBO")) return Kind.P_APX_BYBO;
//		if(s.equalsIgnoreCase("S?SZ2W")) return Kind.P_APX_SZ2W;
//		if(s.equalsIgnoreCase("S?W2SZ")) return Kind.P_APX_W2SZ;
//		if(s.equalsIgnoreCase("S?RF2N")) return Kind.P_APX_RF2N;
//		if(s.equalsIgnoreCase("S?N2RF")) return Kind.P_APX_N2RF;
//		if(s.equalsIgnoreCase("S?BNOT")) return Kind.P_APX_BNOT;
//		if(s.equalsIgnoreCase("S?BAND")) return Kind.P_APX_BAND;
//		if(s.equalsIgnoreCase("S?BOR"))  return Kind.P_APX_BOR;
//		if(s.equalsIgnoreCase("S?BXOR")) return Kind.P_APX_BXOR;
//		if(s.equalsIgnoreCase("S?WNOT")) return Kind.P_APX_WNOT;
//		if(s.equalsIgnoreCase("S?WAND")) return Kind.P_APX_WAND;
//		if(s.equalsIgnoreCase("S?WOR"))  return Kind.P_APX_WOR;
//		if(s.equalsIgnoreCase("S?WXOR")) return Kind.P_APX_WXOR;
//		if(s.equalsIgnoreCase("S?BSHL")) return Kind.P_APX_BSHL;
//		if(s.equalsIgnoreCase("S?WSHL")) return Kind.P_APX_WSHL;
//		if(s.equalsIgnoreCase("S?BSHR")) return Kind.P_APX_BSHR;
//		if(s.equalsIgnoreCase("S?WSHR")) return Kind.P_APX_WSHR;
//		if(s.equalsIgnoreCase("M?SVDM")) return Kind.P_DOS_SDMODE;
//		if(s.equalsIgnoreCase("M?UPOS")) return Kind.P_DOS_UPDPOS;
//		if(s.equalsIgnoreCase("M?CURS")) return Kind.P_DOS_CURSOR;
//		if(s.equalsIgnoreCase("M?SDPG")) return Kind.P_DOS_SDPAGE;
//		if(s.equalsIgnoreCase("M?SRUP")) return Kind.P_DOS_SROLUP;
//		if(s.equalsIgnoreCase("M?SRDW")) return Kind.P_DOS_SROLDW;
//		if(s.equalsIgnoreCase("M?GETC")) return Kind.P_DOS_GETCEL;
//		if(s.equalsIgnoreCase("M?PUTC")) return Kind.P_DOS_PUTCHR;
//		if(s.equalsIgnoreCase("M?GVDM")) return Kind.P_DOS_GDMODE;
//		if(s.equalsIgnoreCase("M?SPAL")) return Kind.P_DOS_SETPAL;
//		if(s.equalsIgnoreCase("M?RCHK")) return Kind.P_DOS_RDCHK;
//		if(s.equalsIgnoreCase("M?KEYI")) return Kind.P_DOS_KEYIN;
		Util.IERR(""+s);
		return 0;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("ProfileDescr.Write: " + this);
		oupt.writeKind(kind);
//		oupt.writeShort(ModuleIO.chgType(tag));
		tag.write(oupt);
		oupt.writeKind(Pkind);
		oupt.writeShort(nparbyte);
//		oupt.writeShort(imports.size()); // npar
//		for(Variable imprt:imports) imprt.write(oupt);
//		if(exit   != null) exit.write(oupt);
//		if(export != null) export.write(oupt);
//		Util.IERR("");
	}

	public static ProfileDescr read(AttributeInputStream inpt) throws IOException {
//		int tag = inpt.readShort();
//		tag = InsertStatement.current.chgInType(tag);
		Tag tag = Tag.read(inpt);
		ProfileDescr prf = new ProfileDescr(Kind.K_RecordDescr, tag);
		prf.Pkind = inpt.readKind();
		prf.nparbyte = inpt.readShort();
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("ProfileDescr.Read: " + prf);
		prf.print("   ");
//		Util.IERR("");
		return prf;
	}



}
