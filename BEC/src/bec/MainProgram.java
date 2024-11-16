package bec;

import bec.util.Scode;
import bec.util.Util;

public class MainProgram extends S_Module {
	
	/**
	 * 	MainPprogram ::= main <local_quantity>* <program_element>*
	 * 
	 * 		local_quantity ::= local var:newtag quantity_descriptor
	 * 
	 *			quantity_descriptor ::= resolved_type < Rep count:number >?
	 *
	 *		program_element
	 *			::= instruction
	 *			::= label_declaration
	 *			::= routine_profile
	 *			::= routine_definition
	 *			::= if_statement
	 *			::= skip_statement
	 *			::= insert_statement
	 *			::= protect_statement
	 *			::= delete_statement
	 */
	public MainProgram() {
		//  M a i n   P r o g r a m  ---
//        if PROGID.val=0 then PROGID:=DefSymb("MAIN") endif;
//        BEGASM(CSEGNAM,DSEGNAM); ed(sysedit,"SIM_");
//        EdSymb(sysedit,PROGID); entx:=DefPubl(pickup(sysedit));
//        MainEntry:=NewFixAdr(CSEGID,entx);
//        DefLABEL(qBPROC,MainEntry.fix.val,entx.val);
	
		while(Scode.nextByte() == Scode.S_LOCAL) {
			Scode.inputInstr(); 
			Util.IERR("NOT IMPL");
//			Minut.inGlobal();
		}
//        if LtabEntry.kind <> 0
//        then Ltab.kind:=segadr; Ltab.rela.val:=0;
//             Ltab.segmid:=LSEGID;
//             opr.kind:=extadr; opr.rela.val:=0;
//             opr.smbx:=DefExtr("G@PRGINF",DGROUP);
//%-E                  Ltab.sbireg:=0;       opr.sbireg:=oSS;
//%+E                  Ltab.sibreg:=NoIBREG; opr.sibreg:=NoIBREG;
//%-E                  Qf2b(qLOADC,0,qAX,cOBJ,F_OFFSET,Ltab);
//%-E                  Qf3(qSTORE,0,qAX,cOBJ,opr);
//%-E                  opr.rela.val:=opr.rela.val+2;
//%-E                  Qf2b(qLOADC,0,qAX,cOBJ,F_BASE,Ltab);
//%-E                  Qf3(qSTORE,0,qAX,cOBJ,opr);
//%+E                  Qf2b(qLOADC,0,qEAX,cOBJ,0,Ltab);
//%+E                  Qf3(qSTORE,0,qEAX,cOBJ,opr);
//        endif;

//		Util.IERR("NOT IMPL");
		Scode.inputInstr(); 
		programElements();

//        Qf2(qRET,0,0,0,0);
//        DefLABEL(qEPROC,MainEntry.fix.val,entx.val);
//        peepExhaust(true); ENDASM;
	
		if(Scode.curinstr != Scode.S_ENDPROGRAM)
			Util.IERR("Illegal termination of program");
		
	}
	

}
