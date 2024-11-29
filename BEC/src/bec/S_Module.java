package bec;

import bec.descriptor.LabelDescr;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RoutineDescr;
import bec.instruction.Instruction;
import bec.statement.IfStatement;
import bec.statement.InsertStatement;
import bec.statement.ProtectStatement;
import bec.statement.SkipifStatement;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.virtualMachine.SVM_LINE;

public abstract class S_Module {


	/**
	 * 	program_element
	 * 		::= instruction
	 * 		::= label_declaration
	 * 		::= routine_profile | routine_definition
	 * 		::= skip_statement | if_statement
	 * 		::= protect_statement
	 * 		::= goto_statement | insert_statement
	 * 		::= delete_statement
	 */
	public static void programElements() {
		LOOP: while(true) {
//			System.out.println("S_Module.programElements: CurInstr="+Scode.edInstr(Scode.curinstr));
			switch(Scode.curinstr) {
				case Scode.S_LABELSPEC ->	LabelDescr.ofLabelSpec();
				case Scode.S_LABEL ->		LabelDescr.ofLabel(Tag.ofScode());
				case Scode.S_PROFILE ->		ProfileDescr.ofProfile();
				case Scode.S_ROUTINE ->		RoutineDescr.ofRoutine();
				case Scode.S_IF ->			new IfStatement();
				case Scode.S_SKIPIF ->		new SkipifStatement();
				case Scode.S_SAVE ->		new ProtectStatement();
				case Scode.S_INSERT ->		new InsertStatement(false);
				case Scode.S_SYSINSERT ->	new InsertStatement(true);
				default -> { if(! Instruction.inInstruction()) break LOOP; }
			}
			Scode.inputInstr();
		}
	}

	public static void setSwitch() {
		Util.IERR("NOT IMPL");
	}

	public static void setLine(int type) {
		Global.curline = Scode.inNumber();
		Global.PSEG.emit(new SVM_LINE(type, Global.curline), "MONITOR.setLine: ");
	}

}
