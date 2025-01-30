package make;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Make_SimulettaFEC_Jarfile {
	private final static String RELEASE_HOME  = "C:/SPORT";
	private final static String Simuletta_FEC_ROOT = "C:/GitHub/S-Port-Simula/SimulettaFEC";
	private final static String COMPILER_BIN  = Simuletta_FEC_ROOT+"/bin";
	
//	C:\GitHub\S-Port-Simula\BEC\src\bec\make\CompilerManifest.MF

	public static void main(String[] args) {
		try {
			System.out.println("Make SIMULETTA FEC Compiler.jar in "+RELEASE_HOME);
			File releaseHome=new File(RELEASE_HOME);
			releaseHome.mkdirs();
			String compilerManifest=Simuletta_FEC_ROOT+"/src/make/SML_Manifest.MF";
			
			exec("jar", "cmf", compilerManifest, RELEASE_HOME+"/SimulettaFEC.jar", "-C", COMPILER_BIN, "./simuletta");
			exec("jar", "-tvf", RELEASE_HOME+"/SimulettaFEC.jar");
			
			System.out.println("Make_SimulettaFEC_Jarfile - DONE");
		} catch(Exception e) { e.printStackTrace(); }
	}


	public static int exec(String... cmd) throws IOException {
		String line="";
		for(int i=0;i<cmd.length;i++) line=line+" "+cmd[i];
        System.out.println("MakeSIM.execute: command="+line);
		ProcessBuilder processBuilder = new ProcessBuilder(cmd);
		processBuilder.redirectErrorStream(true);
		try {
			Process process = processBuilder.start();		
			InputStream output = process.getInputStream();  // Process' output
			while (process.isAlive()) {
				while (output.available() > 0)
					System.out.append((char) output.read());
//				System.out.println("ALIVE: "+process.isAlive());
			}
			System.out.println("RETURN: "+process.exitValue());
			Thread.dumpStack();
			return (process.exitValue());

		} catch(Exception e) {
			System.out.println("ERROR: "+e);
			throw new RuntimeException("Process Execution failed: " + line, e);
		}
	}

}
