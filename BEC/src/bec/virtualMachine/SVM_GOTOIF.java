package bec.virtualMachine;

import bec.segment.MemAddr;
import bec.util.Relation;

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
