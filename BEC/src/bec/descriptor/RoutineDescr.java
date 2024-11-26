package bec.descriptor;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.CTStack;
import bec.instruction.Instruction;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_RETURN;

public class RoutineDescr extends Descriptor {
	ProgramSegment PSEG;
	public MemAddr adr;
	
	// NOT SAVED
	Tag prftag;
	
	public RoutineDescr(int kind, Tag tag) {
		super(kind, tag);
	}
	
	public String toString() {
		return "Routine " + tag + ", profile:" + prftag + ", adr:" + adr;
	}
	
	/**
	 * routine_specification
	 *		::= routinespec body:newtag profile:tag
	 * @return
	 */
	public static RoutineDescr ofRoutineSpec() {
//		%+S Visible Routine SpecRut; import Boolean inHead;
//		%+S begin infix(WORD) tag,ptag; infix(WORD) smb;
//		%+S       infix(string) s; ref(IntDescr) v;
//		%+S       InTag(%tag%); InXtag(%ptag%);
//		%+S       if inHead
//		%+S       then if TagIdent.val <> 0
//		%+S            then EdSymb(sysedit,PRFXID); -- edchar(sysedit,'@');
//		%+S                 edsymb(sysedit,TagIdent); s:=pickup(sysedit);
//		%+S                 if s.nchr>16 then smb:=NewPubl
//		%+S                 else smb:=DefPubl(s) endif;
//		%+S            else smb:=NewPubl endif;
//		%+S       else smb.val:=0 endif;
//		%+S       v:=NEWOBJ(K_IntRoutine,size(IntDescr));
//		%+S       v.adr:=NewFixAdr(CSEGID,smb); IntoDisplay(v,tag);
//		%+S end;
		Tag tag = Tag.inTag();
		Tag prftag = Tag.inTag();
		RoutineDescr rut = (RoutineDescr) Global.DISPL.get(tag.val);
		if(rut != null) Util.IERR("");
		rut = new RoutineDescr(Kind.K_IntRoutine,tag);
		rut.prftag = prftag;
		rut.adr = null;
//		Util.IERR("TEST DETTE");
		return rut;
	}

	/**
	 * 	routine_definition
	 * 		::= routine body:spectag profile:tag
	 *			 <local_quantity>* <instruction>* endroutine
	 *
	 *		local_quantity
	 *			::= local var:newtag quantity_descriptor
	 */
	public static void ofRoutine() {
		Tag tag = Tag.inTag();
		Tag prftag = Tag.inTag();
		Global.dumpDISPL("RoutineDescr.ofRoutine: ");
		System.out.println("RoutineDescr.ofRoutine: tag="+tag + "  prfTag="+prftag);
		
		RoutineDescr rut = (RoutineDescr) Global.DISPL.get(tag.val);
		if(rut == null) rut = new RoutineDescr(Kind.K_IntRoutine, tag);
		rut.prftag = prftag;
		String id = Global.moduleID + '_' + prftag.ident();
		rut.PSEG = new ProgramSegment("PSEG_" + id, Kind.K_SEG_CODE);
		ProgramSegment prevPSEG = Global.PSEG; Global.PSEG = rut.PSEG;
		rut.adr = new MemAddr(rut.PSEG,0);
		
		System.out.println("RoutineDescr.ofRoutine: "+Global.DISPL.get(prftag.val));
		ProfileDescr prf = (ProfileDescr) Global.DISPL.get(prftag.val);
		if(prf == null) Util.IERR("Missing Profile " + Scode.edTag(prftag.val));
//		boolean visflag = prf.kind == Kind.P_ROUTINE;
//		rut.tag = prf.tag;
//		Global.insideRoutine = true;

		Scode.inputInstr();
		while(Scode.curinstr == Scode.S_LOCAL) {
			Variable.ofLocal(prf.DSEG);
			Scode.inputInstr();
		}
	
		Instruction instr;
//		while((instr = (Instruction) Instruction.inInstruction()) != null) { Scode.inputInstr(); }
		while(Instruction.inInstruction()) { Scode.inputInstr(); }
	
		if(Scode.curinstr != Scode.S_ENDROUTINE) Util.IERR("Missing - endroutine");
		CTStack.checkStackEmpty();
		Global.PSEG.emit(new SVM_RETURN(prf.returAddr), "");
		CTStack.checkStackEmpty();

		rut.PSEG.dump("RoutineDescr.ofRoutine: "+id);
		prf.DSEG.dump("RoutineDescr.ofRoutine: "+id);
		Global.PSEG = prevPSEG;
//		Util.IERR("");
	}

	
	/**
	 * 	local_quantity ::= local var:newtag quantity_descriptor
	 * 
	 * quantity_descriptor ::= resolved_type < Rep count:number >?
	 * 
	 * resolved_type
	 * 		::= resolved_structure | simple_type
	 * 		::= INT range lower:number upper:number
	 * 		::= SINT
	 */
//	private static int InLocal(int nlocbyte) {
//		//--- Input Local Variable in Routine Body ---
//		Tag tag = Tag.inTag();
////		
//////		TypeLength = 0;
//////		type = intype;
//////		nbyte = TypeLength;
////		Type Type = new Type();
////		Type type = Type.tag;
////		int nbyte = 0; // ?????
////		Util.IERR("SJEKK DETTE");
////
//		int count = 1;
//		if(Scode.nextByte() == Scode.S_REP) {
//			Scode.inputInstr(); count = Scode.inNumber();
//			if(count == 0) {
//				Util.IERR("Illegal 'REP 0'"); count = 1;
//			}
//		}
//////	%+S      if type < T_Max then nbyte:=TTAB(type).nbyte endif;
////		
//////	%+S      locvar:=NEWOBJ(K_LocalVar,size(LocDescr));
//////	%+S      locvar.type:=type; IntoDisplay(locvar,tag);
//////		LocDescr locvar = new LocDescr(Kind.K_LocalVar, tag);
//		Variable locvar = Variable.ofLocal(Global.DSEG);
//		int nbyte = locvar.size();
//		nlocbyte = nlocbyte + (nbyte*count);
////		locvar.rela = nlocbyte;
//		Util.IERR("SJEKK DETTE");
//		return nlocbyte;
//	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("RoutineDescr.Write: " + this);
		if(PSEG != null) PSEG.write(oupt);
		oupt.writeKind(kind);
		tag.write(oupt);
		if(adr != null) {
			oupt.writeBoolean(true);
			adr.write(oupt);
			oupt.writeString(PSEG.ident);
		} else {
			oupt.writeBoolean(false);
		}
	}

	public static RoutineDescr read(AttributeInputStream inpt) throws IOException {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  BEGIN RoutineDescr.Read");
		Tag tag = Tag.read(inpt);
		RoutineDescr rut = new RoutineDescr(Kind.K_IntRoutine, tag);
		boolean present = inpt.readBoolean();
		if(present) {
			rut.adr = MemAddr.read(inpt);
			String segID = inpt.readString();
			rut.PSEG = (ProgramSegment) Segment.lookup(segID);
		}
		return(rut);
	}

}
