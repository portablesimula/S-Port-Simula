package bec.compileTimeStack;

import bec.segment.MemAddr;
import bec.util.Util;

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
    
    public void storeInto(MemAddr addr) {
    	Util.IERR("Method 'storeInto' need redefinition in "+this.getClass().getSimpleName());
    }
}
