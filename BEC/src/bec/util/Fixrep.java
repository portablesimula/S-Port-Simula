package bec.util;

public class Fixrep {
	int rep;
	
	public Fixrep() {
		parse();
	}

	public void parse() {
		rep = Scode.inNumber();
	}
	
	public String toString() {
		return "FIXREP " + rep;
	}
	
}
