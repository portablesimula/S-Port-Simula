package bec.virtualMachine;

/**
 * 
 * Remove the top item on the Runtime-Stack and push the negative value
 */
public class SVM_LINE extends SVM_Instruction {
	int sourceLine;

	public SVM_LINE(int type, int sourceLine) {
		this.sourceLine = sourceLine;
	}
	
	@Override	
	public String toString() {
		return "LINE     " + sourceLine;
	}

}
