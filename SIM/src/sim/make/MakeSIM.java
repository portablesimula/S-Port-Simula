package sim.make;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class MakeSIM {

	private final static String RELEASE_HOME  = "C:/SPORT";
	
	static boolean verbose;
	static String selectors;
	static boolean fecListing;
	static int fecTraceLevel;
	static boolean becListing;
	static String sourceFileName;
	static String sCodeFileName;

	/**
	 * Print synopsis of standard options
	 */
	private static void help() {
//		System.out.println(Global.simulaReleaseID+" See: https://github.com/portablesimula");
		System.out.println("");
		System.out.println("Usage: java -jar simula.jar  [options]  sourceFile ");
		System.out.println("");
		System.out.println("possible options include:");
		System.out.println("  -help                      Print this synopsis of standard options");
		System.out.println("  -caseSensitive             Source file is case sensitive.");	
		System.out.println("  -compilerMode modeString   Simula Compiler mode *) see below.");	
		System.out.println("  -noexec                    Don't execute generated .jar file");
		System.out.println("  -nowarn                    Generate no warnings");
		System.out.println("  -noextension               Disable all language extensions");
		System.out.println("                             In other words, follow the Simula Standard literally");
		System.out.println("  -verbose                   Output messages about what the compiler is doing");

		System.out.println("  -select characters         First, all selectors are reset.");
		System.out.println("                             Then, for each character, the corresponding selector is set");
		System.out.println("  -sport                     Enable all S-PORT extensions");
		
		System.out.println("  -keepJava <directory>      Specify where to place generated .java files");
		System.out.println("                             Default: Temp directory which is deleted upon exit");
		System.out.println("  -output <directory>        Specify where to place generated executable .jar file");
		System.out.println("                             Default: Same directory as source file");
		System.out.println("  -extLib <directory>        Specify where to search for precompiled classes and procedures");
		System.out.println("                             If not found, output directory is also searched");
		System.out.println("");
		System.out.println("sourceFile ::= Simula Source File");
		System.out.println("");
		System.out.println("modeString ::= viaJavaSource | directClassFiles | simulaClassLoader");
		System.out.println("");
		System.out.println("");
		System.out.println("viaJavaSource");
		System.out.println("   The Simula Compiler will generate Java source files and use");
		System.out.println("   the Java compiler to generate JavaClass files which in turn");
		System.out.println("   are collected together with the Runtime System into the");
		System.out.println("   resulting executable jar-file.");
		System.out.println("");
		System.out.println("");
		System.out.println("directClassFiles");
		System.out.println("   The Simula Compiler will generate JavaClass files directly");
		System.out.println("   which in turn are collected together with the Runtime System");
		System.out.println("   into the resulting executable jar-file.");
		System.out.println("   No Java source files are generated.");
		System.out.println("");
		System.out.println("");
		System.out.println("simulaClassLoader");
		System.out.println("   The Simula Compiler will generate ClassFile byte array and");
		System.out.println("   load it directly. No intermediate files are created.");
		System.out.println("");
		System.out.println("   NOTE: In this mode, the editor will terminate after the first");
		System.out.println("         program execution");

		System.exit(0);
	}

	public static void main(String[] argv) {
		try {
			System.out.println("Make SPORT SIM Compiler.jar in "+RELEASE_HOME);
			

			// Parse command line arguments.
			for(int i=0;i<argv.length;i++) {
				String arg=argv[i];
				if (arg.charAt(0) == '-') { // command line option
					if (arg.equalsIgnoreCase("-help")) help();
//					else if (arg.equalsIgnoreCase("-noexec")) Option.noExecution=true;
//					else if (arg.equalsIgnoreCase("-nowarn")) Option.WARNINGS=false;
//					else if (arg.equalsIgnoreCase("-noextension")) Option.EXTENSIONS=false;
					else if (arg.equalsIgnoreCase("-verbose")) verbose=true;

					else if (arg.equalsIgnoreCase("-select")) selectors = argv[++i];
					
					else if (arg.equalsIgnoreCase("-FEC:Listing")) fecListing = true;
					else if (arg.equalsIgnoreCase("-BEC:Listing")) becListing = true;
//					else if (arg.equalsIgnoreCase("-output")) setOutputDir(argv[++i]);
//					else if (arg.equalsIgnoreCase("-extLib")) Global.extLib=new File(argv[++i]);
//					else if (arg.equalsIgnoreCase("-source")) Option.internal.SOURCE_FILE=argv[++i];
//					else if (arg.equalsIgnoreCase("-runtimeUserDir")) Option.internal.RUNTIME_USER_DIR=argv[++i];
//					else if (arg.equalsIgnoreCase("-noConsole")) noConsole = true;

					else if (arg.equalsIgnoreCase("--enable-preview")) ; // TODO: TESTING_JDK24: Change when ClassFile API is released

//					else error("Unknown option "+arg);
					else {
						System.out.println("ERROR: Unknown option " + arg);
						help();
					}
				} else if(sourceFileName==null) sourceFileName = arg;
				else {
					System.out.println("ERROR: multiple input files specified");
					help();
				}
			}

			
			File releaseHome=new File(RELEASE_HOME);
			releaseHome.mkdirs();
//			String compilerManifest=SportBEC_ROOT+"/src/bec/make/CompilerManifest.MF";
//			
//			exec("jar", "cmf", compilerManifest, RELEASE_HOME+"/BEC.jar", "-C", COMPILER_BIN, "./bec");
//			exec("jar", "-tvf", RELEASE_HOME+"/BEC.jar");
			
			INLINE_TEST();
		} catch(Exception e) { e.printStackTrace(); }
	}

	
	private static void INLINE_TEST() {
		String name ="adHoc00";
		sourceFileName = "C:\\GitHub\\S-Port-Simula\\SIM\\src\\sim\\testPrograms\\"+name+".sim";
		sCodeFileName  = "C:\\GitHub\\S-Port-Simula\\SIM\\src\\sim\\testPrograms\\scode\\"+name+".scd";
		
//		verbose = true;
//		fecListing = true;
		becListing = true;
//		fecTraceLevel = 4;
		
		int execCode = callFEC();
//		System.out.println("ExitCode = "+execCode);
		
		if(execCode == 0) callBEC();
	}
	
	private static int callFEC() {
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\SPORT\\FEC.jar");
		if(verbose) cmds.add("-verbose");
		if(fecTraceLevel > 0) { cmds.add("-SPORT:trace"); cmds.add(""+fecTraceLevel); }
		if(fecListing) cmds.add("-SPORT:listing");
		if(selectors != null) {	cmds.add("-SPORT:select"); cmds.add(selectors); }
		cmds.add("-SPORT:SCodeFile"); cmds.add(sCodeFileName);
		cmds.add(sourceFileName);

		try {
			return exec(cmds);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return -4;
	}
	
	private static int callBEC() {
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\SPORT\\BEC.jar");
		cmds.add("-verbose");
		if(becListing) cmds.add("-listing");
		cmds.add(sCodeFileName);

		try {
			return exec(cmds);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return -6;
	}
	
	// ***************************************************************
	// *** EXECUTE OS COMMAND
	// ***************************************************************
	public static int exec(final Vector<String> cmd) throws IOException {
		String[] cmds = new String[cmd.size()];
		cmd.copyInto(cmds);
		return (exec(cmds));
	}

	public static int exec(String... cmd) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		String line="";
		for(int i=0;i<cmd.length;i++) line=line+" "+cmd[i];
        System.out.println("MakeCompiler.execute: "+line);
//	    String cmd=command.trim()+'\n';
		Process process = runtime.exec(cmd);
		//try
		{ InputStream err=process.getErrorStream();
		  InputStream inp=process.getInputStream();
		  while(process.isAlive())
		  { while(err.available()>0) System.err.append((char)err.read());
		    while(inp.available()>0) System.out.append((char)inp.read());
			
		  }
		  // process.waitFor();
		} //catch(InterruptedException e) { e.printStackTrace(); }
		return(process.exitValue());
	}

	
	// ***************************************************************
	// *** LIST .jar file
	// ***************************************************************
	/**
	 * List .jar file
	 * @param file the .jar file
	 */
	public static void listJarFile(final File file) {
		System.out.println("---------  LIST .jar File: " + file + "  ---------");
		if (!(file.exists() && file.canRead())) {
			System.out.println("ERROR: Can't read .jar file: " + file);
			return;
		}
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(file);
			Manifest manifest = jarFile.getManifest();
			Attributes mainAttributes = manifest.getMainAttributes();
			Set<Object> keys = mainAttributes.keySet();
			for (Object key : keys) {
				String val = mainAttributes.getValue(key.toString());
				System.out.println(key.toString() + "=\"" + val + "\"");
			}

			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String size = "" + entry.getSize();
				while (size.length() < 6)
					size = " " + size;
				FileTime fileTime = entry.getLastModifiedTime();
				String date = DateTimeFormatter.ofPattern("uuuu-MMM-dd HH:mm:ss", Locale.getDefault())
						.withZone(ZoneId.systemDefault()).format(fileTime.toInstant());
				System.out.println("Jar-Entry: " + size + "  " + date + "  \"" + entry + "\"");
			}
		} catch (IOException e) {
			System.out.println("IERR: Caused by: " + e);
		} finally {
			if (jarFile != null)
				try {
					jarFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}


}
