package bec.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.segment.MemAddr;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_GOTOIF;

public class FJUMPIF extends Instruction {
	Relation relation;
	int destination;
	
	public FJUMPIF() {
		parse();
	}

	/**
	 * forward_jump ::= fjumpif relation destination:newindex
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		relation = new Relation();
		destination = Scode.inByte();
//		Util.IERR(""+this);
	}

	@Override
	public void doCode() {
		CTStack.dumpStack();
		int cond = Util.GQrelation();
//		%+D        b:=InputByte;
//		%-D        InByte(%b%);
//		%+C        if DESTAB(b) <> none then IERR("PARSE:FJUMPIF") endif;
//		if(CTStack.TOS == CTStack.SAV) {
////			DESTAB(b):=ForwJMP(cond)
//			Util.IERR(""+this);
//		} else {
////			LL:=ForwJMP(NotQcond(cond));
////		    ClearSTK;
////		    DESTAB(b):=ForwJMP(0); DefFDEST(LL);
//			Util.IERR(""+this);
//		}
		Global.DESTAB[destination] = Global.PSEG.nextAddress();
		Global.PSEG.emit(new SVM_GOTOIF(relation, null), ""+this);
		Global.PSEG.dump();
//		Util.IERR(""+this);
	}
	
	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "FJUMPIF " + relation + " " + destination;
	}
	

}
