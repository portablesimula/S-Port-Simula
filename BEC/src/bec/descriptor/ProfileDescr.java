package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_SYSCALL;

//Record ProfileDescr:Descriptor;  -- K_ProfileDescr     SIZE = (6+npar*2) align 4
//begin range(0:MaxByte) npar;     -- No.of parameters
//      range(0:1) WithExit;
//      range(0:MaxParByte) nparbyte;
//      range(0:P_max) pKind;
//      infix(ParamSpec) Par(0);   -- Parameter Specifications
//end;
public class ProfileDescr extends Descriptor {
	public int npar;     // No.of parameters
//	boolean withExit;
//	int nparbyte;
	public int pKind; // Peculiar Profile Kind
	public Vector<Tag> params;
	private Tag exportTag;
	MemAddr returAddr;
	public DataSegment DSEG;
	
	//	NOT SAVED:
	public Vector<Variable> imports;
	public Variable export;
	public Variable exit;
	
	public int bodyTag;    // peculiar
	private String nature; // peculiar
	public String ident;   // peculiar

	
//	Record ParamSpec; info "TYPE";
//	begin range(0:MaxType) type;
//	      range(0:MaxByte) count;
//	end;
//	class ParamSpec {
//		Type type;
//		int count;
//		public void write(AttributeOutputStream oupt) throws IOException {
//			oupt.writeShort(type);
//			oupt.writeShort(count);
//		}
//	}
	
	private ProfileDescr(int kind, Tag tag) {
		super(kind, tag);
//		System.out.println("NEW ProfileDescr: " + Scode.edTag(tag));
	}
	
	public Variable getExport() {
		if(exportTag == null) return null;
		Variable export = (Variable) Global.getMeaning(exportTag.val);
		return export;
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
	public static ProfileDescr ofProfile() {
		Tag ptag = Tag.inTag();
//		System.out.println("ProfileDescr.inProfile: " + Scode.edTag(ptag));
		ProfileDescr prf = new ProfileDescr(Kind.K_ProfileDescr, ptag);
		if(Scode.nextByte() == Scode.S_EXTERNAL) {
			 // peculiar ::= external body:newtag nature:string xid:string
			Scode.inputInstr();
			Tag.inTag();
			Scode.inString();
			// IntDescr rut = new IntDescr(Kind.K_IntRoutine, rtag);
			Util.IERR("External Routines is not part of this implementation");
		} else if(Scode.nextByte() == Scode.S_INTERFACE) {
			 // peculiar ::= interface pid:string
			Scode.inputInstr();
			String xid = Scode.inString();
			prf.pKind = getSysKind(xid);
		} else if(Scode.nextByte() == Scode.S_KNOWN) {
			// peculiar	::= known body:newtag kid:string
			Scode.inputInstr();
			Tag rtag = Tag.inTag();
			String xid = Scode.inString();
			RoutineDescr rut = new RoutineDescr(Kind.K_IntRoutine, rtag);
//			prf.pKind = getKnownKind(xid);
		} else if(Scode.nextByte() == Scode.S_SYSTEM) {
			 //	peculiar ::= system body:newtag sid:string
			Scode.inputInstr();
			Tag rtag = Tag.inTag();
			String xid = Scode.inString();
			RoutineDescr rut = new RoutineDescr(Kind.K_IntRoutine, rtag);
			prf.pKind = getSysKind(xid);
		}
		prf.imports = new Vector<Variable>();
		prf.params = new Vector<Tag>();
		prf.DSEG = new DataSegment("DSEG_" + Global.moduleID + '_' + ptag.ident(), Kind.K_SEG_DATA);
		prf.returAddr = prf.DSEG.nextAddress();
		prf.DSEG.emit(null, "RETUR");

		Scode.inputInstr();
		while(Scode.curinstr == Scode.S_IMPORT) {
			Variable par = Variable.ofIMPORT(prf.DSEG);
			prf.imports.add(par);
			prf.params.add(par.tag);
			Scode.inputInstr();
		}
		prf.npar = prf.imports.size();
		if(Scode.curinstr == Scode.S_EXIT) {
//			prf.exit = Variable.ofEXIT(prf.DSEG);
			prf.exit = Variable.ofEXIT(prf.returAddr);
			Scode.inputInstr();
		} else if(Scode.curinstr == Scode.S_EXPORT) {
			prf.export = Variable.ofEXPORT(prf.DSEG);
			prf.exportTag = prf.export.tag;
			Scode.inputInstr();
		}
		if(prf.exit == null) prf.exit = Variable.ofRETUR(prf.returAddr);
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
		if(exportTag != null) profile += " exportTag=" + exportTag;
		profile += " returAddr=" + returAddr;
		return profile;
	}

	
	private static int getKnownKind(String s) {
//		if(s.equalsIgnoreCase("RLOG10")) return SVM_SYSCALL.P_RLOG10;
//		if(s.equalsIgnoreCase("DLOG10")) return SVM_SYSCALL.P_DLOG10;
//		if(s.equalsIgnoreCase("RCOSIN")) return SVM_SYSCALL.P_RCOSIN;
//		if(s.equalsIgnoreCase("COSINU")) return SVM_SYSCALL.P_COSINU;
//		if(s.equalsIgnoreCase("RTANGN")) return SVM_SYSCALL.P_RTANGN;
//		if(s.equalsIgnoreCase("TANGEN")) return SVM_SYSCALL.P_TANGEN;
//		if(s.equalsIgnoreCase("RARCOS")) return SVM_SYSCALL.P_RARCOS;
//		if(s.equalsIgnoreCase("ARCCOS")) return SVM_SYSCALL.P_ARCCOS;
//		if(s.equalsIgnoreCase("RARSIN")) return SVM_SYSCALL.P_RARSIN;
//		if(s.equalsIgnoreCase("ARCSIN")) return SVM_SYSCALL.P_ARCSIN;
//		if(s.equalsIgnoreCase("ERRNON")) return SVM_SYSCALL.P_ERRNON;
//		if(s.equalsIgnoreCase("ERRQUA")) return SVM_SYSCALL.P_ERRQUA;
//		if(s.equalsIgnoreCase("ERRSWT")) return SVM_SYSCALL.P_ERRSWT;
//		if(s.equalsIgnoreCase("ERROR")) return SVM_SYSCALL.P_ERROR;
//		if(s.equalsIgnoreCase("CBLNK")) return SVM_SYSCALL.P_CBLNK;
//		if(s.equalsIgnoreCase("CMOVE")) return SVM_SYSCALL.P_CMOVE;
		if(s.equalsIgnoreCase("STRIP")) return SVM_SYSCALL.P_STRIP;
//		if(s.equalsIgnoreCase("TXTREL")) return SVM_SYSCALL.P_TXTREL;
		if(s.equalsIgnoreCase("TRFREL")) return SVM_SYSCALL.P_TRFREL;
		if(s.equalsIgnoreCase("TRFREL")) return SVM_SYSCALL.P_TRFREL;
//		if(s.equalsIgnoreCase("AR1IND")) return SVM_SYSCALL.P_AR1IND;
//		if(s.equalsIgnoreCase("AR2IND")) return SVM_SYSCALL.P_AR2IND;
//		if(s.equalsIgnoreCase("ARGIND")) return SVM_SYSCALL.P_ARGIND;
//		if(s.equalsIgnoreCase("IABS")) return SVM_SYSCALL.P_IABS;
//		if(s.equalsIgnoreCase("RABS")) return SVM_SYSCALL.P_RABS;
//		if(s.equalsIgnoreCase("DABS")) return SVM_SYSCALL.P_DABS;
//		if(s.equalsIgnoreCase("RSIGN")) return SVM_SYSCALL.P_RSIGN;
//		if(s.equalsIgnoreCase("DSIGN")) return SVM_SYSCALL.P_DSIGN;
//		if(s.equalsIgnoreCase("MODULO")) return SVM_SYSCALL.P_MODULO;
//		if(s.equalsIgnoreCase("RENTI")) return SVM_SYSCALL.P_RENTI;
//		if(s.equalsIgnoreCase("DENTI")) return SVM_SYSCALL.P_DENTI;
//		if(s.equalsIgnoreCase("DIGIT")) return SVM_SYSCALL.P_DIGIT;
//		if(s.equalsIgnoreCase("LETTER")) return SVM_SYSCALL.P_LETTER;
//		if(s.equalsIgnoreCase("RIPOWR")) return SVM_SYSCALL.P_RIPOWR;
//		if(s.equalsIgnoreCase("RRPOWR")) return SVM_SYSCALL.P_RRPOWR;
//		if(s.equalsIgnoreCase("RDPOWR")) return SVM_SYSCALL.P_RDPOWR;
//		if(s.equalsIgnoreCase("DIPOWR")) return SVM_SYSCALL.P_DIPOWR;
//		if(s.equalsIgnoreCase("DRPOWR")) return SVM_SYSCALL.P_DRPOWR;
//		if(s.equalsIgnoreCase("DDPOWR")) return SVM_SYSCALL.P_DDPOWR;
		Util.IERR(""+s);
		return 0;
	}

	private static int getSysKind(String s) {
		//--- Search for inline index ---
		if(s.equalsIgnoreCase("TERMIN")) return SVM_SYSCALL.P_TERMIN;
		if(s.equalsIgnoreCase("INTRHA")) return SVM_SYSCALL.P_INTRHA;
		if(s.equalsIgnoreCase("STREQL")) return SVM_SYSCALL.P_STREQL;
		if(s.equalsIgnoreCase("PRINTO")) return SVM_SYSCALL.P_PRINTO;
		if(s.equalsIgnoreCase("INITIA")) return SVM_SYSCALL.P_INITIA;
		if(s.equalsIgnoreCase("SETOPT")) return SVM_SYSCALL.P_SETOPT;
		if(s.equalsIgnoreCase("DMPSEG")) return SVM_SYSCALL.P_DMPSEG;
		if(s.equalsIgnoreCase("DMPENT")) return SVM_SYSCALL.P_DMPENT;
		if(s.equalsIgnoreCase("DMPOOL")) return SVM_SYSCALL.P_DMPOOL;
		if(s.equalsIgnoreCase("VERBOSE")) return SVM_SYSCALL.P_VERBOSE;
		if(s.equalsIgnoreCase("GINTIN")) return SVM_SYSCALL.P_GINTIN;
		if(s.equalsIgnoreCase("GTEXIN")) return SVM_SYSCALL.P_GTEXIN;
		if(s.equalsIgnoreCase("SIZEIN")) return SVM_SYSCALL.P_SIZEIN;
		if(s.equalsIgnoreCase("GVIINF")) return SVM_SYSCALL.P_GVIINF;
		if(s.equalsIgnoreCase("GIVINF")) return SVM_SYSCALL.P_GIVINF;
		if(s.equalsIgnoreCase("CPUTIM")) return SVM_SYSCALL.P_CPUTIM;
		if(s.equalsIgnoreCase("DWAREA")) return SVM_SYSCALL.P_DWAREA;
		if(s.equalsIgnoreCase("MOVEIN")) return SVM_SYSCALL.P_MOVEIN;
		if(s.equalsIgnoreCase("OPFILE")) return SVM_SYSCALL.P_OPFILE;
		if(s.equalsIgnoreCase("CLFILE")) return SVM_SYSCALL.P_CLFILE;
		if(s.equalsIgnoreCase("LSTLOC")) return SVM_SYSCALL.P_LSTLOC;
		if(s.equalsIgnoreCase("MAXLOC")) return SVM_SYSCALL.P_MAXLOC;
		if(s.equalsIgnoreCase("CHKPNT")) return SVM_SYSCALL.P_CHKPNT;
		if(s.equalsIgnoreCase("LOCKFI")) return SVM_SYSCALL.P_LOCKFI;
		if(s.equalsIgnoreCase("UNLOCK")) return SVM_SYSCALL.P_UNLOCK;
		if(s.equalsIgnoreCase("INIMAG")) return SVM_SYSCALL.P_INIMAG;
		if(s.equalsIgnoreCase("OUTIMA")) return SVM_SYSCALL.P_OUTIMA;
		if(s.equalsIgnoreCase("BREAKO")) return SVM_SYSCALL.P_BREAKO;
		if(s.equalsIgnoreCase("LOCATE")) return SVM_SYSCALL.P_LOCATE;
		if(s.equalsIgnoreCase("DELETE")) return SVM_SYSCALL.P_DELETE;
		if(s.equalsIgnoreCase("GDSNAM")) return SVM_SYSCALL.P_GDSNAM;
		if(s.equalsIgnoreCase("GDSPEC")) return SVM_SYSCALL.P_GDSPEC;
		if(s.equalsIgnoreCase("GETLPP")) return SVM_SYSCALL.P_GETLPP;
		if(s.equalsIgnoreCase("NEWPAG")) return SVM_SYSCALL.P_NEWPAG;
		if(s.equalsIgnoreCase("PRINTO")) return SVM_SYSCALL.P_PRINTO;
		if(s.equalsIgnoreCase("STREQL")) return SVM_SYSCALL.P_STREQL;
		if(s.equalsIgnoreCase("INBYTE")) return SVM_SYSCALL.P_INBYTE;
		if(s.equalsIgnoreCase("OUTBYT")) return SVM_SYSCALL.P_OUTBYT;
		if(s.equalsIgnoreCase("GETINT")) return SVM_SYSCALL.P_GETINT;
		if(s.equalsIgnoreCase("GTREAL")) return SVM_SYSCALL.P_GTREAL;
		if(s.equalsIgnoreCase("GTFRAC")) return SVM_SYSCALL.P_GTFRAC;
		if(s.equalsIgnoreCase("PUTSTR")) return SVM_SYSCALL.P_PUTSTR;
		if(s.equalsIgnoreCase("PUTINT")) return SVM_SYSCALL.P_PUTINT;
		if(s.equalsIgnoreCase("PUTINT2")) return SVM_SYSCALL.P_PUTINT2;
		if(s.equalsIgnoreCase("PUTSIZE")) return SVM_SYSCALL.P_PUTSIZE;
		if(s.equalsIgnoreCase("PUTHEX")) return SVM_SYSCALL.P_PUTHEX;
		if(s.equalsIgnoreCase("PUTFIX")) return SVM_SYSCALL.P_PUTFIX;
		if(s.equalsIgnoreCase("PUTFIX2")) return SVM_SYSCALL.P_PUTFIX2;
		if(s.equalsIgnoreCase("PTLFIX")) return SVM_SYSCALL.P_PTLFIX;
		if(s.equalsIgnoreCase("PTLFIX2")) return SVM_SYSCALL.P_PTLFIX2;
		if(s.equalsIgnoreCase("PTREAL")) return SVM_SYSCALL.P_PTREAL;
		if(s.equalsIgnoreCase("PTREAL2")) return SVM_SYSCALL.P_PTREAL2;
		if(s.equalsIgnoreCase("PLREAL")) return SVM_SYSCALL.P_PLREAL;
		if(s.equalsIgnoreCase("PLREAL2")) return SVM_SYSCALL.P_PLREAL2;
		if(s.equalsIgnoreCase("PTFRAC")) return SVM_SYSCALL.P_PTFRAC;
		if(s.equalsIgnoreCase("PTSIZE")) return SVM_SYSCALL.P_PTSIZE;
		if(s.equalsIgnoreCase("PTOADR")) return SVM_SYSCALL.P_PTOADR;
		if(s.equalsIgnoreCase("PTOADR2")) return SVM_SYSCALL.P_PTOADR2;
		if(s.equalsIgnoreCase("PTAADR")) return SVM_SYSCALL.P_PTAADR;
		if(s.equalsIgnoreCase("PTAADR2")) return SVM_SYSCALL.P_PTAADR2;
		if(s.equalsIgnoreCase("PTGADR")) return SVM_SYSCALL.P_PTGADR;
		if(s.equalsIgnoreCase("PTGADR2")) return SVM_SYSCALL.P_PTGADR2;
		if(s.equalsIgnoreCase("PTPADR")) return SVM_SYSCALL.P_PTPADR;
		if(s.equalsIgnoreCase("PTPADR2")) return SVM_SYSCALL.P_PTPADR2;
		if(s.equalsIgnoreCase("PTRADR")) return SVM_SYSCALL.P_PTRADR;
		if(s.equalsIgnoreCase("PTRADR2")) return SVM_SYSCALL.P_PTRADR2;
		if(s.equalsIgnoreCase("DRAWRP")) return SVM_SYSCALL.P_DRAWRP;
		if(s.equalsIgnoreCase("DATTIM")) return SVM_SYSCALL.P_DATTIM;
		if(s.equalsIgnoreCase("LOWTEN")) return SVM_SYSCALL.P_LOWTEN;
		if(s.equalsIgnoreCase("DCMARK")) return SVM_SYSCALL.P_DCMARK;
		if(s.equalsIgnoreCase("RSQROO")) return SVM_SYSCALL.P_RSQROO;
		if(s.equalsIgnoreCase("SQROOT")) return SVM_SYSCALL.P_SQROOT;
		if(s.equalsIgnoreCase("RLOGAR")) return SVM_SYSCALL.P_RLOGAR;
		if(s.equalsIgnoreCase("LOGARI")) return SVM_SYSCALL.P_LOGARI;
		if(s.equalsIgnoreCase("RLOG10")) return SVM_SYSCALL.P_RLOG10;
		if(s.equalsIgnoreCase("DLOG10")) return SVM_SYSCALL.P_DLOG10;
		if(s.equalsIgnoreCase("REXPON")) return SVM_SYSCALL.P_REXPON;
		if(s.equalsIgnoreCase("EXPONE")) return SVM_SYSCALL.P_EXPONE;
		if(s.equalsIgnoreCase("RSINUS")) return SVM_SYSCALL.P_RSINUS;
		if(s.equalsIgnoreCase("SINUSR")) return SVM_SYSCALL.P_SINUSR;
		if(s.equalsIgnoreCase("RCOSIN")) return SVM_SYSCALL.P_RCOSIN;
		if(s.equalsIgnoreCase("COSINU")) return SVM_SYSCALL.P_COSINU;
		if(s.equalsIgnoreCase("RTANGN")) return SVM_SYSCALL.P_RTANGN;
		if(s.equalsIgnoreCase("TANGEN")) return SVM_SYSCALL.P_TANGEN;
		if(s.equalsIgnoreCase("RCOTAN")) return SVM_SYSCALL.P_RCOTAN;
		if(s.equalsIgnoreCase("COTANG")) return SVM_SYSCALL.P_COTANG;
		if(s.equalsIgnoreCase("RARTAN")) return SVM_SYSCALL.P_RARTAN;
		if(s.equalsIgnoreCase("ARCTAN")) return SVM_SYSCALL.P_ARCTAN;
		if(s.equalsIgnoreCase("RARCOS")) return SVM_SYSCALL.P_RARCOS;
		if(s.equalsIgnoreCase("ARCCOS")) return SVM_SYSCALL.P_ARCCOS;
		if(s.equalsIgnoreCase("RARSIN")) return SVM_SYSCALL.P_RARSIN;
		if(s.equalsIgnoreCase("ARCSIN")) return SVM_SYSCALL.P_ARCSIN;
		if(s.equalsIgnoreCase("RATAN2")) return SVM_SYSCALL.P_RATAN2;
		if(s.equalsIgnoreCase("ATAN2")) return SVM_SYSCALL.P_ATAN2;
		if(s.equalsIgnoreCase("RSINH")) return SVM_SYSCALL.P_RSINH;
		if(s.equalsIgnoreCase("SINH")) return SVM_SYSCALL.P_SINH;
		if(s.equalsIgnoreCase("RCOSH")) return SVM_SYSCALL.P_RCOSH;
		if(s.equalsIgnoreCase("COSH")) return SVM_SYSCALL.P_COSH;
		if(s.equalsIgnoreCase("RTANH")) return SVM_SYSCALL.P_RTANH;
		if(s.equalsIgnoreCase("TANH")) return SVM_SYSCALL.P_TANH;
		if(s.equalsIgnoreCase("BEGDEB")) return SVM_SYSCALL.P_BEGDEB;
		if(s.equalsIgnoreCase("ENDDEB")) return SVM_SYSCALL.P_ENDDEB;
		if(s.equalsIgnoreCase("BEGTRP")) return SVM_SYSCALL.P_BEGTRP;
		if(s.equalsIgnoreCase("ENDTRP")) return SVM_SYSCALL.P_ENDTRP;
		if(s.equalsIgnoreCase("GTPADR")) return SVM_SYSCALL.P_GTPADR;
		if(s.equalsIgnoreCase("GTOUTM")) return SVM_SYSCALL.P_GTOUTM;
		if(s.equalsIgnoreCase("GTLNID")) return SVM_SYSCALL.P_GTLNID;
		if(s.equalsIgnoreCase("GTLNO")) return SVM_SYSCALL.P_GTLNO;
		if(s.equalsIgnoreCase("BRKPNT")) return SVM_SYSCALL.P_BRKPNT;
		if(s.equalsIgnoreCase("STMNOT")) return SVM_SYSCALL.P_STMNOT;
		if(s.equalsIgnoreCase("DMPOBJ")) return SVM_SYSCALL.P_DMPOBJ;

		// KNOWN 
		if(s.equalsIgnoreCase("MODULO")) return SVM_SYSCALL.P_MODULO;
		if(s.equalsIgnoreCase("RADDEP")) return SVM_SYSCALL.P_RADDEP;
		if(s.equalsIgnoreCase("DADDEP")) return SVM_SYSCALL.P_DADDEP;
		if(s.equalsIgnoreCase("RSUBEP")) return SVM_SYSCALL.P_RSUBEP;
		if(s.equalsIgnoreCase("DSUBEP")) return SVM_SYSCALL.P_DSUBEP;
		if(s.equalsIgnoreCase("IIPOWR")) return SVM_SYSCALL.P_IIPOWR;
		if(s.equalsIgnoreCase("RIPOWR")) return SVM_SYSCALL.P_RIPOWR;
		if(s.equalsIgnoreCase("RRPOWR")) return SVM_SYSCALL.P_RRPOWR;
		if(s.equalsIgnoreCase("RDPOWR")) return SVM_SYSCALL.P_RDPOWR;
		if(s.equalsIgnoreCase("DIPOWR")) return SVM_SYSCALL.P_DIPOWR;
		if(s.equalsIgnoreCase("DRPOWR")) return SVM_SYSCALL.P_DRPOWR;
		if(s.equalsIgnoreCase("DDPOWR")) return SVM_SYSCALL.P_DDPOWR;
		Util.IERR(""+s);
		return 0;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	private static final boolean TESTING = true;
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("ProfileDescr.Write: " + this);
		DSEG.write(oupt);
		oupt.writeKind(kind);
//		oupt.writeShort(ModuleIO.chgType(tag));
		tag.write(oupt);
		oupt.writeShort(npar);
		oupt.writeShort(pKind);
//		oupt.writeShort(nparbyte);
		oupt.writeString(DSEG.ident);
		
		//public Vector<Variable> imports;
//		oupt.writeShort(imports.size());
//		for(Variable par:imports) {
//			if(TESTING) {
//				par.write(oupt);
//			} else {
//				par.tag.write(oupt);
//			}
//		}
		oupt.writeShort(params.size());
		for(Tag par:params) {
			par.write(oupt);
		}

		returAddr.write(oupt);
		if(export != null) {
			oupt.writeBoolean(true);
			exportTag.write(oupt);
//			export.write(oupt);
		} else oupt.writeBoolean(false);
		
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
		prf.npar = inpt.readShort();
		prf.pKind = inpt.readShort();
//		prf.nparbyte = inpt.readShort();
		String segID = inpt.readString();
		prf.DSEG = (DataSegment) Segment.lookup(segID);
		System.out.println("ProfileDescr.read: DSEG="+prf.DSEG);
		
		//public Vector<Variable> imports;
//		prf.imports = new Vector<Variable>();
		prf.params = new Vector<Tag>();
		int n = inpt.readShort();
		System.out.println("ProfileDescr.read: nPar="+n);
		for(int i=0;i<n;i++) {
//			if(TESTING) {
//				int kind = inpt.readKind();
//				Variable par = Variable.read(inpt, kind);
//				System.out.println("ProfileDescr.read: par="+par);
//				prf.imports.add(par);
//			} else {
//				Tag ptag = Tag.read(inpt);
//				Variable par = (Variable) Global.getMeaning(ptag.val);
//				System.out.println("ProfileDescr.read: par="+par);
//				prf.imports.add(par);
//			}
			prf.params.add(Tag.read(inpt));
		}
		
		prf.returAddr = MemAddr.read(inpt);
		System.out.println("ProfileDescr.read: returAddr="+prf.returAddr);
		boolean present = inpt.readBoolean();
		if(present) {
//			int kind = inpt.readKind();
//			prf.export = Variable.read(inpt, kind);
			prf.exportTag = Tag.read(inpt);
			System.out.println("ProfileDescr.read: exportTag="+prf.exportTag);
		}
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("ProfileDescr.Read: " + prf);
		prf.print("   ");
//		Util.IERR("");
		return prf;
	}



}
