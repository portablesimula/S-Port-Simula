package bec.virtualMachine;

import bec.descriptor.ProfileDescr;

public class SVM_RETURN extends SVM_Instruction {
	ProfileDescr prf;

	public SVM_RETURN(ProfileDescr prf2) {
		this.prf = prf2;
	}
	
	@Override	
	public String toString() {
		return "RETURN   " + prf;
	}

}
