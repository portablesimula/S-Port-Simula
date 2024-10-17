package bec.util;

public class Range {
	int low, high;
	
	public Range() {
		parse();
	}

	public void parse() {
		low = Scode.inNumber();
		high = Scode.inNumber();
	}
	
	public String toString() {
		return "RANGE(" + low + ':' + high + ')';
	}
	
}
