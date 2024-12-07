package bec.instruction;

import java.util.Vector;

import bec.compileTimeStack.ProfileItem;
import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.virtualMachine.SVM_CALL;
import bec.virtualMachine.SVM_POP2MEM;
import bec.virtualMachine.SVM_PUSH;
import bec.virtualMachine.SVM_SYSCALL;

public abstract class CALL extends Instruction {
	
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
	public static void ofScode(int n) {
		int profileTag = Scode.ofScode();
		Scode.inputInstr();
		
//		if(Scode.inputTrace > 3) System.out.println("CallInstruction: n="+n+", Curinstr="+Scode.edInstr(Scode.curinstr));
		ProfileDescr spec = (ProfileDescr) Global.DISPL.get(profileTag);
		if(spec == null) Util.IERR(""+Scode.edTag(profileTag));
		ProfileItem pitem = new ProfileItem(Type.T_VOID,spec);
		CTStack.push(pitem);
		
//		Vector<Instruction> CALL_TOS_Instructions = null;
		boolean CALL_TOS = false;
		
		LOOP:while(Scode.curinstr != Scode.S_CALL) {
			Instruction.inInstructions();
			if(Scode.curinstr == Scode.S_ASSPAR) {
				Scode.inputInstr();
				putPar(pitem,1);
			}
			else if(Scode.curinstr == Scode.S_ASSREP) {
				int nRep = Scode.inByte();
				Scode.inputInstr();
				putPar(pitem,nRep);
//				System.out.println("CallInstruction: ASSREP: NextInstr="+Scode.edInstr(Scode.nextByte()));
			}
			else if(Scode.curinstr == Scode.S_CALL_TOS) {
				CALL_TOS = true;
				break LOOP;
			}
			else Util.IERR("Syntax error in call Instruction");
		}
//	    ---------  Final Actions  ---------
	    if(pitem.nasspar != pitem.spc.params.size()) Util.IERR("Wrong number of Parameters");
//	    ---------  Call Routine  ---------
		ObjectAddress prfAddr = ObjectAddress.ofSegAddr(spec.DSEG, 0);
	    if(CALL_TOS) {
	    	Global.PSEG.emit(SVM_CALL.ofTOS(prfAddr), "");
	    	CTStack.pop();
	    } else {
			int bodyTag = Scode.ofScode();
	    	if(spec.pKind > 0) {
	    		Global.PSEG.emit(new SVM_SYSCALL(spec.pKind), "");
	    	} else {
	    		RoutineDescr rut = (RoutineDescr) Global.DISPL.get(bodyTag);
	    		if(rut == null) Util.IERR("Unknown Routine: " + Scode.edTag(bodyTag));
//	    		this.rutAddr = rut.adr;
	    		Global.PSEG.emit(new SVM_CALL(rut.adr, prfAddr), ""+rut);
	    	}
	    }
//	    repeat while npop<>0 do Pop; npop:=npop-1 endrepeat;
	    if(CTStack.TOS != pitem) Util.IERR("PARSE.CallSYS-3");
	    CTStack.pop();
		// Routines return values on the RT-Stack
		Variable export = spec.getExport();
		if(export != null) {
			Type returnType = export.type;
//			System.out.println("CallInstructil.callSYS: returnType="+returnType);
			CTStack.pushTemp(returnType, "EXPORT: ");
			Global.PSEG.emit(new SVM_PUSH(returnType, export.address, returnType.size()), "CallInstruction: EXPORT " + spec);
//			Global.PSEG.dump("END CallInstruction.doCode: ");
//			CTStack.dumpStack("END CallInstruction.doCode: ");
//			Util.IERR("");
		}
//		Global.PSEG.dump("END CALL: ");
//		Util.IERR("");
	}
	
	private static int putPar(ProfileItem pItm, int nrep) { // export range(0:255) npop;
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
//		System.out.println("CallInstruction.putPar: "+st.tag + " ===> " + parType.tag);
		if(st != parType) CONVERT.GQconvert(parType);
		else if(tos instanceof AddressItem) tos.type = st;
		else CONVERT.GQconvert(parType);
		
		if(CTStack.TOS instanceof AddressItem) Util.GQfetch("putPar: ");
		CTStack.pop();
		
		int parSize = parType.size();
		ObjectAddress parAddr = param.address;
		Global.PSEG.emit(new SVM_POP2MEM(parAddr, parSize), "putPar: ");
		
//		pItm.spc.printTree(2);
//		Global.PSEG.dump("putPar: ");
//		pItm.spc.DSEG.dump("putPar: ");
//		CTStack.dumpStack("putPar: ");
//		Util.IERR("");
		
		if(nrep > 1) { // Then: Treat rest of rep-par ---
			CTStack.dumpStack("putPar: ");
			for(int i=nrep-1;i>0;i--) {
				StackItem TOS = CTStack.takeTOS();
				System.out.println("CALL.putPar: "+TOS);
				if(TOS instanceof AddressItem) Util.IERR("MODE mismatch below TOS");
				if(TOS.type != parType) Util.IERR("TYPE mismatch below TOS -- ASSREP");
				parAddr = parAddr.ofset(parSize);
				Global.PSEG.emit(new SVM_POP2MEM(parAddr, parSize), "putPar: ASSREP: ");
			}
//			CTStack.dumpStack("putPar: ");
//			Global.PSEG.dump("putPar: ");
//			Util.IERR("Parse.XXX: NOT IMPLEMENTED: nrep="+nrep);
		}
		npop = nrep;
		return npop;
	}

}
