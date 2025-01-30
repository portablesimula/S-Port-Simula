package simuletta.compiler;

import static simuletta.compiler.common.S_Instructions.*;

public class MacroSymbol {
	int s;
	String box;
	String v;
	
	public MacroSymbol(int s, String box, String v) {
		this.s=s; this.box=box; this.v=v;
	}
	
	public void dump() {
		System.out.println(" MacroSymbol: " + edSymbol(s) + "/" + box + "/" + v );
	}
}
