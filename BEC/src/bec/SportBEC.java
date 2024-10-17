package bec;

import bec.syntaxClass.module.S_Module;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

import java.util.Vector;

import bec.syntaxClass.module.InterfaceModule;
import bec.syntaxClass.module.MainProgram;
import bec.syntaxClass.module.ModuleDefinition;

public class SportBEC {
	String programHead;
	S_Module module;
	
	public static void main(String[] argv) {
		String scodeSource = null;
//		Scode.inputTrace = 4;
//		new Pick_FEC_Source();
//		new Pick_RTS_Source();		

		

		// Parse command line arguments.
		for(int i=0;i<argv.length;i++) {
			String arg=argv[i];
			if (arg.charAt(0) == '-') { // command line option
				if (arg.equalsIgnoreCase("-help")) help();
				else if (arg.equalsIgnoreCase("-inputTrace")) Scode.inputTrace = 4;
				else if (arg.equalsIgnoreCase("-listing")) Scode.listing = true;
				else if (arg.equalsIgnoreCase("-verbose")) Global.verbose=true;
				else {
					Util.ERROR("Unknown option " + arg);
					help();
				}
			} else if(scodeSource==null) scodeSource = arg;
			else Util.ERROR("multiple input files specified");
		}
		
		if(scodeSource==null) {
			Util.ERROR("no input file specified");
			help();
		}

		new SportBEC(scodeSource);
	}


	/**
	 * Print synopsis of standard options
	 */
	private static void help() {
		System.out.println("");
		System.out.println("Usage: java -jar SportFEC.jar  [options]  ScodeFile ");
		System.out.println("");
		System.out.println("possible options include:");
		System.out.println("  -help        Print this synopsis of standard options");
		System.out.println("  -inputTrace  Produce input Scode trace");
		System.out.println("  -listing     Produce pretty Scode listing");
		System.out.println("  -verbose     Output messages about what the compiler is doing");
		System.out.println("");
		System.out.println("sourceFile ::= S-Code Source File");

		System.exit(0);
	}


	public SportBEC(String scodeSource) {
//		System.out.println("SportBEC: " + scodeSource);
		Global.scodeSource = scodeSource;
		Scode.initScode();
		parse();
	}

	/**
	 * S-program ::= program program_head:string
	 * 						 program_body endprogram
	 * 
	 * 	program_body 
	 * 		::= interface_module
	 * 		::= macro_definition_module
	 * 		::= <module_definition>*
	 * 		::= main <local_quantity>* <program_element>*
	 */
	public void parse() {
		Scode.expect(Scode.S_PROGRAM);
  		Global.progIdent = Scode.inString();
  		
  		switch(Scode.nextByte()) {
	  		case Scode.S_GLOBAL -> new InterfaceModule();
//	  		case Scode.S_MACRO  -> new MacroDefinition();
	  		case Scode.S_MODULE -> inModules();
	  		case Scode.S_MAIN ->   new MainProgram();
  		}
  		
  		if(Scode.accept(Scode.S_GLOBAL)) {
			new InterfaceModule();
			Scode.inputInstr();
		}
	}
	
	
	public static Vector<S_Module> inModules() {
		Vector<S_Module> result = new Vector<S_Module>();
  		S_Module module;
  		do {
  			module = new ModuleDefinition();
  			result.add(module);
//			System.out.println("SportBEC.parse: curinstr="+Scode.edInstr(Scode.curinstr));
  		} while(Scode.accept(Scode.S_MODULE));
  		
  		if(Scode.listing)
  			for(S_Module modle:result) modle.printTree(0);
  		return result;
	}

	public void print() {
		
	}
	
	public String toString() {
		return "MODULE " + programHead;
	}

}
