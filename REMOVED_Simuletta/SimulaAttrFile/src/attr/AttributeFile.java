package attr;

import static comn.Util.*;

import comn.brecord;
import comn.extbrecord;
import comn.extquantity;
import comn.quantity;

@SuppressWarnings("unused")
public class AttributeFile {
	public static String attrck;
	public static String attrmod;
	static quantity q,qe,qx,qq;


	
	// ****************************************************************
	// ***  openattributefile
	// ****************************************************************
	public void openattributefile(quantity q) {
		String fileName="C:/WorkSpaces/SPort-System/S_Port/Attrs/FEC/PREDEF.atr";
		inpt=new ByteInputStream(fileName);
       if(inpt.inbyte() != layoutindex) ERROR("wrongLayout");
       inpt.in2byte(); // firstbufsize: SKIP it
       String head=inpt.intext(12);
       System.out.println("AttrFileReader.openAttrFile: head="+head);
       // must start "S-port 108.1x" where 'x' is layout index;
       if(!head.startsWith("S-port ")) ERROR("wrongLayout");
       
//       sysattrfile=head.charAt(11) == ':'; // TODO: ??????????
//       sysattrfile=true;
       
       attrck=inpt.gettext(); // Check-code
       attrmod=inpt.gettext(); // Module id
       System.out.println("AttrFileReader.openAttrFile: sysattrfile="+sysattrfile+", Check="+attrck+", Module="+attrmod);
	}
	

	// ****************************************************************
	// ***  treatExternalHead
	// ****************************************************************
	void treatExternalHead(brecord b) {
		// *** Treat external head;
		inpt.nextKey();
        if(TESTING>2) System.out.println("AttributeFile.treatExternalHead: key="+edKey(key));
		while(key < lowkey) {
			generate=quantity.fetchquant();
//			if(xident == null) xident=xidentstring;

			// *** Search for ident in prefix chain;
			qx=b.declquant;
//			EXIT();
			LABEL("nextplev:");      qe=qx.descr.fpar;
			while(qe != null) {
				if(qe.symb == xident) GOTO("found");
				qe=qe.next;
			}
			if(qx.plev>one) { qx=qx.prefqual; GOTO("nextplev"); }

			// *** We know xident not in prefix chain;
			if(xkind==K_class) GOTO("newhead");
			qe=meaningof(xident);
			if(qe == null) qe=newnotseen(xident);
			if(qe.categ == C_unknwn) {
				if(xclf==Clf001) GOTO("addnewnature");
				GOTO("newhead");
			}

			LABEL("found:");        
				// QTRC("BUILDER2",sourceline,"FOUND: ",qe);
				if(qe.encl != b) {
					if(xclf==Clf001) GOTO("addnewnature");
					if(!(qe.descr instanceof extbrecord)) GOTO("newhead");
					if( xcheck != ((extbrecord)qe.descr).check ) GOTO("newhead");
					markermade=true; // *** new marker;
					qq=newextquantbrec(b.blev,b.rtblev,'M',qe);
					GOTO("littned");
				}
			// *** Local name conflict;
			if(xclf==Clf001) {
				if(qe.categ !=C_extnal) errQTN(qe,417,q);
				else if( ((extquantity)qe).clf != Clf001 || (qe.virtno != xextident) || (qe.dim != xlanguage) ) errQTN(qe,418,q);
				if(key==begList) { // skip parameters;
					inpt.nextKey();
					while(key < lowkey) quantity.fetchquant();
					if(key != endlist) expxerr('E');
					inpt.nextKey();
				}
			}
			else if(!(qe.descr instanceof extbrecord)) errQTN(qe,417,q);
			else {

				//							inspect qe.descr qua extbrecord do {
				//								if(checkhi != NUL && (checkhi != xcheckhi || checklo != xchecklo) errQTN(qe,418,q);
				//										else { // *** Status is 'S';
				//											checkhi=xcheckhi; checklo=xchecklo;
				//											cause=q;
				//										}
				//							}	


				extbrecord xb=(extbrecord)qe.descr;
				if(xb.check != null && xb.check != xcheck) errQTN(qe,418,q);
				else {  // *** Status is 'S';
					xb.check=xcheck; cause=q;	
				}

			}
			GOTO("nonew");

			// *** external procedure with binding, xclf=Clf001;
			LABEL("addnewnature:");  if(generate) {
				qq=new extquantity(null); fillextquantity((extquantity) qq);
				qq.encl=b;
				qq.next=q.next; q.next=qq;

				//					inspect new brecord do {
				//						blev=char(rank(b.  blev)+1);
				//						rtblev=char(rank(b.rtblev)+1);
				//						qq.descr=this brecord; declquant=qq }

				brecord bb=new brecord();
				bb.blev=b.blev+1;
				bb.rtblev=b.rtblev+1;
				qq.descr=bb; bb.declquant=qq;

				q.descr.kind=K_proc;  // ***  qq.descr. ... ???;
			}
			indent++;
			if(key == begList) {
				if(generate) expandextlist(null,qq.descr);
				else skipList();
			}
			indent--;
			GOTO("nonew");

			LABEL("newhead:");
			qq=newextquantbrec(b.blev,b.rtblev,'H',q);
			//! not used b.nloc=b.nloc+1;
			if(qq.kind==K_class) b.localclasses=true;
			LABEL("littned:");
			qq.encl=b;
			qq.next=q.next; q.next=qq;
			fillextquantity((extquantity) qq);
			LABEL("nonew:");
		} //treatment of external head qantities;

	}

	// ****************************************************************
	// ***  expandexternals
	// ****************************************************************
	void expandexternals(brecord b) {
		q=b.fpar;
		LABEL("restartloop:");
			if(TESTING>2) System.out.println("AttributeFile.expandexternals: MAIN LOOP q="+q+", symb="+q.symb);
			while(q !=null) {                  // *** main loop;
				if(TESTING>2) System.out.println("AttributeFile.expandexternals: MAIN LOOP q.descr="+q.descr.getClass().getSimpleName());
				if(q.descr instanceof extbrecord eb) { // *** another module;
//					eb=q.descr;
					if(TESTING>2) System.out.println("AttributeFile.expandexternals: MAIN LOOP eb="+eb+", status="+eb.status);
					if(eb.status != 'M')  {  // *** not marker;
						openattributefile(q);
						// QTRC("BUILDER2",sourceline,"OPEN AttributeFile: ",q);
						//						if(! sysattrfile) inspect new extmodule do {
						//							if(firstextmodule==null) currentextmodule=firstextmodule=this extmodule;
						//							else {
						//								currentextmodule=firstextmodule;
						//								while(currentextmodule.next != null) currentextmodule=currentextmodule.next;
						//								currentextmodule.next=this extmodule;
						//							}
						//							noofextmodules=noofextmodules+1;
						//							qty=q; checkhi=attrckhi; checklo=attrcklo;
						//						}
						//
						//						if(eb.status=='S') {
						//							if(eb.checkhi==NUL) { eb.checkhi=attrckhi; eb.checklo=attrcklo }
						//							else if(attrcklo != eb.checklo || attrckhi !=eb.checkhi) { messageLinenr=q.line; error2quants(406,q,eb.cause) }
						//						} //*** The status is 'B' or 'H':
						//						else if(attrcklo != eb.checklo || attrckhi != eb.checkhi) errQTN(q,416,eb.cause);

						// *** Treat external head;
						treatExternalHead(b);

						if(key != mainKey) expxerr('M');
						inpt.nextKey();
						if(key >= lowkey) expxerr('Q');
						generate=quantity.fetchquant();
						//if(q.symb != xident) errQT(q,327);
						if(!q.symb.equalsIgnoreCase(xident)) ERROR("Wrong ident: q.symb=\""+q.symb+"\", xident=\""+xident+'"');
						xextident=q.virtno; //! ext.name of main not from file;
						xmodul=attrmod;
						xcheck=attrck;
						xcateg=C_extnal; //! main has old categ on attr.file ;
						fillextquantity((extquantity) q);
						if(key == begList) expandextlist((extbrecord) q.descr,q.descr);
						if(key != mainKey) expxerr('T');
//						xtagnum=CURF.nextNumber();
						xtagnum=inpt.in2byte();
						eb.exttagnum=xtagnum;
						//								if(option('O') != 0)
						//									inspect sysout do {
						//									outtext(" - end attr. file ");
						//									t=CURF.filename; outtext(t);
						//									outimage }
						//CURF.close;
						//CURF=null;
					} //not marker;
				} //another module;
				LABEL("nextq:");   q=q.next;
			} //main loop;

			// *** Remove markers if any. First is never marker;
			if(markermade) {
				q=b.fpar; qe=q.next;
				while(qe != null) {
					if((qe.descr instanceof extbrecord) && ((extbrecord)qe.descr).status =='M') q.next=qe=qe.next;
					else { q=qe; qe=q.next; }
				}
			}
	}
	


	private static void fillextquantity(extquantity xq) {
		brecord xb=null;
//		if(xident == null) xq.symb=xidentstring;
//		else
			xq.symb=xident;
        if(TESTING>2) System.out.println("BEGIN AttributeFile.fillextquantity: "+xq.symb);
        xq.type=xtype; xq.kind=xkind; xq.categ=xcateg;
        xq.visible=NUL; xq.protect=xprotect;
        xq.line= - xprefq;
        xq.plev=one;
        xq.ftag=xftag;
        xq.clf=xclf;
        xq.longindic=xlongindic;
        if(xcateg == C_extnal) {
        	xq.dim=xlanguage; //*** zero if no language;
        	xq.virtno=xextident; // DEFCONST("?") if no extident;
        	if(xq.descr instanceof extbrecord) {
        		xb=xq.descr;
        		xb.kind=xkind;
//        		xb.thisused=xthisused;
        		xb.connests=xconnests;
//%+K               if xinrtag>='!4!' then begin xb.isGlobal:=true;
//%+K                  xb.inrtag:=char(rank(xinrtag)-4); end else
//        		xb.inrtag=xinrtag;
        		xb.hasCode=xhasCode;
        		((extbrecord)xb).modul=xmodul;
        		((extbrecord)xb).check=xcheck;
//        		xb.hidlist=xhidlist;
        	}
        } else xq.dim=xdim;
        
        xq.special=xspecial;
        if(xq.special != NUL || xq.kind == K_rep) {
        	NOTIMPL("");
//          xb:-new brecord;
//          if xq.descr=/=none then internerr('!7!',sourceline);
//          xb.declquant:-xq; xq.descr:-xb;
//          xb.blnohi:=xarrhi; xb.blnolo:=xarrlo;
//          if xq.kind=K_rep then begin
//             xb.line1:=xlanguage; xb.npar:=xextident;
//          end;
        }
        
        xq.languagestring=xlanguagestring;
        xq.extidentstring=xextidentstring;
        
        if(TESTING>2) System.out.println("ENDOF AttributeFile.fillextquantity:\n    "+xq);
        if(TESTING>0) System.out.println(edIndent()+"QNT "+xq);
//        xq.print("ENDOF AttributeFile.fillextquantity: ");
        
        
		xident=null; xtype=0; xkind=0; xcateg=0; xprotect=0; xprefq=0; xftag=0; xclf=0;
        xlongindic=0; xconnests=0; xhasCode=false; xmodul=null; xcheck=null; xdim=0; xspecial=0;
        xlanguage=0; xextident=0; xlanguagestring=null; xextidentstring=null;
       
        
//	EXIT();
}

	/**
	 * 
     *  <quantityList> ::=
     *  
	 *    begList
	 *    	<quantity descriptor>* -- for each new virtual in virt-list
	 *      	                   -- NOTE: the virtuals MUST be first
	 *    	<quantity descriptor>* -- for each elt in fpar-list for which:
	 *      		               --  descr is not extbrecord OR descr.status='S'
	 *    endList
	 */
	private void expandextlist(extbrecord module, brecord encl) {
		extquantity cq,lq = null; int overload;
        boolean genqnt;
        boolean doExpDescr=false;
        indent++;
        
        if(TESTING>2) System.out.println("BEGIN AttributeFile.expandextlist: "+module);
        if(key!=begList) ERROR("Out of sync");
        inpt.nextKey();
        if(TESTING>2) System.out.println("AttributeFile.expandextlist: "+module+", key="+key+" &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        overload=0;
        while(key < lowkey) { // While QUANT follows
        	// ======================================
        	genqnt=quantity.fetchquant();
        	// ======================================
        	if(overload>0) { overload=overload-1; genqnt=true; }
        	if(!genqnt) {
        		if(key == begList) skipList();
        	} else {
        		if(xlongindic != NUL) {
        			overload=xlongindic;
        			if(overload>10) overload=overload - ((overload>20)?20:10);
        			if(xclf==Clf005) overload=overload+overload+1;
        		} else if(xclf==Clf005) overload=1;
        		if(xcateg==C_extnal && xclf != Clf001)
        			cq=(extquantity) newextquantbrec(encl.blev,encl.rtblev,'B',module.declquant);
        		else cq=new extquantity(module);
            	// ======================================
        		fillextquantity(cq);
            	// ======================================
        		cq.encl=encl;
    	        System.out.println("AttributeFile.expandextlist: "+module+", key="+key+" CQNT "+cq);
        		if(cq.categ != C_virt) {
        			if(encl.fpar==null) {
        				encl.fpar=lq=cq;
            	        System.out.println("AttributeFile.expandextlist: "+module+", key="+key+" ENCL "+encl);
        			} else if(lq !=null) {
        				lq=cq; lq.next=cq;
        			}
        			// lq==none: quantity is declquant of virt.descr., should not be inserted in fpar-list;
        	   
        			if(cq.categ != C_local && cq.categ != C_extnal) encl.npar=encl.npar+1;
        			if(cq.kind == K_class) encl.localclasses=true;
        		} else {
        			if(encl.favirt==null) encl.favirt=lq=cq;
        			else { lq=cq; lq.next=cq; }
        			if(key == begList) doExpDescr=true; //goto ExpDescr;
        		}
        		boolean extAndDescrNull=(cq.categ==C_extnal && cq.descr==null);
        		if( doExpDescr || ( (cq.categ==C_local ||  (extAndDescrNull)) && (cq.kind==K_proc || cq.kind==K_class))) {
        			brecord bb=new brecord();
        			bb.blev=encl.blev+1;
        			bb.rtblev=encl.rtblev+1;
        			cq.descr=bb; bb.declquant=cq; bb.kind=xkind;
        			bb.thisused=xthisused;
        			bb.inrtag=xinrtag;
        			bb.hasCode=xhasCode;
        			
        	        System.out.println("AttributeFile.expandextlist: "+module+", key="+key+" NEW "+bb);
        	        
                	// ======================================
        			if(key == begList) expandextlist(module,bb);
                	// ======================================
        	        System.out.println("AttributeFile.expandextlist: "+module+", key="+key+" END "+bb);
        		}           
      
        	} //***genbrec***
        } // end reading quants in list;
//EXIT:
        indent--;
        if(key != endlist) ERROR("Out of sync: key="+key);
        inpt.nextKey();
        if(TESTING>2) System.out.println("ENDOF AttributeFile.expandextlist: "+module);
//		EXIT();
	}
	
	private void skipList() {
        if(TESTING>2) System.out.println("BEGIN AttributeFile.skipList: ");
//		%           key:=isochar(CURF.inbyte); !*** 'L' is read, get next key;
		inpt.nextKey();
		indent++;
		while(key < lowkey) { // *** skip this quantity;
			quantity.fetchquant();
			if(key==begList) skipList(); //*** skip local list and then continue with this;
		}
		indent--;
        if(key != endlist) ERROR("Out of sync: key="+key);
		inpt.nextKey();
        if(TESTING>2) System.out.println("ENDOF AttributeFile.skipList: ");
	}



	private static quantity newextquantbrec(Object blev, Object rtblev, char c, quantity qe) {
		EXIT(); // TODO Auto-generated method stub
		return null;
	}

	private static void LABEL(String string) {
		//NOTIMPL("LABEL "+string);
	}

	private static void GOTO(String string) {
		NOTIMPL("GOTO "+string);
	}

	private static void expxerr(char c) {
		EXIT(); // TODO Auto-generated method stub

	}

	private static void errQTN(quantity qe, int i, quantity q) {
		EXIT(); // TODO Auto-generated method stub

	}

	private static quantity newnotseen(Object xident) {
		EXIT(); // TODO Auto-generated method stub
		return null;
	}

	private static quantity meaningof(Object xident) {
		EXIT(); // TODO Auto-generated method stub
		return null;
	}

}
