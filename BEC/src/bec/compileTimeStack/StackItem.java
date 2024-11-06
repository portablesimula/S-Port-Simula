package bec.compileTimeStack;

//Record Object;
//begin range(0:MaxByte) kind;   --- Object kind code
//      range(0:MaxType) type; 
//end;


//Record StackItem:Object;
public class StackItem {
//	int kind;   // Object kind code
	public int type;
	public int repdist;
	public StackItem suc;
    StackItem pred;

}
