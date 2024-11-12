package bec.virtualMachine;

import bec.util.Relation;
import bec.value.MemAddr;

public class SVM_GOTOIF extends SVM_GOTO {
	Relation relation;
//	MemAddr destination;

	public SVM_GOTOIF(Relation relation, MemAddr destination) {
		super(destination);
		this.relation = relation;
//		this.destination = destination;
	}
	
	@Override	
	public String toString() {
		return "GOTOIF   " + relation + " " + destination;
	}

}
