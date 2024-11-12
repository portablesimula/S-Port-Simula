package bec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import bec.descriptor.CONST;
import bec.descriptor.Kind;
import bec.descriptor.LocDescr;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RecordDescr;
import bec.descriptor.Variable;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class InsertStatement {
    String modid;
    String check;
    String extid;
    int bias;
    int limit;
    
    public static InsertStatement current;

    /**
     * insert_statement
     * 		::= insert module_id:string check_code:string
     * 			external_id:string tagbase:newtag taglimit:newtag
     * 
     * 		::= sysinsert module_id:string check_code:string
     * 			external_id:string tagbase:newtag taglimit:newtag
     * 
     * @param sysmod when SYSINSERT
     * @throws IOException 
     * @throws  
     */
	public InsertStatement(boolean sysmod) {
//	 begin infix(WORD) modid,check,bias,limit,LinTab,AtrElt,AtrNam,RelElt;
//	       infix(WORD) dx,dxlim,tg,i,smbx,segid,extid;
//	       range(0:MaxByte) n,clas,segtype; ref(ModElt) me;
//	       infix(DataType) dt; range(0:MaxType) tx;
//	       ref(ProfileDescr) prf; range(0:MaxByte) npar;
//	       ref(Descriptor) d; Boolean TagMapping;
//	       infix(String) action,xid,s,buf; range(0:MaxWord) dlng,Magic;
//
		modid = Scode.inString();
		check = Scode.inString();
		extid = Scode.inString();
		bias = Scode.inTag();
		limit = Scode.inTag();

		if(Global.ATTR_INPUT_TRACE)
			System.out.println("**************   Begin  -  Input-module  " + modid + "  " + check + "   **************");
		try {
			current = this;
//			readPreamble();
			
			readDescriptors();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(Global.ATTR_INPUT_TRACE)
			System.out.println("**************   Endof  -  Input-module  " + modid + "  " + check + "   **************");
		Global.dumpDisplay("END INSERT: ");
		Util.IERR("");
	}
	
	public int chgInType(int tx) { // export range(0:MaxType) t;
//	begin infix(WORD) n;
//	      if tx <= T_max then t:=tx
//	      else n.val:=tx-(T_max+1);
//	           if TYPMAP(n.HI)=none then IERR("ChgInType-1"); t:=0;
//	           else t:=TYPMAP(n.HI).elt(n.LO).val endif;
//	      endif;
		int t = 0;
		if(tx <= Scode.T_max) t = tx; else {
			t = tx - Scode.T_max + bias - 2;
		}
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("chgInType xTag:" + tx + " ==> " + Scode.edTag(t));
//		Util.IERR("SJEKK DETTE");
		return t;
	}
	
	private void readDescriptors() throws IOException {
		String fileName = Global.getAttrFileName(modid, ".AT2");
		AttributeInputStream inpt = new AttributeInputStream(new FileInputStream(fileName));
		if(Global.ATTR_INPUT_TRACE) System.out.println("ATTRIBUTE INPUT: " + fileName);
		int kind = inpt.readKind();
		if(kind != Kind.K_Module) Util.IERR("Missing MODULE");
		String modident = inpt.readString();
		String modcheck = inpt.readString();
//		System.out.println("**************   Begin  -  Input-module  " + modident + "  " + modcheck + "   **************");
		if(! modident.equalsIgnoreCase(modid)) Util.IERR("WRONG modident");
		
//	       ------ Read Descriptors ------
		LOOP:while(true) {
			kind = inpt.readKind();
			if(kind == Kind.K_EndModule) break LOOP;
			switch(kind) {
			case Kind.K_SEG_DATA:		DataSegment.readObject(inpt, kind); break;
			case Kind.K_SEG_CONST:		DataSegment.readObject(inpt, kind); break;
			case Kind.K_SEG_CODE:		ProgramSegment.readObject(inpt); break;
			case Kind.K_Coonst:			CONST.read(inpt); break;
			case Kind.K_RecordDescr:	RecordDescr.read(inpt); break;
			case Kind.K_Attribute:		LocDescr.read(inpt, kind); break;
			case Kind.K_GlobalVar:		Variable.read(inpt, kind); break;
			case Kind.K_LocalVar:		Variable.read(inpt, kind); break;
			case Kind.K_ProfileDescr:	ProfileDescr.read(inpt); break;
			case Kind.K_Import:			Variable.read(inpt, kind); break;
			case Kind.K_Export:			Variable.read(inpt, kind); break;
			case Kind.K_Retur:			Variable.read(inpt, kind); break;
			default: Util.IERR("MISSING: " + Kind.edKind(kind));
			}
		}
		
		
//	       ------ Read Descriptors ------
//	       EnvLocate(modinpt,modlab.DescLoc+1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Read Descriptors *** LOC = ");
//	%+D         outint(EnvLocation(modinpt)); outstring(", LNG = ");
//	%+D         outword(modlab.sDesc); OutImage;
//	%+D    endif;
//	       d:=NEWOBX(Word2Size(modlab.sDesc));
//	       buf.chradr:=Ref2Name(d); buf.nchr:=modlab.sDesc;
//	       EnvInBytes(modinpt,buf);
//	       if status<>0 then FILERR(modinpt,"MINUT.Rdsc-1") endif;
//
//	       --- Perform Relocations ---
//	       if TagMapping then dx.val:=0; dxlim.val:=modlab.nTmap-1
//	       else dx:=bias; dxlim:=limit endif;
//	       repeat while dx.val <= dxlim.val
//	       do d.type:=ChgInType(d.type);
//	          case 0:K_Max (d.kind)
//	          when K_ExternVar,K_ExtLabel,K_ExtRoutine:
//	               d qua ExtDescr.adr.smbx:=
//	                     ChgInSmb(d qua ExtDescr.adr.smbx);
//	               dlng:=Size2Word(size(ExtDescr))
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_ExternVar  then S:="ExternVar: "
//	%+D              elsif d.kind=K_ExtRoutine then S:="ExtRoutine: "
//	%+D              else S:="ExtLabel: " endif; outstring(S); Print(d);
//	%+D            endif;
//	          when K_RecordDescr:
//	               dlng:=Size2Word(size(RecordDescr))
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("RecordDescr: "); Print(d) endif;
//	          when K_TypeRecord:
//	               d qua TypeRecord.pntmap:=
//	                     ChgInSmb(d qua TypeRecord.pntmap);
//	               dlng:=Size2Word(size(TypeRecord))
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("TypeRecord: "); Print(d) endif;
//	          when K_ProfileDescr:
//	               prf:=d; npar:=prf.npar; dlng:=Size2Word(size(ProfileDescr:npar))
//	               dlng:=((dlng+3)/4)*4; -- To force 4-byte allignment
//	               repeat while npar <> 0
//	               do npar:=npar-1;
//	                  prf.par(npar).type:=ChgInType(prf.par(npar).type);
//	               endrepeat;
//	%+D            if ModuleTrace <> 0 then
//	%+D            outstring("ProfileDescr: "); Print(d) endif;
//	          when K_Attribute,K_Import,K_Export:
//	               dlng:=Size2Word(size(LocDescr))
//	%+D            if ModuleTrace <> 0
//	%+D            then if d.kind=K_Attribute then S:="Attribute: "
//	%+D              elsif d.kind=K_Import then S:="Parameter: "
//	%+D              else S:="Export: " endif; outstring(S); Print(d);
//	%+D            endif;
//	          otherwise ERROR("Unknown Descriptor in AttrFile");
//	%+D                 outword(d.kind); Print(d);
//	          endcase;
//	          if TagMapping
//	          then tg.val:=TAGTAB(dx.HI).elt(dx.LO).val+bias.val;
//	               IntoDisplay(d,tg);
//	          else IntoDisplay(d,dx) endif;
//	          d:=d+Word2Size(dlng); dx.val:=dx.val+1;
//	       endrepeat;
//
//	       EnvClose(modinpt,nostring);
//	       if status<>0 then IERR("MINUT.InputModule-3") endif; modinpt:=0;
//
//	       ------ Release TAGTAB, SMBMAP & TYPMAP ------
//	       if TagMapping
//	       then i.val:=modlab.nTmap; n:=i.HI;
//	            repeat DELETE(TAGTAB(n)); TAGTAB(n):=none
//	            while n<>0 do n:=n-1 endrepeat;
//	       endif;
//	       i.val:=modlab.nSymb; n:=i.HI;
//	       repeat DELETE(SMBMAP(n)); SMBMAP(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//	       i.val:=modlab.nType; n:=i.HI;
//	       repeat DELETE(TYPMAP(n)); TYPMAP(n):=none
//	       while n<>0 do n:=n-1 endrepeat;
//	E1:
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("******************   End of  -  Input-module  ");
//	%+D         outsymb(modid); outstring("  "); outsymb(check);
//	%+D         outstring("   ******************"); outimage;
//	%+D    endif;
			Util.IERR("Parse.inputModule: NOT IMPLEMENTED");
	}

	private void readPreamble() {
//	       ------ Read Module Header ------
//	       EnvLocate(modinpt,1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Read Module Header *** LOC = ");
//	%+D         outint(EnvLocation(modinpt)); OutImage;
//	%+D    endif;
//	       buf:=Name2Buf(@modlab,size(ModuleHeader));
//	       EnvInBytes(modinpt,buf);
//	       if status<>0 then FILERR(modoupt,"MINUT.Rsmb-3") endif;
//	%+E    if Size2Word(size(ProfileDescr:2))=12 then Magic:=3327
//	%+E    else Magic:=3227; -- HYBRID Attribute file ---
//	%+ED        if Size2Word(size(ProfileDescr:2))<>10
//	%+ED        then EdWrd(errmsg,Size2Word(size(ProfileDescr:2)));
//	%+ED             IERR(" MINUT:Magic-1");
//	%+ED        endif;
//	%+E    endif;
//	%+E    if modlab.Magic=3227
//	%+E    then WARNING("HYBRID Attribute input file") endif;
//	%-E    if (modlab.Magic <> 3127)  or (modlab.Layout <> 2)
//	%+E    if (modlab.Magic <> Magic) or (modlab.Layout <> 2)
//	       then ed(errmsg,"Illegal Attribute File: ");
//	            edsymb(errmsg,modid); ed(errmsg,", Magic:");
//	            edwrd(errmsg,modlab.magic); WARNING(" ");
//	       endif;
//	       if sysmod then CombAtr:=modlab.Comb endif;
//
//	       ------ Read Symbol Table ------
//	       EnvLocate(modinpt,modlab.SymbLoc+1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Read Symbol Table *** LOC = ");
//	%+D         outint(EnvLocation(modinpt)); OutImage;
//	%+D    endif;
//	       s.chradr:=@sysedit.chr; i.val:=0;
//	       repeat while i.val < modlab.nSymb
//	       do s.nchr:=EnvInByte(modinpt);
//	          if status<>0 then FILERR(modinpt,"MINUT.Rsmb-1") endif;
//	          clas:=EnvInByte(modinpt);
//	          if status<>0 then FILERR(modinpt,"MINUT.Rsmb-2") endif;
//	          EnvInBytes(modinpt,s);
//	          if status<>0 then FILERR(modinpt,"MINUT.Rsmb-3") endif;
//	          case 0:sMAX (clas)
//	          when sSYMB: smbx:=DefSymb(s)
//	          when sMODL: LinTab.val:=EnvIn2Byte(modinpt);
//	                      if status<>0 then
//	                      FILERR(modinpt,"MINUT.Rsmb-5") endif;
//	                      RelElt.val:=EnvIn2Byte(modinpt);
//	                      if status<>0 then
//	                      FILERR(modinpt,"MINUT.Rsmb-5") endif;
//	                      smbx:=DefModl(s); me:=DICREF(smbx);
//	                      me.LinTab:=ChgInSmb(LinTab);
//	                      me.RelElt:=ChgInSmb(RelElt);
//	                      me.AtrElt:=AtrElt;
//	                      me.AtrNam:=AtrNam;
//	%+S                   if SYSGEN=0 then PutModule(smbx) endif;
//	%-S                   PutModule(smbx);
//	          when sSEGM: segtype:=EnvInByte(modinpt);
//	                      if status<>0 then
//	                      FILERR(modinpt,"MINUT.Rsmb-4") endif;
//	                      smbx:=DefSegm(s,segtype)
//	          when sEXTR: segid.val:=EnvIn2Byte(modinpt);
//	                      if status<>0 then
//	                      FILERR(modinpt,"MINUT.Rsmb-5") endif;
//	                      smbx:=DefExtr(s,ChgInSmb(segid))
//	%+C       otherwise IERR("MINUT.Rsmb-6")
//	          endcase;
//	          if SMBMAP(i.HI)=none
//	          then SMBMAP(i.HI):=NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	          SMBMAP(i.HI).elt(i.LO):=smbx; i.val:=i.val+1;
//	%+D       if ModuleTrace <> 0
//	%+D       then outstring("DEFSYMB "); outword(i.val);
//	%+D            outchar(' '); outword(smbx.val);
//	%+D            outchar(' '); outsymb(smbx); OutImage;
//	%+D       endif;
//	       endrepeat;
//
//	       ------ Update Module Header and Test ------
//	       modlab.modid:=ChgInSmb(modlab.modid);
//	       modlab.check:=ChgInSmb(modlab.check);
//	       if sysmod and (CombAtr>0)
//	       then limit.val:=bias.val+modlab.nXtag
//	       else if modlab.modid <> modid
//	            then ERROR("Wrong identification in module") endif;
//	            if modlab.check <> check
//	            then ERROR("Wrong check code in module") endif;
//	            if limit.val-bias.val <> modlab.nXtag
//	            then ERROR("Wrong no.of visible items in module") endif;
//	       endif;
//	       ------ Read Type Table ------
//	       EnvLocate(modinpt,modlab.TypeLoc+1);
//	%+D    if ModuleTrace <> 0
//	%+D    then outstring("*** Read Type Table *** LOC = ");
//	%+D         outint(EnvLocation(modinpt)); OutImage;
//	%+D    endif;
//	       i.val:=0;
//	       repeat while i.val < modlab.nType
//	       do EnvInbytes(modinpt,Name2Buf(@dt,size(DataType)));
//	          if status<>0 then FILERR(modoupt,"MINUT.Rtyp-1") endif;
//	          dt.pntmap:=ChgInSmb(dt.pntmap); tx:=ntype;
//	          repeat while tx > T_max
//	          do if dt=TTAB(tx) then goto T1 endif; tx:=tx-1 endrepeat;
//	          ntype:=ntype+1;
//	%+C       if ntype >= MaxType
//	%+C       then IERR("Too many types"); ntype:=MaxType/2 endif;
//	          TTAB(ntype):=dt; tx:=ntype;
//	     T1:  if TYPMAP(i.HI)=none
//	          then TYPMAP(i.HI):=NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	%+C       if i.hi >= MxpXtyp then IERR("DEFTYPE: TYPMAP overflow"); endif;
//	          TYPMAP(i.HI).elt(i.LO).val:=tx; i.val:=i.val+1;
//	%+D       if ModuleTrace <> 0
//	%+D       then outstring("DEFTYPE "); outword(T_max+i.val);
//	%+D            outchar(' '); outword(tx);
//	%+D            outchar(' '); EdType(sysout,tx); OutImage;
//	%+D       endif;
//	       endrepeat;
//
//	       ------ Read Tag-Mapping Table ------
//	       if modlab.nTmap=0 then TagMapping:=false
//	       else TagMapping:=true;
//	            EnvLocate(modinpt,modlab.TmapLoc+1); i.val:=0;
//	%+D         if ModuleTrace <> 0
//	%+D         then outstring("*** Read Tag-Mapping Table *** LOC = ");
//	%+D              outint(EnvLocation(modinpt));
//	%+D              outstring(", LNG = ");
//	%+D              outword(modlab.nTmap); OutImage;
//	%+D         endif;
//	            repeat while i.val < modlab.nTmap
//	            do tg.val:=EnvIn2Byte(modinpt);
//	               if status<>0 then FILERR(modinpt,"MINUT.Rmap-1") endif;
//	               if TAGTAB(i.HI)=none
//	               then TAGTAB(i.HI):=NEWOBZ(size(WordBlock)) endif;
//	               TAGTAB(i.HI).elt(i.LO):=tg;
//	               i.val:=i.val+1;
//	            endrepeat;
//	       endif;
//
//	       ------ Read Tag-Ident Table ------
//	%+SD   if (BECDEB <> 0) and (modlab.TgidLoc > 0)
//	%+SD   then EnvLocate(modinpt,modlab.TgidLoc+1); i:=bias;
//	%+SD        if ModuleTrace <> 0
//	%+SD        then outstring("*** Read Tag-Ident Table *** LOC = ");
//	%+SD             outint(EnvLocation(modinpt)); OutImage;
//	%+SD        endif;
//	%+SD        repeat while i.val < limit.val
//	%+SD        do smbx.val:=EnvIn2Byte(modinpt);
//	%+SD           if status<>0 then FILERR(modinpt,"MINUT.Rtid-1") endif;
//	%+SD           if TIDTAB(i.HI)=none
//	%+SD           then TIDTAB(i.HI):=NEWOBZ(size(WordBlock)) endif;
//	%+SD           TIDTAB(i.HI).elt(i.LO):=ChgInSmb(smbx);
//	%+SD           i.val:=i.val+1;
//	%+SD        endrepeat;
//	%+SD   endif;

	}
}
