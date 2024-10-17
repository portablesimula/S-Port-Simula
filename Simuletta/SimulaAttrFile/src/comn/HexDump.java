/*
 * (CC) This work is licensed under a Creative Commons
 * Attribution 4.0 International License.
 *
 * You find a copy of the License on the following
 * page: https://creativecommons.org/licenses/by/4.0/
 */
package comn;

import java.util.HexFormat;

public final class HexDump {
	
	private static HexFormat hexFormat = HexFormat.of().withUpperCase();
	
	public static void hexDump(String buffer,int n) {
		int sequ=0;
		String hex="", chr=""; int count=0;
		byte[] bytes=buffer.getBytes();
		if(n > buffer.length()) n=buffer.length();
		for(int i=0;i<n;i++) {
			byte b=bytes[i];
			String hx=Integer.toHexString(b).toUpperCase();
			hx=hexFormat.toHexDigits(b);
			hex=hex+hx+" ";
			if(!Character.isAlphabetic(b)) b='.';
			chr=chr+((char)b)+" ";
			count++;
			if(count==16) {
				String sq=""+sequ;
				while(sq.length()<6) sq=" "+sq;
				System.out.println(sq+":  "+hex+"   "+chr);
				sequ=sequ+16;
				count=0; hex=""; chr="";
			}
		}
		while(hex.length()<(16*4)) hex=hex+" ";
		System.out.println("  "+hex+"   "+chr);
	}
	
	public static void hexDump(int[] bytes,int n) {
		int sequ=0;
		String hex="", chr=""; int count=0;
		for(int i=0;i<n;i++) {
			int b=bytes[i];
			//String hx=Integer.toHexString(b).toUpperCase();
			String hx=hexFormat.toHexDigits(b);
			hx=hx.substring(6);
			hex=hex+hx+" ";
			if(!Character.isAlphabetic(b)) b='.';
			chr=chr+((char)b)+" ";
			count++;
			if(count==16) {
				String sq=""+sequ;
				while(sq.length()<6) sq=" "+sq;
				System.out.println(sq+":  "+hex+"   "+chr);
				sequ=sequ+16;
				count=0; hex=""; chr="";
			}
		}
		while(hex.length()<(16*4)) hex=hex+" ";
		System.out.println("  "+hex+"   "+chr);
	}
	
//	private static void list() {
//		File file=new File("C:\\WorkSpaces\\SPort-Backend\\BackendCompiler\\src\\sport\\bec\\instructions");
//		String[] names=file.list();
//		int n=names.length;
//		for(int i=0;i<n;i++) {
//			String name=names[i];
//			// E.g. 		case oprINFO:   instr=new INFO(); return(instr.readInstr(inpt));
//			int lng=name.length();
//			name=name.substring(0,lng-5);
//			String lead="case opr"+name+": ";
//			while(lead.length()<20) lead=lead+' ';
//			System.out.println(lead+"instr=new "+name+"(); return(instr.readInstr(inpt));");
//		}
//	}
	
	

}
