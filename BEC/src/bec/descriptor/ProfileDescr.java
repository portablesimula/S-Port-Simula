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
import bec.util.ResolvedType;
import bec.util.Scode;
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
	
	public static boolean USE_RTStack = false; // Allocate Parameters on the Runtime Stack; Otherwise on the Profile DSEG Segment
//	public int ptag;
	public int bodyTag;   // peculiar
	String nature; // peculiar
	public String ident;  // peculiar
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
	
	private ProfileDescr(int kind, int tag) {
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
	public static ProfileDescr inProfile(int defkind) { // export ref(ProfileDescr) pr;
//	begin infix(WORD) tag,rtag,ptag,ExpTag,segid,count;
//	      range(0:MaxType) type,ExpType; infix(string) s,xs;
//	      range(0:MaxWord) i,npar,kind,nbyte,lng,nlocbyte;
//	      ref(LocDescr) v,w,fpar; ref(IntDescr) rut;
//	      infix(WORD) eid,xid,nid; range(0:1) wxt;
//	      range(0:MaxByte) nCnt,Pno(4),Cnt(4);

		int kind = defkind;
		int ptag = Scode.inTag();
//		System.out.println("ProfileDescr.inProfile: " + Scode.edTag(ptag));
//		pr.ptag = ptag;
		ProfileDescr pr = new ProfileDescr(Kind.K_ProfileDescr, ptag);
		if(Scode.nextByte() == Scode.S_EXTERNAL) {
			 // peculiar ::= external body:newtag nature:string xid:string
			Scode.inputInstr();
			Util.IERR("");
//	            kind:=P_EXTERNAL;
//	            InXtag(%rtag%);     -- Body'Tag
//	            nid:=inSymb;        -- Nature'String
//	            xs:=InString;       -- xid'String
//	            Ed(sysedit,xs); -- Deault EntryPoint
//	            rut:=NEWOBJ(K_IntRoutine,size(IntDescr));
//	            rut.adr:=noadr;
//	            --- Search for NATURE index ---
//	            s:=DICSMB(nid);
//	               if STEQ(s,"simuletta")  then kind:=P_SIMULETTA
//	--- pje                                     segid:=EnvCSEG;
//	--- pje     elsif STEQ(s,"library")    then kind:=P_SIMULETTA
//	--- pje                                     segid:=EnvCSEG;
//	            elsif STEQ(s,"assembly")   then kind:=P_ASM
//	            elsif STEQ(s,"assembler")  then kind:=P_ASM
//	            elsif STEQ(s,"cc")         then kind:=P_C
//	            elsif STEQ(s,"c")          then kind:=P_C
//	                  if OSID<>oUNIX386 then if OSID<>oUNIX386W
//	                  then pickup(sysedit); -- Remove Deault EntryPoint
//	                       edchar(sysedit,'_'); Ed(sysedit,xs);
//	                  endif endif;
//	            elsif STEQ(s,"fortran")    then kind:=P_FTN
//	            elsif STEQ(s,"pascal")     then kind:=P_PASCAL
//	            endif;
//	            --- set segid (and kind?) for interface to envir  --- pje
//	            if sysedit.nchr>2                                 --- pje
//	            then s.chradr:=@sysedit.chr; s.nchr:=2;           --- pje
//	                 if STEQ(s,"E@")                              --- pje
//	                 then segid:=EnvCSEG; -- kind:=P_SIMULETTA ?  --- pje
//	            endif endif                                       --- pje
//	            
//	            eid:=DefExtr(pickup(sysedit),segid);
//	%+D         if InputTrace <> 0
//	%+D         then ed(inptrace,"ENT="); edsymb(inptrace,eid); ITRC("*BEC") endif;
//	            rut.adr.kind:=extadr; rut.adr.rela.val:=0; rut.adr.smbx:=eid
//	%-E         rut.adr.sbireg:=0;
//	%+E         rut.adr.sibreg:=NoIBREG;
		} else if(Scode.nextByte() == Scode.S_INTERFACE) {
			 // peculiar ::= interface pid:string
			Scode.inputInstr();
			Scode.inString();
			kind = Kind.P_INTERFACE;
		} else if(Scode.nextByte() == Scode.S_KNOWN) {
			// peculiar	::= known body:newtag kid:string
			Scode.inputInstr();
			kind = Kind.P_KNOWN;
			int rtag = Scode.inTag();
			String xid = Scode.inString();
			Util.IERR("");
			kind = getKnownKind(xid);
		} else if(Scode.nextByte() == Scode.S_SYSTEM) {
			 //	peculiar ::= system body:newtag sid:string
			Scode.inputInstr();
			Util.IERR("");
			kind = Kind.P_SYSTEM;
			int rtag = Scode.inTag();
//	%+S         xid:=InExtr('E',EnvCSEG);
			String xid = Scode.inString();
//	%+S         rut:=NEWOBJ(K_IntRoutine,size(IntDescr));
//	%+S         if PsysKind=P_KNL
//	%+S         then rut.adr.kind:=knladr; rut.adr.knlx:=xid
//	%+S         else rut.adr.kind:=extadr; rut.adr.smbx:=xid endif;
//	%+S         rut.adr.rela.val:=0;
//	%+S %-E     rut.adr.sbireg:=0;
//	%+SE        rut.adr.sibreg:=NoIBREG;
//	%+S         if PsysKind <> 0 then kind:=PsysKind
//	%+S -- ????????  rut.adr.segid.val:=0  ?????????????????
//	%+S         else --- Search for inline index ---
			kind = getSysKind(xid);
//	%+S         endif;
		}
		
		if(! USE_RTStack) {
			pr.imports = new Vector<Variable>();
			
//			String pID = "Name";//Scode.TAGTABLE[profileTag];
			String pID = Scode.edTag(ptag);
			pr.DSEG = new DataSegment("DSEG_" + Global.moduleID + '_' + pID, Kind.K_SEG_DATA);

		}
		int npar = 0;
		int nbyte = 0;
		LocDescr fpar = null;
//	      repeat InputInstr while CurInstr=S_IMPORT
//	      do InTag(%ptag%); type:=intype;
		Scode.inputInstr();
//		System.out.println("ProfileDescr.inProfile: BEFORE IMPORT: " + Scode.edInstr(Scode.curinstr));
		LocDescr w = null;
		while(Scode.curinstr == Scode.S_IMPORT) {
			 // import_definition ::= import parm:newtag quantity_descriptor
			 //   quantity_descriptor ::= resolved_type < Rep count:number >?
			 //		 * 
			 //		  resolved_type
			 //		  		::= resolved_structure | simple_type
			 //		  		::= INT range lower:number upper:number
			 //		  		::= SINT
			if(USE_RTStack) {
//				int ptag = Scode.inTag();
//				QuantityDescriptor quant = new QuantityDescriptor();
				ResolvedType type = new ResolvedType();
				int repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
				System.out.println("ProfileDescr'IMPORT: " + Scode.edTag(ptag) + " " + type);
				int lng = DataType.typeSize(type.tag);
	
				if(lng == 0) Util.IERR("Illegal Type on Parameter");
				nbyte = nbyte + lng;
//				v.rela = nbyte;
				LocDescr v = LocDescr.ofParameter(ptag, type, nbyte);
				if(npar == 0) fpar = v; else w.nextag = ptag;
				System.out.println("ProfileDescr'IMPORT-2: " + Scode.edTag(ptag) + " " + v);
				Util.IERR("");
				if(repCount > 1) {
					nbyte = nbyte + (repCount * lng)-lng;
//	%+C           	if nCnt >= 4
//	%+C           	then IERR("MINUT: Too many rep-params"); nCnt:=3 endif;
//	%+C           	if count.val>255 then IERR("MINUT: too large count") endif;
//	              	Pno(nCnt):=npar; Cnt(nCnt):=count.val; nCnt:=nCnt+1;
					Util.IERR("SJEKK DETTE");
				}
				npar = npar + 1;
				w = v;
				Global.intoDisplay(v,ptag);
			} else {
				pr.imports.add(Variable.ofIMPORT(pr.DSEG));
				
			}
			
			Scode.inputInstr();
		}

//	      wxt:=0;
		if(Scode.curinstr == Scode.S_EXIT) {
			if(USE_RTStack) {
//	      then v:=NEWOBJ(K_Import,size(LocDescr)); v.type:=T_PADDR;
//	           v.rela:=AllignFac; -- Offset of return address in stack head
//	           InTag(%ptag%); wxt:=qEXIT; IntoDisplay(v,ptag);
//	           InputInstr;
				Util.IERR("NOT IMPL");
			} else {
				pr.exit = Variable.ofEXIT(pr.DSEG);
				Scode.inputInstr();
			}
		} else if(Scode.curinstr == Scode.S_EXPORT) {
			if(USE_RTStack) {
//	      then InTag(%ExpTag%); ExpType:=intype;
//	%+E        if    ExpType=T_BYT1 then ExpType:=T_WRD4 --- NY: FOR TEST ?????
//	%+E        elsif ExpType=T_BYT2 then ExpType:=T_WRD4
//	%+E        elsif ExpType=T_WRD2 then ExpType:=T_WRD4 endif;
//	           v:=NEWOBJ(K_Export,size(LocDescr)); v.type:=ExpType;
//	           v.rela:=(4+AllignFac)+nbyte; IntoDisplay(v,ExpTag);
//	           InputInstr;
				Util.IERR("NOT IMPL");
			} else {
				pr.export = Variable.ofEXPORT(pr.DSEG);				
				Scode.inputInstr();
			}
		}
		if(Scode.curinstr != Scode.S_ENDPROFILE)
			Util.IERR("Missing ENDPROFILE. Got " + Scode.edInstr(Scode.curinstr));

		if(USE_RTStack) {
//	      pr:=NEWOBJ(K_ProfileDescr,size(ProfileDescr:npar));
//	      pr.npar:=npar; pr.Pkind:=kind; pr.type:=ExpType;
//	      pr.nparbyte:=nbyte; pr.WithExit:=wxt;
//	      --- Increment relative addresses by size of import block ---
//	      --- and fill Profile's Parameter - Specifications        ---
//	      v:=fpar; i:=0;
//	      repeat while i < npar
//	      do v.rela:=((4+AllignFac)+nbyte)-v.rela; pr.par(i).type:=v.type;
//	         pr.par(i).count:=1;
//	         v:=DISPL(v.nextag.HI).elt(v.nextag.LO); i:=i+1;
//	      endrepeat;
//	      repeat while nCnt <> 0
//	      do nCnt:=nCnt-1; pr.par(Pno(nCnt)).count:=Cnt(nCnt) endrepeat;
//	      IntoDisplay(pr,tag);
//	      if rut<>none then rut.type:=ExpType; IntoDisplay(rut,rtag) endif;
			Util.IERR("NOT IMPL");
		}
		
//		Global.dumpDISPL("END PROFILE: ");
//		pr.print("   ");
//		Util.IERR("");
		return pr;
	}

	@Override
	public void print(final String indent) {
		String profile = "PROFILE " + Scode.edTag(tag);
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
		String profile = "PROFILE " + Scode.edTag(tag);
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
		Util.IERR("");
		//--- Search for inline index ---
//		if(s.equalsIgnoreCase("GTOUTM")) return Kind.P_GTOUTM;
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
		return 0;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("ProfileDescr.Write: " + this);
		oupt.writeKind(kind);
		oupt.writeShort(ModuleIO.chgType(tag));
//		oupt.writeKind(Pkind);
		oupt.writeShort(nparbyte);
//		oupt.writeShort(imports.size()); // npar
//		for(Variable imprt:imports) imprt.write(oupt);
//		if(exit   != null) exit.write(oupt);
//		if(export != null) export.write(oupt);
//		Util.IERR("");
	}

	public static ProfileDescr read(AttributeInputStream inpt) throws IOException {
		int tag = inpt.readShort();
		tag = InsertStatement.current.chgInType(tag);
		ProfileDescr prf = new ProfileDescr(Kind.K_RecordDescr, tag);
		prf.nparbyte = inpt.readShort();
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("ProfileDescr.Read: " + prf);
		prf.print("   ");
//		Util.IERR("");
		return prf;
	}



}
