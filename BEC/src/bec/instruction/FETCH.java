package bec.instruction;

import bec.util.Util;

public class FETCH extends Instruction {
	
	/**
	 * addressing_instruction ::= fetch
	 * 
	 * force TOS value;
	 * 
	 * TOS.MODE should be REF, otherwise fetch has no effect.
	 * TOS is modified to describe the contents of the area previously described.
	 * 
	 *      (TOS) -------------------,
	 *                               |
	 *                               V
	 *      The resulting            .============.
	 *          TOS -----------------|---> VALUE  |
	 *      after fetch              '============'
	 */
	public FETCH() {
	}


	@Override
	public void doCode() {
//		CTStack.dumpStack();
		Util.GQfetch("FETCH");
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "FETCH";
	}
	

}
