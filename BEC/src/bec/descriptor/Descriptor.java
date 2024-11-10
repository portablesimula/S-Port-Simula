package bec.descriptor;

import java.io.IOException;

import PREV.syntaxClass.SyntaxClass;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;

//Record Object;
//begin range(0:MaxByte) kind;   --- Object kind code
//      range(0:MaxType) type; 
//end;
public class Descriptor {
	int kind;  // Object kind code
	int tag; 

	public  Descriptor(int kind, int tag) {
		this.kind = kind;
		this.tag =tag;
		Global.intoDisplay(this, tag);
	}
	

	/**
	 * Utility print method.
	 * 
	 * @param indent number of spaces leading the lines
	 */
	public void print(final String indent) {
		Util.IERR("Method printTree need a redefinition in "+this.getClass().getSimpleName());
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

	public static SyntaxClass read(AttributeInputStream inpt) throws IOException {
		Util.IERR("Static Method 'readObject' needs a redefiniton");
		return(null);
	}


}
