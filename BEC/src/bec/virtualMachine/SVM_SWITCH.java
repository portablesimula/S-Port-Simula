package bec.virtualMachine;

import bec.segment.MemAddr;

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
		return "SWITCH   " + DESTAB.length;
	}

}
