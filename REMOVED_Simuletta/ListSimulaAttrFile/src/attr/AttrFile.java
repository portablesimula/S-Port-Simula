package attr;

import static attr.Util.*;

public class AttrFile {
	public static String attrck;
	public static String attrmod;

	public AttrFile(String fileName) {
		inpt=new ByteInputStream(fileName);
		if(inpt.inbyte() != layoutindex) ERROR("wrongLayout");
		inpt.in2byte(); // firstbufsize: SKIP it
		String head=inpt.intext(12);
		System.out.println("AttrFile.openAttrFile: head="+head);
		// must start "S-port 108.1x" where 'x' is layout index;
//		if(!head.startsWith("S-port ")) ERROR("wrongLayout");

		//       sysattrfile=head.charAt(11) == ':'; // TODO: ??????????
		//       sysattrfile=true;

		attrck=inpt.gettext(); // Check-code
		attrmod=inpt.gettext(); // Module id
		System.out.println("AttrFile.openAttrFile: Check="+attrck+", Module="+attrmod);
	}
	

    /************************************************************************************
     * treatExternalHead:
     ************************************************************************************
	 * 
	 *  <externalHead> ::= <quant> <quantityList>?
	 */
	void treatExternalHead() {
		inpt.nextKey();
        System.out.println("AttrFile.treatExternalHead: key="+edKey(key));
		while(key < lowkey) {
			QUANT.fetchquant(); indent++;
    		//System.out.println("AttrFile-1: RETURN FROM QUANT.fetchquant: Next key="+edKey(key));
			if(key == begList) readList();
			indent--;
		}

	}
	
	
    /************************************************************************************
     * readList:
     ************************************************************************************
	 * 
     *  <quantityList> ::=
	 *    begList
	 *    	<quantityDescriptor>* -- for each new virtual in virt-list
	 *      	                  -- NOTE: the virtuals MUST be first
	 *    	<quantityDescriptor>*
	 *    endList
	 *    
     *		<quantityDescriptor> ::= <quant> <quantityList>?
	 *    
     *			<quant> ::= <quantityHead> <markSequence>?
	 */
	void readList() {
        if(TESTING>2) System.out.println("BEGIN AttrFile.readList: indent="+indent);
		inpt.nextKey();
		while(key < lowkey) {
			QUANT.fetchquant(); indent++;
    		//System.out.println("AttrFile-2: RETURN FROM QUANT.fetchquant: "+" Next key="+edKey(key));
			if(key==begList) readList();
			indent--;
		}
        if(key != endlist) ERROR("Out of sync: key="+key);
		inpt.nextKey();
		if(TESTING>2) System.out.println("ENDOF AttrFile.readList: indent="+indent);
	}

}
