package simuletta.RTS_FEC_InterfaceGenerator;

import static simuletta.compiler.Global.modset;
import static simuletta.utilities.Util.createFile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;
import java.util.Vector;

import simuletta.RTS_FEC_InterfaceGenerator.predef.PredefCompiler;
import simuletta.RTS_FEC_InterfaceGenerator.predef.util.RTS_FEC_Interface_Option;
import simuletta.RTS_FEC_InterfaceGenerator.predef.util.PredefGlobal;
import simuletta.compiler.Global;
import simuletta.compiler.declaration.Declaration;
import simuletta.compiler.declaration.VariableDeclaration;
import simuletta.compiler.declaration.scope.InsertedModule;
import simuletta.compiler.declaration.scope.ProgramModule;
import simuletta.compiler.declaration.scope.Record;
import simuletta.utilities.Tag;
import simuletta.utilities.Util;

public class RTS_FEC_InterfaceGenerator {

	public static void doGenerateFiles(String moduleident) throws IOException {
		doGenerateInserts(moduleident);
		doGenerateRTSInit(moduleident);
		
		FileReader inpt=new FileReader("C:/WorkSpaces/SimulettaFECinJava/SimulettaFEC/src/simulaRTS/sml/RTSINIT1.def");
		FileWriter oupt=new FileWriter("C:/WorkSpaces/SPort-System/S_Port/src/sport/fec/RTS-FEC-INTERFACE1.def");
		doGenerateTagDefs(inpt,oupt);

		inpt=new FileReader("C:/WorkSpaces/SimulettaFECinJava/SimulettaFEC/src/simulaRTS/sml/RTSINIT2.def");
		oupt=new FileWriter("C:/WorkSpaces/SPort-System/S_Port/src/sport/fec/RTS-FEC-INTERFACE2.def");
		oupt.write("integer RTS_TAG_LIMIT=1774;\n");
		doGenerateTagDefs(inpt,oupt);
		
		doGeneratePredefAttrFile();

		oupt=new FileWriter("C:/WorkSpaces/SPort-System/S_Port/src/sport/fec/RTS-FEC-TAGTABLE.def");
		doGenerateTagTable(oupt);

	}

	private static void doGenerateTagTable(FileWriter oupt) throws IOException {
		if (RTS_FEC_Interface_Option.verbose) System.out.println("************************** BEGIN WRITE RTS-FEC-TAGTABLE **************************");
		int taglimit=0;
		for(InsertedModule m:modset) {
			if(RTS_FEC_Interface_Option.INTERFACE_TRACE_LEVEL > 2) Util.println(m.identifier+" "+m.tagbase+" "+m.taglimit);
			taglimit=Math.max(taglimit,m.taglimit);
		}
		
		oupt.write("\n\n% NOTE: Autogenerated file, generated by: RTS_FEC_InterfaceGenerator in WorkSpace: SimulettaFECinJava\n\n");
		oupt.write("\nref(TAGTABLE) TAGTAB;");
		oupt.write("\n\ntext procedure tagID(i); integer i; begin");
		oupt.write("\n	tagID:-TAGTAB.tagIdent(i);");
		oupt.write("\nend;");
		oupt.write("\n\nClass TAGTABLE; begin");
		oupt.write("\n");
		oupt.write("\n\n	text array ident(0:"+(taglimit+1500)+");");
		oupt.write("\n	text undef;");
		
		oupt.write("\n\n	text procedure tagIdent(i); integer i; begin");
//		oupt.write("\n		sysout.outtext(\"TAGIDENT: \" & edit(i)); sysout.outimage;");
//		oupt.write("\n		if i > "+(taglimit+1)+" then begin");
//		oupt.write("\n			sysout.outtext(\"TAGTABLE.tagIdent: ERROR - Illegal tag \" & edit(i)); sysout.outimage;");
//		oupt.write("\n		end;"); 
		oupt.write("\n		if ident(i) =/= none then tagIdent:-ident(i) else tagIdent:-undef;");
		oupt.write("\n	end;");

		oupt.write("\n\n	procedure INIT; begin");
		oupt.write("\n		undef:-copy(\"TAG\");");
		
		oupt.write("\n		ident(1):-copy(\"BOOL\");");						
		oupt.write("\n		ident(2):-copy(\"CHAR\");");						
		oupt.write("\n		ident(3):-copy(\"INT\");");						
		oupt.write("\n		ident(4):-copy(\"SINT\");");						
		oupt.write("\n		ident(5):-copy(\"REAL\");");						
		oupt.write("\n		ident(6):-copy(\"LREAL\");");						
		oupt.write("\n		ident(7):-copy(\"AADDR\");");						
		oupt.write("\n		ident(8):-copy(\"OADDR\");");						
		oupt.write("\n		ident(9):-copy(\"GADDR\");");						
		oupt.write("\n		ident(10):-copy(\"PADDR\");");						
		oupt.write("\n		ident(11):-copy(\"RADDR\");");						
		oupt.write("\n		ident(12):-copy(\"SIZE\");");						
		
		//oupt.write("\n		ident(32):-copy(\"STRING\");");
		for(InsertedModule m:modset) {
			if(RTS_FEC_Interface_Option.INTERFACE_TRACE_LEVEL > 2) Util.println(m.identifier+" "+m.tagbase+" "+m.taglimit);
			oupt.write("\n		init_"+m.identifier+";");
		}
		
		
		oupt.write("\n	end;\n");
		
		
		for(InsertedModule m:modset) {
			if(RTS_FEC_Interface_Option.INTERFACE_TRACE_LEVEL > 2) Util.println(m.identifier+" "+m.tagbase+" "+m.taglimit);
			oupt.write("\n	procedure init_"+m.identifier+"; begin");
			oupt.write("\n		-- tagbase="+m.tagbase+", taglimit="+m.taglimit);
			for(Declaration d:m.declarationList) {
				int code=m.tagbase+d.getTag().getXtag();
				String id=d.getTag().getIdent();
				if(RTS_FEC_Interface_Option.INTERFACE_TRACE_LEVEL > 2) Util.println("TAG ["+code + "] = "+id);
				oupt.write("\n		ident("+code+"):-copy(\""+id+"\");");
				if(d instanceof Record rec) {
					for(Declaration atr:rec.declarationList) {
						code=m.tagbase+atr.getTag().getXtag();
						String id2=atr.getTag().getIdent();
						if(RTS_FEC_Interface_Option.INTERFACE_TRACE_LEVEL > 2) Util.println("TAG ["+code + "] = "+id2+"   -- "+id+'.'+id2);
						oupt.write("\n		ident("+code+"):-copy(\""+id2+"\");");						
					}
				}
			}
			oupt.write("\n	end;\n");
		}
		
		oupt.write("\n\n	INIT;");
		oupt.write("\n\nend class TAGTABLE;");
		oupt.flush(); oupt.close();
	}

	private static void doGenerateTagDefs(FileReader inpt,FileWriter oupt) throws IOException {
		oupt.write("\n\n% NOTE: Autogenerated file, generated by: RTS_FEC_InterfaceGenerator in WorkSpace: SimulettaFECinJava\n\n");
		try (LineNumberReader reader = new LineNumberReader(inpt)) {
			LOOP:while(true) {
				String line=reader.readLine();
				if(line==null) break LOOP;
				if(RTS_FEC_Interface_Option.TRACE_ATTRIBUTE_INPUT) System.out.println("Line "+reader.getLineNumber()+": "+line);
				if(line.length()>0 && line.charAt(0)!='%') {
					String outputLine=treatLine(line);
					if(RTS_FEC_Interface_Option.TRACE_ATTRIBUTE_OUTPUT) System.out.println("OUTPUT:  "+outputLine);
					oupt.write(outputLine+"\n");
				}
			}
		}
		oupt.flush(); oupt.close();
	}
	
	private static String treatLine(String line) {
		StringTokenizer st = new StringTokenizer(line," =.;",true);
		String type=st.nextToken();
		st.nextToken(); // Skip blank
		String identifier=st.nextToken();
		st.nextToken(); // Skip =
		String tagIdent=st.nextToken();
		InsertedModule module=null;
		Tag modTag = null;
   LOOP:for(InsertedModule mod:modset) {
			modTag=mod.findTag(tagIdent);
			if(modTag!=null) {
				module=mod; break LOOP;
			}
		}
   		Declaration meaning=ProgramModule.findMeaning(tagIdent);
   		if(meaning!=null) {
			Tag tag=meaning.getTag();
			Util.ASSERT(tag.equals(modTag),"tag =/= modTag");
//			int xcode=tag.getCode();
			int xcode=module.tagbase+tag.getXtag();
			String comment="RTS "+meaning.getClass().getSimpleName()+": "+tag.getIdent()+"="+xcode+", xTag="+tag.getXtag()+", module="+module.identifier;
			String sep=st.nextToken();
			if(sep.equalsIgnoreCase(".")) {
				String attrIdent=st.nextToken();
//				System.out.println("RTS_FEC_InterfaceGenerator.treatLine: attrIdent="+attrIdent);
//				System.out.println("RTS_FEC_InterfaceGenerator.treatLine: meaning="+meaning.getClass().getSimpleName());
				if(meaning instanceof VariableDeclaration var) {
					Record rec=var.type.getQualifyingRecord();
					tag=rec.findAttribute(attrIdent).getTag();
					xcode=tag.getCode();
//					System.out.println("RTS_FEC_InterfaceGenerator.treatLine: var="+var);
//					System.out.println("RTS_FEC_InterfaceGenerator.treatLine: tag="+tag);
//					System.out.println("RTS_FEC_InterfaceGenerator.treatLine: xcode="+xcode);
				} else if(meaning instanceof Record rec) {
					tag=rec.findAttribute(attrIdent).getTag();
					xcode=tag.getCode();
					comment="RTS Record.Attribute: "+rec.identifier+'.'+tag.getIdent()+"="+xcode;
				}
			}
			if(type.equalsIgnoreCase("text")) return("text "+identifier+"=\""+encodeTag(xcode)+"\"; -- "+comment);		
			else if(type.equalsIgnoreCase("integer")) return("integer "+identifier+'='+xcode+"; -- "+comment);
			else { Util.IERR(""); return(null); }
		} else {
			if(type.equalsIgnoreCase("text")) return("text "+identifier+"=\""+encodeTag(0)+"\"; --------------------------------------------------------------- NOT FOUND");		
			else if(type.equalsIgnoreCase("integer")) return("integer "+identifier+"=0; --------------------------------------------------------------- NOT FOUND");
			else { Util.IERR(""); return(null); }			
		}
		
	}
	
	private static String encodeTag(int xcode) {
		int b1=xcode/256;
		int b2=xcode-(b1*256);
		return("!"+b1+"!!"+b2+"!");
	}

	private static void doGenerateRTSInit(String moduleident) throws IOException {
		if (RTS_FEC_Interface_Option.verbose) System.out.println("************************** BEGIN WRITE RTSINIT FILE: "+moduleident+" **************************");
		String fileName="C:/WorkSpaces/SPort-System/S_Port/src/sport/fec/RTSINIT.ini";
		FileWriter oupt=new FileWriter(fileName);
//		if (Option.verbose)
			Util.println("RTS_FEC_InterfaceGenerator.doGenerateRTSInit: \"" + fileName+"\"");
//		createFile(oupt);
		oupt.write("\n\n% NOTE: Autogenerated file, generated by: RTS_FEC_InterfaceGenerator in WorkSpace: SimulettaFECinJava\n\n");
		int lastusedtag=0;
		oupt.write("lastusedtag:=31;\n");
		for(InsertedModule m:modset) {
			int n=m.taglimit-m.tagbase+1;
			Util.println(m.identifier+" "+m.tagbase+" "+m.taglimit+"  n="+n);
			oupt.write("   regnewmodule(\""+m.identifier+"\",\"cc\","+n+");\n");
			lastusedtag=m.taglimit;
		}
		oupt.write("lastusedtag:="+lastusedtag+';');
		oupt.flush(); oupt.close();
	}

	private static void doGenerateInserts(String moduleident) throws IOException {
		if (RTS_FEC_Interface_Option.verbose) System.out.println("************************** BEGIN WRITE COMBINED RTS LIST: "+moduleident+" **************************");
		String relativeAttributeFileName="Attrs/BEC/"+Global.packetName+"/"+Global.sourceName+".dat";
		File attributeFile = new File(Global.outputDir,relativeAttributeFileName);
//		if (Option.verbose)
			Util.println("RTS_FEC_InterfaceGenerator.doGenerateInserts: \"" + attributeFile+"\"");
		createFile(attributeFile);
		FileOutputStream fileOutputStream = new FileOutputStream(attributeFile);
		DataOutputStream out=new DataOutputStream(fileOutputStream);
		out.writeInt(modset.size());
		for(InsertedModule m:modset) {
			Util.println(m.identifier+" "+m.tagbase+" "+m.taglimit);
			out.writeUTF(m.identifier);
			out.writeInt(m.tagbase);
			out.writeInt(m.taglimit);
		}
		out.flush(); out.close();
	}
		
	// C:/WorkSpaces/SPort-System/S_Port/src/sport/fec/PREDEF.ATR
	public static void doGeneratePredefAttrFile() {
		System.out.println("\nBEGIN COMPILE _PREDEF TO .ATTR FILE");
		String fileName = "C:/WorkSpaces/SimulettaFECinJava/SimulettaFEC/src/simuletta/RTS_FEC_InterfaceGenerator/predef/PREDEF.DEF";
		File outputFile=new File("C:/WorkSpaces/SPort-System/S_Port/src/sport/fec/PREDEF.ATR");
		PredefGlobal.outputDir=new File("C:/WorkSpaces/SPort-System/S_Port");

		//outputFile.getParentFile().mkdirs();
		try { outputFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
		PredefCompiler compiler = new PredefCompiler(fileName,outputFile);
		compiler.doCompile();
		System.out.println("    Attrs Output: "+PredefGlobal.attributeFile+"   Length="+PredefGlobal.attributeFile.length());

		System.out.println("--- END COMPILE _PREDEF TO .ATTR FILE");
	}
	
}
