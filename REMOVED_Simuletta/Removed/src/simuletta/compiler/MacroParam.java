package simuletta.compiler;

public class MacroParam {
	int n;
	
	public MacroParam(int n) {
		this.n=n;
	}

    public void dump() {
    	System.out.println(" MacroParam#"+n+' ');
    }
}
