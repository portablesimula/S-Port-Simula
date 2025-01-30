package simuletta.compiler.common;

import java.util.Vector;

import simuletta.compiler.Global;
import simuletta.compiler.declaration.Declaration;
import simuletta.compiler.declaration.scope.DeclarationScope;
import simuletta.compiler.declaration.scope.InsertedModule;
import simuletta.compiler.declaration.scope.ProgramModule;
import simuletta.utilities.Util;

public class Comn {
//	public static Vector<InsertedModule> modset=new Vector<InsertedModule>(); // The set of all inserted modules
//	public static Vector<Declaration> bodyList=new Vector<Declaration>(); // See: RoutineBody.doSCodeDeclaration
//	public static ProgramModule currentModule=new ProgramModule(null);
//	public static DeclarationScope currentScope=null; // Current Scope. Maintained during Checking and Coding
//    public static boolean SportOk=true;       // true: allow special S-code extensions
//	public static boolean allVisible;           // Pass1: Set by Compiler Directive  %VISIBLE and %HIDDEN
//	public static SCodeFile sCode;//=new SCodeFile();

//
//	// ******   ERROR HANDLING   ******
//
////	public static int curline;
//	public static int nerr;
//
//	public static void ERROR(String msg) {
//		StringBuilder s=new StringBuilder();
//		System.out.println(s.toString());
//		if(Global.sourceLineNumber>0) s.append("LINE " + Global.sourceLineNumber + "  ");
//		s.append("ERROR: " + msg); nerr=nerr+1; System.out.println(s.toString());
//		Util.FATAL_ERROR("ERROR: MIDLERTIDIG STOPP HER !!! - SKAL RETTES SEINERE");
//	}
//
//	public static void WARNING(String msg) {
//		StringBuilder s=new StringBuilder();
//		if(Global.sourceLineNumber>0) s.append("LINE " + Global.sourceLineNumber + "  ");
//		s.append(" NOTE: " + msg); System.out.println(s.toString());
//	}
//
////	public static void TRACE(String id, int lno, String msg) {
////		if(DO_TRACING > 0) {
////			StringBuilder s=new StringBuilder();
////			if(Global.sourceLineNumber>0) s.append("LINE " + Global.sourceLineNumber + "  ");
////			s.append(id); s.append("(");
////			s.append(lno); s.append(")  ");
////			s.append(msg);
////			System.out.println(s.toString());
////			//breakoutimage; inimage;
////		}		
////	}
//
//	public static void IERR() { 
//		StringBuilder s=new StringBuilder();
//		if(Global.sourceLineNumber>0) s.append("LINE " + Global.sourceLineNumber + "  ");
//		s.append(" Internal Error");
//		Util.FATAL_ERROR(s.toString());
//	}
//
//	public static void FATAL_ERROR(String msg) {
//		Util.FATAL_ERROR(msg);
//	}
//
}
