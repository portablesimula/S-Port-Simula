package bec.compileTimeStack;

import bec.syntaxClass.programElement.routine.PREV_PROFILE;

public class ProfileItem extends StackItem {
	int type;
	public PREV_PROFILE spc;
	public int nasspar;
	
	public ProfileItem(int tagVoid, PREV_PROFILE spc) {
//	      prf.kind:=K_ProfileItem;
		this.type = tagVoid;
//	      prf qua StackItem.repdist:=TTAB(type).nbyte;
//	      prf qua StackItem.suc:=none; prf qua StackItem.pred:=none;
		this.spc = spc;
		this.nasspar = 0;
//	%+C   if type=T_NOINF then IERR("No info TYPE-1") endif;
	}

	public String toString() {
		return ""+spc;
	}

}
