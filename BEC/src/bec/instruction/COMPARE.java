package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Type;
import bec.virtualMachine.SVM_COMPARE;

public class COMPARE extends Instruction {
	Relation relation;
	
	public COMPARE() {
		parse();
	}

	/**
	 * arithmetic_instruction ::= compare relation
	 * 
	 * force TOS value; force SOS value;
	 * check relation;
	 * pop; pop;
	 * push( VAL, BOOL, "value(SOS) rel value(TOS)" );
	 * 
	 * TOS and SOS replaced by a description of the boolean result of evaluating the relation. SOS is always
	 * the left operand, i.e. SOS rel TOS.
	 * 
	 * relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	 */
	public void parse() {
		relation = Relation.ofScode();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		
		CTStack.checkTypesEqual(); CTStack.checkSosValue();	
		CTStack.pop(); CTStack.pop();
		Global.PSEG.emit(new SVM_COMPARE(relation), "");
		CTStack.pushTemp(Type.T_BOOL, "COMPARE: ");

//		CTStack.dumpStack();
//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + toString());
	}
	
	public String toString() {
		return "COMPARE " + relation;
	}
	

}
