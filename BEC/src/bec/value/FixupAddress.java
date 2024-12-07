package bec.value;

import bec.descriptor.LabelDescr;
import bec.util.Global;
import bec.util.Type;

public class FixupAddress extends ProgramAddress {
	LabelDescr label;
	
	public FixupAddress(Type type, LabelDescr label) {
		super(type, null, 0);
		this.label = label;
	}

	public void setCurrentPADDR() {
		ProgramAddress current = Global.PSEG.nextAddress();
		this.segID = current.segID;
		this.ofst = current.ofst;
	}
}
