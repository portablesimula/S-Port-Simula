package bec.virtualMachine;

import bec.value.MemAddr;

/**
 * 
 * BESKRIVELSE ....
 * Jump to switch case 'tos' in DESTAB.
 */
public class SVM_SWITCH extends SVM_Instruction {
	MemAddr[] DESTAB;

	public SVM_SWITCH(MemAddr[] DESTAB) {
		this.DESTAB = DESTAB;
	}
	
	@Override	
	public String toString() {
		String s = "SWITCH   " + DESTAB.length;
		for(int i=0;i<DESTAB.length;i++) {
			s = s + " " + DESTAB[i];
		}
		return s;
	}

}
