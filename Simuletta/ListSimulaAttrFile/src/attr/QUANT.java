package attr;

import static attr.Util.*;

public class QUANT {
	public String symb;
	boolean parameter;
    public int type,kind,categ,special; //  see below;
    public int protect;                 //  see below;
    public int dim;                     //  see below;
    public int ftag;                    //  see below;
    public int line;                    //  see below;
    
    String ident;
    String prefix;
    boolean thisused;
    boolean hasCode;
    int inrtag;
	public int clf; // "classific", see sortcodes ;
	public int longindic;
       
    //     LONGINDIC:   The rank of this variable is not zero if this is
    //                  a procedure that is overloaded on the parameter type.
    //                  -- see CHECKER1 for usage --
    //                  It is given after "classific" to attributefile,
    //                  as a negative number in front of "number of tags". ;
	public int connests;

	String extident;
	String language;
	
	String markList=" ";

	
    /************************************************************************************
     * fetchquant:
     ************************************************************************************
     * 
     * <quantityDescriptor> ::= <quantityHead> <markSequence>? <quantityList>?
     * 
     *  	<quantityList> ::=
     *     		begList
     *				<quantityDescriptor>* -- for each new virtual in virt-list
     *			                          -- NOTE: the virtuals MUST be first
     *			    <quantityDescriptor>*
     *		    endList
     *
     *		<markSequence> ::= ....   See: scanMarkSequence()
     *
     */
	@SuppressWarnings("unused")
	public static QUANT fetchquant() {
		if(TESTING>2) System.out.println("BEGIN QUANT.fetchquant: Next key="+edKey(key));
		QUANT q=new QUANT();
		q.readQuantityHead();
		q.scanMarkSequence();
		// NOTE: next key has been read ***;
//		System.out.println("Quantity.fetchquant: QUANT: "+q);
		System.out.println(edIndent()+q);
		if(TESTING>2) System.out.println("END QUANT.fetchquant: "+q.ident+", Next key="+edKey(key));
		return(q);
	}

	
    /************************************************************************************
     * readQuantityHead:
     ************************************************************************************
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
	@SuppressWarnings("unused")
	public void readQuantityHead() {

          categ=key;
          if(categ >= 8) { // *** not simple;
             kind=key/8;
             categ=key-(kind*8); // categ=mod(key,8);
          } else kind=0;
          if(TESTING>2) System.out.println("Quantity.readQuantityHead: categ="+categ+", kind="+kind);

          type=inpt.inbyte();
          if(TESTING>2) System.out.println("Quantity.readQuantityHead: type="+type);
          if(type >= 128) { // *** prefix;
        	  type=type-128;
        	  prefix=inpt.intext();
              if(TESTING>2) System.out.println("Quantity.readQuantityHead: prefix="+prefix);
          }
          clf=inpt.inbyte();
          if(TESTING>2) System.out.println("Quantity.readQuantityHead: clf="+clf);

          if(clf>=128) { // *** procedure parameter;
        	  parameter=true;
        	  clf=clf-128;
//        	  if(sysattrfile) ; // no proc param tags;
//        		  else {
        			  ftag=inpt.in2byte()-1;
                	  if(TESTING>2) System.out.println("Quantity.readQuantityHead-1: ftag="+ftag);
//        		  }
          } else {
        	  ftag=inpt.in2byte()-1;
        	  if(TESTING>2) System.out.println("Quantity.readQuantityHead-2: ftag="+ftag);

        	  key=inpt.inbyte();
        	  if(TESTING>2) System.out.println("Quantity.readQuantityHead: key="+edKey(key));
        	  if(key != 0) {
        		  ident=inpt.intext(key);
        		  if(TESTING>2) System.out.println("Quantity.readQuantityHead: xidentstring="+ident);
//        		  if(xkind==K_class) {
//        			  if(xclf==Clf004 || xclf==Clf009) xhasCode=true; // system class has code always;
////                	  DEFIDENT; goto SETID;
//        			  xident=xidentstring;
//        		  }
        	  }
          }
          if(TESTING>2)
        	  System.out.println("Quantity.readQuantityHead: QUANTITY-HEAD: kind="+kind+", categ="+categ+", type="+type
				  			+", xprefix="+prefix+", clf="+clf+", ftag="+ftag+", ident="+ident);
//		  EXIT();
	}

	
    /************************************************************************************
     * scanMarkSequence:
     ************************************************************************************
     *      
     *		<markSequence> ::=
     *
     */
	@SuppressWarnings("unused")
	public void scanMarkSequence() {
		CHECKMARK: while(true) {
			key=inpt.inbyte();
			if(TESTING>2) System.out.println("QUANT.scanMarkSequence: CHECKMARK-LOOP key="+edKey(key));
			switch(key) {
			case forcMark: // *** force creation ***;
				markList=markList+"forcMark "; /*doGenerateQuantity=true;*/  continue CHECKMARK;
			case protMark: // *** force creation ***;
				protect=inpt.inbyte();
				markList=markList+"protMark="+protect+" ";
				continue CHECKMARK;
			case dimMark: // *** dim ***;
				dim=inpt.inbyte();
				markList=markList+"dimMark="+dim+" ";
				continue CHECKMARK;
			case overMark: // *** overloaded ***;
				longindic=inpt.inbyte();
				markList=markList+"overMark="+longindic+" ";
				continue CHECKMARK;

			case specMark: // *** special ***;
				if(kind==K_rep) {
					int xlanguage=inpt.in2byte();
					int xextident=inpt.in2byte();
					markList=markList+"specMark1=("+xlanguage+","+xextident+") ";
					System.out.println(""+markList);
					NOTIMPL("");
					continue CHECKMARK;
				}
				int xspecial=inpt.inbyte();
				if(xspecial > 128) {
					xspecial=xspecial-128;
					int xarrlo=inpt.inbyte();
					markList=markList+"specMark2=("+xspecial+","+xarrlo+") ";
					//System.out.println(""+markList);
				} else {
					String tt=inpt.intext();
					markList=markList+"specMark3=\""+tt+"\" ";
					//System.out.println(""+markList);
				}
//				NOTIMPL("");
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
				//NOTIMPL("");
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
				language=inpt.gettext();
				if(TESTING>2) System.out.println("QUANT.scanMarkSequence'case yMark: language="+language);
				extident=inpt.gettext();
				if(TESTING>2) System.out.println("QUANT.scanMarkSequence'case yMark: extident="+extident);
				markList=markList+"yMark=("+language+","+extident+") ";
				continue CHECKMARK;

			case nestMark: // *** for/connect vars ;
				connests=inpt.inbyte();
				markList=markList+"nestMark="+connests+" ";
				continue CHECKMARK;

			case thisMark: // *** inr, hasCode, thisused ;
				int nxtc=inpt.inbyte();
				if(TESTING>2) System.out.println("QUANT.scanMarkSequence'case thisMark: nxtc="+nxtc);
				markList=markList+"thisMark="+nxtc+" ";
				if(nxtc >= 64 ) { thisused=true; nxtc=nxtc-64; }
				if(nxtc >= 32 ) { hasCode=true; nxtc=nxtc-32; }
				inrtag=nxtc; // NOTE - also isGlobal !!!;
				if(TESTING>2) System.out.println("QUANT.scanMarkSequence'case thisMark: thisused="+thisused+", hasCode="+hasCode+", inrtag="+inrtag);
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
			//			} // key>=lowkey;
		} // end CHECKMARK LOOP
		//   NOMORE: !*** next key has been read ***;
		if(TESTING>2) System.out.println("END QUANT.scanMarkSequence: Next key="+edKey(key));
	}


	public String toString() {
		StringBuilder sb=new StringBuilder();
		if(kind==K_class) sb.append("\n").append(edIndent());
		if(parameter) sb.append("parameter ");
		else sb.append(K_code(kind)).append(" ");
		if(ident!=null) sb.append(ident);
		if(prefix != null && prefix.length() > 0 ) sb.append(':').append(prefix);
		sb.append(" ");
		if(ftag!=0)	sb.append("ftag=").append(ftag).append(" ");
		if(type!=14) sb.append("type=").append(edType(type)).append(" ");
//		sb.append("kind=").append(K_code(kind)).append(" ");
		sb.append("categ=").append(C_code(categ)).append(" ");
		sb.append("clf=").append(clf).append(" ");
		if(special!=0)	sb.append("special=").append(special).append(" ");
		if(protect!=0)	sb.append("protect=").append(protect).append(" ");
		if(dim!=0)	sb.append("dim=").append(dim).append(" ");
		if(longindic!=0) sb.append(", longindic=").append(longindic);
		if(connests!=0) sb.append(", connests=").append(connests);
		if(language!=null && language.length()>0) sb.append(" language=").append(language).append(" ");
		if(extident!=null && extident.length()>0) sb.append(" extident=").append(extident).append(" ");
		sb.append(markList);
		return(sb.toString());
	}
	
}
