package bec.virtualMachine;

public abstract class RTRegister {
	
	private static int[] register = new int[4];
	public static final int qEAX = 1;
	public static final int qEBX = 2;
	public static final int qECX = 3;
	public static final int qEDX = 4;
	
	public static void putIndex(int reg, int index) {
		register[reg-1] = index;
	}
	
	public static int getIndex(int reg) {
		return register[reg-1];
	}
	
	public static String edReg(int reg) {
		switch(reg) {
		case 1: return("qEAX");
		case 2: return("qEBX");
		case 3: return("qECX");
		case 4: return("qEDX");
		}
		return("UNDEF_REG_"+reg);
	}

}
