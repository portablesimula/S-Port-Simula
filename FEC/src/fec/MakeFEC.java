package fec;

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

public class MakeFEC {

	
	public static void main(String[] argv) {
		compile("CLASS_COMMON.sim");
	}
	
	private static void compile(String name) {
//		String fileName = "C:/WorkSpaces/SPort-System/S_Port/src/sport/fec/"+name;
		String fileName = "C:/WorkSpaces/SportFECinSimula/SPortFEC/src/fec/source/"+name;
		
		Vector<String> cmds = new Vector<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add("C:\\Users\\omyhr\\Simula\\Simula-2.0\\simula.jar");
		cmds.add("-verbose");
		cmds.add("-sport");
		cmds.add("-select"); cmds.add("ZDTW");
		cmds.add(fileName);

		try {
			exec(cmds);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        System.out.println("MakeCompiler.execute: command="+line);
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
