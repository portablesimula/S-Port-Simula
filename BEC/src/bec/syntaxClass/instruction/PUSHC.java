package bec.syntaxClass.instruction;

import bec.compileTimeStack.Coonst;
import bec.compileTimeStack.CTStack;
import bec.segment.ProgramSegment;
import bec.syntaxClass.value.AttributeAddress;
import bec.syntaxClass.value.BooleanValue;
import bec.syntaxClass.value.CharacterValue;
import bec.syntaxClass.value.DotAddress;
import bec.syntaxClass.value.GeneralAddress;
import bec.syntaxClass.value.IntegerValue;
import bec.syntaxClass.value.LongRealValue;
import bec.syntaxClass.value.ObjectAddress;
import bec.syntaxClass.value.ProgramAddress;
import bec.syntaxClass.value.RealValue;
import bec.syntaxClass.value.RecordValue;
import bec.syntaxClass.value.RoutineAddress;
import bec.syntaxClass.value.SizeValue;
import bec.syntaxClass.value.TextValue;
import bec.syntaxClass.value.Value;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_CALL;

public class PUSHC extends Instruction {
	Value value;
	
	public PUSHC() {
		parse();
	}

	/**
	 * stack_instruction ::= pushc value
	 * 
	 *	 value
	 *		::= boolean_value | character_value
	 *		::= integer_value | size_value
	 *		::= real_value | longreal_value
	 *		::= attribute_address | object_address
	 *		::= general_address | program_address
	 *		::= routine_address | record_value
	 * 
	 * text_value      ::= text long_string
	 * boolean_value   ::= true | false 
	 * character_value ::= c-char byte
	 * integer_value   ::= c-int integer_literal:string
	 * real_value      ::= c-real real_literal:string 
	 * longreal_value  ::= c-lreal real_literal:string
	 * size_value      ::= c-size type | nosize
	 * 
	 * attribute_address
	 * 		::= < c-dot attribute:tag >* c-aaddr attribute:tag
	 * 		::= anone
	 * 
	 * object_address
	 * 		::= c-oaddr global_or_const:tag
	 * 		::= onone
	 * 
	 * general_address
	 * 		::= < c-dot attr:tag >* c-gaddr global_or_const:tag
	 * 		::= gnone
	 * 
	 * program_address ::= c-paddr label:tag | nowhere
	 * routine_address ::= c-raddr body:tag | nobody
	 * 
	 * record_value
	 * 		::= c-record structured_type
	 * 			<attribute_value>+ endrecord
	 * 
	 * End-Condition: Scode'nextByte = First byte after the value
	 */
	public void parse() {
		Scode.inputInstr();
		switch(Scode.curinstr) {
		    case Scode.S_TEXT:     value = new TextValue(); break;
		    case Scode.S_C_INT:    value = new IntegerValue(); break;
		    case Scode.S_C_REAL:   value = new RealValue(); break;
		    case Scode.S_C_LREAL:  value = new LongRealValue(); break;
		    case Scode.S_C_CHAR:   value = new CharacterValue(); break;
		    case Scode.S_NOSIZE:   value = new SizeValue(true); break;
		    case Scode.S_C_SIZE:   value = new SizeValue(false); break;
		    case Scode.S_TRUE:     value = new BooleanValue(true); break;
		    case Scode.S_FALSE:    value = new BooleanValue(false); break;
		    case Scode.S_ANONE:    value = new AttributeAddress(true); break;
		    case Scode.S_C_AADDR:  value = new AttributeAddress(false); break;
		    case Scode.S_NOWHERE:  value = new ProgramAddress(true); break;
		    case Scode.S_C_PADDR:  value = new ProgramAddress(false); break;
			case Scode.S_NOBODY:   value = new RoutineAddress(true); break;
		    case Scode.S_C_RADDR:  value = new RoutineAddress(false); break;
		    case Scode.S_ONONE:    value = new ObjectAddress(true); break;
		    case Scode.S_C_OADDR:  value = new ObjectAddress(false); break;
		    case Scode.S_GNONE:    value = new GeneralAddress(true); break;
		    case Scode.S_C_GADDR:  value = new GeneralAddress(false); break;
		    case Scode.S_C_DOT:    value = new DotAddress(); break;
		    case Scode.S_C_RECORD: value = new RecordValue(); break;
		    default: Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr));
		}
	}
	
	@Override
	public void doCode() {
		Coonst cns = new Coonst(value);
		CTStack.push(cns);
	}
//	Visible Routine pushCoonst;
//	import range(0:MaxType) type; infix(ValueItem) itm;
//	begin ref(Object) cns;
//	      cns:=FreeObj(K_Coonst);
//	      if cns <> none
//	      then FreeObj(K_Coonst):=cns qua FreeObject.next;
//	      else L: cns:=PoolNxt; PoolNxt:=PoolNxt+size(Coonst);
//	           if PoolNxt >= PoolBot
//	           then PALLOC(size(Coonst),cns); goto L endif;
//	%+D        ObjCount(K_Coonst):=ObjCount(K_Coonst)+1;
//	      endif;
//	      cns.kind:=K_Coonst; cns.type:=type;
//	      cns qua StackItem.repdist:=TTAB(type).nbyte;
//	      cns qua StackItem.suc:=none; cns qua StackItem.pred:=none;
//	      cns qua Coonst.itm:=itm;
//	%+C   if TTAB(type).nbyte=0 then IERR("No info TYPE-4") endif;
//	      DOpush(cns);
//	end;

	@Override
	public void printTree(final int indent) {
		if(value instanceof RecordValue rVal) {
			sLIST(indent, "PUSHC");
			rVal.printTree(indent + 1);
		} else sLIST(indent, toString());
		
	}
	
	public String toString() {
		return "PUSHC " + value;
	}
	

}
