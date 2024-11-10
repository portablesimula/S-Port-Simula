package bec;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import bec.compileTimeStack.DataType;
import bec.descriptor.Descriptor;
import bec.util.Array;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class ModuleIO {

//	%title ***   O u t p u t    M o d u l e   ***
	
	private static void writeDescriptors(int nXtag) throws IOException {
		AttributeOutputStream modoupt = new AttributeOutputStream(new FileOutputStream(Global.getAttrFileName(Global.modident, ".AT2")));
		modoupt.writeKind(Scode.S_MODULE);
		modoupt.writeString(Global.modident);
		modoupt.writeString(Global.modcheck);

//	       ------ Prepare Dependent Module info ------
//	       i.val:=0;
//	       repeat while i.val < DIC.nModl
//	       do i.val:=i.val+1;
//	          ChgSmb(DIC.Modl(i.HI).elt(i.LO));
//	       endrepeat;

//	       ------ Modify and Write Descriptors ------
//	       EnvLocate(modoupt,Size2Word(size(ModuleHeader))+1);
//	       modlab.DescLoc:=EnvLocation(modoupt)-1;
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Modify and Write Descriptors *** LOC = ");
//	%+D         outint(EnvLocation(modoupt)); OutImage;
//	%+D    endif;

		for(int i=0;i<nXtag;i++) {
			int tx = Global.TAGTAB.get(i);
			Descriptor d = Global.DISPL.get(tx);
			if(d == null) Util.IERR("External tag " + i + " = Tag " + tx + " is not defined (OutModule)");
			d.write(modoupt);
//	          case 0:K_Max (d.kind)
//	          when K_ExternVar,K_ExtLabel,K_ExtRoutine:
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_ExternVar  then S:="ExternVar: "
//	%+D              elsif d.kind=K_ExtRoutine then S:="ExtRoutine:"
//	%+D              else S:="ExtLabel: " endif; outstring(S); Print(d)
//	%+D            endif;
//	               -- Modify: d qua ExtDescr.type
//	               xd.kind:=d.kind;
//	               xd.type:=ChgType(d.type);
//	               xd.adr:=d qua ExtDescr.adr;
//	               xd.adr.smbx:=ChgSmb(xd.adr.smbx);
//	               buf.nchr:=Size2Word(size(ExtDescr))
//	%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-1") endif;
//	               buf.chradr:=@xd; EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-1") endif;
//	          when K_GlobalVar,K_IntLabel,K_IntRoutine:
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_GlobalVar  then S:="GlobalVar: "
//	%+D              elsif d.kind=K_IntRoutine then S:="IntRoutine:"
//	%+D              else S:="IntLabel: " endif; outstring(S); Print(d)
//	%+D            endif;
//	               if d qua IntDescr.adr.kind=0  -- No address attached
//	               then sx.val:=0;
//	                    d qua IntDescr.adr:=NewFixAdr(DSEGID,sx);
//	               endif;
//	               -- Modify: d qua IntDescr.type
//	               -- Modify: d qua IntDescr's address
//	               if    d.kind=K_GlobalVar  then xd.kind:=K_ExternVar
//	               elsif d.kind=K_IntRoutine then xd.kind:=K_ExtRoutine
//	               else xd.kind:=K_ExtLabel endif;
//	               xd.type:=ChgType(d.type);
//	               xd.adr:=ChgAdr(d qua IntDescr.adr);
//	               buf.nchr:=Size2Word(size(ExtDescr))
//	%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-2") endif;
//	               buf.chradr:=@xd; EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-2") endif;
//	          when K_TypeRecord:
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("TypeRecord: "); Print(d) endif;
//	               rd.kind:=K_TypeRecord; rd.type:=ChgType(d.type);
//	               rd.nbyte:=d qua RecordDescr.nbyte;
//	               rd.nbrep:=d qua RecordDescr.nbrep;
//	               rd.pntmap:=ChgSmb(d qua TypeRecord.pntmap);
//	               buf.nchr:=Size2Word(size(TypeRecord))
//	%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-4") endif;
//	               buf.chradr:=@rd; EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-4") endif;
//	          when K_ProfileDescr:
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("ProfileDescr: "); Print(d) endif;
//	               prf:=d; type:=d.type; d.type:=ChgType(type);
//	%+D            buf.nchr:=Size2Word(size(ProfileDescr:0))
//	%+D            if buf.nchr=6 then -- OK
//	%+D            elsif buf.nchr <> 8 then IERR("OUTMOD:Dsize-4") endif;
//	               buf.nchr:=6; --- TO FORCE RIGTH FORMAT OF PROFILEDESCR
//	               buf.chradr:=ref2name(prf); EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-5") endif;
//	               prf.type:=type; npar:=prf.npar px.val:=0;
//	               repeat while px.val < npar
//	               do par:=prf.par(px.val); px.val:=px.val+1;
//	                  EnvOutByte(modoupt,ChgType(par.type));
//	                  if status<>0 then FILERR(modoupt,"Wdescr-6") endif
//	                  EnvOutByte(modoupt,par.count);
//	                  if status<>0 then FILERR(modoupt,"Wdescr-7") endif
//	               endrepeat;
//	               if bAND(npar,1)=0  -- FORCE 4-BYTE ALLIGNMENT OF PROFILES
//	               then EnvOutByte(modoupt,0);
//	                    if status<>0 then FILERR(modoupt,"Wdescr-6x") endif
//	                    EnvOutByte(modoupt,0);
//	                    if status<>0 then FILERR(modoupt,"Wdescr-7x") endif
//	               endif;
//	          when K_Attribute,K_Parameter,K_Export:
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_Attribute then S:="Attribute: "
//	%+D              elsif d.kind=K_Parameter then S:="Parameter: "
//	%+D              else S:="Export: " endif; outstring(S); Print(d);
//	%+D            endif;
//	               -- Modify: d qua IntDescr.type
//	               ld.kind:=d.kind; ld.type:=ChgType(d.type);
//	               ld.rela:=d qua LocDescr.rela; ld.nextag.val:=0;
//	               buf.nchr:=Size2Word(size(LocDescr))
//	%+D            if buf.nchr <> 8 then IERR("OUTMOD:Dsize-4") endif;
//	               buf.chradr:=@ld; EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"Wdescr-8") endif;
//	          otherwise ERROR("Unknown Descriptor in display");
//	%+D                 outword(d.kind); Print(d);
//	          endcase;
//	       endrepeat;
		}
//	       modlab.sDesc:=(EnvLocation(modoupt)-1)-modlab.DescLoc;
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** End Descriptors *** LNG = ");
//	%+D         outword(modlab.sDesc); OutImage;
//	%+D    endif;

	}

	public static void outputModule(int nXtag) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("**************   Begin  -  Output-module  " + Global.modident + "  " + Global.modcheck + "   **************");

		writeDescriptors(nXtag);
		
//		writePreamble();
		
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("**************   Endof  -  Output-module  " + Global.modident + "  " + Global.modcheck + "   **************");
	}
	
	private static void writePreamble() throws IOException {
		AttributeOutputStream preamble = new AttributeOutputStream(new FileOutputStream(Global.getAttrFileName(Global.modident, ".AT3")));

//	       ------ Initiate Module Label ------
//	%-E    modlab.Magic:=3127;
//	%+E    if Size2Word(size(ProfileDescr:2))=12 then modlab.Magic:=3327
//	%+E    else modlab.Magic:=3227; WARNING("HYBRID Attribute output file");
//	%+ED        if Size2Word(size(ProfileDescr:2))<>10
//	%+ED        then EdWrd(errmsg,Size2Word(size(ProfileDescr:2)));
//	%+ED             IERR(" MINUT:Magic-2");
//	%+ED        endif;
//	%+E    endif;
//	       modlab.Layout:=2; modlab.Comb:=CombAtr;
//	       modlab.modid:=ChgSmb(modident);
//	       modlab.check:=ChgSmb(modcheck);
//	       modlab.nXtag:=nXtag; nXtag:=nXtag+1;
//	       modlab.sFeca:=0; modlab.FecaLoc:=0;
//	       modlab.nTmap:=0; modlab.TmapLoc:=0;
//
//	       ------ Write Type Table ------
//	       modlab.nType:=nXtyp;
//	       modlab.Typeloc:=EnvLocation(modoupt)-1;
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Write Type Table *** LOC = ");
//	%+D         outint(EnvLocation(modoupt)); OutImage;
//	%+D    endif;
//	       i.val:=0;
//	       repeat while i.val < nXtyp
//	       do tx:=TYPMAP(i.HI).elt(i.LO);
		for(int i=0;i<TYPMAP.size();i++) {
//		for(int tx:TYPMAP) {
			int tx = TYPMAP.get(i);
//	          dt:=TTAB(tx.val); i.val:=i.val+1;
			DataType dt = DataType.dataType(tx);
//	          if dt.pntmap.val <> 0 then dt.pntmap:=ChgSmb(dt.pntmap) endif;
//	          EnvOutbytes(modoupt,Name2Buf(@dt,size(DataType)));
//	          if status<>0 then FILERR(modoupt,"Wtyp-1") endif;
			System.out.println("ModuleIO'TYPMAP: i="+i+", tx="+tx+", dt="+dt);
			preamble.writeShort(i);
			preamble.writeShort(tx);
			Util.IERR("TEST DETTE"); // TODO: TEST DETTE
		}
//
//	       ------ Write Tag-ident Table ------
//	       modlab.TgidLoc:=0;
//	%+SD   if BECDEB > 1
//	%+SD   then i.val:=0; modlab.TgidLoc:=EnvLocation(modoupt)-1;
//	%+SD        if ModuleTrace <> 0
//	%+SD        then outstring("*** Write Tag-ident Table *** LOC = ");
//	%+SD             outint(EnvLocation(modoupt)); OutImage;
//	%+SD        endif;
//	%+SD        repeat while i.val < nXtag
//	%+SD        do tx:=TAGTAB(i.HI).elt(i.LO); i.val:=i.val+1;
//	%+SD           d:=DISPL(tx.HI).elt(tx.LO);
//	%+SD           if d=none then IERR("MINUT.OutputModule - 1") endif;
//	%+SD           sx:=TIDTAB(tx.HI).elt(tx.LO); sx:=ChgSmb(sx);
//	%+SD           EnvOut2byte(modoupt,sx.val);
//	%+SD           if status<>0 then FILERR(modoupt,"MINUT.OutWord") endif;
//	%+SD        endrepeat;
//	%+SD   endif;
//
//	       ------ Write Symbol Table ------
//	       modlab.SymbLoc:=EnvLocation(modoupt)-1; modlab.nSymb:=nXsmb;
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Write Symbol Table *** LOC = ");
//	%+D         outint(EnvLocation(modoupt)); OutImage;
//	%+D    endif;
//	       i.val:=0;
//	       repeat while i.val < nXsmb
//	       do smb:=DICREF(SMBMAP(i.HI).elt(i.LO)); i.val:=i.val+1;
//	          EnvOutByte(modoupt,smb.nchr); buf.nchr:=smb.nchr;
//	          if status<>0 then FILERR(modoupt,"MINUT.Wsmb-1") endif;
//	          case 0:sMax (smb.clas)
//	          when sSYMB: EnvOutByte(modoupt,sSYMB);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-2") endif;
//	               buf.chradr:=name(smb qua Symbol.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-3") endif;
//	          when sMODL: EnvOutByte(modoupt,sMODL);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-4") endif;
//	               buf.chradr:=name(smb qua ModElt.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-5") endif;
//	               sx:=ChgSmb(smb qua ModElt.LinTab);
//	               EnvOut2Byte(modoupt,sx.val);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-6") endif;
//	               sx:=ChgSmb(smb qua ModElt.RelElt);
//	               EnvOut2Byte(modoupt,sx.val);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-7") endif;
//	          when sSEGM: EnvOutByte(modoupt,sSEGM);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-8") endif;
//	               buf.chradr:=name(smb qua Segment.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-9") endif;
//	               EnvOutByte(modoupt,smb qua Segment.type);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-10") endif;
//	          when sPUBL: EnvOutByte(modoupt,sEXTR);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-11") endif;
//	               buf.chradr:=name(smb qua Public.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-12") endif;
//	               sx:=smb qua Public.segx;
//	               sx:=ChgSmb(DIC.Segm(sx.HI).elt(sx.LO));
//	               EnvOut2Byte(modoupt,sx.val);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-13") endif;
//	          when sEXTR: EnvOutByte(modoupt,sEXTR);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-14") endif;
//	               buf.chradr:=name(smb qua Extern.chr);
//	               EnvOutBytes(modoupt,buf);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-15") endif;
//	               sx:=ChgSmb(smb qua Extern.segid);
//	               EnvOut2Byte(modoupt,sx.val);
//	               if status<>0 then FILERR(modoupt,"MINUT.Wsmb-16") endif;
//	%+C       Otherwise IERR("MINUT.Wsmb-3");
//	          endcase;
//	       endrepeat;
//
//	       ------ Write Module Header and Close file ------
//	       EnvLocate(modoupt,1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Write Module Header *** LOC = ");
//	%+D         outint(EnvLocation(modoupt)); OutImage;
//	%+D    endif;
//	       buf:=Name2Buf(@modlab,size(ModuleHeader));
//	       EnvOutBytes(modoupt,buf);
//	       if status<>0 then FILERR(modoupt,"MINUT.Wsmb-3") endif;
//	       EnvClose(modoupt,nostring);
//	       if status<>0 then IERR("MINUT.OutputModule-4") endif; modoupt:=0;
//
//	       ------ Release TAGTAB, SMBMAP and TYPMAP ------
//	       i.val:=nXtag; n:=i.HI;
//	       repeat DELETE(TAGTAB(n)); TAGTAB(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//	       i.val:=modlab.nSymb; n:=i.HI;
//	       repeat DELETE(SMBMAP(n)); SMBMAP(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//	       i.val:=modlab.nType; n:=i.HI;
//	       repeat DELETE(TYPMAP(n)); TYPMAP(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("**************   End of  -  Output-module  ");
//	%+D         outsymb(modident); outstring("  ");
//	%+D         outsymb(modcheck);
//	%+D         outstring("   **************"); outimage;
//	%+D    endif;

		Util.IERR("");
	}

	
	private static Array<Integer> TYPMAP = new Array<Integer>();
	public static int chgType(int t) { // export range(0:MaxType) tx;
		int tx = 0;
		if(t <= Scode.T_max) tx = t; else {
			int n = 0;
			LOOP:for(int tt:TYPMAP) {
				if(tt == t) {
					n = tt;
					break LOOP;
				}
			}
			if(n == 0) {
				TYPMAP.add(t);
				n = TYPMAP.size();
			}
			tx = n + Scode.T_max + 1;
		}
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("CHGTYP " + Scode.edTag(t) + " ==> " + tx);
//		Util.IERR("SJEKK DETTE");
		return tx;
	}

}
