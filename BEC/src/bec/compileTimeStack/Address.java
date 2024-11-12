package bec.compileTimeStack;

import bec.util.Scode;
import bec.value.MemAddr;

//Record Address:StackItem;
//begin infix(MemAddr) Objadr;   -- Object Address
//      range(0:MaxWord) Offset; -- Attribute Offset
//      range(0:2) ObjState;     -- NotStacked ! FromConst ! Calculated
//      range(0:2) AtrState;     -- NotStacked ! FromConst ! Calculated
//end;
public class Address extends StackItem {
//	Visible Define NotStacked=0,FromConst=1,Calculated=2;
	public enum State { NotStacked , FromConst , Calculated }
	public MemAddr objadr;
	public int offset;
	public State objState;
	public State atrState;
	
	public Address(int type, int offset, MemAddr objadr) {
		this.type = type;
//		this.repdist = DataType.TTAB[type].nbyte;
		this.repdist = DataType.typeSize(type);
		this.objadr = objadr;
		this.offset = offset;
		this.objState = State.NotStacked;
		this.atrState = State.NotStacked;
	}

	public String toString() {
		return Scode.edTag(type) + " AT " + objadr + "[" + offset + "] objState="+objState + ", atrState="+atrState;
	}
}
