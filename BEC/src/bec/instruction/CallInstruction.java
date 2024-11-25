package bec.instruction;

import java.util.Vector;

import bec.compileTimeStack.ProfileItem;
import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.DataType;
import bec.compileTimeStack.StackItem;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.ParameterEval;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_CALL;
import bec.virtualMachine.SVM_CALL_TOS;
import bec.virtualMachine.SVM_NOT_IMPL;
import bec.virtualMachine.SVM_POP2MEM;
import bec.virtualMachine.SVM_PUSH;
import bec.virtualMachine.SVM_SYSCALL;

public class CallInstruction extends Instruction {
	int n; // Kind
	int profileTag;
	int routineTag;
	Vector<ParameterEval> argumentEvaluation;
	Vector<Instruction> CALL_TOS_Instructions;
	
	/**
	 * call_instruction
	 * 		::= connect_profile <parameter_eval>*
	 * 				connect_routine
	 * 
	 * 		connect_profile
	 * 			::= precall profile:tag
	 * 			::= asscall profile:tag
	 * 			::= repcall n:byte profile:tag
	 * 
	 * 		connect_routine ::= call body:tag | <instruction>+ call-tos
	 * 
	 * 		parameter_eval
	 * 			::= <instruction>+ asspar
	 * 			::= <instruction>+ assrep n:byte
	 */
	public CallInstruction(int n) {
		this.n = n;
		argumentEvaluation = new Vector<ParameterEval>();
		profileTag = Scode.inTag();
		Scode.inputInstr();
		
//		if(Scode.inputTrace > 3) System.out.println("CallInstruction: n="+n+", Curinstr="+Scode.edInstr(Scode.curinstr));
		
		LOOP:while(Scode.curinstr != Scode.S_CALL) {
			Vector<Instruction> instructions = Instruction.inInstructionSet();
			if(instructions.isEmpty()) break LOOP;
			
			if(Scode.curinstr == Scode.S_ASSPAR) {
				Scode.inputInstr();
				argumentEvaluation.add(new ParameterEval(instructions,-1));
			}
			else if(Scode.curinstr == Scode.S_ASSREP) {
				int nRep = Scode.inByte();
				Scode.inputInstr();
				argumentEvaluation.add(new ParameterEval(instructions,nRep));
//				System.out.println("CallInstruction: ASSREP: NextInstr="+Scode.edInstr(Scode.nextByte()));
			}
			else if(Scode.curinstr == Scode.S_CALL_TOS) {
//				Scode.inputInstr();
				CALL_TOS_Instructions = instructions;
				break LOOP;
			}
			else Util.IERR("Syntax error in call Instruction");
		}
	    //  ---------  Call Routine  ---------
		if(CALL_TOS_Instructions == null) routineTag = Scode.inTag();
	}
	
	public void doCode() {
		// CODING ....
		ProfileDescr spec = (ProfileDescr) Global.DISPL.get(profileTag);
		if(spec == null) Util.IERR(""+Scode.edTag(profileTag));
//		System.out.println("-------------------------------------------------- BEGIN PRINT CALL Instruction");
//		print("   ");
//		spec.print("   ");
//		System.out.println("-------------------------------------------------- ENDOF PRINT CALL Instruction");
		CTStack.dumpStack("BEGIN CallInstruction.doCode: ");
		if(spec.pKind > 0)
			 callSYS(spec);
		else {
			callDefault(spec);
		}
		// Routines return values on the RT-Stack
		Variable export = spec.getExport();
		if(export != null) {
			Type returnType = export.type;
//			System.out.println("CallInstructil.callSYS: returnType="+returnType);
			CTStack.pushTemp(returnType, "EXPORT: ");
			Global.PSEG.emit(new SVM_PUSH(returnType, export.address, returnType.size()), "CallInstruction: EXPORT " + spec);
//			Global.PSEG.emit(new SVM_PUSH(spec.export), "CallInstruction: EXPORT " + spec);
			Global.PSEG.dump("END CallInstruction.doCode: ");
			CTStack.dumpStack("END CallInstruction.doCode: ");
//			Util.IERR("");
		}
	}
	
	private void callSYS(ProfileDescr spec) {
		System.out.println("BEGIN CallInstruction.callSYS: " + spec.tag +" EXPORT="+ spec.export +" ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		spec.print("   ");
		ProfileItem pitem = new ProfileItem(Type.T_VOID,spec);
		CTStack.push(pitem);
		CTStack.dumpStack("BEGIN CallInstruction.callSYS: ");
		
		for(ParameterEval par:argumentEvaluation) {
			par.doCode();
			putPar(pitem,1);
		}
//	    Global.PSEG.dump();
//	    spec.DSEG.dump();
//	    ---------  Final Actions  ---------
	    if(pitem.nasspar != pitem.spc.npar) Util.IERR("Wrong number of Parameters");

//	    ---------  Call Routine  ---------
	    Global.PSEG.emit(new SVM_SYSCALL(spec.pKind), "");
	      
//	    repeat while npop<>0 do Pop; npop:=npop-1 endrepeat;
	    if(CTStack.TOS != pitem) Util.IERR("PARSE.CallSYS-3");
	    CTStack.pop();
//	    Util.IERR("");
		System.out.println("ENDOF CallInstruction.callSYS: ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	
	
	private int putPar(ProfileItem pItm, int nrep) { // export range(0:255) npop;
		int npop = 0;
		Variable param = (Variable) pItm.spc.params.get(pItm.nasspar).getMeaning();
		Type parType = param.type;
		int repCount = param.repCount;
//	      n:=TTAB(parType).nbyte; i:=nrep;
		if(nrep>repCount) Util.IERR("Too many values in repeated param: Got "+nrep+", expect "+repCount);
		pItm.nasspar = pItm.nasspar+1;
		StackItem tos = CTStack.TOS;
		Type st = tos.type;
		//--- First: Treat TOS ---
		System.out.println("CallInstruction.putPar: "+st.tag + " ===> " + parType.tag);
		if(st != parType) Util.GQconvert(parType);
		else if(tos instanceof AddressItem) tos.type = st;
		else Util.GQconvert(parType);
		
		if(CTStack.TOS instanceof AddressItem) Util.GQfetch("putPar: ");
		CTStack.pop();
		
		int n = parType.size();
		Global.PSEG.emit(new SVM_POP2MEM(param.address, n), "putPar: ");
		
//		pItm.spc.printTree(2);
//		Global.PSEG.dump("putPar: ");
//		pItm.spc.DSEG.dump("putPar: ");
//		CTStack.dumpStack("putPar: ");
//		Util.IERR("");
		
		if(nrep > 1) { // --- Then: Treat rest of rep-par ---
			Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	      repeat i:=i-1 while i <> 0
//	      do tos:=tos.suc;
//	--??     Husk at integer-type skal legges p} stacken m.h.t spesifikasjon!!!
//	--??     CheckTypesEqual(tos.type,p.type);
//	%+C      if tos.kind=K_Address then IERR("MODE mismatch below TOS") endif;
//	         if tos.type <> parType
//	         then
//	%+S           if SYSGEN <> 0 then
//	%+S           WARNING("PARSE: TYPE mismatch below TOS -- ASSREP") endif;
//	              if    parType=T_WRD4 then ConvRepWRD4(nrep); goto L2;
//	%+C           else IERR("PARSE: TYPE mismatch below TOS -- ASSREP");
//	              endif;
//	         endif;
//	      endrepeat;
//	   L2:if nrep < repCount
//	      then
//	%+E        Qf2(qDYADC,qSUB,qESP,cSTP,(repCount-nrep)*n);
//	      endif;
		}
		npop = nrep;
		return npop;
	}

	private void callDefault(ProfileDescr spec) {
		ProfileItem pitem = new ProfileItem(Type.T_VOID,spec);
		CTStack.push(pitem);

		for(ParameterEval par:argumentEvaluation) {
			par.doCode();
			putPar(pitem,1);
		}
//		Global.PSEG.dump("CallInstruction: After putpar: ");
//		spec.DSEG.dump("CallInstruction: After putpar: ");
//	    ---------  Final Actions  ---------
	    if(pitem.nasspar != pitem.spc.npar) Util.IERR("Wrong number of Parameters");
//		Util.IERR("NOT IMPLEMENTED");

//	    ---------  Call Routine  ---------
	    if(CALL_TOS_Instructions != null) {
	    	for(Instruction instr:CALL_TOS_Instructions) instr.doCode();
	    	Global.PSEG.emit(new SVM_CALL_TOS(), "");
	    	CTStack.pop();
//	    	Global.PSEG.dump("");
//	    	Util.IERR("");
	    } else {
	    	RoutineDescr rut = (RoutineDescr) Global.DISPL.get(routineTag);
	    	if(rut == null) Util.IERR("Unknown Routine: " + Scode.edTag(routineTag));
	    	Global.PSEG.emit(new SVM_CALL(rut, spec), ""+rut);
	    }
	      
//	    repeat while npop<>0 do Pop; npop:=npop-1 endrepeat;
//	    CTStack.dumpStack("CallInstruction.callDefault: ");
	    if(CTStack.TOS != pitem) Util.IERR("SSTMT.Call");
	    CTStack.pop();
	}
	
	@Override
	public void print(final String indent) {
//		System.out.println(edLead(indent) + this);
		String lead = null;
		switch(n) {
			case 0:  lead = "PRECALL "; break;
			case 1:  lead = "ASSCALL "; break;
			default: lead = "REPCALL " + n + " "; break;
		}
		System.out.println(indent + lead + Scode.edTag(profileTag));
		for(ParameterEval p:argumentEvaluation) p.printTree(indent + 1);
		if(CALL_TOS_Instructions != null) {
			for(Instruction instr:CALL_TOS_Instructions)
				System.out.println(indent + "   " + instr.toString());
			System.out.println(indent + "CALL-TOS");
//			Util.IERR("SJEKK DETTE");
		} else {
			System.out.println(indent + "CALL " + Scode.edTag(routineTag));
		}
	}
	
	public String toString() {
		String lead = null;
		switch(n) {
			case 0:  lead = "PRECALL "; break;
			case 1:  lead = "ASSCALL "; break;
			default: lead = "REPCALL " + n + " "; break;
		}
		return lead + Scode.edTag(profileTag) + " ...";
	}
	

}
