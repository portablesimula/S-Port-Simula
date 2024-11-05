package bec.virtualMachine;

import java.util.HashMap;
import java.util.Map;

import bec.util.Util;

public class SVM_SYSCALL extends SVM_Instruction {
	private static int size;
	private static final Map<String, Integer> map1 = new HashMap<String, Integer>();
	private static final Map<Integer, String> map2 = new HashMap<Integer, String>();
	
	static {
		defName("PRINTO");
		defName("BREAKO");
		defName("INIMAG");
		defName("INBYTE");
		defName("OUTBYT");
	}
	
	int kind;

	public SVM_SYSCALL(String kind) {
		
		Integer code = map1.get(kind);
		if(code == null) Util.IERR("Undefined System Routine: " + kind);
		this.kind = code;
	}
	
	@Override	
	public String toString() {
		return "SYSCALL  " + edKind(kind);
	}
	
	private static void defName(String name) {
		size++;
		map1.put(name, size);
		map2.put(size, name);
	}
	
	private static String edKind(int i) {
		return map2.get(i);
	}

}
