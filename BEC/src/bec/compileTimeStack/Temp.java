package bec.compileTimeStack;

import bec.util.Scode;
import bec.util.Type;

public class Temp extends StackItem {
	String comment;
	
	// Value is pushed on RT-stack
	public Temp(Type type, String comment) {
		this.type = type;
		this.comment = comment;
	}
	
	public String toString() {
		return "Temp " + Scode.edTag(type.tag) + " " + comment;
	}
}
