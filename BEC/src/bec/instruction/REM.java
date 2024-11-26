package bec.instruction;

import bec.util.Util;

public abstract class REM extends Instruction {
	
	/**
	 * arithmetic_instruction ::= rem
	 */
	private REM() {}
	public static void ofScode() {
		Util.IERR("NOT IMPL");
	}

}
