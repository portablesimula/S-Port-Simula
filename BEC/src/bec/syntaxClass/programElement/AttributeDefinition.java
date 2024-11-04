package bec.syntaxClass.programElement;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.QuantityDescriptor;
import bec.util.Scode;

public class AttributeDefinition extends ProgramElement {
	public int tag;
	public QuantityDescriptor quant;
	public int rela;
	
	public AttributeDefinition(int rela) {
		this.rela = rela;
		parse();
	}

	/**
	 *	attribute_definition ::= attr attr:newtag quantity_descriptor
	 */
	public void parse() {
		tag = Scode.inTag(this);
//		System.out.println("NEW AttributeDefinition: tag="+tag);
		quant = new QuantityDescriptor();
//		System.out.println("NEW AttributeDefinition: " + this);
	}
	
	public int size() {
		return quant.size();
	}
	
	public String toString() {
		return "ATTR[" + rela + "] " + Scode.edTag(tag) + " " + quant;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	public AttributeDefinition(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag(this);
		rela = inpt.readShort();
		quant = QuantityDescriptor.read(inpt);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_ATTR);
		oupt.writeTag(tag);
		oupt.writeShort(rela);
		quant.write(oupt);
	}

	public static AttributeDefinition read(AttributeInputStream inpt) throws IOException {
		return(new AttributeDefinition(inpt));
	}

}
