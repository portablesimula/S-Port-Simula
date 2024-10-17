package bec.util;

public class QuantityDescriptor {
	ResolvedType type;
	int repCount;
	
	public QuantityDescriptor() {
		repCount = -1;
		parse();
	}

	/**
	 * quantity_descriptor ::= resolved_type < Rep count:number >?
	 * 
	 * resolved_type
	 * 		::= resolved_structure | simple_type
	 * 		::= INT range lower:number upper:number
	 * 		::= SINT
	 */
	public void parse() {
		type = new ResolvedType();
		if(Scode.accept(Scode.S_REP))
			repCount = Scode.inNumber();
//		System.out.println("NEW QuantityDescriptor: " + this);
	}
	
	public String toString() {
		if(repCount >= 0 )
		return "" + type + " " + repCount;
		return "" + type;
	}
	

}
