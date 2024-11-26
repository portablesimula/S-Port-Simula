package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ConstItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.AddressValue;
import bec.value.BooleanValue;
import bec.value.CharacterValue;
import bec.value.DotAddress;
import bec.value.IntegerValue;
import bec.value.LongRealValue;
import bec.value.RealValue;
import bec.value.RecordValue;
import bec.value.SizeValue;
import bec.value.TextValue;
import bec.value.Value;
import bec.virtualMachine.SVM_PUSHC;

public abstract class PUSHC extends Instruction {
	
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
	 * 
	 * pushc constant:value
	 * push( VAL, constant.TYPE, "value" );
	 * 
	 * A descriptor of the given value is pushed onto the stack.
	 */
	public static void ofScode() {
		Type type = null;
		Value value = null;
		Scode.inputInstr();
		switch(Scode.curinstr) {
		    case Scode.S_C_INT:    type = Type.T_INT; value = new IntegerValue(); break;
		    case Scode.S_C_REAL:   type = Type.T_REAL; value = new RealValue(); break;
		    case Scode.S_C_LREAL:  type = Type.T_LREAL; value = new LongRealValue(); break;
		    case Scode.S_C_CHAR:   type = Type.T_CHAR; value = new CharacterValue(); break;
		    case Scode.S_NOSIZE:   type = Type.T_SIZE; value = null; break;
		    case Scode.S_C_SIZE:   type = Type.T_SIZE; value = new SizeValue(); break;
		    case Scode.S_TRUE:     type = Type.T_BOOL; value = new BooleanValue(true); break;
		    case Scode.S_FALSE:    type = Type.T_BOOL; value = new BooleanValue(false); break;
		    case Scode.S_ANONE:    type = Type.T_AADDR; value = null; break;
		    case Scode.S_C_AADDR:  type = Type.T_AADDR; value = AddressValue.ofAADDR(); break;
		    case Scode.S_NOWHERE:  type = Type.T_PADDR; value = null; break;
		    case Scode.S_C_PADDR:  type = Type.T_PADDR; value = AddressValue.ofPADDR(); break;
			case Scode.S_NOBODY:   type = Type.T_RADDR; value = null; break;
		    case Scode.S_C_RADDR:  type = Type.T_RADDR; value = AddressValue.ofRADDR(); break;
		    case Scode.S_ONONE:    type = Type.T_OADDR; value = null; break;
		    case Scode.S_C_OADDR:  type = Type.T_OADDR; value = AddressValue.ofOADDR(); break;
		    case Scode.S_GNONE:    type = Type.T_GADDR; value = null; break;
		    case Scode.S_C_GADDR:  type = Type.T_GADDR; value = AddressValue.ofGADDR(); break;
		    case Scode.S_C_DOT:	   value = new DotAddress(); type = value.type; break;
		    case Scode.S_C_RECORD: value = RecordValue.ofScode(); type = value.type; break;
		    case Scode.S_TEXT: 	   type = Type.T_TEXT; value = new TextValue(); break;
		    default: Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr));
		}
		ConstItem cns = new ConstItem(type, value);
		CTStack.push(cns);
		CTStack.dumpStack("PUSHC: "+value+": ");
//		Util.IERR("DETTE MÅ RETTES");
		
		Global.PSEG.emit(new SVM_PUSHC(type, value), "");
		Global.PSEG.dump("PUSHC: "+value+": ");
		if(type == Type.T_TEXT) {
			Global.CSEG.dump("PUSHC.doCode: ");
//			Util.IERR(""+value);
		}
	}

}
