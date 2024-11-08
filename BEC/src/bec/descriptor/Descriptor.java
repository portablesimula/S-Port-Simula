package bec.descriptor;


//Record Object;
//begin range(0:MaxByte) kind;   --- Object kind code
//      range(0:MaxType) type; 
//end;
public class Descriptor {
	int kind;  // Object kind code
	int type; 

//	public  Descriptor(int kind, int type) {
//		this.kind = kind;
//		this.type =type;
//	}
}
