package bec.virtualMachine;

import bec.util.Relation;

public class SVM_COMPARE extends SVM_Instruction {
	Relation relation;
	
	public SVM_COMPARE(Relation relation) {
		this.relation = relation;
	}
	
	public String toString() {
		return "COMPARE  " + relation;
	}
}
