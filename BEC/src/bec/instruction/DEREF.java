package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Type;

public abstract class DEREF extends Instruction {
	
	/**
	 * addressing_instruction ::= deref
	 * 
	 * 
	 * check TOS ref;
	 * TOS.MODE := VAL; TOS.TYPE := GADDR;
	 * TOS is modified to describe the address of the area.
	 * 
	 *     (TOS) ------------------------------------------,
     *                              REF                     |
     *                                                      |
     *                                                      |
     *  The resulting           .==================.        V
     *       TOS ---------------|--> GADDR VALUE --|------->.========,
     *  after deref             '=================='        |        |
     *                                                      |        |
     *                                                      '========'
	 */
	public static void ofScode() {
//		CTStack.dumpStack();

		CTStack.checkTosRef();
//        adr:=TOS;
//%+S        if SYSGEN <> 0
//%+S        then if adr.size <> (TTAB(adr.type).nbyte)
//%+S             then WARNING("DEREF on parameter") endif;
//%+S        endif;

		CTStack.assertAtrStacked();
		CTStack.pop();
		CTStack.pushTemp(Type.T_GADDR, "DEREF: ");

//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}
	
}
