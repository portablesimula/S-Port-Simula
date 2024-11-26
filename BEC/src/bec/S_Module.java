package bec;

import bec.descriptor.Kind;
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
//	public static boolean programElement() {
//		System.out.println("S_Module.programElements: CurInstr="+Scode.edInstr(Scode.curinstr));
//		Thread.dumpStack();
//		switch(Scode.curinstr) {
//			case Scode.S_LABELSPEC:		LabelDescr.ofLabelSpec(); break;
//			case Scode.S_LABEL:			LabelDescr.ofLabel(Tag.inTag()); break;
//			case Scode.S_PROFILE:		ProfileDescr.ofProfile(); break;
//			case Scode.S_ROUTINE:		RoutineDescr.ofRoutine(); break;
//			case Scode.S_IF:			new IfStatement(); break;
//			case Scode.S_SKIPIF:		new SkipifStatement(); break;
//			case Scode.S_SAVE:			new ProtectStatement(); break;
//			case Scode.S_INSERT:		new InsertStatement(false); break;
//			case Scode.S_SYSINSERT:		new InsertStatement(true); break;
//			default:{
//				Object obj = Instruction.inInstruction();
//				if(obj == null) return false;
//				if(obj instanceof Instruction instr)
//					instr.doCode();
//			}
//		}
//		return true;
//	}
//
//	public static void programElements() {
//		while(programElement()) ;
//	}
//	
	public static void programElements() {
		LOOP: while(true) {
//			Scode.inputInstr();
			System.out.println("MONITOR.programElements: CurInstr="+Scode.edInstr(Scode.curinstr));
			switch(Scode.curinstr) {
				case Scode.S_LABELSPEC:		LabelDescr.ofLabelSpec(); break;
				case Scode.S_LABEL:			LabelDescr.ofLabel(Tag.inTag()); break;
				case Scode.S_PROFILE:		ProfileDescr.ofProfile(); break;
				case Scode.S_ROUTINE:		RoutineDescr.ofRoutine(); break;
				case Scode.S_IF:			new IfStatement(); break;
				case Scode.S_SKIPIF:		new SkipifStatement(); break;
				case Scode.S_SAVE:			new ProtectStatement(); break;
				case Scode.S_INSERT:		new InsertStatement(false); break;
				case Scode.S_SYSINSERT:		new InsertStatement(true); break;
				default:{
					Object obj = Instruction.inInstruction();
					if(obj == null) break LOOP;
				}
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
