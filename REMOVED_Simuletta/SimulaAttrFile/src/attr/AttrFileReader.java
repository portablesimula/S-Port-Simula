package attr;

import comn.extbrecord;
import comn.extquantity;
import comn.quantity;

public class AttrFileReader {
	
	public static void main(String[] argv) {
		AttributeFile attributeFile=new AttributeFile();
		quantity mainqnt=new quantity();
		mainqnt.symb="_predefmoduleXXX";
		
		extbrecord b=new extbrecord();
		mainqnt.descr=b;
//		quantity fpar=new quantity();
		quantity fpar=new extquantity(b);
		mainqnt.descr.fpar=fpar;
		fpar.symb="_predefmodule";
		extbrecord bb=new extbrecord();
		fpar.descr=bb;
		
//		attributeFile.expandexternals(new brecord());
		attributeFile.expandexternals(mainqnt.descr);
		
		System.out.println("\nEND AttrFileReader");
		
		
		AttrFileWriter.afileWriter(mainqnt);
	}
	
}
