package attr;

import static comn.Util.*;

import java.util.StringTokenizer;

import comn.brecord;
import comn.extbrecord;
import comn.extquantity;
import comn.quantity;

public class AttrFileWriter {
	private static final int layoutindex = 2; //!change if attr file changed;
	private static final int ITEXT=8; //  !text;

	
	public static void afileWriter(quantity mainqnt) {
	
		String fileName="C:/WorkSpaces/SPort-System/S_Port/Attrs/FEC/EXTRA_PREDEF.atr";
//		String sportid="SPORT SYSTEM";
		String sportid="SPORT SYST:M";
		String moduleident="MODID";
		String checkcode="CHECK";

	
		ByteOutputStream AOF=new ByteOutputStream(fileName);
		AOF.outbyte(layoutindex);
//%+T		    TRC("BUILDER2",sourceline,"sportid=" & sportid);
//%+D         if sportid.length<>12 then internerr('!7!',sourceline);
//%+Z         sysattrfile:=(mainqnt.virtno <> 0);
//%+Z         if sysattrfile then begin
//%+Z            t:-copy(sportid); t.sub(11,1):=":"; end else
//    t:-sportid; attrbuffer.sub(3,12):=t; p:=14;
		
		AOF.outtext(sportid);
		AOF.outtext(checkcode);
		AOF.outtext(moduleident);
		
//    !***********  output external head  ****************;
//    nextexttag:=0; q:-display(6).fpar;
//    while q=/=none do
//    begin if q.categ=C_extnal and then
//             ( q.descr is brecord or else
//               q.descr qua extbrecord.status='S' )
//          then outquantwlist(q);
//          q:-q.next;
//    end;

		//!***********  output main w/locals and tag count  ****;
		AOF.outbyte(mainKey); outquantwlist(mainqnt,AOF);
		AOF.outbyte(mainKey); AOF.out2byte(nextexttag);
		AOF.outbyte(mainKey); //!*** terminates reading ***;
		
		
//		swapObuffer(); //!output last buffer;
		// !*** at least two bytes MUST follow the last buffer,
        //    - see the handling of input buffers ;
//    AOF.outtext(timestamp); AOF.close; AOF:-none;
	} //end of normalattrfile;

	
	

//%title ***  Procedure EXTTAG  ***

    private static int nextexttag; //! Next external tag to be given;

	private static int exttag(quantity q) {
		int prevxtag=nextexttag;
		if(q.visible==0 || q.protect==1) {
    	  // Delivers the external tag by which q is associated,
    	  // -1 if no external tag should be given to  q,
    	  // -2 if q should not go to attribute file (hidden prot.);
    	  // Called from 'normalattrfile', 'recompattrfile' (Builder2)
    	  // and 'generatetagindexlist' (coder2) ;

    	  if(q.categ==C_virt) return(-1);
    	  if(q.categ==C_extnal) {
    		  if(q.descr instanceof extbrecord) return(-1);
    		  nextexttag=nextexttag+2; return(prevxtag); // procedure with binding: profile- and bodytag;
    	  }
    	  if(q instanceof extquantity xq && xq.clf==Clf002) return(-1); // parameter to external non-Simula procedure ;
    	  if(q.special > 1 && q.type != ITEXT) {
    		  // named constant, same check as in LAYOUT ;
              // text const get one tag: const addr of txtent;
              // const array get one tag: tag of first elt;
    		  return(-1);
    	  }
    	  if(q.kind==K_class && q.categ==C_local) {
    		  nextexttag=nextexttag + ((q.descr.inrtag!=0)?3:2);
    		  return(prevxtag);
    	  }
    	  if(q.kind==K_proc && q.categ==C_local) {
    		  nextexttag=nextexttag + 2;
    		  return(prevxtag);
    	  }
    	  if(q.encl!=null && q.encl.declquant.categ==C_virt) return(-1); // parameter to specification of virt proc ;
    	  nextexttag=nextexttag  + ((q.categ==C_name)?2:1);
		  return(prevxtag);
      } else return(-2);
	}


	// Output Quant With List
	private static void outquantwlist(quantity q,ByteOutputStream oupt) {
		System.out.println("writeQuant: " + q);
		int idlength=0;
		int clf = 0;
		int overload = 0;
		boolean inrflag = false;
		String idddd1 = null,idddd2 = null;
		StringTokenizer tokenizer = null;
		
		int xtag=exttag(q)+1; // NOTE: +1 to take care of exttag=-1 !!!;
		if(xtag>=0) { // *** OutputQuant: ***;
			System.out.println("*** BEGIN OutputQuant: "  + q.symb + ", xtag=" + xtag + " *****************");
			int quallength=0;
			if(q.prefqual != null && (q.kind != K_class || q.plev>1)) quallength=q.prefqual.symb.length()+1;

			if(q.encl!=null && q.encl.kind==K_proc && q.categ < C_local) {
				// !procedure parameter - no identifier out;
			} else {
				idlength=q.symb.length()+1;
				if(q.kind != K_class) {
					// symbols starting with a single underline are skipped ;
					if(q.symb.startsWith("_") && !q.symb.startsWith("__")) idlength=1;
				}
			}
//
//	            !*** check that the basic descriptor may be output ***;
//	            if p + rank(idlength) + rank(quallength) > bufmax-5
//	            then swapObuffer;

			if(sysattrfile && q.categ != C_virt && q.categ != C_extnal) {
				//!set overload and inrflag, read basetag;
	         
	            //permt:-nameof(char(virtno//256),char(rem(virtno,256)));
				String permt=q.permt; // E.G:  ="!12! 2 *T_SETPOS1 *T_SETPOS2";
				System.out.println("permt=" + permt);
//	            permt.setpos(1);
				clf=permt.charAt(0);  // clf=permt.getchar;
				tokenizer = new StringTokenizer(permt.substring(2));
			}

//	%+TZ        if idlength = NUL then outtext("PROCEDURE PARAMETER: ");  !procedure parameter;
//	%+T    		outtext(K_code(kind) & " " & symb.symbol);
//	%+T    		outtext(", exttag=" & edit(xtag) & ", type=" & edType(type));
//	%+T    		outtext(", kind=" & K_code(kind) & ", categ=" & C_code(categ) & ", clf=" & edit(rank(clf)));
//	%+TZ   		outtext(", permt=""" & permt.sub(2,permt.length-1) & """");
//	%+T    		outimage;


			// *** basic quantity descriptor, size computed above ***;
			//   - assume that categ <8 and kind < 16  ALWAYS ***;
			oupt.outbyte(q.kind*8 + q.categ);

			// - assume that type < 128 ALWAYS ***;
			if(quallength  !=  0) {
				oupt.outbyte(q.type+128);
				oupt.outtext(q.prefqual.symb);
			} else {
				System.out.println("type=" + edType(q.type));
				oupt.outbyte(q.type);
			}

			// - assume that clf < 128 ALWAYS ***;
			if(idlength == 0) { //!procedure parameter;
				oupt.outbyte(clf+128);
				oupt.out2byte(xtag);                           // TODO:      ! ØM 8/3-2023 ;
				return; //goto SPECX;
            }

			System.out.println("languagestring=" + q.languagestring);
			System.out.println("extidentstring=" + q.extidentstring);
			idddd1=q.languagestring;
			idddd2=q.extidentstring;

//			int k=Integer.parseInt(tokenizer.nextToken());
//			if(k < 0) { overload= -k; k=Integer.parseInt(tokenizer.nextToken()); }
//			if(k==3) inrflag=true;
//
//			if(k==1) {
//				idddd1=tokenizer.nextToken().substring(1);
//				idddd2=null;
//			} else if(k==2) {
//				idddd1=tokenizer.nextToken().substring(1);
//				idddd2=tokenizer.nextToken().substring(1);
//			} else {
//				idddd2=null;
//				idddd2=null;
//			}
			System.out.println("idddd1=" + idddd1);
			System.out.println("idddd2=" + idddd2);
			xtag=1;
			
			int k= -999;

//	% +Z            inspect coder qua visiblegenerator do {
			if(k > 0) {
				String u=tokenizer.nextToken().toLowerCase();
				System.out.println("u=" + u + ", k=" + k);
//	% +Z                  goto XXXX;                                                                  !*** MYH  7/3-2023;
//	% +Z                  l:=      if u = "rt"   then   1
//	% +Z                     else  if u = "knwn" then   2
//	% +Z                     else  if u = "cent" then   3
//	% +Z                     else  if u = "cint" then   4
//	% +Z                     else  if u = "arr"  then   5
//	% +Z                     else  if u = "fil"  then   6
//	% +Z                     else  if u = "edit" then   7
//	% +Z                     else  if u = "form" then   8
//	% +Z                     else  if u = "libr" then   9
//	% +Z                     else  if u = "smst" then  10
//	% +Z                     else  if u = "sml"  then  11
//	% +Z                     else  if u = "mntr" then  12
//	% +Z                     else  10000; !WILL ENSURE INDEX TRAP BELOW;
//	% +Z                  basetag:=moduletab(l).basetag;
//	% +Z                  xtag:=basetag+tokenizer.nextToken.getint+1;
//	%  +Z               } else xtag:=1;
               } else XXXX: xtag=1;  // TODO:
//            }

		} else {
			if(q instanceof extquantity xq) clf=xq.clf; else clf=Clf000;
		}
		System.out.println("clf=" + clf + ", xtag=" + xtag);
		oupt.outbyte(clf); oupt.out2byte(xtag);

		if(idlength == 1) oupt.outbyte(0); else oupt.outtext(q.symb);

		
		// *****************************************************
		// ******* specially marked output ********************;
		// *****************************************************
		if(overload != 0) {
			System.out.println("overMark=" + overload);
			oupt.outKey(overMark); oupt.outbyte(overload); }

		if(q.protect != 0) {
			System.out.println("protMark=" + q.protect);
			oupt.outKey(protMark); oupt.outbyte(q.protect); }

		if(q.categ==C_extnal) {
			// IKKE RELEVANT HER
		}
		else { // !categ  !=  C_extnal;
			System.out.println("categ  !=  C_extnal");
	 
			if(idddd2==null) idddd2="MISSING";
			oupt.outKey(yMark);
			oupt.outtext(idddd1);
			oupt.outtext(idddd2);

			if(q.encl != null && (q.categ != C_local || (q.match != null && q.match.categ==C_virt))) {
				oupt.outKey(forcMark);
			}	 
	               
			if(q.dim > 0) {
				oupt.outKey(dimMark); oupt.outbyte(q.dim); }
			System.out.println("dimMark=" + dimMark + ", dim=" + q.dim);
		}

		if( (clf==Clf004 || clf==Clf009) && inrflag) { //!set inrtag for sys class;
			System.out.println("thisMark=" + thisMark);
            oupt.outKey(thisMark); oupt.outbyte(2); }

		System.out.println("!*** end of OutputQuant ***************************************************");
		//!*** end of OutputQuant ***;

		if(q.descr instanceof brecord) {
			if(q.special==0) {

				quantity qq=q.descr.fpar;
				boolean begListWritten=false;
				while(qq != null) {
					if ( (qq.descr instanceof extbrecord xb)?xb.status=='S': true )  {
						if(!begListWritten) { oupt.outKey(begList); begListWritten=true; }
						outquantwlist(qq,oupt);
					}
					if(qq != null) qq=qq.next;
				}
				if(begListWritten) {
					oupt.outKey(endlist);
					System.out.println("endList=" + endlist);
				}
			}

					
//	   SPECX:
		} //exttag>-2;
	} //of outquantwlist;


}
