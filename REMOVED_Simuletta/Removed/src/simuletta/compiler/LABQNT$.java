package simuletta.compiler;

// ************************************************************
// *** FRAMEWORK for NonLocal Label-Parameters in Java Coding
// ************************************************************
public final class LABQNT$ extends RuntimeException {
	static final long serialVersionUID = 42L;
//	public final RTObject$ SL$; // Static link, i.e. the block in which the label is defined.
	public final boolean isGlobal;  // Labels may be local to a Routine or Global in a Module
	public final int index; // I.e. ordinal number of the Label within its Scope(staticLink).
	public final String identifier; // To improve error and trace messages.

	// Constructor
//	public LABQNT$(final RTObject$ SL$,final int index,final String identifier) {
	public LABQNT$(final boolean isGlobal,final int index,final String identifier) {
//		this.SL$ = SL$;
		this.isGlobal=isGlobal;
		this.index = index;
		this.identifier = identifier;
	}

	public String toString() {
//		return ("LABQNT$(" + SL$ + ", LABEL#" + index + ", identifier=" + identifier + ')');
		return ("LABQNT$("+(isGlobal?"GLOBAL":"LOCAL")+", LABEL#" + index + ", identifier=" + identifier + ')');
	}
}
