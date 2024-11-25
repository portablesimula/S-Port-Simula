package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.value.MemAddr;

/**
 * 
 * BESKRIVELSE ....
 * Jump to switch case 'tos' in DESTAB.
 */
public class SVM_SWITCH extends SVM_Instruction {
	MemAddr[] DESTAB;

	public SVM_SWITCH(MemAddr[] DESTAB) {
		this.opcode = SVM_Instruction.iSWITCH;
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
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_SWITCH(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSWITCH;
		int n = inpt.readShort();
		DESTAB = new MemAddr[n];
		for(int i=0;i<n;i++) {
			DESTAB[i] = MemAddr.read(inpt);
		}		
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeKind(opcode);
		int n = DESTAB.length;
		oupt.writeShort(n);
		for(int i=0;i<n;i++) {
			DESTAB[i].write(oupt);
		}
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_SWITCH(inpt);
	}

}
