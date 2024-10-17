package bec.make;

import java.io.File;
import bec.util.Util;

public class Make_BEC_Jarfile {
	private final static String RELEASE_HOME  = "C:/SPORT";
	private final static String SportBEC_ROOT = "C:/WorkSpaces/SPortBECinJava/SportBEC";
	private final static String COMPILER_BIN  = SportBEC_ROOT+"/bin";

	public static void main(String[] args) {
		try {
			System.out.println("Make SPORT BEC Compiler.jar in "+RELEASE_HOME);
			File releaseHome=new File(RELEASE_HOME);
			releaseHome.mkdirs();
			String compilerManifest=SportBEC_ROOT+"/src/bec/make/CompilerManifest.MF";
			
			Util.exec("jar", "cmf", compilerManifest, RELEASE_HOME+"/BEC.jar", "-C", COMPILER_BIN, "./bec");
			Util.exec("jar", "-tvf", RELEASE_HOME+"/BEC.jar");
		} catch(Exception e) { e.printStackTrace(); }
	}

}
