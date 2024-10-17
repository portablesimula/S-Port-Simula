package bec.syntaxClass.programElement;

public class BODY extends ProgramElement {
	
	public BODY() {
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "BODY "; // + lab;
	}
	

}
