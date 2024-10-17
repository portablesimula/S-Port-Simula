package comn;

import static comn.Util.*;

@SuppressWarnings("unused")
public class quantity {

	public quantity prefqual; // For prefix or qualification;
	public brecord descr;
	public quantity next;
	public brecord encl;
    public quantity match;

	public String symb;
    public int type,kind,categ,special; //  see below;
    public int protect;                 //  see below;
    public int visible;                 //  see below;
    public int plev;
    public int dim;                     //  see below;
    public int virtno;                  //  see below;
    public int ftag;                    //  see below;
    public int line;                    //  see below;
    
    public String permt; // From PREDEF - E.G:  "!12! 2 *T_SETPOS1 *T_SETPOS2";
    public String languagestring;
    public String extidentstring;


	

    /************************************************************************************
     * fetchquant:
     * 
     * <quantityDescriptor> ::= <quantityHead> <markSequence>? <quantityList>?
     * 
     *  	<quantityList> ::=
     *     		begList
     *				<quantityDescriptor>* -- for each new virtual in virt-list
     *			                           -- NOTE: the virtuals MUST be first
     *			    <quantityDescriptor>* -- for each elt in fpar-list for which:
     *					                   --  descr is not extbrecord OR descr.status='S'
     *		    endList
     *
     *		<markSequence> ::= ....   See: scanMarkSequence()
     *
     */
	private static boolean doGenerateQuantity;
	public static boolean fetchquant() {
		if(TESTING>2) System.out.println("BEGIN QUANT.fetchquant: Next key="+edKey(key));
		doGenerateQuantity=false;
		readQuantityHead();
		scanMarkSequence();
		// NOTE: next key has been read ***;
//		System.out.println(edIndent()+q);
//		if(TESTING>2) System.out.println("END QUANT.fetchquant: "+q.ident+", Next key="+edKey(key));
		System.out.println(edIndent()+XQUANTtoString());
		if(TESTING>2) System.out.println("END QUANT.fetchquant: "+xident+", Next key="+edKey(key));
		return(doGenerateQuantity);
	}

	
    /************************************************************************************
     * readQuantityHead:
     *      
     *      The format of a quantityHead descriptor is
     *
     *      <kind*8 + categ>1B  -- NOTE: this byte must be less than lowKey
     *      <type + (if prefix or qual then 128 else 0)>1B
     *      if prefix/qual marked then "qualification identifier"
     *      <clf + (if procedure parameter then 128 else 0)>1B
     * NEI  if NOT standard procedure parameter then
     * NEI    ? if sysattrfile then <RTStagbase of quantity + 1>I
     * NEI    ? else                <exttag of quantity + 1>I
     * NEI  endif
     *      <ftag>I
     *      <xidentstring>I
     *
     */
	public static void readQuantityHead() {

          xcateg=key;
          if(xcateg >= 8) { // *** not simple;
             xkind=key/8;
             xcateg=key-(xkind*8); // xcateg=mod(key,8);
          } else xkind=0;
          if(TESTING>2) System.out.println("Quantity.readQuantityHead: xcateg="+xcateg+", xkind="+xkind);

//          xtype:= loadchar(inpt,p); p:=p+1;
          xtype=inpt.inbyte();
          if(TESTING>2) System.out.println("Quantity.readQuantityHead: xtype="+xtype);
          if(xtype >= 128) { // *** prefix;
        	  xtype=xtype-128;
//             key:=loadchar(inpt,p);
//             simsymbol:-inpt.sub(p+2,rank(key)); DEFIDENT;
//             p:=p+1+rank(key);
//             xprefqhi:=hashhi; xprefqlo:=hashlo;
        	  xprefix=inpt.intext();
        	  xprefq=DEFIDENT(xprefix);
              if(TESTING>2) System.out.println("Quantity.readQuantityHead: xprefq="+GETIDENT(xprefq));
          }
//
//          xclf := loadchar(inpt,p); p:=p+1;
          int xclf=inpt.inbyte();
          if(TESTING>2) System.out.println("Quantity.readQuantityHead: xclf="+xclf);

          if(xclf>=128) { // *** procedure parameter;
        	  doGenerateQuantity=true;
        	  xclf=xclf-128;
        	  if(sysattrfile) ; // no proc param tags;
        		  else {
        			  xftag=inpt.in2byte()-1;
                	  if(TESTING>2) System.out.println("Quantity.readQuantityHead-1: xftag="+xftag);
        		  }
//             goto SETDID;
       		  xident=""; //:-dummybox;
    		  // !xidentstring unch.;
          } else {

//        	  int xftag=CURF.nextNumber()-1;
        	  xftag=inpt.in2byte()-1;
        	  if(TESTING>2) System.out.println("Quantity.readQuantityHead-2: xftag="+xftag);
//
// 		      key:=loadchar(inpt,p); p:=p+1;
        	  key=inpt.inbyte();
        	  if(TESTING>2) System.out.println("Quantity.readQuantityHead: key="+edKey(key));
        	  if(key != 0) {
//                simsymbol:-xidentstring:-inpt.sub(p+1,rank(key));
//             	  p:=p+rank(key);
        		  xident=inpt.intext(key);
        		  if(TESTING>2) System.out.println("Quantity.readQuantityHead: xident="+xident);
        		  if(xkind==K_class) {
        			  if(xclf==Clf004 || xclf==Clf009) xhasCode=true; // system class has code always;
//                	  DEFIDENT; goto SETID;
        			  doGenerateQuantity=true;
        		  }
//             if LOOKUPid then begin
//                !*** true if identifier referenced in source module ;
//%+D               seen:=true;
//    SETID:      doGenerateQuantity:=true; xident:-boxof(hashhi,hashlo);
//             end
//             else begin xident:-none; ! doGenerateQuantity:= false; end;
        	  } else {
        		  //LABEL("SETDID:");
        		  xident=""; //:-dummybox;
        		  // !xidentstring unch.;
        	  }
          }
		  if(TESTING>2) System.out.println("Quantity.readQuantityHead: QUANTITY-HEAD: xkind="+xkind+", xcateg="+xcateg+", xtype="+xtype
				  			+", xprefq="+GETIDENT(xprefq)+", xclf="+xclf+", xftag="+xftag+", xident="+xident);
//		  EXIT();
	}

	
    /************************************************************************************
     * scanMarkSequence:
     *      
     *		<markSequence> ::=
     *
     */
	public static void scanMarkSequence() {
		CHECKMARK: while(true) {
			key=inpt.inbyte();
				if(TESTING>2) System.out.println("AttributeFile.scanMarkSequence: CHECKMARK-LOOP key="+edKey(key));
				switch(key) {
				case forcMark: // *** force creation ***;
					doGenerateQuantity=true;  continue CHECKMARK;
				case protMark: // *** force creation ***;
					xprotect=inpt.inbyte();   continue CHECKMARK;
				case dimMark: // *** dim ***;
					xdim=inpt.inbyte();       continue CHECKMARK;
				case overMark: // *** overloaded ***;
					xlongindic=inpt.inbyte(); continue CHECKMARK;

				case specMark: // *** special ***;
					NOTIMPL("");
					//                if xkind=K_rep then begin
					//                   xlanguage:=nextNumber; xextident:=nextNumber;
					//                   continue CHECKMARK;
					//                end;
					//                xspecial:=loadchar(inpt,p); p:=p+1;
					//                if xspecial > '!128!' then begin
					//                   xspecial:=char(rank(xspecial)-128);
					//                   xarrlo:=loadchar(inpt,p); p:=p+1 end
					//                else begin
					//         GETARR:   gettext; DEFCONST;
					//                   xarrhi:=hashhi; xarrlo:=hashlo end;
					continue CHECKMARK;
					//             end;

				case xMark: // moduleid: never notext when of interest;
					NOTIMPL("");
					//                gettext; DEFCONST;
					//                xmodulhi:=hashhi; xmodullo:=hashlo;
					//                     ! checkcode: never notext when of interest;
					//                gettext; DEFCONST;
					//                xcheckhi:=hashhi; xchecklo:=hashlo;
					//   xyMark:   ! language: zero if no language (i.e. SIMULA for main);
					//                gettext; DEFIDENT;
					//                xlanguage:=rank(hashhi)*256+rank(hashlo);
					//                     ! extident: DEFCONST("?") if no extident;
					//                gettext; DEFCONST;
					//                xextident:=rank(hashhi)*256+rank(hashlo);
					//                continue CHECKMARK;
					//             end;
					//
				case yMark:
					//                goto xyMark;
					//   xyMark:   ! language: zero if no language (i.e. SIMULA for main);
					//	                gettext; DEFIDENT;
					//	                xlanguage:=rank(hashhi)*256+rank(hashlo);
					//	                     ! extident: DEFCONST("?") if no extident;
					//	                gettext; DEFCONST;
					//	                xextident:=rank(hashhi)*256+rank(hashlo);
					//	                continue CHECKMARK;
					//             end;
					xlanguagestring=inpt.gettext();
					xlanguage=DEFIDENT(xlanguagestring);
					if(TESTING>2) System.out.println("AttributeFile.scanMarkSequence'case yMark: xlanguage="+GETIDENT(xlanguage));
					xextidentstring=inpt.gettext();
					xextident=DEFIDENT(xextidentstring);
					if(TESTING>2) System.out.println("AttributeFile.scanMarkSequence'case yMark: xextident="+GETIDENT(xextident));
					continue CHECKMARK;

				case nestMark: // *** for/connect vars ;
					xconnests=inpt.inbyte(); continue CHECKMARK;

				case thisMark: // *** inr, hasCode, thisused ;
					int nxtc=inpt.inbyte();
					if(nxtc >= 64) { xthisused=true; nxtc=nxtc-64; }
					if(nxtc >= 32) { xhasCode =true; nxtc=-32; }
					// if nxtc <> '!00!' then xinrtag:='!2!';
					xinrtag=nxtc; //!NOTE - also isGlobal !!!;
					continue CHECKMARK;

				case bufSwap: // *** buffer swap ;
					inpt.swapIbuffer(); continue CHECKMARK;

				case hidMark: // *** hidden list;
					NOTIMPL("");
					//                cvis:-xhidlist:-new idpack;
					//                while key=hidmark do begin
					//                   cvis:-cvis.next:-new idpack;
					//                   gettext; DEFIDENT;
					//                   cvis.idhi:=hashhi; cvis.idlo:=hashlo;
					//                   cvis.line:=1; !*** must be non-zero;
					//                   nextKey;
					//                end hidmarks;
					//                xhidlist:-xhidlist.next;
					//                ! goto NOMORE;
					//             end;
					//
					//             when NONE do ! goto NOMORE; ;
				default: break CHECKMARK;
				} // case;
		} // end CHECKMARK LOOP
		//   NOMORE: !*** next key has been read ***;
		if(TESTING>2) System.out.println("END AttributeFile.scanMarkSequence: Next key="+edKey(key));
	}


	public String toString() {
		StringBuilder sb=new StringBuilder();
//		if(kind==K_class) sb.append("\n").append(edIndent());
//		if(parameter) sb.append("parameter ");
//		else
			sb.append(K_code(kind)).append(" ");
		if(symb!=null) sb.append(symb);
		if(prefqual != null) {
			String prefix=prefqual.symb;
			if(prefix!=null && prefix.length() > 0 ) sb.append(':').append(prefix);
		}
		sb.append(" ");
		if(ftag!=0)	sb.append("ftag=").append(ftag).append(" ");
		if(type!=14) sb.append("type=").append(edType(type)).append(" ");
//		sb.append("kind=").append(K_code(kind)).append(" ");
		sb.append("categ=").append(C_code(categ)).append(" ");
//		sb.append("clf=").append(clf).append(" ");
		if(special!=0)	sb.append("special=").append(special).append(" ");
		if(protect!=0)	sb.append("protect=").append(protect).append(" ");
		if(visible!=0)	sb.append("visible=").append(visible).append(" ");
		if(plev!=0)	sb.append("plev=").append(plev).append(" ");
		if(dim!=0)	sb.append("dim=").append(dim).append(" ");
		if(virtno!=0) sb.append(", virtno=").append(virtno);
		if(this instanceof extquantity xq) {
			if(xq.clf!=0) sb.append(", clf=").append(xq.clf);
			if(xq.longindic!=0) sb.append(", longindic=").append(xq.longindic);			
		}
		return(sb.toString());
	}

	public String OLD_toString() {
		StringBuilder sb=new StringBuilder();
		if(kind==K_subbl) sb.append("BLOCK");
		else if(kind==K_prefbl) sb.append("PREFB");
		else if(symb==null) sb.append("NOSYMB");
		else if(symb.length()==0) sb.append("NONAME");
		else sb.append(symb);

		if(prefqual != null) {
			sb.append(':');
			if(prefqual.symb != null)
				sb.append(prefqual.symb); else {
					brecord brc=prefqual.encl;
					if(brc!=null) sb.append('#').append(brc.blno);
				}
		}

		sb.append(", type=").append(edType(type));
		sb.append(", kind=").append(K_code(kind));
		sb.append(", categ=").append(C_code(categ));

		if(plev!=0)	sb.append(", plev=").append(plev);
		if(ftag!=0)	sb.append(", ftag=").append(ftag);
		if(dim!=0)	sb.append(", dim=").append(dim);
		if(virtno!=0) sb.append(", virtno=").append(virtno);
//		if protect='!1!' then outtext(" P");
//		if visible<>'!0!' then begin
//		if protect='!0!' then outtext(" V");
//		outint(rank(visible),0) end;
//		if match=/=none then begin NL;
//		outtext(" MATCH:"); if match.encl=/=none
//				and then match.encl.declquant=/=none
//				then outtext(match.encl.declquant.symb.symbol)
//				else outblno(match.encl);
//		end;
//		if descr=/=none then begin
//				if special<>'!0!' then outtext(" SPECIAL ");
//		if kind<>K_proc and kind<>K_class
//		then descr.dump(" DESCR ") else outblno(descr);
//		end;
//		inspect this quantity when extquantity do begin NL;
//		if module=/=none
//				then outtext(" MODUL:" &
//						module.declquant.symb.symbol);
//		outtext(" Clf:" & Cl_code(clf));
//		if longindic<>'!0!' then begin outtext(" LIX:");
//		outint(rank(longindic),0) end;
//		end;

		return(this.getClass().getSimpleName().toUpperCase()+": "+sb);
	}

	public void print(String title) {
		StringBuilder sb=new StringBuilder();
		if(kind==K_subbl) sb.append("BLOCK");
		else if(kind==K_prefbl) sb.append("PREFB");
		else if(symb==null) sb.append("NOSYMB");
		else if(symb.length()==0) sb.append("NONAME");
		else sb.append(symb);

		if(prefqual != null) {
			sb.append(':');
			if(prefqual.symb != null)
				sb.append(prefqual.symb); else {
					brecord brc=prefqual.encl;
					if(brc!=null) sb.append('#').append(brc.blno);
				}
		}

		sb.append(", type=").append(edType(type));
		sb.append(", kind=").append(K_code(kind));
		sb.append(", categ=").append(C_code(categ));

		if(plev!=0)	sb.append(", plev=").append(plev);
		if(ftag!=0)	sb.append(", ftag=").append(ftag);
		if(dim!=0)	sb.append(", dim=").append(dim);
		if(virtno!=0) sb.append(", virtno=").append(virtno);
//		if protect='!1!' then outtext(" P");
//		if visible<>'!0!' then begin
//		if protect='!0!' then outtext(" V");
//		outint(rank(visible),0) end;
//		if match=/=none then begin NL;
//		outtext(" MATCH:"); if match.encl=/=none
//				and then match.encl.declquant=/=none
//				then outtext(match.encl.declquant.symb.symbol)
//				else outblno(match.encl);
//		end;
//		if descr=/=none then begin
//				if special<>'!0!' then outtext(" SPECIAL ");
//		if kind<>K_proc and kind<>K_class
//		then descr.dump(" DESCR ") else outblno(descr);
//		end;
		
		if(TESTING>2) System.out.println(this.getClass().getSimpleName().toUpperCase()+": "+sb);
		if(this instanceof extquantity extq) {
			sb=new StringBuilder();
			if(extq.module !=null) {
				sb.append(" MODUL:");
				if(extq.module.declquant!=null)
					sb.append(extq.module.declquant.symb);
				else sb.append("NONAME");
			}
			sb.append(", clf=").append(Cl_code(extq.clf));
			if(extq.longindic!=0)	sb.append(", longindic=").append(extq.longindic);
			if(TESTING>2) System.out.println("   "+sb);
		}
//		inspect this quantity when extquantity do begin NL;
//		if module=/=none
//				then outtext(" MODUL:" &
//						module.declquant.symb.symbol);
//		outtext(" Clf:" & Cl_code(clf));
//		if longindic<>'!0!' then begin outtext(" LIX:");
//		outint(rank(longindic),0) end;
//		end;

	}

}
