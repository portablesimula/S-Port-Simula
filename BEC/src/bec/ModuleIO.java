package bec;

import java.io.FileOutputStream;
import java.io.IOException;

import bec.descriptor.Descriptor;
import bec.descriptor.Kind;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Util;

public class ModuleIO {

//	%title ***   O u t p u t    M o d u l e   ***
	
	private static void writeDescriptors(AttributeOutputStream modoupt, int nXtag) throws IOException {
//		for(int i=0;i<nXtag;i++) {
//			int tx = Global.TAGTAB.get(i);
//			Descriptor d = Global.DISPL.get(tx);
//			System.out.println("XTAGIDENT: " + i + ": " + Scode.TAGIDENT.get(d.tag.val));
//		}
//		Util.IERR("");

		for(int i=0;i<=nXtag;i++) {
			int tx = Global.iTAGTAB.get(i);
			Descriptor d = Global.DISPL.get(tx);
			if(d == null) Util.IERR("External tag " + i + " = Tag " + tx + " is not defined (OutModule)");
//			if(Global.ATTR_OUTPUT_TRACE)
//				System.out.println("iTAGTAB["+i+"] " + d);
			d.write(modoupt);
		}
	}

	public static void outputModule(int nXtag) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("**************   Begin  -  Output-module  " + Global.modident + "  " + Global.modcheck + "   **************");
		AttributeOutputStream modoupt = new AttributeOutputStream(new FileOutputStream(Global.getAttrFileName(Global.modident, ".AT2")));
		modoupt.writeKind(Kind.K_Module);
		modoupt.writeString(Global.modident);
		modoupt.writeString(Global.modcheck);

		Segment.writeSegments(modoupt);
		
		writeDescriptors(modoupt, nXtag);
		
//		writePreamble();
		modoupt.writeKind(Kind.K_EndModule);
		
		if(Global.ATTR_OUTPUT_TRACE)
			System.out.println("**************   Endof  -  Output-module  " + Global.modident + "  " + Global.modcheck + "   **************");
	}

}
