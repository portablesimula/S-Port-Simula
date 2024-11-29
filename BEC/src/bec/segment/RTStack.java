package bec.segment;

import java.util.Stack;

import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.MemAddr;
import bec.value.TextValue;
import bec.value.Value;

public abstract class RTStack {
	private static Stack<Value> stack = new Stack<Value>();

	public static void push(Type type, Value value) {
		
		switch(type.tag) {
		case Scode.TAG_TEXT:
//			System.out.println("RTStack.push: " + value.getClass().getSimpleName());
			TextValue text = (TextValue) value;
			push(Type.T_OADDR, text.addr); // CHRADR.OADDR
			push(Type.T_INT, null);        // CHRADR.Offset
			push(Type.T_INT, new IntegerValue(text.value.length())); // NCHR
//			Util.IERR("");
			return;
//			break;
		case Scode.TAG_GADDR:
			if(value == null) {
				stack.push(null);
				stack.push(null);
			} else {
//				System.out.println("RTStack.push: " + value.getClass().getSimpleName());
//				RepetitionValue repval = (RepetitionValue) value;
//				for(Value val:repval.values) {
//					push(type, value);
//				}
				MemAddr oaddr = (MemAddr) value;
				push(Type.T_OADDR, oaddr);
				Util.IERR("");
			}
		}
//		System.out.println("RTStack.push: " + value);
		stack.push(value);
//		Util.IERR("");
	}

	public static Value pop() {
		return stack.pop();
//		Util.IERR("");
	}
}
