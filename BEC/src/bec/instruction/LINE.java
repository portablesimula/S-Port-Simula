package bec.instruction;

import bec.util.Scode;

public abstract class LINE extends Instruction {

	/**
	 * 	info_setting
	 * 		::= decl line:number
	 * 		::= line line:number
	 * 		::= stmt line:number
	 */
	public static void ofScode(int kind) {
		Scode.curline = Scode.inNumber();	
	}

}
