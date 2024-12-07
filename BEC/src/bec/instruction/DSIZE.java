package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ConstItem;
import bec.compileTimeStack.StackItem;
import bec.descriptor.RecordDescr;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT_IMPL;

public abstract class DSIZE extends Instruction {
	
	/**
	 * addressing_instruction ::= dsize structured_type
	 * 
	 *		structured_type ::= record_tag:tag
	 *
	 * dsize structured_type
	 * 
	 * force TOS value; check TOS type(INT);
	 * pop;
	 * push( VAL, SIZE, "size(type with mod. rep.count)" );
	 * 
	 * The structured type must be prefixed with a "DYNAMIC" type (see 4.3.6),
	 * and it must contain an indefinite repetition, otherwise: error.
	 */
	public static void ofScode() {
//        InTag(%tag%); fixrec:=DISPL(tag.HI).elt(tag.LO);
//        if fixrec.nbrep <> 0
//        then n:=fixrec.nbrep;
//%+C             CheckTosInt;
//             if TOS.kind=K_Coonst
//             then itm:=TOS qua Coonst.itm; GQpop;
//                  n:=wAllign(%(n*(itm.wrd))+fixrec.nbyte%);
//%+E                  Qf2(qPUSHC,0,qEAX,cVAL,n); itm.int:=n;
//                  pushCoonst(T_SIZE,itm);
//             else
//%+E                  GetTosAdjustedIn86(qEAX); Pop;
//%+E                  OldTSTOFL:=TSTOFL; TSTOFL:=true;
//%+E                  if n > 3
//%+E                  then GQeMultc(n); -- EAX:=EAX*n
//%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte);
//%+E                  else if n>1 then GQeMultc(n) endif; -- EAX:=EAX*n
//%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte+3);
//%+E                       Qf2(qDYADC,qAND,qEAX,cVAL,-4);
//%+E                  endif;
//%+E                  TSTOFL:=OldTSTOFL;
//%+E                  Qf1(qPUSHR,qEAX,cVAL);
//                  pushTemp(T_SIZE);
//             endif;
//        else
//%+D             edit(errmsg,fixrec);
//             IERR("Illegal DSIZE on: ");
//             GQpop; itm.int:=0; pushCoonst(T_SIZE,itm);
//        endif;
		
		
//      InTag(%tag%); fixrec:=DISPL(tag.HI).elt(tag.LO);
		Tag tag = Tag.ofScode();
		RecordDescr fixrec = (RecordDescr) tag.getMeaning();
		System.out.println("DSIZE.ofScode: fixrec="+fixrec+"  nbrep="+fixrec.nbrep);
		if(fixrec.nbrep != 0) {
			int n = fixrec.nbrep;
			CTStack.checkTosInt();
			StackItem TOS = CTStack.TOS;
//           if TOS.kind=K_Coonst
			if(TOS instanceof ConstItem citm) {
//           then itm:=TOS qua Coonst.itm; GQpop;
//                n:=wAllign(%(n*(itm.wrd))+fixrec.nbyte%);
//%+E                  Qf2(qPUSHC,0,qEAX,cVAL,n); itm.int:=n;
//                pushCoonst(T_SIZE,itm);
				Util.IERR("NOT IMPL");
			} else {
//%+E                  GetTosAdjustedIn86(qEAX);
				CTStack.pop();
//%+E                  OldTSTOFL:=TSTOFL; TSTOFL:=true;
//%+E                  if n > 3
//%+E                  then GQeMultc(n); -- EAX:=EAX*n
//%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte);
//%+E                  else if n>1 then GQeMultc(n) endif; -- EAX:=EAX*n
//%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte+3);
//%+E                       Qf2(qDYADC,qAND,qEAX,cVAL,-4);
//%+E                  endif;
//%+E                  TSTOFL:=OldTSTOFL;
//%+E                  Qf1(qPUSHR,qEAX,cVAL);
				Global.PSEG.emit(new SVM_NOT_IMPL(), "DSIZE: ");
				CTStack.pushTemp(Type.T_SIZE, "DSIZE: ");
			}
		} else {
//%+D             edit(errmsg,fixrec);
			Util.IERR("Illegal DSIZE on: " + fixrec);
//           GQpop; itm.int:=0; pushCoonst(T_SIZE,itm);
		}
	}

}
