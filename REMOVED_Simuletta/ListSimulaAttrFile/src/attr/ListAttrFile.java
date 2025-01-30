package attr;

import static attr.Util.*;

public class ListAttrFile {
	
	public static void main(String[] argv) {
//		String fileName="C:/WorkSpaces/SPort-System/S_Port/Attrs/FEC/NEXT-PREDEF.atr";
//		String fileName="C:/WorkSpaces/SPort-System/S_Port/Attrs/FEC/PREDEF.atr";
		
//		String fileName="C:/GitHub/SimulaCompiler/Simula/src/sport/rts/temp/adHoc01.sim.atr";
//		String fileName="C:/GitHub/SimulaCompiler/Simula/src/sport/rts/temp/PREDEF.DEF.atr";
		
		String fileName="C:/GitHub/SimulaCompiler/Simula/src/sport/rts/temp/simtst119MainSeparate.sim.atr";

		
		AttrFile attributeFile=new AttrFile(fileName);

		attributeFile.treatExternalHead();

		if(key != mainKey) expxerr("mainKey");
		inpt.nextKey();
		if(key >= lowkey) ERROR("Illegal start of Quantity");
		QUANT.fetchquant(); indent++;
        if(key!=begList) ERROR("Out of sync");
        inpt.nextKey();
        while(key < lowkey) {
        	// ======================================
        	QUANT.fetchquant(); indent++;
        	// ======================================
        	if(key == begList) attributeFile.readList();
			indent--;
        } // end reading quants in list;
        if(key != endlist) ERROR("Out of sync: key="+key);
        inpt.nextKey();
        //System.out.println("ENDOF ListAttrFile.expandextlist: ");
		
		
		if(key != mainKey) expxerr("mainKey");

		System.out.println("\nEND ListAttrFile");
	}

}
