package bec.descriptor;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Tag;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;

//Record IntDescr:Descriptor;      -- K_Globalvar
//begin infix(MemAddr) adr;        -- K_IntLabel
//end;                             -- K_IntRoutine   Local Routine  ER FJÆRNET !"!!
public class LabelDescr extends Descriptor {
	public ProgramAddress adr;

	private LabelDescr(int kind, Tag tag) {
		super(kind, tag);
	}
	
	public static LabelDescr ofLabelSpec() {
//		Tag tag = Tag.ofScode();
		Tag tag = Tag.ofScode();
		LabelDescr lab = (LabelDescr) Global.DISPL.get(tag.val);
		if(lab != null) Util.IERR("");
		lab = new LabelDescr(Kind.K_IntLabel,tag);
		lab.adr = null;
		return lab;
	}
	
	public static LabelDescr ofLabel(Tag tag) {
		LabelDescr lab = (LabelDescr) Global.DISPL.get(tag.val);
		if(lab == null) lab = new LabelDescr(Kind.K_IntLabel,tag);
		lab.adr = Global.PSEG.nextAddress();
		CTStack.checkStackEmpty();
		return lab;
	}
	
	public String toString() {
		return "IntDescr " + Kind.edKind(kind) + " " + tag + " " + adr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("IntDescr.Write: " + this);
		oupt.writeKind(kind);
		tag.write(oupt);
		if(adr != null) {
			oupt.writeBoolean(true);
			adr.write(oupt);
		} else {
			oupt.writeBoolean(false);
		}
	}

	public static LabelDescr read(AttributeInputStream inpt, int kind) throws IOException {
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  BEGIN IntDescr.Read");
		Tag tag = Tag.read(inpt);
		LabelDescr lab = new LabelDescr(kind, tag);
		System.out.println("AFTER NEW CONST: "+lab);
		boolean present = inpt.readBoolean();
		if(present) lab.adr = (ProgramAddress) Value.read(inpt);
		System.out.println("AFTER NEW MEMADDR: "+lab);
		if(Global.ATTR_INPUT_TRACE) System.out.println("LabelDescr.Read: " + lab);
		return(lab);
	}


}
